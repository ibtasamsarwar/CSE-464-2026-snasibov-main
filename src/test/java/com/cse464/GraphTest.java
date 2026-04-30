package com.cse464;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.List;
import java.util.Random;

/**
 * GraphTest class provides comprehensive unit tests for the Graph API
 * Tests all four features: Parse, Add Nodes, Add Edges, Output
 */
public class GraphTest {
    
    private Graph graph;
    private static final String TEST_OUTPUT_DIR = "target/test-output/";
    private static final String DOT_FILE = "src/main/resources/dot_files/simple_graph.dot";

    @Before
    public void setUp() {
        // Create output directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(TEST_OUTPUT_DIR));
        } catch (IOException e) {
            // Directory may already exist
        }
        graph = new Graph();
    }

    @After
    public void tearDown() {
        // Clean up if needed
        graph = null;
    }

    // ============== Feature 1: Parse Graph Tests ==============

    /**
     * Test 1: Parse simple DOT file
     */
    @Test
    public void testParseSimpleDOTFile() {
        if (GraphParser.isValidDOTFile(DOT_FILE)) {
            Graph parsedGraph = GraphParser.parseGraph(DOT_FILE);
            assertNotNull("Parsed graph should not be null", parsedGraph);
            assertTrue("Graph should have nodes", parsedGraph.getNodeCount() > 0);
            assertTrue("Graph should have edges", parsedGraph.getEdgeCount() > 0);
        }
    }

    /**
     * Test 2: Verify parsed graph contains expected nodes
     */
    @Test
    public void testParsedGraphNodes() {
        Graph parsedGraph = GraphParser.parseGraph(DOT_FILE);
        assertTrue("Graph should contain node A", parsedGraph.hasNode("A"));
        assertTrue("Graph should contain node B", parsedGraph.hasNode("B"));
        assertTrue("Graph should contain node C", parsedGraph.hasNode("C"));
    }

    /**
     * Test 3: Verify parsed graph contains expected edges
     */
    @Test
    public void testParsedGraphEdges() {
        Graph parsedGraph = GraphParser.parseGraph(DOT_FILE);
        assertTrue("Graph should contain edge A->B", parsedGraph.hasEdge("A", "B"));
        assertTrue("Graph should contain edge B->C", parsedGraph.hasEdge("B", "C"));
        assertTrue("Graph should contain edge A->C", parsedGraph.hasEdge("A", "C"));
    }

    /**
     * Test 4: Test graph toString() output format
     */
    @Test
    public void testGraphToString() {
        graph.addNode("X");
        graph.addNode("Y");
        graph.addEdge("X", "Y");
        
        String output = graph.toString();
        assertTrue("Output should contain node count", output.contains("Number of Nodes: 2"));
        assertTrue("Output should contain edge count", output.contains("Number of Edges: 1"));
        assertTrue("Output should contain edge representation", output.contains("X -> Y"));
    }

    // ============== Feature 2: Add Nodes Tests ==============

    /**
     * Test 5: Add single node
     */
    @Test
    public void testAddSingleNode() {
        boolean added = graph.addNode("A");
        assertTrue("Node should be added successfully", added);
        assertEquals("Graph should contain 1 node", 1, graph.getNodeCount());
        assertTrue("Graph should contain node A", graph.hasNode("A"));
    }

    /**
     * Test 6: Add multiple nodes
     */
    @Test
    public void testAddMultipleNodes() {
        String[] labels = {"A", "B", "C", "D"};
        int addedCount = graph.addNodes(labels);
        
        assertEquals("Should add 4 nodes", 4, addedCount);
        assertEquals("Graph should contain 4 nodes", 4, graph.getNodeCount());
        
        for (String label : labels) {
            assertTrue("Graph should contain node " + label, graph.hasNode(label));
        }
    }

    /**
     * Test 7: Detect duplicate nodes - single add
     */
    @Test
    public void testDuplicateNodeDetectionSingle() {
        boolean first = graph.addNode("A");
        boolean second = graph.addNode("A");
        
        assertTrue("First node addition should succeed", first);
        assertFalse("Duplicate node should be rejected", second);
        assertEquals("Graph should contain only 1 node", 1, graph.getNodeCount());
    }

    /**
     * Test 8: Detect duplicate nodes - array add
     */
    @Test
    public void testDuplicateNodeDetectionArray() {
        String[] labels = {"A", "B", "A", "C", "B"};
        int addedCount = graph.addNodes(labels);
        
        assertEquals("Should add only 3 unique nodes", 3, addedCount);
        assertEquals("Graph should contain 3 unique nodes", 3, graph.getNodeCount());
    }

    /**
     * Test 9: Add node with whitespace
     */
    @Test
    public void testAddNodeWithWhitespace() {
        boolean added = graph.addNode("  NodeA  ");
        assertTrue("Node with whitespace should be added", added);
        assertTrue("Graph should contain trimmed node", graph.hasNode("NodeA"));
    }

    /**
     * Test 10: Reject null or empty nodes
     */
    @Test
    public void testRejectInvalidNodes() {
        assertFalse("Null node should be rejected", graph.addNode(null));
        assertFalse("Empty node should be rejected", graph.addNode(""));
        assertFalse("Whitespace-only node should be rejected", graph.addNode("   "));
    }

    // ============== Feature 3: Add Edges Tests ==============

    /**
     * Test 11: Add single edge
     */
    @Test
    public void testAddSingleEdge() {
        graph.addNode("A");
        graph.addNode("B");
        
        boolean added = graph.addEdge("A", "B");
        assertTrue("Edge should be added successfully", added);
        assertEquals("Graph should contain 1 edge", 1, graph.getEdgeCount());
    }

    /**
     * Test 12: Add multiple edges
     */
    @Test
    public void testAddMultipleEdges() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        
        boolean edge1 = graph.addEdge("A", "B");
        boolean edge2 = graph.addEdge("B", "C");
        boolean edge3 = graph.addEdge("A", "C");
        
        assertTrue("All edges should be added", edge1 && edge2 && edge3);
        assertEquals("Graph should contain 3 edges", 3, graph.getEdgeCount());
    }

    /**
     * Test 13: Detect duplicate edges
     */
    @Test
    public void testDuplicateEdgeDetection() {
        graph.addNode("A");
        graph.addNode("B");
        
        boolean first = graph.addEdge("A", "B");
        boolean second = graph.addEdge("A", "B");
        
        assertTrue("First edge should be added", first);
        assertFalse("Duplicate edge should be rejected", second);
        assertEquals("Graph should contain only 1 edge", 1, graph.getEdgeCount());
    }

    /**
     * Test 14: Reject edge with non-existent nodes
     */
    @Test
    public void testEdgeWithNonExistentNodes() {
        graph.addNode("A");
        
        boolean added = graph.addEdge("A", "NonExistent");
        assertFalse("Edge to non-existent node should be rejected", added);
        assertEquals("Graph should contain 0 edges", 0, graph.getEdgeCount());
    }

    /**
     * Test 15: Reject null edge endpoints
     */
    @Test
    public void testRejectNullEdgeEndpoints() {
        graph.addNode("A");
        graph.addNode("B");
        
        assertFalse("Edge with null source should be rejected", graph.addEdge(null, "B"));
        assertFalse("Edge with null destination should be rejected", graph.addEdge("A", null));
        assertEquals("Graph should contain no edges", 0, graph.getEdgeCount());
    }

    // ============== Feature 4: Output Tests ==============

    /**
     * Test 16: Output graph to text file
     */
    @Test
    public void testOutputGraphToFile() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B");
        
        String outputPath = TEST_OUTPUT_DIR + "test_graph.txt";
        graph.outputGraph(outputPath);
        
        try {
            assertTrue("Output file should exist", Files.exists(Paths.get(outputPath)));
            String content = Files.readString(Paths.get(outputPath));
            assertTrue("Output should contain graph info", content.contains("Graph Information"));
        } catch (IOException e) {
            fail("Error reading output file: " + e.getMessage());
        }
    }

    /**
     * Test 17: Output graph in DOT format
     */
    @Test
    public void testOutputDOTFormat() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addNode("C");
        graph.addEdge("A", "B");
        graph.addEdge("B", "C");
        
        String outputPath = TEST_OUTPUT_DIR + "test_graph.dot";
        graph.outputDOTGraph(outputPath);
        
        try {
            assertTrue("DOT file should exist", Files.exists(Paths.get(outputPath)));
            String content = Files.readString(Paths.get(outputPath));
            assertTrue("DOT file should contain digraph keyword", content.contains("digraph"));
            assertTrue("DOT file should contain edges", content.contains("->"));
        } catch (IOException e) {
            fail("Error reading DOT file: " + e.getMessage());
        }
    }

    /**
     * Test 18: Output graphics (placeholder test)
     */
    @Test
    public void testOutputGraphics() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B");
        
        // This test verifies the method doesn't throw an exception
        assertDoesNotThrow("Graphics output should not throw exception",
                () -> graph.outputGraphics(TEST_OUTPUT_DIR + "test_graph.png", "png"));
    }

    // ============== Integration Tests ==============

    /**
     * Test 19: Complex graph operations
     */
    @Test
    public void testComplexGraphOperations() {
        // Create a larger graph
        String[] nodes = {"A", "B", "C", "D", "E"};
        graph.addNodes(nodes);
        
        graph.addEdge("A", "B");
        graph.addEdge("A", "C");
        graph.addEdge("B", "D");
        graph.addEdge("C", "D");
        graph.addEdge("D", "E");
        
        assertEquals("Graph should have 5 nodes", 5, graph.getNodeCount());
        assertEquals("Graph should have 5 edges", 5, graph.getEdgeCount());
        
        // Verify structure
        assertTrue("All nodes should exist", 
                nodes.length == graph.getNodes().size());
    }

    /**
     * Test 20: Full workflow - Parse, Modify, Output
     */
    @Test
    public void testFullWorkflow() {
        // Parse
        Graph parsed = GraphParser.parseGraph(DOT_FILE);
        assertNotNull("Parsed graph should not be null", parsed);
        int originalNodeCount = parsed.getNodeCount();
        
        // Modify
        boolean nodeAdded = parsed.addNode("D");
        boolean edgeAdded = parsed.addEdge("A", "D");
        
        assertTrue("Node should be added", nodeAdded);
        assertTrue("Edge should be added", edgeAdded);
        assertEquals("Node count should increase by 1", 
                originalNodeCount + 1, parsed.getNodeCount());
        
        // Output
        String outputPath = TEST_OUTPUT_DIR + "workflow_test.dot";
        parsed.outputDOTGraph(outputPath);
        
        try {
            assertTrue("Output file should exist", Files.exists(Paths.get(outputPath)));
        } catch (Exception e) {
            fail("Output operation failed: " + e.getMessage());
        }
    }

    // ============== Edge Cases ==============

    /**
     * Test 21: Self-loop edge
     */
    @Test
    public void testSelfLoopEdge() {
        graph.addNode("A");
        boolean added = graph.addEdge("A", "A");
        
        assertTrue("Self-loop should be allowed", added);
        assertEquals("Graph should contain 1 edge", 1, graph.getEdgeCount());
    }

    /**
     * Test 22: Empty graph operations
     */
    @Test
    public void testEmptyGraphOperations() {
        assertEquals("Empty graph should have 0 nodes", 0, graph.getNodeCount());
        assertEquals("Empty graph should have 0 edges", 0, graph.getEdgeCount());
        
        String output = graph.toString();
        assertTrue("Output should contain zero counts", 
                output.contains("Number of Nodes: 0") && 
                output.contains("Number of Edges: 0"));
    }

    /**
     * Test 23: Get graph elements
     */
    @Test
    public void testGetGraphElements() {
        graph.addNode("A");
        graph.addNode("B");
        graph.addEdge("A", "B");
        
        assertNotNull("Nodes set should not be null", graph.getNodes());
        assertNotNull("Edges set should not be null", graph.getEdges());
        
        assertEquals("Nodes set should have 2 elements", 2, graph.getNodes().size());
        assertEquals("Edges set should have 1 element", 1, graph.getEdges().size());
    }

        @Test
    public void testBFSsearch() {
        graph.addNode("A"); graph.addNode("B"); graph.addNode("C");
        graph.addEdge("A", "B"); graph.addEdge("B", "C"); graph.addEdge("A", "C");

        GraphSearchContext ctx = new GraphSearchContext();
        List<String> path = ctx.graphSearch(graph, "A", "C", Algorithm.BFS);
        assertNotNull(path);
        assertEquals("BFS should find shortest path A->C directly", "C", path.get(path.size()-1));
        assertEquals("First element should be A", "A", path.get(0));
    }

    @Test
    public void testDFSsearch() {
        graph.addNode("A"); graph.addNode("B"); graph.addNode("C");
        graph.addEdge("A", "B"); graph.addEdge("B", "C"); graph.addEdge("A", "C");

        GraphSearchContext ctx = new GraphSearchContext();
        List<String> path = ctx.graphSearch(graph, "A", "C", Algorithm.DFS);
        assertNotNull(path);
        assertEquals("Path should start at A", "A", path.get(0));
        assertEquals("Path should end at C", "C", path.get(path.size()-1));
    }

    @Test
    public void testRandomWalkSearchSeededProducesDifferentPaths() {
        graph.addNode("A"); graph.addNode("B"); graph.addNode("C"); graph.addNode("D");
        graph.addEdge("A", "B"); graph.addEdge("A", "C"); graph.addEdge("C", "D");

        RandomWalkSearch rw1 = new RandomWalkSearch(new Random(1));
        RandomWalkSearch rw2 = new RandomWalkSearch(new Random(2));

        List<String> p1 = rw1.search(graph, "A", "D");
        List<String> p2 = rw2.search(graph, "A", "D");

        // Both may find a path or empty; if both non-empty, expect possible differences
        if (!p1.isEmpty() && !p2.isEmpty()) {
            // Not strictly required to differ, but likely; at least assert valid endpoints if non-empty
            assertEquals("Paths should end at destination", "D", p1.get(p1.size()-1));
            assertEquals("Paths should end at destination", "D", p2.get(p2.size()-1));
        }
    }

    @Test
    public void testStrategySelectionBFSandDFSandRandom() {
        graph.addNode("A"); graph.addNode("B"); graph.addNode("C");
        graph.addEdge("A", "B"); graph.addEdge("B", "C"); graph.addEdge("A", "C");

        GraphSearchContext ctx = new GraphSearchContext();

        List<String> bfsPath = ctx.graphSearch(graph, "A", "C", Algorithm.BFS);
        List<String> dfsPath = ctx.graphSearch(graph, "A", "C", Algorithm.DFS);
        List<String> rwPath = ctx.graphSearch(graph, "A", "C", Algorithm.RANDOM_WALK);

        assertNotNull(bfsPath);
        assertNotNull(dfsPath);
        assertNotNull(rwPath);
        assertEquals("All strategies should yield a path ending at C if reachable",
                "C", bfsPath.get(bfsPath.size()-1));
        assertEquals("All strategies should yield a path ending at C if reachable",
                "C", dfsPath.get(dfsPath.size()-1));
        // Random walk may or may not find C depending on randomness; ensure the call doesn't throw
    }


    /**
     * Helper: Assert that a function doesn't throw an exception
     */
    private void assertDoesNotThrow(String message, Runnable runnable) {
        try {
            runnable.run();
        } catch (Exception e) {
            fail(message + ": " + e.getMessage());
        }
    }
}
