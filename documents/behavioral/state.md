# State Pattern

## 1. Tổng quan

*   **Định nghĩa:** State là một mẫu thiết kế thuộc nhóm Behavioral, cho phép một đối tượng thay đổi hành vi của nó khi trạng thái nội tại (internal state) của nó thay đổi. Đối tượng sẽ trông giống như thể nó đã thay đổi lớp của mình.
*   **Vấn đề giải quyết:**
    *   **Tránh dùng câu lệnh `if-else` hoặc `switch-case` lớn:** Khi một đối tượng có nhiều trạng thái và hành vi của nó phụ thuộc rất nhiều vào trạng thái hiện tại, bạn sẽ phải viết các khối `if/switch` lớn trong mọi phương thức để kiểm tra trạng thái trước khi hành động. State pattern giúp loại bỏ điều này.
    *   **Gói gọn logic của từng trạng thái:** Tách logic liên quan đến một trạng thái cụ thể vào một lớp riêng biệt, làm cho code sạch sẽ và dễ quản lý hơn.
    *   **Tuân thủ Open/Closed Principle:** Dễ dàng thêm các trạng thái mới mà không cần sửa đổi các lớp trạng thái hiện có hoặc lớp Context.

## 2. Ví dụ đời thường

Hãy xem xét một chiếc máy bán hàng tự động (**Context**). Nó có các trạng thái khác nhau:

*   `NoCoinState`: Trạng thái không có xu.
*   `HasCoinState`: Trạng thái đã có xu.
*   `SoldState`: Trạng thái đang bán hàng.
*   `SoldOutState`: Trạng thái hết hàng.

Hành vi của máy bán hàng thay đổi hoàn toàn tùy thuộc vào trạng thái:
*   Ở `NoCoinState`, nếu bạn nhấn nút mua hàng, nó sẽ yêu cầu bạn bỏ xu vào. Nếu bạn bỏ xu, nó sẽ chuyển sang `HasCoinState`.
*   Ở `HasCoinState`, nếu bạn nhấn nút mua hàng, nó sẽ chuyển sang `SoldState` và trả hàng. Nếu bạn bỏ thêm xu, nó sẽ báo lỗi.
*   Ở `SoldState`, nó bận rộn trả hàng và sẽ không chấp nhận hành động nào khác cho đến khi xong và chuyển sang `NoCoinState`.

Thay vì có một lớp `VendingMachine` với các câu lệnh `if (state == NO_COIN) { ... } else if (state == HAS_COIN) { ... }`, chúng ta sẽ có các lớp `State` riêng biệt, và `VendingMachine` chỉ cần ủy quyền hành động cho đối tượng state hiện tại của nó.

## 3. Khi nào nên dùng

*   **Khi hành vi của một đối tượng phụ thuộc vào trạng thái của nó và phải thay đổi lúc runtime.**
*   **Khi một đối tượng có quá nhiều trạng thái và các câu lệnh điều kiện để xử lý chúng trở nên cồng kềnh và khó bảo trì.**
*   **Khi bạn muốn các trạng thái và sự chuyển đổi giữa chúng phải rõ ràng và tường minh.**

## 4. Khi nào KHÔNG nên dùng

*   **Khi đối tượng có ít trạng thái và logic không phức tạp:** Nếu chỉ có 2-3 trạng thái và một vài câu lệnh `if` là đủ, việc áp dụng State pattern có thể là quá mức cần thiết (over-engineering).
*   **Khi các trạng thái không chia sẻ cùng một interface:** Nếu các hành động trong các trạng thái khác nhau quá khác biệt và không thể gom vào một interface chung, pattern này sẽ khó áp dụng.

## 5. Cách hoạt động

1.  **Context:**
    *   Lưu trữ một tham chiếu đến một instance của một `ConcreteState`. Đây là trạng thái hiện tại của Context.
    *   Cung cấp một phương thức để cho phép các đối tượng `State` thay đổi trạng thái hiện tại của Context.
    *   Ủy quyền các yêu cầu liên quan đến trạng thái cho đối tượng state hiện tại.
2.  **State Interface (hoặc Abstract Class):**
    *   Định nghĩa một interface chung cho tất cả các trạng thái. Interface này chứa các phương thức tương ứng với các hành vi của Context.
3.  **Concrete States:**
    *   Implement State Interface. Mỗi lớp đại diện cho một trạng thái cụ thể.
    *   Mỗi `ConcreteState` sẽ implement các hành vi phù hợp với trạng thái đó.
    *   Nó cũng chịu trách nhiệm cho việc chuyển đổi trạng thái của `Context` sang một trạng thái khác (ví dụ, sau khi bỏ xu vào, `NoCoinState` sẽ gọi `context.setState(new HasCoinState())`).

**Flow hoạt động:**
Client -> `Gọi một phương thức trên Context` -> `Context ủy quyền lời gọi đến đối tượng State hiện tại` -> `Đối tượng State xử lý yêu cầu` -> `(Nếu cần) Đối tượng State tạo một State mới và cập nhật trạng thái của Context`.

## 6. Code ví dụ (Java)

Ví dụ về một trình phát nhạc (Audio Player) đơn giản.

```java
// File: examples/java/behavioral/StateDemo.java
package behavioral;

// --- State Interface and Concrete States ---

// 2. The State Interface
interface PlayerState {
    void play(AudioPlayer player);
    void pause(AudioPlayer player);
    void stop(AudioPlayer player);
}

// 3. Concrete State classes
class ReadyState implements PlayerState {
    @Override
    public void play(AudioPlayer player) {
        System.out.println("Starting playback...");
        player.setState(new PlayingState()); // Change state to Playing
    }

    @Override
    public void pause(AudioPlayer player) {
        System.out.println("Already stopped. Cannot pause.");
    }

    @Override
    public void stop(AudioPlayer player) {
        System.out.println("Already stopped.");
    }
}

class PlayingState implements PlayerState {
    @Override
    public void play(AudioPlayer player) {
        System.out.println("Already playing.");
    }

    @Override
    public void pause(AudioPlayer player) {
        System.out.println("Pausing playback...");
        player.setState(new PausedState()); // Change state to Paused
    }

    @Override
    public void stop(AudioPlayer player) {
        System.out.println("Stopping playback...");
        player.setState(new ReadyState()); // Change state to Ready
    }
}

class PausedState implements PlayerState {
    @Override
    public void play(AudioPlayer player) {
        System.out.println("Resuming playback...");
        player.setState(new PlayingState()); // Change state to Playing
    }

    @Override
    public void pause(AudioPlayer player) {
        System.out.println("Already paused.");
    }

    @Override
    public void stop(AudioPlayer player) {
        System.out.println("Stopping playback from paused state...");
        player.setState(new ReadyState()); // Change state to Ready
    }
}


// 1. The Context Class
class AudioPlayer {
    private PlayerState state;

    public AudioPlayer() {
        // Initial state is Ready
        this.state = new ReadyState();
    }

    // The context allows state objects to change its state.
    void setState(PlayerState state) {
        this.state = state;
    }

    // The context delegates behavior to the current state object.
    public void clickPlay() {
        System.out.print("Play button clicked: ");
        state.play(this);
    }

    public void clickPause() {
        System.out.print("Pause button clicked: ");
        state.pause(this);
    }

    public void clickStop() {
        System.out.print("Stop button clicked: ");
        state.stop(this);
    }
}

// --- Demo ---
public class StateDemo {
    public static void main(String[] args) {
        AudioPlayer player = new AudioPlayer();

        // The client interacts with the context, unaware of the internal states.
        System.out.println("--- Initial State ---");
        player.clickPlay();  // Starts playing
        player.clickPause(); // Pauses
        
        System.out.println("\n--- Paused State ---");
        player.clickPause(); // Does nothing
        player.clickPlay();  // Resumes playing
        
        System.out.println("\n--- Playing State ---");
        player.clickStop();  // Stops
        
        System.out.println("\n--- Stopped State ---");
        player.clickPause(); // Does nothing
    }
}
```

## 7. Ứng dụng trong Spring Boot

State pattern rất hữu ích trong việc quản lý vòng đời của các đối tượng nghiệp vụ phức tạp.

*   **Quản lý trạng thái đơn hàng (Order Management):** Một đơn hàng có thể có nhiều trạng thái: `PENDING`, `PAID`, `SHIPPING`, `DELIVERED`, `CANCELLED`. Hành động có thể thực hiện trên đơn hàng (ví dụ: `thanh toán`, `giao hàng`, `hủy`) phụ thuộc hoàn toàn vào trạng thái hiện tại.

    ```java
    // State Interface
    public interface OrderState {
        void process(OrderContext order);
        void ship(OrderContext order);
        void cancel(OrderContext order);
    }
    
    // Context
    @Entity
    public class OrderContext {
        @Id private Long id;
        
        @Transient // The state object is not persisted directly
        private OrderState state;
        
        private String stateName; // Persist the state's class name
        
        // after loading from DB, set the correct state object based on stateName
        @PostLoad
        void initState() {
            // Logic to instantiate the correct state class from stateName
        }
        
        public void setState(OrderState state) {
            this.state = state;
            this.stateName = state.getClass().getName();
        }
        
        // Delegate calls
        public void process() { state.process(this); }
        public void ship() { state.ship(this); }
        // ...
    }
    
    // Concrete State
    @Component
    public class PendingState implements OrderState {
        @Override
        public void process(OrderContext order) {
            System.out.println("Processing payment...");
            // on success:
            order.setState(new PaidState());
        }
        // ...
    }
    ```
    Đây là một cách tiếp cận. Tuy nhiên, trong các ứng dụng Spring, việc quản lý trạng thái thường được thực hiện hiệu quả hơn bằng các thư viện **State Machine** như **Spring Statemachine**.

*   **Spring Statemachine:**
    *   Đây là một framework của Spring được xây dựng riêng để giải quyết vấn đề quản lý trạng thái.
    *   Nó cung cấp một cách cấu hình máy trạng thái (state machine) một cách tường minh, bao gồm các trạng thái (states), sự kiện (events), chuyển đổi (transitions), và hành động (actions).
    *   Nó trừu tượng hóa toàn bộ State pattern, giúp bạn tập trung vào logic nghiệp vụ thay vì phải tự implement các lớp State và Context.

## 8. So sánh

*   **State vs. Strategy:**
    *   Cấu trúc của hai pattern này rất giống nhau (Context, State/Strategy interface, Concrete States/Strategies).
    *   **Sự khác biệt cốt lõi nằm ở mục đích:**
        *   **Strategy:** Tập trung vào **cách** một tác vụ được thực hiện (thuật toán). Client thường biết và chủ động chọn Strategy. Các strategy thường không biết về nhau.
        *   **State:** Tập trung vào **việc ở trong một trạng thái** và cách hành vi thay đổi khi trạng thái thay đổi. Việc chuyển đổi trạng thái thường do chính các đối tượng State hoặc Context quản lý, client không can thiệp. Các State biết về nhau và chịu trách nhiệm chuyển đổi qua lại.

## 9. Interview Tips

*   **Câu hỏi:** "State pattern là gì và nó giải quyết vấn đề gì?"
    *   **Trả lời:** "State pattern cho phép một đối tượng thay đổi hành vi khi trạng thái nội tại của nó thay đổi. Nó giải quyết vấn đề các câu lệnh if/switch lồng nhau phức tạp khi một đối tượng có nhiều trạng thái. Bằng cách đóng gói logic của từng trạng thái vào một lớp riêng, code trở nên sạch sẽ và dễ bảo trì hơn."
*   **Câu hỏi:** "So sánh State và Strategy pattern."
    *   **Trả lời:** "Cả hai có cấu trúc giống nhau nhưng mục đích khác nhau. Strategy là về việc chọn một *thuật toán* để thực hiện một công việc, và client thường chủ động chọn thuật toán đó. State là về việc đối tượng *đang ở trong trạng thái nào* và hành vi của nó thay đổi ra sao. Việc chuyển trạng thái thường do chính đối tượng hoặc các state con quản lý, client không cần biết."
*   **Câu hỏi:** "Trong một ứng dụng e-commerce, bạn sẽ dùng State pattern để làm gì?"
    *   **Trả lời:** "Em sẽ dùng nó để quản lý vòng đời của một đơn hàng. Một đơn hàng có thể ở các trạng thái như 'Mới tạo', 'Đã thanh toán', 'Đang giao', 'Hoàn thành', 'Đã hủy'. Các hành động như `thanh toán()` hay `hủy()` chỉ hợp lệ ở một số trạng thái nhất định. State pattern giúp em định nghĩa rõ ràng các hành vi và sự chuyển đổi cho từng trạng thái, thay vì dùng một mớ if-else trong lớp `Order`."

