package model;
public class SavingsAccount extends Account implements Premium {
    private long minBalance;
    public SavingsAccount(String name, long balance, long minBalance) {
        super(name,balance); this.minBalance = minBalance;
    }
    public double interestRate() { return 4.0; }
    public boolean canWithdraw(long amount) {
        return amount > 0 && amount <= getBalance() - minBalance;
    }
}
