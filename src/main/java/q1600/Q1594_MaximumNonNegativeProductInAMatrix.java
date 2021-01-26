package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1594. Maximum Non Negative Product in a Matrix
 * https://leetcode.com/problems/maximum-non-negative-product-in-a-matrix/
 *
 * You are given a rows x cols matrix grid. Initially, you are located at the top-left corner (0, 0), and in each step,
 * you can only move right or down in the matrix.
 *
 * Among all possible paths starting from the top-left corner (0, 0) and ending in the bottom-right corner (rows - 1,
 * cols - 1), find the path with the maximum non-negative product. The product of a path is the product of all integers
 * in the grid cells visited along the path.
 *
 * Return the maximum non-negative product modulo 109 + 7. If the maximum product is negative return -1.
 *
 * Notice that the modulo is performed after getting the maximum product.
 *
 * Example 1:
 *
 * Input: grid = [[-1,-2,-3],
 * [-2,-3,-3],
 * [-3,-3,-2]]
 * Output: -1
 * Explanation: It's not possible to get non-negative product in the path from (0, 0) to (2, 2), so return -1.
 *
 * Example 2:
 *
 * Input: grid = [[1,-2,1],
 * [1,-2,1],
 * [3,-4,1]]
 * Output: 8
 * Explanation: Maximum non-negative product is in bold (1 * 1 * -2 * -4 * 1 = 8).
 *
 * Example 3:
 *
 * Input: grid = [[1, 3],
 * [0,-4]]
 * Output: 0
 * Explanation: Maximum non-negative product is in bold (1 * 0 * -4 = 0).
 *
 * Example 4:
 *
 * Input: grid = [[ 1, 4,4,0],
 * [-2, 0,0,1],
 * [ 1,-1,1,1]]
 * Output: 2
 * Explanation: Maximum non-negative product is in bold (1 * -2 * 1 * -1 * 1 * 1 = 2).
 *
 * Constraints:
 *
 * 1 <= rows, cols <= 15
 * -4 <= grid[i][j] <= 4
 */
@RunWith(LeetCodeRunner.class)
public class Q1594_MaximumNonNegativeProductInAMatrix {

    @Answer
    public int maxProductPath(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        long[][] max = new long[m][n];
        long[][] min = new long[m][n];
        max[0][0] = min[0][0] = grid[0][0];
        for (int i = 1; i < m; i++) {
            long maxProd = max[i - 1][0] * grid[i][0];
            long minProd = min[i - 1][0] * grid[i][0];
            max[i][0] = Math.max(maxProd, minProd);
            min[i][0] = Math.min(maxProd, minProd);
        }
        for (int i = 1; i < n; i++) {
            long maxProd = max[0][i - 1] * grid[0][i];
            long minProd = min[0][i - 1] * grid[0][i];
            max[0][i] = Math.max(maxProd, minProd);
            min[0][i] = Math.min(maxProd, minProd);
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                if (grid[i][j] > 0) {
                    max[i][j] = grid[i][j] * Math.max(max[i - 1][j], max[i][j - 1]);
                    min[i][j] = grid[i][j] * Math.min(min[i - 1][j], min[i][j - 1]);
                } else {
                    max[i][j] = grid[i][j] * Math.min(min[i - 1][j], min[i][j - 1]);
                    min[i][j] = grid[i][j] * Math.max(max[i - 1][j], max[i][j - 1]);
                }
            }
        }
        if (max[m - 1][n - 1] < 0) {
            return -1;
        }
        return (int) (max[m - 1][n - 1] % 10_0000_0007);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {-1, -2, -3},
            {-2, -3, -3},
            {-3, -3, -2}
    }).expect(-1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, -2, 1},
            {1, -2, 1},
            {3, -4, 1}
    }).expect(8);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 3},
            {0, -4}
    }).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 4, 4, 0},
            {-2, 0, 0, 1},
            {1, -1, 1, 1}
    }).expect(2);

}
