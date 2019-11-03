package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/unique-paths/
 *
 * A robot is located at the top-left corner of a m x n grid (marked 'Start' in the diagram below).
 *
 * The robot can only move either down or right at any point in time. The robot is trying to reach the bottom-right
 * corner of the grid (marked 'Finish' in the diagram below).
 *
 * How many possible unique paths are there?
 *
 *  (图片见 Q062_PIC.png)
 *
 * Above is a 7 x 3 grid. How many possible unique paths are there?
 *
 * Note: m and n will be at most 100.
 *
 * Example 1:
 *
 * Input: m = 3, n = 2
 * Output: 3
 * Explanation:
 * From the top-left corner, there are a total of 3 ways to reach the bottom-right corner:
 * 1. Right -> Right -> Down
 * 2. Right -> Down -> Right
 * 3. Down -> Right -> Right
 * Example 2:
 *
 * Input: m = 7, n = 3
 * Output: 28
 */
@RunWith(LeetCodeRunner.class)
public class Q062_UniquePaths {

    @Answer
    public int uniquePaths(int m, int n) {
        int[][] dp = new int[m][n];
        for (int i = 0; i < m; i++) {
            dp[i][0] = 1;
        }
        for (int j = 0; j < n; j++) {
            dp[0][j] = 1;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
            }
        }
        return dp[m - 1][n - 1];
    }

    /**
     * 另一种做法, 因为从一头走到另一头, 向下和向右的走的步数是确定的, 向下m-1步和向右n-1步
     * 于是问题就变成了(m+n)个元素的排列组合的问题了. 总数是在m-1 个 → 序列中插入n-1 个 ↓ 的不同组合情况
     * 计算公式是(m+n-2)!/((m-1)!(n-1)!), 优化这个算式后得到下面的结果.
     */
    @Answer
    public int leetCode(int m, int n) {
        if (m < n) {
            int t = m;
            m = n;
            n = t;
        }
        long res = 1;
        for (int i = m; i <= m + n - 2; i++) {
            res *= i;
        }
        for (int i = 2; i <= n - 1; i++) {
            res /= i;
        }
        return (int) res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 2).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(7, 3).expect(28);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(10, 10).expect(48620);
}
