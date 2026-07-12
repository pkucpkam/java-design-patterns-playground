package behavioral.visitor.before;

public class CommercialPolicy implements Policy {
    private final double revenue;
    private final int employeeCount;

    public CommercialPolicy(double revenue, int employeeCount) {
        this.revenue = revenue;
        this.employeeCount = employeeCount;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }
}
