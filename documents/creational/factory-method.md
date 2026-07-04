# Factory Method Pattern

## 1. Tổng quan

*   **Định nghĩa:** Factory Method là một mẫu thiết kế thuộc nhóm Creational, định nghĩa một giao diện (interface) để tạo ra một đối tượng, nhưng để các lớp con (subclasses) quyết định lớp (class) nào sẽ được khởi tạo. Factory Method cho phép một lớp ủy quyền việc khởi tạo cho các lớp con của nó.
*   **Vấn đề giải quyết:**
    *   **Giảm sự phụ thuộc (Decoupling):** Giúp mã nguồn của bạn không bị phụ thuộc cứng vào các lớp cụ thể (concrete classes). Thay vì `new ProductA()`, bạn sẽ gọi một phương thức `createProduct()`, và lớp con sẽ quyết định trả về `ProductA` hay `ProductB`.
    *   **Tăng tính mở rộng:** Khi cần thêm một loại sản phẩm mới (ví dụ: `ProductC`), bạn chỉ cần tạo một lớp con mới và override lại Factory Method mà không cần sửa đổi mã nguồn ở nơi gọi (client code).

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn có một công ty giao vận. Công ty này có thể giao hàng bằng **xe tải (Truck)** hoặc **tàu thủy (Ship)**.

*   **Lớp cha (Creator):** là `Logistics` (Công ty Giao vận). Nó có một phương thức trừu tượng là `createTransport()`. Lớp này không biết chính xác sẽ dùng phương tiện gì, nó chỉ biết rằng "cần phải tạo một phương tiện vận chuyển".
*   **Các lớp con (Concrete Creators):** là `RoadLogistics` (Giao vận đường bộ) và `SeaLogistics` (Giao vận đường biển).
    *   `RoadLogistics` sẽ implement `createTransport()` để trả về một đối tượng `Truck`.
    *   `SeaLogistics` sẽ implement `createTransport()` để trả về một đối tượng `Ship`.

Khi một khách hàng yêu cầu giao hàng, tùy vào loại hình giao vận họ chọn (đường bộ hay đường biển), hệ thống sẽ sử dụng "nhà máy" tương ứng (`RoadLogistics` hoặc `SeaLogistics`) để tạo ra phương tiện phù hợp.

## 3. Khi nào nên dùng

*   **Khi bạn không biết trước loại đối tượng cần tạo:** Khi một lớp cần tạo các đối tượng nhưng không thể biết trước chính xác loại và sự phụ thuộc của chúng. Factory Method cho phép bạn ủy quyền logic khởi tạo này cho các lớp con.
*   **Khi bạn muốn cung cấp cho người dùng một cách để mở rộng thư viện hoặc framework của bạn:** Ví dụ, bạn đang viết một framework UI. Bạn có thể cung cấp một lớp `UIFramework` với một factory method là `createButton()`. Người dùng framework có thể kế thừa và tạo ra `MyFancyFramework` và override `createButton()` để trả về một `FancyButton` tùy chỉnh của riêng họ.
*   **Khi bạn muốn tái sử dụng các đối tượng đã có thay vì tạo mới (tiết kiệm tài nguyên):** Factory method có thể được dùng để trả về các đối tượng từ một pool hoặc cache, thay vì lúc nào cũng tạo mới.

## 4. Khi nào KHÔNG nên dùng

*   **Khi hệ thống của bạn đơn giản:** Nếu hệ thống chỉ có một vài loại đối tượng và logic khởi tạo không phức tạp, việc áp dụng Factory Method có thể làm tăng số lượng lớp không cần thiết, khiến code cồng kềnh hơn (Over-engineering).
*   **Khi các lớp cụ thể không thay đổi:** Nếu bạn chắc chắn rằng mình sẽ không cần thêm các loại sản phẩm mới trong tương lai, việc sử dụng `new` trực tiếp có thể đơn giản và dễ đọc hơn.

## 5. Cách hoạt động

1.  **Product Interface:** Định nghĩa một interface chung cho tất cả các đối tượng (sản phẩm) mà factory method có thể tạo ra. (Ví dụ: `Transport`).
2.  **Concrete Products:** Các lớp cụ thể implement Product interface. (Ví dụ: `Truck`, `Ship`).
3.  **Creator Class:**
    *   Khai báo một factory method trừu tượng (`abstract`) hoặc có một cài đặt mặc định. Phương thức này trả về một đối tượng thuộc Product interface.
    *   Lớp này có thể chứa các logic nghiệp vụ khác sử dụng đối tượng Product.
4.  **Concrete Creators:**
    *   Kế thừa từ Creator class.
    *   Override factory method để trả về một instance của một Concrete Product cụ thể.

**Flow hoạt động:**
Client code muốn tạo một `Product`. Nó không gọi `new ConcreteProduct()` mà gọi `creator.factoryMethod()`. `creator` ở đây là một `ConcreteCreator` nào đó, và `factoryMethod` của nó sẽ quyết định `new ConcreteProductA()` hay `new ConcreteProductB()`.

## 6. Code ví dụ (Java)

```java
// File: examples/java/creational/FactoryMethodDemo.java

package creational;

// 1. Product Interface
interface PaymentGateway {
    void processPayment(double amount);
}

// 2. Concrete Products
class PayPalGateway implements PaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " through PayPal.");
    }
}

class StripeGateway implements PaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " through Stripe.");
    }
}

class VnPayGateway implements PaymentGateway {
    @Override
    public void processPayment(double amount) {
        System.out.println("Processing payment of $" + amount + " through VNPay.");
    }
}

// 3. Creator Class
abstract class PaymentGatewayFactory {
    // The Factory Method
    public abstract PaymentGateway createPaymentGateway();

    public void processOrder(double amount) {
        PaymentGateway gateway = createPaymentGateway();
        gateway.processPayment(amount);
    }
}

// 4. Concrete Creators
class PayPalFactory extends PaymentGatewayFactory {
    @Override
    public PaymentGateway createPaymentGateway() {
        // Returns a specific product
        return new PayPalGateway();
    }
}

class StripeFactory extends PaymentGatewayFactory {
    @Override
    public PaymentGateway createPaymentGateway() {
        return new StripeGateway();
    }
}

class VnPayFactory extends PaymentGatewayFactory {
    @Override
    public PaymentGateway createPaymentGateway() {
        return new VnPayGateway();
    }
}


// --- Demo ---
public class FactoryMethodDemo {
    public static void main(String[] args) {
        PaymentGatewayFactory factory;
        String paymentMethod = "vnpay"; // This could come from user input or config

        // The client code doesn't know which concrete factory it's using.
        if ("paypal".equalsIgnoreCase(paymentMethod)) {
            factory = new PayPalFactory();
        } else if ("stripe".equalsIgnoreCase(paymentMethod)) {
            factory = new StripeFactory();
        } else if ("vnpay".equalsIgnoreCase(paymentMethod)) {
            factory = new VnPayFactory();
        } else {
            throw new IllegalArgumentException("Unknown payment method");
        }
        
        // Use the factory to process an order
        factory.processOrder(100.50);
    }
}
```

## 7. Ứng dụng trong Spring Boot

Factory Method không được thể hiện rõ ràng như Singleton trong Spring, nhưng triết lý của nó lại nằm ở nhiều nơi:

*   **`FactoryBean` Interface:** Đây là một trong những ví dụ điển hình nhất. Khi bạn implement interface `FactoryBean<T>`, bạn đang tạo ra một "nhà máy" cho bean. Thay vì Spring tự tạo bean từ lớp của bạn, nó sẽ gọi phương thức `getObject()` trong `FactoryBean` của bạn để lấy bean.
    *   **Sử dụng:** Hữu ích khi logic khởi tạo bean rất phức tạp, ví dụ như cần kết nối đến một dịch vụ ngoài để lấy thông tin cấu hình trước khi tạo đối tượng.

    ```java
    // Ví dụ về FactoryBean
    import org.springframework.beans.factory.FactoryBean;
    import org.springframework.stereotype.Component;

    // A complex class that is hard to instantiate directly
    class ComplicatedObject {
        public ComplicatedObject(String config) { /* ... */ }
    }
    
    @Component
    public class ComplicatedObjectFactoryBean implements FactoryBean<ComplicatedObject> {

        @Override
        public ComplicatedObject getObject() throws Exception {
            // Complex logic to create the object
            String config = "read-from-remote-service";
            return new ComplicatedObject(config);
        }

        @Override
        public Class<?> getObjectType() {
            return ComplicatedObject.class;
        }

        @Override
        public boolean isSingleton() {
            return true;
        }
    }
    ```

*   **`@Configuration` và `@Bean`:** Khi bạn sử dụng các phương thức được chú thích `@Bean` trong một lớp `@Configuration`, mỗi phương thức đó hoạt động như một factory method. Spring sẽ gọi các phương thức này để lấy các bean instance.

    ```java
    @Configuration
    public class AppConfig {

        @Bean // This is a factory method
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

        @Bean // Another factory method
        public ObjectMapper objectMapper() {
            ObjectMapper mapper = new ObjectMapper();
            // custom configuration
            return mapper;
        }
    }
    ```

## 8. So sánh

*   **Factory Method vs. Abstract Factory:**
    *   **Mục tiêu:** Factory Method tạo ra **một sản phẩm** (one product). Abstract Factory tạo ra một **họ các sản phẩm liên quan** (a family of related products).
    *   **Cách implement:** Factory Method thường được implement bằng một phương thức trong một lớp cha. Abstract Factory thường được implement bằng một interface với nhiều phương thức, mỗi phương thức là một factory method để tạo một sản phẩm trong họ.
*   **Factory Method vs. Simple Factory (không phải là một pattern chính thức của GoF):**
    *   Simple Factory là một lớp có một phương thức nhận một tham số và trả về một trong nhiều loại sản phẩm. Nó không sử dụng kế thừa.
    *   Factory Method sử dụng kế thừa, các lớp con sẽ quyết định sản phẩm nào được tạo. Factory Method linh hoạt và dễ mở rộng hơn.

## 9. Interview Tips

*   **Câu hỏi:** "Factory Method là gì và nó giải quyết vấn đề gì?"
    *   **Trả lời:** "Factory Method là một Creational Pattern cho phép một interface định nghĩa cách tạo đối tượng, nhưng để các lớp con quyết định lớp cụ thể nào sẽ được tạo. Nó giúp giảm sự phụ thuộc vào các lớp cụ thể và tăng khả năng mở rộng, vì em có thể thêm sản phẩm mới mà không cần sửa code ở nơi gọi."
*   **Câu hỏi:** "Hãy so sánh Factory Method và Abstract Factory."
    *   **Trả lời:** "Factory Method tập trung vào việc tạo ra một đối tượng duy nhất thông qua kế thừa. Còn Abstract Factory tập trung vào việc tạo ra một gia đình các đối tượng liên quan với nhau thông qua composition (sử dụng một đối tượng factory). Dùng Abstract Factory khi cần đảm bảo các sản phẩm tạo ra tương thích với nhau, ví dụ như bộ theme UI (Button, Checkbox cho Windows phải đi chung với nhau)."
*   **Câu hỏi:** "Bạn đã thấy Factory Method được dùng ở đâu trong Spring?"
    *   **Trả lời:** "Triết lý của nó được thể hiện rõ nhất qua interface `FactoryBean`, nơi phương thức `getObject()` hoạt động như một factory method để tạo ra các bean phức tạp. Ngoài ra, các phương thức được đánh dấu `@Bean` trong lớp `@Configuration` cũng có thể được xem là các factory method."

