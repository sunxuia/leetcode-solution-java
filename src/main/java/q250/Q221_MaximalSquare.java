package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/maximal-square/
 *
 * Given a 2D binary matrix filled with 0's and 1's, find the largest square containing only 1's and return its area.
 *
 * Example:
 *
 * Input:
 *
 * 1 0 1 0 0
 * 1 0 1 1 1
 * 1 1 1 1 1
 * 1 0 0 1 0
 *
 * Output: 4
 */
@RunWith(LeetCodeRunner.class)
public class Q221_MaximalSquare {

    @Answer
    public int maximalSquare(char[][] matrix) {
        int res = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == '1') {
                    res = Math.max(matrixSize(matrix, i, j), res);
                }
            }
        }
        return res;
    }

    private int matrixSize(char[][] matrix, int i, int j) {
        int extend = 0;
        while (i + extend < matrix.length && j + extend < matrix[i].length) {
            for (int k = i; k <= i + extend; k++) {
                if (matrix[k][j + extend] == '0') {
                    return extend * extend;
                }
            }
            for (int k = j; k < j + extend; k++) {
                if (matrix[i + extend][k] == '0') {
                    return extend * extend;
                }
            }
            extend++;
        }
        return extend * extend;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new char[][]{
            {'1', '0', '1', '0', '0'},
            {'1', '0', '1', '1', '1'},
            {'1', '1', '1', '1', '1'},
            {'1', '0', '0', '1', '0'}
    }).expect(4);

}
