class ReportSession implements AutoCloseable {
    public void close() throws Exception {
        System.out.println("Session closed");
        throw new Exception("Close error");
    }
}
public class ResourceDemo {
    public static void main(String[] args) {
        try (ReportSession session = new ReportSession()) {
            System.out.println("Session opened");
            throw new Exception("Original processing error");
        } catch (Exception e) {
            System.out.println("Reported: " + e.getMessage());
            for (Throwable error : e.getSuppressed()) {
                System.out.println("Suppressed: " + error.getMessage());
            }
        }
    }
}
