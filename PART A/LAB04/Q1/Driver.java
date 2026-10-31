import java.util.Arrays;
public class Driver {
    public static void main(String[] args) {
        String[] passwords = {"abc", "Abcd1234!", "abcd1234"};
        for (String pw : passwords) {
            System.out.println(pw);
            System.out.println("Length, uppercase, digit, special: "
                + Arrays.toString(PasswordChecker.rules(pw)));
            System.out.println(PasswordChecker.strength(pw));
        }
    }
}
