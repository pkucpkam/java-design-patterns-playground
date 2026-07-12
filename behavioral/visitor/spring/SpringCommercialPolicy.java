package behavioral.visitor.spring;

public class SpringCommercialPolicy implements SpringPolicy {
    private final double revenue;
    private final int employeeCount;

    public SpringCommercialPolicy(double revenue, int employeeCount) {
        this.revenue = revenue;
        this.employeeCount = employeeCount;
    }

    public double getRevenue() {
        return revenue;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    @Override
    public <R> R accept(SpringPolicyVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
