package htbla.aud3.graphtheory;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

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
        return determineShortestPath(sourceNodeId, targetNodeId, new int[0]);
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return dspRec(new ArrayList<Edge>(), sourceNodeId, targetNodeId, viaNodeIds);
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        return -1.0;
    }
    
    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        return null;
    }


    /* --- private recursive methods --- */
    private Path dspRec(List<Edge> path, int currentNodeId, int targetNode, int[] requiredNodes) {
        List<Edge> neighbors = determinePossiblePaths(currentNodeId).stream().filter(p ->!path.contains(p)).collect(Collectors.toList());

        if(neighbors.size() == 0)
            return null;
        Path currentBest = null;
        double currentBestDist = Double.MAX_VALUE;
        for (Edge i : neighbors) {
            path.add(i);

            if(i.getToNodeId() == targetNode) {
                for (int x : requiredNodes) {
                    if(!Arrays.asList(requiredNodes).contains(x))
                        return null;
                }
                return new Path(path);
            }

            Path result = dspRec(path, i.getToNodeId(), targetNode, requiredNodes);

            if (result == null)
                continue;

            double currentDist = result.computeDistance();
            if (currentBest == null) {
                currentBest = result;
                currentBestDist = currentDist;
            }
            else {
                if (currentDist < currentBestDist) {
                    currentBest = result;
                    currentBestDist = currentDist;
                }
            }
            path.remove(i);
        }
        return currentBest;
    }

    private List<Edge> determinePossiblePaths(int nodeId) {
        return edges.stream().filter(p -> p.getFromNodeId() == nodeId).collect(Collectors.toList());
    }
}
