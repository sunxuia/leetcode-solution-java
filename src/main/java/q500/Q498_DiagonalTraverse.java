package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/diagonal-traverse/
 *
 * Given a matrix of M x N elements (M rows, N columns), return all elements of the matrix in diagonal order as shown
 * in the below image.
 *
 *
 * Example:
 *
 * Input:
 * [
 * [ 1, 2, 3 ],
 * [ 4, 5, 6 ],
 * [ 7, 8, 9 ]
 * ]
 *
 * Output:  [1,2,4,7,5,3,6,8,9]
 *
 * Explanation:
 *
 * (图 Q498_PIC.png)
 *
 * Note:
 *
 * The total number of elements of the given matrix will not exceed 10,000.
 */
@RunWith(LeetCodeRunner.class)
public class Q498_DiagonalTraverse {

    @Answer
    public int[] findDiagonalOrder(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return new int[0];
        }
        int m = matrix.length, n = matrix[0].length;
        int[] res = new int[m * n];
        int y = 0, x = 0;
        for (int i = 0; i < m * n; i++) {
            res[i] = matrix[y][x];

            if ((x + y) % 2 == 0) {
                // 向上运动
                if (x == n - 1) {
                    y++;
                } else if (y == 0) {
                    x++;
                } else {
                    x++;
                    y--;
                }
            } else {
                // 向下运动
                if (y == m - 1) {
                    x++;
                } else if (x == 0) {
                    y++;
                } else {
                    x--;
                    y++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.create(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    }).expect(new int[]{1, 2, 4, 7, 5, 3, 6, 8, 9});

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {2, 5, 8},
            {4, 0, -1}
    }).expect(new int[]{2, 5, 4, 0, 8, -1});

}
