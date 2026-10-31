import java.lang.annotation.*;
import java.lang.reflect.Field;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column { String name(); }
class Student {
    @Column(name="name") String name;
    @Column(name="age") int age;
    @Column(name="city") String city;
}
public class ColumnDemo {
    static void populate(Object obj, String[] headers, String[] row) throws Exception {
        for (Field f : obj.getClass().getDeclaredFields()) {
            Column column = f.getAnnotation(Column.class);
            if (column == null) { continue; }
            int index = -1;
            for (int i = 0; i < headers.length; i++) {
                if (headers[i].equals(column.name())) { index = i; break; }
            }
            if (index < 0 || index >= row.length) {
                System.out.println("Missing column: " + column.name()); continue;
            }
            f.setAccessible(true);
            if (f.getType() == int.class) { f.setInt(obj, Integer.parseInt(row[index])); }
            else { f.set(obj, row[index]); }
        }
    }
    public static void main(String[] args) throws Exception {
        Student s = new Student();
        populate(s, new String[]{"name", "age"}, new String[]{"Tej", "19"});
        System.out.println(s.name + " " + s.age + " " + s.city);
    }
}
