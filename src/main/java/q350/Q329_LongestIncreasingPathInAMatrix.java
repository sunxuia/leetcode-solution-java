package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/longest-increasing-path-in-a-matrix/
 *
 * Given an integer matrix, find the length of the longest increasing path.
 *
 * From each cell, you can either move to four directions: left, right, up or down. You may NOT move diagonally or
 * move outside of the boundary (i.e. wrap-around is not allowed).
 *
 * Example 1:
 *
 * Input: nums =
 * [
 * [9,9,4],
 * [6,6,8],
 * [2,1,1]
 * ]
 * Output: 4
 * Explanation: The longest increasing path is [1, 2, 6, 9].
 *
 * Example 2:
 *
 * Input: nums =
 * [
 * [3,4,5],
 * [3,2,6],
 * [2,2,1]
 * ]
 * Output: 4
 * Explanation: The longest increasing path is [3, 4, 5, 6]. Moving diagonally is not allowed.
 */
@RunWith(LeetCodeRunner.class)
public class Q329_LongestIncreasingPathInAMatrix {

    @Answer
    public int longestIncreasingPath(int[][] matrix) {
        if ((m = matrix.length) == 0 || (n = matrix[0].length) == 0) {
            return 0;
        }
        int[][] max = new int[m][n];
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res = Math.max(res, dfs(matrix, max, i, j, Integer.MIN_VALUE));
            }
        }
        return res;
    }

    private int m, n;

    private int dfs(int[][] matrix, int[][] max, int i, int j, int prev) {
        if (i == -1 || j == -1 || i == m || j == n || matrix[i][j] <= prev) {
            return 0;
        }
        if (max[i][j] > 0) {
            return max[i][j];
        }
        int a = dfs(matrix, max, i - 1, j, matrix[i][j]);
        int b = dfs(matrix, max, i, j + 1, matrix[i][j]);
        int c = dfs(matrix, max, i + 1, j, matrix[i][j]);
        int d = dfs(matrix, max, i, j - 1, matrix[i][j]);
        max[i][j] = 1 + Math.max(Math.max(a, b), Math.max(c, d));
        return max[i][j];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {9, 9, 4},
            {6, 6, 8},
            {2, 1, 1}
    }).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {3, 4, 5},
            {3, 2, 6},
            {2, 2, 1}
    }).expect(4);

    @TestData
    public DataExpectation longData = DataExpectation.create(new int[][]{
            {0, 1, 2, 3, 4, 5, 6, 7, 8, 9},
            {19, 18, 17, 16, 15, 14, 13, 12, 11, 10},
            {20, 21, 22, 23, 24, 25, 26, 27, 28, 29},
            {39, 38, 37, 36, 35, 34, 33, 32, 31, 30},
            {40, 41, 42, 43, 44, 45, 46, 47, 48, 49},
            {59, 58, 57, 56, 55, 54, 53, 52, 51, 50},
            {60, 61, 62, 63, 64, 65, 66, 67, 68, 69},
            {79, 78, 77, 76, 75, 74, 73, 72, 71, 70},
            {80, 81, 82, 83, 84, 85, 86, 87, 88, 89},
            {99, 98, 97, 96, 95, 94, 93, 92, 91, 90},
            {100, 101, 102, 103, 104, 105, 106, 107, 108, 109},
            {119, 118, 117, 116, 115, 114, 113, 112, 111, 110},
            {120, 121, 122, 123, 124, 125, 126, 127, 128, 129},
            {139, 138, 137, 136, 135, 134, 133, 132, 131, 130},
            {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    }).expect(140);

}
