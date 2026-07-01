package structural.proxy.after;

/**
 * Domain exception thrown by {@link AuthorizationProxy} when a user attempts
 * to access a resource they are not permitted to view.
 *
 * <p>Using a dedicated, typed exception instead of a generic
 * {@link RuntimeException} allows callers to handle authorization failures
 * distinctly from other runtime errors (e.g., returning HTTP 403 in a
 * Spring Boot controller).
 */
public class AccessDeniedException extends RuntimeException {

    /**
     * Constructs an {@code AccessDeniedException} with a descriptive message.
     *
     * @param message human-readable explanation of the denial
     */
    public AccessDeniedException(String message) {
        super(message);
    }
}
