public class ParkingLot {
    private int twoWheelers, fourWheelers;
    private final int twoCap, fourCap;
    private static long revenue = 0;
    ParkingLot(int twoCap, int fourCap) {
        this.twoCap = twoCap; this.fourCap = fourCap;
    }
    void park(String type) {
        if (type.equals("two") && twoWheelers < twoCap) {
            twoWheelers++; revenue += 20; System.out.println("Two parked");
        } else if (type.equals("four") && fourWheelers < fourCap) {
            fourWheelers++; revenue += 40; System.out.println("Four parked");
        } else { System.out.println("Full or invalid type"); }
    }
    void leave(String type) {
        if (type.equals("two") && twoWheelers > 0) { twoWheelers--; }
        else if (type.equals("four") && fourWheelers > 0) { fourWheelers--; }
        System.out.println("Occupancy: " + twoWheelers + ", " + fourWheelers);
    }
    public static void main(String[] args) {
        ParkingLot p = new ParkingLot(1, 1);
        p.park("two"); p.park("two"); p.park("four");
        p.leave("two"); p.park("two");
        System.out.println("Final: " + p.twoWheelers + ", " + p.fourWheelers);
        System.out.println("Revenue: " + revenue);
    }
}
