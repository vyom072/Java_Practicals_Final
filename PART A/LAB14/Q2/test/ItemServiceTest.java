package test;
import service.ItemService;
import java.nio.file.*;
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
    @Test void saveAndLoad() throws Exception {
        Path file = Files.createTempFile("items", ".dat");
        try {
            ItemService first = new ItemService(); first.add("Pen"); first.save(file);
            ItemService second = new ItemService(); second.load(file);
            assertEquals(first.all(),second.all());
        } finally { Files.deleteIfExists(file); }
    }
}
