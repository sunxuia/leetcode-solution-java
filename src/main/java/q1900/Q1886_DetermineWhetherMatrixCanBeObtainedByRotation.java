package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1886. Determine Whether Matrix Can Be Obtained By Rotation
 * https://leetcode.com/problems/determine-whether-matrix-can-be-obtained-by-rotation/
 *
 * Given two n x n binary matrices mat and target, return true if it is possible to make mat equal to target by rotating
 * mat in 90-degree increments, or false otherwise.
 *
 * Example 1:
 * (图Q1886_PIC1.png)
 * Input: mat = [[0,1],[1,0]], target = [[1,0],[0,1]]
 * Output: true
 * Explanation: We can rotate mat 90 degrees clockwise to make mat equal target.
 *
 * Example 2:
 * (图Q1886_PIC2.png)
 * Input: mat = [[0,1],[1,1]], target = [[1,0],[0,1]]
 * Output: false
 * Explanation: It is impossible to make mat equal to target by rotating mat.
 *
 * Example 3:
 * (图Q1886_PIC3.png)
 * Input: mat = [[0,0,0],[0,1,0],[1,1,1]], target = [[1,1,1],[0,1,0],[0,0,0]]
 * Output: true
 * Explanation: We can rotate mat 90 degrees clockwise two times to make mat equal target.
 *
 * Constraints:
 *
 * n == mat.length == target.length
 * n == mat[i].length == target[i].length
 * 1 <= n <= 10
 * mat[i][j] and target[i][j] are either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1886_DetermineWhetherMatrixCanBeObtainedByRotation {

    @Answer
    public boolean findRotation(int[][] mat, int[][] target) {
        for (int i = 0; i < 4; i++) {
            mat = rotate90(mat);
            if (isEqual(mat, target)) {
                return true;
            }
        }
        return false;
    }

    private int[][] rotate90(int[][] mat) {
        final int n = mat.length;
        int[][] rotated = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                rotated[i][j] = mat[n - 1 - j][i];
            }
        }
        return rotated;
    }

    private boolean isEqual(int[][] mat, int[][] target) {
        final int n = mat.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] != target[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                            {0, 1},
                            {1, 0}},
                    new int[][]{
                            {1, 0},
                            {0, 1}})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                            {0, 1},
                            {1, 1}},
                    new int[][]{
                            {1, 0},
                            {0, 1}})
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{
                            {0, 0, 0},
                            {0, 1, 0},
                            {1, 1, 1}},
                    new int[][]{
                            {1, 1, 1},
                            {0, 1, 0},
                            {0, 0, 0}})
            .expect(true);

}
