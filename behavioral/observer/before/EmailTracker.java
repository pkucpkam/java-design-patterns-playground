package behavioral.observer.before;

public class EmailTracker {
    public void sendEmail(String email, String message) {
        System.out.println("Sending email to " + email + ": " + message);
        // Simulate email sending logic
    }
}
