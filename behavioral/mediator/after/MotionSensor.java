package behavioral.mediator.after;

public class MotionSensor extends SmartHomeColleague {
    public MotionSensor(SmartHomeMediator mediator) {
        super(mediator);
    }

    public void detectMotion() {
        System.out.println("MotionSensor: Phát hiện chuyển động! Gửi thông báo đến Mediator.");
        mediator.notify(this, "motionDetected");
    }
}
