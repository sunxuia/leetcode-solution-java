package q250;

import org.junit.runner.RunWith;
import q100.Q074_SearchA2DMatrix;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/search-a-2d-matrix-ii/
 *
 * Write an efficient algorithm that searches for a value in an m x n matrix. This matrix has the following properties:
 *
 * Integers in each row are sorted in ascending from left to right.
 * Integers in each column are sorted in ascending from top to bottom.
 *
 * Example:
 *
 * Consider the following matrix:
 *
 * [
 * [1,   4,  7, 11, 15],
 * [2,   5,  8, 12, 19],
 * [3,   6,  9, 16, 22],
 * [10, 13, 14, 17, 24],
 * [18, 21, 23, 26, 30]
 * ]
 *
 * Given target = 5, return true.
 *
 * Given target = 20, return false.
 *
 * 相关题目: {@link Q074_SearchA2DMatrix}
 */
@RunWith(LeetCodeRunner.class)
public class Q240_SearchA2DMatrixII {

    // 这题没什么思路, 参考网上的解法, 从对角线开始找
    @Answer
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }
        int y = 0, x = matrix[0].length - 1;
        while (y < matrix.length && x >= 0) {
            if (matrix[y][x] < target) {
                y++;
            } else if (matrix[y][x] > target) {
                x--;
            } else {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{
            {1, 4, 7, 11, 15},
            {2, 5, 8, 12, 19},
            {3, 6, 9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
    }, 5).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{
            {1, 4, 7, 11, 15},
            {2, 5, 8, 12, 19},
            {3, 6, 9, 16, 22},
            {10, 13, 14, 17, 24},
            {18, 21, 23, 26, 30}
    }, 20).expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation.createWith(new int[0][0], 1).expect(false);

    @TestData
    public DataExpectation border2 = DataExpectation.createWith(new int[][]{{}}, 1).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[][]{{1}}, 2).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[][]{{-1, 3}}, 3).expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(new int[][]{{-1, 3}}, -1).expect(true);

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(new int[][]{{1, 4}, {2, 5}}, 2).expect(true);

}
