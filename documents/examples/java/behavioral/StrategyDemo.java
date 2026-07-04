package behavioral;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This demo illustrates the Strategy pattern.
 * We have a context (SortedListContext) that needs to sort a list of numbers.
 * Instead of implementing the sorting logic itself, it delegates this task to a strategy.
 * We have different strategies for sorting (BubbleSort, QuickSort), which can be
 * switched at runtime.
 */

// 2. The Strategy Interface
// Defines a common interface for all supported algorithms.
interface SortingStrategy {
    void sort(List<Integer> list);
}

// 3. Concrete Strategies
// Each class implements a specific sorting algorithm.

/**
 * Implements sorting using the Bubble Sort algorithm.
 */
class BubbleSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("Sorting using Bubble Sort");
        // A simple (and inefficient) bubble sort implementation for demonstration.
        int n = list.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (list.get(j) > list.get(j + 1)) {
                    // swap elements
                    int temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }
}

/**
 * Implements sorting using the Quick Sort algorithm.
 */
class QuickSortStrategy implements SortingStrategy {
    @Override
    public void sort(List<Integer> list) {
        System.out.println("Sorting using Quick Sort");
        // In a real-world scenario, a full quick sort algorithm would be implemented here.
        // For simplicity and correctness, we'll use the highly optimized sort from Java's collections.
        Collections.sort(list);
    }
}


// 1. The Context
// The context maintains a reference to a Strategy object and delegates the work to it.
class SortedListContext {
    private SortingStrategy strategy;
    private List<Integer> list;

    public SortedListContext() {
        // Initialize with some data
        this.list = new ArrayList<>(Arrays.asList(5, 1, 4, 2, 8, 9, 3, 7, 6));
    }

    /**
     * The client uses this method to provide the context with a strategy.
     * @param strategy The sorting strategy to be used.
     */
    public void setStrategy(SortingStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * The context delegates the sorting task to the currently set strategy.
     */
    public void sort() {
        if (strategy == null) {
            throw new IllegalStateException("Sorting strategy has not been set!");
        }
        // The context calls the method of the strategy interface, without knowing the concrete implementation.
        strategy.sort(list);
        System.out.println("Sorted list: " + list);
    }
    
    // Method to reset the list for another sort
    public void resetList() {
        this.list = new ArrayList<>(Arrays.asList(5, 1, 4, 2, 8, 9, 3, 7, 6));
        System.out.println("List has been reset to: " + list);
    }
}

// --- Demo ---
// The client code that uses the context and strategies.
public class StrategyDemo {
    public static void main(String[] args) {
        // Create the context object.
        SortedListContext context = new SortedListContext();

        // --- Scenario 1: Use Bubble Sort for a small dataset ---
        System.out.println("--- Client decides to use Bubble Sort ---");
        // The client chooses a concrete strategy and passes it to the context.
        context.setStrategy(new BubbleSortStrategy());
        // The client asks the context to perform the action.
        context.sort();
        
        // Reset the list for the next demonstration
        context.resetList();

        System.out.println("
--- Scenario 2: Change strategy to Quick Sort for better performance ---");
        // The client can change the strategy at any time.
        context.setStrategy(new QuickSortStrategy());
        // The context now uses the new strategy seamlessly.
        context.sort();
    }
}
