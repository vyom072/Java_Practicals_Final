public class ChatFilter {
    static String filter(String[] lines, String keyword) {
        int count = 0;
        StringBuilder report = new StringBuilder();
        for (String line : lines) {
            String[] p = line.split(" ", 3);
            if (p.length != 3) { continue; }
            if (p[2].toLowerCase().contains(keyword.toLowerCase())) {
                count++;
                report.append(p[0]).append(" ").append(p[1]).append(": ")
                    .append(p[2]).append("\n");
            }
        }
        return "Matches: " + count + "\n" + report;
    }
}
