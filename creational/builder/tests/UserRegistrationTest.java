package creational.builder.tests;

import creational.builder.after.UserRegistration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserRegistrationTest {

    @Test
    public void testHappyPath_AllFields() {
        UserRegistration user = new UserRegistration.Builder("johndoe", "john@example.com", "securePass123")
                .firstName("John")
                .lastName("Doe")
                .phoneNumber("0123456789")
                .address("123 Main St, City")
                .build();

        assertEquals("johndoe", user.getUsername());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("securePass123", user.getPassword());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals("0123456789", user.getPhoneNumber());
        assertEquals("123 Main St, City", user.getAddress());
    }

    @Test
    public void testEdgeCase_OnlyRequiredFields() {
        UserRegistration user = new UserRegistration.Builder("janedoe", "jane@example.com", "securePass456").build();

        assertEquals("janedoe", user.getUsername());
        assertEquals("jane@example.com", user.getEmail());
        assertEquals("securePass456", user.getPassword());
        // Các thuộc tính optional sẽ là null do chưa được gán
        assertNull(user.getFirstName());
        assertNull(user.getLastName());
        assertNull(user.getPhoneNumber());
        assertNull(user.getAddress());
    }

    @Test
    public void testFailureCase_MissingRequiredFields() {
        // Test null username
        Exception ex1 = assertThrows(IllegalArgumentException.class, () -> {
            new UserRegistration.Builder(null, "test@example.com", "pass123");
        });
        assertEquals("Username is required", ex1.getMessage());

        // Test rỗng email
        Exception ex2 = assertThrows(IllegalArgumentException.class, () -> {
            new UserRegistration.Builder("user", "", "pass123");
        });
        assertEquals("Email is required", ex2.getMessage());

        // Test chuỗi khoảng trắng password
        Exception ex3 = assertThrows(IllegalArgumentException.class, () -> {
            new UserRegistration.Builder("user", "test@example.com", "   ");
        });
        assertEquals("Password is required", ex3.getMessage());
    }
}
