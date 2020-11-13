package htbla.aud3.graphtheory;

import java.util.List;

/**
 * @author Jan Fuehrer, David Lienbacher, Fabian Psutka
 */
public class Path {

    private List<Edge> edges;

    
    public int[] getNodeIds() {
        return null;
    }
    
    public double computeDistance() {
        double distance = 0.0;
        for (Edge e : edges) {
            distance = distance + e.getEdgeWeight();
        }
        return distance;
    }
    
}




















