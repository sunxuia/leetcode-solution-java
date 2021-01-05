package q1500;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1478. Allocate Mailboxes
 * https://leetcode.com/problems/allocate-mailboxes/
 *
 * Given the array houses and an integer k. where houses[i] is the location of the ith house along a street, your task
 * is to allocate k mailboxes in the street.
 *
 * Return the minimum total distance between each house and its nearest mailbox.
 *
 * The answer is guaranteed to fit in a 32-bit signed integer.
 *
 * Example 1:
 * <img src="./Q1478_PIC1.png">
 * Input: houses = [1,4,8,10,20], k = 3
 * Output: 5
 * Explanation: Allocate mailboxes in position 3, 9 and 20.
 * Minimum total distance from each houses to nearest mailboxes is |3-1| + |4-3| + |9-8| + |10-9| + |20-20| = 5
 *
 * Example 2:
 * <img src="./Q1478_PIC2.png">
 * Input: houses = [2,3,5,12,18], k = 2
 * Output: 9
 * Explanation: Allocate mailboxes in position 3 and 14.
 * Minimum total distance from each houses to nearest mailboxes is |2-3| + |3-3| + |5-3| + |12-14| + |18-14| = 9.
 *
 * Example 3:
 *
 * Input: houses = [7,4,6,1], k = 1
 * Output: 8
 *
 * Example 4:
 *
 * Input: houses = [3,6,14,10], k = 4
 * Output: 0
 *
 * Constraints:
 *
 * n == houses.length
 * 1 <= n <= 100
 * 1 <= houses[i] <= 10^4
 * 1 <= k <= n
 * Array houses contain unique integers.
 */
@RunWith(LeetCodeRunner.class)
public class Q1478_AllocateMailboxes {

    /**
     * 带缓存的dfs(分治), 这个比较慢, 时间复杂度 O(N^2)
     */
    @Answer
    public int minDistance(int[] houses, int k) {
        final int n = houses.length;
        Arrays.sort(houses);
        int[][][] cache = new int[n][n][k + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(cache[i][j], -1);
            }
        }
        return dfs(cache, houses, 0, n - 1, k);
    }

    private int dfs(int[][][] cache, int[] houses, int start, int end, int k) {
        if (end - start + 1 <= k) {
            return 0;
        }
        if (cache[start][end][k] != -1) {
            return cache[start][end][k];
        }
        if (k == 1) {
            // 只有1 个点的情况, 则中位点距离所有点的距离最短
            int res = medianDistance(houses, start, end);
            cache[start][end][k] = res;
            return res;
        }
        int res = Integer.MAX_VALUE;
        // 通过遍历数组中的点, 平分k, 得到最小的结果.
        for (int m = start; m < end; m++) {
            int left = dfs(cache, houses, start, m, k / 2);
            int right = dfs(cache, houses, m + 1, end, (k + 1) / 2);
            res = Math.min(res, left + right);
        }
        cache[start][end][k] = res;
        return res;
    }

    /**
     * 求 houses[start:end] 中分配1 个邮箱的结果.
     */
    private int medianDistance(int[] houses, int start, int end) {
        int median = (houses[(start + end) / 2] + houses[(start + end + 1) / 2]) / 2;
        int res = 0;
        for (int i = start; i <= end; i++) {
            res += Math.abs(houses[i] - median);
        }
        return res;
    }

    /**
     * 上面解法的dp 改写, 和上面一样的慢.
     */
    @Answer
    public int minDistance2(int[] houses, int k) {
        final int n = houses.length;
        Arrays.sort(houses);
        // dp[t][s][e] 表示 houses[s:e] 分配了t 个邮箱的结果.
        int[][][] dp = new int[k + 1][n][n];
        // 分配1个邮箱的结果.
        for (int s = 0; s < n; s++) {
            for (int e = s; e < n; e++) {
                dp[1][s][e] = medianDistance(houses, s, e);
            }
        }
        // 分配多个邮箱的结果.
        for (int t = 2; t <= k; t++) {
            for (int s = 0; s < n; s++) {
                for (int e = s + t - 1; e < n; e++) {
                    dp[t][s][e] = Integer.MAX_VALUE;
                    for (int i = s; i < e; i++) {
                        int dist = dp[t / 2][s][i] + dp[(t + 1) / 2][i + 1][e];
                        dp[t][s][e] = Math.min(dp[t][s][e], dist);
                    }
                }
            }
        }
        return dp[k][0][n - 1];
    }

    /**
     * 另一种dp 解法, 优化了上面的算法, 将原来的对半分变为前面t-1, 后面1.
     */
    @Answer
    public int minDistance3(int[] houses, int k) {
        final int n = houses.length;
        Arrays.sort(houses);

        // medians[s][e] 表示 houses[s:e] 分配1 个邮箱的结果.
//        int[][] medians = new int[n][n];
//        for (int i = 0; i < n; i++) {
//            for (int j = i; j < n; j++) {
//                medians[i][j] = medianDistance(houses, i, j);
//            }
//        }
        // (LeetCode 上更快的求解方式)
        int[][] medians = new int[n][n];
        for (int s = 0; s < n; s++) {
            for (int e = s + 1; e < n; e++) {
                medians[s][e] = medians[s][e - 1] + houses[e] - houses[(s + e) / 2];
            }
        }

        // dp[t-1][i] 表示 houses[0:i] 分配了t 个邮箱的结果.
        int[][] dp = new int[k][n];
        // 分配1 个的情况
        System.arraycopy(medians[0], 0, dp[0], 0, n);
        // 分配多个的情况
        for (int t = 1; t < k; t++) {
            for (int i = t + 1; i < n; i++) {
                dp[t][i] = Integer.MAX_VALUE;
                for (int j = t - 1; j < i; j++) {
                    // houses[0:j] 占t 个, houses[j+1:i] 占1 个
                    int dist = dp[t - 1][j] + medians[j + 1][i];
                    dp[t][i] = Math.min(dp[t][i], dist);
                }
            }
        }
        return dp[k - 1][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 4, 8, 10, 20}, 3).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{2, 3, 5, 12, 18}, 2).expect(9);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{7, 4, 6, 1}, 1).expect(8);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{3, 6, 14, 10}, 4).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[]{15, 8, 9, 6}, 1).expect(10);

    @TestData
    public DataExpectation overTime1 = DataExpectation
            .createWith(new int[]{48, 23, 44, 42, 2, 7, 25, 18, 32, 20, 36, 31, 30, 26, 10, 33, 22, 9, 1, 35}, 15)
            .expect(5);

    @TestData
    public DataExpectation overTime2 = DataExpectation.createWith(new int[]{
            4231, 1078, 4976, 1013, 4029, 2037, 9359, 5013, 2001, 4858, 5546, 6513, 9866, 1447, 5561, 2174, 6623, 5867,
            8755, 875, 1845, 5757, 1697, 7874, 3367, 4389, 3331, 6820, 1655, 3615, 9994, 4025, 7782, 2875, 3212, 9179,
            8418, 7086, 3613, 5538, 1292, 991, 4145, 2623, 8541, 3627, 7288, 3914, 1707, 7998, 9634, 85, 4033, 2673,
            2860, 9893, 4562, 8043, 4550, 4921, 9306, 9323, 6612, 7814, 5253, 1642, 3607, 8392, 3116, 9299, 6232, 2026,
            7769, 9059, 9018, 5434, 3060, 5609, 717, 222, 9304, 9124, 4545, 2368, 8602, 9745, 3531, 5614, 2563, 9362,
            5419, 9394, 2927, 5683, 877, 9874, 4905}, 82)
            .expect(86);

}
