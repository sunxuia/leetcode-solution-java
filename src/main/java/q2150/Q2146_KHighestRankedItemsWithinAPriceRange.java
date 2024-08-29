package q2150;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2146. K Highest Ranked Items Within a Price Range</h3>
 * <a href="https://leetcode.com/problems/k-highest-ranked-items-within-a-price-range/">
 * https://leetcode.com/problems/k-highest-ranked-items-within-a-price-range/
 * </a><br/>
 *
 * <p>You are given a <strong>0-indexed</strong> 2D integer array <code>grid</code> of size <code>m x n</code> that
 * represents a map of the items in a shop. The integers in the grid represent the following:</p>
 *
 * <ul>
 * 	<li><code>0</code> represents a wall that you cannot pass through.</li>
 * 	<li><code>1</code> represents an empty cell that you can freely move to and from.</li>
 * 	<li>All other positive integers represent the price of an item in that cell. You may also freely move to and from
 * 	these item cells.</li>
 * </ul>
 *
 * <p>It takes <code>1</code> step to travel between adjacent grid cells.</p>
 *
 * <p>You are also given integer arrays <code>pricing</code> and <code>start</code> where <code>pricing = [low,
 * high]</code> and <code>start = [row, col]</code> indicates that you start at the position <code>(row, col)</code>
 * and are interested only in items with a price in the range of <code>[low, high]</code>
 * (<strong>inclusive</strong>). You are further given an integer <code>k</code>.</p>
 *
 * <p>You are interested in the <strong>positions</strong> of the <code>k</code> <strong>highest-ranked</strong>
 * items whose prices are <strong>within</strong> the given price range. The rank is determined by the
 * <strong>first</strong> of these criteria that is different:</p>
 *
 * <ol>
 * 	<li>Distance, defined as the length of the shortest path from the <code>start</code> (<strong>shorter</strong>
 * 	distance has a higher rank).</li>
 * 	<li>Price (<strong>lower</strong> price has a higher rank, but it must be <strong>in the price range</strong>)
 * 	.</li>
 * 	<li>The row number (<strong>smaller</strong> row number has a higher rank).</li>
 * 	<li>The column number (<strong>smaller</strong> column number has a higher rank).</li>
 * </ol>
 *
 * <p>Return <em>the </em><code>k</code><em> highest-ranked items within the price range <strong>sorted</strong> by
 * their rank (highest to lowest)</em>. If there are fewer than <code>k</code> reachable items within the price
 * range, return <em><strong>all</strong> of them</em>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/16/example1drawio.png" style="width: 200px; height:
 * 151px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,2,0,1],[1,3,0,1],[0,2,5,1]], pricing = [2,5], start = [0,0], k = 3
 * <strong>Output:</strong> [[0,1],[1,1],[2,1]]
 * <strong>Explanation:</strong> You start at (0,0).
 * With a price range of [2,5], we can take items from (0,1), (1,1), (2,1) and (2,2).
 * The ranks of these items are:
 * - (0,1) with distance 1
 * - (1,1) with distance 2
 * - (2,1) with distance 3
 * - (2,2) with distance 4
 * Thus, the 3 highest ranked items in the price range are (0,1), (1,1), and (2,1).
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/16/example2drawio1.png" style="width: 200px; height:
 * 151px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,2,0,1],[1,3,3,1],[0,2,5,1]], pricing = [2,3], start = [2,3], k = 2
 * <strong>Output:</strong> [[2,1],[1,2]]
 * <strong>Explanation:</strong> You start at (2,3).
 * With a price range of [2,3], we can take items from (0,1), (1,1), (1,2) and (2,1).
 * The ranks of these items are:
 * - (2,1) with distance 2, price 2
 * - (1,2) with distance 2, price 3
 * - (1,1) with distance 3
 * - (0,1) with distance 4
 * Thus, the 2 highest ranked items in the price range are (2,1) and (1,2).
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/12/30/example3.png" style="width: 149px; height: 150px;" />
 * <pre>
 * <strong>Input:</strong> grid = [[1,1,1],[0,0,1],[2,3,4]], pricing = [2,3], start = [0,0], k = 3
 * <strong>Output:</strong> [[2,1],[2,0]]
 * <strong>Explanation:</strong> You start at (0,0).
 * With a price range of [2,3], we can take items from (2,0) and (2,1).
 * The ranks of these items are:
 * - (2,1) with distance 5
 * - (2,0) with distance 6
 * Thus, the 2 highest ranked items in the price range are (2,1) and (2,0).
 * Note that k = 3 but there are only 2 reachable items within the price range.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>m == grid.length</code></li>
 * 	<li><code>n == grid[i].length</code></li>
 * 	<li><code>1 &lt;= m, n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= m * n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>0 &lt;= grid[i][j] &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>pricing.length == 2</code></li>
 * 	<li><code>2 &lt;= low &lt;= high &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>start.length == 2</code></li>
 * 	<li><code>0 &lt;= row &lt;= m - 1</code></li>
 * 	<li><code>0 &lt;= col &lt;= n - 1</code></li>
 * 	<li><code>grid[row][col] &gt; 0</code></li>
 * 	<li><code>1 &lt;= k &lt;= m * n</code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2146_KHighestRankedItemsWithinAPriceRange {

    @Answer
    public List<List<Integer>> highestRankedKItems(int[][] grid, int[] pricing, int[] start, int k) {
        final int m = grid.length;
        final int n = grid[0].length;
        final int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};

        List<List<Integer>> res = new ArrayList<>(k);
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(start);
        grid[start[0]][start[1]] *= -1;

        while (!queue.isEmpty() && res.size() < k) {
            List<int[]> matched = new ArrayList<>();
            // 走向相邻路径
            for (int i = queue.size(); i > 0; i--) {
                int[] pos = queue.poll();
                int price = -grid[pos[0]][pos[1]];
                if (pricing[0] <= price && price <= pricing[1]) {
                    matched.add(pos);
                }
                for (int[] dir : dirs) {
                    int y = pos[0] + dir[0];
                    int x = pos[1] + dir[1];
                    if (0 <= y && y < m
                            && 0 <= x && x < n
                            && grid[y][x] > 0) {
                        queue.offer(new int[]{y, x});
                        // 设置为负数表示已经来过了
                        grid[y][x] = -grid[y][x];
                    }
                }
            }
            // 本轮循环距离相同, 因此不参与排序
            matched.sort((a, b) -> {
                int ay = a[0], ax = a[1];
                int by = b[0], bx = b[1];
                if (grid[ay][ax] != grid[by][bx]) {
                    // 因为价格都被值为负数了, 所以要取反
                    return grid[by][bx] - grid[ay][ax];
                }
                if (ay != by) {
                    return ay - by;
                }
                return ax - bx;
            });
            for (int i = 0; i < matched.size() && res.size() < k; i++) {
                int[] pos = matched.get(i);
                res.add(List.of(pos[0], pos[1]));
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {1, 2, 0, 1},
                    {1, 3, 0, 1},
                    {0, 2, 5, 1}
            }, new int[]{2, 5}, new int[]{0, 0}, 3)
            .expect(List.of(List.of(0, 1), List.of(1, 1), List.of(2, 1)));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                    {1, 2, 0, 1},
                    {1, 3, 3, 1},
                    {0, 2, 5, 1}
            }, new int[]{2, 3}, new int[]{2, 3}, 2)
            .expect(List.of(List.of(2, 1), List.of(1, 2)));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{
                    {1, 1, 1},
                    {0, 0, 1},
                    {2, 3, 4}
            }, new int[]{2, 3}, new int[]{0, 0}, 3)
            .expect(List.of(List.of(2, 1), List.of(2, 0)));

}
