package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1901. Find a Peak Element II
 * https://leetcode.com/problems/find-a-peak-element-ii/
 *
 * A peak element in a 2D grid is an element that is strictly greater than all of its adjacent neighbors to the left,
 * right, top, and bottom.
 *
 * Given a 0-indexed m x n matrix mat where no two adjacent cells are equal, find any peak element mat[i][j] and return
 * the length 2 array [i,j].
 *
 * You may assume that the entire matrix is surrounded by an outer perimeter with the value -1 in each cell.
 *
 * You must write an algorithm that runs in O(m log(n)) or O(n log(m)) time.
 *
 * Example 1:
 * (图Q1901_PIC1.png)
 * Input: mat = [[1,4],[3,2]]
 * Output: [0,1]
 * Explanation: Both 3 and 4 are peak elements so [1,0] and [0,1] are both acceptable answers.
 *
 * Example 2:
 * (图Q1901_PIC2.png)
 * Input: mat = [[10,20,15],[21,30,14],[7,16,32]]
 * Output: [1,1]
 * Explanation: Both 30 and 32 are peak elements so [1,1] and [2,2] are both acceptable answers.
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 500
 * 1 <= mat[i][j] <= 10^5
 * No two adjacent cells are equal.
 */
@RunWith(LeetCodeRunner.class)
public class Q1901_FindAPeakElementII {

    /**
     * 参考leetcode 的最快解法, 虽然题目规定了时间复杂度限制, 不过更低的也能通过.
     */
    @Answer
    public int[] findPeakGrid(int[][] mat) {
        final int m = mat.length;
        int low = 0, high = m - 1, maxIdx;
        while (low < high) {
            int mid = (low + high) / 2;
            // 找出本行中最大的值, 这样这个值只需要和上下进行比较.
            maxIdx = findMaxIdx(mat[mid]);
            if (mat[mid][maxIdx] < mat[mid + 1][maxIdx]) {
                // 如果小于下面的值, 说明峰值在下面
                low = mid + 1;
            } else {
                // 如果大于下面的值, 说明峰值在上面或是自己
                high = mid;
            }
        }
        maxIdx = findMaxIdx(mat[low]);
        return new int[]{low, maxIdx};
    }

    private int findMaxIdx(int[] arr) {
        final int n = arr.length;
        int index = 0, maxIdx = 0;
        for (int i = 0; i < n; i++) {
            if (maxIdx < arr[i]) {
                maxIdx = arr[i];
                index = i;
            }
        }
        return index;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
                    {1, 4},
                    {3, 2}})
            .expect(new int[]{0, 1})
            .orExpect(new int[]{1, 0});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
                    {10, 20, 15},
                    {21, 30, 14},
                    {7, 16, 32}})
            .expect(new int[]{1, 1})
            .orExpect(new int[]{2, 2});

}
