package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/set-matrix-zeroes/
 *
 * Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
 *
 * Example 1:
 *
 * Input:
 * [
 * [1,1,1],
 * [1,0,1],
 * [1,1,1]
 * ]
 * Output:
 * [
 * [1,0,1],
 * [0,0,0],
 * [1,0,1]
 * ]
 * Example 2:
 *
 * Input:
 * [
 * [0,1,2,0],
 * [3,4,5,2],
 * [1,3,1,5]
 * ]
 * Output:
 * [
 * [0,0,0,0],
 * [0,4,5,0],
 * [0,3,1,0]
 * ]
 * Follow up:
 *
 * A straight forward solution using O(mn) space is probably a bad idea.
 * A simple improvement uses O(m + n) space, but still not the best solution.
 * Could you devise a constant space solution?
 */
@RunWith(LeetCodeRunner.class)
public class Q073_SetMatrixZeroes {

    /**
     * 如果有 1 个值为 0, 就将该行和该列的所有值改为 0.
     * 根据题目的要求, 使用 O(1) 的空间占用, 改进下面的O(m+n) 的解法, 使用第一行和第一列存储标识.
     */
    @Answer
    public void setZeroes(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        boolean firstRowZero = false, firstColumnZero = false;
        for (int i = 0; i < n && !firstRowZero; i++) {
            firstRowZero = matrix[0][i] == 0;
        }
        for (int i = 0; i < m && !firstColumnZero; i++) {
            firstColumnZero = matrix[i][0] == 0;
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i][0] == 0 || matrix[0][j] == 0) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (firstRowZero) {
            for (int i = 0; i < n; i++) {
                matrix[0][i] = 0;
            }
        }
        if (firstColumnZero) {
            for (int i = 0; i < m; i++) {
                matrix[i][0] = 0;
            }
        }
    }

    // O(m+n) 空间占用的解法
    @Answer
    public void setZeroesOMN(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        boolean[] row = new boolean[m], column = new boolean[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    row[i] = true;
                    column[j] = true;
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (row[i] || column[j]) {
                    matrix[i][j] = 0;
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 1, 1},
                    {1, 0, 1},
                    {1, 1, 1}
            }).expectArgument(0, new int[][]{
                    {1, 0, 1},
                    {0, 0, 0},
                    {1, 0, 1}
            }).build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {0, 1, 2, 0},
                    {3, 4, 5, 2},
                    {1, 3, 1, 5}
            }).expectArgument(0, new int[][]{
                    {0, 0, 0, 0},
                    {0, 4, 5, 0},
                    {0, 3, 1, 0}
            }).build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {0, 1}
            }).expectArgument(0, new int[][]{
                    {0, 0}
            }).build();

}
