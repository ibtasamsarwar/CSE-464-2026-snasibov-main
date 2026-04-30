package com.cse464;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * Graph class represents a directed graph with nodes and edges.
 * Supports operations like adding nodes, adding edges, and outputting the graph.
 */
public class Graph {
    private Set<String> nodes;
    private Set<Edge> edges;

    /**
     * Constructor for Graph - initializes empty graph
     */
    public Graph() {
        this.nodes = new LinkedHashSet<>();
        this.edges = new LinkedHashSet<>();
    }

    /**
     * Feature 2: Add a single node to the graph
     * Checks for duplicate labels
     *
     * @param label the label of the node to add
     * @return true if node was added, false if duplicate
     */
    public boolean addNode(String label) {
        if (label == null || label.trim().isEmpty()) {
            return false;
        }
        return nodes.add(label.trim());
    }

    /**
     * Feature 2: Add multiple nodes to the graph
     * Checks for duplicate labels
     *
     * @param labels array of node labels to add
     * @return number of nodes successfully added
     */
    public int addNodes(String[] labels) {
        if (labels == null) {
            return 0;
        }
        int count = 0;
        for (String label : labels) {
            if (addNode(label)) {
                count++;
            }
        }
        return count;
    }

    /**
     * Feature 3: Add an edge between two nodes
     * Checks for duplicate edges
     *
     * @param srcLabel the source node label
     * @param dstLabel the destination node label
     * @return true if edge was added, false if duplicate or nodes don't exist
     */
    public boolean addEdge(String srcLabel, String dstLabel) {
        if (srcLabel == null || dstLabel == null) {
            return false;
        }
        
        srcLabel = srcLabel.trim();
        dstLabel = dstLabel.trim();
        
        // Ensure both nodes exist
        if (!nodes.contains(srcLabel) || !nodes.contains(dstLabel)) {
            return false;
        }
        
        Edge edge = new Edge(srcLabel, dstLabel);
        return edges.add(edge);
    }

    /**
     * Get the number of nodes in the graph
     *
     * @return number of nodes
     */
    public int getNodeCount() {
        return nodes.size();
    }

    /**
     * Get the number of edges in the graph
     *
     * @return number of edges
     */
    public int getEdgeCount() {
        return edges.size();
    }

    /**
     * Get all nodes in the graph
     *
     * @return set of node labels
     */
    public Set<String> getNodes() {
        return new LinkedHashSet<>(nodes);
    }

    /**
     * Get all edges in the graph
     *
     * @return set of edges
     */
    public Set<Edge> getEdges() {
        return new LinkedHashSet<>(edges);
    }

    /**
     * Check if a node exists in the graph
     *
     * @param label the node label
     * @return true if node exists, false otherwise
     */
    public boolean hasNode(String label) {
        return nodes.contains(label);
    }

    /**
     * Check if an edge exists in the graph
     *
     * @param srcLabel source node label
     * @param dstLabel destination node label
     * @return true if edge exists, false otherwise
     */
    public boolean hasEdge(String srcLabel, String dstLabel) {
        return edges.contains(new Edge(srcLabel, dstLabel));
    }

    /**
     * Feature 1: String representation of the graph
     * Shows node count, node list, edge count, and edge list
     *
     * @return graph information as string
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Graph Information:\n");
        sb.append("Number of Nodes: ").append(nodes.size()).append("\n");
        
        if (!nodes.isEmpty()) {
            sb.append("Node Labels: ");
            sb.append(String.join(" ", nodes)).append("\n");
        }
        
        sb.append("Number of Edges: ").append(edges.size()).append("\n");
        
        if (!edges.isEmpty()) {
            sb.append("Edge List:\n");
            for (Edge edge : edges) {
                sb.append("  ").append(edge.toString()).append("\n");
            }
        }
        
        return sb.toString();
    }

    /**
     * Feature 1: Output graph information to a text file
     *
     * @param filepath the path to output file
     */
    public void outputGraph(String filepath) {
        try {
            Files.write(Paths.get(filepath), toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error writing graph to file: " + e.getMessage());
        }
    }

    /**
     * Feature 4: Output the graph in DOT format
     *
     * @param filepath the path to output DOT file
     */
    public void outputDOTGraph(String filepath) {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph {\n");
        
        for (Edge edge : edges) {
            sb.append("    ").append(edge.getSource()).append(" -> ")
                    .append(edge.getDestination()).append(";\n");
        }
        
        sb.append("}\n");
        
        try {
            Files.write(Paths.get(filepath), sb.toString().getBytes());
        } catch (IOException e) {
            System.err.println("Error writing DOT file: " + e.getMessage());
        }
    }

    /**
     * Feature 4: Output the graph in graphical format (PNG, JPG, PDF)
     * This method requires Graphviz to be installed
     *
     * @param filepath the path to output graphics file
     * @param format the output format (png, jpg, pdf, etc.)
     */
    public void outputGraphics(String filepath, String format) {
        // Implementation will use graphviz-java library
        // This is a placeholder for the actual implementation
        System.out.println("Graphics output not yet implemented");
        System.out.println("Would save to: " + filepath + " in format: " + format);
    }
}
