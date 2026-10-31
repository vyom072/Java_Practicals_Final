import java.util.*;
public class Leaderboard {
    public static void main(String[] args) {
        Map<String,Integer> scores = new HashMap<>();
        scores.put("Tej",90); scores.put("Riya",95);
        scores.put("Aman",90); scores.put("Neha",80);
        ArrayList<Map.Entry<String,Integer>> list = new ArrayList<>(scores.entrySet());
        list.sort(Comparator.<Map.Entry<String,Integer>>comparingInt(e -> e.getValue())
            .reversed().thenComparing(e -> e.getKey()));
        int n = 3;
        for (int i = 0; i < Math.min(n,list.size()); i++) {
            System.out.println((i + 1) + ". " + list.get(i).getKey()
                + " " + list.get(i).getValue());
        }
    }
}
