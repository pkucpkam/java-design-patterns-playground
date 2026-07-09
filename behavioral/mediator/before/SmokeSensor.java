package behavioral.mediator.before;

public class SmokeSensor {
    private final Siren siren;
    private final Sprinkler sprinkler;
    private final AirConditioner airConditioner;
    private final SmartLight light;

    public SmokeSensor(Siren siren, Sprinkler sprinkler, AirConditioner airConditioner, SmartLight light) {
        this.siren = siren;
        this.sprinkler = sprinkler;
        this.airConditioner = airConditioner;
        this.light = light;
    }

    public void detectSmoke() {
        System.out.println("SmokeSensor: CẢNH BÁO! Phát hiện khói!");
        siren.startSounding();
        sprinkler.startWatering();
        airConditioner.turnOff(); // Tắt điều hòa để tránh lan khói qua đường thông khí
        light.turnOn(); // Bật đèn sáng để hỗ trợ thoát hiểm
    }
}
