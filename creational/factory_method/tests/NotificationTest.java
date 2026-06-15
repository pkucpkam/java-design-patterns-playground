package creational.factory_method.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testBeforeRefactoringSendsCorrectly() {
        creational.factory_method.before.NotificationService service = new creational.factory_method.before.NotificationService();

        service.sendNotification("EMAIL", "Hello Email", "alice@example.com");
        assertTrue(outContent.toString().trim().contains("Sending Email to alice@example.com: Hello Email"));

        outContent.reset();
        service.sendNotification("SMS", "Hello SMS", "+123456789");
        assertTrue(outContent.toString().trim().contains("Sending SMS to +123456789: Hello SMS"));

        outContent.reset();
        service.sendNotification("PUSH", "Hello Push", "device_token_xyz");
        assertTrue(outContent.toString().trim().contains("Sending Push Notification to device_token_xyz: Hello Push"));
    }

    @Test
    public void testBeforeRefactoringThrowsOnUnknownType() {
        creational.factory_method.before.NotificationService service = new creational.factory_method.before.NotificationService();
        assertThrows(IllegalArgumentException.class, () -> {
            service.sendNotification("SLACK", "Hello Slack", "#dev-channel");
        });
    }

    @Test
    public void testAfterRefactoringSendsCorrectly() {
        creational.factory_method.after.NotificationSender emailSender = new creational.factory_method.after.EmailNotificationSender();
        creational.factory_method.after.NotificationSender smsSender = new creational.factory_method.after.SmsNotificationSender();
        creational.factory_method.after.NotificationSender pushSender = new creational.factory_method.after.PushNotificationSender();
        creational.factory_method.after.NotificationSender slackSender = new creational.factory_method.after.SlackNotificationSender();

        emailSender.sendNotification("Hello Email", "alice@example.com");
        assertTrue(outContent.toString().trim().contains("Sending Email to alice@example.com: Hello Email"));

        outContent.reset();
        smsSender.sendNotification("Hello SMS", "+123456789");
        assertTrue(outContent.toString().trim().contains("Sending SMS to +123456789: Hello SMS"));

        outContent.reset();
        pushSender.sendNotification("Hello Push", "device_token_xyz");
        assertTrue(outContent.toString().trim().contains("Sending Push Notification to device_token_xyz: Hello Push"));

        outContent.reset();
        slackSender.sendNotification("Hello Slack", "#dev-channel");
        assertTrue(outContent.toString().trim().contains("Sending Slack message to channel #dev-channel: Hello Slack"));
    }

    @Test
    public void testFactoryMethodReturnsCorrectProductInstance() {
        creational.factory_method.after.NotificationSender emailSender = new creational.factory_method.after.EmailNotificationSender();
        creational.factory_method.after.Notification notification = emailSender.createNotification();
        assertTrue(notification instanceof creational.factory_method.after.EmailNotification, "Factory Method should instantiate EmailNotification");
    }
}
