package behavioral.mediator.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringSmokeSensor extends SpringSmartHomeColleague {
    public void detectSmoke() {
        System.out.println("SpringSmokeSensor: Phát hiện khói! Gửi thông báo khẩn cấp đến Spring Mediator.");
        mediator.notify(this, "smokeDetected");
    }
}
