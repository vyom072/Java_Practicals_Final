import java.util.regex.Pattern;

public class MiniBank {
    interface Transactable { void deposit(long amount); boolean withdraw(long amount); }
    interface InterestBearing { double interestRate(); long getBalance(); default double yearlyInterest() { return getBalance() * interestRate() / 100; } }
    interface Premium {}
    @FunctionalInterface interface WithdrawRule { boolean allow(Account account, long amount); }
    static abstract class Account implements Transactable, InterestBearing {
        private static int counter;
        private final String accountNumber;
        private final String ownerName;
        private long balance;
        public Account(String name, long balance) { accountNumber = String.format("AC%04d", ++counter); ownerName = name; this.balance = balance; }
        public abstract boolean canWithdraw(long amount);
        public void deposit(long amount) { if (amount > 0) balance += amount; }
        public boolean withdraw(long amount) { if (amount > 0 && canWithdraw(amount)) { balance -= amount; return true; } return false; }
        public long getBalance() { return balance; }
        public String toString() { return accountNumber + " | " + ownerName + " | " + balance; }
    }
    static class SavingsAccount extends Account implements Premium {
        private final long minBalance;
        public SavingsAccount(String name, long balance, long minBalance) { super(name, balance); this.minBalance = minBalance; }
        public double interestRate() { return 4.0; }
        public boolean canWithdraw(long amount) { return amount > 0 && amount <= getBalance() - minBalance; }
    }
    static class Validator {
        private static final Pattern MOBILE = Pattern.compile("^[6-9][0-9]{9}$");
        public static boolean isValidMobile(String s) { return s != null && MOBILE.matcher(s).matches(); }
    }
    public static void main(String[] args) {
        Account a = new SavingsAccount("Tej", 5000, 1000);
        WithdrawRule first = new WithdrawRule() { public boolean allow(Account account, long amount) { return account.canWithdraw(amount); } };
        WithdrawRule second = (account, amount) -> account.canWithdraw(amount);
        System.out.println(first.allow(a, 500));
        System.out.println(second.allow(a, 500));
        System.out.println("Yearly interest: " + a.yearlyInterest());
        System.out.println("Premium: " + (a instanceof Premium));
        System.out.println(Validator.isValidMobile("9876543210"));
    }
}
