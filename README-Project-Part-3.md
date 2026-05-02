# CSE 464 – Software QA & Testing

## Project Part 3: Refactoring, Design Patterns & Graph Search Enhancements

---

## 1. Project Overview

This project extends the existing **Graph Manipulation Tool** by introducing structured refactoring and applying industry-standard design patterns to implement flexible and scalable graph search algorithms.

The objective of this phase is to enhance code quality, modularity, and extensibility while supporting advanced search behaviors.

### Key Enhancements in Part 3

* Performed **5 structured refactorings** to improve code quality
* Implemented **BFS and DFS using the Template Method Pattern**
* Enabled runtime algorithm selection via the **Strategy Pattern**
* Introduced a new **Random Walk Search Algorithm**
* Added **comprehensive unit tests** for all search behaviors
* Implemented a **Pull Request workflow with code review simulation**

---

## 2. Branch & Version Control

* **Development Branch:** `refactor`
* **Base Branch for Merge:** `main`

All changes for Part 3 were implemented and tracked within the `refactor` branch following clean and consistent commit practices.

---

## 3. Build & Compilation

The project follows Maven-based build requirements.

### Required Command

```bash
mvn package
```

### Verified With

```bash
mvn clean package
```

This ensures:

* Successful compilation
* All tests pass
* The project is ready for execution

---

## 4. Graph Search Execution

All search algorithms are executed through the centralized method:

```java
GraphSearchContext.graphSearch(...)
```

---

### 4.1 Breadth-First Search (BFS)

```java
GraphSearchContext context = new GraphSearchContext();
List<String> path = context.graphSearch(graph, "A", "C", Algorithm.BFS);
System.out.println(path);
```

**Behavior:**

* Explores the graph level-by-level
* Guarantees the shortest path (in terms of edges) in unweighted graphs

---

### 4.2 Depth-First Search (DFS)

```java
GraphSearchContext context = new GraphSearchContext();
List<String> path = context.graphSearch(graph, "A", "C", Algorithm.DFS);
System.out.println(path);
```

**Behavior:**

* Explores the graph depth-wise
* Path depends on traversal order
* Does not guarantee the shortest path

---

### 4.3 Random Walk Search

```java
GraphSearchContext context = new GraphSearchContext();
List<String> path = context.graphSearch(graph, "A", "C", Algorithm.RANDOM_WALK);
System.out.println(path);
```

**Behavior:**

* Randomly selects the next node from available neighbors
* Produces different results on each execution
* Demonstrates non-deterministic traversal

---

## 5. Output Demonstration

Screenshots should be added before final PDF export:

* BFS Execution Output
* DFS Execution Output
* Random Walk outputs (multiple runs to demonstrate randomness)

---

## 6. Refactoring Summary

Five targeted refactorings were applied to improve readability, maintainability, and overall structure.

### Refactor 1: Method Renaming

* `findSourceNodes → getSourceNodes`
* `findSinkNodes → getSinkNodes`

Improved semantic clarity and naming consistency.

---

### Refactor 2: Code Extraction

* Extracted file writing logic into:

```java
writeStringToFile(...)
```

Reduced duplication and centralized file I/O handling.

---

### Refactor 3: Separation of Concerns

* Introduced dedicated search classes:

  * `GraphSearchTemplate`
  * `BFSSearch`
  * `DFSSearch`
  * `RandomWalkSearch`
  * `GraphSearchContext`

Decoupled algorithm logic from the core graph structure.

---

### Refactor 4: Robust Input Handling

* Added validation for:

  * Invalid file paths
  * Null or empty inputs

Improved reliability and error handling.

---

### Refactor 5: Method Decomposition

* Split large parsing logic into:

  * `parseDeclaredNodes(...)`
  * `parseNodesFromEdges(...)`

Enhanced readability and simplified debugging.

---

## 7. Refactoring Commit Links

1. Method Renaming
   https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/0c2f332

2. Code Extraction
   https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/8632e35

3. Structure Refactor
   https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/8165a2a

4. Exception Handling
   https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/181b9ed

5. Method Decomposition
   https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/173f920

---

## 8. Template Method Pattern Implementation

The **Template Method Pattern** defines a unified graph search workflow while allowing algorithm-specific customization.

### Structure

* **Abstract Base Class:** `GraphSearchTemplate`

### Shared Workflow

* Input validation
* Initialization of frontier and visited nodes
* Iterative traversal
* Destination checking
* Path expansion

### Customizable Steps

```java
addPath(...)
getNextPath(...)
```

### Implementations

* `BFSSearch` → Queue-based behavior
* `DFSSearch` → Stack-based behavior

This approach promotes code reuse and eliminates duplication.

---

### Template Pattern Commit

https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/e9db35c

---

## 9. Strategy Pattern Implementation

The **Strategy Pattern** enables runtime selection of graph search algorithms.

### Structure

* **Interface:** `SearchStrategy`
* **Concrete Strategies:**

  * `BFSSearch`
  * `DFSSearch`
  * `RandomWalkSearch`
* **Context Class:** `GraphSearchContext`

### Selection Logic

```java
Algorithm.BFS / DFS / RANDOM_WALK
```

This design:

* Decouples algorithm logic
* Enables easy extension
* Improves scalability

---

### Strategy Pattern Commit

https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/9de5f0e

---

## 10. Random Walk Implementation

A probabilistic search algorithm was introduced.

### Logic

* From the current node, select a random neighbor
* Continue traversal until the destination is found or no further moves are possible

### Key Properties

* Non-deterministic behavior
* Demonstrates randomness in traversal
* Fully integrated with:

  * Template Pattern
  * Strategy Pattern

---

### Random Walk Commit

https://github.com/snasibov-1/CSE-464-2026-snasibov-main/commit/170cdbb

---

## 11. Continuous Integration (CI)

Include screenshots of:

* Successful GitHub Actions build
* Passing test results

This confirms:

* Build stability
* Automated verification

---

## 12. Pull Request

* PR created from `refactor → main`

**Link:** https://github.com/snasibov-1/CSE-464-2026-snasibov-main/pull/1

The PR is intentionally left unmerged as required before demonstration.

---

## 13. Testing Coverage

### Implemented Tests

* BFS path validation
* DFS path validation
* Random Walk behavior validation
* Strategy selection correctness

### Build Results

```bash
mvn clean package
```

* Tests Run: 27
* Failures: 0
* Errors: 0
* Status: SUCCESS

---

## 14. Code Review Simulation

### Review Comments & Responses

1. Duplicate neighbor traversal logic
   Refactored into a reusable helper method

2. Random Walk dead-end handling
   Added fallback condition to prevent failure

3. Strategy error handling
   Introduced explicit exception handling

4. Missing unit tests
   Added comprehensive test coverage

5. README clarity improvements
   Enhanced explanations and added placeholders for screenshots


---

## Conclusion

This project demonstrates:

* Clean and structured refactoring practices
* Effective application of design patterns
* Scalable and extensible architecture
* Strong testing and CI discipline

The system is now modular, maintainable, and well-prepared for future enhancements.

---
