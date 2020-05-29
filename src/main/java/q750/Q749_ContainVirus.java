package q750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/contain-virus/
 *
 * A virus is spreading rapidly, and your task is to quarantine the infected area by installing walls.
 *
 * The world is modeled as a 2-D array of cells, where 0 represents uninfected cells, and 1 represents cells
 * contaminated with the virus. A wall (and only one wall) can be installed between any two 4-directionally adjacent
 * cells, on the shared boundary.
 *
 * Every night, the virus spreads to all neighboring cells in all four directions unless blocked by a wall. Resources
 * are limited. Each day, you can install walls around only one region -- the affected area (continuous block of
 * infected cells) that threatens the most uninfected cells the following night. There will never be a tie.
 *
 * Can you save the day? If so, what is the number of walls required? If not, and the world becomes fully infected,
 * return the number of walls used.
 *
 * Example 1:
 *
 * Input: grid =
 * [[0,1,0,0,0,0,0,1],
 * [0,1,0,0,0,0,0,1],
 * [0,0,0,0,0,0,0,1],
 * [0,0,0,0,0,0,0,0]]
 * Output: 10
 * Explanation:
 * There are 2 contaminated regions.
 * On the first day, add 5 walls to quarantine the viral region on the left. The board after the virus spreads is:
 *
 * [[0,1,0,0,0,0,1,1],
 * [0,1,0,0,0,0,1,1],
 * [0,0,0,0,0,0,1,1],
 * [0,0,0,0,0,0,0,1]]
 *
 * On the second day, add 5 walls to quarantine the viral region on the right. The virus is fully contained.
 *
 * Example 2:
 *
 * Input: grid =
 * [[1,1,1],
 * [1,0,1],
 * [1,1,1]]
 * Output: 4
 * Explanation: Even though there is only one cell saved, there are 4 walls built.
 * Notice that walls are only built on the shared boundary of two different cells.
 *
 * Example 3:
 *
 * Input: grid =
 * [[1,1,1,0,0,0,0,0,0],
 * [1,0,1,0,1,1,1,1,1],
 * [1,1,1,0,0,0,0,0,0]]
 * Output: 13
 * Explanation: The region on the left only builds two new walls.
 *
 * Note:
 *
 * The number of rows and columns of grid will each be in the range [1, 50].
 * Each grid[i][j] will be either 0 or 1.
 * Throughout the described process, there is always a contiguous viral region that will infect strictly more
 * uncontaminated squares in the next round.
 */
@RunWith(LeetCodeRunner.class)
public class Q749_ContainVirus {

    /**
     * 用贪婪 (题目中要求每次隔离可能感染最多的区域) + DFS 来做.
     *
     * 解答中 grid 中的数字的含义:
     * < -1 : 潜在的感染区域(未感染区域)
     * -1 : 隔离的区域
     * 0 : 未感染区域
     * 1 : 已感染区域
     * >1 : DFS 中用于感染区域分区的标记
     */
    @Answer
    public int containVirus(int[][] grid) {
        m = grid.length;
        n = grid[0].length;

        int res = 0, val = 2;
        while (true) {
            // 找出潜在感染数量最多的区域
            int maxCount = 0, maxi = 0, maxj = 0;
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] == 1) {
                        int count = estimateInfect(grid, i, j, val++);
                        if (maxCount < count) {
                            maxCount = count;
                            maxi = i;
                            maxj = j;
                        }
                    }
                }
            }
            if (maxCount == 0) {
                return res;
            }

            // 隔离
            res += quarantine(grid, maxi, maxj);

            // 感染
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    if (grid[i][j] > 1) {
                        infect(grid, i, j);
                    }
                }
            }
        }
    }

    private int m, n;

    // 计算潜在的感染数量. val 用于区域分区.
    private int estimateInfect(int[][] grid, int i, int j, int val) {
        if (i == -1 || j == -1 || i == m || j == n || grid[i][j] == -1
                || grid[i][j] == val || grid[i][j] == -val) {
            // 边界/ 已隔离/ 已遍历感染/潜在感染区域
            return 0;
        }
        // 未被感染区域
        if (grid[i][j] <= 0) {
            grid[i][j] = -val;
            return 1;
        }
        // 已感染区域
        grid[i][j] = val;
        return estimateInfect(grid, i + 1, j, val)
                + estimateInfect(grid, i, j + 1, val)
                + estimateInfect(grid, i - 1, j, val)
                + estimateInfect(grid, i, j - 1, val);
    }

    // 隔离特定区域
    private int quarantine(int[][] grid, int i, int j) {
        if (i == -1 || j == -1 || i == m || j == n || grid[i][j] == -1) {
            return 0;
        }
        if (grid[i][j] <= 0) {
            return 1;
        }
        grid[i][j] = -1;
        return quarantine(grid, i + 1, j)
                + quarantine(grid, i, j + 1)
                + quarantine(grid, i - 1, j)
                + quarantine(grid, i, j - 1);
    }

    // 一天中的感染
    private void infect(int[][] grid, int i, int j) {
        if (i == -1 || j == -1 || i == m || j == n || grid[i][j] == -1
                || grid[i][j] == 1) {
            return;
        }
        if (grid[i][j] > 0) {
            grid[i][j] = 1;
            infect(grid, i + 1, j);
            infect(grid, i, j + 1);
            infect(grid, i - 1, j);
            infect(grid, i, j - 1);
        } else {
            grid[i][j] = 1;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 0, 0, 0, 0}
    }).expect(10);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
    }).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 1, 0, 0, 0, 0, 0, 0},
            {1, 0, 1, 0, 1, 1, 1, 1, 1},
            {1, 1, 1, 0, 0, 0, 0, 0, 0}
    }).expect(13);

}
