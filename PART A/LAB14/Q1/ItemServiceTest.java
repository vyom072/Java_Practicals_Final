import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ItemServiceTest {
    @Test void addIncreasesCount() {
        ItemService service = new ItemService();
        service.add("Book"); assertEquals(1,service.count());
    }
    @Test void blankNameThrows() {
        assertThrows(IllegalArgumentException.class, () -> new ItemService().add(" "));
    }
    @Test void listContainsAddedItem() {
        ItemService service = new ItemService(); service.add("Pen");
        assertEquals("Pen",service.all().get(0).name());
    }
}
