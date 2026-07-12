package behavioral.visitor.after;

public interface PolicyVisitor<R> {
    R visit(ResidentialPolicy policy);
    R visit(CommercialPolicy policy);
    R visit(AutoPolicy policy);
}
