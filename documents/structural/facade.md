# Facade Pattern

## 1. Tổng quan

*   **Định nghĩa:** Facade là một mẫu thiết kế thuộc nhóm Structural, cung cấp một **giao diện đơn giản (một "mặt tiền")** cho một hệ thống con (subsystem) phức tạp gồm nhiều lớp, thư viện, hoặc API. Facade che giấu sự phức tạp của hệ thống và cung cấp một cách truy cập dễ dàng hơn.
*   **Vấn đề giải quyết:**
    *   **Đơn giản hóa việc sử dụng một hệ thống phức tạp:** Khi bạn có một hệ thống con với nhiều thành phần và tương tác phức tạp, Facade cung cấp một lớp duy nhất với các phương thức đơn giản để client có thể sử dụng mà không cần biết về sự phức tạp bên trong.
    *   **Giảm sự phụ thuộc (Decoupling):** Tách biệt client khỏi các thành phần bên trong của hệ thống con. Nếu các thành phần bên trong thay đổi, bạn chỉ cần cập nhật lớp Facade mà không cần sửa đổi code của client.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn muốn khởi động một chiếc xe hơi. Quy trình thực sự bên trong rất phức tạp:

1.  Hệ thống điện được kích hoạt.
2.  Bơm xăng bắt đầu hoạt động.
3.  Bộ đề (starter) quay động cơ.
4.  Bugia (spark plugs) đánh lửa.
5.  ... và nhiều bước khác.

Bạn, với tư cách là người lái xe (client), không cần phải biết tất cả những điều đó. Bạn chỉ cần thực hiện một hành động đơn giản: **vặn chìa khóa** (hoặc nhấn nút Start).

Hành động "vặn chìa khóa" chính là **Facade**. Nó cung cấp một giao diện đơn giản để bạn tương tác với một hệ thống con phức tạp (động cơ xe).

## 3. Khi nào nên dùng

*   **Khi bạn muốn cung cấp một giao diện đơn giản cho một hệ thống con phức tạp:** Đây là mục đích chính của Facade.
*   **Khi bạn muốn cấu trúc một hệ thống thành các lớp con (subsystems):** Facade có thể đóng vai trò là điểm vào (entry point) cho mỗi lớp con.
*   **Khi bạn muốn giảm sự phụ thuộc giữa client và các lớp implement của một hệ thống con:** Client chỉ phụ thuộc vào Facade, giúp cho việc nâng cấp hoặc thay thế hệ thống con trở nên dễ dàng hơn.
*   **Khi làm việc với các thư viện hoặc API cũ (legacy code):** Bạn có thể viết một Facade để cung cấp một API hiện đại, sạch sẽ hơn cho các hệ thống cũ kỹ, khó sử dụng.

## 4. Khi nào KHÔNG nên dùng

*   **Khi hệ thống con không đủ phức tạp:** Nếu hệ thống chỉ có một vài lớp và tương tác đơn giản, việc thêm một lớp Facade là không cần thiết và có thể làm cồng kềnh code.
*   **Khi client cần truy cập sâu vào các thành phần của hệ thống con:** Facade cung cấp một giao diện đơn giản, nhưng nó cũng có thể che giấu đi các chức năng nâng cao. Tuy nhiên, một Facade được thiết kế tốt vẫn có thể cho phép client "vượt rào" để truy cập vào hệ thống con nếu cần.

## 5. Cách hoạt động

1.  **Facade:**
    *   Biết về các lớp và đối tượng nào trong hệ thống con chịu trách nhiệm cho một yêu cầu.
    *   Ủy quyền các yêu cầu của client đến các đối tượng phù hợp trong hệ thống con.
    *   Không chứa logic nghiệp vụ của hệ thống con, chỉ điều phối và đơn giản hóa việc truy cập.
2.  **Subsystem Classes:**
    *   Các lớp implement chức năng của hệ thống con.
    *   Chúng xử lý công việc thực sự được yêu cầu bởi Facade.
    *   Các lớp này không biết về sự tồn tại của Facade.

**Flow hoạt động:**
Client -> `Gọi một phương thức đơn giản trên Facade` -> `Facade` -> `Điều phối và gọi một hoặc nhiều phương thức phức tạp trên các lớp của Subsystem` -> `Subsystem thực hiện công việc`.

## 6. Code ví dụ (Java)

Ví dụ về một hệ thống "Rạp hát tại nhà" (Home Theater) với nhiều thiết bị phức tạp.

```java
// File: examples/java/structural/FacadeDemo.java
package structural;

// --- The Complex Subsystem ---
// These are the individual components of our complex system.
class Amplifier {
    public void on() { System.out.println("Amplifier is on"); }
    public void off() { System.out.println("Amplifier is off"); }
    public void setVolume(int level) { System.out.println("Setting amplifier volume to " + level); }
}

class DvdPlayer {
    public void on() { System.out.println("DVD Player is on"); }
    public void off() { System.out.println("DVD Player is off"); }
    public void play(String movie) { System.out.println("Playing movie: '" + movie + "'"); }
}

class Projector {
    public void on() { System.out.println("Projector is on"); }
    public void off() { System.out.println("Projector is off"); }
    public void wideScreenMode() { System.out.println("Projector in widescreen mode"); }
}

class TheaterLights {
    public void dim(int level) { System.out.println("Theater lights dimming to " + level + "%"); }
    public void on() { System.out.println("Theater lights are on"); }
}

// 1. The Facade Class
/**
 * The Facade provides a simple, unified interface to the complex subsystem.
 * It knows how to coordinate the components to perform a task.
 */
class HomeTheaterFacade {
    // 2. The facade has references to the subsystem components.
    private Amplifier amp;
    private DvdPlayer dvd;
    private Projector projector;
    private TheaterLights lights;

    public HomeTheaterFacade(Amplifier amp, DvdPlayer dvd, Projector projector, TheaterLights lights) {
        this.amp = amp;
        this.dvd = dvd;
        this.projector = projector;
        this.lights = lights;
    }

    /**
     * A simplified method that performs a series of complex actions.
     * The client only needs to call this one method.
     */
    public void watchMovie(String movie) {
        System.out.println("Get ready to watch a movie...");
        lights.dim(10);
        projector.on();
        projector.wideScreenMode();
        amp.on();
        amp.setVolume(5);
        dvd.on();
        dvd.play(movie);
    }

    /**
     * Another simplified method.
     */
    public void endMovie() {
        System.out.println("\nShutting movie theater down...");
        dvd.off();
        amp.off();
        projector.off();
        lights.on();
    }
}


// --- Client Code ---
public class FacadeDemo {
    public static void main(String[] args) {
        // Instantiate the subsystem components
        Amplifier amp = new Amplifier();
        DvdPlayer dvd = new DvdPlayer();
        Projector projector = new Projector();
        TheaterLights lights = new TheaterLights();
        
        // Instantiate the facade with the components
        HomeTheaterFacade homeTheater = new HomeTheaterFacade(amp, dvd, projector, lights);

        // The client interacts with the simplified interface of the facade
        homeTheater.watchMovie("Raiders of the Lost Ark");
        homeTheater.endMovie();
    }
}
```

## 7. Ứng dụng trong Spring Boot

Facade là một pattern rất tự nhiên trong kiến trúc ứng dụng Spring Boot.

*   **Service Layer as a Facade:**
    *   Trong kiến trúc phân lớp (layered architecture), lớp **Service** thường đóng vai trò là một Facade cho lớp **Data Access (Repository)** và các logic nghiệp vụ khác.
    *   Controller (client) không cần biết về `UserRepository`, `ProductRepository`, `EmailService`,... Nó chỉ cần gọi một phương thức duy nhất trên `OrderService` như `placeOrder()`.
    *   `OrderService` sẽ điều phối các hoạt động phức tạp bên trong: lưu đơn hàng vào database (`OrderRepository`), cập nhật kho (`ProductRepository`), gửi email xác nhận (`EmailService`),...

    ```java
    @RestController
    @RequestMapping("/orders")
    public class OrderController { // The Client
        private final OrderServiceFacade orderService; // Depends only on the Facade

        @Autowired
        public OrderController(OrderServiceFacade orderService) {
            this.orderService = orderService;
        }

        @PostMapping
        public ResponseEntity<String> createOrder(@RequestBody OrderRequest request) {
            orderService.placeOrder(request);
            return ResponseEntity.ok("Order placed successfully");
        }
    }

    @Service
    public class OrderServiceFacade { // The Facade
        // References to the subsystem components
        private final OrderRepository orderRepo;
        private final ProductRepository productRepo;
        private final EmailService emailService;

        @Autowired
        public OrderServiceFacade(OrderRepository orderRepo, ProductRepository productRepo, EmailService emailService) {
            this.orderRepo = orderRepo;
            this.productRepo = productRepo;
            this.emailService = emailService;
        }

        // The simplified method for the client
        @Transactional
        public void placeOrder(OrderRequest request) {
            // Complex coordination logic is hidden here
            // 1. Check product stock
            productRepo.reduceStock(request.getProductId(), request.getQuantity());
            // 2. Create and save the order
            Order newOrder = new Order(request);
            orderRepo.save(newOrder);
            // 3. Send confirmation email
            emailService.sendOrderConfirmation(request.getCustomerEmail(), newOrder.getId());
        }
    }
    ```

## 8. So sánh

*   **Facade vs. Adapter:**
    *   **Mục đích:** Facade **đơn giản hóa** một interface. Adapter **chuyển đổi** một interface thành một interface khác.
    *   **Đối tượng:** Facade thường bọc nhiều đối tượng của một hệ thống con. Adapter thường bọc một đối tượng duy nhất.
*   **Facade vs. Abstract Factory:**
    *   Facade che giấu sự phức tạp của hệ thống trong việc **sử dụng** nó. Abstract Factory che giấu sự phức tạp trong việc **tạo** một họ các đối tượng.
*   **Facade vs. Mediator:**
    *   Facade là giao tiếp một chiều: client nói chuyện với Facade, Facade nói chuyện với subsystem. Subsystem không biết về Facade.
    *   Mediator là giao tiếp hai chiều: các colleague nói chuyện với Mediator và Mediator có thể nói chuyện lại với các colleague.

## 9. Interview Tips

*   **Câu hỏi:** "Facade pattern là gì? Cho ví dụ."
    *   **Trả lời:** "Facade pattern cung cấp một giao diện đơn giản cho một hệ thống phức tạp. Nó che giấu sự phức tạp bên trong và giúp client dễ dàng sử dụng hơn. Trong Spring Boot, lớp Service thường hoạt động như một Facade. Controller chỉ cần gọi một phương thức `placeOrder()` trên `OrderService`, và `OrderService` sẽ tự điều phối các repository và các service khác để hoàn thành công việc."
*   **Câu hỏi:** "Facade giúp giảm sự phụ thuộc như thế nào?"
    *   **Trả lời:** "Client chỉ phụ thuộc vào lớp Facade, không phải vào hàng chục lớp lằng nhằng bên trong hệ thống con. Nếu các lớp bên trong đó được tái cấu trúc hoặc thay thế, ta chỉ cần cập nhật logic trong Facade. Miễn là signature của các phương thức trong Facade không đổi, code của client hoàn toàn không bị ảnh hưởng."
*   **Câu hỏi:** "Phân biệt Facade và Adapter."
    *   **Trả lời:** "Mục đích của chúng khác nhau. Facade dùng để *đơn giản hóa* một interface, trong khi Adapter dùng để *chuyển đổi* một interface không tương thích thành một interface khác mà client mong đợi. Facade thường bọc nhiều lớp, còn Adapter thường chỉ bọc một lớp."

