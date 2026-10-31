public class Lab03 {
    public static void main(String[] args) {
        Account first = new Account("Riya",4000);
        Account copy = new Account(first);
        System.out.println(first); System.out.println(copy);
        System.out.println(first.equals(copy));
        Object obj = first; System.out.println(obj instanceof Account);
        Customer c = new Customer("Tej","tej@test.com","9876543210",
            new Customer.Address("Main Road","Anand","388001"));
        Customer clone = c.clone();
        System.out.println(clone.getName() + " " + clone.getAddress().getCity());
        System.out.println("Separate customer object: " + (c != clone));
    }
}
