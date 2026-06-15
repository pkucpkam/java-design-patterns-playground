package creational.factory_method.after;

public class SmsNotification implements Notification {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}
