package com.cse464;

import java.util.List;

public class GraphSearchContext {
    public List<String> graphSearch(Graph graph, String src, String dst, Algorithm algo) {
        SearchStrategy strategy;
        switch (algo) {
            case BFS:
                strategy = new BFSSearch();
                break;
            case DFS:
                strategy = new DFSSearch();
                break;
            case RANDOM_WALK:
                strategy = new RandomWalkSearch();
                break;
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + algo);
        }
        return strategy.search(graph, src, dst);
    }
}