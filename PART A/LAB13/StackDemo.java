import java.util.EmptyStackException;
class Stack<T> {
    private Object[] data;
    private int size;
    Stack(int capacity) { data = new Object[capacity]; }
    void push(T item) {
        if (size == data.length) { throw new IllegalStateException("Stack full"); }
        data[size++] = item;
    }
    @SuppressWarnings("unchecked")
    T pop() {
        if (size == 0) { throw new EmptyStackException(); }
        T item = (T) data[--size]; data[size] = null; return item;
    }
}
public class StackDemo {
    public static void main(String[] args) {
        Stack<Integer> numbers = new Stack<>(3); numbers.push(10); numbers.push(20);
        Stack<String> names = new Stack<>(2); names.push("Tej");
        System.out.println(numbers.pop()); System.out.println(names.pop());
        try { names.pop(); }
        catch (EmptyStackException e) { System.out.println("Stack empty"); }
    }
}
