package creational.factory_method.before;

public class NotificationService {
    public void sendNotification(String type, String message, String recipient) {
        if ("EMAIL".equalsIgnoreCase(type)) {
            EmailNotification email = new EmailNotification();
            email.sendEmail(message, recipient);
        } else if ("SMS".equalsIgnoreCase(type)) {
            SmsNotification sms = new SmsNotification();
            sms.sendSms(message, recipient);
        } else if ("PUSH".equalsIgnoreCase(type)) {
            PushNotification push = new PushNotification();
            push.sendPush(message, recipient);
        } else {
            throw new IllegalArgumentException("Unsupported notification type: " + type);
        }
    }
}
