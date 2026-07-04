# Adapter Pattern

## 1. Tổng quan

*   **Định nghĩa:** Adapter là một mẫu thiết kế thuộc nhóm Structural, cho phép các đối tượng có giao diện (interface) không tương thích có thể làm việc cùng nhau. Nó hoạt động như một "bộ chuyển đổi" hoặc "người phiên dịch" giữa hai interface.
*   **Vấn đề giải quyết:**
    *   **Tích hợp các lớp không tương thích:** Khi bạn muốn sử dụng một lớp đã có sẵn (ví dụ: từ một thư viện bên thứ ba) nhưng interface của nó không khớp với interface mà hệ thống của bạn yêu cầu.
    *   **Tái sử dụng code:** Giúp bạn tái sử dụng các lớp cũ, các hệ thống kế thừa (legacy systems) mà không cần phải viết lại chúng.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn có một chiếc thẻ nhớ MicroSD (**Adaptee**) chứa đầy ảnh, nhưng laptop của bạn chỉ có cổng đọc thẻ SD tiêu chuẩn (**Target Interface**). Bạn không thể cắm trực tiếp thẻ MicroSD vào laptop.

Bạn cần một **Adapter**: đó là chiếc áo thẻ SD (SD card adapter). Bạn cắm thẻ MicroSD vào adapter, sau đó cắm cả bộ adapter vào laptop. Laptop sẽ đọc được dữ liệu từ thẻ MicroSD thông qua giao diện thẻ SD mà nó hiểu.

*   **Adaptee:** Thẻ MicroSD (lớp có chức năng bạn cần, nhưng interface không tương thích).
*   **Target:** Cổng đọc thẻ SD (interface mà client mong đợi).
*   **Adapter:** Áo thẻ SD (lớp trung gian chuyển đổi interface).

## 3. Khi nào nên dùng

*   **Khi bạn muốn sử dụng một lớp có sẵn nhưng interface của nó không tương thích với phần còn lại của code.** Đây là trường hợp sử dụng phổ biến nhất.
*   **Khi bạn muốn tạo một lớp có thể tái sử dụng và làm việc được với các lớp khác không lường trước được, có interface không tương thích.**
*   **Khi bạn làm việc với các hệ thống hoặc API của bên thứ ba:** Bạn không thể thay đổi code của họ, vì vậy bạn cần viết một Adapter để "bọc" chúng lại cho phù hợp với hệ thống của mình.

## 4. Khi nào KHÔNG nên dùng

*   **Khi bạn có thể sửa đổi code của một trong hai bên:** Nếu bạn có toàn quyền kiểm soát code, việc sửa đổi trực tiếp interface cho nhất quán thường là giải pháp tốt hơn và đơn giản hơn là thêm một lớp Adapter.
*   **Khi chức năng không phù hợp:** Adapter chỉ giúp "phiên dịch" interface, nó không thêm chức năng mới. Nếu lớp bạn muốn tích hợp không cung cấp đủ chức năng cần thiết, Adapter không thể giải quyết được vấn đề.

## 5. Cách hoạt động

Có hai cách triển khai Adapter Pattern:

1.  **Object Adapter (Sử dụng Composition):**
    *   Adapter chứa một tham chiếu đến một instance của `Adaptee`.
    *   Adapter implement `Target` interface.
    *   Khi một phương thức trên Adapter được gọi, nó sẽ gọi phương thức tương ứng trên đối tượng `Adaptee` mà nó đang giữ.
    *   Đây là cách linh hoạt và được ưa chuộng hơn.

2.  **Class Adapter (Sử dụng Kế thừa):**
    *   Adapter kế thừa từ cả `Adaptee` (lớp cụ thể) và implement `Target` interface.
    *   Cách này chỉ khả thi trong các ngôn ngữ hỗ trợ đa kế thừa (ví dụ: C++). Trong Java, bạn có thể "giả lập" bằng cách kế thừa từ lớp `Adaptee` và implement interface `Target`.
    *   Ít linh hoạt hơn vì nó gắn chặt Adapter với một `Adaptee` cụ thể.

**Flow (Object Adapter):**
Client -> `Gọi phương thức trên Target interface (của Adapter)` -> `Adapter` -> `Gọi phương thức tương ứng trên đối tượng Adaptee` -> `Adaptee thực hiện công việc`.

## 6. Code ví dụ (Java)

Ví dụ về việc tích hợp một dịch vụ gửi thông báo qua Slack (không tương thích) vào một hệ thống thông báo chung.

```java
// File: examples/java/structural/AdapterDemo.java
package structural;

// --- Hệ thống cũ / Thư viện bên thứ ba (Adaptee) ---
/**
 * The Adaptee: A third-party library for sending Slack notifications.
 * It has a specific interface that is incompatible with our system.
 */
class SlackNotifier {
    public void sendSlackMessage(String channel, String title, String message) {
        System.out.println("SLACK NOTIFICATION:");
        System.out.println("Channel: " + channel);
        System.out.println("Title: " + title);
        System.out.println("Message: " + message);
    }
}

// --- Hệ thống của chúng ta (Target) ---
/**
 * The Target Interface: This is the interface our application's client code uses.
 */
interface NotificationSender {
    void sendNotification(String recipient, String message);
}

// --- Adapter ---
/**
 * The Adapter: This class makes the SlackNotifier work with our NotificationSender interface.
 * This is an Object Adapter because it holds an instance of the Adaptee.
 */
class SlackAdapter implements NotificationSender {
    private final SlackNotifier slackNotifier;
    private final String slackChannel; // The adapter can hold extra configuration

    public SlackAdapter(SlackNotifier slackNotifier, String slackChannel) {
        this.slackNotifier = slackNotifier;
        this.slackChannel = slackChannel;
    }

    @Override
    public void sendNotification(String recipient, String message) {
        // The adapter translates the method call from the Target interface
        // into a method call on the Adaptee.
        String title = "Notification for " + recipient;
        slackNotifier.sendSlackMessage(this.slackChannel, title, message);
    }
}


// --- Client code ---
public class AdapterDemo {
    public static void main(String[] args) {
        // The client code wants to send a notification.
        // It only knows about the NotificationSender interface.
        
        // Create an instance of the incompatible class (the adaptee)
        SlackNotifier slackApi = new SlackNotifier();

        // Create an adapter and wrap the adaptee with it
        // The adapter is configured to send to the #general channel
        NotificationSender notificationSender = new SlackAdapter(slackApi, "#general");
        
        System.out.println("Client is sending a notification via the adapter...");
        
        // The client calls the method on the adapter, using the target interface
        notificationSender.sendNotification("John Doe", "Your build has failed!");
        
        // The client code remains unchanged, even though the underlying implementation is completely different.
    }
}
```

## 7. Ứng dụng trong Spring Boot

*   **`HandlerAdapter` trong Spring MVC:**
    *   Đây là một trong những ví dụ kinh điển nhất. Trong Spring MVC, có nhiều cách để định nghĩa một controller (ví dụ: implement interface `Controller`, dùng `@RequestMapping`,...).
    *   `DispatcherServlet` (client) không quan tâm controller của bạn được định nghĩa như thế nào. Nó chỉ cần một `HandlerAdapter` phù hợp để thực thi controller đó.
    *   Ví dụ, `RequestMappingHandlerAdapter` là adapter xử lý các phương thức được chú thích `@RequestMapping`. `SimpleControllerHandlerAdapter` xử lý các controller implement interface `Controller`. `DispatcherServlet` sẽ duyệt qua danh sách các `HandlerAdapter` đã đăng ký để tìm ra adapter phù hợp có thể xử lý một handler (controller) cụ thể.

*   **Bọc (Wrapping) các Service của bên thứ ba:**
    *   Khi bạn tích hợp một API của bên thứ ba (ví dụ: một SDK của dịch vụ thanh toán, gửi email), bạn thường sẽ tạo một lớp Service của riêng mình (ví dụ: `PaymentService`) để "bọc" SDK đó lại. Lớp service này chính là một Adapter. Nó chuyển đổi các lời gọi phương thức từ hệ thống của bạn thành các lời gọi đến SDK, đồng thời che giấu các chi tiết phức tạp của SDK.

    ```java
    // Adaptee (Third-party SDK)
    // public class StripeSDK {
    //     public void charge(int amountInCents, String currency, String cardToken) { ... }
    // }

    // Target Interface (Our system's interface)
    public interface PaymentService {
        boolean processPayment(double amount, String paymentToken);
    }
    
    // Adapter
    @Service
    public class StripeAdapter implements PaymentService {
        private final StripeSDK stripeSDK = new StripeSDK();

        @Override
        public boolean processPayment(double amount, String paymentToken) {
            int amountInCents = (int) (amount * 100);
            // Adapting the method call
            stripeSDK.charge(amountInCents, "USD", paymentToken);
            return true; // add error handling
        }
    }
    ```

## 8. So sánh

*   **Adapter vs. Decorator:**
    *   **Mục đích:** Adapter thay đổi interface của một đối tượng. Decorator thêm chức năng mới vào một đối tượng mà không thay đổi interface của nó.
    *   **Interface:** Decorator luôn implement cùng interface với đối tượng nó bọc. Adapter implement một interface khác.
*   **Adapter vs. Facade:**
    *   **Mục đích:** Adapter "bọc" một đối tượng duy nhất để làm cho nó tương thích. Facade cung cấp một giao diện đơn giản cho cả một hệ thống con (subsystem) phức tạp gồm nhiều đối tượng.
*   **Adapter vs. Proxy:**
    *   **Mục đích:** Cả hai đều "đứng giữa" và ủy quyền. Adapter thay đổi interface. Proxy có cùng interface với đối tượng thật và thường dùng để kiểm soát truy cập, lazy loading, hoặc logging.

## 9. Interview Tips

*   **Câu hỏi:** "Adapter pattern là gì? Khi nào bạn dùng nó?"
    *   **Trả lời:** "Adapter pattern giúp hai interface không tương thích có thể làm việc cùng nhau. Em dùng nó khi cần tích hợp một lớp có sẵn, ví dụ từ thư viện bên thứ ba, vào hệ thống của mình nhưng interface của nó không khớp. Em sẽ tạo một lớp Adapter để 'bọc' lớp đó lại và 'dịch' các lời gọi từ interface của hệ thống em sang interface của lớp đó."
*   **Câu hỏi:** "So sánh Adapter và Decorator."
    *   **Trả lời:** "Mục đích của chúng khác nhau. Adapter dùng để thay đổi interface, còn Decorator dùng để thêm chức năng. Một Decorator sẽ có cùng interface với đối tượng nó bọc, trong khi Adapter sẽ có interface khác với đối tượng nó bọc (adaptee)."
*   **Câu hỏi:** "Trong Spring, bạn có thấy Adapter pattern ở đâu không?"
    *   **Trả lời:** "Ví dụ điển hình là `HandlerAdapter` trong Spring MVC. `DispatcherServlet` không cần biết chi tiết về các loại controller khác nhau. Nó chỉ cần tìm một `HandlerAdapter` phù hợp để thực thi controller đó. Ví dụ, `RequestMappingHandlerAdapter` là adapter cho các controller dùng `@RequestMapping`."

