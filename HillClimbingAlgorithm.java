import java.util.Random;

public class HillClimbingAlgorithm {

    // Define the objective function to be optimized
    public static double objectiveFunction(double x) {
        return -(x * x) + 4 * x - 4; // Example quadratic function
    }

    // Hill Climbing algorithm
    public static double hillClimbing(double start, double stepSize, int maxIterations) {
        double currentSolution = start;
        double currentValue = objectiveFunction(currentSolution);
        Random rand = new Random();

        for (int i = 0; i < maxIterations; i++) {
            double neighbor = currentSolution + (rand.nextDouble() * 2 - 1) * stepSize; // Generate a neighbor within
                                                                                        // stepSize range
            double neighborValue = objectiveFunction(neighbor);

            if (neighborValue > currentValue) {
                currentSolution = neighbor;
                currentValue = neighborValue;
            }
        }

        return currentSolution;
    }

    public static void main(String[] args) {
        double start = 0.0; // Starting point for the search
        double stepSize = 0.01; // Step size for generating neighbors
        int maxIterations = 1000; // Maximum number of iterations

        double solution = hillClimbing(start, stepSize, maxIterations);
        double value = objectiveFunction(solution);

        System.out.println("Optimal solution: " + solution);
        System.out.println("Optimal value: " + value);
    }
}
