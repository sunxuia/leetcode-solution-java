package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 931. Minimum Falling Path Sum
 * https://leetcode.com/problems/minimum-falling-path-sum/
 *
 * Given a square array of integers A, we want the minimum sum of a falling path through A.
 *
 * A falling path starts at any element in the first row, and chooses one element from each row.  The next row's choice
 * must be in a column that is different from the previous row's column by at most one.
 *
 * Example 1:
 *
 * Input: [[1,2,3],[4,5,6],[7,8,9]]
 * Output: 12
 * Explanation:
 * The possible falling paths are:
 *
 * [1,4,7], [1,4,8], [1,5,7], [1,5,8], [1,5,9]
 * [2,4,7], [2,4,8], [2,5,7], [2,5,8], [2,5,9], [2,6,8], [2,6,9]
 * [3,5,7], [3,5,8], [3,5,9], [3,6,8], [3,6,9]
 *
 * The falling path with the smallest sum is [1,4,7], so the answer is 12.
 *
 * Constraints:
 *
 * 1 <= A.length == A[0].length <= 100
 * -100 <= A[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q931_MinimumFallingPathSum {

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})
            .expect(12);

    @Answer
    public int minFallingPathSum(int[][] A) {
        final int m = A.length, n = A[0].length;
        int[][] dp = new int[m][n];
        dp[0] = A[0];
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = dp[i - 1][j];
                if (0 < j) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1]);
                }
                if (j < n - 1) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j + 1]);
                }
                dp[i][j] += A[i][j];
            }
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.min(res, dp[m - 1][i]);
        }
        return res;
    }

}
