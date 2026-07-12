package behavioral.visitor.before;

public class ResidentialPolicy implements Policy {
    private final double propertyValue;
    private final int yearBuilt;

    public ResidentialPolicy(double propertyValue, int yearBuilt) {
        this.propertyValue = propertyValue;
        this.yearBuilt = yearBuilt;
    }

    public double getPropertyValue() {
        return propertyValue;
    }

    public int getYearBuilt() {
        return yearBuilt;
    }
}
