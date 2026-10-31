import java.util.Scanner;
public class ItemApp {
    public static void main(String[] args) {
        ItemService service = new ItemService();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Add\n2. List\n3. Exit");
            String choice = sc.nextLine();
            if (choice.equals("3")) { break; }
            try {
                if (choice.equals("1")) {
                    System.out.print("Name: "); service.add(sc.nextLine());
                } else if (choice.equals("2")) { System.out.println(service.all()); }
                else { System.out.println("Invalid choice"); }
            } catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
        }
    }
}
