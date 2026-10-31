package service;
import model.*;
public class Lab09 {
    public static void main(String[] args) throws Exception {
        Account a = new SavingsAccount("Tej",0,0);
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(new AccountWorker(a,1000,1),"Worker-" + i);
            threads[i].start();
        }
        for (Thread t : threads) { t.join(); }
        System.out.println("Final balance: " + a.getBalance());
    }
}
