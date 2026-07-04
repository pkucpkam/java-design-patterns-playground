# Builder Pattern

## 1. Tổng quan

*   **Định nghĩa:** Builder là một mẫu thiết kế thuộc nhóm Creational, cho phép bạn xây dựng một đối tượng phức tạp từng bước một. Pattern này cho phép bạn tạo ra các biểu diễn (representations) khác nhau của một đối tượng bằng cách sử dụng cùng một quy trình xây dựng.
*   **Vấn đề giải quyết:**
    *   **"Telescoping Constructor" Hell:** Tránh việc phải tạo quá nhiều constructor với các tham số khác nhau. Khi một đối tượng có nhiều thuộc tính tùy chọn, bạn sẽ phải tạo nhiều constructor (ví dụ: `User(id)`, `User(id, name)`, `User(id, name, email)`,...), rất khó quản lý và dễ gây lỗi.
    *   **Tăng tính dễ đọc (Readability):** Thay vì một hàm khởi tạo với 10 tham số không rõ ràng (`new House(10, 4, 2, true, false, ...)`), Builder pattern cho phép bạn thiết lập các thuộc tính một cách tường minh qua các phương thức có tên rõ ràng (`.withWindows(10).withDoors(4)...`).
    *   **Đảm bảo tính bất biến (Immutability):** Builder giúp bạn tạo ra một đối tượng bất biến (immutable) một cách dễ dàng. Sau khi đối tượng được `build()`, trạng thái của nó không thể thay đổi.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn đi đặt một chiếc Pizza. Một chiếc Pizza có thể có rất nhiều thành phần tùy chọn: cỡ bánh, loại vỏ, phô mai, sốt, và các loại topping (thịt, rau, hải sản...).

*   Nếu dùng constructor, bạn sẽ có một cơn ác mộng: `new Pizza("Large", "Thin", true, "Tomato", "Pepperoni", "Mushroom", ...)`
*   Thay vào đó, người bán hàng (Builder) sẽ hỏi bạn từng bước:
    1.  "Bạn muốn cỡ bánh nào?" -> `setSize("Large")`
    2.  "Loại vỏ gì?" -> `setCrust("Thin")`
    3.  "Thêm phô mai không?" -> `withExtraCheese()`
    4.  "Topping là gì?" -> `addTopping("Pepperoni")`, `addTopping("Mushroom")`
    5.  Cuối cùng, người bán hàng "làm" chiếc bánh theo yêu cầu của bạn -> `build()`

Quy trình này cho phép tạo ra vô số loại pizza khác nhau nhưng vẫn giữ được sự rõ ràng và có cấu trúc.

## 3. Khi nào nên dùng

*   **Khi cần tạo một đối tượng phức tạp với nhiều thuộc tính tùy chọn:** Đây là trường hợp sử dụng phổ biến nhất. Ví dụ: tạo đối tượng `User`, `Configuration`, `HttpRequest`.
*   **Khi bạn muốn quá trình khởi tạo đối tượng phải diễn ra theo nhiều bước:** Builder cho phép bạn trì hoãn việc tạo đối tượng cho đến khi tất cả các bước cấu hình hoàn tất.
*   **Khi bạn cần tạo ra các biểu diễn khác nhau của cùng một đối tượng:** Cùng một `Builder` có thể được sử dụng để tạo ra các đối tượng `Product` có cấu hình khác nhau. Ví dụ, một `CarBuilder` có thể tạo ra `SportsCar` hoặc `FamilyCar`.
*   **Khi cần tạo đối tượng Immutable:** Builder là cách tuyệt vời để tạo các đối tượng bất biến, đảm bảo thread-safety và tính toàn vẹn dữ liệu.

## 4. Khi nào KHÔNG nên dùng

*   **Khi đối tượng đơn giản và có ít thuộc tính:** Nếu một đối tượng chỉ có 2-3 thuộc tính bắt buộc, việc dùng Builder sẽ làm tăng số lượng lớp và sự phức tạp không cần thiết. Một constructor thông thường là đủ.
*   **Khi các thuộc tính không có giá trị mặc định và đều là bắt buộc:** Trong trường hợp này, một constructor yêu cầu tất cả các tham số sẽ rõ ràng và an toàn hơn.

## 5. Cách hoạt động

1.  **Product:** Đối tượng phức tạp cần được tạo. (Ví dụ: `HttpRequest`).
2.  **Builder Interface:** Định nghĩa tất cả các bước cần thiết để xây dựng `Product`. Mỗi bước là một phương thức để thiết lập một thuộc tính. (Ví dụ: `setHeader()`, `setBody()`, `setMethod()`). Cuối cùng là phương thức `build()` hoặc `getResult()`.
3.  **Concrete Builder:** Implement Builder Interface. Lớp này sẽ chứa một instance của `Product` và thực hiện việc xây dựng nó từng bước. Nó thường có các phương thức trả về chính nó (`return this;`) để cho phép "method chaining" (gọi nối tiếp).
4.  **Director (Tùy chọn):** Một lớp Director định nghĩa thứ tự gọi các bước xây dựng. Director nhận một đối tượng builder và thực thi các bước cần thiết. Nó hữu ích khi có những quy trình xây dựng phổ biến và bạn muốn tái sử dụng chúng.

**Flow hoạt động (không có Director):**
Client -> `new ConcreteBuilder()` -> `builder.stepA()` -> `builder.stepB()` -> `Product p = builder.build()`

**Flow hoạt động (có Director):**
Client -> `new ConcreteBuilder()` -> `Director.construct(builder)` -> `Product p = builder.build()`

## 6. Code ví dụ (Java)

Cách triển khai phổ biến nhất là sử dụng một lớp static lồng (nested static class) làm Builder.

```java
// File: examples/java/creational/BuilderDemo.java
package creational;

// 1. The Product class (often immutable)
class HttpRequest {
    private final String method;
    private final String url;
    private final String body;
    private final String headers;
    private final int timeout;

    // Private constructor, only accessible by the Builder
    private HttpRequest(Builder builder) {
        this.method = builder.method;
        this.url = builder.url;
        this.body = builder.body;
        this.headers = builder.headers;
        this.timeout = builder.timeout;
    }
    
    @Override
    public String toString() {
        return "HttpRequest{" + "method='" + method + '\'' + ", url='" + url + '\'' + 
               ", headers='" + headers + '\'' + ", body='" + body + '\'' + 
               ", timeout=" + timeout + '}';
    }

    // 2. The static nested Builder class
    public static class Builder {
        // Required parameters
        private final String method;
        private final String url;

        // Optional parameters - initialized to default values
        private String body = "";
        private String headers = "Content-Type: application/json";
        private int timeout = 30; // seconds

        public Builder(String method, String url) {
            this.method = method;
            this.url = url;
        }

        // --- Setter methods for optional parameters ---
        // They return the builder instance to allow for method chaining
        public Builder withBody(String body) {
            this.body = body;
            return this;
        }

        public Builder withHeaders(String headers) {
            this.headers = headers;
            return this;
        }
        
        public Builder withTimeout(int timeout) {
            this.timeout = timeout;
            return this;
        }

        // The final build method that returns the constructed object
        public HttpRequest build() {
            // Can add validation here before creating the object
            if (url == null || url.isEmpty()) {
                throw new IllegalStateException("URL cannot be null or empty.");
            }
            return new HttpRequest(this);
        }
    }
}

// --- Demo ---
public class BuilderDemo {
    public static void main(String[] args) {
        // Example 1: Creating a simple GET request
        HttpRequest getRequest = new HttpRequest.Builder("GET", "https://api.example.com/users")
                .withTimeout(10)
                .build();
        System.out.println("GET Request: " + getRequest);

        // Example 2: Creating a complex POST request using method chaining
        HttpRequest postRequest = new HttpRequest.Builder("POST", "https://api.example.com/users")
                .withHeaders("Authorization: Bearer my-token, Content-Type: application/json")
                .withBody("{ \"name\": \"John Doe\" }")
                .withTimeout(20)
                .build();
        System.out.println("POST Request: " + postRequest);
        
        // Example 3: Trying to build without a required parameter (will fail at compile time)
        // HttpRequest invalidRequest = new HttpRequest.Builder().build(); // Compile error
        
        // Example 4: Trying to build with invalid state (will fail at runtime)
        try {
            new HttpRequest.Builder("GET", "").build();
        } catch (IllegalStateException e) {
            System.out.println("Error building request: " + e.getMessage());
        }
    }
}
```

## 7. Ứng dụng trong Spring Boot

*   **`@Builder` Annotation (Lombok):** Trong thực tế, ít ai tự viết Builder bằng tay. Thư viện **Lombok** cung cấp annotation `@Builder` cực kỳ mạnh mẽ, tự động sinh ra toàn bộ code của Builder pattern cho bạn. Đây là cách làm phổ biến nhất trong các dự án Spring Boot.

    ```java
    import lombok.Builder;
    import lombok.ToString;

    @Builder
    @ToString
    public class User {
        private final Long id;
        private final String username;
        private final String email;
        private final boolean isActive;
    }

    // --- How to use it ---
    User user = User.builder()
                    .id(1L)
                    .username("john.doe")
                    .email("john.doe@example.com")
                    .isActive(true)
                    .build();
    ```

*   **`RestTemplateBuilder` và `WebClient.Builder`:** Spring cung cấp các lớp builder để cấu hình các HTTP client.
    *   `RestTemplateBuilder`: Dùng để tạo và cấu hình một `RestTemplate` với các interceptor, message converter, timeout...
    *   `WebClient.Builder`: Dùng để tạo và cấu hình `WebClient` (non-blocking HTTP client) một cách tường minh.

    ```java
    // Example with RestTemplateBuilder
    @Service
    public class MyService {
        private final RestTemplate restTemplate;

        @Autowired
        public MyService(RestTemplateBuilder builder) {
            this.restTemplate = builder
                .setConnectTimeout(Duration.ofSeconds(10))
                .setReadTimeout(Duration.ofSeconds(10))
                .build();
        }
    }
    ```

*   **`HttpSecurity` trong Spring Security:** Khi cấu hình Spring Security, bạn sử dụng một builder (`HttpSecurity`) để xây dựng chuỗi filter bảo mật một cách linh hoạt và dễ đọc.

    ```java
    @Configuration
    public class SecurityConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests() // returns a builder
                    .antMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
                .and() // returns the original HttpSecurity builder
                .formLogin()
                    .loginPage("/login").permitAll()
                .and()
                .logout().permitAll();
        }
    }
    ```

## 8. So sánh

*   **Builder vs. Abstract Factory:**
    *   Builder xây dựng **một đối tượng phức tạp** từng bước.
    *   Abstract Factory tạo ra một **họ các đối tượng đơn giản hoặc phức tạp**.
*   **Builder vs. Fluent Interface:**
    *   Builder là một pattern cụ thể để tạo đối tượng. Fluent Interface là một khái niệm rộng hơn về API design, nơi các phương thức được gọi nối tiếp nhau (method chaining) để tăng tính dễ đọc.
    *   Builder pattern thường implement một Fluent Interface, nhưng không phải mọi Fluent Interface đều là Builder.

## 9. Interview Tips

*   **Câu hỏi:** "Builder pattern là gì? Nó giải quyết vấn đề 'telescoping constructor' như thế nào?"
    *   **Trả lời:** "Builder là một Creational Pattern giúp xây dựng đối tượng phức tạp từng bước. Thay vì tạo nhiều constructor với các tham số khác nhau, ta dùng một lớp Builder riêng. Lớp này cho phép ta thiết lập các thuộc tính một cách tường minh qua các phương thức, sau đó gọi `build()` để tạo đối tượng. Cách này giúp code dễ đọc hơn và tránh lỗi khi truyền sai thứ tự tham số."
*   **Câu hỏi:** "Lợi ích của việc dùng Builder để tạo đối tượng immutable là gì?"
    *   **Trả lời:** "Bằng cách làm cho constructor của đối tượng là `private` và chỉ cho phép Builder gọi nó, ta có thể đảm bảo rằng sau khi đối tượng được tạo qua phương thức `build()`, trạng thái của nó không thể bị thay đổi từ bên ngoài. Điều này rất quan trọng để tạo ra các đối tượng thread-safe và đảm bảo tính toàn vẹn của dữ liệu."
*   **Câu hỏi:** "Bạn thường dùng Builder trong Spring Boot như thế nào?"
    *   **Trả lời:** "Trong thực tế, em thường dùng annotation `@Builder` của Lombok để tự động sinh code. Ngoài ra, em cũng thường xuyên sử dụng các builder có sẵn của Spring như `RestTemplateBuilder` để cấu hình HTTP client, hoặc `HttpSecurity` để cấu hình Spring Security."

