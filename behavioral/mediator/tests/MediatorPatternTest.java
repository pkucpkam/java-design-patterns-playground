package behavioral.mediator.tests;

import behavioral.mediator.before.*;
import behavioral.mediator.after.*;
import behavioral.mediator.spring.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class MediatorPatternTest {

    // =========================================================================
    // 1. TESTS FOR BEFORE REFACTORING
    // =========================================================================

    @Test
    void testBefore_HappyPath_MotionDetected() {
        behavioral.mediator.before.SmartLight light = new behavioral.mediator.before.SmartLight();
        behavioral.mediator.before.AirConditioner ac = new behavioral.mediator.before.AirConditioner();
        behavioral.mediator.before.MotionSensor motionSensor = new behavioral.mediator.before.MotionSensor(light, ac);

        assertFalse(light.isOn());
        assertFalse(ac.isOn());

        motionSensor.detectMotion();

        assertTrue(light.isOn());
        assertTrue(ac.isOn());
    }

    @Test
    void testBefore_HappyPath_SmokeDetected() {
        behavioral.mediator.before.Siren siren = new behavioral.mediator.before.Siren();
        behavioral.mediator.before.Sprinkler sprinkler = new behavioral.mediator.before.Sprinkler();
        behavioral.mediator.before.AirConditioner ac = new behavioral.mediator.before.AirConditioner();
        behavioral.mediator.before.SmartLight light = new behavioral.mediator.before.SmartLight();
        behavioral.mediator.before.SmokeSensor smokeSensor = new behavioral.mediator.before.SmokeSensor(siren, sprinkler, ac, light);

        // Giả sử điều hòa đang bật trước đó
        ac.turnOn();
        assertTrue(ac.isOn());
        assertFalse(siren.isSounding());
        assertFalse(sprinkler.isWatering());
        assertFalse(light.isOn());

        smokeSensor.detectSmoke();

        assertTrue(siren.isSounding());
        assertTrue(sprinkler.isWatering());
        assertFalse(ac.isOn()); // Phải tắt khi có khói
        assertTrue(light.isOn()); // Phải bật để thoát hiểm
    }

    // =========================================================================
    // 2. TESTS FOR AFTER REFACTORING (PURE JAVA PATTERN)
    // =========================================================================

    @Test
    void testAfter_HappyPath_MotionDetected() {
        behavioral.mediator.after.SmartHomeHub hub = new behavioral.mediator.after.SmartHomeHub();
        behavioral.mediator.after.SmartLight light = new behavioral.mediator.after.SmartLight(hub);
        behavioral.mediator.after.AirConditioner ac = new behavioral.mediator.after.AirConditioner(hub);
        behavioral.mediator.after.MotionSensor motionSensor = new behavioral.mediator.after.MotionSensor(hub);

        hub.setLight(light);
        hub.setAirConditioner(ac);

        assertFalse(light.isOn());
        assertFalse(ac.isOn());

        motionSensor.detectMotion();

        assertTrue(light.isOn());
        assertTrue(ac.isOn());
    }

    @Test
    void testAfter_HappyPath_SmokeDetected() {
        behavioral.mediator.after.SmartHomeHub hub = new behavioral.mediator.after.SmartHomeHub();
        behavioral.mediator.after.Siren siren = new behavioral.mediator.after.Siren(hub);
        behavioral.mediator.after.Sprinkler sprinkler = new behavioral.mediator.after.Sprinkler(hub);
        behavioral.mediator.after.AirConditioner ac = new behavioral.mediator.after.AirConditioner(hub);
        behavioral.mediator.after.SmartLight light = new behavioral.mediator.after.SmartLight(hub);
        behavioral.mediator.after.SmokeSensor smokeSensor = new behavioral.mediator.after.SmokeSensor(hub);

        hub.setSiren(siren);
        hub.setSprinkler(sprinkler);
        hub.setAirConditioner(ac);
        hub.setLight(light);

        ac.turnOn();
        assertTrue(ac.isOn());
        assertFalse(siren.isSounding());
        assertFalse(sprinkler.isWatering());
        assertFalse(light.isOn());

        smokeSensor.detectSmoke();

        assertTrue(siren.isSounding());
        assertTrue(sprinkler.isWatering());
        assertFalse(ac.isOn());
        assertTrue(light.isOn());
    }

    @Test
    void testAfter_EdgeCase_DeviceNotRegistered() {
        behavioral.mediator.after.SmartHomeHub hub = new behavioral.mediator.after.SmartHomeHub();
        // Không đăng ký bất kỳ thiết bị nào vào hub
        behavioral.mediator.after.MotionSensor motionSensor = new behavioral.mediator.after.MotionSensor(hub);

        // Không được ném ra NullPointerException
        assertDoesNotThrow(motionSensor::detectMotion);
    }

    @Test
    void testAfter_EdgeCase_UnrecognizedEvent() {
        behavioral.mediator.after.SmartHomeHub hub = new behavioral.mediator.after.SmartHomeHub();
        behavioral.mediator.after.SmartLight light = new behavioral.mediator.after.SmartLight(hub);
        hub.setLight(light);

        assertFalse(light.isOn());
        hub.notify(light, "unknownEvent");
        assertFalse(light.isOn()); // Đèn giữ nguyên trạng thái
    }

    // =========================================================================
    // 3. TESTS FOR SPRING BOOT INTEGRATION
    // =========================================================================

    @Test
    void testSpring_HappyPath_MotionDetected() {
        SpringSmartHomeHub hub = new SpringSmartHomeHub();
        SpringSmartLight light = new SpringSmartLight();
        SpringAirConditioner ac = new SpringAirConditioner();
        SpringMotionSensor motionSensor = new SpringMotionSensor();

        // Thiết lập thủ công mối quan hệ phụ thuộc để mô phỏng Spring IoC container
        light.setMediator(hub);
        ac.setMediator(hub);
        motionSensor.setMediator(hub);
        hub.registerDevices(light, ac, null, null);

        assertFalse(light.isOn());
        assertFalse(ac.isOn());

        motionSensor.detectMotion();

        assertTrue(light.isOn());
        assertTrue(ac.isOn());
    }

    @Test
    void testSpring_HappyPath_SmokeDetected() {
        SpringSmartHomeHub hub = new SpringSmartHomeHub();
        SpringSiren siren = new SpringSiren();
        SpringSprinkler sprinkler = new SpringSprinkler();
        SpringAirConditioner ac = new SpringAirConditioner();
        SpringSmartLight light = new SpringSmartLight();
        SpringSmokeSensor smokeSensor = new SpringSmokeSensor();

        siren.setMediator(hub);
        sprinkler.setMediator(hub);
        ac.setMediator(hub);
        light.setMediator(hub);
        smokeSensor.setMediator(hub);
        hub.registerDevices(light, ac, siren, sprinkler);

        ac.turnOn();
        assertTrue(ac.isOn());
        assertFalse(siren.isSounding());
        assertFalse(sprinkler.isWatering());
        assertFalse(light.isOn());

        smokeSensor.detectSmoke();

        assertTrue(siren.isSounding());
        assertTrue(sprinkler.isWatering());
        assertFalse(ac.isOn());
        assertTrue(light.isOn());
    }
}
