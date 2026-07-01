package structural.proxy.spring;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * Spring AOP Aspect — acts as both an <strong>Authorization Proxy</strong>
 * and a <strong>Logging Proxy</strong> using Spring's AOP infrastructure.
 *
 * <p>Spring generates a CGLIB/JDK dynamic proxy around the target bean and
 * routes every matching method call through this advice, producing the same
 * behavior as the hand-crafted proxy chain in the pure-Java implementation —
 * but with zero boilerplate in the service class.
 *
 * <p>This demonstrates that the Proxy Pattern is not merely an academic
 * concept: it is the mechanism that powers Spring Security, @Transactional,
 * @Cacheable, and virtually every cross-cutting Spring feature.
 */
@Aspect
@Component
public class VideoServiceAspect {

    /**
     * Authorized user IDs.  In a real application these would be resolved
     * from a Spring Security context or injected via a configuration property.
     */
    private static final Set<String> AUTHORIZED_USERS = Set.of("premium_user", "admin");

    /**
     * Around advice that:
     * <ol>
     *     <li>Logs the incoming request (Logging Proxy role)</li>
     *     <li>Checks authorization (Protection Proxy role)</li>
     *     <li>Proceeds to the real method if permitted</li>
     *     <li>Logs the outgoing response</li>
     * </ol>
     *
     * <p>Pointcut targets every method in {@link RealVideoService}.
     *
     * @param joinPoint the intercepted method call
     * @return the return value of the intercepted method
     * @throws Throwable any exception thrown by the real method
     */
    @Around("execution(* structural.proxy.spring.RealVideoService.*(..))")
    public Object proxyAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args   = joinPoint.getArgs();
        String videoId  = args.length > 0 ? String.valueOf(args[0]) : "unknown";
        String userId   = args.length > 1 ? String.valueOf(args[1]) : "unknown";

        // ---- Logging Proxy: log request ----
        System.out.printf("[AOP Proxy] REQUEST  — method=%s, video=%s, user=%s%n",
                joinPoint.getSignature().getName(), videoId, userId);

        // ---- Authorization Proxy: check access ----
        if (!"unknown".equals(userId) && !AUTHORIZED_USERS.contains(userId)) {
            String msg = "[AOP Proxy] ACCESS DENIED for user: " + userId;
            System.out.println(msg);
            throw new org.springframework.security.access.AccessDeniedException(msg);
        }

        // ---- Proceed to real method (or next proxy in chain) ----
        Object result = joinPoint.proceed();

        // ---- Logging Proxy: log response ----
        System.out.printf("[AOP Proxy] RESPONSE — method=%s, result=%s%n",
                joinPoint.getSignature().getName(), result);

        return result;
    }
}
