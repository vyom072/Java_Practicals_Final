package model;
public class SavingsAccount extends Account implements Premium {
    private static final long serialVersionUID = 1L;
    private long minBalance;
    public SavingsAccount(String name, long balance, long minBalance) {
        super(name,balance); this.minBalance = minBalance;
    }
    @Override
    protected long withdrawalCapacity() { return Math.max(0,getBalance() - minBalance); }
    @Override
    public double interestRate() { return 4.0; }
    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && amount <= getBalance() - minBalance;
    }
}
