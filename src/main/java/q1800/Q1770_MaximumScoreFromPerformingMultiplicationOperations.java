package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1770. Maximum Score from Performing Multiplication Operations
 * https://leetcode.com/problems/maximum-score-from-performing-multiplication-operations/
 *
 * You are given two integer arrays nums and multipliers of size n and m respectively, where n >= m. The arrays are
 * 1-indexed.
 *
 * You begin with a score of 0. You want to perform exactly m operations. On the ith operation (1-indexed), you will:
 *
 * Choose one integer x from either the start or the end of the array nums.
 * Add multipliers[i] * x to your score.
 * Remove x from the array nums.
 *
 * Return the maximum score after performing m operations.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3], multipliers = [3,2,1]
 * Output: 14
 * Explanation: An optimal solution is as follows:
 * - Choose from the end, [1,2,3], adding 3 * 3 = 9 to the score.
 * - Choose from the end, [1,2], adding 2 * 2 = 4 to the score.
 * - Choose from the end, [1], adding 1 * 1 = 1 to the score.
 * The total score is 9 + 4 + 1 = 14.
 *
 * Example 2:
 *
 * Input: nums = [-5,-3,-3,-2,7,1], multipliers = [-10,-5,3,4,6]
 * Output: 102
 * Explanation: An optimal solution is as follows:
 * - Choose from the start, [-5,-3,-3,-2,7,1], adding -5 * -10 = 50 to the score.
 * - Choose from the start, [-3,-3,-2,7,1], adding -3 * -5 = 15 to the score.
 * - Choose from the start, [-3,-2,7,1], adding -3 * 3 = -9 to the score.
 * - Choose from the end, [-2,7,1], adding 1 * 4 = 4 to the score.
 * - Choose from the end, [-2,7], adding 7 * 6 = 42 to the score.
 * The total score is 50 + 15 - 9 + 4 + 42 = 102.
 *
 * Constraints:
 *
 * n == nums.length
 * m == multipliers.length
 * 1 <= m <= 10^3
 * m <= n <= 10^5
 * -1000 <= nums[i], multipliers[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1770_MaximumScoreFromPerformingMultiplicationOperations {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int maximumScore(int[] nums, int[] multipliers) {
        final int n = nums.length, m = multipliers.length;
        Integer[][] cache = new Integer[m][m];
        return dfs(cache, nums, 0, n - 1, multipliers, 0);
    }

    private int dfs(Integer[][] cache, int[] nums, int left, int right, int[] multipliers, int i) {
        if (i == multipliers.length) {
            return 0;
        }
        if (cache[i][left] != null) {
            return cache[i][left];
        }
        int leftRes = multipliers[i] * nums[left]
                + dfs(cache, nums, left + 1, right, multipliers, i + 1);
        int rightRes = multipliers[i] * nums[right]
                + dfs(cache, nums, left, right - 1, multipliers, i + 1);
        int res = Math.max(leftRes, rightRes);
        cache[i][left] = res;
        return res;
    }

    /**
     * dp 的解法
     */
    @Answer
    public int maximumScore2(int[] nums, int[] multipliers) {
        final int n = nums.length, m = multipliers.length;
        // dp[i][left] 表示第 i 轮尚未取值, 左边界在left 的结果.
        int[][] dp = new int[m + 1][m + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dp[i + 1], Integer.MIN_VALUE);
            for (int left = 0; left <= i; left++) {
                int right = n - i + left - 1;
                // 本轮取左边的元素
                dp[i + 1][left + 1] = Math.max(dp[i + 1][left + 1],
                        multipliers[i] * nums[left] + dp[i][left]);
                // 本轮取右边的元素
                dp[i + 1][left] = Math.max(dp[i + 1][left],
                        multipliers[i] * nums[right] + dp[i][left]);
            }
        }

        int res = Integer.MIN_VALUE;
        for (int i = 0; i <= m; i++) {
            res = Math.max(res, dp[m][i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3}, new int[]{3, 2, 1})
            .expect(14);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{-5, -3, -3, -2, 7, 1}, new int[]{-10, -5, 3, 4, 6})
            .expect(102);

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.read(testDataFile, 1, int[].class),
                    TestDataFileHelper.read(testDataFile, 2, int[].class))
            .expect(-18334745);

}
