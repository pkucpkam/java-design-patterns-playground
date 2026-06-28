package structural.composite.before;

import java.util.ArrayList;
import java.util.List;

public class Department {
    private String name;
    private List<Employee> employees = new ArrayList<>();
    private List<Department> subDepartments = new ArrayList<>();

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public void addSubDepartment(Department department) {
        subDepartments.add(department);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public List<Department> getSubDepartments() {
        return subDepartments;
    }

    public void printDetails() {
        System.out.println("Department: " + name);
    }
}
