package com.cse464;

import java.util.*;

public class RandomWalkSearch extends GraphSearchTemplate implements SearchStrategy {
    private final Random random;

    public RandomWalkSearch() {
        this(new Random());
    }

    public RandomWalkSearch(Random random) {
        this.random = random == null ? new Random() : random;
    }

    @Override
    protected void addPath(Deque<List<String>> frontier, List<String> path) {
        frontier.addLast(path);
    }

    @Override
    protected List<String> getNextPath(Deque<List<String>> frontier) {
        return frontier.removeFirst();
    }

    @Override
    protected List<String> orderNeighbors(List<String> neighbors) {
        if (neighbors == null || neighbors.isEmpty()) return Collections.emptyList();
        String pick = neighbors.get(random.nextInt(neighbors.size()));
        return Collections.singletonList(pick);
    }

    @Override
    public List<String> search(Graph graph, String src, String dst) {
        return super.search(graph, src, dst);
    }
}