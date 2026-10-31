public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16, MAX = 30;
    private static int activeCount = 0;
    Thermostat(String location, int startTemp) {
        this.location = location;
        temperature = 22;
        if (startTemp >= MIN && startTemp <= MAX) { temperature = startTemp; }
        activeCount++;
    }
    Thermostat(String location) { this(location, 22); }
    void raise() {
        if (temperature < MAX) { temperature++; }
        else { System.out.println("Already at maximum (30)"); }
    }
    void lower() {
        if (temperature > MIN) { temperature--; }
        else { System.out.println("Already at minimum (16)"); }
    }
    int getTemperature() { return temperature; }
    static int getActiveCount() { return activeCount; }
    public static void main(String[] args) {
        Thermostat a = new Thermostat("Room 1");
        Thermostat b = new Thermostat("Room 2", 24);
        for (int i = 0; i < 10; i++) {
            a.raise(); System.out.println(a.getTemperature());
        }
        for (int i = 0; i < 20; i++) {
            a.lower(); System.out.println(a.getTemperature());
        }
        System.out.println("Active: " + getActiveCount());
    }
}
