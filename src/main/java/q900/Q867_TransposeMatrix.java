package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 867. Transpose Matrix
 * https://leetcode.com/problems/transpose-matrix/
 *
 * Given a matrix A, return the transpose of A.
 *
 * The transpose of a matrix is the matrix flipped over it's main diagonal, switching the row and column indices of the
 * matrix.
 * (图 Q867_PIC.png)
 *
 * Example 1:
 *
 * Input: [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [[1,4,7],[2,5,8],[3,6,9]]
 *
 * Example 2:
 *
 * Input: [[1,2,3],[4,5,6]]
 * Output: [[1,4],[2,5],[3,6]]
 *
 * Note:
 *
 * 1 <= A.length <= 1000
 * 1 <= A[0].length <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q867_TransposeMatrix {

    @Answer
    public int[][] transpose(int[][] A) {
        final int m = A.length, n = A[0].length;
        int[][] res = new int[n][m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[j][i] = A[i][j];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 9}})
            .expect(new int[][]{{1, 4, 7}, {2, 5, 8}, {3, 6, 9}});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 2, 3}, {4, 5, 6}})
            .expect(new int[][]{{1, 4}, {2, 5}, {3, 6}});

}
