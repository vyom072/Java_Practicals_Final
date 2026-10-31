public class MiniBank {
    static abstract class Account {
        private static int counter;
        private final String accountNumber;
        private final String ownerName;
        private long balance;
        public Account(String ownerName, long balance) { accountNumber = String.format("AC%04d", ++counter); this.ownerName = ownerName; this.balance = balance; }
        public abstract double interestRate();
        public abstract boolean canWithdraw(long amount);
        public void deposit(long amount) { if (amount > 0) balance += amount; }
        public boolean withdraw(long amount) { if (amount > 0 && canWithdraw(amount)) { balance -= amount; return true; } return false; }
        public long getBalance() { return balance; }
        @Override public String toString() { return accountNumber + " | " + ownerName + " | " + balance; }
    }
    static class SavingsAccount extends Account {
        private final long minBalance;
        public SavingsAccount(String name, long balance, long minBalance) { super(name, balance); this.minBalance = minBalance; }
        public double interestRate() { return 4.0; }
        public boolean canWithdraw(long amount) { return amount > 0 && amount <= getBalance() - minBalance; }
    }
    static class CurrentAccount extends Account {
        private final long overdraftLimit;
        public CurrentAccount(String name, long balance, long overdraftLimit) { super(name, balance); this.overdraftLimit = overdraftLimit; }
        public double interestRate() { return 0; }
        public boolean canWithdraw(long amount) { return amount > 0 && amount <= getBalance() + overdraftLimit; }
    }
    static class FixedDepositAccount extends Account {
        public FixedDepositAccount(String name, long balance) { super(name, balance); }
        public double interestRate() { return 7.0; }
        public boolean canWithdraw(long amount) { return false; }
    }
    public static void main(String[] args) {
        Account[] accounts = { new SavingsAccount("Riya", 5000, 1000), new CurrentAccount("Tej", 2000, 1000), new FixedDepositAccount("Aman", 9000) };
        for (Account a : accounts) { System.out.println(a + " Rate: " + a.interestRate()); if (a instanceof SavingsAccount s) System.out.println("Savings withdrawal allowed: " + s.canWithdraw(4500)); }
    }
}
