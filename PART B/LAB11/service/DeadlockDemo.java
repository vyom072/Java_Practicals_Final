package service;
import model.*;
import java.util.concurrent.CountDownLatch;
public class DeadlockDemo {
    public static void main(String[] args) {
        Account a = new SavingsAccount("Tej",1000,0);
        Account b = new SavingsAccount("Riya",1000,0);
        CountDownLatch held = new CountDownLatch(2);
        new Thread(() -> badTransfer(a,b,held)).start();
        new Thread(() -> badTransfer(b,a,held)).start();
    }
    static void badTransfer(Account from, Account to, CountDownLatch held) {
        synchronized (from) {
            held.countDown();
            try { held.await(); }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); return; }
            synchronized (to) {
                try { from.withdraw(100); to.deposit(100); }
                catch (Exception e) { System.out.println(e.getMessage()); }
            }
        }
    }
}
