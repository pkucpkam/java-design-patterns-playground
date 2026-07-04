# Template Method Pattern

## 1. Tổng quan

*   **Định nghĩa:** Template Method là một mẫu thiết kế thuộc nhóm Behavioral, định nghĩa bộ khung (skeleton) của một thuật toán trong một phương thức của lớp cha, và cho phép các lớp con định nghĩa lại (override) một số bước của thuật toán đó mà không cần thay đổi cấu trúc của thuật toán.
*   **Vấn đề giải quyết:**
    *   **Tránh trùng lặp code:** Khi bạn có nhiều thuật toán tương tự nhau nhưng chỉ khác ở một vài bước chi tiết, bạn có thể gom phần code chung vào một "template method" ở lớp cha và tách các bước khác biệt ra các phương thức trừu tượng (abstract) để các lớp con implement.
    *   **Cung cấp một khung sườn cố định:** Đảm bảo rằng một thuật toán luôn tuân theo một chuỗi các bước nhất định, nhưng vẫn cho phép các bước đó được tùy chỉnh.

## 2. Ví dụ đời thường

Hãy tưởng tượng quy trình xây dựng một ngôi nhà. Dù là nhà gỗ hay nhà bê tông, quy trình chung (template method) đều như nhau:

1.  **Chuẩn bị móng (prepareFoundation)**
2.  **Xây tường (buildWalls)**
3.  **Lợp mái (buildRoof)**
4.  **Lắp cửa và cửa sổ (installDoorsAndWindows)**

Đây là **template method `buildHouse()`**.

*   Lớp cha `HouseBuilder` sẽ định nghĩa phương thức `buildHouse()` này.
*   Các bước như "Chuẩn bị móng" và "Lắp cửa" có thể là chung cho mọi loại nhà.
*   Tuy nhiên, các bước `buildWalls()` và `buildRoof()` sẽ khác nhau:
    *   `WoodenHouseBuilder` (lớp con) sẽ implement `buildWalls()` bằng cách dựng các khung gỗ.
    *   `ConcreteHouseBuilder` (lớp con) sẽ implement `buildWalls()` bằng cách đổ bê tông.

Template Method cho phép các lớp con "điền vào chỗ trống" trong một thuật toán đã có khung sườn.

## 3. Khi nào nên dùng

*   **Khi bạn muốn các lớp con có thể mở rộng một thuật toán mà không cần thay đổi cấu trúc của nó.**
*   **Khi bạn có nhiều lớp thực hiện các thuật toán tương tự nhau, với một vài khác biệt nhỏ.** Template Method giúp bạn gom phần chung lại và loại bỏ code trùng lặp.
*   **Khi bạn muốn kiểm soát việc mở rộng của các lớp con:** Bạn có thể định nghĩa một template method là `final` để các lớp con không thể thay đổi thứ tự các bước, mà chỉ có thể thay đổi cách implement của từng bước.

## 4. Khi nào KHÔNG nên dùng

*   **Khi các thuật toán quá khác biệt:** Nếu các biến thể của thuật toán không có cùng cấu trúc và thứ tự các bước, việc cố gắng ép chúng vào một template sẽ không hợp lý. Pattern **Strategy** có thể phù hợp hơn trong trường hợp này.
*   **Khi logic quá đơn giản:** Nếu thuật toán chỉ có một vài bước và không có nhiều biến thể, việc tạo ra một hệ thống phân cấp lớp có thể là không cần thiết.

## 5. Cách hoạt động

1.  **Abstract Class (Lớp trừu tượng):**
    *   Chứa một `templateMethod()`. Phương thức này nên được định nghĩa là `final` để các lớp con không thể override nó.
    *   `templateMethod()` sẽ gọi một chuỗi các phương thức "bước" (step methods) theo một thứ tự cụ thể.
    *   Các phương thức bước này có thể là:
        *   `abstract`: Các lớp con **bắt buộc** phải implement.
        *   `hook` (móc): Các phương thức có một implement mặc định (thường là rỗng). Các lớp con **có thể** override nếu cần tùy chỉnh. Hooks cung cấp một điểm mở rộng tùy chọn.
2.  **Concrete Classes (Lớp cụ thể):**
    *   Kế thừa từ Abstract Class.
    *   Implement các bước trừu tượng bắt buộc.
    *   (Tùy chọn) Override các hook để thêm các hành vi tùy chỉnh.

**Flow hoạt động:**
Client -> `Gọi templateMethod() trên một đối tượng Concrete Class` -> `templateMethod() trong lớp cha bắt đầu chạy` -> `Nó lần lượt gọi các phương thức bước` -> `Nếu phương thức bước được implement ở lớp con, phiên bản đó sẽ được thực thi`.

## 6. Code ví dụ (Java)

Ví dụ về một quy trình xử lý dữ liệu, bao gồm đọc, xử lý, và lưu.

```java
// File: examples/java/behavioral/TemplateMethodDemo.java
package behavioral;

// 1. The Abstract Class
abstract class DataProcessor {

    // 1a. The final template method that defines the skeleton of the algorithm.
    public final void processData() {
        readData();
        processInternal();
        if (isValidationNeeded()) { // A hook
            validateData();
        }
        saveData();
    }

    // 1b. Abstract "step" methods that must be implemented by subclasses.
    protected abstract void readData();
    protected abstract void processInternal();
    
    // 1c. A concrete method, common for all subclasses.
    private void saveData() {
        System.out.println("Saving data to the database...");
    }

    // 1d. A "hook" method. Subclasses can override it, but it's optional.
    protected boolean isValidationNeeded() {
        return true; // Default is to perform validation.
    }
    
    private void validateData() {
        System.out.println("Performing data validation...");
    }
}

// 2. Concrete Classes
class CsvDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from a CSV file.");
    }

    @Override
    protected void processInternal() {
        System.out.println("Processing CSV data (e.g., parsing rows and columns).");
    }
}

class JsonDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("Reading data from a JSON file.");
    }

    @Override
    protected void processInternal() {
        System.out.println("Processing JSON data (e.g., parsing objects and arrays).");
    }
    
    // Overriding a hook method
    @Override
    protected boolean isValidationNeeded() {
        System.out.println("JSON processor says validation is not needed.");
        return false;
    }
}

// --- Demo ---
public class TemplateMethodDemo {
    public static void main(String[] args) {
        System.out.println("--- Processing CSV file ---");
        DataProcessor csvProcessor = new CsvDataProcessor();
        csvProcessor.processData(); // Client calls the template method

        System.out.println("\n--- Processing JSON file ---");
        DataProcessor jsonProcessor = new JsonDataProcessor();
        jsonProcessor.processData();
    }
}
```

## 7. Ứng dụng trong Spring Boot

Template Method là một pattern được sử dụng **rất rộng rãi** trong lõi của Spring Framework để giảm code lặp lại và cung cấp các điểm mở rộng.

*   **`JdbcTemplate`, `RestTemplate`, `JmsTemplate`, etc.:**
    *   Đây là những ví dụ kinh điển nhất. Các lớp "Template" này của Spring quản lý các tài nguyên và xử lý các boilerplate code (code lặp đi lặp lại) như mở/đóng kết nối, xử lý exception, quản lý transaction.
    *   Chúng cung cấp một "template" cho một quy trình, và bạn chỉ cần cung cấp phần code nghiệp vụ cốt lõi thông qua các **callback interface** (ví dụ: `RowMapper`, `PreparedStatementSetter`).
    *   Mặc dù cách triển khai này sử dụng callback (một dạng của Strategy) thay vì kế thừa, nhưng triết lý của nó hoàn toàn là của Template Method: định nghĩa một bộ khung và để người dùng "điền vào chỗ trống".

    ```java
    // JdbcTemplate's query method is a template method
    jdbcTemplate.query("SELECT * FROM users", 
        // RowMapper is the "step" that you provide
        (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name")) 
    );
    ```
*   **`AbstractController` trong Spring MVC cũ:**
    *   Trong các phiên bản cũ của Spring, bạn có thể kế thừa từ `AbstractController` và chỉ cần implement phương thức `handleRequestInternal(...)`. Phương thức `handleRequest(...)` của lớp cha chính là template method, nó thực hiện các việc kiểm tra chung trước khi gọi đến `handleRequestInternal`.

*   **Spring Security `WebSecurityConfigurerAdapter` (cũ):**
    *   Trước Spring Boot 2.7, `WebSecurityConfigurerAdapter` là một ví dụ điển hình. Bạn kế thừa từ nó và override các phương thức `configure(HttpSecurity http)` hoặc `configure(AuthenticationManagerBuilder auth)` để tùy chỉnh các bước trong quy trình cấu hình bảo mật. Lớp cha đã định nghĩa sẵn bộ khung cấu hình.

## 8. So sánh

*   **Template Method vs. Strategy:**
    *   **Cơ chế:** Template Method dùng **kế thừa**. Strategy dùng **composition**.
    *   **Phạm vi:** Template Method thay đổi một phần của một thuật toán lớn. Strategy thay đổi **toàn bộ** thuật toán.
    *   **Linh hoạt:** Strategy linh hoạt hơn vì bạn có thể thay đổi strategy lúc runtime. Với Template Method, sự lựa chọn là cố định tại compile time (bạn chọn lớp con nào để khởi tạo).
*   **Template Method vs. Factory Method:**
    *   Cả hai đều dựa trên kế thừa.
    *   Template Method định nghĩa bộ khung của một **thuật toán**.
    *   Factory Method định nghĩa một phương thức để **tạo đối tượng**, cho phép lớp con quyết định tạo lớp cụ thể nào. Một Template Method có thể gọi một Factory Method bên trong nó.

## 9. Interview Tips

*   **Câu hỏi:** "Template Method là gì?"
    *   **Trả lời:** "Đây là một behavioral pattern, nơi một phương thức ở lớp cha định nghĩa bộ khung của một thuật toán nhưng để các lớp con định nghĩa lại chi tiết của một vài bước. Nó giúp tái sử dụng code và đảm bảo một quy trình luôn tuân thủ các bước nhất định."
*   **Câu hỏi:** "Hãy cho một ví dụ về Template Method trong Spring."
    *   **Trả lời:** "Các lớp template của Spring như `JdbcTemplate` hay `RestTemplate` là ví dụ điển hình. `JdbcTemplate` xử lý tất cả các boilerplate code như tạo kết nối, thực thi câu lệnh, xử lý exception, và đóng kết nối. Nó cung-cấp một "khung sườn", và chúng ta chỉ cần cung cấp các chi tiết, ví dụ như `RowMapper` để định nghĩa cách map một dòng kết quả thành một đối tượng Java. Về mặt triết lý, đây chính là Template Method."
*   **Câu hỏi:** "Bạn sẽ chọn Template Method hay Strategy?"
    *   **Trả lời:** "Em sẽ chọn Template Method khi em có nhiều thuật toán có cùng cấu trúc nhưng chỉ khác nhau ở các bước chi tiết. Em sẽ chọn Strategy khi các thuật toán hoàn toàn khác nhau và em muốn có khả năng chuyển đổi giữa chúng một cách linh hoạt lúc runtime. Về cơ bản, Template Method dùng kế thừa, còn Strategy dùng composition."

