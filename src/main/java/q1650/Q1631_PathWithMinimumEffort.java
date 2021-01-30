package q1650;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1631. Path With Minimum Effort
 * https://leetcode.com/problems/path-with-minimum-effort/
 *
 * You are a hiker preparing for an upcoming hike. You are given heights, a 2D array of size rows x columns, where
 * heights[row][col] represents the height of cell (row, col). You are situated in the top-left cell, (0, 0), and you
 * hope to travel to the bottom-right cell, (rows-1, columns-1) (i.e., 0-indexed). You can move up, down, left, or
 * right, and you wish to find a route that requires the minimum effort.
 *
 * A route's effort is the maximum absolute difference in heights between two consecutive cells of the route.
 *
 * Return the minimum effort required to travel from the top-left cell to the bottom-right cell.
 *
 * Example 1:
 * <img src="./Q1631_PIC1.png">
 * Input: heights = [[1,2,2],[3,8,2],[5,3,5]]
 * Output: 2
 * Explanation: The route of [1,3,5,3,5] has a maximum absolute difference of 2 in consecutive cells.
 * This is better than the route of [1,2,2,2,5], where the maximum absolute difference is 3.
 *
 * Example 2:
 * <img src="./Q1631_PIC2.png">
 * Input: heights = [[1,2,3],[3,8,4],[5,3,5]]
 * Output: 1
 * Explanation: The route of [1,2,3,4,5] has a maximum absolute difference of 1 in consecutive cells, which is better
 * than route [1,3,5,3,5].
 *
 * Example 3:
 * <img src="./Q1631_PIC3.png">
 * Input: heights = [[1,2,1,1,1],[1,2,1,2,1],[1,2,1,2,1],[1,2,1,2,1],[1,1,1,2,1]]
 * Output: 0
 * Explanation: This route does not require any effort.
 *
 * Constraints:
 *
 * rows == heights.length
 * columns == heights[i].length
 * 1 <= rows, columns <= 100
 * 1 <= heights[i][j] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1631_PathWithMinimumEffort {

    /**
     * bfs, 这种方法比较慢.
     */
    @Answer
    public int minimumEffortPath(int[][] heights) {
        final int m = heights.length, n = heights[0].length;
        int[][] cache = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cache[i], Integer.MAX_VALUE);
        }
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int i = pos[0], j = pos[1], effort = pos[2];
            if (cache[i][j] <= effort) {
                continue;
            }
            cache[i][j] = effort;
            for (int[] direction : DIRECTIONS) {
                int ni = i + direction[0];
                int nj = j + direction[1];
                if (-1 < ni && ni < m && -1 < nj && nj < n) {
                    int eff = Math.max(effort, Math.abs(heights[ni][nj] - heights[i][j]));
                    queue.offer(new int[]{ni, nj, eff});
                }
            }
        }
        return cache[m - 1][n - 1];
    }

    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    /**
     * 参考LeetCode 解答, 优先队列的做法
     */
    @Answer
    public int minimumEffortPath2(int[][] heights) {
        final int m = heights.length, n = heights[0].length;
        int[][] cache = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cache[i], Integer.MAX_VALUE);
        }
        // 这里换成了优先队列
        Queue<int[]> queue = new PriorityQueue<>(Comparator.comparingInt(a -> a[2]));
        queue.offer(new int[]{0, 0, 0});
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int i = pos[0], j = pos[1], effort = pos[2];
            if (cache[i][j] <= effort) {
                continue;
            }
            // 因为是优先队列, 所以第一个到达的就是最小的结果
            if (i == m - 1 && j == n - 1) {
                return effort;
            }
            cache[i][j] = effort;
            for (int[] direction : DIRECTIONS) {
                int ni = i + direction[0];
                int nj = j + direction[1];
                if (-1 < ni && ni < m && -1 < nj && nj < n) {
                    int eff = Math.max(effort, Math.abs(heights[ni][nj] - heights[i][j]));
                    queue.offer(new int[]{ni, nj, eff});
                }
            }
        }
        return cache[m - 1][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2, 2},
            {3, 8, 2},
            {5, 3, 5}
    }).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 2, 3},
            {3, 8, 4},
            {5, 3, 5}
    }).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 2, 1, 1, 1},
            {1, 2, 1, 2, 1},
            {1, 2, 1, 2, 1},
            {1, 2, 1, 2, 1},
            {1, 1, 1, 2, 1}
    }).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{{1, 10, 6, 7, 9, 10, 4, 9}}).expect(9);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[][]{
            {8, 3, 2, 5, 2, 10, 7, 1, 8, 9},
            {1, 4, 9, 1, 10, 2, 4, 10, 3, 5},
            {4, 10, 10, 3, 6, 1, 3, 9, 8, 8},
            {4, 4, 6, 10, 10, 10, 2, 10, 8, 8},
            {9, 10, 2, 4, 1, 2, 2, 6, 5, 7},
            {2, 9, 2, 6, 1, 4, 7, 6, 10, 9},
            {8, 8, 2, 10, 8, 2, 3, 9, 5, 3},
            {2, 10, 9, 3, 5, 1, 7, 4, 5, 6},
            {2, 3, 9, 2, 5, 10, 2, 7, 1, 8},
            {9, 10, 4, 10, 7, 4, 9, 3, 1, 6}
    }).expect(5);

    @TestData
    public DataExpectation overTime = DataExpectation.create(TestDataFileHelper.read(int[][].class)).expect(430152);

}
