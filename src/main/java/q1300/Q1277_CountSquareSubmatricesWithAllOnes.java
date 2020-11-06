package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1277. Count Square Submatrices with All Ones
 * https://leetcode.com/problems/count-square-submatrices-with-all-ones/
 *
 * Given a m * n matrix of ones and zeros, return how many square submatrices have all ones.
 *
 * Example 1:
 *
 * Input: matrix =
 * [
 * [0,1,1,1],
 * [1,1,1,1],
 * [0,1,1,1]
 * ]
 * Output: 15
 * Explanation:
 * There are 10 squares of side 1.
 * There are 4 squares of side 2.
 * There is  1 square of side 3.
 * Total number of squares = 10 + 4 + 1 = 15.
 *
 * Example 2:
 *
 * Input: matrix =
 * [
 * [1,0,1],
 * [1,1,0],
 * [1,1,0]
 * ]
 * Output: 7
 * Explanation:
 * There are 6 squares of side 1.
 * There is 1 square of side 2.
 * Total number of squares = 6 + 1 = 7.
 *
 * Constraints:
 *
 * 1 <= arr.length <= 300
 * 1 <= arr[0].length <= 300
 * 0 <= arr[i][j] <= 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1277_CountSquareSubmatricesWithAllOnes {

    /**
     * 从左上角找矩形, 时间复杂度 O(N^3).
     */
    @Answer
    public int countSquares(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        // ones[i][j] 表示 (0, 0) 到 (i, j) 的矩形(包含2点) 中1 的数量
        int[][] ones = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                ones[i + 1][j + 1] = matrix[i][j]
                        + ones[i][j + 1] + ones[i + 1][j] - ones[i][j];
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int k = 1; i + k <= m && j + k <= n; k++) {
                    int ei = i + k - 1, ej = j + k - 1;
                    // 计算 (i, j) 为起点, 长度为k 的正方形中1 的数量, 如果是k*k 则说明里面都是1
                    int area = ones[ei + 1][ej + 1] - ones[ei + 1][j] - ones[i][ej + 1] + ones[i][j];
                    if (area == k * k) {
                        res++;
                    } else {
                        break;
                    }
                }
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的方法, 从右下角找矩形, 时间复杂度 O(N^2)
     * 使用dp 的方式, dp[i][j] 表示以 (i, j) 为右下角的正方形的最大边长.
     * 下面的实现直接在matrix 上进行操作了.
     */
    @Answer
    public int countSquares2(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i > 0 && j > 0 && matrix[i][j] > 0) {
                    // 以 (i, j) 为右下角的正方形是左、上、左上3 个正方形中最小的边长+1
                    matrix[i][j] = 1 + Math.min(matrix[i - 1][j - 1], Math.min(matrix[i - 1][j], matrix[i][j - 1]));
                }
                res += matrix[i][j];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 1, 1, 1},
            {1, 1, 1, 1},
            {0, 1, 1, 1}
    }).expect(15);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 0, 1},
            {1, 1, 0},
            {1, 1, 0}
    }).expect(7);

}
