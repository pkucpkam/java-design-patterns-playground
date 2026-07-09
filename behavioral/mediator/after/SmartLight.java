package behavioral.mediator.after;

public class SmartLight extends SmartHomeColleague {
    private boolean on = false;

    public SmartLight(SmartHomeMediator mediator) {
        super(mediator);
    }

    public void turnOn() {
        on = true;
        System.out.println("SmartLight: Đã bật đèn.");
    }

    public void turnOff() {
        on = false;
        System.out.println("SmartLight: Đã tắt đèn.");
    }

    public boolean isOn() {
        return on;
    }
}
