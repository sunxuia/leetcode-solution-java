package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1314. Matrix Block Sum
 * https://leetcode.com/problems/matrix-block-sum/
 *
 * Given a m * n matrix mat and an integer K, return a matrix answer where each answer[i][j] is the sum of all elements
 * mat[r][c] for i - K <= r <= i + K, j - K <= c <= j + K, and (r, c) is a valid position in the matrix.
 *
 * Example 1:
 *
 * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], K = 1
 * Output: [[12,21,16],[27,45,33],[24,39,28]]
 *
 * Example 2:
 *
 * Input: mat = [[1,2,3],[4,5,6],[7,8,9]], K = 2
 * Output: [[45,45,45],[45,45,45],[45,45,45]]
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n, K <= 100
 * 1 <= mat[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1314_MatrixBlockSum {

    @Answer
    public int[][] matrixBlockSum(int[][] mat, int K) {
        final int m = mat.length, n = mat[0].length;
        int[][] sums = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sums[i + 1][j + 1] = mat[i][j] + sums[i + 1][j] + sums[i][j + 1] - sums[i][j];
            }
        }
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int li = Math.max(0, i - K);
                int lj = Math.max(0, j - K);
                int ri = Math.min(m - 1, i + K);
                int rj = Math.min(n - 1, j + K);
                res[i][j] = sums[ri + 1][rj + 1] - sums[ri + 1][lj] - sums[li][rj + 1] + sums[li][lj];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    }, 1).expect(new int[][]{
            {12, 21, 16},
            {27, 45, 33},
            {24, 39, 28}
    });

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    }, 2).expect(new int[][]{
            {45, 45, 45},
            {45, 45, 45},
            {45, 45, 45}
    });

}
