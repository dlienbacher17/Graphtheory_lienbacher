package htbla.aud3.graphtheory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Jan Fuehrer, David Lienbacher, Fabian Psutka
 */
public class Path {

    private List<Edge> edges;
    
    public Path(List<Edge> edges) {
        this.edges = edges;
    }
    
    public int[] getNodeIds() {
        int[] ids = edges.stream().mapToInt(e -> e.getFromNodeId()).toArray();
        int lastIndex = edges.size() - 1;
        ids[lastIndex] = edges.get(lastIndex).getToNodeId();
        return ids;
    }
    
    public double computeDistance() {
        double distance = 0.0;
        for (Edge e : edges) {
            distance = distance + e.getEdgeWeight();
        }
        return distance;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}




















