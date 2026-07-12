package behavioral.visitor.before;

public class AutoPolicy implements Policy {
    private final double vehicleValue;
    private final boolean hasAccidents;

    public AutoPolicy(double vehicleValue, boolean hasAccidents) {
        this.vehicleValue = vehicleValue;
        this.hasAccidents = hasAccidents;
    }

    public double getVehicleValue() {
        return vehicleValue;
    }

    public boolean isHasAccidents() {
        return hasAccidents;
    }
}
