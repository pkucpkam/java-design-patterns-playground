# Observer Pattern

## 1. Tổng quan

*   **Định nghĩa:** Observer là một mẫu thiết kế thuộc nhóm Behavioral, trong đó một đối tượng, được gọi là **Subject** (chủ thể), duy trì một danh sách các đối tượng phụ thuộc nó, được gọi là **Observers** (người quan sát). Khi trạng thái của Subject thay đổi, nó sẽ tự động thông báo cho tất cả các Observer.
*   **Vấn đề giải quyết:**
    *   **Giảm sự phụ thuộc (Loose Coupling):** Giúp giảm sự phụ thuộc trực tiếp giữa các đối tượng. Subject chỉ biết về Observer qua một interface chung, không cần biết Observer là lớp cụ thể nào. Tương tự, Observer có thể được tái sử dụng và kết hợp với nhiều Subject khác nhau.
    *   **Tự động cập nhật:** Khi một sự kiện xảy ra, các đối tượng liên quan sẽ được cập nhật một cách tự động mà không cần phải viết code kiểm tra, hỏi han liên tục.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn là một người nổi tiếng trên YouTube (là **Subject**). Các fan hâm mộ của bạn (là các **Observers**) đã "đăng ký kênh" (subscribe).

*   Khi bạn đăng một video mới (trạng thái của bạn thay đổi), hệ thống YouTube sẽ tự động gửi thông báo đến tất cả những người đã đăng ký.
*   Bạn không cần phải tự mình gửi email cho từng fan.
*   Các fan cũng không cần phải vào kênh của bạn mỗi 5 phút để kiểm tra xem có video mới không.

Mối quan hệ này là một ví dụ hoàn hảo về Observer pattern: Subject (YouTuber) và Observers (Subscribers).

## 3. Khi nào nên dùng

*   **Khi sự thay đổi trạng thái của một đối tượng cần được thông báo cho các đối tượng khác:** Mà bạn không biết chính xác có bao nhiêu đối tượng cần được thông báo và chúng là những đối tượng nào.
*   **Khi bạn muốn các đối tượng có thể "lắng nghe" sự kiện từ các đối tượng khác mà không tạo ra sự phụ thuộc cứng:** Ví dụ, trong kiến trúc MVC (Model-View-Controller), Model là Subject và View là Observer. Khi dữ liệu trong Model thay đổi, View sẽ tự động cập nhật để hiển thị dữ liệu mới.
*   **Trong các hệ thống hướng sự kiện (Event-Driven Systems):** Rất phổ biến trong các ứng dụng GUI, message queues, và các hệ thống microservices giao tiếp qua events.
*   **Khi cần thông báo cho nhiều loại đối tượng khác nhau:** Các observer không nhất thiết phải cùng một loại. Miễn là chúng implement cùng một observer interface, chúng đều có thể nhận thông báo.

## 4. Khi nào KHÔNG nên dùng

*   **Khi logic đơn giản và chỉ có một vài đối tượng liên quan:** Nếu chỉ có 1-2 đối tượng cần cập nhật và mối quan hệ này không thay đổi, việc dùng Observer có thể làm phức tạp hóa vấn đề. Một lời gọi phương thức trực tiếp có thể đơn giản hơn.
*   **Khi cần thứ tự thông báo nghiêm ngặt:** Hầu hết các implementation của Observer pattern không đảm bảo thứ tự mà các observer sẽ nhận được thông báo. Nếu thứ tự là quan trọng, bạn cần một cơ chế khác (ví dụ: Chain of Responsibility).
*   **Khi cần hiệu năng cao và tránh "Update Storm":** Nếu Subject thay đổi trạng thái quá thường xuyên và có hàng ngàn observer, việc thông báo cho tất cả cùng một lúc có thể gây ra vấn đề về hiệu năng.

## 5. Cách hoạt động

1.  **Subject Interface:**
    *   Định nghĩa các phương thức để quản lý observers: `registerObserver()`, `removeObserver()`, và `notifyObservers()`.
2.  **Observer Interface:**
    *   Định nghĩa một phương thức `update()` mà Subject sẽ gọi để gửi thông báo.
3.  **Concrete Subject:**
    *   Lưu trữ một danh sách các observers.
    *   Implement các phương thức quản lý observers.
    *   Khi trạng thái của nó thay đổi, nó sẽ duyệt qua danh sách và gọi phương thức `update()` của từng observer.
4.  **Concrete Observers:**
    *   Implement Observer Interface.
    *   Khi phương thức `update()` được gọi, nó sẽ thực hiện một hành động nào đó (ví dụ: lấy trạng thái mới từ Subject và cập nhật giao diện).

**Flow hoạt động:**
1.  Client tạo các Concrete Observer và đăng ký chúng với một Concrete Subject.
2.  Một sự kiện xảy ra làm thay đổi trạng thái của Concrete Subject.
3.  Subject gọi phương thức `notifyObservers()` của chính nó.
4.  Phương thức này duyệt qua tất cả các observer trong danh sách và gọi phương thức `update()` của từng observer.

## 6. Code ví dụ (Java)

Java đã có sẵn hỗ trợ cho Observer pattern thông qua `java.util.Observer` và `java.util.Observable`, nhưng chúng đã bị đánh dấu là "deprecated" (lỗi thời) từ Java 9 vì một số hạn chế về thiết kế. Cách tốt hơn là tự implement hoặc sử dụng các thư viện hiện đại hơn.

Dưới đây là cách tự implement:

```java
// File: examples/java/behavioral/ObserverDemo.java
package behavioral;

import java.util.ArrayList;
import java.util.List;

// --- Interfaces ---

// 2. Observer Interface
interface NewsObserver {
    void update(String news);
}

// 1. Subject Interface
interface NewsSubject {
    void registerObserver(NewsObserver observer);
    void removeObserver(NewsObserver observer);
    void notifyObservers();
}


// --- Concrete Classes ---

// 3. Concrete Subject
class NewsAgency implements NewsSubject {
    private final List<NewsObserver> observers = new ArrayList<>();
    private String latestNews;

    @Override
    public void registerObserver(NewsObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(NewsObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (NewsObserver observer : observers) {
            observer.update(latestNews);
        }
    }

    // Method to update the state and notify observers
    public void setLatestNews(String news) {
        this.latestNews = news;
        System.out.println("\n[News Agency] Breaking News: " + news);
        notifyObservers();
    }
}

// 4. Concrete Observers
class EmailSubscriber implements NewsObserver {
    private final String email;

    public EmailSubscriber(String email) {
        this.email = email;
    }

    @Override
    public void update(String news) {
        System.out.println("[Email to " + email + "] New story received: '" + news + "'");
    }
}

class SMSSubscriber implements NewsObserver {
    private final String phoneNumber;

    public SMSSubscriber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void update(String news) {
        System.out.println("[SMS to " + phoneNumber + "] Breaking news: '" + news + "'");
    }
}

// --- Demo ---
public class ObserverDemo {
    public static void main(String[] args) {
        // Create the subject (news agency)
        NewsAgency agency = new NewsAgency();

        // Create observers (subscribers)
        NewsObserver emailSub1 = new EmailSubscriber("john.doe@example.com");
        NewsObserver smsSub1 = new SMSSubscriber("123-456-7890");
        NewsObserver emailSub2 = new EmailSubscriber("jane.doe@example.com");

        // Observers subscribe to the subject
        agency.registerObserver(emailSub1);
        agency.registerObserver(smsSub1);
        agency.registerObserver(emailSub2);

        // The subject has new state and notifies all observers
        agency.setLatestNews("Design Patterns book released!");
        
        // One observer unsubscribes
        System.out.println("\n--- John Doe unsubscribes from email notifications ---");
        agency.removeObserver(emailSub1);
        
        // The subject has another new state
        agency.setLatestNews("Spring Boot 3.5 is now available!");
    }
}
```

## 7. Ứng dụng trong Spring Boot

Observer pattern là trái tim của kiến trúc hướng sự kiện trong Spring.

*   **`ApplicationListener` và `ApplicationEvent`:** Đây là cách triển khai Observer pattern chuẩn mực của Spring.
    *   **Subject:** Là `ApplicationEventPublisher`. Bất kỳ bean nào muốn gửi sự kiện chỉ cần inject `ApplicationEventPublisher` và gọi `publishEvent()`.
    *   **Event:** Là một lớp kế thừa từ `ApplicationEvent`. Đây là thông tin về sự kiện đã xảy ra.
    *   **Observer:** Là một bean implement `ApplicationListener<T>` hoặc một phương thức được đánh dấu `@EventListener`.

    ```java
    // 1. The Event (The data object)
    // This is a custom event. It must extend ApplicationEvent.
    public class OrderPlacedEvent extends ApplicationEvent {
        private final String orderId;
        public OrderPlacedEvent(Object source, String orderId) {
            super(source);
            this.orderId = orderId;
        }
        public String getOrderId() { return orderId; }
    }

    // 2. The Publisher (Part of the Subject)
    @Service
    public class OrderService {
        private final ApplicationEventPublisher eventPublisher;

        @Autowired
        public OrderService(ApplicationEventPublisher eventPublisher) {
            this.eventPublisher = eventPublisher;
        }

        public void placeOrder(String orderId) {
            System.out.println("[OrderService] Placing order: " + orderId);
            // Business logic to place order...
            
            // Publish an event to notify other parts of the system
            eventPublisher.publishEvent(new OrderPlacedEvent(this, orderId));
        }
    }

    // 3. The Observers (Listeners)
    @Component
    public class InventoryService {
        @EventListener // This annotation marks the method as an observer
        public void handleOrderPlaced(OrderPlacedEvent event) {
            System.out.println("[InventoryService] Received order " + event.getOrderId() + ". Reducing stock...");
            // Logic to update inventory
        }
    }

    @Component
    public class NotificationService {
        @EventListener
        public void handleOrderPlaced(OrderPlacedEvent event) {
            System.out.println("[NotificationService] Received order " + event.getOrderId() + ". Sending confirmation email...");
            // Logic to send email
        }
    }
    ```
    Khi `OrderService.placeOrder()` được gọi, nó sẽ publish một `OrderPlacedEvent`. Spring sẽ tự động tìm tất cả các `@EventListener` có thể xử lý event này và gọi chúng. `OrderService` không hề biết về `InventoryService` hay `NotificationService`, tạo ra một kiến trúc cực kỳ linh hoạt và tách biệt.

## 8. So sánh

*   **Observer vs. Mediator:**
    *   Observer tạo ra mối quan hệ một-nhiều (Subject-Observers). Giao tiếp thường là một chiều: Subject thông báo cho Observers.
    *   Mediator tạo ra mối quan hệ nhiều-nhiều (Colleagues-Mediator). Mọi giao tiếp đều phải đi qua Mediator, giúp các đối tượng không cần biết về nhau. Mediator phức tạp hơn nhưng giảm coupling mạnh hơn trong các hệ thống có nhiều tương tác chéo.

## 9. Interview Tips

*   **Câu hỏi:** "Observer pattern là gì? Nó giúp giải quyết vấn đề gì?"
    *   **Trả lời:** "Observer là một pattern mà một đối tượng (Subject) duy trì một danh sách các đối tượng phụ thuộc (Observers) và tự động thông báo cho chúng khi trạng thái của nó thay đổi. Nó giúp xây dựng các hệ thống có sự phụ thuộc lỏng lẻo (loose coupling), nơi các thành phần có thể phản ứng với sự kiện mà không cần biết chi tiết về nhau. Ví dụ như tính năng 'subscribe' trên YouTube."
*   **Câu hỏi:** "Bạn đã dùng Observer pattern trong Spring Boot như thế nào?"
    *   **Trả lời:** "Em đã sử dụng cơ chế `ApplicationEvent` và `@EventListener` của Spring. Em tạo một lớp custom event kế thừa từ `ApplicationEvent`. Trong một service, em inject `ApplicationEventPublisher` để publish event đó. Ở các service khác cần lắng nghe, em tạo một phương thức với annotation `@EventListener` để xử lý event. Cách này rất hữu ích để tách biệt các module, ví dụ như sau khi một đơn hàng được tạo, module inventory và module notification có thể lắng nghe và xử lý độc lập."
*   **Câu hỏi:** "Mô hình Publish-Subscribe (Pub/Sub) có phải là Observer pattern không?"
    *   **Trả lời:** "Về cơ bản là có, Pub/Sub là một biến thể hoặc một dạng triển khai ở quy mô lớn hơn của Observer pattern. Trong Observer pattern cổ điển, Subject và Observer thường nằm trong cùng một ứng dụng. Trong hệ thống Pub/Sub (như với RabbitMQ, Kafka), Publisher (Subject) và Subscriber (Observer) là các tiến trình hoặc dịch vụ riêng biệt, giao tiếp với nhau qua một message broker. Nhưng nguyên lý cốt lõi là như nhau: một bên phát tin, nhiều bên nhận tin."

