package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1559. Detect Cycles in 2D Grid
 * https://leetcode.com/problems/detect-cycles-in-2d-grid/
 *
 * Given a 2D array of characters grid of size m x n, you need to find if there exists any cycle consisting of the same
 * value in grid.
 *
 * A cycle is a path of length 4 or more in the grid that starts and ends at the same cell. From a given cell, you can
 * move to one of the cells adjacent to it - in one of the four directions (up, down, left, or right), if it has the
 * same value of the current cell.
 *
 * Also, you cannot move to the cell that you visited in your last move. For example, the cycle (1, 1) -> (1, 2) -> (1,
 * 1) is invalid because from (1, 2) we visited (1, 1) which was the last visited cell.
 *
 * Return true if any cycle of the same value exists in grid, otherwise, return false.
 *
 * Example 1:
 * <img src="./Q1559_PIC1.png">
 * Input: grid = [["a","a","a","a"],["a","b","b","a"],["a","b","b","a"],["a","a","a","a"]]
 * Output: true
 * Explanation: There are two valid cycles shown in different colors in the image below:
 * <img src="./Q1559_PIC2.png">
 *
 * Example 2:
 * <img src="./Q1559_PIC3.png">
 * Input: grid = [["c","c","c","a"],["c","d","c","c"],["c","c","e","c"],["f","c","c","c"]]
 * Output: true
 * Explanation: There is only one valid cycle highlighted in the image below:
 * <img src="./Q1559_PIC4.png">
 *
 * Example 3:
 * <img src="./Q1559_PIC5.png">
 * Input: grid = [["a","b","b"],["b","z","b"],["b","b","a"]]
 * Output: false
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m <= 500
 * 1 <= n <= 500
 * grid consists only of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1559_DetectCyclesIn2dGrid {

    @Answer
    public boolean containsCycle(char[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[][] matrix = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] >= 0
                        && dfs(grid, matrix, i, j, 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] grid, int[][] matrix, int i, int j, int v) {
        final int m = grid.length, n = grid[0].length;
        if (matrix[i][j] > 0) {
            // >=4 则说明存在环路 (不是从边上的点往回走的)
            return v - matrix[i][j] >= 4;
        }
        matrix[i][j] = v;
        for (int[] d : DIRECTIONS) {
            int ni = i + d[0], nj = j + d[1];
            if (0 <= ni && ni < m && 0 <= nj && nj < n
                    && grid[ni][nj] == grid[i][j]) {
                if (dfs(grid, matrix, ni, nj, v + 1)) {
                    return true;
                }
            }
        }
        // -1 表示这个点已经访问过了
        matrix[i][j] = -1;
        return false;
    }

    private static int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    /**
     * LeetCode 上最快的解法, 使用并查集.
     */
    @Answer
    public boolean containsCycle2(char[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[] roots = new int[m * n];
        for (int i = 0; i < m * n; i++) {
            roots[i] = i;
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int id = i * n + j;
                if (i > 0 && grid[i][j] == grid[i - 1][j]) {
                    // 与上面的字符相同, 判断是否是属于同一个集合, 如果是则形成回路
                    int upper = findRoot(roots, (i - 1) * n + j);
                    if (id != upper) {
                        roots[id] = upper;
                        id = upper;
                    }
                }
                if (j > 0 && grid[i][j] == grid[i][j - 1]) {
                    // 与左边的字符相同, 判断是否是属于同一个集合, 如果是则形成回路
                    int left = findRoot(roots, i * n + j - 1);
                    if (i > 0 && grid[i][j] == grid[i - 1][j] && id == left) {
                        // 此时 id 肯定 == upper, 左边与该点此时并未做联通操作,
                        // 而此时已经判定两点联通, 则说明存在一个其他联通路径,
                        // 加上该联通路径则形成一个环.
                        return true;
                    }
                    if (id != left) {
                        roots[id] = left;
                    }
                }
            }
        }
        return false;
    }

    private int findRoot(int[] parents, int i) {
        if (parents[i] == i) {
            return i;
        }
        return parents[i] = findRoot(parents, parents[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new char[][]{
            {'a', 'a', 'a', 'a'},
            {'a', 'b', 'b', 'a'},
            {'a', 'b', 'b', 'a'},
            {'a', 'a', 'a', 'a'}
    }).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new char[][]{
            {'c', 'c', 'c', 'a'},
            {'c', 'd', 'c', 'c'},
            {'c', 'c', 'e', 'c'},
            {'f', 'c', 'c', 'c'}
    }).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new char[][]{
            {'a', 'b', 'b'},
            {'b', 'z', 'b'},
            {'b', 'b', 'a'}
    }).expect(false);

}
