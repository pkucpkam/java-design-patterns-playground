package creational.prototype.spring;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service demonstrating how to retrieve Prototype scoped beans.
 * We use ObjectFactory to get a fresh instance each time.
 * If we used direct @Autowired ReportTemplate, we would only get one instance
 * because DocumentService is a Singleton.
 */
@Service
public class DocumentService {

    private final ObjectFactory<ReportTemplate> reportTemplateFactory;

    @Autowired
    public DocumentService(ObjectFactory<ReportTemplate> reportTemplateFactory) {
        this.reportTemplateFactory = reportTemplateFactory;
    }

    public ReportTemplate createNewReport(String customHeader) {
        // Requesting a new instance from the Spring container
        ReportTemplate report = reportTemplateFactory.getObject();
        report.setHeader(customHeader);
        return report;
    }
}
