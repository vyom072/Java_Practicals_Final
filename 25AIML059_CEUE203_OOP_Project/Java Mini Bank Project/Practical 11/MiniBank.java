import java.io.*;
import java.util.*;

public class MiniBank {
    static class Account implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String accountNumber; private final String ownerName; private long balance;
        public Account(String id, String name, long balance) { accountNumber = id; ownerName = name; this.balance = balance; }
        public void deposit(long amount) { if (amount > 0) balance += amount; }
        public boolean withdraw(long amount) { if (amount > 0 && amount <= balance) { balance -= amount; return true; } return false; }
        public long getBalance() { return balance; }
        @Override public String toString() { return accountNumber + " | " + ownerName + " | " + balance; }
    }
    static class TransactionLog {
        private final List<String> entries = new ArrayList<>();
        public void record(String entry) { entries.add(entry); }
        public List<String> getEntries() { return new ArrayList<>(entries); }
        public void save(String filename) throws IOException { try (PrintWriter out = new PrintWriter(new FileWriter(filename))) { for (String entry : entries) out.println(entry); } }
    }
    static class AccountStore {
        private final Map<String, Account> accounts = new LinkedHashMap<>();
        public void add(String id, Account account) { accounts.put(id, account); }
        public List<Account> getAll() { return new ArrayList<>(accounts.values()); }
        public void save(String filename) throws IOException { try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) { out.writeObject(new ArrayList<>(accounts.values())); } }
    }
    public static void main(String[] args) throws IOException {
        AccountStore store = new AccountStore(); TransactionLog log = new TransactionLog();
        Account a = new Account("AC0001", "Tej", 5000); store.add("AC0001", a);
        a.deposit(1000); log.record("Deposited 1000 into AC0001");
        a.withdraw(500); log.record("Withdrew 500 from AC0001");
        for (Account account : store.getAll()) System.out.println(account);
        log.save("transactions.txt"); store.save("accounts.dat");
        System.out.println("Transaction report:"); for (String entry : log.getEntries()) System.out.println(entry);
    }
}
