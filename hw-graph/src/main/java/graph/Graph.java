package graph;

import jdk.jshell.spi.ExecutionControl;

import java.util.*;

/**
 * Represents a mutable directed labeled graph containing a collections of
 * nodes (vertices) and edges. Edges connect two nodes and are one-way in a directed graph.
 * e = (A, B) indicates that B is directly reachable from Aa. Edges are labeled and multiple
 * edges could have the same label. Edges between 2 nodes can't have the same level
 * edges ((A-B) and (A-B)) can't have the same label.
 * This is a multi-graph, so you can have any number of edges (0, 1, more) between
 * a pair of nodes (vertices).
 */
public class Graph<V, E> {


    /**
     * Represents an immutable Single Node that stores a Strings of nodeLabel and weightLabel.
     * This Node is used to store a child of a vertex.
     */
    public static class Node<Ve, Ed>  {

        // RI: nodeLabel != null and wightLabel != null
        // AF: A Node with nodeLabel node name and wightLabel edge/weight name
        private Ve nodeLabel;
        private Ed weightLabel;

        /**
         * Creates a single Node that stores nodeLabel and weightLabel
         *
         * @param nodeLabel the name of nodeBel
         * @param weightLabel the name of weightLabel
         * @spec.requires nodeLabel != null and weightLabel != null
         */
        public Node(Ve nodeLabel, Ed weightLabel) {
            this.nodeLabel = nodeLabel;
            this.weightLabel = weightLabel;
        }

        /**
         *  returns a String of the nodeLabel label
         * @return returns a String of the nodeLabel label
         */
        public Ve getNodeLabel() {
            return this.nodeLabel;
        }

        /**
         *  returns a String of the weightLabel label
         * @return returns a String of the wightLabel label
         */
        public Ed getWeightLabel() {
            return this.weightLabel;
        }

        /**
         * returns true if obj is instance of Node, and they store the same Strings of
         * nodeLabel and weightLabel
         */
        @Override
        public boolean equals(Object obj) {
            if (! (obj instanceof Node<?, ?>))
                return false;

            Node<?, ?> o = (Node<?, ?>) obj;
            return this.nodeLabel.equals(o.nodeLabel) && this.weightLabel.equals(o.weightLabel);
        }

        @Override
        public int hashCode() {
            int prime = 37;
            int ans = 1;
            ans = prime * ans + this.nodeLabel.hashCode();
            ans = prime * ans + this.weightLabel.hashCode();
            return ans;
        }
    }

    // RI: adjacencyList can't be null and adjacencyList.keySet() should contain ony vertexes
    //     in this graph and no two Nodes in the List<Node> should contain the same
    //     wightLabel and nodeLabel for each key(vertex), no duplicate edges.

    // AF(this): if adjacencyList is empty then this is an empty graph
    //           else (this) a graph with set of vertices (the keys of adjacency list) and
    //           set of weighted edges from parent vertices to List of Children Node (the values
    //           of adjacency list).
    //

    private static final boolean DEBUG = false;
    private Map<V, LinkedList<Node<V, E>>> adjacencyList;
    private int numEdges;

    /**
     * @spec.modifies (this) graph
     * @spec.effects Constructs an empty graph
     */
    public Graph()  {
        adjacencyList = new HashMap<>();
        this.numEdges = 0;
        //checkRep();
    }

    /**
     * A method that checks the representation of this Graph is met.
     * checks if the adjacency list is null
     * checks there are duplicate edges, no two nodes have the same (have the same src, dest and weight label)
     */
    private void checkRep() {
        // check this is not null
        assert this.adjacencyList != null : "adjacencyList was null";

        ///Vertexes(keys) are not duplicate since map doesn't allow duplicate key

        // check there are no duplicate edges in the map
        for (V key: adjacencyList.keySet()) {
            Set<Node<V, E>> set = new HashSet<>();
            for (Node<V, E> curr: this.adjacencyList.get(key)) {
                assert !set.contains(curr) : "duplicate edges";
                set.add(curr);
            }
        }

    }
    /**
     *  Adds a vertex to this graph
     *
     * @param nodeLabel Name\Label of the Vertex/Node
     * @spec.requires nodeLabel != null
     * @spec.modifies (this) graph
     * @spec.effects a vertex/node with the given label is added to this graph,
     *               if it's not already in this graph
     * @return returns true if this graph didn't already contain the node, false otherwise
     */
    public boolean addVertex(V nodeLabel)  {
        //checkRep();
        // check if the vertex already exists
        if (containsVertex(nodeLabel)) {
            return false;
        }
        adjacencyList.put(nodeLabel, new LinkedList<Node<V, E>>());
        //checkRep();
        return true;

    }

    /**
     * Adds an edge to this graph
     *
     * @param srcLabel Name\Label of the source Vertex/Node
     * @param dstLabel Name\Label of the destination Vertex/Node
     * @param edgeLabel Name\label of the edge between the source and destination vertex/node
     * @spec.requires srcLabel != null and dstLabel != null and edgeLabel != null
     * @spec.modifies (this) graph
     * @spec.effects  a directed edge from the source vertex to the destination
     *                vertex is added to the graph with the given edgeLabel label,
     *                if it's not already in the graph
     * @return returns true if this graph didn't already contain the edge, false otherwise
     */
    public boolean addEdge(V srcLabel, V dstLabel, E edgeLabel) {
        //checkRep();
        // check if the edge already exists
        if (containsEdge(srcLabel, dstLabel, edgeLabel)) {
            return false;
        }
        Node<V, E> n = new Node<V,E>(dstLabel, edgeLabel);
        adjacencyList.get(srcLabel).add(n);
        this.numEdges++;
        //checkRep();
        return true;
    }

    /**
     * Removes a vertex from this graph
     *
     * @param nodeLabel Name\Label of the Vertex/Node
     * @spec.requires srcNode != null
     * @spec.modifies (this) graph
     * @spec.effects  removes the given vertex from the graph, if already contained in this graph
     * @return returns true if this graph contained the vertex/node
     */
    public boolean removeVertex(V nodeLabel) {
        //checkRep();
        if (!containsVertex(nodeLabel)) {
            return false;
        }
        adjacencyList.remove(nodeLabel);
        for (V key: adjacencyList.keySet()) {
            LinkedList<Node<V, E>> nodes = adjacencyList.get(key);
            Iterator<Node<V, E>> itr = nodes.iterator();
            while (itr.hasNext()) {
                Node<V, E> curr = itr.next();
                if (curr.nodeLabel.equals(nodeLabel)) {
                    itr.remove();
                }
            }
        }
        //checkRep();
        return true;
    }

    /**
     *  Removes an edge from this graph
     *
     * @param srcLabel Name\Label of the source Vertex/Node
     * @param dstLabel Name\Label of the destination Vertex/Node
     * @param edgeLabel Name\label of the edge between the source and destination vertex/node
     * @spec.requires srcLabel != null and dstLabel != null and edgeLabel != null
     * @spec.modifies (this) graph
     * @spec.effects  removes the directed edge from the source vertex to the destination
     *                vertex with the given edgeLabel, if it's already contained in this graph
     * @return returns true if this graph contained the edge
     */
    public boolean removeEdge(V srcLabel, V dstLabel, E edgeLabel) {
        //checkRep();
        // check if the edge exists or not
        if (!containsEdge(srcLabel, dstLabel, edgeLabel)) {
            return false;
        }
        LinkedList<Node<V, E>> nodes = adjacencyList.get(srcLabel);
        Iterator<Node<V, E>> itr = nodes.iterator();
        while (itr.hasNext()) {
            Node<V, E> curr = itr.next();
            if (curr.nodeLabel.equals(dstLabel) && curr.weightLabel.equals(edgeLabel)) {
                itr.remove();
                break;
            }
        }
        this.numEdges--;
        //checkRep();
        return true;
    }

    /**
     *  Returns true if this graph contains a vertex with the given label, false else
     *
     * @param nodeLabel Name\Label of the Vertex/Node
     * @spec.requires srcNode != null
     * @return returns true if the graph contains the given vertex, false otherwise
     */
    public boolean containsVertex(V nodeLabel) {
        //checkRep();
        // key of the adjacency list are the vertex
        return adjacencyList.containsKey(nodeLabel);

    }

    /**
     *  Returns true if the graph contains an edge with given param, false else
     *
     * @param srcLabel Name\Label of the source Vertex/Node
     * @param dstLabel Name\Label of the destination Vertex/Node
     * @param edgeLabel Name\label of the edge between the source and destination vertex/node
     * @spec.requires srcLabel != null and dstLabel != null and edgeLabel != null and
     * @return returns true if a directed edge from the source vertex to the destination
     *         vertex with the given edgeLabel label is in the graph, false otherwise
     */
    public boolean containsEdge(V srcLabel, V dstLabel, E edgeLabel) {
        //checkRep();
        if (!containsVertex(srcLabel) || !containsVertex(dstLabel)) {
            return false;
        }
        LinkedList<Node<V, E>> nodes = adjacencyList.get(srcLabel);
        Iterator<Node<V, E>> itr = nodes.iterator();
        while (itr.hasNext()) {
            Node<V, E> curr = itr.next();
            if (curr.nodeLabel.equals(dstLabel) && curr.weightLabel.equals(edgeLabel)) {
                return true;
            }
        }
        return false;
    }

    /**
     *  Returns list of nodes of the children of the given vertex
     *
     * @param nodeLabel Name\Label of the Vertex/Node
     * @spec.requires srcNode != null
     * @return returns a list of the children of the given vertex, empty if
     *         the vertex is not contained in this graph
     */

    public List<Node<V, E>> getChildren(V nodeLabel) {
        //checkRep();
        if (!containsVertex(nodeLabel)) {
            return new LinkedList<>();
        }
        return new LinkedList<Node<V, E>>(adjacencyList.get(nodeLabel));
    }

    /**
     * returns the vertices of this graph
     *
     * @return returns the vertices of this graph
     */
    public Set<V> getVertices() {
        //checkRep();
        return new HashSet<>(adjacencyList.keySet());
    }

    /**
     *  The graphs are considered to be equal in the case of below,
     *  The given object o should be a graph, must have the same number of edges
     *  and vertex as this vertex. All the vertexes and edges of this graph should be in
     *  other graph and All the vertexes and edges of other graph should be in this with exactly
     *  the same data for vertices and edges
     */
    @Override
    public boolean equals(Object o) {
        //checkRep();
        if (! (o instanceof Graph<?, ?>))
            return false;

        Graph<?, ?> other = (Graph<?, ?>) o;

        return this.adjacencyList.equals(((Graph<?, ?>) o).adjacencyList);
//        Set<V> one = this.getVertices();
//        Set<?> two = other.getVertices();
//
//        if (!one.equals(two))
//            return false;
//
//        for (V ver: one) {
//            if (!this.getChildren(ver).equals(other.getChildren(ver)))
//                return false;
//        }
       // return true;
    }

    @Override
    public int hashCode() {
        //checkRep();
        int prime  = 37;
        int ans = 1;
        Set<V> one = this.getVertices();
        for (V ver: one) {
            ans = prime * ans + ver.hashCode();
            for (Node<V, E> curr: adjacencyList.get(ver)) {
                ans = prime * ans + curr.hashCode();
            }
        }
        return ans;
    }
    /**
     * returns the number of vertexes in this graph
     *
     * @return returns the number vertexes in this graph
     */
    public int sizeV() {
        //checkRep();
        return adjacencyList.keySet().size();
    }

    /**
     * returns the number of edges in this graph
     *
     * @return returns the number of edges in this graph
     *
     */
    public int sizeE() {
        //checkRep();
        return this.numEdges;
    }

}
