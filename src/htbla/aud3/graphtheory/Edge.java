package htbla.aud3.graphtheory;

/*
 * @author TODO Bitte Gruppenmitglieder eintragen!
 */
public class Edge {
    
    private int fromNodeId;
    private int toNodeId;
    
    public Edge (int fromNodeId, int toNodeId) {
        this.fromNodeId = fromNodeId;
        this.toNodeId = toNodeId;
    }
    
    public int getFromNodeId() {
        return fromNodeId;
    }
    
    public int getToNodeId() {
        return toNodeId;
    }
    
}
