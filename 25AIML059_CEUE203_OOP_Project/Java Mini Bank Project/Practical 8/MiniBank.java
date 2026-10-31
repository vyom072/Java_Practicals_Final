public class MiniBank {
    static class BankException extends Exception { public BankException(String message) { super(message); } }
    static class InsufficientFundsException extends BankException { public InsufficientFundsException(String message) { super(message); } }
    static class InvalidAmountException extends BankException { public InvalidAmountException(String message) { super(message); } }
    static class AccountNotFoundException extends BankException { public AccountNotFoundException(String message) { super(message); } }
    interface Transactable { void deposit(long amount) throws BankException; boolean withdraw(long amount) throws BankException; }
    static class Account implements Transactable {
        private final String accountNumber; private long balance;
        public Account(String accountNumber, long balance) { this.accountNumber = accountNumber; this.balance = balance; }
        public void deposit(long amount) throws BankException { if (amount <= 0) throw new InvalidAmountException("Amount must be positive"); balance += amount; }
        public boolean withdraw(long amount) throws BankException {
            if (amount <= 0) throw new InvalidAmountException("Amount must be positive");
            if (amount > balance) throw new InsufficientFundsException("Insufficient funds");
            balance -= amount; return true;
        }
        public long getBalance() { return balance; }
        public String getAccountNumber() { return accountNumber; }
    }
    public static void main(String[] args) {
        Account account = new Account("AC0001", 1000);
        try { account.deposit(500); System.out.println("Balance: " + account.getBalance()); account.withdraw(5000); }
        catch (BankException e) { System.out.println(e.getMessage()); }
    }
}
