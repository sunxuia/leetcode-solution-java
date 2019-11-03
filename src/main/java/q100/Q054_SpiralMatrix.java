package q100;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/spiral-matrix/
 *
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.
 *
 * Example 1:
 *
 * Input:
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 * Output: [1,2,3,6,9,8,7,4,5]
 * Example 2:
 *
 * Input:
 * [
 * [1, 2, 3, 4],
 * [5, 6, 7, 8],
 * [9,10,11,12]
 * ]
 * Output: [1,2,3,4,8,12,11,10,9,5,6,7]
 */
@RunWith(LeetCodeRunner.class)
public class Q054_SpiralMatrix {

    @Answer
    public List<Integer> spiralOrder(int[][] matrix) {
        if (matrix.length == 0) {
            return Collections.emptyList();
        }
        final int m = matrix.length, n = matrix[0].length;
        List<Integer> res = new ArrayList<>(m * n);
        int[] dx = new int[]{1, 0, -1, 0}, dy = new int[]{0, 1, 0, -1};
        int x = -1, y = 0, parse = 0, round = 0;
        for (int i = 0; i < m * n; i++) {
            x = x + dx[parse];
            y = y + dy[parse];
            res.add(matrix[y][x]);

            if (parse == 0 && x == n - round - 1) {
                parse = 1;
            } else if (parse == 1 && y == m - round - 1) {
                parse = 2;
            } else if (parse == 2 && x == round) {
                parse = 3;
            } else if (parse == 3 && y == round + 1) {
                parse = 0;
                round++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            }).expect(new int[]{1, 2, 3, 6, 9, 8, 7, 4, 5})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12}
            }).expect(new int[]{1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7})
            .build();

}
