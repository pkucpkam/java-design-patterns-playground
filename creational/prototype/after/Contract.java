package creational.prototype.after;

import java.util.Objects;

public class Contract extends Document {
    private String legalTerms;

    public Contract() {
        super();
    }

    // Copy Constructor
    public Contract(Contract target) {
        super(target);
        if (target != null) {
            this.legalTerms = target.legalTerms;
        }
    }

    @Override
    public Document clone() {
        return new Contract(this);
    }

    public String getLegalTerms() {
        return legalTerms;
    }

    public void setLegalTerms(String legalTerms) {
        this.legalTerms = legalTerms;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Contract contract = (Contract) o;
        return Objects.equals(legalTerms, contract.legalTerms);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), legalTerms);
    }
}
