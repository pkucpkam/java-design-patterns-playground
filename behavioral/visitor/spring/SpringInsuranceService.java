package behavioral.visitor.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpringInsuranceService {

    private final SpringPremiumCalculatorVisitor premiumCalculator;
    private final SpringRiskEvaluatorVisitor riskEvaluator;
    private final SpringPolicyExporterVisitor policyExporter;

    @Autowired
    public SpringInsuranceService(
            SpringPremiumCalculatorVisitor premiumCalculator,
            SpringRiskEvaluatorVisitor riskEvaluator,
            SpringPolicyExporterVisitor policyExporter) {
        this.premiumCalculator = premiumCalculator;
        this.riskEvaluator = riskEvaluator;
        this.policyExporter = policyExporter;
    }

    public double calculateTotalPremium(List<SpringPolicy> policies) {
        return policies.stream()
                .mapToDouble(policy -> policy.accept(premiumCalculator))
                .sum();
    }

    public List<String> evaluateAllRisks(List<SpringPolicy> policies) {
        return policies.stream()
                .map(policy -> policy.accept(riskEvaluator))
                .collect(Collectors.toList());
    }

    public String exportAllToJson(List<SpringPolicy> policies) {
        return "[" + policies.stream()
                .map(policy -> policy.accept(policyExporter))
                .collect(Collectors.joining(",")) + "]";
    }
}
