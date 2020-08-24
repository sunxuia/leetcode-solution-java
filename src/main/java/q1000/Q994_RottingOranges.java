package q1000;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 994. Rotting Oranges
 * https://leetcode.com/problems/rotting-oranges/
 *
 * In a given grid, each cell can have one of three values:
 *
 * the value 0 representing an empty cell;
 * the value 1 representing a fresh orange;
 * the value 2 representing a rotten orange.
 *
 * Every minute, any fresh orange that is adjacent (4-directionally) to a rotten orange becomes rotten.
 *
 * Return the minimum number of minutes that must elapse until no cell has a fresh orange.  If this is impossible,
 * return -1 instead.
 *
 * Example 1:
 * (图Q994_PIC.png)
 * Input: [[2,1,1],[1,1,0],[0,1,1]]
 * Output: 4
 *
 * Example 2:
 *
 * Input: [[2,1,1],[0,1,1],[1,0,1]]
 * Output: -1
 * Explanation:  The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens
 * 4-directionally.
 *
 * Example 3:
 *
 * Input: [[0,2]]
 * Output: 0
 * Explanation:  Since there are already no fresh oranges at minute 0, the answer is just 0.
 *
 * Note:
 *
 * 1 <= grid.length <= 10
 * 1 <= grid[0].length <= 10
 * grid[i][j] is only 0, 1, or 2.
 */
@RunWith(LeetCodeRunner.class)
public class Q994_RottingOranges {

    @Answer
    public int orangesRotting(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 2) {
                    grid[i][j] = 1;
                    queue.add(i);
                    queue.add(j);
                }
            }
        }

        int res = -2;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len -= 2) {
                int i = queue.remove();
                int j = queue.remove();
                if (i == -1 || i == m || j == -1 || j == n
                        || grid[i][j] != 1) {
                    continue;
                }
                grid[i][j] = 2;
                queue.add(i + 1);
                queue.add(j);
                queue.add(i);
                queue.add(j - 1);
                queue.add(i - 1);
                queue.add(j);
                queue.add(i);
                queue.add(j + 1);
            }
            res++;
        }
        res = Math.max(res, 0);

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    return -1;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {2, 1, 1},
            {1, 1, 0},
            {0, 1, 1}
    }).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {2, 1, 1},
            {0, 1, 1},
            {1, 0, 1}
    }).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{0, 2}}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[1][1]).expect(0);

}
