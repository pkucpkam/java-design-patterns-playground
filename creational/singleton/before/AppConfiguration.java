package creational.singleton.before;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AppConfiguration {
    private final Properties properties = new Properties();

    public AppConfiguration() {
        // Mỗi lần khởi tạo lại thực hiện đọc file cấu hình từ đĩa cứng (gây tốn tài nguyên và I/O)
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                // Giả lập load default properties nếu không tìm thấy file
                properties.setProperty("db.url", "jdbc:mysql://localhost:3306/dev_db");
                properties.setProperty("db.user", "root");
                properties.setProperty("db.password", "password");
            } else {
                properties.load(input);
            }
            // Giả lập thời gian đọc file tốn kém
            Thread.sleep(100); 
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
    
    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }
}
