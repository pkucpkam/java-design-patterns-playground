package creational.factory_method.after;

public class PushNotificationSender extends NotificationSender {
    @Override
    public Notification createNotification() {
        return new PushNotification();
    }
}
