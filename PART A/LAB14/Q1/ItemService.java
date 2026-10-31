import java.util.ArrayList;
public class ItemService {
    private ArrayList<Item> items = new ArrayList<>();
    public void add(String name) {
        if (name == null || name.isBlank()) { throw new IllegalArgumentException("Name required"); }
        items.add(new Item(name));
    }
    public int count() { return items.size(); }
    public ArrayList<Item> all() { return new ArrayList<>(items); }
}
