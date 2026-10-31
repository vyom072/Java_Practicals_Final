import java.util.*;
import java.util.concurrent.*;

public class MiniBank {
    static class Account {
        private static int counter; private final String accountNumber; private final String ownerName; private long balance;
        public Account(String ownerName, long balance) { accountNumber = String.format("AC%04d", ++counter); this.ownerName = ownerName; this.balance = balance; }
        public synchronized void deposit(long amount) { if (amount > 0) balance += amount; }
        public synchronized boolean withdraw(long amount) { if (amount > 0 && amount <= balance) { balance -= amount; return true; } return false; }
        public synchronized long getBalance() { return balance; }
        public String getAccountNumber() { return accountNumber; }
        @Override public String toString() { return accountNumber + " | " + ownerName + " | " + getBalance(); }
    }
    static class AccountStore {
        private final Map<String, Account> accounts = new ConcurrentHashMap<>();
        private final List<String> history = Collections.synchronizedList(new ArrayList<>());
        public void add(Account account) { accounts.put(account.getAccountNumber(), account); }
        public Account get(String id) { return accounts.get(id); }
        public Collection<Account> getAll() { return new ArrayList<>(accounts.values()); }
        public void record(String entry) { history.add(entry); }
        public List<String> getHistory() { synchronized (history) { return new ArrayList<>(history); } }
        public List<Account> sortedByBalance() { List<Account> list = new ArrayList<>(accounts.values()); list.sort(Comparator.comparingLong(Account::getBalance)); return list; }
    }
    static class AccountWorker implements Runnable {
        private final Account account; private final long amount; private final int repetitions;
        public AccountWorker(Account account, long amount, int repetitions) { this.account = account; this.amount = amount; this.repetitions = repetitions; }
        public void run() { for (int i = 0; i < repetitions; i++) account.deposit(amount); }
    }
    static class TransactionProcessor {
        private final ExecutorService pool = Executors.newFixedThreadPool(4);
        public void submit(Runnable task) { pool.execute(task); }
        public void stop() throws InterruptedException { pool.shutdown(); if (!pool.awaitTermination(1, TimeUnit.MINUTES)) pool.shutdownNow(); }
    }
    public static void main(String[] args) throws InterruptedException {
        AccountStore store = new AccountStore();
        Account a = new Account("Tej", 5000), b = new Account("Riya", 2000), c = new Account("Aman", 9000);
        store.add(a); store.add(b); store.add(c); store.record("Opened " + a.getAccountNumber());
        System.out.println(store.get(b.getAccountNumber()));
        for (Account item : store.sortedByBalance()) System.out.println(item);
        System.out.println(store.getHistory());
        TransactionProcessor processor = new TransactionProcessor();
        for (int i = 0; i < 10; i++) processor.submit(new AccountWorker(a, 100, 1));
        processor.stop(); System.out.println(a);
    }
}
