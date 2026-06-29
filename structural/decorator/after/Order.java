package structural.decorator.after;

/**
 * Component interface that defines the core operations.
 * Both the concrete order class and decorators will implement this interface.
 */
public interface Order {
    double getPrice();
    String getDescription();
}
