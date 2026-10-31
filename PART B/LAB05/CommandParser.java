public class CommandParser {
    public static Command parse(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) { throw new IllegalArgumentException("Use TYPE ACCOUNT AMOUNT"); }
        long amount = Long.parseLong(parts[2]);
        if (amount <= 0) { throw new IllegalArgumentException("Amount must be positive"); }
        return new Command(TransactionType.valueOf(parts[0].toUpperCase()),parts[1],amount);
    }
}
