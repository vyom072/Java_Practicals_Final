package model;
import model.annotation.*;
import exception.*;
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
    protected long withdrawalCapacity() { return balance; }
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);
    @Override
    public synchronized void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) { throw new InvalidAmountException(); }
        balance += amount;
    }
    @Override
    public synchronized boolean withdraw(long amount)
        throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) { throw new InvalidAmountException(); }
        if (!canWithdraw(amount)) {
            long available = Math.max(0, withdrawalCapacity());
            throw new InsufficientFundsException(Math.max(0,amount - available));
        }
        balance -= amount; return true;
    }
    public void transfer(Account to, long amount) throws BankException {
        try {
            if (to == null) { throw new AccountNotFoundException("destination"); }
            if (to == this) { throw new BankException("Choose a different account"); }
            withdraw(amount); to.deposit(amount);
        } catch (BankException e) {
            System.out.println("Transfer failed: " + e.getMessage()); throw e;
        } finally { System.out.println("Transfer attempt completed"); }
    }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    @Override
    public synchronized long getBalance() { return balance; }
    public boolean isActive() { return active; }
}
