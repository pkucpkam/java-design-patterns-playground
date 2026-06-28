package structural.composite.before;

public class Employee {
    private String name;
    private String role;
    private double salary;

    public Employee(String name, String role, double salary) {
        this.name = name;
        this.role = role;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getSalary() {
        return salary;
    }

    public void printDetails() {
        System.out.println("Employee: " + name + " [" + role + "] - $" + salary);
    }
}
