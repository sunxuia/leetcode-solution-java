package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2022. Convert 1D Array Into 2D Array
 * https://leetcode.com/problems/convert-1d-array-into-2d-array/
 *
 * You are given a 0-indexed 1-dimensional (1D) integer array original, and two integers, m and n. You are tasked with
 * creating a 2-dimensional (2D) array with  m rows and n columns using all the elements from original.
 *
 * The elements from indices 0 to n - 1 (inclusive) of original should form the first row of the constructed 2D array,
 * the elements from indices n to 2 * n - 1 (inclusive) should form the second row of the constructed 2D array, and so
 * on.
 *
 * Return an m x n 2D array constructed according to the above procedure, or an empty 2D array if it is impossible.
 *
 * Example 1:
 * (图Q2022_PIC.png)
 * Input: original = [1,2,3,4], m = 2, n = 2
 * Output: [[1,2],[3,4]]
 * Explanation: The constructed 2D array should contain 2 rows and 2 columns.
 * The first group of n=2 elements in original, [1,2], becomes the first row in the constructed 2D array.
 * The second group of n=2 elements in original, [3,4], becomes the second row in the constructed 2D array.
 *
 * Example 2:
 *
 * Input: original = [1,2,3], m = 1, n = 3
 * Output: [[1,2,3]]
 * Explanation: The constructed 2D array should contain 1 row and 3 columns.
 * Put all three elements in original into the first row of the constructed 2D array.
 *
 * Example 3:
 *
 * Input: original = [1,2], m = 1, n = 1
 * Output: []
 * Explanation: There are 2 elements in original.
 * It is impossible to fit 2 elements in a 1x1 2D array, so return an empty 2D array.
 *
 * Constraints:
 *
 * 1 <= original.length <= 5 * 10^4
 * 1 <= original[i] <= 10^5
 * 1 <= m, n <= 4 * 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q2022_Convert1dArrayInto2dArray {

    @Answer
    public int[][] construct2DArray(int[] original, int m, int n) {
        if (m * n != original.length) {
            return new int[0][0];
        }
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = original[i * n + j];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4}, 2, 2)
            .expect(new int[][]{{1, 2}, {3, 4}});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3}, 1, 3)
            .expect(new int[][]{{1, 2, 3}});

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 2}, 1, 1)
            .expect(new int[0][0]);

}
