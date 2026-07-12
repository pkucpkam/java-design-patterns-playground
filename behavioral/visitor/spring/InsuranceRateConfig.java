package behavioral.visitor.spring;

import org.springframework.stereotype.Component;

@Component
public class InsuranceRateConfig {
    private double residentialRateFactor = 0.005;
    private double commercialRateFactor = 0.02;
    private double autoRateFactor = 0.03;
    private double employeeRateBonus = 100.0;
    private double autoAccidentCharge = 500.0;

    public double getResidentialRateFactor() {
        return residentialRateFactor;
    }

    public double getCommercialRateFactor() {
        return commercialRateFactor;
    }

    public double getAutoRateFactor() {
        return autoRateFactor;
    }

    public double getEmployeeRateBonus() {
        return employeeRateBonus;
    }

    public double getAutoAccidentCharge() {
        return autoAccidentCharge;
    }

    // Setters (useful for testing dynamically changing configs)
    public void setResidentialRateFactor(double residentialRateFactor) {
        this.residentialRateFactor = residentialRateFactor;
    }

    public void setCommercialRateFactor(double commercialRateFactor) {
        this.commercialRateFactor = commercialRateFactor;
    }

    public void setAutoRateFactor(double autoRateFactor) {
        this.autoRateFactor = autoRateFactor;
    }

    public void setEmployeeRateBonus(double employeeRateBonus) {
        this.employeeRateBonus = employeeRateBonus;
    }

    public void setAutoAccidentCharge(double autoAccidentCharge) {
        this.autoAccidentCharge = autoAccidentCharge;
    }
}
