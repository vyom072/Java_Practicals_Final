import java.util.*;
public class WordFrequency {
    public static void main(String[] args) {
        String paragraph = "Java is simple. Java is useful. Java supports objects.";
        HashMap<String,Integer> counts = new HashMap<>();
        for (String word : paragraph.toLowerCase().split("[^a-z]+")) {
            if (!word.isEmpty()) { counts.put(word, counts.getOrDefault(word,0) + 1); }
        }
        ArrayList<Map.Entry<String,Integer>> list = new ArrayList<>(counts.entrySet());
        list.sort(Comparator.<Map.Entry<String,Integer>>comparingInt(e -> e.getValue())
            .reversed().thenComparing(e -> e.getKey()));
        for (Map.Entry<String,Integer> e : list) {
            System.out.println(e.getKey() + ": " + e.getValue());
        }
    }
}
