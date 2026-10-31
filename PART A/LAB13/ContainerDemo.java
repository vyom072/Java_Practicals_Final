class Box<T> {
    private T value;
    void set(T value) { this.value = value; }
    T get() { return value; }
}
class Pair<A,B> {
    private A first; private B second;
    Pair(A first, B second) { this.first = first; this.second = second; }
    A getFirst() { return first; }
    B getSecond() { return second; }
}
public class ContainerDemo {
    static <T> T first(T[] values) {
        if (values.length == 0) { throw new IllegalArgumentException("Empty array"); }
        return values[0];
    }
    public static void main(String[] args) {
        Box<String> names = new Box<>(); names.set("Tej");
        Box<Integer> marks = new Box<>(); marks.set(90);
        Pair<String,Integer> pair = new Pair<>(names.get(), marks.get());
        System.out.println(pair.getFirst() + " " + pair.getSecond());
        System.out.println(first(new String[]{"Java","Python"}));
        System.out.println(first(new Integer[]{10,20}));
    }
}
