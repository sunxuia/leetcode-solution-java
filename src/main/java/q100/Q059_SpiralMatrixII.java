package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/spiral-matrix-ii/
 *
 * Given a positive integer n, generate a square matrix filled with elements from 1 to n2 in spiral order.
 *
 * Example:
 *
 * Input: 3
 * Output:
 * [
 * [ 1, 2, 3 ],
 * [ 8, 9, 4 ],
 * [ 7, 6, 5 ]
 * ]
 */
@RunWith(LeetCodeRunner.class)
public class Q059_SpiralMatrixII {

    @Answer
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int x = -1, y = 0, dx = 1, dy = 0, parse = 0, round = 1;
        for (int i = 1; i <= n * n; i++) {
            x += dx;
            y += dy;
            res[y][x] = i;

            switch (parse) {
                case 0:
                    if (x == n - round) {
                        dx = 0;
                        dy = 1;
                        parse = 1;
                    }
                    break;
                case 1:
                    if (y == n - round) {
                        dx = -1;
                        dy = 0;
                        parse = 2;
                    }
                    break;
                case 2:
                    if (x == round - 1) {
                        dx = 0;
                        dy = -1;
                        parse = 3;
                    }
                    break;
                default:
                    if (y == round) {
                        dx = 1;
                        dy = 0;
                        round++;
                        parse = 0;
                    }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(3)
            .expect(new int[][]{
                    {1, 2, 3},
                    {8, 9, 4},
                    {7, 6, 5}
            });
}
