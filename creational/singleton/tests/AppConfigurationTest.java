package creational.singleton.tests;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AppConfigurationTest {

    @Test
    public void testBeforeRefactoringCreatesMultipleInstances() {
        creational.singleton.before.AppConfiguration config1 = new creational.singleton.before.AppConfiguration();
        creational.singleton.before.AppConfiguration config2 = new creational.singleton.before.AppConfiguration();

        // Kiểm tra xem hai instance có phải là hai đối tượng khác nhau trên Heap không
        assertNotSame(config1, config2, "Trước khi refactor, mỗi lần khởi tạo bằng new sẽ tạo ra các đối tượng khác nhau");

        // Thay đổi giá trị ở instance 1 nhưng không phản ánh lên instance 2
        config1.setProperty("db.user", "admin");
        assertNotEquals(config1.getProperty("db.user"), config2.getProperty("db.user"), "Thay đổi thuộc tính trên instance này không ảnh hưởng đến instance khác");
    }

    @Test
    public void testAfterRefactoringAlwaysReturnsSameInstance() {
        creational.singleton.after.AppConfiguration config1 = creational.singleton.after.AppConfiguration.getInstance();
        creational.singleton.after.AppConfiguration config2 = creational.singleton.after.AppConfiguration.getInstance();

        // Đảm bảo cùng tham chiếu đến một đối tượng duy nhất
        assertSame(config1, config2, "Sau khi refactor, cả hai tham chiếu phải trỏ đến cùng một đối tượng");

        // Kiểm tra chia sẻ trạng thái
        config1.setProperty("db.user", "admin");
        assertEquals("admin", config2.getProperty("db.user"), "Thay đổi thuộc tính ở instance 1 phải được cập nhật ở instance 2");
    }

    @Test
    public void testMultiThreadedAccessReturnsSameInstance() throws InterruptedException {
        final creational.singleton.after.AppConfiguration[] instances = new creational.singleton.after.AppConfiguration[2];
        
        Thread thread1 = new Thread(() -> instances[0] = creational.singleton.after.AppConfiguration.getInstance());
        Thread thread2 = new Thread(() -> instances[1] = creational.singleton.after.AppConfiguration.getInstance());

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        assertSame(instances[0], instances[1], "Trong môi trường đa luồng, Singleton vẫn phải đảm bảo chỉ tạo duy nhất 1 instance");
    }
}
