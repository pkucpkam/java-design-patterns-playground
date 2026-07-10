# Memento Design Pattern (Mẫu Lưu Trữ Trạng Thái)

## Overview
**Memento** là một mẫu thiết kế thuộc nhóm **Behavioral** (Hành vi). Mẫu thiết kế này cho phép ghi lại và lưu trữ trạng thái nội bộ của một đối tượng tại một thời điểm nhất định để có thể khôi phục lại trạng thái đó sau này, đặc biệt là **không vi phạm nguyên tắc đóng gói (Encapsulation)** của Lập trình hướng đối tượng.

Trong Memento Pattern, có ba thành phần chính tham gia:
1. **Originator (Người khởi tạo)**: Đối tượng sở hữu trạng thái cần được sao lưu và khôi phục. Nó chịu trách nhiệm tạo ra `Memento` chứa thông tin trạng thái hiện tại và sử dụng `Memento` đó để khôi phục lại trạng thái cũ khi cần.
2. **Memento (Vật lưu trữ)**: Đối tượng lưu trữ trạng thái của `Originator`. Memento cung cấp hai loại quyền truy cập (interface):
   - *Giao diện hẹp (Narrow Interface)*: Dành cho Caretaker, chỉ cho phép truyền đối tượng đi mà không thể đọc hay thay đổi dữ liệu bên trong.
   - *Giao diện rộng (Wide Interface)*: Dành riêng cho Originator, cho phép Originator truy cập đầy đủ thông tin để khôi phục trạng thái.
3. **Caretaker (Người trông giữ)**: Đối tượng chịu trách nhiệm giữ các bản lưu `Memento` (thường thông qua Stack hoặc List). Caretaker không bao giờ được phép đọc hoặc thay đổi nội dung bên trong của các Memento.

---

## Problem

### What problem exists?
Giả sử chúng ta đang phát triển một **Trình soạn thảo văn bản (Text Editor)**. Trình soạn thảo này quản lý một đối tượng tài liệu (`Document`) bao gồm các thông tin: `title` (tiêu đề), `content` (nội dung) và `fontSize` (cỡ chữ). 

Khách hàng yêu cầu phát triển tính năng **Hoàn tác (Undo)**. Mỗi khi người dùng chỉnh sửa tài liệu và ấn Undo, tài liệu phải quay về trạng thái liền trước đó.

### Why traditional implementation fails?
Trong cách triển khai thông thường (trong [before/](file:///f:/Learning/java-design-patterns-playground/behavioral/memento/before/)), ta tạo ra lớp `DocumentState` chứa các trường tương tự `Document`. Khi cần lưu trạng thái, lớp quản lý lịch sử `History` (Caretaker) sẽ làm như sau:

```java
// Trong History.java (Trước refactor)
public void save(Document document) {
    DocumentState state = new DocumentState(
            document.getTitle(),
            document.getContent(),
            document.getFontSize()
    );
    push(state);
}
```

Cách làm này gặp các hạn chế nghiêm trọng sau:
1. **Vi phạm tính đóng gói (Encapsulation Violation)**: 
   - Để `History` có thể tạo `DocumentState`, `Document` bắt buộc phải cung cấp các hàm `get` cho các trường dữ liệu nội bộ.
   - Để khôi phục trạng thái cũ, `Document` bắt buộc phải phơi bày các hàm `set` công khai (`setTitle`, `setContent`, ...). Bên ngoài có thể can thiệp chỉnh sửa tùy ý.
   - Đối tượng trạng thái `DocumentState` phơi bày hết các trường dữ liệu. Một đối tượng bên ngoài có thể tự ý lấy nó từ lịch sử ra và sửa đổi giá trị bên trong trước khi kích hoạt hoàn tác (như đã chứng minh trong bài test `testBefore_EncapsulationViolation`).
2. **Liên kết chặt chẽ (Tight Coupling)**: 
   - Lớp `History` và `DocumentState` phụ thuộc trực tiếp vào cấu trúc bên trong của `Document`. Nếu sau này tài liệu có thêm các thuộc tính mới (như màu chữ, vị trí con trỏ chuột, v.v.), ta phải sửa cả ba lớp `Document`, `DocumentState` và `History`.

### Which SOLID principle is violated?
* **Single Responsibility Principle (SRP)**: Lớp `Document` vừa phải lo quản lý nghiệp vụ tài liệu của mình, vừa phải lo chuẩn bị giao tiếp thuộc tính cho bên ngoài lưu trữ.
* **Open/Closed Principle (OCP)**: Mỗi lần cấu trúc của `Document` thay đổi, ta buộc phải thay đổi code lưu trữ ở các lớp khác, tăng khả năng phát sinh lỗi trên các tính năng hiện tại.

---

## Solution

Memento giải quyết bài toán trên bằng cách **giao trách nhiệm tự chụp ảnh trạng thái** và **tự phục hồi trạng thái** cho chính lớp `Document` (Originator).
* Định nghĩa lớp `Memento` dưới dạng một **lớp nội bộ tĩnh (static inner class)** bên trong lớp `Document`.
* Thiết lập constructor và các thuộc tính của `Memento` là `private`. Do là inner class, `Document` vẫn có quyền đọc ghi trực tiếp các trường private này (Wide Interface), trong khi các lớp bên ngoài kể cả `History` hoàn toàn không có quyền truy cập (Narrow Interface).
* Khi lưu trạng thái, `Document` tự tạo ra một `Memento` mới từ chính dữ liệu của nó và trả ra ngoài dưới dạng tham chiếu mờ.
* Lớp `History` chỉ lưu giữ tham chiếu đến đối tượng `Memento` này trong một ngăn xếp (`Stack`), và khi hoàn tác chỉ cần chuyển trả lại đối tượng đó cho `Document` xử lý khôi phục.

---

## UML Diagram

```mermaid
classDiagram
    class Document {
        -String title
        -String content
        -int fontSize
        +save() Memento
        +restore(Memento memento) void
        +getTitle() String
        +setTitle(String) void
        +getContent() String
        +setContent(String) void
        +getFontSize() int
        +setFontSize(int) void
    }

    class Memento {
        -String title
        -String content
        -int fontSize
        -Memento(String title, String content, int fontSize)
    }

    class History {
        -Stack~Memento~ states
        +push(Memento memento) void
        +pop() Memento
    }

    Document ..> Memento : creates & restores
    Document +-- Memento : static inner class
    History --> Memento : stores
```

---

## Code Explanation

### 1. Pure Java Implementation (Sau khi áp dụng Memento)

* **Originator**: [Document.java](file:///f:/Learning/java-design-patterns-playground/behavioral/memento/after/Document.java)
  - Khai báo một static inner class `Memento` với các thuộc tính và constructor `private`.
  - Hàm `save()`: Khởi tạo đối tượng `Memento` bằng từ khóa `new Memento(...)`.
  - Hàm `restore(Memento)`: Lấy dữ liệu từ đối tượng `Memento` truyền vào để ghi đè lại các trường của chính mình.
* **Caretaker**: [History.java](file:///f:/Learning/java-design-patterns-playground/behavioral/memento/after/History.java)
  - Chứa một `Stack<Document.Memento>`.
  - Hoàn toàn độc lập với chi tiết cấu trúc của `Document` và không thể can thiệp vào dữ liệu bên trong của `Memento`.

Cách thức hoạt động:
```java
// Khởi tạo Document và History
Document doc = new Document("Tiêu đề", "Nội dung", 12);
History history = new History();

// Lưu trạng thái trước khi chỉnh sửa
history.push(doc.save());

// Thực hiện thay đổi dữ liệu tài liệu
doc.setContent("Nội dung mới chỉnh sửa...");

// Hoàn tác về trạng thái cũ
doc.restore(history.pop());
```

---

### 2. Spring Boot Implementation (Enterprise Pattern)

Trong môi trường Spring, mẫu thiết kế Memento thường được tích hợp vào các lớp Service để quản lý bản nháp (Autosave/Drafts) hoặc cơ chế rollback nghiệp vụ của nhiều tài liệu đồng thời.

* **Spring Originator**: [SpringDocument.java](file:///f:/Learning/java-design-patterns-playground/behavioral/memento/spring/SpringDocument.java) là một POJO thuần chứa thông tin tài liệu và lớp lồng Memento.
* **Spring Caretaker**: [SpringDocumentHistory.java](file:///f:/Learning/java-design-patterns-playground/behavioral/memento/spring/SpringDocumentHistory.java) được đánh dấu `@Component`. Sử dụng một `ConcurrentHashMap` map từ `documentId` sang `Stack<SpringDocument.Memento>` để lưu trữ lịch sử độc lập cho từng tài liệu của từng phiên làm việc của người dùng.
* **Spring Coordinator**: [DocumentService.java](file:///f:/Learning/java-design-patterns-playground/behavioral/memento/spring/DocumentService.java) được đánh dấu `@Service`. Nó tự động lưu trữ trạng thái hiện tại vào `SpringDocumentHistory` trước khi thực hiện chỉnh sửa qua phương thức `updateDocument`. Khi cần hoàn tác, client chỉ cần gọi `undo(documentId)`.

```java
@Service
public class DocumentService {
    @Autowired
    private SpringDocumentHistory history;
    private final Map<String, SpringDocument> activeDocuments = new ConcurrentHashMap<>();

    public void updateDocument(String id, String newTitle, String newContent, int newFontSize) {
        SpringDocument doc = activeDocuments.get(id);
        
        // Tự động sao lưu trạng thái trước khi thay đổi (Autosave)
        history.push(id, doc.save());

        // Cập nhật trạng thái mới
        doc.setTitle(newTitle);
        doc.setContent(newContent);
        doc.setFontSize(newFontSize);
    }

    public boolean undo(String id) {
        SpringDocument doc = activeDocuments.get(id);
        SpringDocument.Memento memento = history.pop(id);
        if (memento != null) {
            doc.restore(memento);
            return true;
        }
        return false;
    }
}
```

---

## Advantages & Disadvantages

### Advantages (Ưu điểm)
* **Bảo vệ tính đóng gói tối đa**: Dữ liệu lưu trữ trạng thái nằm hoàn toàn an toàn trong Memento và chỉ lớp Originator mới có thể đọc/ghi được dữ liệu này.
* **Giảm liên kết (Loose Coupling)**: Người giữ trạng thái (Caretaker) được giải phóng hoàn toàn khỏi các chi tiết triển khai cấu trúc của trạng thái.
* **Đơn giản hóa lớp Originator**: Giúp Originator không phải tự quản lý lịch sử các trạng thái đã qua của nó, trách nhiệm lưu giữ lịch sử đã được chuyển giao hoàn toàn cho Caretaker.

### Disadvantages (Nhược điểm)
* **Tiêu tốn bộ nhớ RAM**: Nếu đối tượng Originator có dung lượng trạng thái lớn và người dùng thực hiện lưu trữ liên tục (ví dụ: gõ phím lưu liên tục), số lượng Memento trong ngăn xếp của Caretaker sẽ phình to nhanh chóng, dễ gây ra tràn bộ nhớ (Out of Memory).
* **Chi phí xử lý sao chép (Overhead)**: Việc tạo ra các bản sao trạng thái của đối tượng có thể tiêu tốn nhiều thời gian CPU nếu đối tượng phức tạp.
* **Khó thu hồi bộ nhớ trong các ngôn ngữ không tự động dọn rác**: Caretaker cần có chiến lược dọn dẹp các Memento cũ hoặc đã hết hạn để tránh rò rỉ bộ nhớ (Memory Leak).

---

## Use Cases (Trường hợp áp dụng)
* **Tính năng Undo / Redo**: Trong các công cụ soạn thảo văn bản, IDE viết code, phần mềm chỉnh sửa đồ họa (Photoshop, Figma) hay các trò chơi điện tử để quay lại bước đi trước đó.
* **Quản lý Bản nháp / Autosave**: Tự động lưu lại trạng thái viết bài của người dùng trên web form để khôi phục khi gặp sự cố ngắt kết nối mạng hoặc tắt trình duyệt đột ngột.
* **Transaction Savepoint**: Thiết lập các điểm lưu trữ trong các phiên làm việc cơ sở dữ liệu để thực hiện rollback về điểm savepoint nhất định thay vì rollback toàn bộ transaction.
