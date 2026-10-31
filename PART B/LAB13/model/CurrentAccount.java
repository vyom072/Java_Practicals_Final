package model;
public class CurrentAccount extends Account {
    private static final long serialVersionUID = 1L;
    private long overdraftLimit;
    public CurrentAccount(String name, long balance, long overdraftLimit) {
        super(name,balance); this.overdraftLimit = overdraftLimit;
    }
    @Override
    protected long withdrawalCapacity() { return Math.max(0,getBalance() + overdraftLimit); }
    @Override
    public double interestRate() { return 0; }
    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && amount <= getBalance() + overdraftLimit;
    }
}
