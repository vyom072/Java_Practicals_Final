abstract class Media {
    String title; int daysLate;
    Media(String title, int daysLate) { this.title = title; this.daysLate = daysLate; }
    abstract double lateFee();
}
class Book extends Media {
    Book(String title, int days) { super(title, days); }
    double lateFee() { return Math.max(0, daysLate) * 2; }
}
class DVD extends Media {
    DVD(String title, int days) { super(title, days); }
    double lateFee() { return Math.max(0, daysLate) * 5; }
}
class Magazine extends Media {
    Magazine(String title, int days) { super(title, days); }
    double lateFee() { return Math.max(0, daysLate - 1); }
}
public class MediaDemo {
    public static void main(String[] args) {
        Media[] items = {new Book("Java",3), new DVD("Movie",2),
                         new Magazine("Science",4)};
        double total = 0;
        for (Media m : items) {
            System.out.println(m.title + ": " + m.lateFee()); total += m.lateFee();
        }
        System.out.println("Total fees: " + total);
    }
}
