@FunctionalInterface
interface Notifier { void send(String message); }
interface Urgent {}
public class NotificationDemo {
    public static void main(String[] args) {
        Notifier email = message -> System.out.println("Email: " + message);
        Notifier sms = (Notifier & Urgent) message -> System.out.println("SMS: " + message);
        Notifier[] senders = {email, sms};
        for (Notifier sender : senders) {
            sender.send("Meeting at 10 AM");
            if (sender instanceof Urgent) { sender.send("Meeting at 10 AM"); }
        }
    }
}
