import java.util.concurrent.*;
public class PoolDemo {
    public static void main(String[] args) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);
        for (int i = 1; i <= 10; i++) {
            final int id = i;
            pool.execute(() -> {
                System.out.println("Task " + id + " " + Thread.currentThread().getName());
                try { Thread.sleep(100); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); }
            });
        }
        pool.shutdown();
        if (!pool.awaitTermination(1, TimeUnit.MINUTES)) { pool.shutdownNow(); }
    }
}
