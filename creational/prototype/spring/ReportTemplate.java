package creational.prototype.spring;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * In Spring, the Prototype pattern is naturally supported via Bean Scopes.
 * By setting the scope to "prototype", Spring container will create a new
 * instance every time this bean is requested.
 */
@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReportTemplate {
    private String header;
    private LocalDateTime generatedAt;

    public ReportTemplate() {
        // Simulating expensive initialization
        this.header = "ACME Corp Standard Report Header";
        this.generatedAt = LocalDateTime.now();
        System.out.println("ReportTemplate instantiated at " + this.generatedAt);
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public LocalDateTime getGeneratedAt() {
        return generatedAt;
    }
}
