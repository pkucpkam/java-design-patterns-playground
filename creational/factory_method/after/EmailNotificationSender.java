package creational.factory_method.after;

public class EmailNotificationSender extends NotificationSender {
    @Override
    public Notification createNotification() {
        return new EmailNotification();
    }
}
