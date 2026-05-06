package com.cse464;

/**
 * Main class - Entry point for the Graph Manipulation Tool
 * Demonstrates basic usage of the Graph API
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=== CSE 464 Graph Manipulation Tool ===\n");

        // Example 1: Create a simple graph manually
        System.out.println("--- Example 1: Create Simple Graph ---");
        exampleCreateGraph();

        System.out.println("\n--- Example 2: Parse DOT File ---");
        exampleParseGraph();

        System.out.println("\n--- Example 3: Add Nodes and Edges ---");
        exampleAddNodesEdges();

        System.out.println("\n--- Example 4: Graph Statistics ---");
        exampleGraphStatistics();
    }

    /**
     * Example 1: Create a simple graph
     */
    private static void exampleCreateGraph() {
        Graph graph = new Graph();

        // Add nodes
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");

        // Add edges
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        graph.addEdge("A", "C");

        // Print graph
        System.out.println(graph.toString());
    }

    /**
     * Example 2: Parse a DOT file
     */
    private static void exampleParseGraph() {
        String filepath = "src/main/resources/dot_files/simple_graph.dot";

        if (GraphParser.isValidDOTFile(filepath)) {
            Graph graph = GraphParser.parseGraph(filepath);
            System.out.println("Successfully parsed: " + filepath);
            System.out.println(graph.toString());

            // Output the graph in different formats
            try {
                graph.outputGraph("output/graph_output.txt");
                graph.outputDOTGraph("output/graph_output.dot");
                System.out.println("Graph saved to output/ directory");
            } catch (Exception e) {
                System.out.println("Note: Could not save output files (directory may not exist)");
            }
        } else {
            System.out.println("File not found or invalid DOT format: " + filepath);
        }
    }

    /**
     * Example 3: Add nodes and edges dynamically
     */
    private static void exampleAddNodesEdges() {
        Graph graph = new Graph();

        // Add multiple nodes at once
        String[] nodeLabels = {"Node1", "Node2", "Node3", "Node4"};
        int addedCount = graph.addNodes(nodeLabels);
        System.out.println("Added " + addedCount + " nodes");

        // Add edges
        graph.addEdge("Node1", "Node2");
        graph.addEdge("Node2", "Node3");
        graph.addEdge("Node3", "Node4");
        graph.addEdge("Node1", "Node4");

        System.out.println(graph.toString());
    }

    /**
     * Example 4: Graph statistics and analysis
     */
    private static void exampleGraphStatistics() {
        Graph graph = new Graph();

        // Create a sample graph
        graph.addNode("Start");
        graph.addNode("Process1");
        graph.addNode("Process2");
        graph.addNode("End");

        graph.addEdge("Start", "Process1");
        graph.addEdge("Start", "Process2");
        graph.addEdge("Process1", "End");
        graph.addEdge("Process2", "End");

        // Print statistics
        GraphProcessor.printGraphStats(graph);

        // Find source and sink nodes
        String[] sources = GraphProcessor.getSourceNodes(graph);
        String[] sinks = GraphProcessor.getSinkNodes(graph);

        System.out.println("Source Nodes: " + String.join(", ", sources));
        System.out.println("Sink Nodes: " + String.join(", ", sinks));

        // Display node degrees
        System.out.println("\nNode Degrees:");
        for (String node : graph.getNodes()) {
            System.out.println("  " + node + ": in-degree=" + GraphProcessor.getInDegree(graph, node) +
                    ", out-degree=" + GraphProcessor.getOutDegree(graph, node));
        }
    }
}
