package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1567. Maximum Length of Subarray With Positive Product
 * https://leetcode.com/problems/maximum-length-of-subarray-with-positive-product/
 *
 * Given an array of integers nums, find the maximum length of a subarray where the product of all its elements is
 * positive.
 *
 * A subarray of an array is a consecutive sequence of zero or more values taken out of that array.
 *
 * Return the maximum length of a subarray with positive product.
 *
 * Example 1:
 *
 * Input: nums = [1,-2,-3,4]
 * Output: 4
 * Explanation: The array nums already has a positive product of 24.
 *
 * Example 2:
 *
 * Input: nums = [0,1,-2,-3,-4]
 * Output: 3
 * Explanation: The longest subarray with positive product is [1,-2,-3] which has a product of 6.
 * Notice that we cannot include 0 in the subarray since that'll make the product 0 which is not positive.
 *
 * Example 3:
 *
 * Input: nums = [-1,-2,-3,0,1]
 * Output: 2
 * Explanation: The longest subarray with positive product is [-1,-2] or [-2,-3].
 *
 * Example 4:
 *
 * Input: nums = [-1,2]
 * Output: 1
 *
 * Example 5:
 *
 * Input: nums = [1,2,3,5,-6,4,0,10]
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * -10^9 <= nums[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1567_MaximumLengthOfSubarrayWithPositiveProduct {

    @Answer
    public int getMaxLen(int[] nums) {
        int res = 0;
        int prev = -1, firstNeg = -1, negative = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                prev = i;
                negative = 0;
            } else if (nums[i] < 0) {
                if (negative == 0) {
                    firstNeg = i;
                }
                negative++;
            }
            if (negative % 2 == 0) {
                res = Math.max(res, i - prev);
            } else {
                res = Math.max(res, i - firstNeg);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, -2, -3, 4}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{0, 1, -2, -3, -4}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{-1, -2, -3, 0, 1}).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{-1, 2}).expect(1);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{1, 2, 3, 5, -6, 4, 0, 10}).expect(4);

}
