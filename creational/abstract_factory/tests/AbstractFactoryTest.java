package creational.abstract_factory.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class AbstractFactoryTest {
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
    public void testBeforeRefactoringWindows() {
        creational.abstract_factory.before.Application app = new creational.abstract_factory.before.Application("Windows");
        app.renderUI();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Rendering a button in Windows style."));
        assertTrue(output.contains("Rendering a checkbox in Windows style."));
    }

    @Test
    public void testBeforeRefactoringMac() {
        creational.abstract_factory.before.Application app = new creational.abstract_factory.before.Application("Mac");
        app.renderUI();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Rendering a button in macOS style."));
        assertTrue(output.contains("Rendering a checkbox in macOS style."));
    }

    @Test
    public void testBeforeRefactoringThrowsOnUnknownOS() {
        creational.abstract_factory.before.Application app = new creational.abstract_factory.before.Application("Linux");
        assertThrows(IllegalArgumentException.class, app::renderUI);
    }

    @Test
    public void testAfterRefactoringWindows() {
        creational.abstract_factory.after.GUIFactory factory = new creational.abstract_factory.after.WindowsFactory();
        creational.abstract_factory.after.Application app = new creational.abstract_factory.after.Application(factory);
        app.renderUI();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Rendering a button in Windows style."));
        assertTrue(output.contains("Rendering a checkbox in Windows style."));
    }

    @Test
    public void testAfterRefactoringMac() {
        creational.abstract_factory.after.GUIFactory factory = new creational.abstract_factory.after.MacFactory();
        creational.abstract_factory.after.Application app = new creational.abstract_factory.after.Application(factory);
        app.renderUI();
        String output = outContent.toString().trim();
        assertTrue(output.contains("Rendering a button in macOS style."));
        assertTrue(output.contains("Rendering a checkbox in macOS style."));
    }

    @Test
    public void testAfterRefactoringFactoryInstance() {
        creational.abstract_factory.after.GUIFactory winFactory = new creational.abstract_factory.after.WindowsFactory();
        assertTrue(winFactory.createButton() instanceof creational.abstract_factory.after.WindowsButton);
        assertTrue(winFactory.createCheckbox() instanceof creational.abstract_factory.after.WindowsCheckbox);

        creational.abstract_factory.after.GUIFactory macFactory = new creational.abstract_factory.after.MacFactory();
        assertTrue(macFactory.createButton() instanceof creational.abstract_factory.after.MacButton);
        assertTrue(macFactory.createCheckbox() instanceof creational.abstract_factory.after.MacCheckbox);
    }
}
