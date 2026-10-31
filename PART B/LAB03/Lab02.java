public class Lab02 {
    public static void main(String[] args) {
        Customer customer = new Customer("Tej", "tej@test.com", "9876543210");
        System.out.println(customer.getCustomerId() + " " + customer.getName());
        Account[] accounts = {new Account("Riya",5000),new Account("Tej"),new Account("Aman",2000)};
        accounts[0].deposit(2000); System.out.println(accounts[0].getBalance());
        System.out.println(accounts[0].withdraw(3000));
        System.out.println(accounts[0].getBalance());
        System.out.println(accounts[0].withdraw(10000));
        for (Account a : accounts) {
            System.out.println(a.getAccountNumber() + " " + a.getBalance());
        }
    }
}
