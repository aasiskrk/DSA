public class MaxPointsByShootingTargets {
    public static int maxPoints(int[] a) {
        int n = a.length;
        int[] paddedA = new int[n + 2]; // Padded array with 1's at the beginning and end
        paddedA[0] = 1;
        paddedA[n + 1] = 1;
        for (int i = 0; i < n; i++) {
            paddedA[i + 1] = a[i];
        }

        int[][] dp = new int[n + 2][n + 2]; // dp[i][j] represents max points from hitting targets in [i, j]

        for (int len = 1; len <= n; len++) {
            for (int i = 1; i + len - 1 <= n; i++) {
                int j = i + len - 1;
                for (int k = i; k <= j; k++) {
                    dp[i][j] = Math.max(dp[i][j],
                            dp[i][k - 1] + dp[k + 1][j] + paddedA[i - 1] * paddedA[k] * paddedA[j + 1]);
                }
            }
        }

        return dp[1][n];
    }

    public static void main(String[] args) {
        int[] a = { 3, 1, 5, 8 };
        int result = maxPoints(a);
        System.out.println("Maximum points: " + result);
    }
}
