package q1400;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1391. Check if There is a Valid Path in a Grid
 * https://leetcode.com/problems/check-if-there-is-a-valid-path-in-a-grid/
 *
 * Given a m x n grid. Each cell of the grid represents a street. The street of grid[i][j] can be:
 *
 * 1 which means a street connecting the left cell and the right cell.
 * 2 which means a street connecting the upper cell and the lower cell.
 * 3 which means a street connecting the left cell and the lower cell.
 * 4 which means a street connecting the right cell and the lower cell.
 * 5 which means a street connecting the left cell and the upper cell.
 * 6 which means a street connecting the right cell and the upper cell.
 *
 * You will initially start at the street of the upper-left cell (0,0). A valid path in the grid is a path which starts
 * from the upper left cell (0,0) and ends at the bottom-right cell (m - 1, n - 1). The path should only follow the
 * streets.
 *
 * Notice that you are not allowed to change any street.
 *
 * Return true if there is a valid path in the grid or false otherwise.
 *
 * Example 1:
 *
 * Input: grid = [[2,4,3],[6,5,2]]
 * Output: true
 * Explanation: As shown you can start at cell (0, 0) and visit all the cells of the grid to reach (m - 1, n - 1).
 *
 * Example 2:
 *
 * Input: grid = [[1,2,1],[1,2,1]]
 * Output: false
 * Explanation: As shown you the street at cell (0, 0) is not connected with any street of any other cell and you will
 * get stuck at cell (0, 0)
 *
 * Example 3:
 *
 * Input: grid = [[1,1,2]]
 * Output: false
 * Explanation: You will get stuck at cell (0, 1) and you cannot reach cell (0, 2).
 *
 * Example 4:
 *
 * Input: grid = [[1,1,1,1,1,1,3]]
 * Output: true
 *
 * Example 5:
 *
 * Input: grid = [[2],[2],[2],[2],[2],[2],[6]]
 * Output: true
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 300
 * 1 <= grid[i][j] <= 6
 */
@RunWith(LeetCodeRunner.class)
public class Q1391_CheckIfThereIsAValidPathInAGrid {

    @Answer
    public boolean hasValidPath(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        boolean[][] visited = new boolean[m][n];
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        queue.offer(0);
        visited[0][0] = true;
        while (!queue.isEmpty()) {
            int ci = queue.poll();
            int cj = queue.poll();
            if (ci == m - 1 && cj == n - 1) {
                return true;
            }
            for (int[] next : FROM_TO[grid[ci][cj]]) {
                int ni = ci + next[0];
                int nj = cj + next[1];
                if (-1 == ni || ni == m || -1 == nj || nj == n
                        || visited[ni][nj]) {
                    continue;
                }
                int[] to0 = FROM_TO[grid[ni][nj]][0];
                int[] to1 = FROM_TO[grid[ni][nj]][1];
                if (ni + to0[0] == ci && nj + to0[1] == cj
                        || ni + to1[0] == ci && nj + to1[1] == cj) {
                    visited[ni][nj] = true;
                    queue.offer(ni);
                    queue.offer(nj);
                }
            }
        }
        return false;
    }

    private static final int[][][] FROM_TO = new int[][][]{
            null,
            {{0, -1}, {0, 1}},
            {{-1, 0}, {1, 0}},
            {{0, -1}, {1, 0}},
            {{0, 1}, {1, 0}},
            {{0, -1}, {-1, 0}},
            {{0, 1}, {-1, 0}},
    };

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {2, 4, 3},
            {6, 5, 2}
    }).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 2, 1},
            {1, 2, 1}
    }).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 2}
    }).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1, 1, 1, 3}
    }).expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{
            {2},
            {2},
            {2},
            {2},
            {2},
            {2},
            {6}
    }).expect(true);

}
