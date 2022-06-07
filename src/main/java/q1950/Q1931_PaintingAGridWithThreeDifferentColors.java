package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1931. Painting a Grid With Three Different Colors
 * https://leetcode.com/problems/painting-a-grid-with-three-different-colors/
 *
 * You are given two integers m and n. Consider an m x n grid where each cell is initially white. You can paint each
 * cell red, green, or blue. All cells must be painted.
 *
 * Return the number of ways to color the grid with no two adjacent cells having the same color. Since the answer can be
 * very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: m = 1, n = 1
 * Output: 3
 * Explanation: The three possible colorings are shown in the image above.
 *
 * Example 2:
 *
 * Input: m = 1, n = 2
 * Output: 6
 * Explanation: The six possible colorings are shown in the image above.
 *
 * Example 3:
 *
 * Input: m = 5, n = 5
 * Output: 580986
 *
 * Constraints:
 *
 * 1 <= m <= 5
 * 1 <= n <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1931_PaintingAGridWithThreeDifferentColors {

    /**
     * 根据提示应当利用 1<=m<=5 的限制, 使用bitmask 来解答.
     * 这是{@link q1450.Q1411_NumberOfWaysToPaintN3Grid} 题目的升级版本, 解法类似.
     */
    @Answer
    public int colorTheGrid(int m, int n) {
        final int mod = 10_0000_0007;
        int[][] dp = new int[n][1 << m * 3];
        for (int mask : MASKS[m]) {
            dp[0][mask] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int left : MASKS[m]) {
                for (int curr : MASKS[m]) {
                    // 判断左右之间是否有重叠
                    if ((left & curr) == 0) {
                        dp[i][curr] = (dp[i][curr] + dp[i - 1][left]) % mod;
                    }
                }
            }
        }

        int res = 0;
        for (int mask : MASKS[m]) {
            res = (res + dp[n - 1][mask]) % mod;
        }
        return res;
    }

    private static final int[][] MASKS = new int[6][];

    static {
        MASKS[0] = new int[]{0};
        for (int m = 1; m <= 5; m++) {
            MASKS[m] = new int[3 * (1 << m - 1)];
            int i = 0;
            for (int mask : MASKS[m - 1]) {
                for (int c = 1; c <= 4; c <<= 1) {
                    if ((mask & 0b111) != c) {
                        MASKS[m][i++] = mask << 3 | c;
                    }
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 1).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(1, 2).expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(5, 5).expect(580986);

}
