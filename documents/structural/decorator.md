# Decorator Pattern

## 1. Tổng quan

*   **Định nghĩa:** Decorator là một mẫu thiết kế thuộc nhóm Structural, cho phép bạn **thêm các chức năng mới** vào một đối tượng một cách linh hoạt mà không cần thay đổi code của đối tượng đó. Pattern này hoạt động bằng cách "bọc" (wrap) đối tượng gốc vào trong một hoặc nhiều đối tượng "trang trí" (decorator).
*   **Vấn đề giải quyết:**
    *   **Tránh bùng nổ lớp con (Subclass Explosion):** Nếu bạn có một đối tượng và nhiều tính năng tùy chọn có thể thêm vào (ví dụ: một cửa sổ có thể có viền, có thanh cuộn, hoặc cả hai), việc dùng kế thừa sẽ tạo ra rất nhiều lớp con (`WindowWithBorder`, `WindowWithScrollbar`, `WindowWithBorderAndScrollbar`,...). Decorator cho phép bạn kết hợp các tính năng này một cách linh hoạt lúc runtime.
    *   **Thêm chức năng một cách linh hoạt:** Bạn có thể thêm hoặc bớt chức năng cho một đối tượng tại thời điểm chạy.
    *   **Tuân thủ Single Responsibility Principle:** Mỗi decorator chỉ chịu trách nhiệm cho một chức năng cụ thể.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn đang gọi một ly cà phê.

*   **Đối tượng gốc (Component):** Một ly cà phê đen đơn giản (`SimpleCoffee`).
*   **Các Decorator:** Các thành phần thêm vào như `Sữa (Milk)`, `Đường (Sugar)`, `Kem (Whip)`.

Bạn có thể "trang trí" ly cà phê của mình bằng cách:
1.  Bắt đầu với một ly cà phê đen.
2.  Bọc nó bằng `MilkDecorator`. Bây giờ bạn có cà phê sữa.
3.  Tiếp tục bọc kết quả đó bằng `SugarDecorator`. Bây giờ bạn có cà phê sữa có đường.

Mỗi decorator thêm vào cả "chức năng" (thay đổi vị) và "trách nhiệm" (thay đổi giá). `cost()` của ly cà phê cuối cùng sẽ là `cost(cà phê) + cost(sữa) + cost(đường)`.

## 3. Khi nào nên dùng

*   **Khi bạn muốn thêm chức năng cho các đối tượng một cách linh hoạt và không muốn ảnh hưởng đến các đối tượng khác cùng lớp.**
*   **Khi việc sử dụng kế thừa để mở rộng chức năng là không thực tế** do có quá nhiều sự kết hợp độc lập của các chức năng.
*   **Khi bạn muốn có khả năng loại bỏ chức năng khỏi một đối tượng.**

## 4. Khi nào KHÔNG nên dùng

*   **Khi bạn chỉ cần thêm một vài chức năng đơn giản và cố định:** Nếu không có sự kết hợp phức tạp, việc dùng kế thừa có thể đơn giản hơn.
*   **Khi hiệu năng là tối quan trọng:** Việc có quá nhiều lớp bọc có thể làm chậm quá trình thực thi do phải đi qua nhiều lớp để thực hiện một hành động.
*   **Khi code của client phụ thuộc vào các lớp cụ thể:** Decorator hoạt động tốt nhất khi client chỉ làm việc với interface chung. Nếu client cần truy cập các phương thức của decorator cụ thể, nó sẽ phá vỡ tính trong suốt của pattern.

## 5. Cách hoạt động

1.  **Component Interface:** Định nghĩa interface chung cho cả đối tượng gốc và các decorator. (Ví dụ: `Coffee`).
2.  **Concrete Component:** Lớp cơ sở implement Component interface. Đây là đối tượng sẽ được "trang trí". (Ví dụ: `SimpleCoffee`).
3.  **Base Decorator (Tùy chọn nhưng phổ biến):**
    *   Một lớp trừu tượng implement Component interface.
    *   Nó chứa một tham chiếu đến một đối tượng `Component` (đối tượng mà nó bọc).
    *   Nó ủy quyền tất cả các lời gọi đến đối tượng được bọc. Lớp này giúp giảm code lặp lại trong các concrete decorator.
4.  **Concrete Decorators:**
    *   Kế thừa từ Base Decorator.
    *   Chúng thêm các hành vi mới **trước** hoặc **sau** khi ủy quyền lời gọi đến đối tượng được bọc.

**Flow hoạt động:**
Client -> `new ConcreteDecoratorB(new ConcreteDecoratorA(new ConcreteComponent()))` -> `Client gọi một phương thức` -> `Decorator B làm việc của nó` -> `Gọi đến Decorator A` -> `Decorator A làm việc của nó` -> `Gọi đến Component` -> `Component làm việc của nó`.

## 6. Code ví dụ (Java)

Ví dụ về việc mã hóa và nén dữ liệu trước khi ghi ra file.

```java
// File: examples/java/structural/DecoratorDemo.java
package structural;

// 1. Component Interface
interface DataSource {
    void writeData(String data);
    String readData();
}

// 2. Concrete Component
class FileDataSource implements DataSource {
    private String filename;
    private String content;

    public FileDataSource(String filename) {
        this.filename = filename;
    }

    @Override
    public void writeData(String data) {
        this.content = data;
        System.out.println("Writing data to file: " + filename);
    }

    @Override
    public String readData() {
        System.out.println("Reading data from file: " + filename);
        return content;
    }
}

// 3. Base Decorator
class DataSourceDecorator implements DataSource {
    protected DataSource wrappee;

    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    @Override
    public void writeData(String data) {
        // Delegate the call to the wrapped object
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        // Delegate the call to the wrapped object
        return wrappee.readData();
    }
}

// 4. Concrete Decorators
class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        // Add behavior BEFORE delegating
        String encryptedData = "[Encrypted] " + data + " [Encrypted]";
        System.out.println("Encrypting data.");
        super.writeData(encryptedData);
    }

    @Override
    public String readData() {
        // Add behavior AFTER delegating
        String encryptedData = super.readData();
        System.out.println("Decrypting data.");
        // Simple decryption for demo
        return encryptedData.replace("[Encrypted]", "").trim();
    }
}

class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource source) {
        super(source);
    }

    @Override
    public void writeData(String data) {
        String compressedData = "[Compressed] " + data + " [Compressed]";
        System.out.println("Compressing data.");
        super.writeData(compressedData);
    }

    @Override
    public String readData() {
        String compressedData = super.readData();
        System.out.println("Decompressing data.");
        return compressedData.replace("[Compressed]", "").trim();
    }
}


// --- Demo ---
public class DecoratorDemo {
    public static void main(String[] args) {
        // 1. Start with a plain data source object
        DataSource plainSource = new FileDataSource("mydata.txt");
        System.out.println("--- Writing and reading plain data ---");
        plainSource.writeData("Hello, Decorator!");
        System.out.println("Data read: " + plainSource.readData());

        System.out.println("\n--- Now, wrap it with an encryption decorator ---");
        // 2. Wrap the plain object with an encryption decorator
        DataSource encryptedSource = new EncryptionDecorator(plainSource);
        encryptedSource.writeData("This is a secret.");
        System.out.println("Data read: " + encryptedSource.readData());
        
        System.out.println("\n--- Now, wrap the already-encrypted source with a compression decorator ---");
        // 3. Wrap the already decorated object with another decorator
        DataSource compressedAndEncryptedSource = new CompressionDecorator(encryptedSource);
        compressedAndEncryptedSource.writeData("This is a super secret message.");
        
        System.out.println("\n--- Reading the data back ---");
        // The read process will happen in reverse order of decoration: decompress then decrypt
        System.out.println("Final data read: " + compressedAndEncryptedSource.readData());
    }
}
```

## 7. Ứng dụng trong Spring Boot

*   **Java I/O:** `java.io` là ví dụ kinh điển nhất của Decorator Pattern. `FileInputStream` là một component, và bạn có thể bọc nó bằng `BufferedInputStream` (thêm bộ đệm), `GZIPInputStream` (thêm giải nén),...
    ```java
    InputStream is = new GZIPInputStream(new BufferedInputStream(new FileInputStream("file.gz")));
    ```
*   **Spring `TransactionProxyFactoryBean`:** Mặc dù không phải là một decorator bạn tự tay implement, cách Spring quản lý transaction bằng AOP (Aspect-Oriented Programming) về mặt khái niệm rất giống Decorator. Spring "bọc" bean của bạn trong một proxy. Khi một phương thức được đánh dấu `@Transactional` được gọi, proxy này sẽ bắt đầu một transaction (hành vi thêm vào), sau đó gọi phương thức gốc, và cuối cùng commit hoặc rollback transaction (hành vi thêm vào).
*   **Spring Session:** `SessionRepositoryFilter` của Spring Session có thể bọc `HttpServletRequest` gốc bằng một `request wrapper` để thay đổi hành vi của `getSession()`, làm cho nó tương tác với một kho lưu trữ session ngoài (như Redis) thay vì session mặc định của servlet container.
*   **Tạo các Wrapper cho Bean:** Bạn có thể sử dụng `@Primary` và `@Qualifier` để tạo các decorator thủ công.

    ```java
    public interface MessageService {
        String getMessage();
    }
    
    @Service("simpleMessageService")
    public class SimpleMessageService implements MessageService {
        @Override public String getMessage() { return "Hello"; }
    }
    
    // Decorator
    @Service
    @Primary // This decorator will be injected by default
    public class ExclamationDecorator implements MessageService {
        private final MessageService wrappee;

        // Inject the specific component you want to wrap
        @Autowired
        public ExclamationDecorator(@Qualifier("simpleMessageService") MessageService wrappee) {
            this.wrappee = wrappee;
        }

        @Override
        public String getMessage() {
            // Add new behavior
            return wrappee.getMessage() + "!";
        }
    }
    ```

## 8. So sánh

*   **Decorator vs. Adapter:**
    *   Decorator **thêm chức năng**, không đổi interface. Adapter **đổi interface**, không thêm chức năng.
*   **Decorator vs. Proxy:**
    *   Cấu trúc rất giống nhau. Cả hai đều bọc một đối tượng và có cùng interface.
    *   **Mục đích khác nhau:** Decorator thêm chức năng mới có thể thấy được bởi client (ví dụ: `cost()` thay đổi). Proxy kiểm soát truy cập vào đối tượng gốc. Client thường không biết mình đang làm việc với proxy hay đối tượng thật. Proxy có thể quản lý vòng đời của đối tượng thật (ví dụ: tạo khi cần - lazy initialization), còn decorator thì không.
*   **Decorator vs. Kế thừa (Inheritance):**
    *   Decorator linh hoạt hơn kế thừa vì bạn có thể thêm/bớt chức năng lúc runtime và tránh được việc "bùng nổ lớp con". Kế thừa là tĩnh (static).

## 9. Interview Tips

*   **Câu hỏi:** "Decorator pattern là gì? Nó giải quyết vấn đề gì?"
    *   **Trả lời:** "Decorator là một structural pattern cho phép thêm chức năng mới vào đối tượng một cách linh hoạt bằng cách bọc chúng trong các lớp decorator. Nó giải quyết vấn đề 'bùng nổ lớp con' khi có nhiều sự kết hợp chức năng, và cho phép thay đổi hành vi lúc runtime. Ví dụ kinh điển là `java.io`, nơi ta có thể bọc một `FileInputStream` bằng `BufferedInputStream` để thêm chức năng buffering."
*   **Câu hỏi:** "Khi nào bạn sẽ chọn Decorator thay vì kế thừa?"
    *   **Trả lời:** "Em sẽ chọn Decorator khi các chức năng cần thêm vào là độc lập với nhau và có thể được kết hợp một cách linh hoạt. Kế thừa sẽ tạo ra một cấu trúc cứng nhắc và số lượng lớn các lớp con nếu có nhiều sự kết hợp. Decorator cho phép em 'mix-and-match' các chức năng này lúc runtime."
*   **Câu hỏi:** "So sánh Decorator và Proxy."
    *   **Trả lời:** "Chúng có cấu trúc rất giống nhau, nhưng mục đích khác nhau. Decorator tập trung vào việc thêm các trách nhiệm/chức năng mới vào một đối tượng. Proxy tập trung vào việc kiểm soát truy cập đến đối tượng đó, ví dụ như lazy loading, logging, hoặc kiểm tra quyền. Client có thể không biết sự tồn tại của Proxy, nhưng thường sẽ nhận thấy sự thay đổi hành vi từ Decorator."

