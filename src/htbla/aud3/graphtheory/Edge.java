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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Edge other = (Edge) obj;
        
        if (this.fromNodeId != other.fromNodeId) {
            return false;
        }
        
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + this.fromNodeId;
        hash = 59 * hash + this.toNodeId;
        hash = 59 * hash + (int) (Double.doubleToLongBits(this.edgeWeight) ^ (Double.doubleToLongBits(this.edgeWeight) >>> 32));
        return hash;
    }
    
    
}
