# Command Pattern

## 1. Tổng quan

*   **Định nghĩa:** Command là một mẫu thiết kế thuộc nhóm Behavioral, biến một yêu cầu (request) thành một đối tượng độc lập, chứa tất cả thông tin về yêu cầu đó. Việc này cho phép bạn tham số hóa (parameterize) các phương thức với các yêu cầu khác nhau, trì hoãn hoặc xếp hàng (queue) việc thực thi một yêu cầu, và hỗ trợ các thao tác có thể hoàn tác (undoable operations).
*   **Vấn đề giải quyết:**
    *   **Tách biệt người gọi và người thực thi:** Đối tượng gọi (Invoker) không cần biết gì về đối tượng thực thi (Receiver). Invoker chỉ biết cách thực thi một command.
    *   **Lưu trữ và quản lý các thao tác:** Các command là các đối tượng, vì vậy chúng có thể được lưu trữ trong danh sách, queue, hoặc stack. Điều này cho phép thực hiện các chức năng như: undo/redo, transaction, logging.
    *   **Tham số hóa các đối tượng:** Bạn có thể truyền các command như là tham số của phương thức.

## 2. Ví dụ đời thường

Hãy tưởng tượng bạn đến một nhà hàng.

*   Bạn (là **Client**) xem menu và quyết định gọi món.
*   Bạn gọi người phục vụ (**Invoker**) và nói yêu cầu của mình (ví dụ: "Cho tôi một phần bít tết").
*   Người phục vụ không tự nấu ăn. Họ viết yêu cầu của bạn vào một tờ giấy ghi chú (**Command**). Tờ giấy này chứa tất cả thông tin: món gì, số lượng, yêu cầu đặc biệt...
*   Người phục vụ đặt tờ giấy đó vào hàng chờ trong bếp.
*   Đầu bếp (**Receiver**) nhặt tờ giấy lên và thực hiện chính xác yêu cầu trên đó (nấu món bít tết).

Trong kịch bản này:
*   Người gọi (bạn) và người thực thi (đầu bếp) hoàn toàn không biết về nhau.
*   Yêu cầu (món ăn) đã được đóng gói thành một đối tượng (tờ giấy) và có thể được xếp hàng, lưu trữ.

## 3. Khi nào nên dùng

*   **Khi bạn muốn tham số hóa các đối tượng với các hành động:** Ví dụ, các nút (button) trong giao diện người dùng. Thay vì code cứng hành động cho mỗi nút, bạn có thể gán cho mỗi nút một đối tượng command khác nhau.
*   **Khi bạn muốn xếp hàng, lên lịch, hoặc thực thi các yêu cầu từ xa.**
*   **Khi bạn cần hỗ trợ chức năng Hoàn tác (Undo/Redo):** Command pattern là nền tảng cho chức năng này. Mỗi command có thể có một phương thức `undo()` để đảo ngược hành động của `execute()`. Bằng cách lưu các command đã thực thi vào một stack, bạn có thể dễ dàng undo chúng.
*   **Khi bạn muốn xây dựng các hệ thống giao dịch (transactional):** Một chuỗi các command có thể được thực thi. Nếu một command thất bại, bạn có thể undo tất cả các command đã thực thi trước đó.

## 4. Khi nào KHÔNG nên dùng

*   **Khi chỉ có các tương tác đơn giản, một-một:** Nếu một đối tượng chỉ cần gọi trực tiếp một phương thức trên một đối tượng khác, việc thêm các lớp Command, Invoker, Receiver sẽ làm phức tạp hóa vấn đề không cần thiết.
*   **Khi không cần các tính năng như undo/redo, queueing:** Nếu không tận dụng được các lợi ích chính của pattern, nó có thể là một sự lựa chọn quá tầm (overkill).

## 5. Cách hoạt động

1.  **Command Interface:**
    *   Định nghĩa một phương thức duy nhất, thường là `execute()`.
    *   (Tùy chọn) Có thể có thêm phương thức `undo()`.
2.  **Concrete Commands:**
    *   Implement Command Interface.
    *   Mỗi command chứa một tham chiếu đến một đối tượng **Receiver** (đối tượng sẽ thực hiện công việc).
    *   Khi `execute()` được gọi, command sẽ gọi phương thức tương ứng trên Receiver.
3.  **Receiver:**
    *   Đối tượng chứa logic nghiệp vụ thực sự. Nó biết cách thực hiện các công việc được yêu cầu.
4.  **Invoker:**
    *   Lưu trữ và gọi một hoặc nhiều command.
    *   Invoker không biết command đó là gì hay receiver là ai, nó chỉ biết rằng command đó có thể `execute()`.
5.  **Client:**
    *   Tạo ra các đối tượng Receiver.
    *   Tạo ra các đối tượng Concrete Command và liên kết chúng với Receiver.
    *   Tạo ra Invoker và gán command cho nó.
    *   Client ra lệnh cho Invoker thực thi command tại thời điểm thích hợp.

## 6. Code ví dụ (Java)

Ví dụ về một chiếc điều khiển từ xa (remote control) đơn giản cho các thiết bị điện tử.

```java
// File: examples/java/behavioral/CommandDemo.java
package behavioral;

// --- Receiver Classes ---
// These classes contain the actual business logic.
class Light {
    public void turnOn() { System.out.println("The light is on."); }
    public void turnOff() { System.out.println("The light is off."); }
}

class Fan {
    public void start() { System.out.println("The fan is spinning."); }
    public void stop() { System.out.println("The fan has stopped."); }
}

// 1. Command Interface
interface Command {
    void execute();
    void undo(); // For undo functionality
}

// 2. Concrete Commands
class LightOnCommand implements Command {
    private Light light;
    public LightOnCommand(Light light) { this.light = light; }

    @Override public void execute() { light.turnOn(); }
    @Override public void undo() { light.turnOff(); }
}

class LightOffCommand implements Command {
    private Light light;
    public LightOffCommand(Light light) { this.light = light; }
    
    @Override public void execute() { light.turnOff(); }
    @Override public void undo() { light.turnOn(); }
}

class FanStartCommand implements Command {
    private Fan fan;
    public FanStartCommand(Fan fan) { this.fan = fan; }
    
    @Override public void execute() { fan.start(); }
    @Override public void undo() { fan.stop(); }
}

// 4. Invoker Class
// The invoker is what the client uses to execute commands.
class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        System.out.println("Button pressed...");
        command.execute();
    }
    
    public void pressUndo() {
        System.out.println("Undo button pressed...");
        command.undo();
    }
}

// --- Demo ---
public class CommandDemo {
    public static void main(String[] args) {
        // 5. Client setup
        RemoteControl remote = new RemoteControl();
        Light livingRoomLight = new Light();
        Fan ceilingFan = new Fan();

        // --- Scenario 1: Turn on the light ---
        // Create a command and associate it with a receiver
        Command lightOn = new LightOnCommand(livingRoomLight);
        
        // Configure the invoker with the command
        remote.setCommand(lightOn);
        
        // Client triggers the execution
        remote.pressButton(); // Output: The light is on.
        remote.pressUndo();   // Output: The light is off.

        System.out.println("\n---------------------\n");

        // --- Scenario 2: Start the fan ---
        // Now, the same invoker can be used with a different command
        Command fanStart = new FanStartCommand(ceilingFan);
        remote.setCommand(fanStart);
        remote.pressButton(); // Output: The fan is spinning.
        remote.pressUndo();   // Output: The fan has stopped.
    }
}
```

## 7. Ứng dụng trong Spring Boot

*   **`JdbcTemplate` và `TransactionTemplate`:**
    *   Các "template" class của Spring thường sử dụng một biến thể của Command pattern. Chúng xử lý các logic lặp đi lặp lại (boilerplate code) như quản lý kết nối, transaction, exception handling, và cho phép bạn cung cấp một "command" dưới dạng một callback để thực thi logic nghiệp vụ cốt lõi.
    *   `TransactionTemplate.execute(TransactionCallback action)`: `TransactionCallback` chính là một command. Bạn đưa cho template một "công việc cần làm trong transaction", và template sẽ lo phần còn lại (bắt đầu, commit, rollback).

    ```java
    @Autowired
    private TransactionTemplate transactionTemplate;

    public void someTransactionalWork() {
        transactionTemplate.execute(status -> {
            // This lambda is the 'command'
            // Your business logic here...
            someRepository.save(someEntity);
            return null;
        });
    }
    ```
*   **`Runnable` và `Callable` trong `TaskExecutor`:**
    *   Khi bạn muốn thực thi một tác vụ bất đồng bộ trong Spring, bạn sẽ submit một `Runnable` hoặc `Callable` cho một `TaskExecutor`.
    *   `Runnable` và `Callable` chính là các Command object. Chúng đóng gói một hành động (`run()` hoặc `call()`) mà không có tham số. `TaskExecutor` (Invoker) sẽ lấy các command này từ một hàng đợi và thực thi chúng trên một luồng khác.

    ```java
    @Autowired
    private TaskExecutor taskExecutor;

    public void processDataAsync() {
        // The lambda is an implementation of the Runnable interface (a command)
        taskExecutor.execute(() -> {
            System.out.println("Processing data in a separate thread...");
            // Long running task
        });
    }
    ```

## 8. So sánh

*   **Command vs. Strategy:**
    *   **Mục đích:** Command đóng gói một **hành động** và các tham số của nó. Strategy đóng gói một **thuật toán**.
    *   **Vòng đời:** Command thường chỉ được thực thi một lần. Strategy có thể được sử dụng nhiều lần.
    *   **Receiver:** Command thường chứa một tham chiếu đến Receiver và gọi phương thức trên đó. Strategy thường được chính lớp Context (tương tự Receiver) implement và tự thực thi.
*   **Command vs. Observer:**
    *   Observer được kích hoạt khi có sự thay đổi trạng thái ở Subject. Giao tiếp là một-nhiều.
    *   Command được kích hoạt một cách tường minh bởi Invoker. Giao tiếp là một-một (Invoker-Command).

## 9. Interview Tips

*   **Câu hỏi:** "Command pattern là gì? Lợi ích chính của nó là gì?"
    *   **Trả lời:** "Command pattern đóng gói một yêu cầu thành một đối tượng độc lập. Lợi ích chính là nó tách biệt người gọi yêu cầu (Invoker) khỏi người thực thi (Receiver). Điều này cho phép chúng ta thực hiện các chức năng nâng cao như xếp hàng các yêu cầu, ghi log, và đặc biệt là undo/redo."
*   **Câu hỏi:** "Làm thế nào bạn sẽ implement chức năng undo/redo bằng Command pattern?"
    *   **Trả lời:** "Đầu tiên, interface Command cần có thêm một phương thức `undo()`. Mỗi concrete command sẽ implement logic để đảo ngược hành động của `execute()`. Sau đó, em sẽ dùng một Stack. Mỗi khi một command được thực thi, em sẽ đẩy nó vào stack. Khi người dùng nhấn Undo, em sẽ pop command từ stack ra và gọi phương thức `undo()` của nó."
*   **Câu hỏi:** "Bạn có thấy Command pattern ở đâu trong Spring không?"
    *   **Trả lời:** "Có ạ. Ví dụ rõ nhất là khi làm việc với các tác vụ bất đồng bộ. Khi em submit một `Runnable` hoặc `Callable` cho một `TaskExecutor`, các đối tượng `Runnable`/`Callable` đó chính là các command, đóng gói một công việc để `TaskExecutor` (invoker) thực thi sau này. Ngoài ra, các callback trong `TransactionTemplate` hay `JdbcTemplate` cũng hoạt động tương tự như một command."

