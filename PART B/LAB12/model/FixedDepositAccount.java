package model;
public class FixedDepositAccount extends Account {
    private static final long serialVersionUID = 1L;
    public FixedDepositAccount(String name, long balance) { super(name,balance); }
    @Override
    protected long withdrawalCapacity() { return 0; }
    @Override
    public double interestRate() { return 7.0; }
    @Override
    public boolean canWithdraw(long amount) { return false; }
}
