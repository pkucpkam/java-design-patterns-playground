package creational.factory_method.after;

public class SmsNotificationSender extends NotificationSender {
    @Override
    public Notification createNotification() {
        return new SmsNotification();
    }
}
