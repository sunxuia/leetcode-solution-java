package q450;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/split-array-largest-sum/
 *
 * Given an array which consists of non-negative integers and an integer m, you can split the array into m non-empty
 * continuous subarrays. Write an algorithm to minimize the largest sum among these m subarrays.
 *
 * Note:
 * If n is the length of array, assume the following constraints are satisfied:
 *
 * 1 ≤ n ≤ 1000
 * 1 ≤ m ≤ min(50, n)
 *
 * Examples:
 *
 * Input:
 * nums = [7,2,5,10,8]
 * m = 2
 *
 * Output:
 * 18
 *
 * Explanation:
 * There are four ways to split nums into two subarrays.
 * The best way is to split it into [7,2,5] and [10,8],
 * where the largest sum among the two subarrays is only 18.
 */
@RunWith(LeetCodeRunner.class)
public class Q410_SplitArrayLargestSum {

    /**
     * (遍历的方法会超时)
     * dp 的方式: https://www.cnblogs.com/grandyang/p/5933787.html
     */
    @Answer
    public int splitArray(int[] nums, int m) {
        int n = nums.length;
        int[] sums = new int[n + 1];
        for (int i = 1; i <= n; ++i) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }

        // dp[i][j] 表示将数组中前j 个数字分成i 组所能得到的题意结果
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;

        for (int i = 1; i <= m; i++) {
            // j 表示i 所在分组的起始位置,
            for (int j = 1; j <= n; j++) {
                // 计算如果是这种分组的情况, 那么得到的题意结果是什么.
                for (int k = i - 1; k < j; k++) {
                    int val = Math.max(dp[i - 1][k], sums[j] - sums[k]);
                    dp[i][j] = Math.min(dp[i][j], val);
                }
            }
        }
        return dp[m][n];
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{7, 2, 5, 10, 8}, 2)
            .expect(18);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, Integer.MAX_VALUE}, 2)
            .expect(Integer.MAX_VALUE);

}
