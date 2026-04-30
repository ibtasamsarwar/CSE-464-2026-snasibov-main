package com.cse464;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * GraphParser class handles parsing DOT format graph files.
 * Feature 1: Parse a DOT graph file and create a Graph object
 */
public class GraphParser {

    /**
     * Parse a DOT format graph file and create a Graph object
     *
     * @param filepath path to the DOT file to parse
     * @return Graph object representing the parsed graph
     */
    public static Graph parseGraph(String filepath) {
        Graph graph = new Graph();
        if (filepath == null || filepath.trim().isEmpty()) {
            System.err.println("GraphParser.parseGraph: filepath is null or empty");
            return graph;
        }

        try {
            // Read the entire file
            String content = Files.readString(Paths.get(filepath));

            // Extract all node declarations and edges
            parseNodes(content, graph);
            parseEdges(content, graph);

        } catch (IOException e) {
            System.err.println("Error reading DOT file: " + e.getMessage());
        }
        
        return graph;
    }

    /**
     * Parse node declarations from DOT content
     *
     * @param content the DOT file content
     * @param graph the graph to add nodes to
     */
    private static void parseNodes(String content, Graph graph) {
        parseDeclaredNodes(content, graph);
        parseNodesFromEdges(content, graph);
    }

    private static void parseDeclaredNodes(String content, Graph graph) {
        // Pattern to match node declarations: nodeName [...]
        Pattern nodePattern = Pattern.compile("\\b([A-Za-z_][A-Za-z0-9_]*)\\s*\\[");
        Matcher matcher = nodePattern.matcher(content);

        while (matcher.find()) {
            String nodeLabel = matcher.group(1);
            graph.addNode(nodeLabel);
        }
    }

    private static void parseNodesFromEdges(String content, Graph graph) {
        // Match simple node names that appear in edges (right-hand side)
        Pattern simpleNodePattern = Pattern.compile("->\\s*([A-Za-z_][A-Za-z0-9_]*)");
        Matcher matcher = simpleNodePattern.matcher(content);

        while (matcher.find()) {
            String nodeLabel = matcher.group(1);
            graph.addNode(nodeLabel);
        }
    }

    /**
     * Parse edge declarations from DOT content
     *
     * @param content the DOT file content
     * @param graph the graph to add edges to
     */
    private static void parseEdges(String content, Graph graph) {
        // Pattern to match edges: node1 -> node2
        Pattern edgePattern = Pattern.compile("([A-Za-z_][A-Za-z0-9_]*)\\s*->\\s*([A-Za-z_][A-Za-z0-9_]*)");
        Matcher matcher = edgePattern.matcher(content);
        
        while (matcher.find()) {
            String source = matcher.group(1);
            String destination = matcher.group(2);
            
            // Ensure nodes exist before adding edge
            graph.addNode(source);
            graph.addNode(destination);
            graph.addEdge(source, destination);
        }
    }

    /**
     * Validate if a string is a valid DOT file format
     *
     * @param filepath path to the file to validate
     * @return true if file appears to be valid DOT format
     */
    public static boolean isValidDOTFile(String filepath) {
        if (filepath == null || filepath.trim().isEmpty()) {
            return false;
        }

        try {
            String content = Files.readString(Paths.get(filepath));
            // Basic validation: should contain "digraph" or "graph" keyword
            return content.contains("digraph") || content.contains("graph");
        } catch (IOException e) {
            return false;
        }
    }
}
