package structural.composite.after;

/**
 * Component: The common interface for all elements in the tree (both Leaf and Composite).
 */
public interface OrganizationComponent {
    
    /**
     * Common operation that applies to both simple and complex elements.
     */
    double calculateSalary();
    
    /**
     * Operation to print the structure.
     */
    void printStructure(int indentLevel);
    
    // Default methods for adding/removing children (optional but common in Composite).
    // Throwing UnsupportedOperationException makes sense for Leaf nodes.
    default void add(OrganizationComponent component) {
        throw new UnsupportedOperationException("Cannot add to a leaf component.");
    }

    default void remove(OrganizationComponent component) {
        throw new UnsupportedOperationException("Cannot remove from a leaf component.");
    }
}
