package behavioral.chain_of_responsibility.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderValidationPipeline {

    private final List<OrderValidationHandler> handlers;

    /**
     * Spring will automatically inject all registered OrderValidationHandler beans.
     * The list will be automatically sorted based on the @Order annotation on the beans.
     */
    @Autowired
    public OrderValidationPipeline(List<OrderValidationHandler> handlers) {
        this.handlers = handlers;
    }

    /**
     * Executes the validation pipeline sequentially.
     * If any handler returns false, validation fails and stops.
     */
    public boolean validate(Order order) {
        if (handlers == null || handlers.isEmpty()) {
            System.out.println("Spring Validation Pipeline: No validation handlers registered.");
            return true;
        }

        for (OrderValidationHandler handler : handlers) {
            if (!handler.handle(order)) {
                System.out.println("Spring Validation Pipeline: Rejected by " + handler.getClass().getSimpleName());
                return false;
            }
        }

        System.out.println("Spring Validation Pipeline: All validations passed for order: " + order.getOrderId());
        return true;
    }

    public List<OrderValidationHandler> getHandlers() {
        return handlers;
    }
}
