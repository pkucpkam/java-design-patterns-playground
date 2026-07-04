package structural;

/**
 * This demo illustrates the Adapter pattern.
 *
 * The Adapter pattern allows incompatible interfaces to work together. It acts as a wrapper
 * between two objects, translating calls from one interface to another.
 *
 * In this example, our application's client code works with a `NotificationSender` interface.
 * However, we want to integrate a third-party `SlackNotifier` library which has a completely
 * different, incompatible interface. The `SlackAdapter` class bridges this gap.
 */

// --- The Adaptee (The incompatible class) ---
/**
 * This represents a third-party library or a legacy class.
 * We can't change its source code. Its interface is not what our client expects.
 */
class SlackNotifier {
    /**
     * A specific method to send a message to a Slack channel.
     * @param channel The Slack channel name (e.g., "#general").
     * @param title The title of the message.
     * @param message The main content of the message.
     */
    public void sendSlackMessage(String channel, String title, String message) {
        System.out.println("--- Sending Slack Notification ---");
        System.out.println("Channel: " + channel);
        System.out.println("Title:   " + title);
        System.out.println("Message: " + message);
        System.out.println("--- Notification Sent ---");
    }
}

// --- The Target Interface ---
/**
 * This is the interface that our client code understands and uses.
 * We want to be able to use the SlackNotifier through this interface.
 */
interface NotificationSender {
    /**
     * A generic method to send a notification.
     * @param recipient The user or entity to notify.
     * @param message The notification message.
     */
    void sendNotification(String recipient, String message);
}


// --- The Adapter ---
/**
 * The Adapter class implements the Target interface and holds an instance of the Adaptee.
 * This is an example of an "Object Adapter" (using composition).
 */
class SlackAdapter implements NotificationSender {
    private final SlackNotifier slackNotifier; // Holds a reference to the adaptee.
    private final String slackChannel;         // The adapter can also store additional state/configuration.

    /**
     * The adapter's constructor takes the object it needs to adapt.
     * @param slackNotifier The instance of the incompatible class.
     * @param slackChannel  Configuration needed for the adaptee.
     */
    public SlackAdapter(SlackNotifier slackNotifier, String slackChannel) {
        this.slackNotifier = slackNotifier;
        this.slackChannel = slackChannel;
    }

    /**
     * This is where the translation happens.
     * The adapter receives a call through the Target interface method...
     * ...and translates it into a call to the Adaptee's specific method.
     */
    @Override
    public void sendNotification(String recipient, String message) {
        String title = "Notification for " + recipient;
        // The adapter calls the adaptee's method with the translated data.
        slackNotifier.sendSlackMessage(this.slackChannel, title, message);
    }
}


// --- The Client ---
public class AdapterDemo {
    public static void main(String[] args) {
        // The client code is only aware of the NotificationSender interface.
        
        // 1. Create an instance of the adaptee (the third-party library object).
        SlackNotifier slackApi = new SlackNotifier();

        // 2. Create an adapter and "wrap" the adaptee with it.
        // We configure the adapter to send all notifications to the "#dev-alerts" channel.
        NotificationSender notificationSender = new SlackAdapter(slackApi, "#dev-alerts");
        
        System.out.println("Client is about to send a notification...");
        
        // 3. The client calls the method on the adapter as if it were any other
        //    object implementing the NotificationSender interface.
        notificationSender.sendNotification("Build Server", "Deployment to production failed!");
        
        // The beauty of this is that the client code does not need to change at all.
        // We could easily swap the SlackAdapter with an EmailSender or SMSSender
        // as long as they also implement the NotificationSender interface.
    }
}
