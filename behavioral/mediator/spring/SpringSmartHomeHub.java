package behavioral.mediator.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class SpringSmartHomeHub implements SpringSmartHomeMediator {

    private SpringSmartLight light;
    private SpringAirConditioner airConditioner;
    private SpringSiren siren;
    private SpringSprinkler sprinkler;

    @Autowired
    public void registerDevices(@Lazy SpringSmartLight light,
                                @Lazy SpringAirConditioner airConditioner,
                                @Lazy SpringSiren siren,
                                @Lazy SpringSprinkler sprinkler) {
        this.light = light;
        this.airConditioner = airConditioner;
        this.siren = siren;
        this.sprinkler = sprinkler;
    }

    @Override
    public void notify(Object colleague, String event) {
        if ("motionDetected".equalsIgnoreCase(event)) {
            System.out.println("Spring Mediator: Nhận tin báo phát hiện chuyển động từ " + colleague.getClass().getSimpleName());
            if (light != null) light.turnOn();
            if (airConditioner != null) airConditioner.turnOn();
        } else if ("smokeDetected".equalsIgnoreCase(event)) {
            System.out.println("Spring Mediator: CẢNH BÁO khói từ " + colleague.getClass().getSimpleName() + "! Kích hoạt quy trình khẩn cấp.");
            if (siren != null) siren.startSounding();
            if (sprinkler != null) sprinkler.startWatering();
            if (airConditioner != null) airConditioner.turnOff();
            if (light != null) light.turnOn();
        }
    }
}
