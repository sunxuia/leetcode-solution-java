package q450;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/pacific-atlantic-water-flow/
 *
 * Given an m x n matrix of non-negative integers representing the height of each unit cell in a continent, the
 * "Pacific ocean" touches the left and top edges of the matrix and the "Atlantic ocean" touches the right and bottom
 * edges.
 *
 * Water can only flow in four directions (up, down, left, or right) from a cell to another one with height equal or
 * lower.
 *
 * Find the list of grid coordinates where water can flow to both the Pacific and Atlantic ocean.
 *
 * Note:
 *
 * The order of returned grid coordinates does not matter.
 * Both m and n are less than 150.
 *
 *
 *
 * Example:
 *
 * Given the following 5x5 matrix:
 *
 * >    Pacific ~   ~   ~   ~   ~
 * >         ~  1   2   2   3  (5) *
 * >         ~  3   2   3  (4) (4) *
 * >         ~  2   4  (5)  3   1  *
 * >         ~ (6) (7)  1   4   5  *
 * >         ~ (5)  1   1   2   4  *
 * >            *   *   *   *   * Atlantic
 *
 * Return:
 *
 * [[0, 4], [1, 3], [1, 4], [2, 2], [3, 0], [3, 1], [4, 0]] (positions with parentheses in above matrix).
 */
@RunWith(LeetCodeRunner.class)
public class Q417_PacificAtlanticWaterFlow {

    // 看不懂什么意思. 根据 https://www.cnblogs.com/grandyang/p/5962508.html 中的解答
    // 是这么个意思: 矩阵表示一个海底高度, 太平洋的水从上面行和左边列进入, 大西洋的水从下边行和右边列进入,
    // 水没有高度, 不能叠加, 且是无限的, 可以往高度相同或小于的地方流, 否则就不流. 要求同时被太平洋和大西洋
    // 的水流到的海地的左边.
    @Answer
    public List<List<Integer>> pacificAtlantic(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return Collections.emptyList();
        }
        final int m = matrix.length, n = matrix[0].length;
        List<List<Integer>> res = new ArrayList<>();
        boolean[][] pacific = new boolean[m][n];
        boolean[][] atlantic = new boolean[m][n];
        // 水流扩散
        for (int i = 0; i < m; ++i) {
            dfs(matrix, pacific, Integer.MIN_VALUE, i, 0);
            dfs(matrix, atlantic, Integer.MIN_VALUE, i, n - 1);
        }
        for (int i = 0; i < n; ++i) {
            dfs(matrix, pacific, Integer.MIN_VALUE, 0, i);
            dfs(matrix, atlantic, Integer.MIN_VALUE, m - 1, i);
        }
        // 判断该点是否同时被太平洋和大西洋的水扩散到了.
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (pacific[i][j] && atlantic[i][j]) {
                    res.add(Arrays.asList(i, j));
                }
            }
        }
        return res;
    }

    void dfs(int[][] matrix, boolean[][] visited, int pre, int i, int j) {
        final int m = matrix.length, n = matrix[0].length;
        if (i < 0 || i >= m || j < 0 || j >= n
                || visited[i][j] || matrix[i][j] < pre) {
            return;
        }
        visited[i][j] = true;
        dfs(matrix, visited, matrix[i][j], i + 1, j);
        dfs(matrix, visited, matrix[i][j], i - 1, j);
        dfs(matrix, visited, matrix[i][j], i, j + 1);
        dfs(matrix, visited, matrix[i][j], i, j - 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2, 2, 3, 5},
            {3, 2, 3, 4, 4},
            {2, 4, 5, 3, 1},
            {6, 7, 1, 4, 5},
            {5, 1, 1, 2, 4}
    }).expect(new int[][]{{0, 4}, {1, 3}, {1, 4,}, {2, 2}, {3, 0}, {3, 1}, {4, 0}});

}
