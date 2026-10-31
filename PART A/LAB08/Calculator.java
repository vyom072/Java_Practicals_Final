import java.util.Scanner;
class DivideByZeroException extends Exception {
    DivideByZeroException() { super("Cannot divide by zero"); }
}
public class Calculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean done = false;
        while (!done) {
            try {
                System.out.print("First number: ");
                double a = Double.parseDouble(sc.nextLine());
                System.out.print("Second number: ");
                double b = Double.parseDouble(sc.nextLine());
                System.out.print("Operator: ");
                String op = sc.nextLine();
                double result;
                switch (op) {
                    case "+" -> result = a + b;
                    case "-" -> result = a - b;
                    case "*" -> result = a * b;
                    case "/" -> {
                        if (b == 0) { throw new DivideByZeroException(); }
                        result = a / b;
                    }
                    default -> throw new IllegalArgumentException("Invalid operator");
                }
                System.out.println("Result: " + result); done = true;
            } catch (NumberFormatException e) { System.out.println("Invalid number"); }
            catch (DivideByZeroException e) { System.out.println(e.getMessage()); }
            catch (IllegalArgumentException e) { System.out.println(e.getMessage()); }
            finally { System.out.println("Attempt logged"); }
        }
    }
}
