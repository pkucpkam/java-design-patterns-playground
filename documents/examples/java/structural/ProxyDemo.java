package structural;

/**
 * This demo illustrates the Proxy pattern.
 *
 * A Proxy provides a surrogate or placeholder for another object to control access to it.
 * This allows adding extra logic before or after the request is forwarded to the real object.
 *
 * There are several types of proxies. This example demonstrates two:
 * 1.  Protection Proxy: Controls access based on permissions.
 * 2.  Virtual Proxy: Manages the lifecycle of a resource-intensive object (lazy initialization).
 *
 * In this scenario, we have a `Document` that can be viewed or edited. We create a `DocumentProxy`
 * that controls access: any user can view, but only an 'ADMIN' user can edit. The proxy also
 * lazily initializes the `RealDocument` object, which is assumed to be resource-intensive to create.
 */


// A simple class to represent the user and their role.
class User {
    private final String role;

    public User(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}

// --- Subject and Real Subject ---

/**
 * 1. The Subject Interface
 * This defines the common interface for both the RealSubject and the Proxy,
 * so the client can use the proxy as if it were the real object.
 */
interface Document {
    void view();
    void edit(String content);
}

/**
 * 2. The Real Subject
 * This is the actual object that contains the business logic and data.
 * It's often a heavyweight object that we want to control access to.
 */
class RealDocument implements Document {
    private final String filename;
    private String content;

    public RealDocument(String filename) {
        this.filename = filename;
        // Simulate a resource-intensive operation, like loading a large file from disk.
        System.out.println("<<< Loading document '" + filename + "' from disk... (Heavy Operation) >>>");
        this.content = "This is the original content of the document.";
    }

    @Override
    public void view() {
        System.out.println("Displaying document '" + filename + "': " + content);
    }

    @Override
    public void edit(String newContent) {
        this.content = newContent;
        System.out.println("Successfully edited document '" + filename + "'. New content: '" + newContent + "'");
    }
}


// --- The Proxy ---

/**
 * 3. The Proxy
 * The proxy implements the same interface as the real subject.
 * It maintains a reference to the real subject and controls access to it.
 */
class DocumentProxy implements Document {
    // A reference to the real object. It will be null until the real object is needed.
    private RealDocument realDocument;
    private final String filename;
    private final User user;

    public DocumentProxy(String filename, User user) {
        this.filename = filename;
        this.user = user;
    }

    /**
     * This is the lazy initialization part (Virtual Proxy).
     * The RealDocument object is only created when it's actually needed.
     */
    private void ensureRealDocumentInitialized() {
        if (realDocument == null) {
            realDocument = new RealDocument(filename);
        }
    }

    @Override
    public void view() {
        // Anyone can view the document.
        // The real document is created only when this method is called for the first time.
        ensureRealDocumentInitialized();
        // Once access is granted and the object exists, delegate the call.
        realDocument.view();
    }

    @Override
    public void edit(String content) {
        // This is the access control part (Protection Proxy).
        // Before delegating, the proxy checks the user's permissions.
        if ("ADMIN".equalsIgnoreCase(user.getRole())) {
            // User has permission, so initialize the document (if not already) and delegate.
            ensureRealDocumentInitialized();
            realDocument.edit(content);
        } else {
            // User does not have permission. The proxy blocks the access.
            // The real object's edit method is never called.
            System.out.println("!!! Access Denied: User '" + user.getRole() + "' does not have permission to edit the document.");
        }
    }
}


// --- Demo ---
public class ProxyDemo {
    public static void main(String[] args) {
        User adminUser = new User("ADMIN");
        User normalUser = new User("USER");

        System.out.println("--- Scenario 1: Admin user interacts with the document proxy ---");
        // The client code interacts with the proxy, completely unaware of the real object.
        Document adminProxy = new DocumentProxy("financial_report.doc", adminUser);
        
        // At this point, the RealDocument has not been created yet.
        System.out.println("Proxy created, but real document is not loaded yet.");
        
        // The first call to a method on the proxy triggers the creation of the RealDocument.
        adminProxy.view(); // Access granted. Real document is loaded here.
        adminProxy.edit("This is the new content for the financial report."); // Access granted.

        System.out.println("
--------------------------------------------------
");

        System.out.println("--- Scenario 2: Normal user interacts with the document proxy ---");
        Document userProxy = new DocumentProxy("company_secrets.doc", normalUser);
        
        System.out.println("Proxy created, but real document is not loaded yet.");
        
        // Viewing is allowed, so the RealDocument is created upon this call.
        userProxy.view();
        
        // Editing is not allowed. The proxy blocks the call.
        userProxy.edit("Trying to add some graffiti."); // Access will be denied.
    }
}
