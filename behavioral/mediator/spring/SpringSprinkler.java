package behavioral.mediator.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringSprinkler extends SpringSmartHomeColleague {
    private boolean watering = false;

    public void startWatering() {
        watering = true;
        System.out.println("SpringSprinkler: Hệ thống phun nước BẮT ĐẦU hoạt động!");
    }

    public void stopWatering() {
        watering = false;
        System.out.println("SpringSprinkler: Hệ thống phun nước ĐÃ DỪNG.");
    }

    public boolean isWatering() {
        return watering;
    }
}
