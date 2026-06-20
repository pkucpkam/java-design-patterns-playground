package creational.builder.after;

/**
 * Lớp UserRegistration sau khi áp dụng Builder Pattern.
 * Đảm bảo tính bất biến (Immutability), hỗ trợ API Fluent mượt mà,
 * và giải quyết vấn đề Telescoping Constructor.
 */
public class UserRegistration {
    // Các thuộc tính final để đảm bảo tính bất biến
    private final String username; // required
    private final String email;    // required
    private final String password; // required
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String address;

    // Constructor private chỉ cho phép Builder gọi
    private UserRegistration(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.address = builder.address;
    }

    // Chỉ cung cấp Getters, KHÔNG có Setters
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }

    /**
     * Lớp Builder nội bộ tĩnh (Static Inner Builder Class).
     */
    public static class Builder {
        // Required parameters
        private final String username;
        private final String email;
        private final String password;

        // Optional parameters (Khởi tạo giá trị mặc định nếu cần)
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String address;

        // Constructor của Builder bắt buộc phải có các tham số required
        public Builder(String username, String email, String password) {
            if (username == null || username.isBlank()) {
                throw new IllegalArgumentException("Username is required");
            }
            if (email == null || email.isBlank()) {
                throw new IllegalArgumentException("Email is required");
            }
            if (password == null || password.isBlank()) {
                throw new IllegalArgumentException("Password is required");
            }

            this.username = username;
            this.email = email;
            this.password = password;
        }

        // Các phương thức setter cho tham số optional, trả về chính Builder (Fluent API)
        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        // Phương thức build() để tạo đối tượng cuối cùng
        public UserRegistration build() {
            // Có thể thêm custom validation logic ở đây trước khi tạo đối tượng
            return new UserRegistration(this);
        }
    }
}
