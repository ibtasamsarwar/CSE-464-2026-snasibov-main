package com.cse464;

import java.util.*;

public abstract class GraphSearchTemplate {
    protected abstract void addPath(Deque<List<String>> frontier, List<String> path);
    protected abstract List<String> getNextPath(Deque<List<String>> frontier);

    /**
     * Hook to allow subclasses (RandomWalk) to choose neighbor order.
     */
    protected List<String> orderNeighbors(List<String> neighbors) {
        return neighbors;
    }

    public List<String> search(Graph graph, String src, String dst) {
        if (graph == null || src == null || dst == null) {
            return Collections.emptyList();
        }
        if (!graph.hasNode(src) || !graph.hasNode(dst)) {
            return Collections.emptyList();
        }

        Set<String> visited = new HashSet<>();
        Deque<List<String>> frontier = new ArrayDeque<>();
        List<String> startPath = new ArrayList<>();
        startPath.add(src);
        addPath(frontier, startPath);
        visited.add(src);

        while (!frontier.isEmpty()) {
            List<String> path = getNextPath(frontier);
            if (path == null || path.isEmpty()) continue;
            String current = path.get(path.size() - 1);
            if (current.equals(dst)) {
                return path;
            }

            List<String> neighbors = graph.getNeighbors(current);
            neighbors = orderNeighbors(neighbors);
            for (String nbr : neighbors) {
                if (!visited.contains(nbr)) {
                    List<String> newPath = new ArrayList<>(path);
                    newPath.add(nbr);
                    addPath(frontier, newPath);
                    visited.add(nbr);
                }
            }
        }
        return Collections.emptyList();
    }
}