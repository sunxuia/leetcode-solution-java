package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1292. Maximum Side Length of a Square with Sum Less than or Equal to Threshold
 * https://leetcode.com/problems/maximum-side-length-of-a-square-with-sum-less-than-or-equal-to-threshold/
 *
 * Given a m x n matrix mat and an integer threshold. Return the maximum side-length of a square with a sum less than or
 * equal to threshold or return 0 if there is no such square.
 *
 * Example 1:
 * <img src="./Q1292_PIC.png">
 * Input: mat = [[1,1,3,2,4,3,2],[1,1,3,2,4,3,2],[1,1,3,2,4,3,2]], threshold = 4
 * Output: 2
 * Explanation: The maximum side length of square with sum less than 4 is 2 as shown.
 *
 * Example 2:
 *
 * Input: mat = [[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2],[2,2,2,2,2]], threshold = 1
 * Output: 0
 *
 * Example 3:
 *
 * Input: mat = [[1,1,1,1],[1,0,0,0],[1,0,0,0],[1,0,0,0]], threshold = 6
 * Output: 3
 *
 * Example 4:
 *
 * Input: mat = [[18,70],[61,1],[25,85],[14,40],[11,96],[97,96],[63,45]], threshold = 40184
 * Output: 2
 *
 * Constraints:
 *
 * 1 <= m, n <= 300
 * m == mat.length
 * n == mat[i].length
 * 0 <= mat[i][j] <= 10000
 * 0 <= threshold <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1292_MaximumSideLengthOfASquareWithSumLessThanOrEqualToThreshold {

    /**
     * 求区域和的方式, 时间复杂度 O(N^2).
     */
    @Answer
    public int maxSideLength(int[][] mat, int threshold) {
        final int m = mat.length, n = mat[0].length;
        int[][] sums = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                sums[i + 1][j + 1] = mat[i][j] + sums[i][j + 1] + sums[i + 1][j] - sums[i][j];
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                for (int li = i + res, lj = j + res; li < m && lj < n; li++, lj++) {
                    int sum = sums[li + 1][lj + 1] - sums[i][lj + 1] - sums[li + 1][j] + sums[i][j];
                    if (sum <= threshold) {
                        res = li - i + 1;
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{
            {1, 1, 3, 2, 4, 3, 2},
            {1, 1, 3, 2, 4, 3, 2},
            {1, 1, 3, 2, 4, 3, 2}
    }, 4).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2},
            {2, 2, 2, 2, 2}
    }, 1).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[][]{
            {1, 1, 1, 1},
            {1, 0, 0, 0},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    }, 6).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[][]{
            {18, 70},
            {61, 1},
            {25, 85},
            {14, 40},
            {11, 96},
            {97, 96},
            {63, 45}
    }, 40184).expect(2);

}
