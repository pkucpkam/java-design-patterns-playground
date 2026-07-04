# Abstract Factory Pattern

## 1. Tổng quan

*   **Định nghĩa:** Abstract Factory là một mẫu thiết kế thuộc nhóm Creational, cung cấp một giao diện (interface) để tạo ra một **họ các đối tượng có liên quan hoặc phụ thuộc lẫn nhau** mà không cần chỉ định các lớp cụ thể (concrete classes) của chúng.
*   **Vấn đề giải quyết:**
    *   **Tạo ra các sản phẩm tương thích:** Đảm bảo rằng các đối tượng được tạo ra bởi cùng một factory sẽ tương thích với nhau. Ví dụ, một `WindowsButton` nên được sử dụng cùng với `WindowsCheckbox`, không phải `MacCheckbox`.
    *   **Tách biệt client và concrete classes:** Client code chỉ làm việc với các interface của factory và sản phẩm, hoàn toàn không biết về các lớp implement cụ thể. Điều này giúp bạn dễ dàng thay đổi cả một họ sản phẩm mà không ảnh hưởng đến client.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn đang trang trí nội thất cho một ngôi nhà. Bạn có thể chọn nhiều phong cách khác nhau như **Cổ điển (Victorian)** hoặc **Hiện đại (Modern)**.

*   Mỗi phong cách là một **họ sản phẩm** bao gồm: `Ghế (Chair)`, `Bàn (Table)`, `Sofa`.
*   Một `Ghế Cổ điển` phải đi cùng với `Bàn Cổ điển` và `Sofa Cổ điển` để tạo nên sự nhất quán.

**Abstract Factory** ở đây là một interface `FurnitureFactory` có các phương thức: `createChair()`, `createTable()`, `createSofa()`.

*   **Concrete Factories** là `VictorianFurnitureFactory` và `ModernFurnitureFactory`.
    *   `VictorianFurnitureFactory` sẽ tạo ra `VictorianChair`, `VictorianTable`, `VictorianSofa`.
    *   `ModernFurnitureFactory` sẽ tạo ra `ModernChair`, `ModernTable`, `ModernSofa`.

Khi bạn quyết định chọn phong cách nào, bạn sẽ dùng "nhà máy" tương ứng. Nhà máy đó sẽ đảm bảo cung cấp cho bạn một bộ nội thất hoàn chỉnh và đồng bộ.

## 3. Khi nào nên dùng

*   **Khi hệ thống của bạn cần tạo ra nhiều họ sản phẩm khác nhau:** Ví dụ, ứng dụng của bạn cần hỗ trợ nhiều "theme" giao diện người dùng (Windows, macOS, Linux). Mỗi theme là một họ các component (Button, Window, Checkbox). Bạn có thể dùng Abstract Factory để chuyển đổi giữa các theme một cách dễ dàng.
*   **Khi bạn muốn đảm bảo các sản phẩm được tạo ra phải tương thích với nhau:** Ví dụ, một hệ thống báo cáo cần tạo ra các đối tượng `DataSource`, `Parser`, và `Renderer`. Một `SQLDataSource` phải đi cùng `SQLParser` và `SQLRenderer`. Một `CSVDataSource` phải đi cùng `CSVParser`. Abstract Factory đảm bảo tính nhất quán này.
*   **Khi bạn muốn che giấu chi tiết implement của các sản phẩm:** Client chỉ cần biết về interface của factory và sản phẩm, không cần quan tâm chúng được tạo ra như thế nào.

## 4. Khi nào KHÔNG nên dùng

*   **Khi chỉ có một họ sản phẩm duy nhất:** Nếu hệ thống của bạn không cần và sẽ không cần chuyển đổi giữa các họ sản phẩm, Abstract Factory sẽ làm phức tạp hóa vấn đề một cách không cần thiết.
*   **Khi thêm một loại sản phẩm mới rất khó khăn:** Nếu bạn cần thêm một sản phẩm mới vào họ (ví dụ, thêm `createLamp()` vào `FurnitureFactory`), bạn sẽ phải sửa đổi tất cả các lớp factory con. Đây là nhược điểm lớn nhất của Abstract Factory. Trong trường hợp này, các pattern khác như Factory Method hoặc Prototype có thể linh hoạt hơn.

## 5. Cách hoạt động

1.  **Abstract Products:** Định nghĩa các interface cho từng loại sản phẩm riêng biệt trong một họ. (Ví dụ: `IButton`, `ICheckbox`).
2.  **Concrete Products:** Các lớp cụ thể implement các Abstract Product. Các biến thể khác nhau của cùng một sản phẩm sẽ thuộc về cùng một họ. (Ví dụ: `WindowsButton`, `MacButton`; `WindowsCheckbox`, `MacCheckbox`).
3.  **Abstract Factory Interface:** Khai báo một tập các phương thức "factory method" để tạo ra các abstract product. (Ví dụ: `createButton()`, `createCheckbox()`).
4.  **Concrete Factories:** Các lớp cụ thể implement Abstract Factory interface. Mỗi concrete factory tương ứng với một họ sản phẩm và chịu trách nhiệm tạo ra các sản phẩm thuộc họ đó. (Ví dụ: `WindowsFactory`, `MacFactory`).
5.  **Client:** Sử dụng interface của Abstract Factory và Abstract Products. Client không cần biết nó đang dùng concrete factory nào, chỉ cần biết rằng factory đó sẽ tạo ra một bộ sản phẩm tương thích.

## 6. Code ví dụ (Java)

```java
// File: examples/java/creational/AbstractFactoryDemo.java
package creational;

// 1. Abstract Products
interface Button {
    void paint();
}

interface Checkbox {
    void paint();
}

// 2. Concrete Products for "Windows" family
class WindowsButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in Windows style.");
    }
}

class WindowsCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in Windows style.");
    }
}

// 2. Concrete Products for "macOS" family
class MacOSButton implements Button {
    @Override
    public void paint() {
        System.out.println("Rendering a button in macOS style.");
    }
}

class MacOSCheckbox implements Checkbox {
    @Override
    public void paint() {
        System.out.println("Rendering a checkbox in macOS style.");
    }
}


// 3. Abstract Factory Interface
interface GUIFactory {
    Button createButton();
    Checkbox createCheckbox();
}

// 4. Concrete Factories
class WindowsFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new WindowsButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new WindowsCheckbox();
    }
}

class MacOSFactory implements GUIFactory {
    @Override
    public Button createButton() {
        return new MacOSButton();
    }

    @Override
    public Checkbox createCheckbox() {
        return new MacOSCheckbox();
    }
}

// 5. Client
class Application {
    private final Button button;
    private final Checkbox checkbox;

    public Application(GUIFactory factory) {
        button = factory.createButton();
        checkbox = factory.createCheckbox();
    }

    public void paintUI() {
        button.paint();
        checkbox.paint();
    }
}

// --- Demo ---
public class AbstractFactoryDemo {
    public static void main(String[] args) {
        // Decide which factory to use based on configuration or environment
        String osName = System.getProperty("os.name").toLowerCase();
        GUIFactory factory;

        if (osName.contains("win")) {
            factory = new WindowsFactory();
        } else if (osName.contains("mac")) {
            factory = new MacOSFactory();
        } else {
            // Default or other OS
            throw new UnsupportedOperationException("OS not supported");
        }
        
        // The client code doesn't care which factory it got.
        Application app = new Application(factory);
        app.paintUI();
    }
}

```

## 7. Ứng dụng trong Spring Boot

Mặc dù Spring Boot không có một annotation `@AbstractFactory` tường minh, triết lý của nó có thể được áp dụng bằng cách kết hợp các pattern và tính năng khác của Spring.

*   **Sử dụng `@Configuration` và `@Profile`:** Bạn có thể tạo nhiều lớp `@Configuration` khác nhau, mỗi lớp định nghĩa một bộ bean tương thích cho một môi trường cụ thể (dev, staging, prod) hoặc một tính năng cụ thể. `@Profile` cho phép bạn kích hoạt một "factory" (lớp configuration) nhất định.

    ```java
    // --- Abstract Products (Interfaces) ---
    interface DataSourceConnector { void connect(); }
    interface MessageQueueClient { void send(String message); }

    // --- Concrete Products for "dev" profile ---
    class InMemoryDataSource implements DataSourceConnector { /* ... */ }
    class MockMessageQueue implements MessageQueueClient { /* ... */ }

    // --- Concrete Products for "prod" profile ---
    class MySqlDataSource implements DataSourceConnector { /* ... */ }
    class RabbitMQClient implements MessageQueueClient { /* ... */ }


    // --- Concrete Factory for "dev" ---
    @Configuration
    @Profile("dev")
    public class DevEnvironmentFactory {
        @Bean
        public DataSourceConnector dataSourceConnector() {
            return new InMemoryDataSource();
        }

        @Bean
        public MessageQueueClient messageQueueClient() {
            return new MockMessageQueue();
        }
    }

    // --- Concrete Factory for "prod" ---
    @Configuration
    @Profile("prod")
    public class ProdEnvironmentFactory {
        @Bean
        public DataSourceConnector dataSourceConnector() {
            return new MySqlDataSource();
        }

        @Bean
        public MessageQueueClient messageQueueClient() {
            return new RabbitMQClient();
        }
    }
    ```
    Khi bạn chạy ứng dụng với profile `dev` (`--spring.profiles.active=dev`), Spring sẽ sử dụng `DevEnvironmentFactory` để tạo ra một bộ bean "giả lập". Khi chạy với profile `prod`, nó sẽ tạo ra bộ bean kết nối đến hệ thống thật.

## 8. So sánh

*   **Abstract Factory vs. Factory Method:**
    *   Factory Method sử dụng **kế thừa** để tạo sản phẩm. Abstract Factory sử dụng **composition** (chứa một đối tượng factory) để tạo ra các họ sản phẩm.
    *   Factory Method chỉ có **một** phương thức tạo đối tượng. Abstract Factory có **nhiều** phương thức để tạo các đối tượng khác nhau trong cùng một họ.
*   **Abstract Factory vs. Builder:**
    *   Abstract Factory tập trung vào việc tạo ra **nhiều loại đối tượng** thuộc cùng một họ (ví dụ: `Button`, `Checkbox`).
    *   Builder tập trung vào việc tạo ra **một đối tượng phức tạp** từng bước một (ví dụ: xây dựng một đối tượng `User` với nhiều thuộc tính tùy chọn).

## 9. Interview Tips

*   **Câu hỏi:** "Abstract Factory là gì và nó khác gì Factory Method?"
    *   **Trả lời:** "Abstract Factory là một Creational Pattern dùng để tạo ra các họ đối tượng liên quan mà không cần chỉ định lớp cụ thể. Nó khác với Factory Method ở chỗ nó tạo ra một *gia đình* sản phẩm (ví dụ: UI kit cho Windows), trong khi Factory Method chỉ tạo ra *một* sản phẩm. Abstract Factory thường được implement bằng composition, còn Factory Method dùng inheritance."
*   **Câu hỏi:** "Khi nào bạn sẽ chọn Abstract Factory?"
    *   **Trả lời:** "Em sẽ chọn Abstract Factory khi hệ thống cần làm việc với nhiều họ sản phẩm và các sản phẩm trong một họ phải tương thích với nhau. Ví dụ, khi cần hỗ trợ đa nền tảng (như UI cho Win/Mac) hoặc đa môi trường (như cấu hình cho dev/prod), Abstract Factory giúp đảm bảo tính nhất quán và dễ dàng chuyển đổi giữa các họ."
*   **Câu hỏi:** "Nhược điểm của Abstract Factory là gì?"
    *   **Trả lời:** "Nhược điểm lớn nhất là khó mở rộng. Nếu cần thêm một loại sản phẩm mới vào họ (ví dụ thêm `Textbox`), ta phải sửa đổi interface Abstract Factory và tất cả các lớp concrete factory implement nó. Điều này vi phạm nguyên tắc Open/Closed."

