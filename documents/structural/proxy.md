# Proxy Pattern

## 1. Tổng quan

*   **Định nghĩa:** Proxy là một mẫu thiết kế thuộc nhóm Structural, cung cấp một đối tượng **thay thế** hoặc **đại diện (placeholder)** cho một đối tượng khác. Proxy kiểm soát việc truy cập đến đối tượng gốc, cho phép bạn thêm các hành vi bổ sung trước hoặc sau khi yêu cầu được chuyển đến đối tượng gốc.
*   **Vấn đề giải quyết:**
    *   **Kiểm soát truy cập (Access Control):** Proxy có thể kiểm tra quyền của client trước khi cho phép truy cập vào đối tượng gốc.
    *   **Khởi tạo lười (Lazy Initialization):** Nếu việc tạo đối tượng gốc rất tốn kém, Proxy có thể trì hoãn việc tạo đối tượng này cho đến khi nó thực sự cần thiết.
    *   **Ghi log (Logging):** Proxy có thể ghi lại log của các yêu cầu gửi đến đối tượng gốc.
    *   **Caching:** Proxy có thể lưu trữ kết quả của các yêu cầu tốn kém và trả về bản cache cho các yêu cầu lặp lại.
    *   **Remote Proxy:** Proxy có thể đại diện cho một đối tượng nằm ở một không gian địa chỉ khác (ví dụ: trên một server khác), che giấu sự phức tạp của việc giao tiếp qua mạng.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn có một chiếc **thẻ tín dụng (Credit Card)**.

*   **Đối tượng gốc (Real Subject):** Là **tài khoản ngân hàng (Bank Account)** của bạn, nơi chứa tiền thật.
*   **Proxy:** Là chiếc **thẻ tín dụng**.

Thẻ tín dụng là một vật thay thế cho tiền mặt trong tài khoản. Khi bạn quẹt thẻ để thanh toán:
1.  Proxy (thẻ tín dụng) nhận yêu cầu thanh toán.
2.  Nó thực hiện các thao tác bổ sung: kiểm tra tính hợp lệ của thẻ, xác thực mã PIN, kiểm tra hạn mức... (Kiểm soát truy cập).
3.  Nếu mọi thứ hợp lệ, nó mới chuyển yêu cầu đến đối tượng thật (tài khoản ngân hàng) để trừ tiền.

Bạn không tương tác trực tiếp với tài khoản ngân hàng ở cửa hàng, bạn tương tác qua một proxy.

## 3. Khi nào nên dùng

*   **Khi bạn cần một cách kiểm soát truy cập đến một đối tượng:** Đây là trường hợp sử dụng phổ biến nhất (Protection Proxy).
*   **Khi bạn muốn trì hoãn việc khởi tạo một đối tượng nặng (heavyweight object):** (Virtual Proxy).
*   **Khi bạn muốn cache kết quả của các yêu cầu:** (Caching Proxy).
*   **Khi đối tượng gốc nằm ở một dịch vụ từ xa (remote service):** (Remote Proxy).
*   **Khi bạn muốn ghi log các lời gọi đến đối tượng dịch vụ:** (Logging Proxy).

## 4. Khi nào KHÔNG nên dùng

*   **Khi không có logic bổ sung nào cần thêm vào:** Nếu bạn không cần kiểm soát truy cập, caching, hay lazy loading, việc thêm một lớp Proxy chỉ làm tăng sự phức tạp không cần thiết.
*   **Khi client cần truy cập trực tiếp vào các thuộc tính của đối tượng gốc:** Proxy hoạt động tốt nhất khi client chỉ làm việc thông qua interface.

## 5. Cách hoạt động

1.  **Subject Interface:**
    *   Định nghĩa interface chung cho cả `RealSubject` và `Proxy`.
    *   Điều này cho phép client có thể sử dụng `Proxy` giống hệt như cách nó sử dụng `RealSubject`.
2.  **Real Subject:**
    *   Lớp chứa logic nghiệp vụ thực sự. Đây là đối tượng mà `Proxy` đại diện.
3.  **Proxy:**
    *   Lưu trữ một tham chiếu đến `RealSubject`. Proxy có thể tạo và quản lý vòng đời của `RealSubject`.
    *   Implement cùng `Subject Interface` với `RealSubject`.
    *   Thực hiện các công việc bổ sung (kiểm tra quyền, cache, log,...) trước hoặc sau khi ủy quyền lời gọi đến `RealSubject`.

**Flow hoạt động (Virtual Proxy):**
Client -> `Gọi một phương thức trên Proxy` -> `Proxy kiểm tra xem RealSubject đã được tạo chưa?` -> `Chưa: new RealSubject()` -> `Proxy ủy quyền lời gọi đến RealSubject` -> `RealSubject thực hiện công việc`.

## 6. Code ví dụ (Java)

Ví dụ về một "Protection Proxy" để kiểm soát quyền truy cập vào một tài liệu nhạy cảm.

```java
// File: examples/java/structural/ProxyDemo.java
package structural;

// A simple class representing a user
class User {
    private String role;
    public User(String role) { this.role = role; }
    public String getRole() { return role; }
}

// 1. The Subject Interface
interface Document {
    void view();
    void edit(String content);
}

// 2. The Real Subject
class RealDocument implements Document {
    private String filename;
    private String content;

    public RealDocument(String filename) {
        this.filename = filename;
        // Simulate loading a heavy document from disk
        System.out.println("Loading document '" + filename + "' from disk...");
        this.content = "This is the original content of the document.";
    }

    @Override
    public void view() {
        System.out.println("Viewing document '" + filename + "': " + content);
    }

    @Override
    public void edit(String newContent) {
        this.content = newContent;
        System.out.println("Editing document '" + filename + "' to: " + newContent);
    }
}

// 3. The Proxy
class DocumentProxy implements Document {
    private RealDocument realDocument; // Reference to the real subject
    private String filename;
    private User user;

    public DocumentProxy(String filename, User user) {
        this.filename = filename;
        this.user = user;
    }

    // Lazy initialization of the real subject
    private void ensureRealDocumentInitialized() {
        if (realDocument == null) {
            realDocument = new RealDocument(filename);
        }
    }

    @Override
    public void view() {
        // All users can view
        ensureRealDocumentInitialized();
        realDocument.view();
    }

    @Override
    public void edit(String content) {
        // Protection: Only admins can edit
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            ensureRealDocumentInitialized();
            realDocument.edit(content);
        } else {
            System.out.println("Access Denied: User '" + user.getRole() + "' does not have permission to edit.");
        }
    }
}

// --- Demo ---
public class ProxyDemo {
    public static void main(String[] args) {
        User adminUser = new User("ADMIN");
        User normalUser = new User("USER");

        System.out.println("--- Admin user is interacting with the document ---");
        // The client interacts with the proxy, not the real object
        Document adminProxy = new DocumentProxy("secret.doc", adminUser);
        adminProxy.view(); // Access granted, real document is loaded here
        adminProxy.edit("This is the new content."); // Access granted

        System.out.println("\n--- Normal user is interacting with the document ---");
        Document userProxy = new DocumentProxy("secret.doc", normalUser);
        userProxy.view(); // Access granted, real document is loaded here (if not already)
        userProxy.edit("Trying to add some graffiti."); // Access denied
    }
}
```

## 7. Ứng dụng trong Spring Boot

Proxy là một trong những pattern **cốt lõi và quan trọng nhất** của Spring Framework, được sử dụng rộng rãi thông qua **Spring AOP (Aspect-Oriented Programming)**.

Khi bạn sử dụng các annotation như `@Transactional`, `@Cacheable`, `@Async`, `@PreAuthorize`, Spring không sửa đổi code của bean của bạn. Thay vào đó, nó tạo ra một **proxy** để bọc bean của bạn lại lúc runtime.

*   **`@Transactional` (Transaction Proxy):**
    *   Khi bạn gọi một phương thức được đánh dấu `@Transactional` trên một bean, thực chất bạn đang gọi nó trên proxy.
    *   Proxy sẽ bắt đầu một transaction, sau đó gọi phương thức thật trên đối tượng gốc.
    *   Nếu phương thức thật chạy thành công, proxy sẽ commit transaction. Nếu có exception, proxy sẽ rollback transaction.
*   **`@Cacheable` (Caching Proxy):**
    *   Proxy sẽ kiểm tra xem kết quả của lời gọi phương thức (với các tham số tương ứng) đã có trong cache hay chưa.
    *   Nếu có, proxy trả về kết quả từ cache mà không cần gọi phương thức thật.
    *   Nếu không, proxy gọi phương thức thật, lưu kết quả vào cache, rồi trả về cho client.
*   **`@PreAuthorize` trong Spring Security (Protection Proxy):**
    *   Proxy sẽ kiểm tra các điều kiện bảo mật (ví dụ: `hasRole('ADMIN')`) trước khi gọi phương thức thật. Nếu không thỏa mãn, proxy sẽ ném ra một `AccessDeniedException`.
*   **Lazy Loading trong JPA/Hibernate (Virtual Proxy):**
    *   Khi bạn load một entity `A` có quan hệ `@ManyToOne` với `B` (được đánh dấu `fetch = FetchType.LAZY`), trường `b` trong entity `A` sẽ không phải là một đối tượng `B` thật sự, mà là một **proxy** của `B`.
    *   Chỉ khi bạn truy cập vào một thuộc tính nào đó của `b` (ví dụ: `a.getB().getName()`), proxy này mới thực sự truy vấn database để load đối tượng `B` thật.

## 8. So sánh

*   **Proxy vs. Decorator:**
    *   Cả hai đều bọc một đối tượng và có cùng interface.
    *   **Mục đích:** Proxy **kiểm soát truy cập**, còn Decorator **thêm chức năng**. Decorator cho phép bọc nhiều lớp lồng vào nhau, còn Proxy thường chỉ có một lớp.
*   **Proxy vs. Adapter:**
    *   Proxy implement **cùng** interface với đối tượng thật. Adapter implement một interface **khác** (interface mà client mong đợi).

## 9. Interview Tips

*   **Câu hỏi:** "Proxy pattern là gì?"
    *   **Trả lời:** "Proxy pattern cung cấp một đối tượng thay thế để kiểm soát việc truy cập đến một đối tượng khác. Nó cho phép thêm các hành vi bổ sung trước hoặc sau khi yêu cầu được chuyển đến đối tượng gốc. Ví dụ như kiểm tra quyền, caching, hoặc lazy loading."
*   **Câu hỏi:** "Proxy được sử dụng trong Spring như thế nào? Cho ví dụ."
    *   **Trả lời:** "Proxy là nền tảng của Spring AOP. Khi em dùng `@Transactional`, Spring tạo một proxy để quản lý vòng đời transaction. Khi em dùng `@Cacheable`, Spring tạo một proxy để xử lý logic cache. Tương tự, `@PreAuthorize` của Spring Security cũng dùng proxy để kiểm tra quyền. Về cơ bản, các 'phép màu' cross-cutting concern của Spring đều được thực hiện qua proxy."
*   **Câu hỏi:** "Sự khác biệt giữa `Protection Proxy` và `Virtual Proxy` là gì?"
    *   **Trả lời:** "`Protection Proxy` tập trung vào việc kiểm soát quyền truy cập, ví dụ như chỉ cho phép user có role 'ADMIN' mới được gọi một phương thức. `Virtual Proxy` tập trung vào việc trì hoãn việc tạo một đối tượng tốn kém cho đến khi nó thực sự cần thiết, đây chính là cơ chế Lazy Loading trong Hibernate/JPA."

