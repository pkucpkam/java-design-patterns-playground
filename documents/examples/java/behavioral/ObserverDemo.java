package behavioral;

import java.util.ArrayList;
import java.util.List;

/**
 * This demo illustrates the Observer pattern.
 *
 * We have a "Subject" (NewsAgency) which maintains a list of its dependents, called "Observers" (Subscribers).
 * When the Subject's state changes (e.g., new news is available), it automatically notifies all its observers.
 * The observers then update themselves based on the notification.
 * This creates a loosely coupled design, as the subject only knows about the abstract observer interface,
 * not the concrete classes.
 */

// --- Interfaces ---

/**
 * 2. The Observer Interface
 * Defines the `update` method that the Subject will call when its state changes.
 */
interface NewsObserver {
    void update(String news);
}

/**
 * 1. The Subject Interface
 * Defines methods for observers to register, unregister, and for the subject to notify them.
 */
interface NewsSubject {
    void registerObserver(NewsObserver observer);
    void removeObserver(NewsObserver observer);
    void notifyObservers();
}


// --- Concrete Classes ---

/**
 * 3. The Concrete Subject
 * This class stores the state and a list of observers. When the state changes, it notifies the observers.
 */
class NewsAgency implements NewsSubject {
    private final List<NewsObserver> observers = new ArrayList<>();
    private String latestNews;

    @Override
    public void registerObserver(NewsObserver observer) {
        System.out.println("New observer registered: " + observer.getClass().getSimpleName());
        observers.add(observer);
    }

    @Override
    public void removeObserver(NewsObserver observer) {
        System.out.println("Observer removed: " + observer.getClass().getSimpleName());
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        // Iterate through all registered observers and call their update method.
        for (NewsObserver observer : observers) {
            observer.update(latestNews);
        }
    }

    /**
     * This is the method that triggers the notification.
     * When the news agency has a new story, it calls this method.
     * @param news The new story.
     */
    public void setLatestNews(String news) {
        this.latestNews = news;
        System.out.println("
[News Agency] Breaking News: "" + news + """);
        // After updating its state, the subject notifies all observers.
        notifyObservers();
    }
}

/**
 * 4. Concrete Observers
 * These classes implement the Observer interface and define what to do when they are notified.
 */
class EmailSubscriber implements NewsObserver {
    private final String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String news) {
        // When updated, the subscriber performs its action, e.g., sending an email.
        System.out.println(" -> [Email to " + email + "] New story received: '" + news + "'");
    }
}

class SMSSubscriber implements NewsObserver {
    private final String phoneNumber;

    public SMSSubscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(String news) {
        // This subscriber sends an SMS.
        System.out.println(" -> [SMS to " + phoneNumber + "] Breaking news: '" + news + "'");
    }
}


// --- Demo ---
// The client code that wires everything together.
public class ObserverDemo {
    public static void main(String[] args) {
        // 1. Create the subject (the news agency).
        NewsAgency agency = new NewsAgency();

        // 2. Create observers (the subscribers).
        NewsObserver emailSub1 = new EmailSubscriber("john.doe@example.com");
        NewsObserver smsSub1 = new SMSSubscriber("123-456-7890");
        NewsObserver emailSub2 = new EmailSubscriber("jane.doe@example.com");

        // 3. Observers subscribe to the subject.
        agency.registerObserver(emailSub1);
        agency.registerObserver(smsSub1);
        agency.registerObserver(emailSub2);

        // 4. The subject's state changes. It automatically notifies all subscribed observers.
        agency.setLatestNews("Design Patterns book has been released!");

        // 5. An observer decides to unsubscribe.
        System.out.println("
--- John Doe unsubscribes from email notifications ---");
        agency.removeObserver(emailSub1);

        // 6. The subject's state changes again. Only the remaining observers are notified.
        agency.setLatestNews("Spring Boot 3.5 is now available!");
    }
}
