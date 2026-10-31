public class MiniBank {
    static class Account {
        private long balance;
        public Account(long balance) { this.balance = balance; }
        public synchronized void deposit(long amount) { if (amount > 0) balance += amount; }
        public synchronized boolean withdraw(long amount) { if (amount > 0 && amount <= balance) { balance -= amount; return true; } return false; }
        public synchronized long getBalance() { return balance; }
    }
    static class AccountWorker implements Runnable {
        private final Account account; private final long amount; private final int repetitions;
        public AccountWorker(Account account, long amount, int repetitions) { this.account = account; this.amount = amount; this.repetitions = repetitions; }
        public void run() { for (int i = 0; i < repetitions; i++) account.deposit(amount); }
    }
    public static void main(String[] args) throws InterruptedException {
        Account account = new Account(1000);
        Thread t1 = new Thread(new AccountWorker(account, 100, 100));
        Thread t2 = new Thread(new AccountWorker(account, 200, 100));
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println("Final balance: " + account.getBalance());
    }
}
