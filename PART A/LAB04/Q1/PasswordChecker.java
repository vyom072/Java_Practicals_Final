public class PasswordChecker {
    static boolean[] rules(String pw) {
        return new boolean[]{pw.length() >= 8, pw.matches(".*[A-Z].*"),
            pw.matches(".*[0-9].*"), pw.matches(".*[^a-zA-Z0-9].*")};
    }
    static String strength(String pw) {
        int count = 0;
        for (boolean pass : rules(pw)) { if (pass) { count++; } }
        if (count <= 1) { return "Weak"; }
        if (count <= 3) { return "Medium"; }
        return "Strong";
    }
}
