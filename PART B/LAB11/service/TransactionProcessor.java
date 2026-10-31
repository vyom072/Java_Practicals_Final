package service;
import java.util.concurrent.*;
public class TransactionProcessor {
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
