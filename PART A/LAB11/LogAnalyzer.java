import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.BufferedReader;
public class LogAnalyzer {
    public static void main(String[] args) throws Exception {
        Path folder = Path.of("samplelogs"); Files.createDirectories(folder);
        Files.writeString(folder.resolve("one.log"), "login OK\nlogin ERROR\n");
        Files.writeString(folder.resolve("two.log"), "payment ERROR\n");
        String keyword = "ERROR";
        int lines = 0, matches = 0;
        try (DirectoryStream<Path> files = Files.newDirectoryStream(folder, "*.log")) {
            for (Path file : files) {
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                System.out.println(file + " Size: " + attr.size());
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        lines++;
                        if (line.contains(keyword)) { matches++; }
                    }
                }
            }
        }
        System.out.println("Lines: " + lines + " Matches: " + matches);
    }
}
