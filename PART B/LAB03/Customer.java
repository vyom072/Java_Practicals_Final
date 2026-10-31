public class Customer implements Cloneable {
    public static class Address {
        private final String line, city, pincode;
        public Address(String line, String city, String pincode) {
            this.line = line; this.city = city; this.pincode = pincode;
        }
        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }
    }
    private Address address;
    public Customer(String name, String email, String mobile, Address address) {
        this(name,email,mobile); this.address = address;
    }
    public Address getAddress() { return address; }
    public Customer clone() {
        try { return (Customer) super.clone(); }
        catch (CloneNotSupportedException e) { throw new AssertionError(e); }
    }
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
