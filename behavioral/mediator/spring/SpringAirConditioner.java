package behavioral.mediator.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringAirConditioner extends SpringSmartHomeColleague {
    private boolean on = false;
    private int temperature = 24;

    public void turnOn() {
        on = true;
        System.out.println("SpringAirConditioner: Đã bật điều hòa.");
    }

    public void turnOff() {
        on = false;
        System.out.println("SpringAirConditioner: Đã tắt điều hòa.");
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
        System.out.println("SpringAirConditioner: Đã đặt nhiệt độ thành " + temp + "°C.");
    }

    public boolean isOn() {
        return on;
    }

    public int getTemperature() {
        return temperature;
    }
}
