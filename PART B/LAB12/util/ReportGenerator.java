package util;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.*;
public class ReportGenerator {
    public static String generate(Path folder, Path reportFile) throws IOException {
        Files.createDirectories(folder);
        long deposits = 0, withdrawals = 0;
        StringBuilder details = new StringBuilder();
        try (DirectoryStream<Path> files = Files.newDirectoryStream(folder,"*.log")) {
            for (Path file : files) {
                if (!Files.isRegularFile(file) || Files.size(file) == 0) { continue; }
                BasicFileAttributes attr = Files.readAttributes(file,BasicFileAttributes.class);
                details.append(file.getFileName()).append(" Size: ").append(attr.size())
                    .append(" Modified: ").append(attr.lastModifiedTime()).append("\n");
                try (BufferedReader reader = Files.newBufferedReader(file)) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] p = line.trim().split("\\s+");
                        if (p.length != 3) { continue; }
                        try {
                            long amount = Long.parseLong(p[2]);
                            if (amount <= 0) { continue; }
                            if (p[0].equals("DEPOSIT")) { deposits += amount; }
                            else if (p[0].equals("WITHDRAW")) { withdrawals += amount; }
                        } catch (NumberFormatException e) { continue; }
                    }
                }
            }
        }
        details.append("Deposits: ").append(deposits).append("\nWithdrawals: ")
            .append(withdrawals).append("\nNet change: ").append(deposits - withdrawals);
        Files.createDirectories(reportFile.toAbsolutePath().getParent());
        Files.writeString(reportFile,details); return details.toString();
    }
}
