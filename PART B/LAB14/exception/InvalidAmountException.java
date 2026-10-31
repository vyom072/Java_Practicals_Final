package exception;
public class InvalidAmountException extends BankException {
    public InvalidAmountException() { super("Amount must be positive"); }
}
