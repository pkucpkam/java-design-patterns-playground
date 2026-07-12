package behavioral.visitor.spring;

import org.springframework.stereotype.Component;

@Component
public class SpringRiskEvaluatorVisitor implements SpringPolicyVisitor<String> {

    @Override
    public String visit(SpringResidentialPolicy policy) {
        if (policy.getYearBuilt() < 1980) {
            return "HIGH";
        }
        return policy.getPropertyValue() > 1_000_000 ? "MEDIUM" : "LOW";
    }

    @Override
    public String visit(SpringCommercialPolicy policy) {
        if (policy.getRevenue() > 10_000_000 && policy.getEmployeeCount() > 500) {
            return "HIGH";
        }
        return "MEDIUM";
    }

    @Override
    public String visit(SpringAutoPolicy policy) {
        return policy.isHasAccidents() ? "HIGH" : "LOW";
    }
}
