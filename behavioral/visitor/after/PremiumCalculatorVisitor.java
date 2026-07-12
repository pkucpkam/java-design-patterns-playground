package behavioral.visitor.after;

public class PremiumCalculatorVisitor implements PolicyVisitor<Double> {

    @Override
    public Double visit(ResidentialPolicy policy) {
        // Phí bảo hiểm nhà đất: 0.5% giá trị bất động sản, giảm giá nếu nhà xây mới (sau năm 2010)
        double base = policy.getPropertyValue() * 0.005;
        if (policy.getYearBuilt() > 2010) {
            base *= 0.9; // giảm 10%
        }
        return base;
    }

    @Override
    public Double visit(CommercialPolicy policy) {
        // Phí bảo hiểm doanh nghiệp: 2% doanh thu + $100 mỗi nhân viên
        return policy.getRevenue() * 0.02 + policy.getEmployeeCount() * 100.0;
    }

    @Override
    public Double visit(AutoPolicy policy) {
        // Phí bảo hiểm xe: 3% giá trị xe + $500 nếu từng có tai nạn
        double base = policy.getVehicleValue() * 0.03;
        if (policy.isHasAccidents()) {
            base += 500.0;
        }
        return base;
    }
}
