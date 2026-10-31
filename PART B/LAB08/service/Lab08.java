package service;
import model.*;
import exception.*;
class BankSession implements AutoCloseable {
    public void close() { System.out.println("Session closed"); }
}
public class Lab08 {
    public static void main(String[] args) {
        Account a = new SavingsAccount("Tej",1000,0);
        Account b = new SavingsAccount("Riya",500,0);
        try (BankSession session = new BankSession()) {
            try { a.withdraw(5000); }
            catch (InsufficientFundsException e) { System.out.println(e.getMessage()); }
            try { a.deposit(0); }
            catch (InvalidAmountException e) { System.out.println(e.getMessage()); }
            try { a.transfer(null,100); }
            catch (BankException e) { System.out.println(e.getMessage()); }
            a.transfer(b,200);
            System.out.println(a); System.out.println(b);
        } catch (BankException e) { System.out.println(e.getMessage()); }
        finally { System.out.println("Bank operations completed"); }
    }
}
