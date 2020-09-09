package q1050;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1030. Matrix Cells in Distance Order
 * https://leetcode.com/problems/matrix-cells-in-distance-order/
 *
 * We are given a matrix with R rows and C columns has cells with integer coordinates (r, c), where 0 <= r < R and 0 <=
 * c < C.
 *
 * Additionally, we are given a cell in that matrix with coordinates (r0, c0).
 *
 * Return the coordinates of all cells in the matrix, sorted by their distance from (r0, c0) from smallest distance to
 * largest distance.  Here, the distance between two cells (r1, c1) and (r2, c2) is the Manhattan distance, |r1 - r2| +
 * |c1 - c2|.  (You may return the answer in any order that satisfies this condition.)
 *
 * Example 1:
 *
 * Input: R = 1, C = 2, r0 = 0, c0 = 0
 * Output: [[0,0],[0,1]]
 * Explanation: The distances from (r0, c0) to other cells are: [0,1]
 *
 * Example 2:
 *
 * Input: R = 2, C = 2, r0 = 0, c0 = 1
 * Output: [[0,1],[0,0],[1,1],[1,0]]
 * Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2]
 * The answer [[0,1],[1,1],[0,0],[1,0]] would also be accepted as correct.
 *
 * Example 3:
 *
 * Input: R = 2, C = 3, r0 = 1, c0 = 2
 * Output: [[1,2],[0,2],[1,1],[0,1],[1,0],[0,0]]
 * Explanation: The distances from (r0, c0) to other cells are: [0,1,1,2,2,3]
 * There are other answers that would also be accepted as correct, such as [[1,2],[1,1],[0,2],[1,0],[0,1],[0,0]].
 *
 * Note:
 *
 * 1 <= R <= 100
 * 1 <= C <= 100
 * 0 <= r0 < R
 * 0 <= c0 < C
 */
@RunWith(LeetCodeRunner.class)
public class Q1030_MatrixCellsInDistanceOrder {

    @Answer
    public int[][] allCellsDistOrder(int R, int C, int r0, int c0) {
        final int[][] directions = new int[][]{{1, 1}, {-1, 1}, {-1, -1}, {1, -1}};
        int[][] res = new int[R * C][];
        int r = r0, c = c0, dist = 0, steps = -1, next = 0;
        while (next < res.length) {
            if (0 <= r && r < R && 0 <= c && c < C) {
                res[next++] = new int[]{r, c};
            }
            if (steps == dist * 4 - 1) {
                steps = 0;
                dist++;
                r = r0;
                c = c0 - dist;
            } else {
                r += directions[steps / dist][0];
                c += directions[steps / dist][1];
                steps++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createDataExpectation(1, 2, 0, 0);

    private DataExpectation createDataExpectation(int R, int C, int r0, int c0) {
        boolean[][] visited = new boolean[R][C];
        return DataExpectation.builder()
                .addArgument(R).addArgument(C).addArgument(r0).addArgument(c0)
                .assertMethod((int[][] actual) -> {
                    Assert.assertEquals(R * C, actual.length);
                    int prevDist = 0;
                    for (int i = 0; i < actual.length; i++) {
                        int r = actual[i][0], c = actual[i][1];
                        Assert.assertFalse(visited[r][c]);
                        visited[r][c] = true;
                        int dist = Math.abs(r - r0) + Math.abs(c - c0);
                        Assert.assertTrue(prevDist <= dist);
                        prevDist = dist;
                    }
                }).build();
    }

    @TestData
    public DataExpectation example2 = createDataExpectation(2, 2, 0, 1);

    @TestData
    public DataExpectation example3 = createDataExpectation(2, 3, 1, 2);

}
