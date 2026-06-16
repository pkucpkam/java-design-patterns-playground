package creational.abstract_factory.after;

public class WindowsButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a button in Windows style.");
    }
}
