package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.DataExpectationBuilder;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/find-peak-element/
 *
 * A peak element is an element that is greater than its neighbors.
 *
 * Given an input array nums, where nums[i] ≠ nums[i+1], find a peak element and return its index.
 *
 * The array may contain multiple peaks, in that case return the index to any one of the peaks is fine.
 *
 * You may imagine that nums[-1] = nums[n] = -∞.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,1]
 * Output: 2
 * Explanation: 3 is a peak element and your function should return the index number 2.
 *
 * Example 2:
 *
 * Input: nums = [1,2,1,3,5,6,4]
 * Output: 1 or 5
 * Explanation: Your function can return either index number 1 where the peak element is 2,
 * or index number 5 where the peak element is 6.
 *
 * Note:
 *
 * Your solution should be in logarithmic complexity.
 */
@RunWith(LeetCodeRunner.class)
public class Q162_FindPeakElement {

    // 题目要求 O(logN) 的时间复杂度
    @Answer
    public int findPeakElement(int[] nums) {
        int start = 0, end = nums.length - 1;
        while (start < end - 1) {
            int middle = (start + end) / 2;
            if (nums[middle - 1] < nums[middle] && nums[middle] > nums[middle + 1]) {
                return middle;
            } else if (nums[middle - 1] < nums[middle]) {
                start = middle;
            } else {
                end = middle;
            }
        }
        return nums[start] > nums[end] ? start : end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 1}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[]{1, 2, 1, 3, 5, 6, 4})
            .expect(new int[]{1, 5})
            .assertMethod(DataExpectationBuilder.orExpectAssertMethod)
            .build();

    @TestData
    public DataExpectation border = DataExpectation.create(new int[]{1}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1, 2}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{2, 1}).expect(0);

}
