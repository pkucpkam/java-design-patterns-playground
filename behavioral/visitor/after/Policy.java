package behavioral.visitor.after;

public interface Policy {
    <R> R accept(PolicyVisitor<R> visitor);
}
