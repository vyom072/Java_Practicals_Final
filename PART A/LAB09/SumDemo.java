import java.util.concurrent.atomic.AtomicLong;
public class SumDemo {
    static long total;
    static synchronized void add(long n) { total += n; }
    static void run(int mode, int[] values) throws Exception {
        total = 0;
        AtomicLong atomic = new AtomicLong();
        Thread[] threads = new Thread[4];
        long start = System.nanoTime();
        for (int i = 0; i < 4; i++) {
            int from = i * values.length / 4;
            int to = (i + 1) * values.length / 4;
            threads[i] = new Thread(() -> {
                for (int j = from; j < to; j++) {
                    if (mode == 0) { total += values[j]; }
                    else if (mode == 1) { add(values[j]); }
                    else { atomic.addAndGet(values[j]); }
                }
            });
            threads[i].start();
        }
        for (Thread t : threads) { t.join(); }
        long result = total;
        if (mode == 2) { result = atomic.get(); }
        System.out.println("Mode " + mode + " Sum " + result
            + " Time ns " + (System.nanoTime() - start));
    }
    public static void main(String[] args) throws Exception {
        int[] values = new int[1000000];
        java.util.Arrays.fill(values, 1);
        run(0, values); run(1, values); run(2, values);
    }
}
