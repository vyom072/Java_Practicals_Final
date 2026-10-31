import java.io.*;
class Student implements Serializable {
    private static final long serialVersionUID = 1L;
    String name;
    int marks;
    transient String password;
    Student(String name, int marks, String password) {
        this.name = name; this.marks = marks; this.password = password;
    }
}
public class SaveDemo {
    public static void main(String[] args) throws Exception {
        Student[] students = {new Student("Tej",90,"abc"), new Student("Riya",85,"xyz")};
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("students.dat"))) {
            out.writeObject(students);
        }
        Student[] loaded;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("students.dat"))) {
            loaded = (Student[]) in.readObject();
        }
        for (Student s : loaded) {
            System.out.println(s.name + " " + s.marks + " Password: " + s.password);
        }
        System.out.println("Fresh object: " + (students[0] != loaded[0]));
    }
}
