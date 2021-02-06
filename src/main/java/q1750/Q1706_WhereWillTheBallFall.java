package q1750;

import java.util.stream.IntStream;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1706. Where Will the Ball Fall
 * https://leetcode.com/problems/where-will-the-ball-fall/
 *
 * You have a 2-D grid of size m x n representing a box, and you have n balls. The box is open on the top and bottom
 * sides.
 *
 * Each cell in the box has a diagonal board spanning two corners of the cell that can redirect a ball to the right or
 * to the left.
 *
 * A board that redirects the ball to the right spans the top-left corner to the bottom-right corner and is represented
 * in the grid as 1.
 * A board that redirects the ball to the left spans the top-right corner to the bottom-left corner and is represented
 * in the grid as -1.
 *
 * We drop one ball at the top of each column of the box. Each ball can get stuck in the box or fall out of the bottom.
 * A ball gets stuck if it hits a "V" shaped pattern between two boards or if a board redirects the ball into either
 * wall of the box.
 *
 * Return an array answer of size n where answer[i] is the column that the ball falls out of at the bottom after
 * dropping the ball from the ith column at the top, or -1 if the ball gets stuck in the box.
 *
 * Example 1:
 * <img src="./Q1706_PIC.jpg">
 * Input: grid = [[1,1,1,-1,-1],[1,1,1,-1,-1],[-1,-1,-1,1,1],[1,1,1,1,-1],[-1,-1,-1,-1,-1]]
 * Output: [1,-1,-1,-1,-1]
 * Explanation: This example is shown in the photo.
 * Ball b0 is dropped at column 0 and falls out of the box at column 1.
 * Ball b1 is dropped at column 1 and will get stuck in the box between column 2 and 3 and row 1.
 * Ball b2 is dropped at column 2 and will get stuck on the box between column 2 and 3 and row 0.
 * Ball b3 is dropped at column 3 and will get stuck on the box between column 2 and 3 and row 0.
 * Ball b4 is dropped at column 4 and will get stuck on the box between column 2 and 3 and row 1.
 *
 * Example 2:
 *
 * Input: grid = [[-1]]
 * Output: [-1]
 * Explanation: The ball gets stuck against the left wall.
 *
 * Example 3:
 *
 * Input: grid = [[1,1,1,1,1,1],[-1,-1,-1,-1,-1,-1],[1,1,1,1,1,1],[-1,-1,-1,-1,-1,-1]]
 * Output: [0,1,2,3,4,-1]
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * grid[i][j] is 1 or -1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1706_WhereWillTheBallFall {

    @Answer
    public int[] findBall(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[] res = IntStream.range(0, n).toArray();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (res[j] == -1) {
                    continue;
                }
                if (grid[i][res[j]] == 1) {
                    if (res[j] == n - 1 || grid[i][res[j] + 1] == -1) {
                        res[j] = -1;
                    } else {
                        res[j]++;
                    }
                } else {
                    if (res[j] == 0 || grid[i][res[j] - 1] == 1) {
                        res[j] = -1;
                    } else {
                        res[j]--;
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 1, 1, -1, -1},
            {1, 1, 1, -1, -1},
            {-1, -1, -1, 1, 1},
            {1, 1, 1, 1, -1},
            {-1, -1, -1, -1, -1}
    }).expect(new int[]{1, -1, -1, -1, -1});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{-1}})
            .expect(new int[]{-1});

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1, 1, 1},
            {-1, -1, -1, -1, -1, -1},
            {1, 1, 1, 1, 1, 1},
            {-1, -1, -1, -1, -1, -1}
    }).expect(new int[]{0, 1, 2, 3, 4, -1});

}
