package q1200;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1162. As Far from Land as Possible
 * https://leetcode.com/problems/as-far-from-land-as-possible/
 *
 * Given an N x N grid containing only values 0 and 1, where 0 represents water and 1 represents land, find a water cell
 * such that its distance to the nearest land cell is maximized and return the distance.
 *
 * The distance used in this problem is the Manhattan distance: the distance between two cells (x0, y0) and (x1, y1) is
 * |x0 - x1| + |y0 - y1|.
 *
 * If no land or water exists in the grid, return -1.
 *
 * Example 1:
 * <img src="Q1162_PIC1.jpg">
 * Input: [[1,0,1],[0,0,0],[1,0,1]]
 * Output: 2
 * Explanation:
 * The cell (1, 1) is as far as possible from all the land with distance 2.
 *
 * Example 2:
 * <img src="Q1162_PIC2.jpg">
 * Input: [[1,0,0],[0,0,0],[0,0,0]]
 * Output: 4
 * Explanation:
 * The cell (2, 2) is as far as possible from all the land with distance 4.
 *
 * Note:
 *
 * 1 <= grid.length == grid[0].length <= 100
 * grid[i][j] is 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1162_AsFarFromLandAsPossible {

    @Answer
    public int maxDistance(int[][] grid) {
        final int n = grid.length;
        int[][] dist = new int[n][n];
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    addNeighbor(queue, i, j);
                }
            }
        }

        for (int d = 1; !queue.isEmpty(); d++) {
            for (int len = queue.size(); len > 0; len -= 2) {
                int i = queue.poll(), j = queue.poll();
                if (i == -1 || j == -1 || i == n || j == n
                        || grid[i][j] == 1 || dist[i][j] != 0) {
                    continue;
                }
                dist[i][j] = d;
                addNeighbor(queue, i, j);
            }
        }

        int res = -1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0 && dist[i][j] > 0) {
                    res = Math.max(res, dist[i][j]);
                }
            }
        }
        return res;
    }

    private void addNeighbor(Queue<Integer> queue, int i, int j) {
        queue.add(i + 1);
        queue.add(j);
        queue.add(i);
        queue.add(j - 1);
        queue.add(i - 1);
        queue.add(j);
        queue.add(i);
        queue.add(j + 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 0, 1},
            {0, 0, 0},
            {1, 0, 1}
    }).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    }).expect(4);

    @TestData
    public DataExpectation border0 = DataExpectation.create(new int[][]{
            {0, 0, 0},
            {0, 0, 0},
            {0, 0, 0}
    }).expect(-1);

    @TestData
    public DataExpectation border1 = DataExpectation.create(new int[][]{
            {1, 1, 1},
            {1, 1, 1},
            {1, 1, 1}
    }).expect(-1);

}
