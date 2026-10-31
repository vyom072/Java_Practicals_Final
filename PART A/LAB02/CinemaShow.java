public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;
    CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        seatsAvailable = capacity;
    }
    CinemaShow(String title) { this(title, 100); }
    boolean book(int n) {
        if (n > 0 && n <= seatsAvailable) {
            seatsAvailable -= n; totalBooked += n; return true;
        }
        return false;
    }
    void cancel(int n) {
        if (n > 0) { seatsAvailable = Math.min(capacity, seatsAvailable + n); }
    }
    int getSeatsAvailable() { return seatsAvailable; }
    static int getTotalBooked() { return totalBooked; }
    public static void main(String[] args) {
        CinemaShow s = new CinemaShow("Movie", 10);
        System.out.println(s.book(6) + " Seats: " + s.getSeatsAvailable());
        System.out.println(s.book(8) + " Seats: " + s.getSeatsAvailable());
        s.cancel(2);
        System.out.println("After cancel: " + s.getSeatsAvailable());
        System.out.println(s.book(3) + " Seats: " + s.getSeatsAvailable());
        System.out.println("Total booked: " + getTotalBooked());
    }
}
