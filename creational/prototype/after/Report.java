package creational.prototype.after;

import java.util.Objects;

public class Report extends Document {
    private String analyticalData;

    public Report() {
        super();
    }

    // Copy constructor
    public Report(Report target) {
        super(target);
        if (target != null) {
            this.analyticalData = target.analyticalData;
        }
    }

    @Override
    public Document clone() {
        return new Report(this);
    }

    public String getAnalyticalData() {
        return analyticalData;
    }

    public void setAnalyticalData(String analyticalData) {
        this.analyticalData = analyticalData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Report report = (Report) o;
        return Objects.equals(analyticalData, report.analyticalData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), analyticalData);
    }
}
