package q1300;

import org.junit.runner.RunWith;
import q950.Q931_MinimumFallingPathSum;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1289. Minimum Falling Path Sum II
 * https://leetcode.com/problems/minimum-falling-path-sum-ii/
 *
 * Given a square grid of integers arr, a falling path with non-zero shifts is a choice of exactly one element from each
 * row of arr, such that no two elements chosen in adjacent rows are in the same column.
 *
 * Return the minimum sum of a falling path with non-zero shifts.
 *
 * Example 1:
 *
 * Input: arr = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: 13
 * Explanation:
 * The possible falling paths are:
 * [1,5,9], [1,5,7], [1,6,7], [1,6,8],
 * [2,4,8], [2,4,9], [2,6,7], [2,6,8],
 * [3,4,8], [3,4,9], [3,5,7], [3,5,9]
 * The falling path with the smallest sum is [1,5,7], so the answer is 13.
 *
 * Constraints:
 *
 * 1 <= arr.length == arr[i].length <= 200
 * -99 <= arr[i][j] <= 99
 *
 * 上一题 {@link Q931_MinimumFallingPathSum}
 * 相比上题, 这题向下走的规则变了: arr[i][j] 可以走到 arr[i+1] 中除了 arr[i+1][j] 之外的其他所有单元格.
 */
@RunWith(LeetCodeRunner.class)
public class Q1289_MinimumFallingPathSumII {

    @Answer
    public int minFallingPathSum(int[][] arr) {
        final int n = arr.length;
        // min1 是行中最小的结果(列坐标是idx1), min2 是行中第二小的结果.
        int min1 = 0, min2 = 0, idx1 = -1;
        for (int i = 0; i < n; i++) {
            int currMin1 = Integer.MAX_VALUE;
            int currMin2 = Integer.MAX_VALUE;
            int currIdx1 = -1;
            for (int j = 0; j < n; j++) {
                int val = arr[i][j] + (j == idx1 ? min2 : min1);
                if (currMin1 > val) {
                    currMin2 = currMin1;
                    currMin1 = val;
                    currIdx1 = j;
                } else if (currMin2 > val) {
                    currMin2 = val;
                }
            }
            min1 = currMin1;
            min2 = currMin2;
            idx1 = currIdx1;
        }
        return min1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {1, 2, 3},
            {4, 5, 6},
            {7, 8, 9}
    }).expect(13);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {2, 2, 1, 2, 2},
            {2, 2, 1, 2, 2},
            {2, 2, 1, 2, 2},
            {2, 2, 1, 2, 2},
            {2, 2, 1, 2, 2}
    }).expect(7);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create(TestDataFileHelper.read2DArray("Q1289_TestData"))
            .expect(200);

}
