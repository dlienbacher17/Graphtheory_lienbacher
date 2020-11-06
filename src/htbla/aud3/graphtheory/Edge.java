package htbla.aud3.graphtheory;

/*
 * @author Jan Fuehrer, David Lienbacher, Fabian Psutka
 */
public class Edge {
    
    private int fromNodeId;
    private int toNodeId;
    private double edgeWeight;
    
    public Edge (int fromNodeId, int toNodeId, double edgeWeight) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
        this.edgeWeight = edgeWeight;
    }
    
    public int getFromNodeId() {
        return fromNodeId;
    }
    
    public int getToNodeId() {
        return toNodeId;
    }
    
    public double getEdgeWeight() {
        return edgeWeight;
    }
    
}
