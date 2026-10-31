package service;
import java.util.concurrent.*;
import model.Account;
import java.util.Collection;
public class TransactionProcessor {
    private ConcurrentHashMap<String,Account> accounts = new ConcurrentHashMap<>();
    public void add(Account a) { accounts.put(a.getAccountNumber(),a); }
    public Account get(String number) { return accounts.get(number); }
    public void useAccounts(Collection<Account> source) {
        accounts.clear();
        for (Account a : source) { add(a); }
    }
    private ExecutorService pool = Executors.newFixedThreadPool(4);
    public void submit(Runnable task) { pool.execute(task); }
    public void stop() throws InterruptedException {
        pool.shutdown();
        if (!pool.awaitTermination(1,TimeUnit.MINUTES)) {
            pool.shutdownNow();
            if (!pool.awaitTermination(1,TimeUnit.MINUTES)) {
                throw new IllegalStateException("Tasks did not stop");
            }
        }
    }
}
