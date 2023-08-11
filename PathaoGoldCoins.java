import java.util.Arrays;

public class PathaoGoldCoins {
    public static int minCoins(int[] ratings) {
        int n = ratings.length;
        int[] coins = new int[n];

        // Initialize each rider with 1 coin
        Arrays.fill(coins, 1);

        // Traverse the ratings array from left to right
        for (int i = 1; i < n; i++) {
            // If the current rider's rating is higher than the previous rider's rating,
            // assign one more coin than the previous rider to the current rider
            if (ratings[i] > ratings[i - 1]) {
                coins[i] = coins[i - 1] + 1;
            }
        }

        // Traverse the ratings array from right to left
        for (int i = n - 2; i >= 0; i--) {
            // If the current rider's rating is higher than the next rider's rating and
            // the current rider has fewer coins than the next rider, assign one more coin
            // than the next rider to the current rider
            if (ratings[i] > ratings[i + 1] && coins[i] <= coins[i + 1]) {
                coins[i] = coins[i + 1] + 1;
            }
        }

        int totalCoins = 0;
        for (int coin : coins) {
            totalCoins += coin;
        }

        return totalCoins;
    }

    public static void main(String[] args) {
        int[] ratings = { 1, 0, 2 };
        int result = minCoins(ratings);
        System.out.println("Minimum number of coins required: " + result);
    }
}
