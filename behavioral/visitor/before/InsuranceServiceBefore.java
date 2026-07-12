package behavioral.visitor.before;

public class InsuranceServiceBefore {

    // Vi phạm OCP và SRP: Sử dụng instanceof để tính toán phí bảo hiểm
    public double calculatePremium(Policy policy) {
        if (policy instanceof ResidentialPolicy rp) {
            // Phí bảo hiểm nhà đất: 0.5% giá trị bất động sản, giảm giá nếu nhà xây mới (sau năm 2010)
            double base = rp.getPropertyValue() * 0.005;
            if (rp.getYearBuilt() > 2010) {
                base *= 0.9; // giảm 10%
            }
            return base;
        } else if (policy instanceof CommercialPolicy cp) {
            // Phí bảo hiểm doanh nghiệp: 2% doanh thu + $100 mỗi nhân viên
            return cp.getRevenue() * 0.02 + cp.getEmployeeCount() * 100.0;
        } else if (policy instanceof AutoPolicy ap) {
            // Phí bảo hiểm xe: 3% giá trị xe + $500 nếu từng có tai nạn
            double base = ap.getVehicleValue() * 0.03;
            if (ap.isHasAccidents()) {
                base += 500.0;
            }
            return base;
        }
        throw new IllegalArgumentException("Unknown policy type");
    }

    // Vi phạm OCP và SRP: Sử dụng instanceof để đánh giá mức độ rủi ro
    public String evaluateRisk(Policy policy) {
        if (policy instanceof ResidentialPolicy rp) {
            if (rp.getYearBuilt() < 1980) {
                return "HIGH"; // nhà quá cũ
            }
            return rp.getPropertyValue() > 1_000_000 ? "MEDIUM" : "LOW";
        } else if (policy instanceof CommercialPolicy cp) {
            if (cp.getRevenue() > 10_000_000 && cp.getEmployeeCount() > 500) {
                return "HIGH"; // doanh nghiệp lớn
            }
            return "MEDIUM";
        } else if (policy instanceof AutoPolicy ap) {
            return ap.isHasAccidents() ? "HIGH" : "LOW";
        }
        throw new IllegalArgumentException("Unknown policy type");
    }

    // Vi phạm OCP và SRP: Sử dụng instanceof để xuất dữ liệu
    public String exportToJson(Policy policy) {
        if (policy instanceof ResidentialPolicy rp) {
            return String.format("{\"type\":\"Residential\",\"propertyValue\":%.1f,\"yearBuilt\":%d}", 
                    rp.getPropertyValue(), rp.getYearBuilt());
        } else if (policy instanceof CommercialPolicy cp) {
            return String.format("{\"type\":\"Commercial\",\"revenue\":%.1f,\"employeeCount\":%d}", 
                    cp.getRevenue(), cp.getEmployeeCount());
        } else if (policy instanceof AutoPolicy ap) {
            return String.format("{\"type\":\"Auto\",\"vehicleValue\":%.1f,\"hasAccidents\":%b}", 
                    ap.getVehicleValue(), ap.isHasAccidents());
        }
        throw new IllegalArgumentException("Unknown policy type");
    }
}
