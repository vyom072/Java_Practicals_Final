import java.util.Objects;
public class Fraction {
    private int num, den;
    Fraction(int num, int den) {
        if (den == 0) { throw new IllegalArgumentException("Zero denominator"); }
        if (den < 0) { num = -num; den = -den; }
        int g = gcd(Math.abs(num), den);
        this.num = num / g; this.den = den / g;
    }
    private int gcd(int a, int b) {
        while (b != 0) { int t = a % b; a = b; b = t; }
        return a;
    }
    public String toString() { return num + "/" + den; }
    public boolean equals(Object o) {
        if (!(o instanceof Fraction f)) { return false; }
        return num == f.num && den == f.den;
    }
    public int hashCode() { return Objects.hash(num, den); }
}
