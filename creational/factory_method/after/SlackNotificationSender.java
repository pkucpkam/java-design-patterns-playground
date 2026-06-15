package creational.factory_method.after;

public class SlackNotificationSender extends NotificationSender {
    @Override
    public Notification createNotification() {
        return new SlackNotification();
    }
}
