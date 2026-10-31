package service;
import model.annotation.Id;
import java.lang.reflect.Field;
import java.util.*;
public class Repository<T> {
    private HashMap<String,T> data = new HashMap<>();
    private String idOf(T entity) {
        for (Class<?> type = entity.getClass(); type != null; type = type.getSuperclass()) {
            for (Field field : type.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(entity);
                        if (value == null) { throw new IllegalArgumentException("Null id"); }
                        return value.toString();
                    } catch (IllegalAccessException e) { throw new IllegalStateException(e); }
                }
            }
        }
        throw new IllegalArgumentException("No @Id field");
    }
    public synchronized T save(T entity) { data.put(idOf(entity),entity); return entity; }
    public synchronized T findById(String id) { return data.get(id); }
    public synchronized ArrayList<T> findAll() { return new ArrayList<>(data.values()); }
    public synchronized boolean delete(String id) { return data.remove(id) != null; }
    @SafeVarargs
    public final synchronized void saveAll(T... items) {
        for (T item : items) { save(item); }
    }
    public static <N extends Number> double average(N[] values) {
        if (values.length == 0) { throw new IllegalArgumentException("Empty array"); }
        double total = 0;
        for (N n : values) { total += n.doubleValue(); }
        return total / values.length;
    }
}
