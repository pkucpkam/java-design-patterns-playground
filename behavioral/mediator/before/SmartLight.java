package behavioral.mediator.before;

public class SmartLight {
    private boolean on = false;

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
