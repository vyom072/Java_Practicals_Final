package model;
public class FixedDepositAccount extends Account {
    public FixedDepositAccount(String name, long balance) { super(name,balance); }
    @Override
    public double interestRate() { return 7.0; }
    @Override
    public boolean canWithdraw(long amount) { return false; }
}
