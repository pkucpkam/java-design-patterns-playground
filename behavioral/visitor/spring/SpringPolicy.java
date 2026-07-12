package behavioral.visitor.spring;

public interface SpringPolicy {
    <R> R accept(SpringPolicyVisitor<R> visitor);
}
