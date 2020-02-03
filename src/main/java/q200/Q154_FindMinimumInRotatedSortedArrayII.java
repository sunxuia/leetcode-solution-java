package q200;

import util.runner.Answer;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 *
 * (i.e.,  [0,1,2,4,5,6,7] might become  [4,5,6,7,0,1,2]).
 *
 * Find the minimum element.
 *
 * The array may contain duplicates.
 *
 * Example 1:
 *
 * Input: [1,3,5]
 * Output: 1
 *
 * Example 2:
 *
 * Input: [2,2,2,0,1]
 * Output: 0
 *
 * Note:
 *
 * This is a follow up problem to Find Minimum in Rotated Sorted Array.
 * Would allow duplicates affect the run-time complexity? How and why?
 *
 * 题解: 相比上题多了重复元素的条件.
 */
public class Q154_FindMinimumInRotatedSortedArrayII {

    @Answer
    public int findMin(int[] nums) {
        return dfs(nums, 0, nums.length - 1);
    }

    private int dfs(int[] nums, int start, int end) {
        if (start == end || nums[start] < nums[end]) {
            return nums[start];
        }
        int middle = (start + end) / 2;
        int left = dfs(nums, start, middle);
        int right = dfs(nums, middle + 1, end);
        return Math.min(left, right);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 5}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 2, 2, 0, 1}).expect(0);

}
