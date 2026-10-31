public class FixedDepositAccount extends Account {
    public FixedDepositAccount(String name, long balance) { super(name,balance); }
    public double interestRate() { return 7.0; }
    public boolean canWithdraw(long amount) { return false; }
}
