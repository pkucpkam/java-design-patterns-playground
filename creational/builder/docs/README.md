# Builder Pattern

## Overview
**Builder Pattern** là một design pattern thuộc nhóm Creational Pattern. Nó tách rời quá trình xây dựng một đối tượng phức tạp khỏi biểu diễn của nó, cho phép cùng một tiến trình xây dựng có thể tạo ra các biểu diễn khác nhau. Mẫu thiết kế này cho phép bạn tạo ra các đối tượng phức tạp theo từng bước một (step-by-step).

## Problem Description
Khi một lớp (class) có quá nhiều thuộc tính, trong đó có một vài thuộc tính là bắt buộc (required) và rất nhiều thuộc tính là tùy chọn (optional). Cách tiếp cận truyền thống thường gặp hai vấn đề lớn:

1. **Telescoping Constructor Anti-pattern**: 
   Bạn sẽ phải tạo ra hàng loạt constructor để phục vụ cho các tổ hợp tham số khác nhau. Điều này khiến code khó đọc, khó bảo trì và dễ gây lỗi (ví dụ: truyền nhầm vị trí của 2 tham số cùng kiểu `String`).
2. **JavaBean Pattern (Sử dụng Setters)**:
   Để tránh over-loading constructors, người ta tạo một constructor rỗng và dùng setters. Tuy nhiên, cách này làm đối tượng mất đi tính **bất biến (Immutability)** và có thể đặt đối tượng vào trạng thái **không nhất quán** trong quá trình gán giá trị nếu xảy ra lỗi giữa chừng hoặc trong môi trường đa luồng (multi-threading).

**SOLID Principle Violation**:
Việc thiết kế constructor với quá nhiều tham số thường vi phạm **Single Responsibility Principle (SRP)** ở chỗ lớp đó vừa phải đảm nhiệm logic nghiệp vụ (business logic) vừa phải đảm nhận sự phức tạp trong quá trình tạo mới chính nó.

## Solution
Sử dụng **Builder Pattern**:
1. Tạo một lớp static nested class tên là `Builder` nằm bên trong lớp đối tượng cần tạo (VD: `UserRegistration`).
2. `Builder` sẽ chứa toàn bộ các thuộc tính giống hệt lớp chính.
3. Các thuộc tính bắt buộc sẽ được truyền thông qua constructor của `Builder`.
4. Các thuộc tính tùy chọn sẽ được gán thông qua các phương thức setter của `Builder`, các phương thức này trả về chính đối tượng `Builder` (`return this`) để tạo ra **Fluent API** (method chaining).
5. Cuối cùng, phương thức `build()` sẽ gọi constructor private của lớp chính, truyền `Builder` vào và trả về đối tượng hoàn chỉnh.
6. Lớp chính chỉ cung cấp **getter**, không cung cấp **setter** để đảm bảo tính bất biến.

## UML Diagram

```mermaid
classDiagram
    class UserRegistration {
        -String username
        -String email
        -String password
        -String firstName
        -String lastName
        -String phoneNumber
        -String address
        -UserRegistration(Builder builder)
        +getUsername() String
        +getEmail() String
        +getPassword() String
        +getFirstName() String
        +getLastName() String
        +getPhoneNumber() String
        +getAddress() String
    }
    
    class Builder {
        -String username
        -String email
        -String password
        -String firstName
        -String lastName
        -String phoneNumber
        -String address
        +Builder(String username, String email, String password)
        +firstName(String firstName) Builder
        +lastName(String lastName) Builder
        +phoneNumber(String phoneNumber) Builder
        +address(String address) Builder
        +build() UserRegistration
    }
    
    Builder ..> UserRegistration : builds
    UserRegistration +-- Builder : inner class
```

## Advantages
* **Đóng gói code khởi tạo**: Tách biệt logic khởi tạo đối tượng phức tạp.
* **Tính bất biến (Immutability)**: Không cần đến các hàm setters. Sau khi đối tượng được tạo thông qua `build()`, nó không thể bị thay đổi.
* **Code dễ đọc, dễ bảo trì**: Cú pháp Method Chaining (Fluent API) rõ ràng, chỉ định trực tiếp thuộc tính nào đang được gán.
* **Kiểm soát quy trình tạo**: Cho phép validate dữ liệu và trạng thái ngay bên trong hàm `build()` trước khi đối tượng thực sự được tạo ra.

## Disadvantages
* Làm tăng số lượng dòng code do phải nhân đôi các thuộc tính ở cả class chính và class Builder.
* Khó áp dụng nếu các thuộc tính của đối tượng thay đổi liên tục hoặc phụ thuộc lẫn nhau phức tạp ngoài tầm kiểm soát của method chaining.

## Use Cases (Real-world Example)
* **User Registration**: Hệ thống đăng ký tài khoản yêu cầu thông tin cơ bản (Username, Email, Password) bắt buộc, cùng với hàng loạt thông tin cá nhân tùy chọn khác (First Name, Last Name, Phone, Address...).
* Xây dựng các đối tượng HTTP Request/Response phức tạp trong các thư viện (ví dụ: `HttpRequest.Builder` trong Java 11).
* Xây dựng các đối tượng Configuration.

## Related Patterns
* **Abstract Factory** & **Factory Method**: Builder tập trung vào việc tạo ra đối tượng phức tạp từng bước một, trong khi Abstract/Factory Method tập trung vào việc tạo ra các nhóm đối tượng cụ thể (thường trong một bước).
* **Singleton**: Lớp Builder đôi khi có thể áp dụng chung với Singleton nếu nó được dùng để tạo cấu hình ứng dụng duy nhất.
* **Composite**: Thường sử dụng Builder để cấu trúc cây đối tượng phức tạp của pattern Composite.

## References
* "Design Patterns: Elements of Reusable Object-Oriented Software" - Gang of Four (GoF)
* "Effective Java" (Item 2: Consider a builder when faced with many constructor parameters) - Joshua Bloch
