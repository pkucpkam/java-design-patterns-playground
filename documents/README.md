# Welcome to the Design Patterns Learning Hub

This is a complete set of materials and tutorials on Design Patterns, compiled and written from the perspective of a **Senior Backend Engineer & Technical Instructor**. The goal is to provide a practical, real-world-focused learning experience that is easy to understand and apply directly to your projects, especially in the context of backend development with Java and Spring Boot.

---

## How to Learn Effectively

This curriculum is designed to be flexible, but for the best results, we recommend a 3-step approach for each pattern:

1.  **Understand the "Why" (Theory):**
    *   Start by reading the `.md` file for the pattern. Focus on the **"Tổng quan" (Overview)** and **"Ví dụ đời thường" (Real-world Example)** sections first. Don't worry about the code yet.
    *   The most important thing is to understand **the problem the pattern solves**. If you don't understand the problem, the solution will never make sense.

2.  **Analyze the "How" (Code):**
    *   Once you grasp the concept, move to the **"Code ví dụ (Java)"** and **"Ứng dụng trong Spring Boot"** sections.
    *   Open the corresponding demo file in the `examples/` folder. Read the comments and trace the flow of execution. See how the components (Interface, Concrete Classes, Context, etc.) interact.
    *   **Ask yourself:** How does this code structure solve the problem identified in step 1?

3.  **Solidify with Practice (Your Turn):**
    *   **Don't just read!** Try to re-implement the pattern from scratch in a new file.
    *   Think of a different real-world problem and try to apply the pattern to it. For example, for the Strategy pattern, instead of sorting, try implementing different shipping calculation methods.
    *   This active recall and application is the most critical step for true understanding.

---

## Suggested Roadmap

For a structured learning path, please refer to the [**roadmap.md**](roadmap.md) file. It categorizes the patterns by difficulty and suggests an order for learning them, helping you build knowledge progressively.

---

## General Best Practices for Using Design Patterns

*   **Don't Force It (Avoid "Golden Hammer" Syndrome):** Just because you learned a new pattern doesn't mean you should use it everywhere. The best solution is often the simplest one. If a simple `if` statement or a direct method call is clear and maintainable, use it. **Code that is easy to understand is more valuable than code that is "cleverly" engineered.**
*   **Start Simple:** Begin with a simple, straightforward implementation first. As the code evolves and requirements become more complex, you might recognize the need for a pattern. Refactor towards the pattern then, rather than starting with it. This is often called the "Rule of Three" - don't apply a pattern until you see the same problem in at least three different places.
*   **Favor Composition Over Inheritance:** This is a core design principle that many patterns (like Strategy, Decorator, and Proxy) adhere to. Composition leads to more flexible and less brittle designs than rigid class hierarchies.
*   **Understand the Trade-offs:** No pattern is a silver bullet. A pattern might simplify one part of your system while adding complexity elsewhere (e.g., by increasing the number of classes). Always consider the trade-offs in the context of your specific project.

We hope you find this material useful!
