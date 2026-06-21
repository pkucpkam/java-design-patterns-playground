package creational.prototype.after;

import java.util.HashMap;
import java.util.Map;

/**
 * The DocumentRegistry acts as a Prototype Registry.
 * It stores a set of pre-configured objects that can be cloned
 * whenever a new instance is needed, instead of creating them from scratch.
 */
public class DocumentRegistry {
    private final Map<String, Document> cache = new HashMap<>();

    public DocumentRegistry() {
        // Initialize with default prototypes
        Contract standardContract = new Contract();
        standardContract.setTitle("Standard NDA Contract");
        standardContract.setLegalTerms("1. Confidentiality...");
        cache.put("NDA", standardContract);

        Report standardReport = new Report();
        standardReport.setTitle("Monthly Financial Report");
        standardReport.setAnalyticalData("Q1: $1000, Q2: $1500");
        cache.put("MonthlyReport", standardReport);
    }

    public void addPrototype(String key, Document document) {
        cache.put(key, document);
    }

    public Document getBy(String key) {
        Document prototype = cache.get(key);
        if (prototype != null) {
            // Client asks for a document by key, and gets a clone, not the original reference
            return prototype.clone();
        }
        return null;
    }
}
