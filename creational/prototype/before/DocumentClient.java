package creational.prototype.before;

import java.util.ArrayList;

public class DocumentClient {

    public static void main(String[] args) {
        // 1. We create an original document (simulating an expensive or complex creation process)
        Document originalContract = new Document("Standard Contract", "Hereby we agree...", new ArrayList<>(java.util.List.of("legal", "contract", "standard")));

        // 2. Later, we want to create a new contract based on the standard one.
        // We have to manually copy everything.
        // THIS IS BAD:
        // - It's tedious and error-prone.
        // - If Document adds a new field, we have to update every place we do this.
        // - What if there are private fields without getters? We couldn't copy them!
        // - We are tightly coupled to the concrete class 'Document'.
        
        List<String> copiedTags = new ArrayList<>(originalContract.getTags()); // Manual deep copy of tags
        Document newContract = new Document(
                originalContract.getTitle(),
                originalContract.getContent(),
                copiedTags
        );

        newContract.setTitle("Custom Contract for ACME Corp");

        System.out.println("Original title: " + originalContract.getTitle());
        System.out.println("New title: " + newContract.getTitle());
    }
}
