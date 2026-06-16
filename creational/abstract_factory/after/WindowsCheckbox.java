package creational.abstract_factory.after;

public class WindowsCheckbox implements Checkbox {
    @Override
    public void render() {
        System.out.println("Rendering a checkbox in Windows style.");
    }
}
