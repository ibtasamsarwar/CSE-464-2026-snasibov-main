package com.cse464;

import java.util.Deque;
import java.util.List;

public class BFSSearch extends GraphSearchTemplate implements SearchStrategy {
    @Override
    protected void addPath(Deque<List<String>> frontier, List<String> path) {
        frontier.addLast(path);
    }

    @Override
    protected List<String> getNextPath(Deque<List<String>> frontier) {
        return frontier.removeFirst();
    }

    @Override
    public List<String> search(Graph graph, String src, String dst) {
        return super.search(graph, src, dst);
    }
}