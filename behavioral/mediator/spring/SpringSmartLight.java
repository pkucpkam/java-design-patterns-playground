package behavioral.mediator.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringSmartLight extends SpringSmartHomeColleague {
    private boolean on = false;

    public void turnOn() {
        on = true;
        System.out.println("SpringSmartLight: Đã bật đèn.");
    }

    public void turnOff() {
        on = false;
        System.out.println("SpringSmartLight: Đã tắt đèn.");
    }

    public boolean isOn() {
        return on;
    }
}
