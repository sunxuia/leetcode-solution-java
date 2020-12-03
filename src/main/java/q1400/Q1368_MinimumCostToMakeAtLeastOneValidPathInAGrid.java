package q1400;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1368. Minimum Cost to Make at Least One Valid Path in a Grid
 * https://leetcode.com/problems/minimum-cost-to-make-at-least-one-valid-path-in-a-grid/
 *
 * Given a m x n grid. Each cell of the grid has a sign pointing to the next cell you should visit if you are currently
 * in this cell. The sign of grid[i][j] can be:
 *
 * 1 which means go to the cell to the right. (i.e go from grid[i][j] to grid[i][j + 1])
 * 2 which means go to the cell to the left. (i.e go from grid[i][j] to grid[i][j - 1])
 * 3 which means go to the lower cell. (i.e go from grid[i][j] to grid[i + 1][j])
 * 4 which means go to the upper cell. (i.e go from grid[i][j] to grid[i - 1][j])
 *
 * Notice that there could be some invalid signs on the cells of the grid which points outside the grid.
 *
 * You will initially start at the upper left cell (0,0). A valid path in the grid is a path which starts from the upper
 * left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1) following the signs on the grid. The valid path
 * doesn't have to be the shortest.
 *
 * You can modify the sign on a cell with cost = 1. You can modify the sign on a cell one time only.
 *
 * Return the minimum cost to make the grid have at least one valid path.
 *
 * Example 1:
 * <img src="./Q1368_PIC1.png">
 * Input: grid = [[1,1,1,1],[2,2,2,2],[1,1,1,1],[2,2,2,2]]
 * Output: 3
 * Explanation: You will start at point (0, 0).
 * The path to (3, 3) is as follows. (0, 0) --> (0, 1) --> (0, 2) --> (0, 3) change the arrow to down with cost = 1 -->
 * (1, 3) --> (1, 2) --> (1, 1) --> (1, 0) change the arrow to down with cost = 1 --> (2, 0) --> (2, 1) --> (2, 2) -->
 * (2, 3) change the arrow to down with cost = 1 --> (3, 3)
 * The total cost = 3.
 *
 * Example 2:
 * <img src="./Q1368_PIC2.png">
 * Input: grid = [[1,1,3],[3,2,2],[1,1,4]]
 * Output: 0
 * Explanation: You can follow the path from (0, 0) to (2, 2).
 *
 * Example 3:
 * <img src="./Q1368_PIC3.png">
 * Input: grid = [[1,2],[4,3]]
 * Output: 1
 *
 * Example 4:
 *
 * Input: grid = [[2,2,2],[2,2,2]]
 * Output: 3
 *
 * Example 5:
 *
 * Input: grid = [[4]]
 * Output: 0
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1368_MinimumCostToMakeAtLeastOneValidPathInAGrid {

    /**
     * 1→, 2←, 3↓, 4↑
     * bfs
     */
    @Answer
    public int minCost(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Deque<int[]> queue = new ArrayDeque<>();
        for (int[] pos = {0, 0};
                pos != null;
                pos = next(visited, pos, grid[pos[0]][pos[1]])) {
            visited[pos[0]][pos[1]] = true;
            queue.offer(pos);
        }

        int cost = 0;
        while (!queue.isEmpty() && !visited[m - 1][n - 1]) {
            for (int len = queue.size(); len > 0; len--) {
                int[] curr = queue.poll();
                for (int dir = 1; dir <= 4; dir++) {
                    int[] pos = next(visited, curr, dir);
                    while (pos != null) {
                        visited[pos[0]][pos[1]] = true;
                        queue.offer(pos);
                        pos = next(visited, pos, grid[pos[0]][pos[1]]);
                    }
                }
            }
            cost++;
        }
        return cost;
    }

    private int[] next(boolean[][] visited, int[] pos, int direction) {
        int i = pos[0], j = pos[1];
        if (direction == 1) {
            j++;
        } else if (direction == 2) {
            j--;
        } else if (direction == 3) {
            i++;
        } else {
            i--;
        }
        if (0 <= i && i < visited.length
                && 0 <= j && j < visited[0].length
                && !visited[i][j]) {
            return new int[]{i, j};
        }
        return null;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1},
            {2, 2, 2, 2},
            {1, 1, 1, 1},
            {2, 2, 2, 2}
    }).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 1, 3},
            {3, 2, 2},
            {1, 1, 4}
    }).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 2},
            {4, 3}
    }).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {2, 2, 2},
            {2, 2, 2}
    }).expect(3);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{{4}}).expect(0);

}
