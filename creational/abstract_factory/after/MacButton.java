package creational.abstract_factory.after;

public class MacButton implements Button {
    @Override
    public void render() {
        System.out.println("Rendering a button in macOS style.");
    }
}
