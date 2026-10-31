import java.util.Scanner;
record Vehicle(String number, String type) {}
public class TollBooth {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int total = 0, bikes = 0, cars = 0, trucks = 0;
        while (true) {
            System.out.print("Number or done: ");
            String number = sc.next();
            if (number.equalsIgnoreCase("done")) { break; }
            System.out.print("Type: ");
            Vehicle v = new Vehicle(number, sc.next().toLowerCase());
            int toll = switch (v.type()) {
                case "bike" -> 20;
                case "car" -> 50;
                case "truck" -> 150;
                default -> 0;
            };
            switch (v.type()) {
                case "bike" -> bikes++;
                case "car" -> cars++;
                case "truck" -> trucks++;
                default -> System.out.println("Invalid type");
            }
            total += toll;
        }
        System.out.println("Total toll: " + total);
        if (bikes + cars + trucks == 0) {
            System.out.println("Most frequent: none");
        } else {
            int max = Math.max(bikes, Math.max(cars, trucks));
            if (bikes == max) { System.out.println("Most frequent: bike"); }
            if (cars == max) { System.out.println("Most frequent: car"); }
            if (trucks == max) { System.out.println("Most frequent: truck"); }
        }
    }
}
