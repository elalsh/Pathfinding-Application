package graph.junitTests;
import graph.Graph;
import org.junit.*;
import org.junit.Rule;
import org.junit.rules.Timeout;



import static org.junit.Assert.*;

/**
 * Tets for the Graph class
 */
public class GraphADTTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(10); // 10 seconds max per method tested

    /**
     * Empty Graph
     */
    private static Graph<String, String> g = new Graph<String, String>();

    /**
     * Graphs with 1 nodes
     */
    private static Graph<String, String> node = new Graph<String, String>();

    /**
     * Graphs with 2 island nodes/ nor edges in between
     */
    private static Graph<String, String> node2 = new Graph<String, String>();

    /**
     * graph with a node with single child/ A-> B
     * graph with 2 nodes (A and B) and one-way directed edge from A -> B
     */
    private static Graph<String, String> nodeWithChild = new Graph<String, String>();

    /**
     * graph with a node with 2 children/ A-> B and A-> C
     * graph with 3 nodes (A, B and c) and one-way directed edge from A -> B
     * one-way directed edge from A -> C
     */
    private static Graph<String, String> nodeWith2child = new Graph<String, String>();


    /**
     * graph with 2 nodes (A and B) and 2 one-way directed edge from A -> B
     * with different labels
     */
    private static Graph<String, String> twoNodesMultipleEdges = new Graph<String, String>();

    /**
     * graph with 2 nodes (A and B) and 2  directed edge from A -> B and B -> A
     */
    private static Graph<String, String> twoNodesBothWayEdges = new Graph<String, String>();


    /**
     * a method that initializes the graph's defined above
     */
    public void initGraph() {
        //
        g = new Graph<String, String>();
        //
        node = new Graph<String, String>();
        node.addVertex("A");
        //
        node2 = new Graph<String, String>();
        node2.addVertex("A");
        node2.addVertex("B");
        //
        nodeWithChild = new Graph<String, String>();
        nodeWithChild.addVertex("A");
        nodeWithChild.addVertex("B");
        nodeWithChild.addEdge("A", "B", "EdgeAB");
        //
        nodeWith2child = new Graph<String, String>();
        nodeWith2child.addVertex("A");
        nodeWith2child.addVertex("B");
        nodeWith2child.addVertex("C");
        nodeWith2child.addEdge("A", "B", "EdgeAB");
        nodeWith2child.addEdge("A", "C", "EdgeAC");
        //
        twoNodesMultipleEdges = new Graph<String, String>();
        twoNodesMultipleEdges.addVertex("A");
        twoNodesMultipleEdges.addVertex("B");
        twoNodesMultipleEdges.addEdge("A", "B", "EdgeAB");
        twoNodesMultipleEdges.addEdge("A", "B", "EdgeAB2");
        //
        twoNodesBothWayEdges = new Graph<String, String>();
        twoNodesBothWayEdges.addVertex("A");
        twoNodesBothWayEdges.addVertex("B");
        twoNodesBothWayEdges.addEdge("A", "B", "EdgeAB");
        twoNodesBothWayEdges.addEdge("B", "A", "EdgeBA");
    }

    /**
     * Test Equals
     */

    /**
     * Test Equals for empty graph
     */
    @Test
    public void equalsEmptyGraph() {
        initGraph();
        assertTrue(g.equals(g));
        node.removeVertex("A");
        assertTrue(g.equals(node));
    }

    /**
     * Test Equals for graphs with 1 and more nodes
     */
    @Test
    public void equalsComplexGraph() {
        initGraph();
        assertTrue(node.equals(node));
        g.addVertex("A");
        assertTrue(g.equals(node));
        assertTrue(node2.equals(node2));
    }

    /**
     * Test Equals for graphs with 1 and more nodes and edges
     */
    @Test
    public void equalsComplexGraphEdges() {
        initGraph();
        assertTrue(nodeWithChild.equals(nodeWithChild));
        node.addVertex("B");
        node.addEdge("A", "B", "EdgeAB");
        assertTrue(nodeWithChild.equals(node));

    }


    /**
     * Test remove vertex
     */

    /**
     * Remove vertex for simple graph
     */
    @Test
    public void removeNodeSimple() {
        initGraph();
        assertFalse(g.removeVertex("A"));
        assertTrue(node.removeVertex("A"));
        assertTrue(node2.removeVertex("B"));
    }

    /**
     * Remove vertex for Complex graph
     */
    @Test
    public void removeNodeComplex() {
        initGraph();
        assertTrue(nodeWithChild.removeVertex("A"));
        assertFalse(nodeWithChild.removeVertex("C"));
        assertTrue(nodeWith2child.removeVertex("C"));
    }

    /**
     * Test remove edge
     */

    /**
     * Remove vertex for simple graph
     */
    @Test
    public void removeEdgeSimple() {
        initGraph();
        assertFalse(g.removeEdge("A", "B", "EdgeAB"));
        assertFalse(node.removeEdge("A", "B", "EdgeAB"));
        assertFalse(node2.removeEdge("B", "A", "EdgeAB"));
    }

    /**
     * Remove vertex for Complex graph
     */
    @Test
    public void removeEdgeComplex() {
        initGraph();
        assertTrue(nodeWithChild.removeEdge("A", "B", "EdgeAB"));
        assertFalse(nodeWithChild.removeEdge("A", "C", "EdgeAC"));
        assertTrue(nodeWith2child.removeEdge("A", "C", "EdgeAC"));
        assertTrue(nodeWith2child.removeEdge("A", "B", "EdgeAB"));
    }

    /**
     * Tests the size for vertex and edges
     */

    @Test
    public void testSizeVertex() {
        initGraph();
        assertEquals(0, g.sizeV());
        assertEquals(1, node.sizeV());
        assertEquals(2, node2.sizeV());
    }

    @Test
    public void testSizeEdge() {
        initGraph();
        assertEquals(0, g.sizeE());
        assertEquals(0, node.sizeE());
        assertEquals(0, node2.sizeE());
        assertEquals(1, nodeWithChild.sizeE());
        assertEquals(2, nodeWith2child.sizeE());
    }

    /**
     * Tests hashcode method
     */

    @Test
    public void testHashCode() {
        initGraph();
        assertEquals(g.hashCode(), g.hashCode());
        assertEquals(node.hashCode(), node.hashCode());
        g.addVertex("A");
        g.equals(node);
        assertTrue(g.equals(node));
        assertEquals(g.hashCode(), node.hashCode());
    }

}
