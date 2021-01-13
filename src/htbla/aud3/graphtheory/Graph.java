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
            for(int fromId = 0; line != null; fromId++) {
                int[] splits = Arrays.stream(line.split(";")).mapToInt(Integer::parseInt).toArray();
                for (int toId = 0; toId < splits.length; toId++) {
                    edges.add(new Edge(fromId + 1, toId + 1, splits[toId]));
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
        return dspRec(new ArrayList(), sourceNodeId, targetNodeId, viaNodeIds, true);
    }

    public Path determineLongestPath(int sourceNodeId, int targetNodeId) {
        return determineLongestPath(sourceNodeId, targetNodeId, new int[0]);
    }

    public Path determineLongestPath(int sourceNodeId, int targetNodeId, int... viaNodeIds) {
        return dspRec(new ArrayList(), sourceNodeId, targetNodeId, viaNodeIds, false);
    }
    
    public double determineMaximumFlow(int sourceNodeId, int targetNodeId) {
        List<Edge> tempEdges = new ArrayList<>(edges);

        int u;
        double flow = 0;
        List<Integer> parent = new LinkedList<>();
        while(bfs(sourceNodeId, targetNodeId, tempEdges, parent))
        {
            double pathFlow = Double.MAX_VALUE;
            for (int v = targetNodeId; v != sourceNodeId; v = parent.get(v))
            {
                u = parent.get(v);
                /*List<Integer> fuckingjavawhydoicodethisbullshitomgstopitwhyistherenoenumerablerangeinjavareeeeee = new LinkedList();
                for(int i = 0; i < tempEdges.size() - 1; i++)
                    fuckingjavawhydoicodethisbullshitomgstopitwhyistherenoenumerablerangeinjavareeeeee.add(i);*/
                pathFlow = Math.min(pathFlow, getEdgeFromTo(u, v, tempEdges).getEdgeWeight());
            }

            for (int v = targetNodeId; v != sourceNodeId; v = parent.get(v))
            {
                u = parent.get(v);
                Edge edge = getEdgeFromTo(u, v, tempEdges);
                edge.setEdgeWeight(edge.getEdgeWeight() - pathFlow);
                Edge reverseEdge = getEdgeFromTo(v, u, tempEdges);
                reverseEdge.setEdgeWeight(reverseEdge.getEdgeWeight() + pathFlow);
                tempEdges.set(tempEdges.indexOf(edge), edge);
                tempEdges.set(tempEdges.indexOf(reverseEdge), reverseEdge);
            }

            flow += pathFlow;
        }

        return flow;
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
            for (int v = 1; v < edges.size()+1; v++)
            {
                Edge edge = getEdgeFromTo(source, v, edges);

                if (!alreadyVisited.contains(v) && edge.getEdgeWeight() > 0.0)
                {
                    alreadyVisited.add(v);
                    queue.add(v);
                }
            }
        }

        return alreadyVisited.contains(dest);
    }

    private Path dspRec(List<Edge> path, int currentNodeId, int targetNode, int[] requiredNodes, boolean shortest) {
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
            
            Path result = dspRec(path.stream().collect(Collectors.toList()), i.getToNodeId(), targetNode, requiredNodes, shortest);

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
        return edges.stream().filter(p -> p.getToNodeId() == from && p.getFromNodeId() == to).findFirst().get();
    }
}
