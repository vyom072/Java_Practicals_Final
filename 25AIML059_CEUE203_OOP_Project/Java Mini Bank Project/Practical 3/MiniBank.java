public class MiniBank {
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
        public Account(Account other) {
            accountNumber = other.accountNumber; ownerName = other.ownerName;
            balance = other.balance; active = other.active;
        }
        public Account(String ownerName) { this(ownerName, 0); }
        @Override public String toString() { return accountNumber + " | " + ownerName + " | " + balance + " | " + active; }
        @Override public boolean equals(Object o) { return o instanceof Account a && accountNumber.equals(a.accountNumber); }
        @Override public int hashCode() { return accountNumber.hashCode(); }
        public void deposit(long amount) { if (amount > 0) balance += amount; }
        public boolean withdraw(long amount) { if (amount > 0 && amount <= balance) { balance -= amount; return true; } return false; }
        public String getAccountNumber() { return accountNumber; }
        public String getOwnerName() { return ownerName; }
        public long getBalance() { return balance; }
        public boolean isActive() { return active; }
    }
    static class Customer implements Cloneable {
        public static class Address {
            private final String line, city, pincode;
            public Address(String line, String city, String pincode) { this.line = line; this.city = city; this.pincode = pincode; }
            public String getLine() { return line; }
            public String getCity() { return city; }
            public String getPincode() { return pincode; }
        }
        private String name, email, mobile;
        private final String customerId;
        private Address address;
        private static long customerCounter = 100;
        private static String generateCustomerId() { return "CUST" + (++customerCounter); }
        public Customer(String name, String email, String mobile) { this(name, email, mobile, null); }
        public Customer(String name, String email, String mobile, Address address) {
            this.name = name; this.email = email; this.mobile = mobile; this.address = address;
            customerId = generateCustomerId();
        }
        public String getName() { return name; }
        public String getEmail() { return email; }
        public String getMobile() { return mobile; }
        public String getCustomerId() { return customerId; }
        public Address getAddress() { return address; }
        @Override public Customer clone() { try { return (Customer) super.clone(); } catch (CloneNotSupportedException e) { throw new AssertionError(e); } }
    }
    public static void main(String[] args) {
        Account first = new Account("Riya", 4000);
        Account copy = new Account(first);
        System.out.println(first); System.out.println(copy);
        System.out.println(first.equals(copy));
        Object obj = first; System.out.println(obj instanceof Account);
        Customer c = new Customer("Tej", "tej@test.com", "9876543210", new Customer.Address("Main Road", "Anand", "388001"));
        Customer clone = c.clone();
        System.out.println(clone.getName() + " " + clone.getAddress().getCity());
        System.out.println("Separate customer object: " + (c != clone));
    }
}
