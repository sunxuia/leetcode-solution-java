package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1582. Special Positions in a Binary Matrix
 * https://leetcode.com/problems/special-positions-in-a-binary-matrix/
 *
 * Given a rows x cols matrix mat, where mat[i][j] is either 0 or 1, return the number of special positions in mat.
 *
 * A position (i,j) is called special if mat[i][j] == 1 and all other elements in row i and column j are 0 (rows and
 * columns are 0-indexed).
 *
 * Example 1:
 *
 * Input: mat = [
 * [1,0,0],
 * [0,0,1],
 * [1,0,0]]
 * Output: 1
 * Explanation: (1,2) is a special position because mat[1][2] == 1 and all other elements in row 1 and column 2 are 0.
 *
 * Example 2:
 *
 * Input: mat = [
 * [1,0,0],
 * [0,1,0],
 * [0,0,1]]
 * Output: 3
 * Explanation: (0,0), (1,1) and (2,2) are special positions.
 *
 * Example 3:
 *
 * Input: mat = [
 * [0,0,0,1],
 * [1,0,0,0],
 * [0,1,1,0],
 * [0,0,0,0]]
 * Output: 2
 *
 * Example 4:
 *
 * Input: mat = [
 * [0,0,0,0,0],
 * [1,0,0,0,0],
 * [0,1,0,0,0],
 * [0,0,1,0,0],
 * [0,0,0,1,1]]
 * Output: 3
 *
 * Constraints:
 *
 * rows == mat.length
 * cols == mat[i].length
 * 1 <= rows, cols <= 100
 * mat[i][j] is 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1582_SpecialPositionsInABinaryMatrix {

    @Answer
    public int numSpecial(int[][] mat) {
        final int m = mat.length, n = mat[0].length;
        int[] row = new int[m], col = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                row[i] += mat[i][j];
                col[j] += mat[i][j];
            }
        }
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 1 && row[i] == 1 && col[j] == 1) {
                    res++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {0, 0, 1},
            {1, 0, 0}
    }).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {0, 1, 0},
            {0, 0, 1}
    }).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {0, 0, 0, 1},
            {1, 0, 0, 0},
            {0, 1, 1, 0},
            {0, 0, 0, 0}
    }).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {0, 0, 0, 0, 0},
            {1, 0, 0, 0, 0},
            {0, 1, 0, 0, 0},
            {0, 0, 1, 0, 0},
            {0, 0, 0, 1, 1}
    }).expect(3);

}
