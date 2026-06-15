package creational.factory_method.after;

public class EmailNotification implements Notification {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending Email to " + recipient + ": " + message);
    }
}
