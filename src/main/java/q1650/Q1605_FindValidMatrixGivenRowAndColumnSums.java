package q1650;

import org.junit.Assert;
import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1605. Find Valid Matrix Given Row and Column Sums
 * https://leetcode.com/problems/find-valid-matrix-given-row-and-column-sums/
 *
 * You are given two arrays rowSum and colSum of non-negative integers where rowSum[i] is the sum of the elements in the
 * ith row and colSum[j] is the sum of the elements of the jth column of a 2D matrix. In other words, you do not know
 * the elements of the matrix, but you do know the sums of each row and column.
 *
 * Find any matrix of non-negative integers of size rowSum.length x colSum.length that satisfies the rowSum and colSum
 * requirements.
 *
 * Return a 2D array representing any matrix that fulfills the requirements. It's guaranteed that at least one matrix
 * that fulfills the requirements exists.
 *
 * Example 1:
 *
 * > Input: rowSum = [3,8], colSum = [4,7]
 * > Output: [[3,0],
 * >          [1,7]]
 * > Explanation:
 * > 0th row: 3 + 0 = 3 == rowSum[0]
 * > 1st row: 1 + 7 = 8 == rowSum[1]
 * > 0th column: 3 + 1 = 4 == colSum[0]
 * > 1st column: 0 + 7 = 7 == colSum[1]
 * > The row and column sums match, and all matrix elements are non-negative.
 * > Another possible matrix is: [[1,2],
 * >                              [3,5]]
 *
 * Example 2:
 *
 * > Input: rowSum = [5,7,10], colSum = [8,6,8]
 * > Output: [[0,5,0],
 * >          [6,1,0],
 * >          [2,0,8]]
 *
 * Example 3:
 *
 * > Input: rowSum = [14,9], colSum = [6,9,8]
 * > Output: [[0,9,5],
 * >          [6,0,3]]
 *
 * Example 4:
 *
 * > Input: rowSum = [1,0], colSum = [1]
 * > Output: [[1],
 * >          [0]]
 *
 * Example 5:
 *
 * > Input: rowSum = [0], colSum = [0]
 * > Output: [[0]]
 *
 * Constraints:
 *
 * 1 <= rowSum.length, colSum.length <= 500
 * 0 <= rowSum[i], colSum[i] <= 10^8
 * sum(rows) == sum(columns)
 */
@RunWith(LeetCodeRunner.class)
public class Q1605_FindValidMatrixGivenRowAndColumnSums {

    /**
     * 暴力求解.
     */
    @Answer
    public int[][] restoreMatrix(int[] rowSum, int[] colSum) {
        int[][] grid = new int[rowSum.length][colSum.length];
        dfs(rowSum, colSum, grid, 0, 0);
        return grid;
    }

    private boolean dfs(int[] rowSum, int[] colSum, int[][] grid, int row, int col) {
        final int m = rowSum.length, n = colSum.length;
        if (row == m) {
            // 结束
            for (int i = 0; i < n; i++) {
                if (colSum[i] != 0) {
                    return false;
                }
            }
            return true;
        }
        if (col == n) {
            if (rowSum[row] != 0) {
                return false;
            }
            // 换行
            return dfs(rowSum, colSum, grid, row + 1, 0);
        }
        for (int v = Math.min(rowSum[row], colSum[col]); v >= 0; v--) {
            rowSum[row] -= v;
            colSum[col] -= v;
            grid[row][col] = v;
            if (dfs(rowSum, colSum, grid, row, col + 1)) {
                return true;
            }
            rowSum[row] += v;
            colSum[col] += v;
        }
        return false;
    }

    /**
     * LeetCode 上比较快的解法, 贪婪算法.
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/find-valid-matrix-given-row-and-column-sums/solution/tan-xin-fa-fu-tu-jie-bao-zheng-neng-dong-by-durant/
     * @formatter:on
     */
    @Answer
    public int[][] restoreMatrix2(int[] rowSum, int[] colSum) {
        final int m = rowSum.length, n = colSum.length;
        int[][] grid = new int[m][n];
        for (int i = 0, j = 0; i < m && j < n; ) {
            int v = Math.min(rowSum[i], colSum[j]);
            grid[i][j] = v;
            rowSum[i] -= v;
            colSum[j] -= v;
            if (rowSum[i] == 0) {
                i++;
            }
            if (colSum[j] == 0) {
                j++;
            }
        }
        return grid;
    }

    @TestData
    public DataExpectation example1 = createTestData(new int[]{3, 8}, new int[]{4, 7});

    private DataExpectation createTestData(int[] rowSum, int[] colSum) {
        return DataExpectation.createWith(rowSum.clone(), colSum.clone())
                .assertResult((int[][] res) -> {
                    final int m = rowSum.length, n = colSum.length;
                    Assert.assertEquals(m, res.length);
                    Assert.assertEquals(n, res[0].length);
                    int[] rows = new int[m];
                    int[] cols = new int[n];
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j < n; j++) {
                            rows[i] += res[i][j];
                            cols[j] += res[i][j];
                        }
                    }
                    AssertUtils.assertEquals(rowSum, rows);
                    AssertUtils.assertEquals(colSum, cols);
                });
    }

    @TestData
    public DataExpectation example2 = createTestData(new int[]{5, 7, 10}, new int[]{8, 6, 8});

    @TestData
    public DataExpectation example3 = createTestData(new int[]{14, 9}, new int[]{6, 9, 8});

    @TestData
    public DataExpectation example4 = createTestData(new int[]{1, 0}, new int[]{1});

    @TestData
    public DataExpectation example5 = createTestData(new int[]{0}, new int[]{0});

}
