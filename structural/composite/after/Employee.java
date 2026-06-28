package structural.composite.after;

/**
 * Leaf: Represents the individual objects in the tree structure.
 * A leaf has no children.
 */
public class Employee implements OrganizationComponent {
    private String name;
    private String role;
    private double salary;

    public Employee(String name, String role, double salary) {
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    @Override
    public double calculateSalary() {
        return salary; // Base case for recursion
    }

    @Override
    public void printStructure(int indentLevel) {
        String indent = "  ".repeat(indentLevel);
        System.out.println(indent + "- Employee: " + name + " [" + role + "] - $" + salary);
    }
}
