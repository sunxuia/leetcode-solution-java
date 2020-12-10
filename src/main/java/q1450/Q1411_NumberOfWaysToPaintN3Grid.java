package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1411. Number of Ways to Paint N × 3 Grid
 * https://leetcode.com/problems/number-of-ways-to-paint-n-3-grid/
 *
 * You have a grid of size n x 3 and you want to paint each cell of the grid with exactly one of the three colours: Red,
 * Yellow or Green while making sure that no two adjacent cells have the same colour (i.e no two cells that share
 * vertical or horizontal sides have the same colour).
 *
 * You are given n the number of rows of the grid.
 *
 * Return the number of ways you can paint this grid. As the answer may grow large, the answer must be computed modulo
 * 10^9 + 7.
 *
 * Example 1:
 * <img src="Q1411_PIC.png">
 * Input: n = 1
 * Output: 12
 * Explanation: There are 12 possible way to paint the grid as shown:
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: 54
 *
 * Example 3:
 *
 * Input: n = 3
 * Output: 246
 *
 * Example 4:
 *
 * Input: n = 7
 * Output: 106494
 *
 * Example 5:
 *
 * Input: n = 5000
 * Output: 30228214
 *
 * Constraints:
 *
 * n == grid.length
 * grid[i].length == 3
 * 1 <= n <= 5000
 */
@RunWith(LeetCodeRunner.class)
public class Q1411_NumberOfWaysToPaintN3Grid {

    @Answer
    public int numOfWays(int n) {
        final int mod = 10_0000_0007;
        int[][] dp = new int[n][0b101011];
        for (int mask : VALID_MASKS) {
            dp[0][mask] = 1;
        }
        for (int i = 1; i < n; i++) {
            for (int upper : VALID_MASKS) {
                for (int curr : VALID_MASKS) {
                    // 判断上下之间是否有重叠
                    if ((upper & 0b110000) != (curr & 0b110000)
                            && (upper & 0b1100) != (curr & 0b1100)
                            && (upper & 0b11) != (curr & 0b11)) {
                        dp[i][curr] = (dp[i][curr] + dp[i - 1][upper]) % mod;
                    }
                }
            }
        }

        int res = 0;
        for (int mask : VALID_MASKS) {
            res = (res + dp[n - 1][mask]) % mod;
        }
        return res;
    }

    /**
     * 表示每层有效的排列方式, 0/1/2 分别对应不同的颜色
     */
    private static final int[] VALID_MASKS = new int[]{
            0b00_01_00, 0b00_01_10, 0b00_10_00, 0b00_10_01,
            0b01_00_01, 0b01_00_10, 0b01_10_00, 0b01_10_01,
            0b10_00_01, 0b10_00_10, 0b10_01_00, 0b10_01_10
    };

    /**
     * leetcode 上比较快的解法, 是上面做法的递推优化.
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/number-of-ways-to-paint-n-x-3-grid/solution/gei-n-x-3-wang-ge-tu-tu-se-de-fang-an-shu-by-leetc/
     * @formatter:on
     */
    @Answer
    public int numOfWays2(int n) {
        final int mod = 10_0000_0007;
        long color2 = 6, color3 = 6;
        for (int i = 2; i <= n; i++) {
            long temp = color3;
            color3 = (2 * color3 + 2 * color2) % mod;
            color2 = (2 * temp + 3 * color2) % mod;
        }

        return (int) (color3 + color2) % mod;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(12);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(54);

    @TestData
    public DataExpectation example3 = DataExpectation.create(3).expect(246);

    @TestData
    public DataExpectation example4 = DataExpectation.create(7).expect(106494);

    @TestData
    public DataExpectation example5 = DataExpectation.create(5000).expect(30228214);

}
