public class MinimumCostMatchingOutfits {
    public static int minCost(int[][] price) {
        int n = price.length;

        // Base cases for the first person
        int minCost = price[0][0];
        int secondMinCost = price[0][1];
        int thirdMinCost = price[0][2];

        // Iterate over each person starting from the second one
        for (int i = 1; i < n; i++) {
            int newMinCost = Math.min(secondMinCost, thirdMinCost) + price[i][0];
            int newSecondMinCost = Math.min(minCost, thirdMinCost) + price[i][1];
            int newThirdMinCost = Math.min(minCost, secondMinCost) + price[i][2];

            // Update the minimum costs for the next iteration
            minCost = newMinCost;
            secondMinCost = newSecondMinCost;
            thirdMinCost = newThirdMinCost;
        }

        // Return the minimum cost among the three options for the last person
        return Math.min(minCost, Math.min(secondMinCost, thirdMinCost));
    }

    public static void main(String[] args) {
        int[][] price = { { 14, 4, 11 }, { 11, 14, 3 }, { 14, 2, 10 } };
        int result = minCost(price);
        System.out.println(result); // Output: 9
    }
}
