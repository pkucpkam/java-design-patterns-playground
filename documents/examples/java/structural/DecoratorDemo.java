package structural;

/**
 * This demo illustrates the Decorator pattern.
 *
 * The Decorator pattern allows adding new functionality to an object dynamically
 * without altering its structure. It involves a set of decorator classes that are used
 * to wrap concrete components.
 *
 * In this example, we have a simple `DataSource` that can read/write data. We want to
 * add functionality like encryption and compression without changing the `FileDataSource` class.
 * We create `EncryptionDecorator` and `CompressionDecorator` that "wrap" the original
 * source and add their respective functionalities.
 */

// --- Component and Concrete Component ---

/**
 * 1. The Component Interface
 * Defines the common interface for both the object being wrapped and the decorators.
 */
interface DataSource {
    void writeData(String data);
    String readData();
}

/**
 * 2. The Concrete Component
 * The base object that we can decorate.
 */
class FileDataSource implements DataSource {
    private final String filename;
    private String content;

    public FileDataSource(String filename) {
        this.filename = filename;
        this.content = "";
    }

    @Override
    public void writeData(String data) {
        this.content = data;
        System.out.println("-> Writing raw data to file: " + filename);
    }

    @Override
    public String readData() {
        System.out.println("<- Reading raw data from file: " + filename);
        return content;
    }
}


// --- Decorators ---

/**
 * 3. The Base Decorator (Optional but recommended)
 * This abstract class implements the component interface and holds a reference to a
 * component object. It delegates all calls to the wrapped component, making it easy
 * for concrete decorators to add behavior by overriding methods.
 */
abstract class DataSourceDecorator implements DataSource {
    protected DataSource wrappee; // The object being wrapped

    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }

    // Default implementation: delegate the call to the wrapped object.
    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }

    @Override
    public String readData() {
        return wrappee.readData();
    }
}

/**
 * 4. Concrete Decorators
 * These classes add new responsibilities to the component.
 */
class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }

    /**
     * Adds encryption behavior BEFORE passing the data to the wrappee's writeData method.
     */
    @Override
    public void writeData(String data) {
        System.out.println("   Encrypting data.");
        String encryptedData = "[Encrypted] " + data + " [Encrypted]";
        super.writeData(encryptedData); // Delegate to the wrapped object
    }

    /**
     * Adds decryption behavior AFTER the wrappee's readData method is called.
     */
    @Override
    public String readData() {
        String encryptedData = super.readData(); // Delegate to the wrapped object
        System.out.println("   Decrypting data.");
        // Simple "decryption" for demonstration
        return encryptedData.replace("[Encrypted]", "").trim();
    }
}

class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource source) {
        super(source);
    }

    /**
     * Adds compression behavior BEFORE delegating.
     */
    @Override
    public void writeData(String data) {
        System.out.println("   Compressing data.");
        String compressedData = "[Compressed] " + data + " [Compressed]";
        super.writeData(compressedData);
    }

    /**
     * Adds decompression behavior AFTER delegating.
     */
    @Override
    public String readData() {
        String compressedData = super.readData();
        System.out.println("   Decompressing data.");
        return compressedData.replace("[Compressed]", "").trim();
    }
}


// --- Demo ---
public class DecoratorDemo {
    public static void main(String[] args) {
        String message = "This is a secret message.";
        
        // 1. Start with a plain, undecorated data source object.
        DataSource plainSource = new FileDataSource("mydata.txt");
        System.out.println("--- 1. Writing and reading plain data ---");
        plainSource.writeData(message);
        System.out.println("Data read back: "" + plainSource.readData() + """);

        System.out.println("
--- 2. Now, wrap the source with an encryption decorator ---");
        // 2. Wrap the plain object with the first decorator.
        DataSource encryptedSource = new EncryptionDecorator(plainSource);
        encryptedSource.writeData(message);
        System.out.println("Data read back: "" + encryptedSource.readData() + """);
        
        System.out.println("
--- 3. Now, wrap the already-encrypted source with a compression decorator ---");
        // 3. You can wrap a decorator with another decorator.
        DataSource compressedAndEncryptedSource = new CompressionDecorator(new EncryptionDecorator(plainSource));
        compressedAndEncryptedSource.writeData(message);
        
        System.out.println("
--- Reading the fully decorated data back ---");
        // The read process happens in the reverse order of decoration: decompress then decrypt.
        String finalData = compressedAndEncryptedSource.readData();
        System.out.println("Final data read back: "" + finalData + """);
        
        // Verification
        assert message.equals(finalData);
    }
}
