import java.util.*;

class Edge {
    int source, destination, weight;

    public Edge(int source, int destination, int weight) {
        this.source = source;
        this.destination = destination;
        this.weight = weight;
    }
}

public class BellmanFordWithMaxHeap {

    public static int[] bellmanFord(int[][] graph, int source) {
        int vertices = graph.length;
        int[] distance = new int[vertices];
        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[source] = 0;

        PriorityQueue<Edge> maxHeap = new PriorityQueue<>(Collections.reverseOrder());
        maxHeap.add(new Edge(source, source, 0));

        while (!maxHeap.isEmpty()) {
            Edge edge = maxHeap.poll();
            int u = edge.source;

            for (int v = 0; v < vertices; v++) {
                int weight = graph[u][v];
                if (weight != 0 && distance[u] != Integer.MAX_VALUE && distance[u] + weight < distance[v]) {
                    distance[v] = distance[u] + weight;
                    maxHeap.add(new Edge(u, v, distance[v]));
                }
            }
        }

        return distance;
    }

    public static void main(String[] args) {
        int[][] graph = {
                { 0, 6, 0, 0, 0, 0 },
                { 0, 0, 5, -2, 0, 0 },
                { 0, 0, 0, 0, -1, 0 },
                { 0, 0, 0, 0, 1, 0 },
                { 0, 0, 0, 0, 0, 3 },
                { 0, 0, 0, 0, 0, 0 }
        };
        int source = 0;

        int[] distance = bellmanFord(graph, source);

        System.out.println("Vertex\tDistance from Source");
        for (int i = 0; i < distance.length; i++) {
            System.out.println(i + "\t\t" + distance[i]);
        }
    }
}
