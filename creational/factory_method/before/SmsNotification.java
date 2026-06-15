package creational.factory_method.before;

public class SmsNotification {
    public void sendSms(String message, String recipient) {
        System.out.println("Sending SMS to " + recipient + ": " + message);
    }
}
