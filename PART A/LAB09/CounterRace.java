public class CounterRace {
    static class Counter {
        int value;
        void unsafe() { value++; }
        synchronized void safe() { value++; }
    }
    static void run(boolean safe) throws InterruptedException {
        Counter counter = new Counter();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(() -> {
                for (int j = 0; j < 100000; j++) {
                    if (safe) { counter.safe(); } else { counter.unsafe(); }
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) { t.join(); }
        System.out.println("Synchronized: " + safe + " Total: " + counter.value);
    }
    public static void main(String[] args) throws Exception { run(false); run(true); }
}
