package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 861. Score After Flipping Matrix
 * https://leetcode.com/problems/score-after-flipping-matrix/
 *
 * We have a two dimensional matrix A where each value is 0 or 1.
 *
 * A move consists of choosing any row or column, and toggling each value in that row or column: changing all 0s to 1s,
 * and all 1s to 0s.
 *
 * After making any number of moves, every row of this matrix is interpreted as a binary number, and the score of the
 * matrix is the sum of these numbers.
 *
 * Return the highest possible score.
 *
 * Example 1:
 *
 * Input: [[0,0,1,1],[1,0,1,0],[1,1,0,0]]
 * Output: 39
 * Explanation:
 * Toggled to [[1,1,1,1],[1,0,0,1],[1,1,1,1]].
 * 0b1111 + 0b1001 + 0b1111 = 15 + 9 + 15 = 39
 *
 * Note:
 *
 * 1 <= A.length <= 20
 * 1 <= A[0].length <= 20
 * A[i][j] is 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q861_ScoreAfterFlippingMatrix {

    // https://www.cnblogs.com/grandyang/p/10674440.html
    @Answer
    public int matrixScore(int[][] A) {
        final int m = A.length, n = A[0].length;
        int res = (1 << (n - 1)) * m;
        for (int j = 1; j < n; j++) {
            int count = 0;
            for (int i = 0; i < m; i++) {
                count += A[i][j] ^ A[i][0] ^ 1;
            }
            res += Math.max(count, m - count) * (1 << (n - 1 - j));
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new int[][]{{0, 0, 1, 1}, {1, 0, 1, 0}, {1, 1, 0, 0}})
            .expect(39);

}
