package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1752. Check if Array Is Sorted and Rotated
 * https://leetcode.com/problems/check-if-array-is-sorted-and-rotated/
 *
 * Given an array nums, return true if the array was originally sorted in non-decreasing order, then rotated some number
 * of positions (including zero). Otherwise, return false.
 *
 * There may be duplicates in the original array.
 *
 * Note: An array A rotated by x positions results in an array B of the same length such that A[i] == B[(i+x) %
 * A.length], where % is the modulo operation.
 *
 * Example 1:
 *
 * Input: nums = [3,4,5,1,2]
 * Output: true
 * Explanation: [1,2,3,4,5] is the original sorted array.
 * You can rotate the array by x = 3 positions to begin on the the element of value 3: [3,4,5,1,2].
 *
 * Example 2:
 *
 * Input: nums = [2,1,3,4]
 * Output: false
 * Explanation: There is no sorted array once rotated that can make nums.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3]
 * Output: true
 * Explanation: [1,2,3] is the original sorted array.
 * You can rotate the array by x = 0 positions (i.e. no rotation) to make nums.
 *
 * Example 4:
 *
 * Input: nums = [1,1,1]
 * Output: true
 * Explanation: [1,1,1] is the original sorted array.
 * You can rotate any number of positions to make nums.
 *
 * Example 5:
 *
 * Input: nums = [2,1]
 * Output: true
 * Explanation: [1,2] is the original sorted array.
 * You can rotate the array by x = 5 positions to begin on the element of value 2: [2,1].
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1752_CheckIfArrayIsSortedAndRotated {

    @Answer
    public boolean check(int[] nums) {
        final int n = nums.length;
        int change = 0;
        for (int i = 1; i < n; i++) {
            if (nums[i - 1] > nums[i]) {
                if (++change > 1) {
                    return false;
                }
            }
        }
        if (change == 0) {
            return true;
        }
        return nums[0] >= nums[n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 4, 5, 1, 2}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 1, 3, 4}).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3}).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 1, 1}).expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{2, 1}).expect(true);

}
