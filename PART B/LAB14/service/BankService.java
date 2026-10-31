package service;
import model.*;
import util.*;
import exception.*;
import java.nio.file.*;
import java.io.IOException;
import java.util.*;
public class BankService {
    private Repository<Account> repository = new Repository<>();
    private ArrayList<String> history = new ArrayList<>();
    private Path folder;
    public BankService(Path folder) throws IOException, ClassNotFoundException {
        this.folder = folder;
        Files.createDirectories(folder.resolve("logs"));
        Path state = folder.resolve("accounts.dat");
        if (Files.exists(state)) { repository.saveAll(StatePersister.load(state)); }
        Path log = folder.resolve("logs/daily.log");
        if (Files.exists(log)) { history.addAll(Files.readAllLines(log)); }
    }
    private Account require(String number) throws AccountNotFoundException {
        Account account = repository.findById(number);
        if (account == null) { throw new AccountNotFoundException(number); }
        return account;
    }
    private void record(String type, String id, long amount) throws IOException {
        String line = type + " " + id + " " + amount;
        TransactionLog.append(folder.resolve("logs/daily.log"),line);
        history.add(line);
    }
    public synchronized Account openAccount(String type, String name, String email,
        String mobile, String pan, String ifsc, long opening) throws BankException {
        if (name == null || name.isBlank() || name.length() > 30
            || !Validator.isValidEmail(email) || !Validator.isValidMobile(mobile)
            || !Validator.isValidPan(pan) || !Validator.isValidIfsc(ifsc)) {
            throw new BankException("Invalid customer details");
        }
        if (opening <= 0) { throw new InvalidAmountException(); }
        Account account;
        switch (type.toUpperCase()) {
            case "SAVINGS" -> account = new SavingsAccount(name,opening,0);
            case "CURRENT" -> account = new CurrentAccount(name,opening,1000);
            case "FD" -> account = new FixedDepositAccount(name,opening);
            default -> throw new BankException("Invalid account type");
        }
        String[] errors = AnnotationValidator.validate(account);
        if (errors.length > 0) { throw new BankException(String.join(", ",errors)); }
        repository.save(account); return account;
    }
    public synchronized void deposit(String id, long amount) throws BankException, IOException {
        require(id).deposit(amount); record("DEPOSIT",id,amount);
    }
    public synchronized void withdraw(String id, long amount) throws BankException, IOException {
        require(id).withdraw(amount); record("WITHDRAW",id,amount);
    }
    public synchronized void transfer(String from, String to, long amount)
        throws BankException, IOException {
        Account source = require(from), target = require(to);
        source.transfer(target,amount);
        record("WITHDRAW",from,amount); record("DEPOSIT",to,amount);
    }
    public synchronized String statement(String id) throws BankException {
        StringBuilder result = new StringBuilder(StatementFormatter.buildStatement(require(id)));
        result.append("\nTransactions\n");
        for (String line : history) {
            String[] p = line.split(" ");
            if (p.length == 3 && p[1].equals(id)) { result.append(line).append("\n"); }
        }
        return result.toString();
    }
    public synchronized ArrayList<Account> listAccounts() {
        ArrayList<Account> list = repository.findAll();
        Collections.sort(list); return list;
    }
    public synchronized long balance(String id) throws BankException { return require(id).getBalance(); }
    public synchronized void save() throws IOException {
        StatePersister.save(repository.findAll().toArray(new Account[0]),folder.resolve("accounts.dat"));
    }
    public synchronized String report() throws IOException {
        return ReportGenerator.generate(folder.resolve("logs"),folder.resolve("report.txt"));
    }
    public void batchDeposit(String id, int tasks, long amount) throws Exception {
        if (tasks <= 0 || amount <= 0) { throw new InvalidAmountException(); }
        TransactionProcessor processor = new TransactionProcessor();
        processor.useAccounts(listAccounts());
        if (processor.get(id) == null) {
            processor.stop(); throw new AccountNotFoundException(id);
        }
        java.util.concurrent.ConcurrentLinkedQueue<Exception> errors =
            new java.util.concurrent.ConcurrentLinkedQueue<>();
        for (int i = 0; i < tasks; i++) {
            processor.submit(() -> {
                try { deposit(processor.get(id).getAccountNumber(),amount); }
                catch (Exception e) { errors.add(e); }
            });
        }
        processor.stop();
        if (!errors.isEmpty()) { throw errors.peek(); }
    }
}
