package creational.abstract_factory.after;

public class MacCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a checkbox in macOS style.");
    }
}
