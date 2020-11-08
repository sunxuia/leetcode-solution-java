package q1300;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1293. Shortest Path in a Grid with Obstacles Elimination
 * https://leetcode.com/problems/shortest-path-in-a-grid-with-obstacles-elimination/
 *
 * Given a m * n grid, where each cell is either 0 (empty) or 1 (obstacle). In one step, you can move up, down, left or
 * right from and to an empty cell.
 *
 * Return the minimum number of steps to walk from the upper left corner (0, 0) to the lower right corner (m-1, n-1)
 * given that you can eliminate at most k obstacles. If it is not possible to find such walk return -1.
 *
 * Example 1:
 *
 * Input:
 * grid =
 * [[0,0,0],
 * [1,1,0],
 * [0,0,0],
 * [0,1,1],
 * [0,0,0]],
 * k = 1
 * Output: 6
 * Explanation:
 * The shortest path without eliminating any obstacle is 10.
 * The shortest path with one obstacle elimination at position (3,2) is 6. Such path is (0,0) -> (0,1) -> (0,2) -> (1,2)
 * -> (2,2) -> (3,2) -> (4,2).
 *
 * Example 2:
 *
 * Input:
 * grid =
 * [[0,1,1],
 * [1,1,1],
 * [1,0,0]],
 * k = 1
 * Output: -1
 * Explanation:
 * We need to eliminate at least two obstacles to find such a walk.
 *
 * Constraints:
 *
 * grid.length == m
 * grid[0].length == n
 * 1 <= m, n <= 40
 * 1 <= k <= m*n
 * grid[i][j] == 0 or 1
 * grid[0][0] == grid[m-1][n-1] == 0
 */
@RunWith(LeetCodeRunner.class)
public class Q1293_ShortestPathInAGridWithObstaclesElimination {

    /**
     * bfs
     */
    @Answer
    public int shortestPath(int[][] grid, int k) {
        final int m = grid.length, n = grid[0].length;
        boolean[][][] visited = new boolean[m][n][k + 1];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{0, 0, 0});
        int res = 0;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                int[] data = queue.poll();
                int i = data[0], j = data[1], clear = data[2];
                if (i == -1 || j == -1 || i == m || j == n
                        || clear == k + 1 || visited[i][j][clear]) {
                    continue;
                }
                if (i == m - 1 && j == n - 1) {
                    return res;
                }
                visited[i][j][clear] = true;
                clear += grid[i][j];
                queue.add(new int[]{i - 1, j, clear});
                queue.add(new int[]{i, j + 1, clear});
                queue.add(new int[]{i + 1, j, clear});
                queue.add(new int[]{i, j - 1, clear});
            }
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{
            {0, 0, 0},
            {1, 1, 0},
            {0, 0, 0},
            {0, 1, 1},
            {0, 0, 0}
    }, 1).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{
            {0, 1, 1},
            {1, 1, 1},
            {1, 0, 0}
    }, 1).expect(-1);

}
