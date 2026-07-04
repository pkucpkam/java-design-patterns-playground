package behavioral;

/**
 * This demo illustrates the Chain of Responsibility pattern.
 *
 * A request is passed along a chain of handlers. Each handler decides either to process
 * the request or to pass it to the next handler in the chain.
 * This decouples the sender of a request from its receiver.
 *
 * In this example, we model an expense approval system. A request for approving a purchase
 * is sent up a chain of command (Manager -> Department Head -> CEO). Each level can approve
* up to a certain amount, otherwise, they pass the request to their superior.
 */

// --- The Request ---
// A simple class representing the request to be processed.
class PurchaseRequest {
    private final double amount;
    private final String purpose;

    public PurchaseRequest(double amount, String purpose) {
        this.amount = amount;
        this.purpose = purpose;
    }

    public double getAmount() {
        return amount;
    }

    public String getPurpose() {
        return purpose;
    }
}


// --- The Handlers ---

/**
 * 1. The Handler Interface (or Abstract Class)
 * Defines the interface for handling requests and optionally implements the successor link.
 * Using an abstract class is common to provide default chaining behavior.
 */
abstract class Approver {
    protected Approver nextApprover;

    /**
     * Sets the next handler in the chain.
     * @param nextApprover The next approver.
     */
    public void setNext(Approver nextApprover) {
        this.nextApprover = nextApprover;
    }

    /**
     * The method to process the request.
     * @param request The purchase request to be processed.
     */
    public abstract void processRequest(PurchaseRequest request);
}

/**
 * 3. Concrete Handlers
 * Each handler implements its own logic for processing the request.
 * If it can't handle the request, it passes it to the next handler in the chain.
 */
class Manager extends Approver {
    private static final double APPROVAL_LIMIT = 500.0;

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getAmount() <= APPROVAL_LIMIT) {
            System.out.println("Manager approves purchase of $" + request.getAmount() + " for '" + request.getPurpose() + "'.");
        } else if (nextApprover != null) {
            System.out.println("Manager cannot approve amount $" + request.getAmount() + ". Passing to Department Head.");
            nextApprover.processRequest(request);
        }
    }
}

class DepartmentHead extends Approver {
    private static final double APPROVAL_LIMIT = 5000.0;

    @Override
    public void processRequest(PurchaseRequest request) {
        if (request.getAmount() <= APPROVAL_LIMIT) {
            System.out.println("Department Head approves purchase of $" + request.getAmount() + " for '" + request.getPurpose() + "'.");
        } else if (nextApprover != null) {
            System.out.println("Department Head cannot approve amount $" + request.getAmount() + ". Passing to CEO.");
            nextApprover.processRequest(request);
        }
    }
}

class CEO extends Approver {
    // The CEO is the final approver and can approve any amount.
    @Override
    public void processRequest(PurchaseRequest request) {
        System.out.println("CEO approves purchase of $" + request.getAmount() + " for '" + request.getPurpose() + "'.");
    }
}


// --- Demo ---
// The client code that builds the chain and sends requests.
public class ChainOfResponsibilityDemo {

    private static Approver getApprovalChain() {
        // 4. The client builds the chain of responsibility
        Approver manager = new Manager();
        Approver head = new DepartmentHead();
        Approver ceo = new CEO();

        // Set the chain: Manager -> DepartmentHead -> CEO
        manager.setNext(head);
        head.setNext(ceo);
        
        return manager;
    }

    public static void main(String[] args) {
        Approver approvalChain = getApprovalChain();

        // The client sends requests to the first handler in the chain.
        // It does not need to know who will ultimately handle the request.
        
        System.out.println("--- Sending a request for $300 (should be handled by Manager) ---");
        approvalChain.processRequest(new PurchaseRequest(300, "Office Supplies"));
        
        System.out.println("
--- Sending a request for $2500 (should be handled by Department Head) ---");
        approvalChain.processRequest(new PurchaseRequest(2500, "New Laptops"));
        
        System.out.println("
--- Sending a request for $12000 (should be handled by CEO) ---");
        approvalChain.processRequest(new PurchaseRequest(12000, "Server Upgrade"));
    }
}
