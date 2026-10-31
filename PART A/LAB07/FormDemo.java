import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {}
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength { int value(); }
class SignupForm {
    @NotBlank @MaxLength(10) String name;
    @NotBlank String email;
    SignupForm(String name, String email) { this.name = name; this.email = email; }
}
public class FormDemo {
    static ArrayList<String> check(Object obj) throws IllegalAccessException {
        ArrayList<String> errors = new ArrayList<>();
        for (Field f : obj.getClass().getDeclaredFields()) {
            f.setAccessible(true);
            Object value = f.get(obj);
            if (f.isAnnotationPresent(NotBlank.class)
                && (value == null || value.toString().isBlank())) {
                errors.add(f.getName() + " must not be blank");
            }
            MaxLength rule = f.getAnnotation(MaxLength.class);
            if (rule != null && value != null && value.toString().length() > rule.value()) {
                errors.add(f.getName() + " is too long");
            }
        }
        return errors;
    }
    public static void main(String[] args) throws Exception {
        System.out.println(check(new SignupForm("VeryLongUserName", "")));
        System.out.println(check(new SignupForm("Tej", "tej@test.com")));
    }
}
