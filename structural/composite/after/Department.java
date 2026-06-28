package structural.composite.after;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite: Represents a complex component that may have children.
 * It implements the base component interface and delegates work to its children.
 */
public class Department implements OrganizationComponent {
    private String name;
    
    // The Composite holds a collection of children (can be Employees or Sub-departments).
    // Notice it uses the common interface 'OrganizationComponent', not concrete classes.
    private List<OrganizationComponent> components = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    @Override
    public void add(OrganizationComponent component) {
        components.add(component);
    }

    @Override
    public void remove(OrganizationComponent component) {
        components.remove(component);
    }

    @Override
    public double calculateSalary() {
        // Delegate the calculation to all children and sum the results.
        double totalSalary = 0;
        for (OrganizationComponent component : components) {
            totalSalary += component.calculateSalary();
        }
        return totalSalary;
    }

    @Override
    public void printStructure(int indentLevel) {
        String indent = "  ".repeat(indentLevel);
        System.out.println(indent + "+ Department: " + name);
        
        // Delegate to children
        for (OrganizationComponent component : components) {
            component.printStructure(indentLevel + 1);
        }
    }
}
