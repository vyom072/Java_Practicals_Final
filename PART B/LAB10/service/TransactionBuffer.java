package service;
public class TransactionBuffer {
    private Runnable[] buffer = new Runnable[3];
    private int head, tail, size;
    public synchronized void put(Runnable task) throws InterruptedException {
        while (size == buffer.length) { wait(); }
        buffer[tail] = task; tail = (tail + 1) % buffer.length; size++; notify();
    }
    public synchronized Runnable take() throws InterruptedException {
        while (size == 0) { wait(); }
        Runnable task = buffer[head]; buffer[head] = null;
        head = (head + 1) % buffer.length; size--; notify(); return task;
    }
}
