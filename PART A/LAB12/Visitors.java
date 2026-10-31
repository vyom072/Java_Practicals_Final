import java.util.*;
public class Visitors {
    public static void main(String[] args) {
        List<String> names = Arrays.asList("Tej","Riya","Tej","Aman","Riya","Neha");
        HashSet<String> unique = new HashSet<>(names);
        LinkedHashSet<String> ordered = new LinkedHashSet<>(names);
        System.out.println("Unique users: " + unique.size());
        int count = 0;
        for (String name : ordered) {
            if (count == 3) { break; }
            System.out.println(name); count++;
        }
    }
}
