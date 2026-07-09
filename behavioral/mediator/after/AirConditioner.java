package behavioral.mediator.after;

public class AirConditioner extends SmartHomeColleague {
    private boolean on = false;
    private int temperature = 24;

    public AirConditioner(SmartHomeMediator mediator) {
        super(mediator);
    }

    public void turnOn() {
        on = true;
        System.out.println("AirConditioner: Đã bật điều hòa.");
    }

    public void turnOff() {
        on = false;
        System.out.println("AirConditioner: Đã tắt điều hòa.");
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("AirConditioner: Đã đặt nhiệt độ thành " + temp + "°C.");
    }

    public boolean isOn() {
        return on;
    }

    public int getTemperature() {
        return temperature;
    }
}
