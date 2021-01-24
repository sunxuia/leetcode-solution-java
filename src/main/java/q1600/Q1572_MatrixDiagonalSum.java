package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1572. Matrix Diagonal Sum
 * https://leetcode.com/problems/matrix-diagonal-sum/
 *
 * Given a square matrix mat, return the sum of the matrix diagonals.
 *
 * Only include the sum of all the elements on the primary diagonal and all the elements on the secondary diagonal that
 * are not part of the primary diagonal.
 *
 * Example 1:
 * <img src="./!1573_PIC.png">
 * Input: mat = [[1,2,3],
 * [4,5,6],
 * [7,8,9]]
 * Output: 25
 * Explanation: Diagonals sum: 1 + 5 + 9 + 3 + 7 = 25
 * Notice that element mat[1][1] = 5 is counted only once.
 *
 * Example 2:
 *
 * Input: mat = [[1,1,1,1],
 * [1,1,1,1],
 * [1,1,1,1],
 * [1,1,1,1]]
 * Output: 8
 *
 * Example 3:
 *
 * Input: mat = [[5]]
 * Output: 5
 *
 * Constraints:
 *
 * n == mat.length == mat[i].length
 * 1 <= n <= 100
 * 1 <= mat[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1572_MatrixDiagonalSum {

    @Answer
    public int diagonalSum(int[][] mat) {
        final int n = mat.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            res += mat[i][i];
        }
        for (int i = 0; i < n; i++) {
            res += mat[i][n - 1 - i];
        }
        if (n % 2 == 1) {
            res -= mat[n / 2][n / 2];
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    }).expect(25);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1},
            {1, 1, 1, 1}
    }).expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{5}}).expect(5);

}
