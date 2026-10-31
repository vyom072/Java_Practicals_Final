package service;
import model.*;
public class Lab12 {
    public static void main(String[] args) throws Exception {
        AccountStore store = new AccountStore();
        Account a = new SavingsAccount("Tej",5000,0);
        Account b = new SavingsAccount("Riya",2000,0);
        Account c = new SavingsAccount("Aman",9000,0);
        store.add(a); store.add(b); store.add(c);
        store.record("Opened " + a.getAccountNumber());
        System.out.println(store.get(b.getAccountNumber()));
        for (Account item : store.sortedByBalance()) { System.out.println(item); }
        System.out.println(store.getHistory());
        TransactionProcessor processor = new TransactionProcessor();
        processor.useAccounts(store.getAll());
        for (int i = 0; i < 10; i++) {
            processor.submit(new AccountWorker(processor.get(a.getAccountNumber()),100,1));
        }
        processor.stop(); System.out.println(a);
    }
}
