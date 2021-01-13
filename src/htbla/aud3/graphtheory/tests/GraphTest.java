package htbla.aud3.graphtheory.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import htbla.aud3.graphtheory.*;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class GraphTest {

    private final String linz_suchproblem_path = ".\\src\\htbla\\aud3\\graphtheory\\infiles\\Linz_Suchproblem.csv";
    private final String linz_flussproblem_path = ".\\src\\htbla\\aud3\\graphtheory\\infiles\\Linz_Flussproblem.csv";

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

    @Test
    public void testDetermineShortestPath2_int_int() {
        System.out.println("determineShortestPath()");

        Graph graph = new Graph();
        graph.read(new File(linz_suchproblem_path));

        int fromNodeId = 56;
        int toNodeId = 2;

        int expectedDistance = 470;
        int returnedDistance = (int) graph.determineShortestPath(fromNodeId, toNodeId).computeDistance();

        assertEquals(expectedDistance, returnedDistance, 0.0000000001);
    }

    @Test
    public void testDetermineShortestPath3_int_int() {
        System.out.println("determineShortestPath()");

        Graph graph = new Graph();
        graph.read(new File(linz_suchproblem_path));

        int fromNodeId = 12;
        int toNodeId = 55;

        Path expectedDistance = null;
        Path returnedDistance = graph.determineShortestPath(fromNodeId, toNodeId);

        assertEquals(expectedDistance, returnedDistance);
    }

    @Test
    public void testDetermineShortestPathInvalid_int_int() {
        System.out.println("determineShortestPath()");

        Graph graph = new Graph();
        graph.read(new File(linz_suchproblem_path));

        int fromNodeId = -1;
        int toNodeId = 10;

        Path expectedDistance = null;
        Path returnedDistance = graph.determineShortestPath(fromNodeId, toNodeId);

        assertEquals(expectedDistance, returnedDistance);
    }

    @Test
    public void testDetermineMaximumFlow_int_int() {
        System.out.println("determineMaximumFlow()");

        Graph graph = new Graph();
        graph.read(new File(linz_flussproblem_path));

        int fromNodeId = 1;
        int toNodeId = 2;

        int expectedDistance = 2250;
        int returnedDistance = (int) graph.determineMaximumFlow(fromNodeId, toNodeId);

        assertEquals(expectedDistance, returnedDistance, 0.0000000001);
    }

    @Test
    public void testDetermineMaximumFlow2_int_int() {
        System.out.println("determineMaximumFlow()");

        Graph graph = new Graph();
        graph.read(new File(linz_flussproblem_path));

        int fromNodeId = 4;
        int toNodeId = 1;

        int expectedDistance = 1750;
        int returnedDistance = (int) graph.determineMaximumFlow(fromNodeId, toNodeId);

        assertEquals(expectedDistance, returnedDistance, 0.0000000001);
    }

    @Test
    public void testDetermineMaximumFlowInvalid_int_int() {
        System.out.println("determineMaximumFlow()");

        Graph graph = new Graph();
        graph.read(new File(linz_flussproblem_path));

        int fromNodeId = -1;
        int toNodeId = 1;

        int expectedDistance = 0;
        int returnedDistance = (int) graph.determineMaximumFlow(fromNodeId, toNodeId);

        assertEquals(expectedDistance, returnedDistance, 0.0000000001);
    }

    @Test
    public void testDetermineBottleneck_int_int() {
        System.out.println("determineBottlenecks()");

        Graph graph = new Graph();
        graph.read(new File(linz_flussproblem_path));

        int fromNodeId = 4;
        int toNodeId = 1;

        List<Edge> expectedBottlenecks = Arrays.asList(
                new Edge(4, 3, 0),
                new Edge(3, 2, 0),
                new Edge(5, 34, 0),
                new Edge(34, 33, 0),
                new Edge(33, 32, 0),
                new Edge(32, 31, 0),
                new Edge(31, 2, 0),
                new Edge(31, 30, 0),
                new Edge(30, 29, 0),
                new Edge(29, 1, 0),
                new Edge(36, 57, 0));

        List<Edge> returnedBottlenecks = graph.determineBottlenecks(fromNodeId, toNodeId);

        assertTrue(expectedBottlenecks.size() == returnedBottlenecks.size() && expectedBottlenecks.containsAll(returnedBottlenecks) && returnedBottlenecks.containsAll(expectedBottlenecks));
    }

    @Test
    public void testDetermineBottleneckInvalid_int_int() {
        System.out.println("determineBottlenecks()");

        Graph graph = new Graph();
        graph.read(new File(linz_flussproblem_path));

        int fromNodeId = -1;
        int toNodeId = 1;

        List<Edge> expectedBottlenecks = new ArrayList<>();

        List<Edge> returnedBottlenecks = graph.determineBottlenecks(fromNodeId, toNodeId);

        assertTrue(expectedBottlenecks.size() == returnedBottlenecks.size() && expectedBottlenecks.containsAll(returnedBottlenecks) && returnedBottlenecks.containsAll(expectedBottlenecks));
    }

    /**
     * Test of determineShortestPath method, of class Graph.
     */

    @Test
    public void testDetermineShortestPath_3args() {
        System.out.println("determineShortestPath()");

        Graph graph = new Graph();
        graph.read(new File(linz_suchproblem_path));

        int sourceNodeId = 27;
        int targetNodeId = 29;
        int[] viaNodeIds = new int[] { 30 };

        int expectedDistance = 450;
        int returnedDistance = (int) graph.determineShortestPath(sourceNodeId, targetNodeId, viaNodeIds).computeDistance();

        assertEquals(expectedDistance, returnedDistance, 0.0000000001);
    }

    @Test
    public void testGetNodeIds()
    {
        System.out.println("testGetNodeIds()");

        int[] expectedNodeIds = new int[] { 0, 1, 2 };
        int[] returnedNodeIds = new Path(Arrays.asList(new Edge(0, 1, 0), new Edge(1, 2, 0))).getNodeIds();

        assertArrayEquals(expectedNodeIds, returnedNodeIds);
    }

    @Test
    public void testGetFirstNodeId()
    {
        System.out.println("testGetFirstNodeId()");

        Edge edge = new Edge(2, 3, 150);

        int expectedNodeId = 2;
        int returnedNodeId = edge.getFirstNodeId();

        assertEquals(expectedNodeId, returnedNodeId);
    }

    @Test
    public void testGetSecondNodeId()
    {
        System.out.println("testGetSecondNodeId()");

        Edge edge = new Edge(2, 3, 150);

        int expectedNodeId = 3;
        int returnedNodeId = edge.getSecondNodeId();

        assertEquals(expectedNodeId, returnedNodeId);
    }
}