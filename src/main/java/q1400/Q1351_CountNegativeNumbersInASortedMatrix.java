package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1351. Count Negative Numbers in a Sorted Matrix
 * https://leetcode.com/problems/count-negative-numbers-in-a-sorted-matrix/
 *
 * Given a m * n matrix grid which is sorted in non-increasing order both row-wise and column-wise.
 *
 * Return the number of negative numbers in grid.
 *
 * Example 1:
 *
 * Input: grid = [[4,3,2,-1],[3,2,1,-1],[1,1,-1,-2],[-1,-1,-2,-3]]
 * Output: 8
 * Explanation: There are 8 negatives number in the matrix.
 *
 * Example 2:
 *
 * Input: grid = [[3,2],[1,0]]
 * Output: 0
 *
 * Example 3:
 *
 * Input: grid = [[1,-1],[-1,-1]]
 * Output: 3
 *
 * Example 4:
 *
 * Input: grid = [[-1]]
 * Output: 1
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 100
 * -100 <= grid[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1351_CountNegativeNumbersInASortedMatrix {

    @Answer
    public int countNegatives(int[][] grid) {
        int res = 0;
        for (int[] row : grid) {
            res += binaryCount(row);
        }
        return res;
    }

    private int binaryCount(int[] arr) {
        int start = -1, end = arr.length;
        while (start < end - 1) {
            int mid = (start + end) / 2;
            if (arr[mid] < 0) {
                end = mid;
            } else {
                start = mid;
            }
        }
        return arr.length - end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{4, 3, 2, -1}, {3, 2, 1, -1}, {1, 1, -1, -2}, {-1, -1, -2, -3}})
            .expect(8);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{3, 2}, {1, 0}}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, -1}, {-1, -1}}).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{{-1}}).expect(1);

}
