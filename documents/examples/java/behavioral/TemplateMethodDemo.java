package behavioral;

/**
 * This demo illustrates the Template Method pattern.
 *
 * The Template Method pattern defines the skeleton of an algorithm in a base class
 * but lets subclasses override specific steps of the algorithm without changing its structure.
 *
 * In this example, `DataProcessor` is an abstract class that defines a `processData()`
 * template method. This method outlines a fixed sequence of steps: read, process, validate, and save.
 * The concrete subclasses, `CsvDataProcessor` and `JsonDataProcessor`, provide their own
 * implementations for the variable steps (`readData`, `processInternal`) while reusing the
 * common steps (`saveData`) and the overall structure defined in the base class.
 */


// --- Abstract Class with Template Method ---

/**
 * 1. The Abstract Class
 * This class defines the template method and the primitive operations (steps) that subclasses must implement.
 */
abstract class DataProcessor {

    /**
     * 1a. The Template Method.
     * It is declared as `final` to prevent subclasses from altering the algorithm's structure.
     * It defines a sequence of calls to other methods, some of which are abstract and some are implemented here.
     */
    public final void processData() {
        // Step 1: Implemented by subclass
        readData();
        
        // Step 2: Implemented by subclass
        processInternal();
        
        // Step 3 (optional): A "hook" that subclasses can override.
        if (isValidationNeeded()) {
            validateData();
        }
        
        // Step 4: A common step, implemented in the base class.
        saveData();
    }

    // --- Steps of the Algorithm ---

    /**
     * 1b. Abstract "step" methods.
     * These must be implemented by concrete subclasses.
     */
    protected abstract void readData();
    protected abstract void processInternal();

    /**
     * 1c. A concrete method, common for all subclasses.
     * This part of the algorithm is invariant.
     */
    private void saveData() {
        System.out.println("   - Saving processed data to the database...");
    }

    /**
     * 1d. A "hook" method.
     * This is a method with a default implementation (often empty). Subclasses can override it to
     * provide optional behavior, "hooking" into the algorithm.
     * By default, validation is performed.
     */
    protected boolean isValidationNeeded() {
        return true;
    }
    
    private void validateData() {
        System.out.println("   - Performing data validation...");
    }
}


// --- Concrete Classes ---

/**
 * 2. A Concrete Class
 * This class implements the abstract steps of the algorithm for a specific data type (CSV).
 */
class CsvDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("   - Reading data from a CSV file.");
    }

    @Override
    protected void processInternal() {
        System.out.println("   - Processing CSV data (parsing rows, handling commas).");
    }
    
    // This class uses the default hook implementation for validation.
}

/**
 * 2. Another Concrete Class
 * This one implements the steps for JSON data and overrides the hook method.
 */
class JsonDataProcessor extends DataProcessor {
    @Override
    protected void readData() {
        System.out.println("   - Reading data from a JSON file.");
    }

    @Override
    protected void processInternal() {
        System.out.println("   - Processing JSON data (parsing objects and arrays).");
    }
    
    /**
     * Overriding the hook method to change the algorithm's behavior.
     * This specific processor decides that validation is not needed.
     */
    @Override
    protected boolean isValidationNeeded() {
        System.out.println("   - (Hook) JSON processor has its own validation scheme. Skipping default validation.");
        return false;
    }
}


// --- Demo ---
public class TemplateMethodDemo {
    public static void main(String[] args) {
        System.out.println("--- Starting CSV file processing ---");
        // The client creates a specific processor...
        DataProcessor csvProcessor = new CsvDataProcessor();
        // ...and calls the template method. It doesn't need to know the individual steps.
        csvProcessor.processData();

        System.out.println("
--- Starting JSON file processing ---");
        DataProcessor jsonProcessor = new JsonDataProcessor();
        jsonProcessor.processData();
    }
}
