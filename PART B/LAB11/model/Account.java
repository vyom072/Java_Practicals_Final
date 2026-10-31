package model;
import model.annotation.*;
import exception.*;
public abstract class Account implements Transactable, InterestBearing, java.io.Serializable {
    private static final long serialVersionUID = 1L;
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
            Account first = this;
            Account second = to;
            if (accountNumber.compareTo(to.accountNumber) > 0) {
                first = to; second = this;
            }
            synchronized (first) {
                synchronized (second) { withdraw(amount); to.deposit(amount); }
            }
        } catch (BankException e) { throw e; }
        finally { System.out.println("Transfer attempt completed"); }
    }
    private void readObject(java.io.ObjectInputStream in)
        throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        long number = Long.parseLong(accountNumber.substring(2));
        synchronized (Account.class) { counter = Math.max(counter,number); }
    }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    @Override
    public synchronized long getBalance() { return balance; }
    public boolean isActive() { return active; }
}
