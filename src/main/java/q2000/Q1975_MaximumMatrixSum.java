package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1975. Maximum Matrix Sum
 * https://leetcode.com/problems/maximum-matrix-sum/
 *
 * You are given an n x n integer matrix. You can do the following operation any number of times:
 *
 * Choose any two adjacent elements of matrix and multiply each of them by -1.
 *
 * Two elements are considered adjacent if and only if they share a border.
 *
 * Your goal is to maximize the summation of the matrix's elements. Return the maximum sum of the matrix's elements
 * using the operation mentioned above.
 *
 * Example 1:
 * (图Q1975_PIC1.png)
 * Input: matrix = [[1,-1],[-1,1]]
 * Output: 4
 * Explanation: We can follow the following steps to reach sum equals 4:
 * - Multiply the 2 elements in the first row by -1.
 * - Multiply the 2 elements in the first column by -1.
 *
 * Example 2:
 * (图Q1975_PIC2.png)
 * Input: matrix = [[1,2,3],[-1,-2,-3],[1,2,3]]
 * Output: 16
 * Explanation: We can follow the following step to reach sum equals 16:
 * - Multiply the 2 last elements in the second row by -1.
 *
 * Constraints:
 *
 * n == matrix.length == matrix[i].length
 * 2 <= n <= 250
 * -10^5 <= matrix[i][j] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1975_MaximumMatrixSum {

    /**
     * 找规律的题: 偶数个负数会全部抵消掉, 奇数个负数会抵消到只剩下一个负数 (这个负数可以让最小的数字去当, 比如 -0).
     */
    @Answer
    public long maxMatrixSum(int[][] matrix) {
        final int n = matrix.length;
        int neg = 0, min = Integer.MAX_VALUE;
        long sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int value = matrix[i][j];
                if (value >= 0) {
                    sum += value;
                    min = Math.min(min, value);
                } else {
                    neg++;
                    sum -= value;
                    min = Math.min(min, -value);
                }
            }
        }
        if (neg % 2 == 0) {
            return sum;
        } else {
            return sum - 2 * min;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
                    {1, -1},
                    {-1, 1}})
            .expect(4L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
                    {1, 2, 3},
                    {-1, -2, -3},
                    {1, 2, 3}})
            .expect(16L);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{
                    {-1, 0, -1},
                    {-2, 1, 3},
                    {3, 2, 2}})
            .expect(15L);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(new int[][]{
                    {2, 9, 3},
                    {5, 4, -4},
                    {1, 7, 1}})
            .expect(34L);

}
