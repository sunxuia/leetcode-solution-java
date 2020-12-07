package q1400;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1380. Lucky Numbers in a Matrix
 * https://leetcode.com/problems/lucky-numbers-in-a-matrix/
 *
 * Given a m * n matrix of distinct numbers, return all lucky numbers in the matrix in any order.
 *
 * A lucky number is an element of the matrix such that it is the minimum element in its row and maximum in its column.
 *
 * Example 1:
 *
 * Input: matrix = [[3,7,8],[9,11,13],[15,16,17]]
 * Output: [15]
 * Explanation: 15 is the only lucky number since it is the minimum in its row and the maximum in its column
 *
 * Example 2:
 *
 * Input: matrix = [[1,10,4,2],[9,3,8,7],[15,16,17,12]]
 * Output: [12]
 * Explanation: 12 is the only lucky number since it is the minimum in its row and the maximum in its column.
 *
 * Example 3:
 *
 * Input: matrix = [[7,8],[1,2]]
 * Output: [7]
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= n, m <= 50
 * 1 <= matrix[i][j] <= 10^5.
 * All elements in the matrix are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1380_LuckyNumbersInAMatrix {

    @Answer
    public List<Integer> luckyNumbers(int[][] matrix) {
        final int m = matrix.length, n = matrix[0].length;
        int[] rowMin = new int[m];
        int[] columnMax = new int[n];
        Arrays.fill(rowMin, Integer.MAX_VALUE);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rowMin[i] = Math.min(rowMin[i], matrix[i][j]);
                columnMax[j] = Math.max(columnMax[j], matrix[i][j]);
            }
        }

        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rowMin[i] == matrix[i][j] && columnMax[j] == matrix[i][j]) {
                    res.add(matrix[i][j]);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {3, 7, 8},
            {9, 11, 13},
            {15, 16, 17}
    }).expect(List.of(15));

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {1, 10, 4, 2},
            {9, 3, 8, 7},
            {15, 16, 17, 12}
    }).expect(List.of(12));

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {7, 8},
            {1, 2}
    }).expect(List.of(7));

}
