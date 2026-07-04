# Strategy Pattern

## 1. Tổng quan

*   **Định nghĩa:** Strategy là một mẫu thiết kế thuộc nhóm Behavioral, cho phép bạn định nghĩa một họ các thuật toán, đóng gói mỗi thuật toán vào một lớp riêng, và làm cho chúng có thể thay thế lẫn nhau. Strategy cho phép thuật toán thay đổi độc lập so với client sử dụng nó.
*   **Vấn đề giải quyết:**
    *   **Tránh dùng câu lệnh `if-else` hoặc `switch-case` dài dòng:** Khi bạn có một tác vụ có thể được thực hiện bằng nhiều cách khác nhau, thay vì dùng một khối `if-else` lớn để chọn cách thực hiện, bạn có thể dùng Strategy để tách mỗi "cách" ra một lớp riêng.
    *   **Tăng tính linh hoạt và mở rộng:** Dễ dàng thêm các thuật toán mới mà không cần sửa đổi code của client. Bạn chỉ cần tạo một lớp strategy mới.
    *   **Tuân thủ Open/Closed Principle:** Hệ thống "mở" cho việc mở rộng (thêm strategy mới) nhưng "đóng" cho việc sửa đổi (không cần sửa code client).

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn cần đi từ điểm A đến điểm B. Bạn có nhiều "chiến lược" (strategy) di chuyển khác nhau:

*   Đi bộ (`WalkingStrategy`)
*   Đi xe buýt (`BusStrategy`)
*   Đi xe taxi (`TaxiStrategy`)

Tùy vào hoàn cảnh (thời gian, tiền bạc, thời tiết), bạn sẽ chọn một chiến lược phù hợp.

*   **Context:** Là chính bạn, người cần di chuyển. Bạn có một phương thức `travel()`.
*   **Strategy Interface:** Là một interface `RouteStrategy` với một phương thức `findRoute()`.
*   **Concrete Strategies:** Là các lớp `WalkingStrategy`, `BusStrategy`, `TaxiStrategy`, mỗi lớp implement `findRoute()` theo cách riêng của nó.

Khi bạn muốn đi, bạn chỉ cần chọn một "chiến lược" và áp dụng nó: `context.setStrategy(new TaxiStrategy()); context.travel();`

## 3. Khi nào nên dùng

*   **Khi bạn có nhiều biến thể của một thuật toán:** Ví dụ, các thuật toán sắp xếp (Quick Sort, Bubble Sort), các phương thức thanh toán (Credit Card, PayPal, VNPay), các chiến lược nén file (ZIP, RAR).
*   **Khi bạn muốn client không cần biết về chi tiết implement của các thuật toán:** Client chỉ cần biết về interface của Strategy.
*   **Khi bạn muốn thay đổi thuật toán tại thời điểm chạy (runtime):** Context có thể thay đổi strategy của nó một cách linh hoạt.
*   **Khi một lớp có quá nhiều hành vi được quyết định bởi một câu lệnh điều kiện lớn:** Tách mỗi nhánh điều kiện ra một lớp strategy riêng sẽ làm code sạch hơn.

## 4. Khi nào KHÔNG nên dùng

*   **Khi chỉ có một hoặc hai thuật toán và chúng hiếm khi thay đổi:** Nếu số lượng thuật toán ít và ổn định, việc áp dụng Strategy có thể làm tăng số lượng lớp không cần thiết, gây cồng kềnh. Một câu lệnh `if-else` đơn giản có thể hiệu quả hơn.
*   **Khi các thuật toán không có cùng một interface:** Nếu logic và các tham số đầu vào/ra của các "chiến lược" quá khác nhau, việc ép chúng vào cùng một interface sẽ rất khó khăn.

## 5. Cách hoạt động

1.  **Context:**
    *   Lưu trữ một tham chiếu đến một đối tượng `Strategy`.
    *   Cung cấp một phương thức để client có thể thay đổi (set) `Strategy`.
    *   Không thực hiện công việc trực tiếp, mà ủy quyền cho đối tượng `Strategy` mà nó đang giữ.
2.  **Strategy Interface:**
    *   Định nghĩa một phương thức chung mà tất cả các Concrete Strategy phải implement. Đây là phương thức mà `Context` sẽ gọi.
3.  **Concrete Strategies:**
    *   Implement các thuật toán khác nhau theo Strategy Interface.
    *   Mỗi lớp là một "chiến lược" cụ thể.

**Flow hoạt động:**
Client -> `Tạo một Concrete Strategy` -> `Tạo một Context` -> `Context.setStrategy(concreteStrategy)` -> `Client gọi phương thức trên Context` -> `Context gọi phương thức trên Strategy đã được set`.

## 6. Code ví dụ (Java)

```java
// File: examples/java/behavioral/StrategyDemo.java
package behavioral;

import java.util.Arrays;
import java.util.List;

// 2. Strategy Interface
interface SortingStrategy {
    void sort(List<Integer> list);
}

// 3. Concrete Strategies
class BubbleSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("Sorting using Bubble Sort");
        // Simple bubble sort implementation
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    // swap
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}

class QuickSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("Sorting using Quick Sort");
        // In a real scenario, you would implement quick sort here.
        // For simplicity, we just use the collection's sort.
        list.sort(Integer::compareTo);
    }
}

// 1. Context
class SortedListContext {
    private SortingStrategy strategy;
    private List<Integer> list = Arrays.asList(5, 1, 4, 2, 8);

    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    public void sort() {
        if (strategy == null) {
            throw new IllegalStateException("Strategy not set!");
        }
        strategy.sort(list);
        System.out.println("Sorted list: " + list);
    }
}

// --- Demo ---
public class StrategyDemo {
    public static void main(String[] args) {
        SortedListContext context = new SortedListContext();

        // Use Bubble Sort for small datasets
        System.out.println("--- Client wants to use Bubble Sort ---");
        context.setStrategy(new BubbleSortStrategy());
        context.sort();
        
        System.out.println("\n--- Client changes mind, wants to use Quick Sort for larger datasets ---");
        // Change strategy at runtime
        context.setStrategy(new QuickSortStrategy());
        context.sort();
    }
}
```

## 7. Ứng dụng trong Spring Boot

Strategy Pattern là một trong những pattern được sử dụng rộng rãi và tự nhiên nhất trong Spring.

*   **`PasswordEncoder` trong Spring Security:** Spring Security cung cấp một interface `PasswordEncoder` và nhiều implementation khác nhau (`BCryptPasswordEncoder`, `Pbkdf2PasswordEncoder`, `Argon2PasswordEncoder`). Bạn chỉ cần định nghĩa một `Bean` của `PasswordEncoder` mà bạn muốn, và Spring sẽ tự động inject và sử dụng nó trong quá trình xác thực.

    ```java
    @Configuration
    public class SecurityConfig {
        @Bean // This bean defines the strategy
        public PasswordEncoder passwordEncoder() {
            // Here we choose the BCrypt strategy
            return new BCryptPasswordEncoder();
        }
    }
    ```

*   **Tự implement với `@Service` và `@Qualifier`:**
    Đây là cách phổ biến để implement Strategy pattern trong Spring.
    1.  Định nghĩa một interface.
    2.  Tạo nhiều lớp `@Service` implement interface đó. Mỗi service là một strategy.
    3.  Inject tất cả các strategy vào một `Map` hoặc `List` trong Context (ví dụ: một lớp `PaymentProcessor`).
    4.  Chọn strategy để sử dụng dựa trên một key (ví dụ: tên phương thức thanh toán).

    ```java
    // Strategy Interface
    public interface PaymentStrategy {
        String pay(double amount);
        PaymentMethod getPaymentMethod();
    }

    // Concrete Strategies
    @Service("paypal")
    public class PayPalStrategy implements PaymentStrategy {
        @Override public String pay(double amount) { /*...*/ }
        @Override public PaymentMethod getPaymentMethod() { return PaymentMethod.PAYPAL; }
    }
    
    @Service("vnpay")
    public class VnPayStrategy implements PaymentStrategy {
        @Override public String pay(double amount) { /*...*/ }
        @Override public PaymentMethod getPaymentMethod() { return PaymentMethod.VNPAY; }
    }
    
    public enum PaymentMethod { PAYPAL, VNPAY, STRIPE }

    // Context / Client
    @Service
    public class PaymentService {
        private final Map<String, PaymentStrategy> paymentStrategies;

        @Autowired
        public PaymentService(Map<String, PaymentStrategy> paymentStrategies) {
            this.paymentStrategies = paymentStrategies;
        }

        public String processPayment(PaymentMethod method, double amount) {
            // Select strategy from the map based on the bean name
            PaymentStrategy strategy = paymentStrategies.get(method.name().toLowerCase());
            if (strategy == null) {
                throw new IllegalArgumentException("Unsupported payment method: " + method);
            }
            return strategy.pay(amount);
        }
    }
    ```

## 8. So sánh

*   **Strategy vs. State:**
    *   Cả hai đều có cấu trúc tương tự (Context, State/Strategy interface, Concrete States/Strategies).
    *   **Mục đích khác nhau:** Strategy tập trung vào **cách** một tác vụ được thực hiện (thuật toán). Client thường biết về strategy và chủ động thay đổi nó. State tập trung vào **trạng thái** của đối tượng và hành vi của nó thay đổi như thế nào khi trạng thái thay đổi. Sự thay đổi trạng thái thường do chính Context hoặc các State tự quản lý, client không can thiệp.
*   **Strategy vs. Template Method:**
    *   Strategy sử dụng **composition** (Context chứa một Strategy).
    *   Template Method sử dụng **kế thừa** (lớp con override các bước của một thuật toán khung).
    *   Strategy có thể thay đổi lúc runtime, Template Method thì không (được quyết định lúc compile time).

## 9. Interview Tips

*   **Câu hỏi:** "Strategy pattern là gì? Cho một ví dụ thực tế."
    *   **Trả lời:** "Strategy là một behavioral pattern cho phép đóng gói các thuật toán khác nhau vào các lớp riêng và làm chúng có thể thay thế lẫn nhau. Ví dụ điển hình là các phương thức thanh toán trong một trang thương mại điện tử. Thay vì dùng if-else để chọn giữa thanh toán qua thẻ, qua PayPal hay VNPay, ta tạo một interface `PaymentStrategy` và các lớp implement riêng cho từng phương thức. Client chỉ cần chọn strategy phù hợp và thực thi."
*   **Câu hỏi:** "Bạn sẽ implement Strategy pattern trong Spring Boot như thế nào?"
    *   **Trả lời:** "Cách phổ biến là tạo một interface, sau đó tạo nhiều `@Service` implement interface đó, mỗi service là một strategy. Em sẽ inject tất cả các service này vào một `Map` trong lớp context. Key của map là tên của bean (định nghĩa qua `@Service("beanName")`), value là instance của strategy. Khi cần, em sẽ dựa vào một tham số đầu vào để lấy strategy tương ứng từ Map và thực thi."
*   **Câu hỏi:** "So sánh Strategy và State pattern."
    *   **Trả lời:** "Chúng có cấu trúc giống nhau nhưng mục đích khác nhau. Strategy xử lý *cách* một đối tượng thực hiện một việc gì đó và client thường chủ động chọn strategy. State xử lý *trạng thái* của một đối tượng và hành vi của nó thay đổi theo trạng thái. Việc chuyển đổi trạng thái thường do chính đối tượng quản lý, client không cần biết."

