package app;
import java.util.Scanner;
import rules.Discount;
public class DiscountApp {
    public static void main(String[] args) {
        double[] prices = {100, 200, 500};
        Discount[] rules = {p -> p, p -> p * 0.9, p -> p * 0.8};
        System.out.println("1. No discount\n2. 10% discount\n3. 20% discount");
        int choice = new Scanner(System.in).nextInt();
        if (choice < 1 || choice > 3) { System.out.println("Invalid choice"); return; }
        Discount selected = rules[choice - 1];
        for (double p : prices) { System.out.println(p + " -> " + selected.apply(p)); }
    }
}
