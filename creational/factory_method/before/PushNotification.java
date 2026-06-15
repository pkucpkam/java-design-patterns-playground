package creational.factory_method.before;

public class PushNotification {
    public void sendPush(String message, String recipient) {
        System.out.println("Sending Push Notification to " + recipient + ": " + message);
    }
}
