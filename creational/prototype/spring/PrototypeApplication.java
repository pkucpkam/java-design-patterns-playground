package creational.prototype.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "creational.prototype.spring")
public class PrototypeApplication {

    public static void main(String[] args) {
        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PrototypeApplication.class)) {
            DocumentService service = context.getBean(DocumentService.class);

            System.out.println("Creating Report 1...");
            ReportTemplate report1 = service.createNewReport("Q1 Financials");
            
            System.out.println("Creating Report 2...");
            ReportTemplate report2 = service.createNewReport("Q2 Financials");

            System.out.println("Report 1 Header: " + report1.getHeader() + " | Instance: " + report1);
            System.out.println("Report 2 Header: " + report2.getHeader() + " | Instance: " + report2);
            System.out.println("Are they the same instance? " + (report1 == report2));
        }
    }
}
