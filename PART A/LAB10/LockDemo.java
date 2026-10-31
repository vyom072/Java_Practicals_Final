import java.util.concurrent.CountDownLatch;
public class LockDemo {
    static void pause() {
        try { Thread.sleep(50); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
    static void lock(Object a, Object b) {
        synchronized (a) { pause(); synchronized (b) { System.out.println("Completed"); } }
    }
    public static void main(String[] args) throws Exception {
        Object a = new Object(), b = new Object();
        if (args.length > 0 && args[0].equals("deadlock")) {
            CountDownLatch held = new CountDownLatch(2);
            Runnable first = () -> {
                synchronized (a) {
                    held.countDown();
                    try { held.await(); } catch (InterruptedException e) { return; }
                    synchronized (b) { System.out.println("A then B"); }
                }
            };
            Runnable second = () -> {
                synchronized (b) {
                    held.countDown();
                    try { held.await(); } catch (InterruptedException e) { return; }
                    synchronized (a) { System.out.println("B then A"); }
                }
            };
            new Thread(first).start(); new Thread(second).start();
        } else {
            Thread t1 = new Thread(() -> lock(a,b));
            Thread t2 = new Thread(() -> lock(a,b));
            t1.start(); t2.start(); t1.join(); t2.join();
        }
    }
}
