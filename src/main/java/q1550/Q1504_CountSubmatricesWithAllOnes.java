package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1504. Count Submatrices With All Ones
 * https://leetcode.com/problems/count-submatrices-with-all-ones/
 *
 * Given a rows * columns matrix mat of ones and zeros, return how many submatrices have all ones.
 *
 * Example 1:
 *
 * Input: mat = [[1,0,1],
 * [1,1,0],
 * [1,1,0]]
 * Output: 13
 * Explanation:
 * There are 6 rectangles of side 1x1.
 * There are 2 rectangles of side 1x2.
 * There are 3 rectangles of side 2x1.
 * There is 1 rectangle of side 2x2.
 * There is 1 rectangle of side 3x1.
 * Total number of rectangles = 6 + 2 + 3 + 1 + 1 = 13.
 *
 * Example 2:
 *
 * Input: mat = [[0,1,1,0],
 * [0,1,1,1],
 * [1,1,1,0]]
 * Output: 24
 * Explanation:
 * There are 8 rectangles of side 1x1.
 * There are 5 rectangles of side 1x2.
 * There are 2 rectangles of side 1x3.
 * There are 4 rectangles of side 2x1.
 * There are 2 rectangles of side 2x2.
 * There are 2 rectangles of side 3x1.
 * There is 1 rectangle of side 3x2.
 * Total number of rectangles = 8 + 5 + 2 + 4 + 2 + 2 + 1 = 24.
 *
 * Example 3:
 *
 * Input: mat = [[1,1,1,1,1,1]]
 * Output: 21
 *
 * Example 4:
 *
 * Input: mat = [[1,0,1],[0,1,0],[1,0,1]]
 * Output: 5
 *
 * Constraints:
 *
 * 1 <= rows <= 150
 * 1 <= columns <= 150
 * 0 <= mat[i][j] <= 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1504_CountSubmatricesWithAllOnes {

    @Answer
    public int numSubmat(int[][] mat) {
        final int m = mat.length, n = mat[0].length;
        int[][] sums = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sums[i + 1][j + 1] = sums[i + 1][j] + sums[i][j + 1] - sums[i][j] + mat[i][j];
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int ni = i + 1; ni <= m; ni++) {
                    for (int nj = j + 1; nj <= n; nj++) {
                        int area = sums[ni][nj] - sums[i][nj] - sums[ni][j] + sums[i][j];
                        if (area == (ni - i) * (nj - j)) {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的方法
     */
    @Answer
    public int numSubmat2(int[][] mat) {
        final int m = mat.length, n = mat[0].length;
        // dp[i] 表示遍历到(i, j) 时以 (i, j) 左边有多少个连续的1
        int[] dp = new int[n];
        int count = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (mat[i][j] == 0) {
                    dp[j] = 0;
                    continue;
                }
                dp[j]++;
                // 单行的矩形
                count += dp[j];
                // 向上计算多行的结果
                int k = j - 1, min = dp[j];
                while (k >= 0 && mat[i][k] == 1) {
                    min = Math.min(dp[k], min);
                    count += min;
                    k--;
                }
            }
        }
        return count;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {1, 0, 1},
            {1, 1, 0},
            {1, 1, 0}
    }).expect(13);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 1, 1, 0},
            {0, 1, 1, 1},
            {1, 1, 1, 0}
    }).expect(24);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 1, 1, 1, 1, 1}})
            .expect(21);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 0, 1},
            {0, 1, 0},
            {1, 0, 1}
    }).expect(5);

}
