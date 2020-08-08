package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 912. Sort an Array
 * https://leetcode.com/problems/sort-an-array/
 *
 * Given an array of integers nums, sort the array in ascending order.
 *
 * Example 1:
 * Input: nums = [5,2,3,1]
 * Output: [1,2,3,5]
 * Example 2:
 * Input: nums = [5,1,1,2,0,0]
 * Output: [0,0,1,1,2,5]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 50000
 * -50000 <= nums[i] <= 50000
 */
@RunWith(LeetCodeRunner.class)
public class Q912_SortAnArray {

    @Answer
    public int[] sortArray(int[] nums) {
        quickSort(nums, 0, nums.length - 1);
        return nums;
    }

    private void quickSort(int[] nums, int start, int end) {
        if (start >= end) {
            return;
        }
        int i = start, j = end + 1;
        while (i < j) {
            while (nums[start] < nums[--j]) {
            }
            while (i < j && nums[++i] < nums[start]) {
            }
            swap(nums, i, j);
        }
        swap(nums, start, j);
        quickSort(nums, start, j - 1);
        quickSort(nums, j + 1, end);
    }

    private void swap(int[] nums, int i, int j) {
        int t = nums[i];
        nums[i] = nums[j];
        nums[j] = t;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{5, 2, 3, 1}).expect(new int[]{1, 2, 3, 5});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{5, 1, 1, 2, 0, 0}).expect(new int[]{0, 0, 1, 1, 2, 5});

}
