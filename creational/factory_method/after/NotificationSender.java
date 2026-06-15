package creational.factory_method.after;

public abstract class NotificationSender {
    // The Factory Method
    public abstract Notification createNotification();

    // Business method that utilizes the factory method
    public void sendNotification(String message, String recipient) {
        Notification notification = createNotification();
        notification.send(message, recipient);
    }
}
