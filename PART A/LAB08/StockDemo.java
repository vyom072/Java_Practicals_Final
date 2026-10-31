import java.util.HashMap;
class OutOfStockException extends Exception {
    private int shortfall;
    OutOfStockException(int shortfall) {
        super("Short by " + shortfall); this.shortfall = shortfall;
    }
    int getShortfall() { return shortfall; }
}
class InvalidQuantityException extends Exception {
    InvalidQuantityException() { super("Quantity must be positive"); }
}
class Warehouse {
    HashMap<String,Integer> stock = new HashMap<>();
    Warehouse() { stock.put("Pen", 10); stock.put("Book", 5); }
    void issue(String item, int qty)
        throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) { throw new InvalidQuantityException(); }
        int available = stock.getOrDefault(item, 0);
        if (qty > available) { throw new OutOfStockException(qty - available); }
        stock.put(item, available - qty);
        System.out.println("Issued " + qty + " " + item);
    }
}
public class StockDemo {
    public static void main(String[] args) {
        Warehouse w = new Warehouse();
        String[] items = {"Pen", "Book", "Pen", "Book"};
        int[] qty = {4, 8, 0, 2};
        for (int i = 0; i < items.length; i++) {
            try { w.issue(items[i], qty[i]); }
            catch (OutOfStockException e) { System.out.println(e.getMessage()); }
            catch (InvalidQuantityException e) { System.out.println(e.getMessage()); }
        }
    }
}
