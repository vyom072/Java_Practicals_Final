package util;
import model.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
public class AnnotationValidator {
    public static String[] validate(Object obj) {
        ArrayList<String> errors = new ArrayList<>();
        for (Class<?> type = obj.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (!field.isAnnotationPresent(Positive.class)
                    && !field.isAnnotationPresent(MaxLength.class)) { continue; }
                try {
                    field.setAccessible(true);
                    Object value = field.get(obj);
                    Positive p = field.getAnnotation(Positive.class);
                    if (p != null && value instanceof Number n && n.doubleValue() <= 0) {
                        errors.add(field.getName() + ": " + p.message());
                    }
                    MaxLength m = field.getAnnotation(MaxLength.class);
                    if (m != null && value instanceof String s && s.length() > m.value()) {
                        errors.add(field.getName() + ": maximum length " + m.value());
                    }
                } catch (IllegalAccessException e) { throw new IllegalStateException(e); }
            }
        }
        return errors.toArray(new String[0]);
    }
}
