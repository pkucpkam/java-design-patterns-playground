package behavioral.mediator.after;

public class SmokeSensor extends SmartHomeColleague {
    public SmokeSensor(SmartHomeMediator mediator) {
        super(mediator);
    }

    public void detectSmoke() {
        System.out.println("SmokeSensor: Phát hiện khói! Gửi thông báo khẩn cấp đến Mediator.");
        mediator.notify(this, "smokeDetected");
    }
}
