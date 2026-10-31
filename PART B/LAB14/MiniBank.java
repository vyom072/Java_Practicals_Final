import java.io.*;
import java.lang.annotation.*;
import java.lang.reflect.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength { int value(); }

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {}

class AnnotationValidator {
    public static void validate(Object obj) throws IllegalAccessException, IllegalArgumentException {
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object val = f.get(obj);
            if (f.isAnnotationPresent(MaxLength.class)) {
                int max = f.getAnnotation(MaxLength.class).value();
                if (val instanceof String s && s.length() > max) {
                    throw new IllegalArgumentException(f.getName() + " exceeds max length of " + max);
                }
            }
            if (f.isAnnotationPresent(Positive.class)) {
                if (val instanceof Number n && n.doubleValue() <= 0) {
                    throw new IllegalArgumentException(f.getName() + " must be positive");
                }
            }
        }
    }
}

class BankException extends RuntimeException {
    public BankException(String message) { super(message); }
}

class AccountNotFoundException extends BankException {
    public AccountNotFoundException(String id) { super("Account not found: " + id); }
}

class InsufficientFundsException extends BankException {
    public InsufficientFundsException(String msg) { super(msg); }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String msg) { super(msg); }
}

interface Transactable {
    void deposit(long amount);
    void withdraw(long amount);
}

interface InterestBearing {
    double calculateInterest();
}

interface Premium {}

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER,
    STATEMENT, LIST_ACCOUNTS, BATCH, REPORT, EXIT
}

enum TransactionType { DEPOSIT, WITHDRAW, TRANSFER }

record Command(String action, String accountNumber, long amount) {}

record Customer(String name) implements Serializable {}

abstract class Account implements Transactable, Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    protected String accountNumber;
    protected Customer customer;
    protected long balance;
    protected List<String> transactions = new ArrayList<>();

    public Account(String accountNumber, Customer customer, long balance) {
        this.accountNumber = accountNumber;
        this.customer = customer;
        this.balance = balance;
        addTransaction("Account opened with balance: " + balance);
    }

    public String getAccountNumber() { return accountNumber; }
    public long getBalance() { return balance; }
    public Customer getCustomer() { return customer; }
    public List<String> getTransactions() { return Collections.unmodifiableList(transactions); }

    public synchronized void addTransaction(String entry) {
        String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        transactions.add("[" + ts + "] " + entry);
    }

    @Override
    public synchronized void deposit(long amount) {
        if (amount <= 0) throw new InvalidAmountException("Deposit amount must be positive");
        balance += amount;
        addTransaction("Deposited: " + amount + " | New Balance: " + balance);
    }

    @Override
    public abstract void withdraw(long amount);

    @Override
    public String toString() {
        return getClass().getSimpleName() + " [Acc=" + accountNumber + ", Name=" + customer.name()
                + ", Balance=" + balance + "]";
    }
}

class SavingsAccount extends Account implements InterestBearing {
    public SavingsAccount(String id, Customer c, long bal) { super(id, c, bal); }

    @Override
    public synchronized void withdraw(long amount) {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        if (balance - amount < 0) throw new InsufficientFundsException("Insufficient balance in Savings account");
        balance -= amount;
        addTransaction("Withdrew: " + amount + " | New Balance: " + balance);
    }

    @Override
    public double calculateInterest() { return balance * 0.04; }
}

class CurrentAccount extends Account implements Premium {
    private static final long OVERDRAFT_LIMIT = 1000;

    public CurrentAccount(String id, Customer c, long bal) { super(id, c, bal); }

    @Override
    public synchronized void withdraw(long amount) {
        if (amount <= 0) throw new InvalidAmountException("Withdrawal amount must be positive");
        if (balance - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientFundsException("Overdraft limit exceeded (-1000 max)");
        }
        balance -= amount;
        addTransaction("Withdrew: " + amount + " | New Balance: " + balance);
    }
}

class FixedDepositAccount extends Account implements InterestBearing {
    public FixedDepositAccount(String id, Customer c, long bal) { super(id, c, bal); }

    @Override
    public synchronized void withdraw(long amount) {
        throw new UnsupportedOperationException("Withdrawals are locked for Fixed Deposit accounts");
    }

    @Override
    public double calculateInterest() { return balance * 0.07; }
}

class Validator {
    public static void validateOpening(String type, String name, long balance) {
        if (!List.of("SAVINGS", "CURRENT", "FD").contains(type.toUpperCase()))
            throw new IllegalArgumentException("Type must be SAVINGS, CURRENT, or FD");
        if (name == null || name.isBlank() || name.length() > 30)
            throw new IllegalArgumentException("Name cannot be blank and must be <= 30 characters");
        if (balance <= 0)
            throw new IllegalArgumentException("Opening balance must be positive");
    }
}

class CommandParser {
    public static Command parse(String input) {
        String[] parts = input.trim().split("\\s+");
        if (parts.length < 3) throw new IllegalArgumentException("Invalid command syntax: " + input);
        String action = parts[0].toUpperCase();
        String acc = parts[1];
        long amount = Long.parseLong(parts[2]);
        return new Command(action, acc, amount);
    }
}

class BankService {
    private final Map<String, Account> accounts = new ConcurrentHashMap<>();
    private final Path storageDir;
    private final Path accountsFile;
    private final Path logFile;
    private int nextId = 1001;

    public BankService(Path storageDir) throws IOException {
        this.storageDir = storageDir;
        Files.createDirectories(storageDir);
        Files.createDirectories(storageDir.resolve("logs"));
        this.accountsFile = storageDir.resolve("accounts.dat");
        this.logFile = storageDir.resolve("logs").resolve("daily.log");
        load();
    }

    public synchronized Account openAccount(String type, String name, long openingBalance) {
        Validator.validateOpening(type, name, openingBalance);
        String id = "ACC" + (nextId++);
        Customer customer = new Customer(name);

        Account acc = switch (type.toUpperCase()) {
            case "SAVINGS" -> new SavingsAccount(id, customer, openingBalance);
            case "CURRENT" -> new CurrentAccount(id, customer, openingBalance);
            case "FD" -> new FixedDepositAccount(id, customer, openingBalance);
            default -> throw new IllegalArgumentException("Unknown type: " + type);
        };

        accounts.put(id, acc);
        logTransaction("OPEN " + id + " " + type.toUpperCase() + " " + openingBalance);
        return acc;
    }

    public Account getAccount(String id) {
        Account a = accounts.get(id);
        if (a == null) throw new AccountNotFoundException(id);
        return a;
    }

    public synchronized void deposit(String id, long amount) {
        Account a = getAccount(id);
        a.deposit(amount);
        logTransaction("DEPOSIT " + id + " " + amount);
    }

    public synchronized void withdraw(String id, long amount) {
        Account a = getAccount(id);
        a.withdraw(amount);
        logTransaction("WITHDRAW " + id + " " + amount);
    }

    public void transfer(String fromId, String toId, long amount) {
        if (fromId.equals(toId)) throw new IllegalArgumentException("Cannot transfer to the same account");
        Account a1 = getAccount(fromId);
        Account a2 = getAccount(toId);

        Account first = fromId.compareTo(toId) < 0 ? a1 : a2;
        Account second = fromId.compareTo(toId) < 0 ? a2 : a1;

        synchronized (first) {
            synchronized (second) {
                a1.withdraw(amount);
                a2.deposit(amount);
            }
        }
        logTransaction("TRANSFER " + fromId + " -> " + toId + " " + amount);
    }

    public long balance(String id) {
        return getAccount(id).getBalance();
    }

    public String statement(String id) {
        Account a = getAccount(id);
        StringBuilder sb = new StringBuilder("--- Statement for " + id + " (" + a.getCustomer().name() + ") ---\n");
        for (String tx : a.getTransactions()) {
            sb.append(tx).append("\n");
        }
        sb.append("Current Balance: ").append(a.getBalance());
        return sb.toString();
    }

    public Collection<Account> listAccounts() {
        return accounts.values();
    }

    public void batchDeposit(String id, int tasks, long amountPerTask) throws InterruptedException {
        Account a = getAccount(id);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        CountDownLatch latch = new CountDownLatch(tasks);

        for (int i = 0; i < tasks; i++) {
            executor.submit(() -> {
                try {
                    a.deposit(amountPerTask);
                } finally {
                    latch.countDown();
                }
            });
        }
        latch.await();
        executor.shutdown();
        logTransaction("BATCH_DEPOSIT " + id + " " + tasks + "x" + amountPerTask);
    }

    public String report() {
        long totalAccounts = accounts.size();
        long totalBalance = accounts.values().stream().mapToLong(Account::getBalance).sum();
        Map<String, Long> countByType = accounts.values().stream()
                .collect(Collectors.groupingBy(a -> a.getClass().getSimpleName(), Collectors.counting()));

        return String.format("""
                ================ MiniBank System Report ================
                Total Accounts : %d
                Total Balance  : ₹%d
                Distribution   : %s
                ========================================================""",
                totalAccounts, totalBalance, countByType);
    }

    private void logTransaction(String entry) {
        try {
            String line = "[" + LocalDateTime.now() + "] " + entry + System.lineSeparator();
            Files.writeString(logFile, line, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ignored) {}
    }

    @SuppressWarnings("unchecked")
    private void load() {
        if (!Files.exists(accountsFile)) return;
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(accountsFile))) {
            Map<String, Account> loaded = (Map<String, Account>) ois.readObject();
            accounts.putAll(loaded);
            nextId = accounts.keySet().stream()
                    .map(s -> s.replace("ACC", ""))
                    .mapToInt(Integer::parseInt)
                    .max().orElse(1000) + 1;
        } catch (Exception e) {
            System.out.println("No previous state loaded: " + e.getMessage());
        }
    }

    public void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(accountsFile))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            System.out.println("Failed to save accounts: " + e.getMessage());
        }
    }
}

public class MiniBank {
    static String read(Scanner sc, String label) {
        System.out.print(label + ": ");
        return sc.nextLine().trim();
    }

    public static void main(String[] args) {
        BankInfo info = new BankInfo("MiniBank", "Ahmedabad");
        System.out.println("Welcome to " + info.name() + " Banking Services | Branch: " + info.branch());
        try {
            BankService bank = new BankService(Path.of("data"));
            Scanner sc = new Scanner(System.in);
            boolean exit = false;

            while (!exit) {
                System.out.println("""
                        \n1. Open account
                        2. Deposit
                        3. Withdraw
                        4. Transfer
                        5. Statement
                        6. List accounts
                        7. Batch deposits
                        8. Report
                        9. Exit""");
                try {
                    int choice = Integer.parseInt(sc.nextLine());
                    if (choice < 1 || choice > 9) {
                        System.out.println("Invalid choice");
                        continue;
                    }
                    MenuOption option = MenuOption.values()[choice - 1];
                    switch (option) {
                        case OPEN_ACCOUNT -> {
                            String type = read(sc, "Type SAVINGS/CURRENT/FD");
                            String name = read(sc, "Name");
                            long opening = Long.parseLong(read(sc, "Opening balance"));
                            Account a = bank.openAccount(type, name, opening);
                            System.out.println("Opened: " + a);
                        }
                        case DEPOSIT, WITHDRAW -> {
                            String id = read(sc, "Account");
                            String amount = read(sc, "Amount");
                            Command c = CommandParser.parse(option.name() + " " + id + " " + amount);
                            if (option == MenuOption.DEPOSIT) {
                                bank.deposit(c.accountNumber(), c.amount());
                            } else {
                                bank.withdraw(c.accountNumber(), c.amount());
                            }
                            System.out.println("Balance: " + bank.balance(id));
                        }
                        case TRANSFER -> {
                            String from = read(sc, "From account");
                            String to = read(sc, "To account");
                            String amount = read(sc, "Amount");
                            Command c = CommandParser.parse("TRANSFER " + from + " " + amount);
                            bank.transfer(c.accountNumber(), to, c.amount());
                            System.out.println("Transfer completed");
                        }
                        case STATEMENT -> System.out.println(bank.statement(read(sc, "Account")));
                        case LIST_ACCOUNTS -> {
                            for (Account a : bank.listAccounts()) {
                                System.out.println(a);
                            }
                        }
                        case BATCH -> {
                            String id = read(sc, "Account");
                            int tasks = Integer.parseInt(read(sc, "Number of deposits"));
                            long amount = Long.parseLong(read(sc, "Amount per deposit"));
                            bank.batchDeposit(id, tasks, amount);
                            System.out.println("Balance: " + bank.balance(id));
                        }
                        case REPORT -> System.out.println(bank.report());
                        case EXIT -> {
                            bank.save();
                            exit = true;
                            System.out.println("Saved. Goodbye");
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Startup failed: " + e.getMessage());
        }
    }
}