package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/largest-sum-of-averages/
 *
 * We partition a row of numbers A into at most K adjacent (non-empty) groups, then our score is the sum of the
 * average of each group. What is the largest score we can achieve?
 *
 * Note that our partition must use every number in A, and that scores are not necessarily integers.
 *
 * Example:
 * Input:
 * A = [9,1,2,3,9]
 * K = 3
 * Output: 20
 * Explanation:
 * The best choice is to partition A into [9], [1, 2, 3], [9]. The answer is 9 + (1 + 2 + 3) / 3 + 9 = 20.
 * We could have also partitioned A into [9, 1], [2], [3, 9], for example.
 * That partition would lead to a score of 5 + 2 + 6 = 13, which is worse.
 *
 *
 *
 * Note:
 *
 * 1 <= A.length <= 100.
 * 1 <= A[i] <= 10000.
 * 1 <= K <= A.length.
 * Answers within 10^-6 of the correct answer will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q813_LargestSumOfAverages {

    // Solution 中给出的解法如下
    // 中文说明参见 https://www.cnblogs.com/grandyang/p/9504413.html
    @Answer
    public double largestSumOfAverages(int[] A, int K) {
        final int n = A.length;
        double[] sums = new double[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + A[i];
        }

        // dp[i] 表示[i, n) 的子数组最大分为K 组能得到的最大分数
        double[] dp = new double[n];
        // 首先初始化为 [i, n) 在一个数组的情况
        for (int i = 0; i < n; i++) {
            dp[i] = (sums[n] - sums[i]) / (n - i);
        }

        // 分别划分为k 组的情况
        for (int k = 0; k < K - 1; k++) {
            // 每次比上一次多划分出1 组
            for (int i = 0; i < n; i++) {
                // 向后遍历, 寻找最大结果的划分方式
                // 分为 [i, j), [j, n) 2 个区间, [j, n) 区间内可能在上一步进行了更多的划分
                for (int j = i + 1; j < n; j++) {
                    dp[i] = Math.max(dp[i], (sums[j] - sums[i]) / (j - i) + dp[j]);
                }
            }
        }
        // [0, n) 组数组的结果
        return dp[0];
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{9, 1, 2, 3, 9}, 3).expect(20.0);

}
