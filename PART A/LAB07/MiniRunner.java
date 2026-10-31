import java.lang.annotation.*;
import java.lang.reflect.*;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {}
class Checks {
    @Run public void first() { System.out.println("First ran"); }
    @Run public void second() { System.out.println("Second ran"); }
    public void helper() { System.out.println("Helper"); }
}
public class MiniRunner {
    public static void main(String[] args) throws Exception {
        Checks checks = new Checks();
        int count = 0;
        for (Method m : Checks.class.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Run.class) && m.getParameterCount() == 0) {
                m.setAccessible(true);
                try { m.invoke(checks); }
                catch (InvocationTargetException e) {
                    System.out.println("Failed: " + e.getCause().getMessage());
                }
                count++;
            }
        }
        System.out.println("Methods ran: " + count);
    }
}
