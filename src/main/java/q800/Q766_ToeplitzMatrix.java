package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/toeplitz-matrix/
 *
 * A matrix is Toeplitz if every diagonal from top-left to bottom-right has the same element.
 *
 * Now given an M x N matrix, return True if and only if the matrix is Toeplitz.
 *
 *
 * Example 1:
 *
 * Input:
 * matrix = [
 * [1,2,3,4],
 * [5,1,2,3],
 * [9,5,1,2]
 * ]
 * Output: True
 * Explanation:
 * In the above grid, the diagonals are:
 * "[9]", "[5, 5]", "[1, 1, 1]", "[2, 2, 2]", "[3, 3]", "[4]".
 * In each diagonal all elements are the same, so the answer is True.
 *
 * Example 2:
 *
 * Input:
 * matrix = [
 * [1,2],
 * [2,2]
 * ]
 * Output: False
 * Explanation:
 * The diagonal "[1, 2]" has different elements.
 *
 *
 * Note:
 *
 * matrix will be a 2D array of integers.
 * matrix will have a number of rows and columns in range [1, 20].
 * matrix[i][j] will be integers in range [0, 99].
 *
 *
 * Follow up:
 *
 * 1. What if the matrix is stored on disk, and the memory is limited such that you can only load at most one row of
 * the matrix into the memory at once?
 * 2. What if the matrix is so large that you can only load up a partial row into the memory at once?
 */
@RunWith(LeetCodeRunner.class)
public class Q766_ToeplitzMatrix {

    @Answer
    public boolean isToeplitzMatrix(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        for (int i = 0; i < m; i++) {
            for (int y = i + 1, x = 1; y < m && x < n; y++, x++) {
                if (matrix[y][x] != matrix[y - 1][x - 1]) {
                    return false;
                }
            }
        }
        for (int i = 1; i < n; i++) {
            for (int y = 1, x = i + 1; y < m && x < n; y++, x++) {
                if (matrix[y][x] != matrix[y - 1][x - 1]) {
                    return false;
                }
            }
        }
        return true;
    }

    // 不用从对角线去找, 直接遍历也可以.
    @Answer
    public boolean isToeplitzMatrix2(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (matrix[i - 1][j - 1] != matrix[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(new int[][]{
            {1, 2, 3, 4},
            {5, 1, 2, 3},
            {9, 5, 1, 2}
    }).expect(true);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(new int[][]{
            {1, 2},
            {2, 2}
    }).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 2, 3}
    }).expect(true);

}
