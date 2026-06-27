# 🗺️ Design Patterns Learning Roadmap

Lộ trình chi tiết tìm hiểu và hiện thực hóa các mẫu thiết kế (Design Patterns) trong Java. Dưới đây là danh sách 23 mẫu thiết kế phổ biến chia làm 3 nhóm chính:

---

## 🟩 1. Creational Patterns (Nhóm Khởi Tạo)
*Các pattern này liên quan đến quá trình khởi tạo đối tượng, giúp giảm sự phụ thuộc và tăng tính linh hoạt khi tạo đối tượng mới.*

- [x] **Singleton** - Đảm bảo một class chỉ có duy nhất một instance và cung cấp một điểm truy cập toàn cục tới nó.
- [x] **Factory Method** - Định nghĩa một interface để tạo đối tượng, nhưng để các subclass quyết định class nào sẽ được khởi tạo.
- [x] **Abstract Factory** - Cung cấp một interface để tạo các họ đối tượng liên quan hoặc phụ thuộc lẫn nhau mà không chỉ định các class cụ thể.
- [x] **Builder** - Tách biệt việc xây dựng một đối tượng phức tạp khỏi biểu diễn của nó để cùng một quá trình xây dựng có thể tạo ra các biểu diễn khác nhau.
- [x] **Prototype** - Xác định các loại đối tượng cần tạo bằng cách sử dụng một instance mẫu (prototype), và tạo các đối tượng mới bằng cách sao chép (copy) mẫu này.

---

## 🟦 2. Structural Patterns (Nhóm Cấu Trúc)
*Các pattern này tập trung vào việc tổ chức và kết hợp các class, object lại với nhau để tạo nên cấu trúc lớn hơn, linh hoạt hơn và hiệu quả hơn.*

- [x] **Adapter** - Cho phép các interface không tương thích có thể làm việc cùng nhau.
- [x] **Bridge** - Tách biệt phần trừu tượng (abstraction) khỏi phần triển khai (implementation) để cả hai có thể thay đổi độc lập.
- [ ] **Composite** - Gom nhóm các đối tượng thành cấu trúc dạng cây để biểu diễn các phân cấp một phần - toàn bộ (part-whole hierarchies).
- [ ] **Decorator** - Gắn thêm các nhiệm vụ/hành vi bổ sung cho đối tượng một cách động tại runtime.
- [ ] **Facade** - Cung cấp một interface thống nhất, đơn giản hóa việc tương tác với một hệ thống con (subsystem) phức tạp.
- [ ] **Flyweight** - Tiết kiệm bộ nhớ bằng cách chia sẻ tài nguyên dùng chung giữa lượng lớn đối tượng tương tự nhau.
- [ ] **Proxy** - Cung cấp một đối tượng đại diện hoặc thay thế để kiểm soát quyền truy cập tới đối tượng thực sự.

---

## 🟨 3. Behavioral Patterns (Nhóm Hành Vi)
*Các pattern này tập trung vào thuật toán và sự tương tác/giao tiếp giữa các đối tượng để thực hiện các nhiệm vụ chung.*

- [ ] **Strategy** - Định nghĩa một tập hợp các thuật toán, đóng gói từng thuật toán lại và giúp chúng có thể hoán đổi cho nhau một cách linh hoạt.
- [ ] **Observer** - Định nghĩa mối quan hệ một-nhiều giữa các đối tượng, sao cho khi một đối tượng thay đổi trạng thái, tất cả các đối tượng phụ thuộc sẽ được thông báo và tự động cập nhật.
- [ ] **Command** - Đóng gói một yêu cầu thành một đối tượng, cho phép bạn tham số hóa các client với các yêu cầu khác nhau.
- [ ] **State** - Cho phép một đối tượng thay đổi hành vi của nó khi trạng thái nội bộ của nó thay đổi.
- [ ] **Chain of Responsibility** - Tránh sự liên kết trực tiếp giữa bên gửi yêu cầu và bên nhận bằng cách cho phép nhiều đối tượng có cơ hội xử lý yêu cầu đó trên một chuỗi tuần tự.
- [ ] **Template Method** - Định nghĩa khung xương (skeleton) của một thuật toán trong một phương thức, nhường việc triển khai các bước chi tiết cho các subclass.
- [ ] **Mediator** - Định nghĩa một đối tượng điều phối trung gian giúp đóng gói cách một tập hợp các đối tượng tương tác với nhau.
- [ ] **Memento** - Lưu giữ lại trạng thái nội bộ của đối tượng để có thể khôi phục lại trạng thái đó sau này mà không vi phạm tính đóng gói.
- [ ] **Iterator** - Cung cấp một cách thức để truy cập tuần tự vào các phần tử của một đối tượng tập hợp mà không cần để lộ cấu trúc bên dưới của nó.
- [ ] **Visitor** - Biểu diễn một thao tác/hành vi cần thực hiện trên các phần tử của một cấu trúc đối tượng mà không làm thay đổi các class của các phần tử đó.
