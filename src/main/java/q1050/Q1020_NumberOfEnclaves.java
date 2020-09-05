package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1020. Number of Enclaves
 * https://leetcode.com/problems/number-of-enclaves/
 *
 * Given a 2D array A, each cell is 0 (representing sea) or 1 (representing land)
 *
 * A move consists of walking from one land square 4-directionally to another land square, or off the boundary of the
 * grid.
 *
 * Return the number of land squares in the grid for which we cannot walk off the boundary of the grid in any number of
 * moves.
 *
 * Example 1:
 *
 * Input: [[0,0,0,0],[1,0,1,0],[0,1,1,0],[0,0,0,0]]
 * Output: 3
 * Explanation:
 * There are three 1s that are enclosed by 0s, and one 1 that isn't enclosed because its on the boundary.
 *
 * Example 2:
 *
 * Input: [[0,1,1,0],[0,0,1,0],[0,0,1,0],[0,0,0,0]]
 * Output: 0
 * Explanation:
 * All 1s are either on the boundary or can reach the boundary.
 *
 * Note:
 *
 * 1 <= A.length <= 500
 * 1 <= A[i].length <= 500
 * 0 <= A[i][j] <= 1
 * All rows have the same size.
 */
@RunWith(LeetCodeRunner.class)
public class Q1020_NumberOfEnclaves {

    @Answer
    public int numEnclaves(int[][] A) {
        final int m = A.length, n = A[0].length;
        for (int i = 0; i < m; i++) {
            dfs(A, i, 0);
            dfs(A, i, n - 1);
        }
        for (int i = 1; i < n - 1; i++) {
            dfs(A, 0, i);
            dfs(A, m - 1, i);
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res += A[i][j];
            }
        }
        return res;
    }

    private void dfs(int[][] A, int i, int j) {
        final int m = A.length, n = A[0].length;
        if (i == -1 || i == m || j == -1 || j == n
                || A[i][j] != 1) {
            return;
        }
        A[i][j] = 0;
        dfs(A, i + 1, j);
        dfs(A, i, j + 1);
        dfs(A, i - 1, j);
        dfs(A, i, j - 1);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{
                    {0, 0, 0, 0},
                    {1, 0, 1, 0},
                    {0, 1, 1, 0},
                    {0, 0, 0, 0}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{
                    {0, 1, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 0}})
            .expect(0);

}
