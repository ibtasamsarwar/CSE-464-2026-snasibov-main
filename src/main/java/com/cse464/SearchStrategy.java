package com.cse464;

import java.util.List;

public interface SearchStrategy {
    List<String> search(Graph graph, String src, String dst);
}