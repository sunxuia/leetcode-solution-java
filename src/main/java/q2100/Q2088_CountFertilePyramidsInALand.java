package q2100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2088. Count Fertile Pyramids in a Land</h3>
 * <a href="https://leetcode.com/problems/count-fertile-pyramids-in-a-land/">
 * https://leetcode.com/problems/count-fertile-pyramids-in-a-land/
 * </a><br/>
 *
 * <p>A farmer has a <strong>rectangular grid</strong> of land with <code>m</code> rows and <code>n</code> columns that
 * can be divided into unit cells. Each cell is either <strong>fertile</strong> (represented by a <code>1</code>) or
 * <strong>barren</strong> (represented by a <code>0</code>). All cells outside the grid are considered barren.</p>
 *
 * <p>A <strong>pyramidal plot</strong> of land can be defined as a set of cells with the following criteria:</p>
 *
 * <ol>
 * 	<li>The number of cells in the set has to be <strong>greater than </strong><code>1</code> and all cells must be
 * 	<strong>fertile</strong>.</li>
 * 	<li>The <strong>apex</strong> of a pyramid is the <strong>topmost</strong> cell of the pyramid. The
 * 	<strong>height</strong> of a pyramid is the number of rows it covers. Let <code>(r, c)</code> be the apex of the
 * 	pyramid, and its height be <code>h</code>. Then, the plot comprises of cells <code>(i, j)</code> where <code>r
 * 	&lt;= i &lt;= r + h - 1</code> <strong>and</strong> <code>c - (i - r) &lt;= j &lt;= c + (i - r)</code>.</li>
 * </ol>
 *
 * <p>An <strong>inverse pyramidal plot</strong> of land can be defined as a set of cells with similar criteria:</p>
 *
 * <ol>
 * 	<li>The number of cells in the set has to be <strong>greater than </strong><code>1</code> and all cells must be
 * 	<strong>fertile</strong>.</li>
 * 	<li>The <strong>apex</strong> of an inverse pyramid is the <strong>bottommost</strong> cell of the inverse pyramid
 * 	. The <strong>height</strong> of an inverse pyramid is the number of rows it covers. Let <code>(r, c)</code> be
 * 	the apex of the pyramid, and its height be <code>h</code>. Then, the plot comprises of cells <code>(i, j)</code>
 * 	where <code>r - h + 1 &lt;= i &lt;= r</code> <strong>and</strong> <code>c - (r - i) &lt;= j &lt;= c + (r - i)
 * 	</code>.</li>
 * </ol>
 *
 * <p>Some examples of valid and invalid pyramidal (and inverse pyramidal) plots are shown below. Black cells
 * indicate fertile cells.</p>
 * <img src="https://assets.leetcode.com/uploads/2021/11/08/image.png" style="width: 700px; height: 156px;" />
 * <p>Given a <strong>0-indexed</strong> <code>m x n</code> binary matrix <code>grid</code> representing the
 * farmland, return <em>the <strong>total number</strong> of pyramidal and inverse pyramidal plots that can be found
 * in</em> <code>grid</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/22/1.JPG" style="width: 575px; height: 109px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[0,1,1,0],[1,1,1,1]]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> The 2 possible pyramidal plots are shown in blue and red respectively.
 * There are no inverse pyramidal plots in this grid.
 * Hence total number of pyramidal and inverse pyramidal plots is 2 + 0 = 2.
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/22/2.JPG" style="width: 502px; height: 120px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,1,1],[1,1,1]]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong> The pyramidal plot is shown in blue, and the inverse pyramidal plot is shown in red.
 * Hence the total number of plots is 1 + 1 = 2.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/22/3.JPG" style="width: 676px; height: 148px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,1,1,1,0],[1,1,1,1,1],[1,1,1,1,1],[0,1,0,0,1]]
 * <strong>Output:</strong> 13
 * <strong>Explanation:</strong> There are 7 pyramidal plots, 3 of which are shown in the 2nd and 3rd figures.
 * There are 6 inverse pyramidal plots, 2 of which are shown in the last figure.
 * The total number of plots is 7 + 6 = 13.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>m == grid.length</code></li>
 * 	<li><code>n == grid[i].length</code></li>
 * 	<li><code>1 &lt;= m, n &lt;= 1000</code></li>
 * 	<li><code>1 &lt;= m * n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>grid[i][j]</code> is either <code>0</code> or <code>1</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2088_CountFertilePyramidsInALand {

    /**
     * 通过找出金字塔的一个脚的方式来进行DP查询, 时间复杂度 O(m*m)
     */
    @Answer
    public int countPyramids(int[][] grid) {
        final int m = grid.length;
        final int n = grid[0].length;

        int res = 0;

        // 找出正金字塔的右下角.
        // heights[i][j] 表示以(i,j)为右下角的金字塔最高有多高(1表示就一个塔尖, 2才是有效的金字塔),
        // 这样以 grid[i][j] 为右下角的金字塔就有 heights[i][j]-1 个.
        int[][] heights = new int[m][n];
        System.arraycopy(grid[0], 0, heights[0], 0, n);
        for (int i = 1; i < m; i++) {
            int bottom = grid[i][0];
            heights[i][0] = grid[i][0];
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1) {
                    bottom++;
                    // 金字塔的最高高度限制: 左上角的高度+1, 底部长度限制的高度
                    heights[i][j] = Math.min(heights[i - 1][j - 1] + 1, (bottom + 1) / 2);
                    res += heights[i][j] - 1;
                } else {
                    bottom = 0;
                }
            }
        }

        // 找出倒金字塔的右上角.
        System.arraycopy(grid[m - 1], 0, heights[m - 1], 0, n);
        for (int i = m - 2; i >= 0; i--) {
            int bottom = grid[i][0];
            heights[i][0] = grid[i][0];
            for (int j = 1; j < n; j++) {
                if (grid[i][j] == 1) {
                    bottom++;
                    heights[i][j] = Math.min(heights[i + 1][j - 1] + 1, (bottom + 1) / 2);
                    res += heights[i][j] - 1;
                } else {
                    bottom = 0;
                    heights[i][j] = 0;
                }
            }
        }

        return res;
    }

    /**
     * 比较慢的一种解法, 开始时想出来的.
     * 找金字塔顶的方式来找金字塔个数.
     * 时间复杂度 O(m*m*n), 就算可以通过缓存优化, 也不是很快.
     */
    @Answer
    public int countPyramids_slow(int[][] grid) {
        final int m = grid.length;
        final int n = grid[0].length;

        int[][] sum = new int[m][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sum[i][j + 1] = sum[i][j] + grid[i][j];
            }
        }

        int res = 0;

        // 复用 grid, 避免重复创建, 将grid[i][j] 表示为以 grid[i][j] 为顶部的金字塔的高度

        // 正金字塔
        for (int i = 0; i < m - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                // 以 grid[i][j] 为金字塔顶, 计算有多少个底(能组成多少金字塔)
                // h 表示以 grid[i][j] 为塔顶的最大金字塔高度
                int h = grid[i][j];
                for (; i + h < m; h++) {
                    int[] bottom = sum[i + h];
                    // 底的左右两边
                    int left = j - h;
                    int right = j + h;
                    if (left < 0 || n <= right
                            // grid[bottom][left:right] 都是 1 才行
                            || bottom[right + 1] - bottom[left] != h * 2 + 1) {
                        break;
                    }
                    res++;
                }
            }
        }

        // 倒金字塔
        for (int i = m - 1; i > 0; i--) {
            for (int j = 1; j < n - 1; j++) {
                // 以 grid[i][j] 为金字塔顶, 计算有多少个底(能组成多少金字塔)
                // h 表示以 grid[i][j] 为塔顶的最大金字塔高度
                int h = grid[i][j];
                for (; i - h >= 0; h++) {
                    int[] bottom = sum[i - h];
                    // 底的左右两边
                    int left = j - h;
                    int right = j + h;
                    if (left < 0 || n <= right
                            // grid[bottom][left:right] 都是 1 才行
                            || bottom[right + 1] - bottom[left] != h * 2 + 1) {
                        break;
                    }
                    res++;
                }
            }
        }

        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{0, 1, 1, 0}, {1, 1, 1, 1}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1, 1}, {1, 1, 1}})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 1, 1, 1, 0}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {0, 1, 0, 0, 1}})
            .expect(13);

}
