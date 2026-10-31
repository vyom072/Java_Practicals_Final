package model;
import model.annotation.*;
public abstract class Account implements Transactable, InterestBearing {
    @Id
    private final String accountNumber;
    @MaxLength(30)
    private String ownerName;
    @Positive
    private long balance;
    private boolean active = true;
    private static long counter;
    public Account(String ownerName, long balance) {
        accountNumber = String.format("AC%04d", ++counter);
        this.ownerName = ownerName; this.balance = balance;
    }
    protected Account(Account other) {
        accountNumber = other.accountNumber;
        ownerName = other.ownerName; balance = other.balance; active = other.active;
    }
    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance + " | " + active;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Account a)) { return false; }
        return accountNumber.equals(a.accountNumber);
    }
    @Override
    public int hashCode() { return accountNumber.hashCode(); }
    public Account(String ownerName) { this(ownerName, 0); }
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);
    @Override
    public void deposit(long amount) {
        if (amount > 0) { balance += amount; }
    }
    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && canWithdraw(amount)) { balance -= amount; return true; }
        return false;
    }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    @Override
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }
}
