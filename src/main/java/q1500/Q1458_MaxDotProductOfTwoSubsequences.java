package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1458. Max Dot Product of Two Subsequences
 * https://leetcode.com/problems/max-dot-product-of-two-subsequences/
 *
 * Given two arrays nums1 and nums2.
 *
 * Return the maximum dot product between non-empty subsequences of nums1 and nums2 with the same length.
 *
 * A subsequence of a array is a new array which is formed from the original array by deleting some (can be none) of the
 * characters without disturbing the relative positions of the remaining characters. (ie, [2,3,5] is a subsequence of
 * [1,2,3,4,5] while [1,5,3] is not).
 *
 * Example 1:
 *
 * Input: nums1 = [2,1,-2,5], nums2 = [3,0,-6]
 * Output: 18
 * Explanation: Take subsequence [2,-2] from nums1 and subsequence [3,-6] from nums2.
 * Their dot product is (2*3 + (-2)*(-6)) = 18.
 *
 * Example 2:
 *
 * Input: nums1 = [3,-2], nums2 = [2,-6,7]
 * Output: 21
 * Explanation: Take subsequence [3] from nums1 and subsequence [7] from nums2.
 * Their dot product is (3*7) = 21.
 *
 * Example 3:
 *
 * Input: nums1 = [-1,-1], nums2 = [1,1]
 * Output: -1
 * Explanation: Take subsequence [-1] from nums1 and subsequence [1] from nums2.
 * Their dot product is -1.
 *
 * Constraints:
 *
 * 1 <= nums1.length, nums2.length <= 500
 * -1000 <= nums1[i], nums2[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1458_MaxDotProductOfTwoSubsequences {

    @Answer
    public int maxDotProduct(int[] nums1, int[] nums2) {
        final int m = nums1.length, n = nums2.length;
        // dp[i][j] 表示在 nums1[0:i] 和 nums2[0:j] 的最大结果
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // nums1[i] 与 nums2[j] 匹配的结果.
                int prod = nums1[i] * nums2[j];
                if (i > 0 && j > 0) {
                    // 如果前面的数字 <0, 则不使用, 从这里开始匹配.
                    prod += Math.max(0, dp[i - 1][j - 1]);
                }
                // 和 nums1[i] 与 nums2[j] 不匹配的结果比较.
                if (i > 0) {
                    prod = Math.max(prod, dp[i - 1][j]);
                }
                if (j > 0) {
                    prod = Math.max(prod, dp[i][j - 1]);
                }
                dp[i][j] = prod;
            }
        }
        return dp[m - 1][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 1, -2, 5}, new int[]{3, 0, -6})
            .expect(18);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, -2}, new int[]{2, -6, 7})
            .expect(21);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{-1, -1}, new int[]{1, 1})
            .expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{-3, -8, 3, -10, 1, 3, 9}, new int[]{9, 2, 3, 7, -9, 1, -8, 5, -1, -1})
            .expect(200);

}
