class Buffer {
    private int value;
    private boolean full;
    synchronized void put(int n) throws InterruptedException {
        while (full) { wait(); }
        value = n; full = true;
        System.out.println("Produced: " + n); notify();
    }
    synchronized int take() throws InterruptedException {
        while (!full) { wait(); }
        int n = value; full = false;
        System.out.println("Consumed: " + n); notify(); return n;
    }
}
public class BufferDemo {
    public static void main(String[] args) throws Exception {
        Buffer buffer = new Buffer();
        Thread producer = new Thread(() -> {
            try { for (int i = 1; i <= 5; i++) { buffer.put(i); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        Thread consumer = new Thread(() -> {
            try { for (int i = 1; i <= 5; i++) { buffer.take(); } }
            catch (InterruptedException e) { Thread.currentThread().interrupt(); }
        });
        producer.start(); consumer.start(); producer.join(); consumer.join();
    }
}
