/*
 * Copyright (C) 2022 Kevin Zatloukal and James Wilcox.  All rights reserved.  Permission is
 * hereby granted to students registered for University of Washington
 * CSE 331 for use solely during Autumn Quarter 2022 for purposes of
 * the course.  No other use, copying, distribution, or modification
 * is permitted without prior written consent. Copyrights for
 * third-party components of this work must be honored.  Instructors
 * interested in reusing these course materials should contact the
 * author.
 */

package pathfinder.scriptTestRunner;

import graph.Graph;
import pathfinder.Dijkstra;
import pathfinder.datastructures.Path;

import java.io.*;
import java.util.*;

/**
 * This class implements a test driver that uses a script file format
 * to test an implementation of Dijkstra's algorithm on a graph.
 */
public class PathfinderTestDriver {

    // Leave this constructor public
    private final Map<String, Graph<String, Double>> graphs = new HashMap<>();
    private final PrintWriter output;
    private final BufferedReader input;
    public PathfinderTestDriver(Reader r, Writer w) {
        // TODO: Implement this, reading commands from `r` and writing output to `w`.
        // See GraphTestDriver as an example.
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    // Leave this method public
    public void runTests() throws IOException  {
        // TODO: Implement this.
        String inputLine;
        while((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                    (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            } else {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            switch(command) {
                case "CreateGraph":
                    createGraph(arguments);
                    break;
                case "AddNode":
                    addNode(arguments);
                    break;
                case "AddEdge":
                    addEdge(arguments);
                    break;
                case "ListNodes":
                    listNodes(arguments);
                    break;
                case "ListChildren":
                    listChildren(arguments);
                    break;
                case "FindPath":
                    findPath(arguments);
                    break;
                default:
                    output.println("Unrecognized command: " + command);
                    break;
            }
        } catch(Exception e) {
            String formattedCommand = command;
            formattedCommand += arguments.stream().reduce("", (a, b) -> a + " " + b);
            output.println("Exception while running command: " + formattedCommand);
            e.printStackTrace(output);
        }
    }

    private void createGraph(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // TODO Insert your code here.
        Graph<String, Double> graph = new Graph<>();
        graphs.put(graphName, graph);
        // graphs.put(graphName, ___);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to AddNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // TODO Insert your code here.

        Graph<String, Double> graph = graphs.get(graphName);
        graph.addVertex(nodeName);
        // ___ = graphs.get(graphName);
        output.println("added node " + nodeName + " to " + graphName);
    }

    private void addEdge(List<String> arguments) {
        if(arguments.size() != 4) {
            throw new CommandException("Bad arguments to AddEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
                         String edgeLabel) {
        // TODO Insert your code here.
        Graph<String, Double> graph = graphs.get(graphName);
        graph.addEdge(parentName, childName, Double.valueOf(edgeLabel));
        double nu = Double.parseDouble(edgeLabel);
        String num = String.format("%.3f", nu);
        // ___ = graphs.get(graphName);
        output.println("added edge " + num + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if(arguments.size() != 1) {
            throw new CommandException("Bad arguments to ListNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // TODO Insert your code here.
        Graph<String, Double> graph = graphs.get(graphName);
        Set<String> nodes = new TreeSet<>(graph.getVertices());
        output.print(graphName + " contains:");
        for (String ver: nodes) {
            output.print(" " + ver);
        }
        output.println();
        // ___ = graphs.get(graphName);
        // output.println(...);
    }

    private void listChildren(List<String> arguments) {
        if(arguments.size() != 2) {
            throw new CommandException("Bad arguments to ListChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // TODO Insert your code here.
        Graph<String, Double> graph = graphs.get(graphName);
        Map<String, Set<Double>> values = new TreeMap<>();
        for (Graph.Node<String,Double> curr: graph.getChildren(parentName)) {
            if (!values.containsKey(curr.getNodeLabel())) {
                Set<Double> set = new TreeSet<>();
                values.put(curr.getNodeLabel(), set);
            }
            values.get(curr.getNodeLabel()).add(curr.getWeightLabel());
        }

        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (String key: values.keySet()) {
            for (Double s: values.get(key)) {
                //double nu = Double.parseDouble(edgeLabel);
                String num = String.format("%.3f", s);
                output.print(" " + key+"("+num+")");
            }
        }
        output.println();
    }

    private void findPath(List<String> arguments) {
        if(arguments.size() != 3) {
            throw new CommandException("Bad arguments to FindPath: " + arguments);
        }

        String graphName = arguments.get(0);
        String node_a = arguments.get(1);
        String node_b = arguments.get(2);
        findPath(graphName, node_a, node_b);
    }

    private void findPath(String graphName, String node_a, String node_b) {
        // TODO Insert your code here.
        Graph<String, Double> graph = graphs.get(graphName);
        if (!graph.containsVertex(node_a) && !graph.containsVertex(node_b)) {
            output.println("unknown: " + node_a);
            output.println("unknown: " + node_b);
        } else if (!graph.containsVertex(node_a)) {
            output.println("unknown: " + node_a);
        } else if (!graph.containsVertex(node_b)) {
            output.println("unknown: " + node_b);
        } else if (node_a.equals(node_b)) {
            output.println("path from " + node_a + " to " + node_b +":");
            output.println("total cost: 0.000");
        } else {
            Dijkstra shp = new Dijkstra();
            Path<String> path = shp.shortestPath(graph, node_a, node_b);
            if (path == null) {
                output.println("path from " + node_a + " to " + node_b+":");
                output.println("no path found");
            } else {
                output.println("path from " + node_a + " to " + node_b + ":");
                for (Path<String>.Segment curr : path) {
                    //double nu = Double.parseDouble(edgeLabel);
                    String num = String.format("%.3f", curr.getCost());
                    output.println(curr.getStart() + " to " + curr.getEnd() + " with weight " + num);
                }
                output.println("total cost: " + String.format("%.3f", path.getCost()));
            }
        }
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }

        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
