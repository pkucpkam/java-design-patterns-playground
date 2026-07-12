package behavioral.visitor.after;

public class RiskEvaluatorVisitor implements PolicyVisitor<String> {

    @Override
    public String visit(ResidentialPolicy policy) {
        if (policy.getYearBuilt() < 1980) {
            return "HIGH"; // nhà quá cũ
        }
        return policy.getPropertyValue() > 1_000_000 ? "MEDIUM" : "LOW";
    }

    @Override
    public String visit(CommercialPolicy policy) {
        if (policy.getRevenue() > 10_000_000 && policy.getEmployeeCount() > 500) {
            return "HIGH"; // doanh nghiệp lớn
        }
        return "MEDIUM";
    }

    @Override
    public String visit(AutoPolicy policy) {
        return policy.isHasAccidents() ? "HIGH" : "LOW";
    }
}
