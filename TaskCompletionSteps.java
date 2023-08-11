import java.util.*;

public class TaskCompletionSteps {
    public int minSteps(int N, int[][] r) {
        List<List<Integer>> graph = new ArrayList<>(N + 1);
        int[] inDegree = new int[N + 1];

        for (int i = 0; i <= N; i++) {
            graph.add(new ArrayList<>());
        }

        for (int[] prerequisite : r) {
            int x = prerequisite[0];
            int y = prerequisite[1];
            graph.get(x).add(y);
            inDegree[y]++;
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int i = 1; i <= N; i++) {
            if (inDegree[i] == 0) {
                queue.add(i);
            }
        }

        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int task = queue.poll();
                for (int nextTask : graph.get(task)) {
                    if (--inDegree[nextTask] == 0) {
                        queue.add(nextTask);
                    }
                }
            }
            steps++;
        }

        return steps == N ? steps : -1;
    }

    public static void main(String[] args) {
        int N = 3;
        int[][] r = { { 1, 3 }, { 2, 3 } };

        TaskCompletionSteps solver = new TaskCompletionSteps();
        int result = solver.minSteps(N, r);
        System.out.println("Minimum steps: " + result);
    }
}
