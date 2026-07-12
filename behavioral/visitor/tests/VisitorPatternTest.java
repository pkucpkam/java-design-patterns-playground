package behavioral.visitor.tests;

import behavioral.visitor.before.*;
import behavioral.visitor.after.*;
import behavioral.visitor.spring.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class VisitorPatternTest {

    private Policy residentialPolicy;
    private Policy commercialPolicy;
    private Policy autoPolicy;

    @BeforeEach
    public void setUp() {
        residentialPolicy = new ResidentialPolicy(500_000.0, 2015);
        commercialPolicy = new CommercialPolicy(5_000_000.0, 50);
        autoPolicy = new AutoPolicy(50_000.0, true);
    }

    // --- 1. Test cho Before Refactoring ---
    @Test
    public void testBeforeRefactoring_Success() {
        InsuranceServiceBefore beforeService = new InsuranceServiceBefore();

        // Residential Policy (Before)
        behavioral.visitor.before.Policy resBefore = new behavioral.visitor.before.ResidentialPolicy(500_000.0, 2015);
        // Premium: 500k * 0.005 * 0.9 (since built after 2010) = 2250
        assertEquals(2250.0, beforeService.calculatePremium(resBefore), 0.001);
        assertEquals("LOW", beforeService.evaluateRisk(resBefore));
        assertEquals("{\"type\":\"Residential\",\"propertyValue\":500000.0,\"yearBuilt\":2015}", beforeService.exportToJson(resBefore));

        // Commercial Policy (Before)
        behavioral.visitor.before.Policy comBefore = new behavioral.visitor.before.CommercialPolicy(5_000_000.0, 50);
        // Premium: 5M * 0.02 + 50 * 100 = 100,000 + 5,000 = 105,000
        assertEquals(105000.0, beforeService.calculatePremium(comBefore), 0.001);
        assertEquals("MEDIUM", beforeService.evaluateRisk(comBefore));
        assertEquals("{\"type\":\"Commercial\",\"revenue\":5000000.0,\"employeeCount\":50}", beforeService.exportToJson(comBefore));

        // Auto Policy (Before)
        behavioral.visitor.before.Policy autoBefore = new behavioral.visitor.before.AutoPolicy(50_000.0, true);
        // Premium: 50k * 0.03 + 500 = 1500 + 500 = 2000
        assertEquals(2000.0, beforeService.calculatePremium(autoBefore), 0.001);
        assertEquals("HIGH", beforeService.evaluateRisk(autoBefore));
        assertEquals("{\"type\":\"Auto\",\"vehicleValue\":50000.0,\"hasAccidents\":true}", beforeService.exportToJson(autoBefore));
    }

    @Test
    public void testBeforeRefactoring_InvalidPolicyType_ThrowsException() {
        InsuranceServiceBefore beforeService = new InsuranceServiceBefore();
        behavioral.visitor.before.Policy customPolicy = new behavioral.visitor.before.Policy() {};
        
        assertThrows(IllegalArgumentException.class, () -> beforeService.calculatePremium(customPolicy));
        assertThrows(IllegalArgumentException.class, () -> beforeService.evaluateRisk(customPolicy));
        assertThrows(IllegalArgumentException.class, () -> beforeService.exportToJson(customPolicy));
    }


    // --- 2. Test cho After Refactoring (Pattern Solution) ---
    
    // Happy Path: Tính phí bảo hiểm (Premium Calculator Visitor)
    @Test
    public void testPatternSolution_PremiumCalculator() {
        PolicyVisitor<Double> premiumCalculator = new PremiumCalculatorVisitor();

        // Residential (built in 2015 -> discount 10%)
        // 500,000 * 0.005 * 0.9 = 2250.0
        assertEquals(2250.0, residentialPolicy.accept(premiumCalculator), 0.001);

        // Residential (built in 2005 -> no discount)
        // 500,000 * 0.005 = 2500.0
        Policy oldResPolicy = new ResidentialPolicy(500_000.0, 2005);
        assertEquals(2500.0, oldResPolicy.accept(premiumCalculator), 0.001);

        // Commercial: 5,000,000 * 0.02 + 50 * 100 = 105,000.0
        assertEquals(105000.0, commercialPolicy.accept(premiumCalculator), 0.001);

        // Auto (has accidents): 50,000 * 0.03 + 500 = 2000.0
        assertEquals(2000.0, autoPolicy.accept(premiumCalculator), 0.001);

        // Auto (no accidents): 50,000 * 0.03 = 1500.0
        Policy safeAutoPolicy = new AutoPolicy(50_000.0, false);
        assertEquals(1500.0, safeAutoPolicy.accept(premiumCalculator), 0.001);
    }

    // Happy Path: Đánh giá rủi ro (Risk Evaluator Visitor)
    @Test
    public void testPatternSolution_RiskEvaluator() {
        PolicyVisitor<String> riskEvaluator = new RiskEvaluatorVisitor();

        // Residential
        assertEquals("LOW", residentialPolicy.accept(riskEvaluator));
        
        Policy highValueRes = new ResidentialPolicy(1_500_000.0, 2010);
        assertEquals("MEDIUM", highValueRes.accept(riskEvaluator));

        Policy oldRes = new ResidentialPolicy(300_000.0, 1970);
        assertEquals("HIGH", oldRes.accept(riskEvaluator));

        // Commercial
        assertEquals("MEDIUM", commercialPolicy.accept(riskEvaluator));

        Policy megaCorp = new CommercialPolicy(20_000_000.0, 1000);
        assertEquals("HIGH", megaCorp.accept(riskEvaluator));

        // Auto
        assertEquals("HIGH", autoPolicy.accept(riskEvaluator));
        
        Policy safeAuto = new AutoPolicy(30_000.0, false);
        assertEquals("LOW", safeAuto.accept(riskEvaluator));
    }

    // Happy Path: Xuất JSON (Policy Exporter Visitor)
    @Test
    public void testPatternSolution_PolicyExporter() {
        PolicyVisitor<String> exporter = new PolicyExporterVisitor();

        assertEquals("{\"type\":\"Residential\",\"propertyValue\":500000.0,\"yearBuilt\":2015}", 
                residentialPolicy.accept(exporter));
        assertEquals("{\"type\":\"Commercial\",\"revenue\":5000000.0,\"employeeCount\":50}", 
                commercialPolicy.accept(exporter));
        assertEquals("{\"type\":\"Auto\",\"vehicleValue\":50000.0,\"hasAccidents\":true}", 
                autoPolicy.accept(exporter));
    }

    // Edge Case: Giá trị tài sản hoặc doanh thu bằng 0
    @Test
    public void testPatternSolution_EdgeCase_ZeroValues() {
        PolicyVisitor<Double> premiumCalculator = new PremiumCalculatorVisitor();
        
        Policy zeroRes = new ResidentialPolicy(0.0, 2020);
        assertEquals(0.0, zeroRes.accept(premiumCalculator), 0.001);

        Policy zeroCom = new CommercialPolicy(0.0, 0);
        assertEquals(0.0, zeroCom.accept(premiumCalculator), 0.001);

        Policy zeroAuto = new AutoPolicy(0.0, false);
        assertEquals(0.0, zeroAuto.accept(premiumCalculator), 0.001);
    }

    // Edge Case: Cận ranh giới năm giảm giá của nhà (2010 vs 2011)
    @Test
    public void testPatternSolution_EdgeCase_BoundaryYears() {
        PolicyVisitor<Double> premiumCalculator = new PremiumCalculatorVisitor();

        Policy res2010 = new ResidentialPolicy(100_000.0, 2010);
        // Không giảm giá: 100,000 * 0.005 = 500.0
        assertEquals(500.0, res2010.accept(premiumCalculator), 0.001);

        Policy res2011 = new ResidentialPolicy(100_000.0, 2011);
        // Giảm 10%: 100,000 * 0.005 * 0.9 = 450.0
        assertEquals(450.0, res2011.accept(premiumCalculator), 0.001);
    }


    // --- 3. Test cho Spring Boot Integration ---
    @Test
    public void testSpringIntegration_Success() {
        // Khởi tạo các Spring Beans thủ công để mô phỏng DI
        InsuranceRateConfig rateConfig = new InsuranceRateConfig();
        SpringPremiumCalculatorVisitor premiumCalculator = new SpringPremiumCalculatorVisitor(rateConfig);
        SpringRiskEvaluatorVisitor riskEvaluator = new SpringRiskEvaluatorVisitor();
        SpringPolicyExporterVisitor policyExporter = new SpringPolicyExporterVisitor();

        SpringInsuranceService insuranceService = new SpringInsuranceService(
                premiumCalculator, riskEvaluator, policyExporter
        );

        // Khởi tạo danh sách Policies
        List<SpringPolicy> policies = List.of(
                new SpringResidentialPolicy(500_000.0, 2015), // Premium: 500k * 0.005 * 0.9 = 2250
                new SpringCommercialPolicy(5_000_000.0, 50),  // Premium: 5M * 0.02 + 50 * 100 = 105,000
                new SpringAutoPolicy(50_000.0, true)          // Premium: 50k * 0.03 + 500 = 2000
        );

        // Kiểm tra tổng phí bảo hiểm: 2250 + 105000 + 2000 = 109,250
        assertEquals(109250.0, insuranceService.calculateTotalPremium(policies), 0.001);

        // Kiểm tra danh sách rủi ro
        List<String> risks = insuranceService.evaluateAllRisks(policies);
        assertEquals(3, risks.size());
        assertEquals("LOW", risks.get(0));
        assertEquals("MEDIUM", risks.get(1));
        assertEquals("HIGH", risks.get(2));

        // Kiểm tra export JSON
        String expectedJson = "[" +
                "{\"type\":\"Residential\",\"propertyValue\":500000.0,\"yearBuilt\":2015}," +
                "{\"type\":\"Commercial\",\"revenue\":5000000.0,\"employeeCount\":50}," +
                "{\"type\":\"Auto\",\"vehicleValue\":50000.0,\"hasAccidents\":true}" +
                "]";
        assertEquals(expectedJson, insuranceService.exportAllToJson(policies));
    }

    @Test
    public void testSpringIntegration_DynamicConfigChanges() {
        // Mô phỏng Spring DI
        InsuranceRateConfig rateConfig = new InsuranceRateConfig();
        SpringPremiumCalculatorVisitor premiumCalculator = new SpringPremiumCalculatorVisitor(rateConfig);
        SpringRiskEvaluatorVisitor riskEvaluator = new SpringRiskEvaluatorVisitor();
        SpringPolicyExporterVisitor policyExporter = new SpringPolicyExporterVisitor();

        SpringInsuranceService insuranceService = new SpringInsuranceService(
                premiumCalculator, riskEvaluator, policyExporter
        );

        List<SpringPolicy> policies = List.of(
                new SpringResidentialPolicy(100_000.0, 2000) // Premium: 100k * rateConfig
        );

        // Rate mặc định: 0.005 -> Premium: 500
        assertEquals(500.0, insuranceService.calculateTotalPremium(policies), 0.001);

        // Thay đổi tỷ lệ phí thông qua Config Bean
        rateConfig.setResidentialRateFactor(0.01); // tăng gấp đôi
        
        // Premium mới phải tự động phản ánh thay đổi: 100k * 0.01 = 1000
        assertEquals(1000.0, insuranceService.calculateTotalPremium(policies), 0.001);
    }
}
