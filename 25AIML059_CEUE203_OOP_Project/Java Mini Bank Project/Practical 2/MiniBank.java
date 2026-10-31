public class MiniBank {
    static class Customer {
        private String name, email, mobile;
        private final String customerId;
        private static long customerCounter = 100;
        private static String generateCustomerId() { return "CUST" + (++customerCounter); }
        public Customer(String name, String email, String mobile) {
            this.name = name; this.email = email; this.mobile = mobile;
            customerId = generateCustomerId();
        }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getMobile() { return mobile; }
        public String getCustomerId() { return customerId; }
    }

    static class Account {
        private final String accountNumber;
        private String ownerName;
        private long balance;
        private boolean active = true;
        private static long counter;
        public Account(String ownerName, long balance) {
            accountNumber = String.format("AC%04d", ++counter);
            this.ownerName = ownerName; this.balance = balance;
        }
        public Account(String ownerName) { this(ownerName, 0); }
        public void deposit(long amount) { if (amount > 0) balance += amount; }
        public boolean withdraw(long amount) {
            if (amount > 0 && amount <= balance) { balance -= amount; return true; }
            return false;
        }
        public String getAccountNumber() { return accountNumber; }
        public String getOwnerName() { return ownerName; }
        public long getBalance() { return balance; }
        public boolean isActive() { return active; }
    }

    public static void main(String[] args) {
        Customer customer = new Customer("Tej", "tej@test.com", "9876543210");
        System.out.println(customer.getCustomerId() + " " + customer.getName());
        Account[] accounts = { new Account("Riya", 5000), new Account("Tej"), new Account("Aman", 2000) };
        accounts[0].deposit(2000);
        System.out.println(accounts[0].getBalance());
        System.out.println(accounts[0].withdraw(3000));
        System.out.println(accounts[0].getBalance());
        System.out.println(accounts[0].withdraw(10000));
        for (Account a : accounts) System.out.println(a.getAccountNumber() + " " + a.getBalance());
    }
}
