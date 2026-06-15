package creational.factory_method.after;

public class SlackNotification implements Notification {
    @Override
    public void send(String message, String recipient) {
        System.out.println("Sending Slack message to channel " + recipient + ": " + message);
    }
}
