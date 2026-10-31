public class SeatRace {
    static class Booking {
        int seatsLeft = 5;
        boolean unsafe() {
            if (seatsLeft > 0) {
                try { Thread.sleep(10); }
                catch (InterruptedException e) { Thread.currentThread().interrupt(); return false; }
                seatsLeft--; return true;
            }
            return false;
        }
        synchronized boolean book() {
            if (seatsLeft > 0) { seatsLeft--; return true; }
            return false;
        }
    }
    static void run(boolean safe) throws Exception {
        Booking booking = new Booking();
        Thread[] threads = new Thread[10];
        boolean[] success = new boolean[10];
        for (int i = 0; i < 10; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                if (safe) { success[id] = booking.book(); }
                else { success[id] = booking.unsafe(); }
            });
            threads[i].start();
        }
        for (Thread t : threads) { t.join(); }
        int count = 0;
        for (boolean booked : success) { if (booked) { count++; } }
        System.out.println("Synchronized: " + safe + " Booked: " + count);
    }
    public static void main(String[] args) throws Exception { run(false); run(true); }
}
