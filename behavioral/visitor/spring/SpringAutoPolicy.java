package behavioral.visitor.spring;

public class SpringAutoPolicy implements SpringPolicy {
    private final double vehicleValue;
    private final boolean hasAccidents;

    public SpringAutoPolicy(double vehicleValue, boolean hasAccidents) {
        this.vehicleValue = vehicleValue;
        this.hasAccidents = hasAccidents;
    }

    public double getVehicleValue() {
        return vehicleValue;
    }

    public boolean isHasAccidents() {
        return hasAccidents;
    }

    @Override
    public <R> R accept(SpringPolicyVisitor<R> visitor) {
        return visitor.visit(this);
    }
}
