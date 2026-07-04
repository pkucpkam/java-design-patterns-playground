# Chain of Responsibility Pattern

## 1. Tổng quan

*   **Định nghĩa:** Chain of Responsibility là một mẫu thiết kế thuộc nhóm Behavioral, cho phép bạn truyền một yêu cầu (request) qua một chuỗi các đối tượng xử lý (handlers). Mỗi handler sẽ quyết định hoặc xử lý yêu cầu đó, hoặc chuyển nó cho handler tiếp theo trong chuỗi.
*   **Vấn đề giải quyết:**
    *   **Giảm sự phụ thuộc (Decoupling):** Tách biệt người gửi yêu cầu (sender) và người nhận (receivers). Người gửi không cần biết handler cụ thể nào sẽ xử lý yêu cầu của mình, nó chỉ cần gửi yêu cầu vào chuỗi.
    *   **Tăng tính linh hoạt:** Dễ dàng thêm hoặc bớt các handler trong chuỗi, hoặc thay đổi thứ tự của chúng mà không ảnh hưởng đến code của người gửi.
    *   **Tuân thủ Single Responsibility Principle:** Mỗi handler chỉ chịu trách nhiệm cho một loại xử lý cụ thể.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn là một nhân viên và bạn cần xin duyệt một khoản chi tiêu. Quy trình duyệt sẽ đi qua một chuỗi các cấp quản lý:

1.  Bạn gửi yêu cầu đến **Quản lý trực tiếp (Direct Manager)**.
2.  Nếu khoản chi tiêu nhỏ (ví dụ: < $500), quản lý trực tiếp sẽ duyệt và kết thúc quy trình.
3.  Nếu không, yêu cầu sẽ được chuyển lên cho **Trưởng phòng (Department Head)**.
4.  Nếu khoản chi tiêu ở mức vừa (ví dụ: < $5000), trưởng phòng sẽ duyệt.
5.  Nếu không, yêu cầu tiếp tục được chuyển lên cho **Giám đốc (CEO)**. CEO là người cuối cùng trong chuỗi và sẽ đưa ra quyết định cuối cùng.

Mỗi cấp quản lý là một **handler** trong chuỗi. Yêu cầu sẽ được "truyền tay" cho đến khi có người xử lý nó hoặc đến cuối chuỗi.

## 3. Khi nào nên dùng

*   **Khi bạn muốn nhiều đối tượng có cơ hội xử lý một yêu cầu:** Nhưng bạn không biết trước đối tượng nào sẽ xử lý nó.
*   **Khi thứ tự xử lý yêu cầu là quan trọng:** Chuỗi định nghĩa thứ tự mà các handler sẽ nhận yêu cầu.
*   **Khi tập hợp các handler và thứ tự của chúng có thể thay đổi lúc runtime:** Bạn có thể linh hoạt cấu hình chuỗi xử lý.
*   **Trong các hệ thống xử lý request/middleware:** Rất phổ biến trong các web server và framework, nơi một HTTP request đi qua một chuỗi các middleware (logging, authentication, caching,...) trước khi đến controller.

## 4. Khi nào KHÔNG nên dùng

*   **Khi yêu cầu luôn phải được xử lý bởi một handler cụ thể:** Nếu không có sự "truyền tay" nào, pattern này là không cần thiết.
*   **Khi chuỗi có thể quá dài và không được quản lý tốt:** Một chuỗi quá dài có thể gây ra vấn đề về hiệu năng do yêu cầu phải đi qua nhiều bước không cần thiết.
*   **Khi yêu cầu có thể không được xử lý:** Nếu không có handler nào trong chuỗi xử lý yêu cầu và cũng không có xử lý mặc định ở cuối chuỗi, yêu cầu đó có thể bị "rơi vào khoảng không".

## 5. Cách hoạt động

1.  **Handler Interface:**
    *   Định nghĩa một phương thức để xử lý yêu cầu (ví dụ: `handleRequest()`).
    *   (Tùy chọn nhưng phổ biến) Định nghĩa một phương thức để thiết lập handler tiếp theo trong chuỗi (`setNext()`).
2.  **Abstract Handler (Tùy chọn):**
    *   Một lớp trừu tượng implement Handler Interface.
    *   Nó chứa một tham chiếu đến handler tiếp theo (`nextHandler`).
    *   Nó cung cấp một implement mặc định cho việc chuyển yêu cầu: nếu handler hiện tại không thể xử lý, nó sẽ gọi `nextHandler.handleRequest()`.
3.  **Concrete Handlers:**
    *   Kế thừa từ Handler Interface hoặc Abstract Handler.
    *   Implement logic xử lý của riêng nó.
    *   Quyết định xem có nên xử lý yêu cầu hay không. Nếu không, nó sẽ gọi phương thức xử lý của handler kế tiếp trong chuỗi.
4.  **Client:**
    *   Tạo ra các handler và xây dựng chúng thành một chuỗi.
    *   Gửi yêu cầu vào handler đầu tiên của chuỗi.

## 6. Code ví dụ (Java)

```java
// File: examples/java/behavioral/ChainOfResponsibilityDemo.java
package behavioral;

// The request class
class Request {
    private final RequestType type;
    private final double amount;

    public Request(RequestType type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public RequestType getType() { return type; }
    public double getAmount() { return amount; }
}

enum RequestType {
    PURCHASE, EXPENSE_REPORT
}


// 1. Handler Interface
// Using an abstract class is common to provide default chaining behavior.
abstract class Approver {
    protected Approver nextApprover;

    public void setNext(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    public abstract void processRequest(Request request);
}

// 3. Concrete Handlers
class Manager extends Approver {
    @Override
    public void processRequest(Request request) {
        if (request.getType() == RequestType.PURCHASE && request.getAmount() < 500) {
            System.out.println("Manager approves purchase of $" + request.getAmount());
        } else if (nextApprover != null) {
            System.out.println("Manager cannot approve. Passing to Department Head.");
            nextApprover.processRequest(request);
        }
    }
}

class DepartmentHead extends Approver {
    @Override
    public void processRequest(Request request) {
        if (request.getType() == RequestType.PURCHASE && request.getAmount() < 5000) {
            System.out.println("Department Head approves purchase of $" + request.getAmount());
        } else if (nextApprover != null) {
            System.out.println("Department Head cannot approve. Passing to CEO.");
            nextApprover.processRequest(request);
        }
    }
}

class CEO extends Approver {
    @Override
    public void processRequest(Request request) {
        // The CEO is the final approver and can handle any request.
        System.out.println("CEO approves purchase of $" + request.getAmount());
    }
}

// --- Demo ---
public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        // 4. Client builds the chain of responsibility
        Approver manager = new Manager();
        Approver head = new DepartmentHead();
        Approver ceo = new CEO();

        manager.setNext(head);
        head.setNext(ceo);

        // Client sends requests to the first handler in the chain
        System.out.println("--- Sending a request for $300 ---");
        manager.processRequest(new Request(RequestType.PURCHASE, 300));
        
        System.out.println("\n--- Sending a request for $2500 ---");
        manager.processRequest(new Request(RequestType.PURCHASE, 2500));
        
        System.out.println("\n--- Sending a request for $12000 ---");
        manager.processRequest(new Request(RequestType.PURCHASE, 12000));
    }
}

```

## 7. Ứng dụng trong Spring Boot

Chain of Responsibility là pattern nền tảng của **Spring Security** và **Servlet Filters**.

*   **Spring Security Filter Chain:**
    *   Mỗi request gửi đến một ứng dụng Spring Boot được bảo vệ bởi Spring Security sẽ phải đi qua một chuỗi các filter (`FilterChain`).
    *   Mỗi filter trong chuỗi là một **handler**, chịu trách nhiệm cho một khía cạnh bảo mật cụ thể:
        *   `CsrfFilter`: Kiểm tra token chống tấn công CSRF.
        *   `UsernamePasswordAuthenticationFilter`: Xử lý xác thực từ form đăng nhập.
        *   `BearerTokenAuthenticationFilter`: Xử lý xác thực từ JWT token.
        *   `AuthorizationFilter`: Kiểm tra xem người dùng đã được xác thực có quyền truy cập vào tài nguyên hay không.
    *   Một filter có thể xử lý request và kết thúc (ví dụ: trả về lỗi 401 Unauthorized) hoặc chuyển request cho filter tiếp theo trong chuỗi bằng cách gọi `filterChain.doFilter(request, response)`.

    ```java
    @Configuration
    @EnableWebSecurity
    public class SecurityConfig {
        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(authz -> authz
                    .requestMatchers("/public/**").permitAll()
                    .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtTokenFilter(), UsernamePasswordAuthenticationFilter.class); // Thêm handler vào chuỗi
            return http.build();
        }
    }
    ```

*   **Servlet Filters:**
    *   Tương tự Spring Security, bạn có thể tạo các `Filter` của riêng mình để thực hiện các tác vụ như logging, kiểm tra header, nén response...
    *   Bằng cách sử dụng `@Order`, bạn có thể kiểm soát vị trí của filter trong chuỗi.

    ```java
    @Component
    @Order(1) // This handler runs first
    public class LoggingFilter implements Filter {
        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
                throws IOException, ServletException {
            // Logic before passing to the next handler
            System.out.println("Logging request: " + ((HttpServletRequest) request).getRequestURI());

            // Pass the request along the filter chain
            chain.doFilter(request, response);

            // Logic after the request has been handled
        }
    }
    ```

## 8. So sánh

*   **Chain of Responsibility vs. Decorator:**
    *   Cả hai đều "bọc" và có thể truyền yêu cầu qua một chuỗi.
    *   **Mục đích:** Chain of Responsibility nhằm mục đích để **một** handler trong chuỗi xử lý yêu cầu. Decorator nhằm mục đích để **tất cả** các đối tượng trong chuỗi đều thêm hành vi của mình vào yêu cầu.
*   **Chain of Responsibility vs. Observer:**
    *   Trong CoR, yêu cầu được truyền tuần tự theo một chuỗi xác định.
    *   Trong Observer, thông báo được gửi đồng thời đến tất cả các observer, không có thứ tự cụ thể.

## 9. Interview Tips

*   **Câu hỏi:** "Chain of Responsibility là gì? Cho ví dụ."
    *   **Trả lời:** "Đây là một behavioral pattern cho phép truyền một yêu cầu qua một chuỗi các handler. Mỗi handler có thể xử lý yêu cầu hoặc chuyển nó cho handler tiếp theo. Ví dụ điển hình là chuỗi filter trong Spring Security. Một HTTP request đi qua các filter như `AuthenticationFilter`, `AuthorizationFilter`,... Mỗi filter xử lý một phần của request và quyết định có cho nó đi tiếp hay không."
*   **Câu hỏi:** "Lợi ích và nhược điểm của pattern này là gì?"
    *   **Trả lời:** "Lợi ích chính là giảm coupling giữa sender và receiver, và tăng tính linh hoạt trong việc cấu hình các handler. Nhược điểm là một yêu cầu có thể không được xử lý nếu không có handler nào phù hợp, và có thể ảnh hưởng đến hiệu năng nếu chuỗi quá dài."
*   **Câu hỏi:** "Bạn sẽ xây dựng một chuỗi handler trong Spring Boot như thế nào?"
    *   **Trả lời:** "Em có thể tạo một interface `Handler` và nhiều lớp `@Component` implement nó. Mỗi component có một tham chiếu `@Autowired` đến handler tiếp theo. Tuy nhiên, cách làm "Spring-native" hơn là sử dụng các `Filter` và dùng annotation `@Order` để sắp xếp thứ tự của chúng, hoặc trong Spring Security thì dùng `addFilterBefore`/`addFilterAfter` để chèn các custom filter vào đúng vị trí trong `FilterChain`."

