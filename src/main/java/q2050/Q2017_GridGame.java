package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 2017. Grid Game
 * https://leetcode.com/problems/grid-game/
 *
 * You are given a 0-indexed 2D array grid of size 2 x n, where grid[r][c] represents the number of points at position
 * (r, c) on the matrix. Two robots are playing a game on this matrix.
 *
 * Both robots initially start at (0, 0) and want to reach (1, n-1). Each robot may only move to the right ((r, c) to
 * (r, c + 1)) or down ((r, c) to (r + 1, c)).
 *
 * At the start of the game, the first robot moves from (0, 0) to (1, n-1), collecting all the points from the cells on
 * its path. For all cells (r, c) traversed on the path, grid[r][c] is set to 0. Then, the second robot moves from (0,
 * 0) to (1, n-1), collecting the points on its path. Note that their paths may intersect with one another.
 *
 * The first robot wants to minimize the number of points collected by the second robot. In contrast, the second robot
 * wants to maximize the number of points it collects. If both robots play optimally, return the number of points
 * collected by the second robot.
 *
 * Example 1:
 * (图Q2017_PIC1.png)
 * Input: grid = [[2,5,4],[1,5,1]]
 * Output: 4
 * Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second
 * robot is shown in blue.
 * The cells visited by the first robot are set to 0.
 * The second robot will collect 0 + 0 + 4 + 0 = 4 points.
 *
 * Example 2:
 * (图Q2017_PIC2.png)
 * Input: grid = [[3,3,1],[8,5,2]]
 * Output: 4
 * Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second
 * robot is shown in blue.
 * The cells visited by the first robot are set to 0.
 * The second robot will collect 0 + 3 + 1 + 0 = 4 points.
 *
 * Example 3:
 * (图Q2017_PIC3.png)
 * Input: grid = [[1,3,1,15],[1,3,3,1]]
 * Output: 7
 * Explanation: The optimal path taken by the first robot is shown in red, and the optimal path taken by the second
 * robot is shown in blue.
 * The cells visited by the first robot are set to 0.
 * The second robot will collect 0 + 1 + 3 + 3 + 0 = 7 points.
 *
 * Constraints:
 *
 * grid.length == 2
 * n == grid[r].length
 * 1 <= n <= 5 * 10^4
 * 1 <= grid[r][c] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q2017_GridGame {

    @Answer
    public long gridGame(int[][] grid) {
        final int n = grid[0].length;
        long[][] sums = new long[2][n + 1];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < n; j++) {
                sums[i][j + 1] = sums[i][j] + grid[i][j];
            }
        }

        // 计算机器人1 从i 位置向下走到第二行的情况下可能的结果
        long res = Long.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            // 机器人2 选择最大结果
            long points = Math.max(sums[0][n] - sums[0][i + 1], sums[1][i] - sums[1][0]);
            // 机器人1 针对机器人2 的结果选择最小值
            res = Math.min(res, points);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {2, 5, 4},
            {1, 5, 1}
    }).expect(4L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {3, 3, 1},
            {8, 5, 2}
    }).expect(4L);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 3, 1, 15},
            {1, 3, 3, 1}
    }).expect(7L);

    @TestData
    public DataExpectation overflow = DataExpectation
            .create(TestDataFileHelper.read(int[][].class))
            .expect(2374739160L);

}
