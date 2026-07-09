package behavioral.mediator.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringSiren extends SpringSmartHomeColleague {
    private boolean sounding = false;

    public void startSounding() {
        sounding = true;
        System.out.println("SpringSiren: Còi báo động BẮT ĐẦU kêu cứu hỏa!");
    }

    public void stopSounding() {
        sounding = false;
        System.out.println("SpringSiren: Còi báo động ĐÃ TẮT.");
    }

    public boolean isSounding() {
        return sounding;
    }
}
