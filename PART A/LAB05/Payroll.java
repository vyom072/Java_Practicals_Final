abstract class Employee {
    String name, id;
    Employee(String name, String id) { this.name = name; this.id = id; }
    abstract double monthlySalary();
}
class FullTime extends Employee {
    double salary;
    FullTime(String n, String id, double salary) { super(n,id); this.salary = salary; }
    double monthlySalary() { return salary; }
}
class PartTime extends Employee {
    int hours; double rate;
    PartTime(String n, String id, int hours, double rate) {
        super(n,id); this.hours = hours; this.rate = rate;
    }
    double monthlySalary() { return hours * rate; }
}
class Intern extends Employee {
    double stipend;
    Intern(String n, String id, double stipend) { super(n,id); this.stipend = stipend; }
    double monthlySalary() { return stipend; }
}
public class Payroll {
    public static void main(String[] args) {
        Employee[] staff = {new FullTime("Tej","E1",30000),
            new PartTime("Riya","E2",40,200), new Intern("Aman","E3",5000)};
        double total = 0;
        for (Employee e : staff) {
            System.out.println(e.id + " " + e.name + " " + e.monthlySalary());
            total += e.monthlySalary();
            if (e instanceof Intern) { System.out.println("Intern stipend"); }
        }
        System.out.println("Total: " + total);
    }
}
