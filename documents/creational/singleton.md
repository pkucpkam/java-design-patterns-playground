# Singleton Pattern

## 1. Tổng quan

*   **Định nghĩa:** Singleton là một mẫu thiết kế thuộc nhóm Creational (khởi tạo), đảm bảo rằng một lớp (class) chỉ có duy nhất một thể hiện (instance) và cung cấp một điểm truy cập toàn cục (global access point) đến thể hiện đó.
*   **Vấn đề giải quyết:**
    *   **Đảm bảo duy nhất một instance:** Ngăn chặn việc tạo nhiều đối tượng của một lớp khi việc có nhiều đối tượng sẽ gây ra lỗi logic, lãng phí tài nguyên, hoặc làm sai lệch trạng thái của hệ thống (ví dụ: lớp quản lý cấu hình, kết nối database, logging).
    *   **Cung cấp điểm truy cập toàn cục:** Thay vì truyền một đối tượng qua lại giữa các phương thức và lớp, Singleton cho phép bạn truy cập trực tiếp vào instance duy nhất từ bất kỳ đâu trong code.

## 2. Ví dụ đời thường

Hãy tưởng tượng trong một quốc gia chỉ có **một chính phủ** duy nhất. Dù có bao nhiêu bộ, ban, ngành hay người dân muốn yêu cầu điều gì, họ đều phải thông qua cùng một chính phủ đó. Chính phủ này là duy nhất, không thể có hai chính phủ song song tồn tại.

Trong lập trình, lớp Singleton chính là "chính phủ" đó. Dù có bao nhiêu phần của chương trình cần dùng đến nó, tất cả đều sẽ nhận được cùng một đối tượng duy nhất.

## 3. Khi nào nên dùng

Singleton hữu ích trong các kịch bản backend sau:

*   **Quản lý cấu hình (Configuration Management):** Một lớp đọc và lưu trữ cấu hình của ứng dụng (ví dụ: từ file `.properties`, `.yml`). Cả hệ thống chỉ cần một đối tượng config duy nhất để đảm bảo tính nhất quán.
*   **Quản lý kết nối (Connection Pool):** Các kết nối đến database, message queue (RabbitMQ, Kafka), hoặc các dịch vụ ngoài rất tốn kém để tạo mới. Một `ConnectionPool` dạng Singleton sẽ quản lý một tập các kết nối có sẵn và tái sử dụng chúng, giúp tăng hiệu năng.
*   **Logging:** Một lớp Logger ghi log ra file hoặc console. Việc dùng Singleton đảm bảo tất cả log đều được ghi vào cùng một stream một cách có trật tự, thay vì mỗi đối tượng ghi log vào một file riêng lẻ.
*   **Caching:** Một lớp cache trong bộ nhớ (in-memory cache) nên là Singleton để tất cả các phần của ứng dụng đều chia sẻ cùng một vùng cache, tránh dữ liệu không nhất quán.
*   **Quản lý Thread Pool:** Trong các ứng dụng xử lý đa luồng, một `ExecutorService` (thread pool) nên được quản lý bởi một Singleton để kiểm soát số lượng luồng và phân phối tác vụ một cách hiệu quả.

## 4. Khi nào KHÔNG nên dùng

Singleton có thể trở thành một "anti-pattern" nếu lạm dụng:

*   **Vi phạm Single Responsibility Principle:** Lớp Singleton thường tự chịu trách nhiệm về cả logic nghiệp vụ của nó và cả việc kiểm soát vòng đời của chính nó.
*   **Khó viết Unit Test:** Vì Singleton mang trạng thái toàn cục, các unit test sẽ bị phụ thuộc lẫn nhau. Test case này có thể làm thay đổi trạng thái của Singleton và ảnh hưởng đến kết quả của test case khác. Rất khó để "mock" một Singleton nếu nó được gọi trực tiếp (`MySingleton.getInstance()`).
*   **Khó mở rộng và kế thừa:** Constructor là `private` nên không thể kế thừa từ lớp Singleton.
*   **Gây耦合 (Coupling) cao:** Mọi lớp trong hệ thống đều có thể truy cập trực tiếp vào Singleton, tạo ra một sự phụ thuộc ngầm và làm cho code khó bảo trì, khó theo dõi luồng dữ liệu.
*   **Không phù hợp trong môi trường đa luồng (nếu không được xử lý đúng):** Nếu không được implement cẩn thận, nhiều luồng có thể cùng lúc tạo ra nhiều instance, phá vỡ nguyên tắc của Singleton.

## 5. Cách hoạt động

Singleton hoạt động dựa trên 3 nguyên tắc:

1.  **Private Constructor:** Ngăn không cho các lớp khác có thể dùng từ khóa `new` để tự do tạo instance của lớp Singleton.
2.  **Private Static Instance:** Lớp Singleton chứa một thuộc tính `static` và `private` để giữ thể hiện (instance) duy nhất của chính nó.
3.  **Public Static `getInstance()` Method:** Cung cấp một phương thức `static` và `public` cho phép bên ngoài truy cập vào instance duy nhất đó. Phương thức này sẽ kiểm tra xem instance đã được tạo hay chưa. Nếu chưa, nó sẽ tạo mới (chỉ trong lần gọi đầu tiên) và trả về. Nếu đã có, nó chỉ việc trả về instance đang tồnentaị.

Flow hoạt động:
`Client` -> `getInstance()` -> `Check if instance is null?` -> `Yes: new Singleton()` -> `Return instance`
`Client` -> `getInstance()` -> `Check if instance is null?` -> `No` -> `Return existing instance`

## 6. Code ví dụ (Java)

Đây là cách triển khai Singleton an toàn trong môi trường đa luồng (thread-safe) bằng phương pháp "Double-Checked Locking".

```java
// File: examples/java/creational/SingletonConfigManager.java

package creational;

/**
 * SingletonConfigManager manages application configuration.
 * This implementation is thread-safe using Double-Checked Locking.
 */
public class SingletonConfigManager {

    // Step 2: Private static instance of the class.
    // The 'volatile' keyword ensures that multiple threads handle the uniqueInstance variable correctly
    // when it is being initialized to the Singleton instance.
    private static volatile SingletonConfigManager instance;

    private String databaseUrl;
    private String apiKey;

    // Step 1: Private constructor to prevent instantiation from other classes.
    private SingletonConfigManager() {
        // Simulating reading configuration from a file
        System.out.println("Reading configuration from file...");
        this.databaseUrl = "jdbc:mysql://localhost:3306/mydb";
        this.apiKey = "XYZ-123-ABC";
    }

    // Step 3: Public static method to get the instance of the class.
    public static SingletonConfigManager getInstance() {
        // First check (without locking) for performance
        if (instance == null) {
            // Synchronize only when the instance is null (first time)
            synchronized (SingletonConfigManager.class) {
                // Double-check inside the synchronized block to ensure thread safety
                if (instance == null) {
                    instance = new SingletonConfigManager();
                }
            }
        }
        return instance;
    }

    // --- Getter methods for configuration properties ---

    public String getDatabaseUrl() {
        return databaseUrl;
    }

    public String getApiKey() {
        return apiKey;
    }

    // --- Example of how to use it ---
    public static void main(String[] args) {
        // Get the singleton instance
        SingletonConfigManager configManager1 = SingletonConfigManager.getInstance();
        SingletonConfigManager configManager2 = SingletonConfig.getInstance();

        // Verify that both variables point to the same instance
        if (configManager1 == configManager2) {
            System.out.println("Both configManager1 and configManager2 are the same instance.");
        }

        // Use the configuration
        System.out.println("Database URL: " + configManager1.getDatabaseUrl());
        System.out.println("API Key: " + configManager2.getApiKey());
    }
}
```

## 7. Ứng dụng trong Spring Boot

Trong Spring Framework và Spring Boot, khái niệm Singleton được quản lý một cách tự nhiên và hiệu quả bởi **IoC Container (Inversion of Control)**.

Mặc định, tất cả các **bean** do Spring quản lý đều là **Singleton scope**.

*   **Bean là gì?** Bean là một đối tượng được khởi tạo, lắp ráp và quản lý bởi Spring IoC container.
*   **Singleton Scope:** Khi bạn định nghĩa một bean (ví dụ, bằng cách đánh dấu một lớp với `@Component`, `@Service`, `@Repository`), Spring container sẽ chỉ tạo ra **một instance duy nhất** của bean đó. Bất cứ khi nào một bean khác yêu cầu (`@Autowired`) bean này, Spring sẽ tiêm (inject) chính instance đó vào.

**Ví dụ:**

```java
// File: examples/spring-boot/services/AppConfig.java

package com.example.designpatterns.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service // By default, this is a Singleton bean
public class AppConfig {

    @Value("${app.name}")
    private String appName;

    @Value("${server.port}")
    private int serverPort;

    public AppConfig() {
        System.out.println("AppConfig bean is being created by Spring...");
    }

    public String getAppName() {
        return appName;
    }

    public int getServerPort() {
        return serverPort;
    }
}

// File: examples/spring-boot/services/UserService.java
package com.example.designpatterns.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final AppConfig appConfig;

    @Autowired
    public UserService(AppConfig appConfig) {
        this.appConfig = appConfig;
        System.out.println("UserService is using AppConfig: " + appConfig.getAppName());
    }

    public void displayConfig() {
        System.out.println("Port from UserService: " + appConfig.getServerPort());
    }
}
```

Trong ví dụ trên, Spring container sẽ:
1.  Tạo một instance duy nhất của `AppConfig`.
2.  Khi tạo `UserService`, nó thấy `UserService` cần một `AppConfig`.
3.  Nó sẽ inject instance `AppConfig` đã tạo ở bước 1 vào `UserService`.

=> Bạn không cần phải tự viết `getInstance()` hay lo về thread-safety. Spring đã làm tất cả cho bạn. Đây là cách "Singleton" được áp dụng một cách thanh lịch và hiệu quả nhất trong các ứng dụng hiện đại.

## 8. So sánh

*   **Singleton vs. Static Class:**
    *   Một lớp static chỉ chứa các phương thức và thuộc tính static, không thể tạo instance. Singleton cho phép tạo một instance, có thể implement interface và kế thừa từ lớp khác (dù constructor private hạn chế việc này).
    *   Singleton linh hoạt hơn, có thể được truyền vào các phương thức dưới dạng tham số (dù không khuyến khích), trong khi lớp static thì không.
*   **Singleton vs. Utility Class:**
    *   Utility class (lớp tiện ích) thường là `final` và có constructor `private`, chỉ chứa các phương thức `static` (ví dụ `Math`, `Collections` trong Java). Nó không bao giờ có instance.
    *   Singleton có một instance và lưu giữ trạng thái. Utility class là stateless (không có trạng thái).

## 9. Interview Tips

*   **Câu hỏi:** "Singleton là gì? Khi nào bạn dùng nó?"
    *   **Trả lời:** "Singleton là một Creational pattern đảm bảo một lớp chỉ có một instance duy nhất và cung cấp một điểm truy cập toàn cục. Em dùng nó cho các trường hợp cần quản lý tài nguyên chia sẻ như Cấu hình (Configuration), Connection Pool, hoặc Logger để đảm bảo tính nhất quán và tiết kiệm tài nguyên."
*   **Câu hỏi:** "Làm thế nào để tạo một Singleton thread-safe?"
    *   **Trả lời:** "Có vài cách. Cách đơn giản nhất là Eager Initialization (tạo instance ngay khi khai báo). Tuy nhiên, để tối ưu, em thường dùng Double-Checked Locking. Nó kiểm tra instance hai lần, với lần thứ hai nằm trong một khối `synchronized`, để vừa đảm bảo thread-safe vừa có hiệu năng tốt."
*   **Câu hỏi:** "Singleton có nhược điểm gì không?"
    *   **Trả lời:** "Có ạ. Nó có thể vi phạm Single Responsibility Principle, làm code khó test vì trạng thái toàn cục, và tạo ra coupling cao. Trong các framework hiện đại như Spring, em ưu tiên để IoC container quản lý scope của bean là Singleton hơn là tự implement."

