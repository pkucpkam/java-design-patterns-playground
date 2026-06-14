package creational.singleton.after;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {
    private final Properties properties = new Properties();

    // Private Constructor ngăn chặn việc khởi tạo từ bên ngoài
    private AppConfiguration() {
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                // Giả lập load default properties nếu không tìm thấy file
                properties.setProperty("db.url", "jdbc:mysql://localhost:3306/dev_db");
                properties.setProperty("db.user", "root");
                properties.setProperty("db.password", "password");
            } else {
                properties.load(input);
            }
            // Giả lập thời gian load tài nguyên tốn kém (chỉ chạy duy nhất 1 lần)
            Thread.sleep(100);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Bill Pugh Singleton Implementation - Luôn thread-safe và lazy loading hiệu quả nhất trong Java
    private static class HelperHolder {
        private static final AppConfiguration INSTANCE = new AppConfiguration();
    }

    // Điểm truy cập toàn cục duy nhất
    public static AppConfiguration getInstance() {
        return HelperHolder.INSTANCE;
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
