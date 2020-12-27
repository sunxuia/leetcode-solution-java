package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1439. Find the Kth Smallest Sum of a Matrix With Sorted Rows
 * https://leetcode.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/
 *
 * You are given an m * n matrix, mat, and an integer k, which has its rows sorted in non-decreasing order.
 *
 * You are allowed to choose exactly 1 element from each row to form an array. Return the Kth smallest array sum among
 * all possible arrays.
 *
 * Example 1:
 *
 * Input: mat = [[1,3,11],[2,4,6]], k = 5
 * Output: 7
 * Explanation: Choosing one element from each row, the first k smallest sum are:
 * [1,2], [1,4], [3,2], [3,4], [1,6]. Where the 5th sum is 7.
 *
 * Example 2:
 *
 * Input: mat = [[1,3,11],[2,4,6]], k = 9
 * Output: 17
 *
 * Example 3:
 *
 * Input: mat = [[1,10,10],[1,4,5],[2,3,6]], k = 7
 * Output: 9
 * Explanation: Choosing one element from each row, the first k smallest sum are:
 * [1,1,2], [1,1,3], [1,4,2], [1,4,3], [1,1,6], [1,5,2], [1,5,3]. Where the 7th sum is 9.
 *
 * Example 4:
 *
 * Input: mat = [[1,1,10],[2,2,9]], k = 7
 * Output: 12
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat.length[i]
 * 1 <= m, n <= 40
 * 1 <= k <= min(200, n ^ m)
 * 1 <= mat[i][j] <= 5000
 * mat[i] is a non decreasing array.
 */
@RunWith(LeetCodeRunner.class)
public class Q1439_FindTheKthSmallestSumOfAMatrixWithSortedRows {

    /**
     * 二分法, 查找每个总和在排序中是第几个.
     *
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/find-the-kth-smallest-sum-of-a-matrix-with-sorted-rows/solution/er-fen-by-newbie-19-3/
     * @formatter:on
     */
    @Answer
    public int kthSmallest(int[][] mat, int k) {
        final int m = mat.length, n = mat[0].length;
        int low = 0, high = 0;
        for (int i = 0; i < m; i++) {
            low += mat[i][0];
            high += mat[i][n - 1];
        }
        for (int sum = low; low < high; ) {
            int mid = low + (high - low) / 2;
            count = 1;
            dfs(mid, 0, sum, k, mat);
            if (count < k) {
                low = mid + 1;
            } else {
                high = mid;
            }
        }
        return low;
    }

    private int count;

    private void dfs(int mid, int index, int sum, int k, int[][] mat) {
        final int m = mat.length, n = mat[0].length;
        if (sum > mid || index == m || count > k) {
            return;
        }
        dfs(mid, index + 1, sum, k, mat);
        for (int i = 1; i < n; i++) {
            if (sum + mat[index][i] - mat[index][0] <= mid) {
                count++;
                dfs(mid, index + 1, sum + mat[index][i] - mat[index][0], k, mat);
            } else {
                break;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {1, 3, 11},
                    {2, 4, 6}
            }, 5)
            .expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                    {1, 3, 11},
                    {2, 4, 6}
            }, 9)
            .expect(17);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{
                    {1, 10, 10},
                    {1, 4, 5},
                    {2, 3, 6}
            }, 7)
            .expect(9);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[][]{
                    {1, 1, 10},
                    {2, 2, 9}
            }, 7)
            .expect(12);

}
