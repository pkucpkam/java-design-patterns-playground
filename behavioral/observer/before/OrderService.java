package behavioral.observer.before;

public class OrderService {
    private EmailTracker emailTracker;
    private SmsTracker smsTracker;

    public OrderService() {
        // Tightly coupled instantiation
        this.emailTracker = new EmailTracker();
        this.smsTracker = new SmsTracker();
    }

    public void updateOrderStatus(Order order, String newStatus) {
        order.setStatus(newStatus);
        
        // Tightly coupled notifications
        String message = "Your order " + order.getId() + " status is now " + newStatus;
        
        if (order.getCustomerEmail() != null && !order.getCustomerEmail().isEmpty()) {
            emailTracker.sendEmail(order.getCustomerEmail(), message);
        }
        
        if (order.getCustomerPhone() != null && !order.getCustomerPhone().isEmpty()) {
            smsTracker.sendSms(order.getCustomerPhone(), message);
        }
        
        // Violation of Open-Closed Principle (OCP):
        // If we want to add Mobile App push notification, we must modify this class.
        // Also violates Dependency Inversion Principle (DIP):
        // OrderService depends on concrete classes (EmailTracker, SmsTracker) instead of abstractions.
    }
}
