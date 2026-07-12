package behavioral.visitor.after;

public class PolicyExporterVisitor implements PolicyVisitor<String> {

    @Override
    public String visit(ResidentialPolicy policy) {
        return String.format("{\"type\":\"Residential\",\"propertyValue\":%.1f,\"yearBuilt\":%d}", 
                policy.getPropertyValue(), policy.getYearBuilt());
    }

    @Override
    public String visit(CommercialPolicy policy) {
        return String.format("{\"type\":\"Commercial\",\"revenue\":%.1f,\"employeeCount\":%d}", 
                policy.getRevenue(), policy.getEmployeeCount());
    }

    @Override
    public String visit(AutoPolicy policy) {
        return String.format("{\"type\":\"Auto\",\"vehicleValue\":%.1f,\"hasAccidents\":%b}", 
                policy.getVehicleValue(), policy.isHasAccidents());
    }
}
