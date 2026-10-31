package service;
import model.*;
public class Lab13 {
    public static void main(String[] args) {
        Repository<Account> accounts = new Repository<>();
        Account a = new SavingsAccount("Tej",5000,0);
        Account b = new CurrentAccount("Riya",2000,1000);
        Account c = new FixedDepositAccount("Aman",9000);
        accounts.saveAll(a,b,c);
        System.out.println(accounts.findById(a.getAccountNumber()));
        for (Account item : accounts.findAll()) { System.out.println(item); }
        Repository<Customer> customers = new Repository<>();
        Customer customer = new Customer("Tej","tej@test.com","9876543210");
        customers.save(customer);
        System.out.println(customers.findById(customer.getCustomerId()).getName());
        System.out.println(Repository.average(new Long[]{5000L,2000L,9000L}));
    }
}
