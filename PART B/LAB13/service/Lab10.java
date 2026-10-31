package service;
import model.*;
import exception.*;
public class Lab10 {
    public static void main(String[] args) throws Exception {
        Account a = new SavingsAccount("Tej",10000,0);
        Account b = new SavingsAccount("Riya",10000,0);
        TransactionProcessor processor = new TransactionProcessor();
        for (int i = 0; i < 10; i++) {
            processor.submit(() -> {
                try { a.deposit(100); a.withdraw(50); }
                catch (BankException e) { System.out.println(e.getMessage()); }
            });
        }
        processor.stop();
        TransactionBuffer buffer = new TransactionBuffer();
        Thread producer = new Thread(() -> {
            try {
                for (int i = 0; i < 5; i++) {
                    buffer.put(() -> {
                        try { a.deposit(10); }
                        catch (BankException e) { System.out.println(e.getMessage()); }
                    });
                }
            } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread consumer = new Thread(() -> {
            try { for (int i = 0; i < 5; i++) { buffer.take().run(); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        producer.start(); consumer.start(); producer.join(); consumer.join();
        Thread t1 = new Thread(() -> move(a,b));
        Thread t2 = new Thread(() -> move(b,a));
        t1.start(); t2.start(); t1.join(); t2.join();
        System.out.println(a); System.out.println(b);
    }
    static void move(Account from, Account to) {
        try { from.transfer(to,100); }
        catch (BankException e) { System.out.println(e.getMessage()); }
    }
}
