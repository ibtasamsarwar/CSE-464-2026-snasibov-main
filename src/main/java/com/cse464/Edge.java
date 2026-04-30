package com.cse464;

import java.util.Objects;

/**
 * Edge class represents a directed edge in the graph.
 * An edge connects a source node to a destination node.
 */
public class Edge {
    private String source;
    private String destination;

    /**
     * Constructor for Edge
     *
     * @param source the source node label
     * @param destination the destination node label
     */
    public Edge(String source, String destination) {
        this.source = source;
        this.destination = destination;
    }

    /**
     * Get the source node of this edge
     *
     * @return source node label
     */
    public String getSource() {
        return source;
    }

    /**
     * Get the destination node of this edge
     *
     * @return destination node label
     */
    public String getDestination() {
        return destination;
    }

    /**
     * String representation of the edge in format: source -> destination
     *
     * @return edge representation
     */
    @Override
    public String toString() {
        return source + " -> " + destination;
    }

    /**
     * Check if two edges are equal
     *
     * @param o object to compare
     * @return true if edges are equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Edge edge = (Edge) o;
        return Objects.equals(source, edge.source) && Objects.equals(destination, edge.destination);
    }

    /**
     * Hash code for Edge
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(source, destination);
    }
}
