package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1671. Minimum Number of Removals to Make Mountain Array
 * https://leetcode.com/problems/minimum-number-of-removals-to-make-mountain-array/
 *
 * You may recall that an array arr is a mountain array if and only if:
 *
 * arr.length >= 3
 * There exists some index i (0-indexed) with 0 < i < arr.length - 1 such that:
 *
 * arr[0] < arr[1] < ... < arr[i - 1] < arr[i]
 * arr[i] > arr[i + 1] > ... > arr[arr.length - 1]
 *
 *
 *
 * Given an integer array nums???, return the minimum number of elements to remove to make nums??? a mountain array.
 *
 * Example 1:
 *
 * Input: nums = [1,3,1]
 * Output: 0
 * Explanation: The array itself is a mountain array so we do not need to remove any elements.
 *
 * Example 2:
 *
 * Input: nums = [2,1,1,5,6,2,3,1]
 * Output: 3
 * Explanation: One solution is to remove the elements at indices 0, 1, and 5, making the array nums = [1,5,6,3,1].
 *
 * Example 3:
 *
 * Input: nums = [4,3,2,1,1,2,3,1]
 * Output: 4
 *
 * Example 4:
 *
 * Input: nums = [1,2,3,4,4,3,2,1]
 * Output: 1
 *
 * Constraints:
 *
 * 3 <= nums.length <= 1000
 * 1 <= nums[i] <= 109
 * It is guaranteed that you can make a mountain array out of nums.
 */
@RunWith(LeetCodeRunner.class)
public class Q1671_MinimumNumberOfRemovalsToMakeMountainArray {

    /**
     * 根据 hint 中的提示, 可以使用 LIS (最长上升子序列来做)
     */
    @Answer
    public int minimumMountainRemovals(int[] nums) {
        final int n = nums.length;

        // 左边的最长上升子序列
        int[] left = new int[n];
        for (int i = 1; i < n; i++) {
            for (int j = i - 1; j >= 0; j--) {
                if (nums[j] < nums[i]) {
                    left[i] = Math.max(left[i], left[j] + 1);
                }
            }
        }

        // 右边的最长上升子序列
        int[] right = new int[n];
        for (int i = n - 2; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                if (nums[i] > nums[j]) {
                    right[i] = Math.max(right[i], right[j] + 1);
                }
            }
        }

        // 最长的左右合并成的山
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (left[i] > 0 && right[i] > 0) {
                max = Math.max(max, left[i] + 1 + right[i]);
            }
        }
        return n - max;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 1}).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 1, 5, 6, 2, 3, 1}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{4, 3, 2, 1, 1, 2, 3, 1}).expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 2, 3, 4, 4, 3, 2, 1}).expect(1);

}
