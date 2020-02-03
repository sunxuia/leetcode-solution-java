package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/search-in-rotated-sorted-array-ii/
 *
 * Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.
 *
 * (i.e., [0,0,1,2,2,5,6] might become [2,5,6,0,0,1,2]).
 *
 * You are given a target value to search. If found in the array return true, otherwise return false.
 *
 * Example 1:
 *
 * Input: nums = [2,5,6,0,0,1,2], target = 0
 * Output: true
 *
 * Example 2:
 *
 * Input: nums = [2,5,6,0,0,1,2], target = 3
 * Output: false
 *
 * Follow up:
 *
 * This is a follow up problem to Search in Rotated Sorted Array, where nums may contain duplicates.
 * Would this affect the run-time complexity? How and why?
 *
 * 相关题目 : {@link q050.Q033_SearchInRotatedSortedArray}, 本题要稍微简单点, 只需要判断是否存在.
 */
@RunWith(LeetCodeRunner.class)
public class Q081_SearchInRotatedSortedArrayII {

    /**
     * 这题我的对Q033 中的解答做了一些改进, 将Q033 中的找出最小值下标和二分查找合并到一起了.
     */
    @Answer
    public boolean search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return false;
        }
        return find(nums, 0, nums.length - 1, target);
    }

    private boolean find(int[] nums, int start, int end, int target) {
        if (start == end) {
            return nums[start] == target;
        }
        int middle = (start + end) / 2;
        if (nums[start] < nums[end]) {
            // 有序, 二分查找
            if (nums[middle] < target) {
                return find(nums, middle + 1, end, target);
            } else {
                return find(nums, start, middle, target);
            }
        }

        // 截断点在中间, 分别查找
        return find(nums, start, middle, target)
                || find(nums, middle + 1, end, target);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 5, 6, 0, 0, 1, 2}, 0).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 5, 6, 0, 0, 1, 2}, 3).expect(false);

    @TestData
    public DataExpectation normal1() {
        int[] arg = new int[1000];
        arg[699] = 1;
        return DataExpectation.createWith(arg, 1).expect(true);
    }

}
