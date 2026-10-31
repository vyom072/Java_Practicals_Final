package service;
import java.util.Scanner;
record BankInfo(String name, String branch) {}
enum MenuOption { OPEN_ACCOUNT, DEPOSIT, WITHDRAW, TRANSFER, EXIT }
public class MiniBank {
    public static void main(String[] args) {
        System.out.println(new BankInfo("MiniBank", "Anand"));
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1. Open account\n2. Deposit\n3. Withdraw\n4. Transfer\n5. Exit");
            int choice;
            try { choice = Integer.parseInt(sc.nextLine()); }
            catch (NumberFormatException e) { System.out.println("Invalid choice"); continue; }
            if (choice < 1 || choice > 5) { System.out.println("Invalid choice"); continue; }
            MenuOption option = MenuOption.values()[choice - 1];
            String message = switch (option) {
                case OPEN_ACCOUNT -> "Open account - to be implemented later";
                case DEPOSIT -> "Deposit - to be implemented later";
                case WITHDRAW -> "Withdraw - to be implemented later";
                case TRANSFER -> "Transfer - to be implemented later";
                case EXIT -> "Goodbye";
            };
            System.out.println(message);
            if (option == MenuOption.EXIT) { break; }
        }
    }
}
