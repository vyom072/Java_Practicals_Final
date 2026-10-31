public class StatementFormatter {
    public static String buildStatement(Account a) {
        return new StringBuilder().append("Account: ").append(a.getAccountNumber())
            .append("\nOwner: ").append(a.getOwnerName()).append("\nBalance: ")
            .append(a.getBalance()).append("\nActive: ").append(a.isActive()).toString();
    }
}
