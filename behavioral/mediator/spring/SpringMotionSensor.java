package behavioral.mediator.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringMotionSensor extends SpringSmartHomeColleague {
    public void detectMotion() {
        System.out.println("SpringMotionSensor: Phát hiện chuyển động! Gửi thông báo đến Spring Mediator.");
        mediator.notify(this, "motionDetected");
    }
}
