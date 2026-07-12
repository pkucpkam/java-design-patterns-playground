package behavioral.visitor.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringPolicyExporterVisitor implements SpringPolicyVisitor<String> {

    @Override
    public String visit(SpringResidentialPolicy policy) {
        return String.format("{\"type\":\"Residential\",\"propertyValue\":%.1f,\"yearBuilt\":%d}", 
                policy.getPropertyValue(), policy.getYearBuilt());
    }

    @Override
    public String visit(SpringCommercialPolicy policy) {
        return String.format("{\"type\":\"Commercial\",\"revenue\":%.1f,\"employeeCount\":%d}", 
                policy.getRevenue(), policy.getEmployeeCount());
    }

    @Override
    public String visit(SpringAutoPolicy policy) {
        return String.format("{\"type\":\"Auto\",\"vehicleValue\":%.1f,\"hasAccidents\":%b}", 
                policy.getVehicleValue(), policy.isHasAccidents());
    }
}
