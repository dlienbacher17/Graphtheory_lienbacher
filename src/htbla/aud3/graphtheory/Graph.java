package htbla.aud3.graphtheory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;

/**
 * @author Jan Fuehrer, David Lienbacher, Fabian Psutka
 */
public class Graph {

    private List<Edge> edges;

    public void read(File adjacencyMatrix) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
            edges = new ArrayList();
            String line = br.readLine();
            for(int fromId = 0; line != null; fromId++) {
                int[] splits = Arrays.stream(line.split(";")).mapToInt(Integer::parseInt).toArray();
                for (int toId = 0; toId < splits.length; toId++) {
                    if (splits[toId] == 0) {
                        continue;
                    }

                    edges.add(new Edge(fromId, toId, splits[toId]));
                }

                line = br.readLine();
            }
        } catch(FileNotFoundException ex) {
            System.err.println("File not found");
        } catch (IOException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId) {
        return null;
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return null;
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        return -1.0;
    }
    
    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        return null;
    }

}
