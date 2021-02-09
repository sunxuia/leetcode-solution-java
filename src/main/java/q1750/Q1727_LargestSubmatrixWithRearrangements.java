package q1750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1727. Largest Submatrix With Rearrangements
 * https://leetcode.com/problems/largest-submatrix-with-rearrangements/
 *
 * You are given a binary matrix matrix of size m x n, and you are allowed to rearrange the columns of the matrix in any
 * order.
 *
 * Return the area of the largest submatrix within matrix where every element of the submatrix is 1 after reordering the
 * columns optimally.
 *
 * Example 1:
 * <img src="./Q1727_PIC1.png">
 * Input: matrix = [[0,0,1],[1,1,1],[1,0,1]]
 * Output: 4
 * Explanation: You can rearrange the columns as shown above.
 * The largest submatrix of 1s, in bold, has an area of 4.
 *
 * Example 2:
 * <img src="./Q1727_PIC2.png">
 * Input: matrix = [[1,0,1,0,1]]
 * Output: 3
 * Explanation: You can rearrange the columns as shown above.
 * The largest submatrix of 1s, in bold, has an area of 3.
 *
 * Example 3:
 *
 * Input: matrix = [[1,1,0],[1,0,1]]
 * Output: 2
 * Explanation: Notice that you must rearrange entire columns, and there is no way to make a submatrix of 1s larger than
 * an area of 2.
 *
 * Example 4:
 *
 * Input: matrix = [[0,0],[0,0]]
 * Output: 0
 * Explanation: As there are no 1s, no submatrix of 1s can be formed and the area is 0.
 *
 * Constraints:
 *
 * m == matrix.length
 * n == matrix[i].length
 * 1 <= m * n <= 10^5
 * matrix[i][j] is 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1727_LargestSubmatrixWithRearrangements {

    /**
     * 根据hint 中的提示得出如下解答
     */
    @Answer
    public int largestSubmatrix(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        // 找出每列以 matrix[i][j] 结尾的连续1 的长度, 0 的话长度就是0.
        for (int j = 0; j < n; j++) {
            for (int i = 0, prev = -1; i < m; i++) {
                if (matrix[i][j] == 1) {
                    matrix[i][j] = i - prev;
                } else {
                    prev = i;
                }
            }
        }

        // 每行按照高度排序, 从高到低寻找最大面积
        int res = 0;
        for (int i = 0; i < m; i++) {
            Arrays.sort(matrix[i]);
            for (int j = n - 1; j >= 0 && matrix[i][j] > 0; j--) {
                int area = matrix[i][j] * (n - j);
                res = Math.max(res, area);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 0, 1},
            {1, 1, 1},
            {1, 0, 1}
    }).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 0, 1, 0, 1}
    }).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 0},
            {1, 0, 1}
    }).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {0, 0},
            {0, 0}
    }).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {1, 1, 1},
            {0, 1, 1}
    }).expect(4);

}
