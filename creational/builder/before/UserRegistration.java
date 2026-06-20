package creational.builder.before;

/**
 * Lớp UserRegistration trước khi áp dụng Builder Pattern.
 * Gặp vấn đề "Telescoping Constructor Anti-pattern" - quá tải constructor.
 * Đồng thời có quá nhiều setter phá vỡ tính bất biến (Immutability).
 */
public class UserRegistration {
    // Required fields
    private String username;
    private String email;
    private String password;

    // Optional fields
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;

    // 1. Telescoping Constructor:
    // Quá nhiều constructor để phục vụ các tổ hợp thuộc tính khác nhau
    public UserRegistration(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRegistration(String username, String email, String password, String firstName, String lastName) {
        this(username, email, password);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UserRegistration(String username, String email, String password, String firstName, String lastName, String phoneNumber, String address) {
        this(username, email, password, firstName, lastName);
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    // 2. JavaBean Pattern:
    // Sử dụng constructor rỗng và gọi setter liên tục, khiến đối tượng ở trạng thái 
    // không nhất quán trong quá trình khởi tạo và mất tính đóng gói.
    public UserRegistration() {
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
}
