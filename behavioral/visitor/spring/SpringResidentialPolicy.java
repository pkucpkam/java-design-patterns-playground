package behavioral.visitor.spring;

public class SpringResidentialPolicy implements SpringPolicy {
    private final double propertyValue;
    private final int yearBuilt;

    public SpringResidentialPolicy(double propertyValue, int yearBuilt) {
        this.propertyValue = propertyValue;
        this.yearBuilt = yearBuilt;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }

    @Override
    public <R> R accept(SpringPolicyVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
