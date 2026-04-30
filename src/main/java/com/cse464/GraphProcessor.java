package com.cse464;

/**
 * GraphProcessor class provides utility methods for graph manipulation
 * and processing operations.
 */
public class GraphProcessor {

    /**
     * Process a graph and output its statistics
     *
     * @param graph the graph to process
     */
    public static void printGraphStats(Graph graph) {
        System.out.println("=== Graph Statistics ===");
        System.out.println("Total Nodes: " + graph.getNodeCount());
        System.out.println("Total Edges: " + graph.getEdgeCount());
        
        if (graph.getNodeCount() > 0) {
            System.out.println("\nNodes: " + String.join(", ", graph.getNodes()));
        }
        
        if (graph.getEdgeCount() > 0) {
            System.out.println("\nEdges:");
            for (Edge edge : graph.getEdges()) {
                System.out.println("  " + edge);
            }
        }
        System.out.println();
    }

    /**
     * Find all nodes with no incoming edges (source nodes)
     *
     * @param graph the graph to analyze
     * @return array of source node labels
     */
    public static String[] findSourceNodes(Graph graph) {
        java.util.Set<String> sourceNodes = new java.util.HashSet<>(graph.getNodes());
        
        for (Edge edge : graph.getEdges()) {
            sourceNodes.remove(edge.getDestination());
        }
        
        return sourceNodes.toArray(new String[0]);
    }

    /**
     * Find all nodes with no outgoing edges (sink nodes)
     *
     * @param graph the graph to analyze
     * @return array of sink node labels
     */
    public static String[] findSinkNodes(Graph graph) {
        java.util.Set<String> sinkNodes = new java.util.HashSet<>(graph.getNodes());
        
        for (Edge edge : graph.getEdges()) {
            sinkNodes.remove(edge.getSource());
        }
        
        return sinkNodes.toArray(new String[0]);
    }

    /**
     * Get all outgoing edges from a given node
     *
     * @param graph the graph to analyze
     * @param nodeLabel the node label
     * @return array of edges originating from the node
     */
    public static Edge[] getOutgoingEdges(Graph graph, String nodeLabel) {
        return graph.getEdges().stream()
                .filter(edge -> edge.getSource().equals(nodeLabel))
                .toArray(Edge[]::new);
    }

    /**
     * Get all incoming edges to a given node
     *
     * @param graph the graph to analyze
     * @param nodeLabel the node label
     * @return array of edges pointing to the node
     */
    public static Edge[] getIncomingEdges(Graph graph, String nodeLabel) {
        return graph.getEdges().stream()
                .filter(edge -> edge.getDestination().equals(nodeLabel))
                .toArray(Edge[]::new);
    }

    /**
     * Calculate the in-degree of a node
     *
     * @param graph the graph to analyze
     * @param nodeLabel the node label
     * @return in-degree (number of incoming edges)
     */
    public static int getInDegree(Graph graph, String nodeLabel) {
        return getIncomingEdges(graph, nodeLabel).length;
    }

    /**
     * Calculate the out-degree of a node
     *
     * @param graph the graph to analyze
     * @param nodeLabel the node label
     * @return out-degree (number of outgoing edges)
     */
    public static int getOutDegree(Graph graph, String nodeLabel) {
        return getOutgoingEdges(graph, nodeLabel).length;
    }
}
