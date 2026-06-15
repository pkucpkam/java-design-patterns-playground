package creational.factory_method.before;

public class EmailNotification {
    public void sendEmail(String message, String recipient) {
        System.out.println("Sending Email to " + recipient + ": " + message);
    }
}
