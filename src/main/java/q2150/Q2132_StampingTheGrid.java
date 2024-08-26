package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2132. Stamping the Grid</h3>
 * <a href="https://leetcode.com/problems/stamping-the-grid/">
 * https://leetcode.com/problems/stamping-the-grid/
 * </a><br/>
 *
 * <p>You are given an <code>m x n</code> binary matrix <code>grid</code> where each cell is either <code>0</code>
 * (empty) or <code>1</code> (occupied).</p>
 *
 * <p>You are then given stamps of size <code>stampHeight x stampWidth</code>. We want to fit the stamps such that they
 * follow the given <strong>restrictions</strong> and <strong>requirements</strong>:</p>
 *
 * <ol>
 * 	<li>Cover all the <strong>empty</strong> cells.</li>
 * 	<li>Do not cover any of the <strong>occupied</strong> cells.</li>
 * 	<li>We can put as <strong>many</strong> stamps as we want.</li>
 * 	<li>Stamps can <strong>overlap</strong> with each other.</li>
 * 	<li>Stamps are not allowed to be <strong>rotated</strong>.</li>
 * 	<li>Stamps must stay completely <strong>inside</strong> the grid.</li>
 * </ol>
 *
 * <p>Return <code>true</code> <em>if it is possible to fit the stamps while following the given restrictions and
 * requirements. Otherwise, return</em> <code>false</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/03/ex1.png" style="width: 180px; height: 237px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,0,0,0],[1,0,0,0],[1,0,0,0],[1,0,0,0],[1,0,0,0]], stampHeight = 4, stampWidth = 3
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> We have two overlapping stamps (labeled 1 and 2 in the image) that are able to cover all the empty cells.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/03/ex2.png" style="width: 170px; height: 179px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,0,0,0],[0,1,0,0],[0,0,1,0],[0,0,0,1]], stampHeight = 2, stampWidth = 2
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong> There is no way to fit the stamps onto all the empty cells without the stamps going outside the grid.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>m == grid.length</code></li>
 * 	<li><code>n == grid[r].length</code></li>
 * 	<li><code>1 &lt;= m, n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= m * n &lt;= 2 * 10<sup>5</sup></code></li>
 * 	<li><code>grid[r][c]</code> is either <code>0</code> or <code>1</code>.</li>
 * 	<li><code>1 &lt;= stampHeight, stampWidth &lt;= 10<sup>5</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2132_StampingTheGrid {

    // leetcode 上有些更加优化的解法, 时间复杂度相同都是 O(mn)
    @Answer
    public boolean possibleToStamp(int[][] grid, int stampHeight, int stampWidth) {
        final int m = grid.length;
        final int n = grid[0].length;
        // grid[0, 0] 到 grid[i, j] 的矩形中元素之和, 由此可以判断一个矩形内是否含有 1
        int[][] sums = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sums[i + 1][j + 1] = grid[i][j] + sums[i][j + 1] + sums[i + 1][j] - sums[i][j];
            }
        }

        // 设置一个 state[i, j] 表示以 (i, j) 为左上角, 是否可以构建一个stamp, 如果是, 则 state[i, j]=1
        // 这个 covers 就是求的 state[0, 0] 到 state[i, j] 的矩形之和, 这样就可以求出一个矩形区间内是否有 stamp 左上角.
        int[][] covers = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                covers[i + 1][j + 1] = covers[i][j + 1] + covers[i + 1][j] - covers[i][j];

                if (grid[i][j] == 0) {
                    // 判断当前点是否能够作为stamp 的左上角
                    int right = j + stampWidth - 1;
                    int bottom = i + stampHeight - 1;
                    if (right < n && bottom < m) {
                        int square = sums[bottom + 1][right + 1]
                                - sums[bottom + 1][j] - sums[i][right + 1] + sums[i][j];
                        if (square == 0) {
                            covers[i + 1][j + 1]++;
                            continue;
                        }
                    }

                    // 如果当前点无法作为stamp 左上角, 则判断当前点的右上边是否有stamp 的左上角
                    int top = Math.max(i - stampHeight + 1, 0);
                    int left = Math.max(j - stampWidth + 1, 0);
                    int square = covers[i + 1][j + 1]
                            - covers[i + 1][left] - covers[top][j + 1] + covers[top][left];
                    if (square == 0) {
                        // 找不到一个左上角, 表示这个点没有被stamp 覆盖.
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0},
                    {1, 0, 0, 0}}, 4, 3)
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                    {1, 0, 0, 0},
                    {0, 1, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}}, 2, 2)
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{
                    {1, 0},
                    {0, 0}}, 2, 2)
            .expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[][]{
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0},
                    {0, 0, 0, 0, 1},
                    {0, 0, 0, 1, 1}}, 2, 2)
            .expect(false);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[][]{
                    {1, 1, 1, 0},
                    {0, 0, 0, 1},
                    {1, 1, 1, 0},
                    {1, 1, 0, 0},
                    {0, 0, 0, 0},
                    {0, 1, 0, 1},
                    {0, 1, 0, 1},
                    {1, 1, 1, 0},
                    {1, 0, 1, 1},
                    {0, 0, 0, 1}}, 13, 6)
            .expect(false);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .createWith(new int[][]{{0}, {0}, {0}, {0}, {0}, {0}}, 6, 1)
            .expect(true);

    @TestData
    public DataExpectation normal5 = DataExpectation
            .createWith(new int[][]{
                    {1, 1, 0, 1},
                    {1, 0, 0, 1},
                    {1, 1, 1, 1}
            }, 1, 2)
            .expect(false);

    @TestData
    public DataExpectation normal6 = DataExpectation
            .createWith(new int[][]{
                    {0, 0, 0, 1, 1},
                    {0, 0, 0, 0, 0},
                    {1, 1, 0, 0, 0}
            }, 2, 3)
            .expect(true);

}
