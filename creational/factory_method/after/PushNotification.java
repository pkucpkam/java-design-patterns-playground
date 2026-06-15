package creational.factory_method.after;

public class PushNotification implements Notification {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending Push Notification to " + recipient + ": " + message);
    }
}
