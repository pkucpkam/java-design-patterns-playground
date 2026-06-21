package creational.prototype.after;

/**
 * Generic Prototype interface.
 * Using a custom interface instead of java.lang.Cloneable to avoid 
 * CloneNotSupportedException and to have strong typing on the return value.
 */
public interface Prototype<T> {
    T clone();
}
