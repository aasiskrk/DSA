public class LongestDecreasingSubsequence {
    public static int longestSubsequence(int[] nums, int k) {
        int n = nums.length;
        int[] dp = new int[n];
        int maxLen = 1; // Minimum length of 1, as each element can form its own subsequence

        for (int i = 0; i < n; i++) {
            dp[i] = 1; // Initialize dp[i] with minimum length of 1
            for (int j = 0; j < i; j++) {
                if (nums[i] < nums[j] && Math.abs(nums[i] - nums[j]) <= k) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }

        return maxLen;
    }

    public static void main(String[] args) {
        int[] nums = { 8, 5, 4, 2, 1, 4, 3, 4, 3, 1, 15 };
        int k = 3;
        int result = longestSubsequence(nums, k);
        System.out.println("Length of the longest subsequence: " + result);
    }
}
