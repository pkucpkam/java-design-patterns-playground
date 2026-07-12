package behavioral.visitor.spring;

public interface SpringPolicyVisitor<R> {
    R visit(SpringResidentialPolicy policy);
    R visit(SpringCommercialPolicy policy);
    R visit(SpringAutoPolicy policy);
}
