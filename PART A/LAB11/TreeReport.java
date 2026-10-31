import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.stream.Stream;
public class TreeReport {
    public static void main(String[] args) throws Exception {
        Path root = Path.of("sampletree"); Files.createDirectories(root.resolve("sub"));
        Files.writeString(root.resolve("sub/a.txt"), "Hello");
        Path report = Path.of("tree-report.txt");
        StringBuilder text = new StringBuilder();
        try (Stream<Path> paths = Files.walk(root)) {
            for (Path file : paths.filter(Files::isRegularFile).toList()) {
                BasicFileAttributes a = Files.readAttributes(file, BasicFileAttributes.class);
                text.append(root.relativize(file)).append(" ").append(a.size())
                    .append(" ").append(a.lastModifiedTime()).append("\n");
            }
        }
        Files.writeString(report, text); System.out.print(text);
    }
}
