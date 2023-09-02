package pathfinder;


import graph.Graph;
import pathfinder.datastructures.Path;
import pathfinder.datastructures.Point;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

public class Dijkstra {

    public Dijkstra() {

    }


    /**
     *
     * @param g The graph storing the paths
     * @param srt the source path
     * @param dst the destination path
     * @return return the shortest path from source to destination vertex, null else
     * @param <V> the Vertex of the graph
     * @param <E> the edge of the graph
     */
    public <V, E extends Double> Path<V> shortestPath(Graph<V, E> g, V srt, V dst) {
        Comparator<Path<V>> comp = new Comparator<Path<V>>() {
            @Override
            public int compare(Path<V> o1, Path<V> o2) {
                double n = o1.getCost()-o2.getCost();
                if (n <= 0) {
                    return -1;
                }
                return 1;
            }

        };
        PriorityQueue<Path<V>> pq = new PriorityQueue<>(g.sizeE(),comp);
        V sn = srt;
        V en = dst;
        Set<V> seen = new HashSet<>();
        Path<V> p = new Path<V>(srt);
        pq.add(p);
        while (!pq.isEmpty()) {
            Path<V> mp = pq.poll();
            V endNode = mp.getEnd();
            if (endNode.equals(dst)) {
                return mp;
            }

            if (seen.contains(endNode))
                continue;

            for (Graph.Node<V, E> curr: g.getChildren(endNode)) {
                if (!seen.contains(curr.getNodeLabel())) {
                    Path<V> ext = mp.extend(curr.getNodeLabel(), curr.getWeightLabel());
                    pq.add(ext);
                }
            }
            seen.add(endNode);
        }
        return null;
    }

}
