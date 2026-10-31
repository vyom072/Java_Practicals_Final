interface Switchable {
    void on();
    void off();
    default void toggle() { on(); off(); }
}
class Fan implements Switchable {
    public void on() { System.out.println("Fan ON"); }
    public void off() { System.out.println("Fan OFF"); }
}
class Light implements Switchable {
    public void on() { System.out.println("Light ON"); }
    public void off() { System.out.println("Light OFF"); }
}
@FunctionalInterface
interface Permission { boolean allow(Switchable device, int hour); }
public class RemoteDemo {
    public static void main(String[] args) {
        Switchable[] devices = {new Fan(), new Light()};
        for (Switchable d : devices) { d.toggle(); }
        Permission p1 = new Permission() {
            public boolean allow(Switchable d, int hour) { return hour < 22; }
        };
        Permission p2 = (d, hour) -> hour < 22;
        System.out.println(p1.allow(devices[0], 10));
        System.out.println(p2.allow(devices[0], 10));
    }
}
