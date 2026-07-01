package structural.proxy.after;

import java.util.Set;

/**
 * Protection Proxy — guards access to the wrapped {@link VideoService}.
 *
 * <p>This proxy intercepts every {@code loadVideo} call and verifies that the
 * requesting user has the required permission before delegating to the next
 * service in the chain (which may be the real service or another proxy).
 *
 * <p>Design Principles satisfied:
 * <ul>
 *     <li>Single Responsibility Principle (SRP): handles <em>only</em>
 *         authorization logic.</li>
 *     <li>Open/Closed Principle (OCP): the authorization ruleset can be
 *         extended (e.g., by injecting a different {@code Set}) without
 *         modifying this class.</li>
 *     <li>Liskov Substitution Principle (LSP): substitutable anywhere a
 *         {@link VideoService} is expected.</li>
 * </ul>
 *
 * <p>Real-world analogy: the ticket gate at a cinema — it checks your ticket
 * before letting you into the screening room; it does not project the film.
 */
public class AuthorizationProxy implements VideoService {

    private final VideoService delegate;
    private final Set<String> authorizedUsers;

    /**
     * Constructs the proxy.
     *
     * @param delegate        the next {@link VideoService} in the chain
     * @param authorizedUsers the set of user IDs permitted to load videos
     * @throws IllegalArgumentException if {@code delegate} is {@code null}
     */
    public AuthorizationProxy(VideoService delegate, Set<String> authorizedUsers) {
        if (delegate == null) {
            throw new IllegalArgumentException("Delegate VideoService must not be null.");
        }
        this.delegate = delegate;
        this.authorizedUsers = Set.copyOf(authorizedUsers); // defensive copy
    }

    /**
     * Checks authorization first; forwards the request only when the user
     * is permitted.
     *
     * @param videoId the video identifier
     * @param userId  the requesting user
     * @return video content, or throws {@link AccessDeniedException} if not authorized
     * @throws AccessDeniedException when the user is not authorized
     */
    @Override
    public String loadVideo(String videoId, String userId) {
        System.out.println("[AuthorizationProxy] Checking access for user: " + userId);

        if (!authorizedUsers.contains(userId)) {
            throw new AccessDeniedException(
                "User [" + userId + "] is not authorized to access video [" + videoId + "]."
            );
        }

        System.out.println("[AuthorizationProxy] Access granted for user: " + userId);
        return delegate.loadVideo(videoId, userId);
    }
}
