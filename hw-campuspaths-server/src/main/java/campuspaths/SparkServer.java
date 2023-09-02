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

package campuspaths;

import campuspaths.utils.CORSFilter;
import com.google.gson.Gson;
import pathfinder.CampusMap;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;

//import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
//import java.util.LinkedList;

public class SparkServer {

    public static void main(String[] args) {
        CORSFilter corsFilter = new CORSFilter();
        corsFilter.apply();
        // The above two lines help set up some settings that allow the
        // React application to make requests to the Spark server, even though it
        // comes from a different server.
        // You should leave these two lines at the very beginning of main().

        // TODO: Create all the Spark Java routes you need here.

        // Handles request for a route between two buildings, returns the coordinates of the path as list where
        // list[i,i+1, i+2, i+3] represent a single segment (x1, y1, x2, y2)
        Spark.get("/your-route", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {
                String startRoute  = request.queryParams("start");
                String endRoute = request.queryParams("end");
                if (startRoute == null || endRoute == null) {
                    Spark.halt(400, "must have start and end");
                }
                // A campusMap that stores the information of the university of washington
                //                // including paths and building names and finds the shortest path between two building
                CampusMap map = new CampusMap();
                Gson gson = new Gson();
                Path<Point> path = map.findShortestPath(startRoute, endRoute);
                if (path == null) {
                    return gson.toJson(new LinkedList<>());
                }
                List<Double> list = new LinkedList<>();
                for (Path<Point>.Segment seg: path) {
                    list.add(seg.getStart().getX());
                    list.add(seg.getStart().getY());
                    list.add(seg.getEnd().getX());
                    list.add(seg.getEnd().getY());
                }

                return gson.toJson(list);
            }
        });

        // handles request for the names of the buildings of the University of Washington
        Spark.get("/get-building", new Route() {
            @Override
            public Object handle(Request request, Response response) throws Exception {

                // A campusMap that stores the information of the university of washington
                // including paths and building names and finds the shortest path between two building
                CampusMap map = new CampusMap();

                List<String> list = new LinkedList<>();
                for (String key: map.buildingNames().keySet()) {
                    list.add(key);
                    list.add(map.buildingNames().get(key));
                }
                Gson gson = new Gson();
                return gson.toJson(list);
            }
        });

    }

}
