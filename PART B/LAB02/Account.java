public class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active = true;
    private static long counter;
    public Account(String ownerName, long balance) {
        accountNumber = String.format("AC%04d", ++counter);
        this.ownerName = ownerName; this.balance = balance;
    }
    public Account(String ownerName) { this(ownerName, 0); }
    public void deposit(long amount) {
        if (amount > 0) { balance += amount; }
    }
    public boolean withdraw(long amount) {
        if (amount > 0 && amount <= balance) { balance -= amount; return true; }
        return false;
    }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }
}
