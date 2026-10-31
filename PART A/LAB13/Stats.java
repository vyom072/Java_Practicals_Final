public class Stats {
    static <N extends Number> double average(N[] values) {
        if (values.length == 0) { throw new IllegalArgumentException("Empty array"); }
        double sum = 0;
        for (N n : values) { sum += n.doubleValue(); }
        return sum / values.length;
    }
    public static void main(String[] args) {
        System.out.println(average(new Integer[]{10,20,30}));
        System.out.println(average(new Double[]{1.5,2.5}));
        System.out.println(average(new Long[]{100L,200L}));
    }
}
