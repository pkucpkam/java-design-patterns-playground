package behavioral.observer.before;

public class SmsTracker {
    public void sendSms(String phone, String message) {
        System.out.println("Sending SMS to " + phone + ": " + message);
        // Simulate SMS sending logic
    }
}
