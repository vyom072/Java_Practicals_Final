import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

public class MiniBank {
    static class Account implements Serializable {
        private static final long serialVersionUID = 1L;
        private final String accountNumber; private final String ownerName; private long balance;
        private transient final Lock lock = new ReentrantLock();
        public Account(String id, String name, long balance) { accountNumber = id; ownerName = name; this.balance = balance; }
        public void deposit(long amount) { synchronized (this) { if (amount > 0) balance += amount; } }
        public synchronized boolean withdraw(long amount) { if (amount > 0 && amount <= balance) { balance -= amount; return true; } return false; }
        public synchronized long getBalance() { return balance; }
        @Override public String toString() { return accountNumber + " | " + ownerName + " | " + getBalance(); }
    }
    static class TransactionBuffer {
        private final BlockingQueue<String> transactions = new LinkedBlockingQueue<>();
        public void add(String transaction) { transactions.offer(transaction); }
        public String take() throws InterruptedException { return transactions.take(); }
    }
    static class TransactionProcessor {
        private final ExecutorService pool = Executors.newFixedThreadPool(2);
        public void submit(Runnable task) { pool.execute(task); }
        public void stop() throws InterruptedException { pool.shutdown(); if (!pool.awaitTermination(1, TimeUnit.MINUTES)) pool.shutdownNow(); }
    }
    public static void main(String[] args) throws InterruptedException, IOException {
        Account account = new Account("AC0001", "Tej", 1000);
        TransactionBuffer buffer = new TransactionBuffer();
        TransactionProcessor processor = new TransactionProcessor();
        buffer.add("Deposit 500"); buffer.add("Withdraw 200");
        processor.submit(() -> account.deposit(500)); processor.submit(() -> account.withdraw(200));
        processor.stop();
        System.out.println("Final balance: " + account.getBalance());
        System.out.println(buffer.take()); System.out.println(buffer.take());
        try (PrintWriter out = new PrintWriter(new FileWriter("transactions.txt"))) {
            out.println("Deposit 500"); out.println("Withdraw 200");
        }
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("accounts.dat"))) { out.writeObject(account); }
    }
}
