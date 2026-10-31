import java.util.Objects;
public class Point {
    private int x, y;
    Point(int x, int y) { this.x = x; this.y = y; }
    public String toString() { return "(" + x + ", " + y + ")"; }
    public boolean equals(Object o) {
        if (!(o instanceof Point p)) { return false; }
        return x == p.x && y == p.y;
    }
    public int hashCode() { return Objects.hash(x, y); }
}
