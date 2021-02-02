package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1659. Maximize Grid Happiness
 * https://leetcode.com/problems/maximize-grid-happiness/
 *
 * You are given four integers, m, n, introvertsCount, and extrovertsCount. You have an m x n grid, and there are two
 * types of people: introverts and extroverts. There are introvertsCount introverts and extrovertsCount extroverts.
 *
 * You should decide how many people you want to live in the grid and assign each of them one grid cell. Note that you
 * do not have to have all the people living in the grid.
 *
 * The happiness of each person is calculated as follows:
 *
 * Introverts start with 120 happiness and lose 30 happiness for each neighbor (introvert or extrovert).
 * Extroverts start with 40 happiness and gain 20 happiness for each neighbor (introvert or extrovert).
 *
 * Neighbors live in the directly adjacent cells north, east, south, and west of a person's cell.
 *
 * The grid happiness is the sum of each person's happiness. Return the maximum possible grid happiness.
 *
 * Example 1:
 * <img src="./Q1659_PIC.png">
 * Input: m = 2, n = 3, introvertsCount = 1, extrovertsCount = 2
 * Output: 240
 * Explanation: Assume the grid is 1-indexed with coordinates (row, column).
 * We can put the introvert in cell (1,1) and put the extroverts in cells (1,3) and (2,3).
 * - Introvert at (1,1) happiness: 120 (starting happiness) - (0 * 30) (0 neighbors) = 120
 * - Extrovert at (1,3) happiness: 40 (starting happiness) + (1 * 20) (1 neighbor) = 60
 * - Extrovert at (2,3) happiness: 40 (starting happiness) + (1 * 20) (1 neighbor) = 60
 * The grid happiness is 120 + 60 + 60 = 240.
 * The above figure shows the grid in this example with each person's happiness. The introvert stays in the light green
 * cell while the extroverts live on the light purple cells.
 *
 * Example 2:
 *
 * Input: m = 3, n = 1, introvertsCount = 2, extrovertsCount = 1
 * Output: 260
 * Explanation: Place the two introverts in (1,1) and (3,1) and the extrovert at (2,1).
 * - Introvert at (1,1) happiness: 120 (starting happiness) - (1 * 30) (1 neighbor) = 90
 * - Extrovert at (2,1) happiness: 40 (starting happiness) + (2 * 20) (2 neighbors) = 80
 * - Introvert at (3,1) happiness: 120 (starting happiness) - (1 * 30) (1 neighbor) = 90
 * The grid happiness is 90 + 80 + 90 = 260.
 *
 * Example 3:
 *
 * Input: m = 2, n = 2, introvertsCount = 4, extrovertsCount = 0
 * Output: 240
 *
 * Constraints:
 *
 * 1 <= m, n <= 5
 * 0 <= introvertsCount, extrovertsCount <= min(m * n, 6)
 */
@RunWith(LeetCodeRunner.class)
public class Q1659_MaximizeGridHappiness {

    /**
     * dp 状态压缩的题.
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/maximize-grid-happiness/solution/you-yi-chong-zhuang-ya-jiao-zuo-hua-dong-chuang-ko/
     * @formatter:on
     */
    @Answer
    public int getMaxGridHappiness(int m, int n, int introvertsCount, int extrovertsCount) {
        // 0- 不放人 1-放内向 2-放外向 3^n
        int cases = (int) Math.pow(3, n);
        int cases_1 = (int) Math.pow(3, n - 1);
        int[][] offset = {
                {0, 0, 0},
                {0, -60, -10},
                {0, -10, 40}
        };

        int M = cases - 1;
        int[][][][] dp = new int[m * n + 1][introvertsCount + 1][extrovertsCount + 1][cases];

        for (int coor = m * n - 1; coor >= 0; coor--) {
            int i = coor / n, j = coor % n;
            for (int x = 0; x <= introvertsCount; x++) {
                for (int y = 0; y <= extrovertsCount; y++) {
                    // pre 就是前 n 个格子的状态（三进制）
                    for (int pre = 0; pre < cases; pre++) {
                        // nem 是 pre "左移" 一位, 并去掉首位的状态,比如三进制 2121->三进制 1210.
                        int nem = (pre * 3) % cases;
                        if (x > 0) {
                            int diff = 120 + (j != 0 ? 1 : 0) * offset[1][pre % 3] + offset[1][pre / cases_1];
                            dp[coor][x][y][pre] = Math.max(dp[coor][x][y][pre], diff + dp[coor + 1][x - 1][y][nem + 1]);
                        }
                        if (y > 0) {
                            int diff = 40 + (j != 0 ? 1 : 0) * offset[2][pre % 3] + offset[2][pre / cases_1];
                            dp[coor][x][y][pre] = Math.max(dp[coor][x][y][pre], diff + dp[coor + 1][x][y - 1][nem + 2]);
                        }
                        dp[coor][x][y][pre] = Math.max(dp[coor][x][y][pre], dp[coor + 1][x][y][nem]);
                    }
                }
            }
        }
        return dp[0][introvertsCount][extrovertsCount][0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 3, 1, 2).expect(240);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(3, 1, 2, 1).expect(260);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(2, 2, 4, 0).expect(240);

}
