package util;
import java.nio.file.*;
import java.io.IOException;
public class TransactionLog {
    public static synchronized void append(Path file, String line) throws IOException {
        Files.createDirectories(file.toAbsolutePath().getParent());
        Files.writeString(file,line + System.lineSeparator(),
            StandardOpenOption.CREATE,StandardOpenOption.APPEND);
    }
}
