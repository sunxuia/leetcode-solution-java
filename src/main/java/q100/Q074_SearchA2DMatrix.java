package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/search-a-2d-matrix/
 *
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 *
 * Integers in each row are sorted from left to right.
 * The first integer of each row is greater than the last integer of the previous row.
 * Example 1:
 *
 * Input:
 * matrix = [
 * [1,   3,  5,  7],
 * [10, 11, 16, 20],
 * [23, 30, 34, 50]
 * ]
 * target = 3
 * Output: true
 * Example 2:
 *
 * Input:
 * matrix = [
 * [1,   3,  5,  7],
 * [10, 11, 16, 20],
 * [23, 30, 34, 50]
 * ]
 * target = 13
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q074_SearchA2DMatrix {

    @Answer
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }
        final int m = matrix.length, n = matrix[0].length;
        int start = 0, end = m * n - 1;
        while (start < end) {
            int middle = (start + end) / 2;
            if (matrix[middle / n][middle % n] < target) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return matrix[start / n][start % n] == target;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 3, 5, 7},
                    {10, 11, 16, 20},
                    {23, 30, 34, 50}
            })
            .addArgument(3)
            .expect(true)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 3, 5, 7},
                    {10, 11, 16, 20},
                    {23, 30, 34, 50}
            })
            .addArgument(13)
            .expect(false)
            .build();

}
