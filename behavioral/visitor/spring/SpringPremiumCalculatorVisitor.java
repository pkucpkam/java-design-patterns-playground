package behavioral.visitor.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SpringPremiumCalculatorVisitor implements SpringPolicyVisitor<Double> {

    private final InsuranceRateConfig rateConfig;

    @Autowired
    public SpringPremiumCalculatorVisitor(InsuranceRateConfig rateConfig) {
        this.rateConfig = rateConfig;
    }

    @Override
    public Double visit(SpringResidentialPolicy policy) {
        double base = policy.getPropertyValue() * rateConfig.getResidentialRateFactor();
        if (policy.getYearBuilt() > 2010) {
            base *= 0.9;
        }
        return base;
    }

    @Override
    public Double visit(SpringCommercialPolicy policy) {
        return policy.getRevenue() * rateConfig.getCommercialRateFactor() 
                + policy.getEmployeeCount() * rateConfig.getEmployeeRateBonus();
    }

    @Override
    public Double visit(SpringAutoPolicy policy) {
        double base = policy.getVehicleValue() * rateConfig.getAutoRateFactor();
        if (policy.isHasAccidents()) {
            base += rateConfig.getAutoAccidentCharge();
        }
        return base;
    }
}
