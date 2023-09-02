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

package pathfinder;

import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import pathfinder.parser.CampusBuilding;
import pathfinder.parser.CampusPath;
import pathfinder.parser.CampusPathsParser;

import java.util.*;

public class CampusMap implements ModelAPI {

    private Graph<Point, Double> graph;
    private List<CampusPath> campusPath;
    private List<CampusBuilding> campusBuil;
    private Map<String, String> shortN;
    private Map<String, Point> shortToP;

    private Dijkstra d;

    public CampusMap() {
        shortToP = new HashMap<>();
        d = new Dijkstra();
        graph = new Graph<Point, Double>();
        campusPath = CampusPathsParser.parseCampusPaths("campus_paths.csv");
        campusBuil = CampusPathsParser.parseCampusBuildings("campus_buildings.csv");
        for (CampusPath curr: campusPath) {
            Point one = new Point(curr.getX1(), curr.getY1());
            Point two = new Point(curr.getX2(), curr.getY2());
            graph.addVertex(one);
            graph.addVertex(two);
            graph.addEdge(one, two, curr.getDistance());
        }
        for (CampusBuilding curr: campusBuil) {
            Point one = new Point(curr.getX(), curr.getY());
            graph.addVertex(one);
            shortToP.put(curr.getShortName(), one);
        }
        shortN = new HashMap<>();
        for (CampusBuilding curr: campusBuil) {
            shortN.put(curr.getShortName(), curr.getLongName());
        }
    }
    @Override
    public boolean shortNameExists(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        //throw new RuntimeException("Not Implemented Yet");
        return shortN.keySet().contains(shortName);
    }

    @Override
    public String longNameForShort(String shortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        //throw new RuntimeException("Not Implemented Yet");
        if (!shortNameExists(shortName)) {
            throw new IllegalArgumentException();
        }
        return shortN.get(shortName);
    }

    @Override
    public Map<String, String> buildingNames() {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        //throw new RuntimeException("Not Implemented Yet");
        return new HashMap<>(shortN);
    }

    @Override
    public Path<Point> findShortestPath(String startShortName, String endShortName) {
        // TODO: Implement this method exactly as it is specified in ModelAPI
        //throw new RuntimeException("Not Implemented Yet");
        if (startShortName == null || endShortName == null || !shortNameExists(startShortName) || !shortNameExists(endShortName)){
            throw new IllegalArgumentException();
        }
        Path<Point> ans = d.shortestPath(this.graph, shortToP.get(startShortName), shortToP.get(endShortName));
        return ans;
    }

}
