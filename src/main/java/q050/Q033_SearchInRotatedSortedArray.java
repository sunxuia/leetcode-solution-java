package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/search-in-rotated-sorted-array/
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 *
 * (i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).
 *
 * You are given a target value to search. If found in the array return its index, otherwise return -1.
 *
 * You may assume no duplicate exists in the array.
 *
 * Your algorithm's runtime complexity must be in the order of O(log n).
 *
 * Example 1:
 *
 * Input: nums = [4,5,6,7,0,1,2], target = 0
 * Output: 4
 * Example 2:
 *
 * Input: nums = [4,5,6,7,0,1,2], target = 3
 * Output: -1
 *
 * 相关题目: {@link q100.Q081_SearchInRotatedSortedArrayII}
 */
@RunWith(LeetCodeRunner.class)
public class Q033_SearchInRotatedSortedArray {

    /**
     * 时间复杂度 O(logN), 极端情况下退化为 O(N)
     */
    @Answer
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int m = findMinimum(nums, 0, nums.length - 1);
        int r1 = binarySearch(nums, 0, m - 1, target);
        int r2 = binarySearch(nums, m, nums.length - 1, target);
        return Math.max(r1, r2);
    }

    private int findMinimum(int[] nums, int start, int end) {
        if (start == end) {
            return start;
        }
        if (nums[start] < nums[end]) {
            return start;
        }
        int middle = (start + end) / 2;
        if (nums[start] == nums[end]) {
            int a = findMinimum(nums, start, middle);
            int b = findMinimum(nums, middle + 1, end);
            // nums[b-1] <= nums[b] 用来处理middle 正好是最大值的情况(测试用例 normal1)
            return nums[a] <= nums[b] && nums[b - 1] <= nums[b] ? a : b;
        }
        // nums[start] > nums[end]
        if (nums[start] <= nums[middle]) {
            return findMinimum(nums, middle + 1, end);
        } else {
            return findMinimum(nums, start, middle);
        }
    }

    private int binarySearch(int[] nums, int start, int end, int target) {
        while (start < end) {
            int middle = (start + end) / 2;
            if (nums[middle] < target) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return nums[start] == target ? start : -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[]{4, 5, 6, 7, 0, 1, 2})
            .addArgument(0)
            .expect(4)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{4, 5, 6, 7, 0, 1, 2})
            .addArgument(3)
            .expect(-1)
            .build();

    @TestData
    public DataExpectation normal1() {
        int[] arg = new int[1000];
        arg[699] = 1;
        return DataExpectation.createWith(arg, 1).expect(699);
    }
}
