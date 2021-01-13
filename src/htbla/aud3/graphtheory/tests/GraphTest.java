package htbla.aud3.graphtheory.tests;

import java.io.File;
import java.util.List;
import htbla.aud3.graphtheory.*;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    private final String linz_suchproblem_path = ".\\src\\htbla\\aud3\\graphtheory\\infiles\\Linz_Suchproblem.csv";

    public GraphTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    /**
     * Test of determineShortestPath method, of class Graph.
     */

    @Test
    public void testRead() {
        System.out.println("read()");

        Graph graph = new Graph();
        graph.read(new File(linz_suchproblem_path));

        int fromNode = 1;
        int toNode = 2;

        List<Edge> returnedEdges = graph.getEdges();
        Edge returnedEdge = returnedEdges.stream()
                .filter(edge -> edge.getFromNodeId() == fromNode && edge.getToNodeId() == toNode)
                .findFirst()
                .get();

        assertEquals(500, returnedEdge.getEdgeWeight(), 0.0000000001);
    }

    @Test
    public void testDetermineShortestPath_int_int() {
        System.out.println("determineShortestPath()");

        Graph graph = new Graph();
        graph.read(new File(linz_suchproblem_path));

        int fromNodeId = 27;
        int toNodeId = 30;

        int expectedDistance = 160;
        int returnedDistance = (int) graph.determineShortestPath(fromNodeId, toNodeId).computeDistance();

        assertEquals(expectedDistance, returnedDistance, 0.0000000001);
    }

    /**
     * Test of determineShortestPath method, of class Graph.
     */

    @Test
    public void testDetermineShortestPath_3args() {
        System.out.println("determineShortestPath");
        int sourceNodeId = 0;
        int targetNodeId = 0;
        int[] viaNodeIds = null;
        Graph instance = new Graph();
        Path expResult = null;
        Path result = instance.determineShortestPath(sourceNodeId, targetNodeId, viaNodeIds);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}