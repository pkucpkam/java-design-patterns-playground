package creational.abstract_factory.before;

public class Application {
    private final String osType;

    public Application(String osType) {
        this.osType = osType;
    }

    public void renderUI() {
        if ("Windows".equalsIgnoreCase(osType)) {
            WindowsButton button = new WindowsButton();
            WindowsCheckbox checkbox = new WindowsCheckbox();
            button.render();
            checkbox.render();
        } else if ("Mac".equalsIgnoreCase(osType)) {
            MacButton button = new MacButton();
            MacCheckbox checkbox = new MacCheckbox();
            button.render();
            checkbox.render();
        } else {
            throw new IllegalArgumentException("Unsupported OS: " + osType);
        }
    }
}
