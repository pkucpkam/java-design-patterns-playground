package behavioral.mediator.after;

public class SmartHomeHub implements SmartHomeMediator {
    private SmartLight light;
    private AirConditioner airConditioner;
    private Siren siren;
    private Sprinkler sprinkler;

    public void setLight(SmartLight light) {
        this.light = light;
    }

    public void setAirConditioner(AirConditioner airConditioner) {
        this.airConditioner = airConditioner;
    }

    public void setSiren(Siren siren) {
        this.siren = siren;
    }

    public void setSprinkler(Sprinkler sprinkler) {
        this.sprinkler = sprinkler;
    }

    @Override
    public void notify(SmartHomeColleague colleague, String event) {
        if ("motionDetected".equalsIgnoreCase(event)) {
            System.out.println("Mediator: Nhận tin báo phát hiện chuyển động từ " + colleague.getClass().getSimpleName());
            if (light != null) light.turnOn();
            if (airConditioner != null) airConditioner.turnOn();
        } else if ("smokeDetected".equalsIgnoreCase(event)) {
            System.out.println("Mediator: CẢNH BÁO khói từ " + colleague.getClass().getSimpleName() + "! Kích hoạt quy trình khẩn cấp.");
            if (siren != null) siren.startSounding();
            if (sprinkler != null) sprinkler.startWatering();
            if (airConditioner != null) airConditioner.turnOff();
            if (light != null) light.turnOn();
        }
    }
}
