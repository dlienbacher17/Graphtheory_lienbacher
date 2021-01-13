package htbla.aud3.graphtheory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.nio.file.Files;
import java.util.*;
import java.io.File;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Jan Fuehrer, David Lienbacher, Fabian Psutka
 */
public class Graph {

    private int numOfNodes;
    private double currentBestDist;
    private Path currentBest;
    private List<Edge> edges;
    
    public List<Edge> getEdges() {
        return edges;
    }

    public void read(File adjacencyMatrix) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(adjacencyMatrix));
            edges = new ArrayList();
            String line = br.readLine();
            for(numOfNodes = 0; line != null; numOfNodes++) {
                int[] splits = Arrays.stream(line.split(";")).mapToInt(Integer::parseInt).toArray();
                for (int toId = 0; toId < splits.length; toId++) {
                    edges.add(new Edge(numOfNodes + 1, toId + 1, splits[toId]));
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
        return determineShortestPath(sourceNodeId, targetNodeId, edges, new int[0]);
    }
    
    public Path determineShortestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return dspRec(new ArrayList(), sourceNodeId, targetNodeId, edges, viaNodeIds, true);
    }

    public Path determineShortestPath(int sourceNodeId, int targetNodeId, List<Edge> edges) {
        return determineShortestPath(sourceNodeId, targetNodeId, edges, new int[0]);
    }

    public Path determineShortestPath(int sourceNodeId, int targetNodeId, List<Edge> edges, int... viaNodeIds) {
        return dspRec(new ArrayList(), sourceNodeId, targetNodeId, edges, viaNodeIds, true);
    }

    public Path determineLongestPath(int sourceNodeId, int targetNodeId) {
        return determineLongestPath(sourceNodeId, targetNodeId, edges, new int[0]);
    }

    public Path determineLongestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return dspRec(new ArrayList(), sourceNodeId, targetNodeId, edges, viaNodeIds, false);
    }

    public Path determineLongestPath(int sourceNodeId, int targetNodeId, List<Edge> edges) {
        return determineLongestPath(sourceNodeId, targetNodeId, edges, new int[0]);
    }

    public Path determineLongestPath(int sourceNodeId, int targetNodeId, List<Edge> edges, int... viaNodeIds) {
        return dspRec(new ArrayList(), sourceNodeId, targetNodeId, edges, viaNodeIds, false);
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        List<Edge> tempEdges = new ArrayList<>(edges);
        double result = 0.0;

        while(true) {
            Path p = determineShortestPath(sourceNodeId, targetNodeId, tempEdges);
            if (p == null)
                return result;

            double maxFlow = p.getEdges().stream().mapToDouble(x -> x.getEdgeWeight()).min().getAsDouble();
            result += maxFlow;
            for (Edge edge : p.getEdges()) {
                Edge listElement = tempEdges.stream().filter(x -> x.equals(edge)).findFirst().get();
                listElement.setEdgeWeight(listElement.getEdgeWeight() - maxFlow);
            }
        }
    }
    
    public List<Edge> determineBottlenecks(int sourceNodeId, int targetNodeId) {
        return null;
    }


    /* --- private recursive methods --- */
    private boolean bfs(int source, int dest, List<Edge> edges, List<Integer> result)
    {
        List<Integer> alreadyVisited = new ArrayList();
        LinkedList<Integer> queue = new LinkedList<>();
        queue.add(source);
        alreadyVisited.add(source);

        while (queue.size() != 0)
        {
            source = queue.poll();
            result.add(source);
            for (int v = 1; v < numOfNodes+1; v++)
            {
                Edge edge = getEdgeFromTo(v, source, edges);

                if (!alreadyVisited.contains(v) && edge.getEdgeWeight() > 0.0)
                {
                    alreadyVisited.add(v);
                    queue.add(v);
                }
            }
        }

        return alreadyVisited.contains(dest);
    }

    private Path dspRec(List<Edge> path, int currentNodeId, int targetNode, List<Edge> edges, int[] requiredNodes, boolean shortest) {
        List<Edge> neighbors = determinePossiblePaths(currentNodeId)
            .stream()
            .filter(p -> !(path.contains(p) || path.stream().anyMatch(x -> x.getFromNodeId() == p.getToNodeId() && x.getToNodeId() == p.getFromNodeId())))
            .collect(Collectors.toList());

        if(neighbors.isEmpty())
            return null;

        for (Edge i : neighbors) {
            path.add(i);

            if(i.getToNodeId() == targetNode) {
                for (int requiredNode : requiredNodes)
                {
                    boolean contains = false;
                    for (Edge edge : path) {
                        if (edge.getToNodeId() == requiredNode)
                        {
                            contains = true;
                            break;
                        }
                    }

                    if (!contains)
                        return null;
                }
                return new Path(path);
            }
            
            Path result = dspRec(path.stream().collect(Collectors.toList()), i.getToNodeId(), targetNode, edges, requiredNodes, shortest);

            if (result == null) {
                path.remove(i);
                continue;
            }

            double currentDist = result.computeDistance();
            if (currentBest == null) {
                currentBest = result;
                currentBestDist = currentDist;
            }
            else {
                if (shortest) {
                    if (currentDist < currentBestDist) {
                        currentBest = result;
                        currentBestDist = currentDist;
                    }
                } else {
                    if (currentDist > currentBestDist) {
                        currentBest = result;
                        currentBestDist = currentDist;
                    }
                }
            }
            path.remove(i);
        }

        return currentBest;
    }

    private List<Edge> determinePossiblePaths(int nodeId) {
        return edges.stream().filter(p -> p.getFromNodeId() == nodeId).filter(p -> p.getEdgeWeight() > 0.0001).collect(Collectors.toList());
    }

    private Edge getEdgeFromTo(int from, int to, List<Edge> edges)
    {
        Edge result = edges.stream().filter(p -> p.getToNodeId() == from && p.getFromNodeId() == to).findFirst().orElse(null);
        return result;
    }
}
