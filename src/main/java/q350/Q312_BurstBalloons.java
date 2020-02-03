package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/burst-balloons/
 *
 * Given n balloons, indexed from 0 to n-1. Each balloon is painted with a number on it represented by array nums.
 * You are asked to burst all the balloons. If the you burst balloon i you will get nums[left] * nums[i] *
 * nums[right] coins. Here left and right are adjacent indices of i. After the burst, the left and right then becomes
 * adjacent.
 *
 * Find the maximum coins you can collect by bursting the balloons wisely.
 *
 * Note:
 *
 * You may imagine nums[-1] = nums[n] = 1. They are not real therefore you can not burst them.
 * 0 ≤ n ≤ 500, 0 ≤ nums[i] ≤ 100
 *
 * Example:
 *
 * Input: [3,1,5,8]
 * Output: 167
 * Explanation: nums = [3,1,5,8] --> [3,5,8] -->   [3,8]   -->  [8]  --> []
 * coins =  3*1*5      +  3*5*8    +  1*3*8      + 1*8*1   = 167
 */
@RunWith(LeetCodeRunner.class)
public class Q312_BurstBalloons {

    /**
     * dfs 暴力算法时间复杂度是 O(n!) 所以不行, 按照网上提示可以采用dp 的方式来计算.
     * dp[i][j] 表示 [i, j] 数字全部被bust 所得的最大值, 状态转移方程:
     * dp[i][j] = dp[i][k] + dp[k][j] + nums[i-1] * nums[k] + nums[j+1] (k∈[i, j]) 所得的最大值
     * 边界条件 dp[i][i] = nums[i-1] * nums[i] * nums[i+1]
     * 这个算法的时间复杂度是 O(N^3)
     */
    @Answer
    public int maxCoins(int[] nums) {
        final int n = nums.length;
        if (n == 0) {
            return 0;
        }
        int[][] dp = new int[n][n];
        // 每步每步的逐渐扩大范围, 这个写成递归调用的方式可能更清晰.
        for (int step = 0; step < n; step++) {
            for (int start = 0, end = start + step; end < n; start++, end++) {
                for (int i = start; i <= end; i++) {
                    int prod = (start == 0 ? 1 : nums[start - 1]) * nums[i] * (end == n - 1 ? 1 : nums[end + 1]);
                    int sum = (i == 0 ? 0 : dp[start][i - 1]) + prod + (i == n - 1 ? 0 : dp[i + 1][end]);
                    dp[start][end] = Math.max(dp[start][end], sum);
                }
            }
        }
        return dp[0][n - 1];
    }

    // 在两边加上哨兵的解法, 这个要快一些
    @Answer
    public int maxCoins_withSentinel(int[] nums) {
        final int n = nums.length;
        {
            int[] t = new int[n + 2];
            System.arraycopy(nums, 0, t, 1, n);
            t[0] = t[n + 1] = 1;
            nums = t;
        }
        int[][] dp = new int[n + 2][n + 2];
        for (int step = 0; step < n; step++) {
            for (int start = 1, end = start + step; end <= n; start++, end++) {
                for (int i = start; i <= end; i++) {
                    int sum = dp[start][i - 1] + nums[start - 1] * nums[i] * nums[end + 1] + dp[i + 1][end];
                    if (dp[start][end] < sum) {
                        dp[start][end] = sum;
                    }
                }
            }
        }
        return dp[1][n];
    }

    // 哨兵+ 递归的解法
    @Answer
    public int maxConins_dfs(int[] nums) {
        final int n = nums.length;
        int[] t = new int[n + 2];
        System.arraycopy(nums, 0, t, 1, n);
        t[0] = t[n + 1] = 1;
        int[][] dp = new int[n + 2][n + 2];
        dfs(dp, t, 1, n);
        return dp[1][n];
    }

    private void dfs(int[][] dp, int[] nums, int start, int end) {
        for (int i = start; i <= end; i++) {
            if (i > start) {
                dfs(dp, nums, start, i - 1);
            }
            if (i < end) {
                dfs(dp, nums, i + 1, end);
            }
            int sum = dp[start][i - 1] + nums[start - 1] * nums[i] * nums[end + 1] + dp[i + 1][end];
            if (dp[start][end] < sum) {
                dp[start][end] = sum;
            }
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{3, 1, 5, 8}).expect(167);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{7, 9, 8, 0, 7, 1, 3, 5, 5, 2, 3}).expect(1654);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[]{8, 2, 6, 8, 9, 8, 1, 4, 1, 5, 3, 0, 7, 7, 0, 4, 2, 2, 5})
            .expect(3630);

    @TestData
    public DataExpectation border = DataExpectation.create(new int[0]).expect(0);

}
