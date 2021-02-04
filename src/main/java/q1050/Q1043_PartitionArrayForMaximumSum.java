package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1043. Partition Array for Maximum Sum
 * https://leetcode.com/problems/partition-array-for-maximum-sum/
 *
 * Given an integer array arr, you should partition the array into (contiguous) subarrays of length at most k. After
 * partitioning, each subarray has their values changed to become the maximum value of that subarray.
 *
 * Return the largest sum of the given array after partitioning.
 *
 * Example 1:
 *
 * Input: arr = [1,15,7,9,2,5,10], k = 3
 * Output: 84
 * Explanation: arr becomes [15,15,15,9,10,10,10]
 *
 * Example 2:
 *
 * Input: arr = [1,4,1,5,7,3,6,1,9,9,3], k = 4
 * Output: 83
 *
 * Example 3:
 *
 * Input: arr = [1], k = 1
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= arr.length <= 500
 * 0 <= arr[i] <= 10^9
 * 1 <= k <= arr.length
 *
 * 题解: 将数组arr 分隔为长度最多为 K 的几个连续子数组, 之后将子数组中的所有元素都赋值为该子数组的最大元素值, 求分隔后所有元素和的最大值.
 */
@RunWith(LeetCodeRunner.class)
public class Q1043_PartitionArrayForMaximumSum {

    /**
     * dp 解法, 这个时间复杂度是 O(n^2) 比较慢.
     */
    @Answer
    public int maxSumAfterPartitioning(int[] arr, int k) {
        final int n = arr.length;

        // max[s][e] 表示 arr[s, e] 区间内的最大元素值
        int[][] max = new int[n][n];
        for (int s = 0; s < n; s++) {
            max[s][s] = arr[s];
            for (int e = s + 1; e < n && e < s + k; e++) {
                max[s][e] = Math.max(max[s][e - 1], arr[e]);
            }
        }

        // dp[s][e] 表示 arr[s,e] 区间内的最大结果
        int[][] dp = new int[n][n];
        // 计算可以合并的区间的结果
        for (int r = 0; r < k; r++) {
            for (int s = 0, e = r; e < n; s++, e++) {
                dp[s][e] = max[s][e] * (e - s + 1);
            }
        }
        // 计算不可以合并的区间的结果
        for (int r = k; r < n; r++) {
            for (int s = 0, e = r; e < n; s++, e++) {
                for (int mid = s; mid < e; mid++) {
                    dp[s][e] = Math.max(dp[s][e], dp[s][mid] + dp[mid + 1][e]);
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * LeetCode 上比较快的结果, 使用了另一种更加简单的dp 算法.
     */
    @Answer
    public int maxSumAfterPartitioning2(int[] arr, int k) {
        final int n = arr.length;
        // dp[e] 表示 A[0, e) 区间内的最大结果
        int[] dp = new int[n + 1];
        for (int e = 1; e <= n; e++) {
            int max = 0;
            // 向前确定如果 arr[s, e-1] 合并可能的最小结果
            for (int s = e - 1; s >= 0 && s >= e - k; s--) {
                max = Math.max(max, arr[s]);
                dp[e] = Math.max(dp[e], dp[s] + max * (e - s));
            }
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 15, 7, 9, 2, 5, 10}, 3)
            .expect(84);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 4, 1, 5, 7, 3, 6, 1, 9, 9, 3}, 4)
            .expect(83);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1}, 1).expect(1);

}
