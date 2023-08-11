import java.util.*;

public class ReorientConnections {
    public static int minReorder(int n, int[][] connections) {
        List<List<int[]>> graph = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] connection : connections) {
            graph.get(connection[0]).add(new int[] { connection[1], 1 }); // Direction from ai to bi
            graph.get(connection[1]).add(new int[] { connection[0], 0 }); // Direction from bi to ai
        }

        boolean[] visited = new boolean[n];
        int[] changes = new int[1]; // Counter for changes needed

        dfs(graph, visited, changes, 0);

        return changes[0];
    }

    private static void dfs(List<List<int[]>> graph, boolean[] visited, int[] changes, int node) {
        visited[node] = true;

        for (int[] neighbor : graph.get(node)) {
            int nextNode = neighbor[0];
            int direction = neighbor[1];

            if (!visited[nextNode]) {
                changes[0] += direction; // Increase changes counter if the edge needs to be reversed
                dfs(graph, visited, changes, nextNode);
            }
        }
    }

    public static void main(String[] args) {
        int n = 6;
        int[][] connections = { { 0, 1 }, { 1, 3 }, { 2, 3 }, { 4, 0 }, { 4, 5 } };

        int result = minReorder(n, connections);
        System.out.println("Minimum number of edges to be changed: " + result);
    }
}
