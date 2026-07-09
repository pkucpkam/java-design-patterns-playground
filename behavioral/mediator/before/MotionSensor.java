package behavioral.mediator.before;

public class MotionSensor {
    private final SmartLight light;
    private final AirConditioner airConditioner;

    public MotionSensor(SmartLight light, AirConditioner airConditioner) {
        this.light = light;
        this.airConditioner = airConditioner;
    }

    public void detectMotion() {
        System.out.println("MotionSensor: Phát hiện chuyển động!");
        light.turnOn();
        airConditioner.turnOn();
    }
}
