import java.util.Scanner;
public class Driver {
    public static void main(String[] args) {
        String[] lines = {"10:05 alice Hello there", "10:06 bob Good morning", "bad"};
        System.out.print("Keyword: ");
        String keyword = new Scanner(System.in).nextLine();
        System.out.print(ChatFilter.filter(lines, keyword));
    }
}
