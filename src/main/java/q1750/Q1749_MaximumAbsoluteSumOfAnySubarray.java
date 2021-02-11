package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1749. Maximum Absolute Sum of Any Subarray
 * https://leetcode.com/problems/maximum-absolute-sum-of-any-subarray/
 *
 * You are given an integer array nums. The absolute sum of a subarray [numsl, numsl+1, ..., numsr-1, numsr] is
 * abs(numsl + numsl+1 + ... + numsr-1 + numsr).
 *
 * Return the maximum absolute sum of any (possibly empty) subarray of nums.
 *
 * Note that abs(x) is defined as follows:
 *
 * If x is a negative integer, then abs(x) = -x.
 * If x is a non-negative integer, then abs(x) = x.
 *
 * Example 1:
 *
 * Input: nums = [1,-3,2,3,-4]
 * Output: 5
 * Explanation: The subarray [2,3] has absolute sum = abs(2+3) = abs(5) = 5.
 *
 * Example 2:
 *
 * Input: nums = [2,-5,1,-4,3,-2]
 * Output: 8
 * Explanation: The subarray [-5,1,-4] has absolute sum = abs(-5+1-4) = abs(-8) = 8.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1749_MaximumAbsoluteSumOfAnySubarray {

    @Answer
    public int maxAbsoluteSum(int[] nums) {
        return Math.max(maxPositive(nums), -minNegative(nums));
    }

    private int maxPositive(int[] nums) {
        int res = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            res = Math.max(res, sum);
            if (sum < 0) {
                sum = 0;
            }
        }
        return res;
    }

    private int minNegative(int[] nums) {
        int res = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            res = Math.min(res, sum);
            if (sum > 0) {
                sum = 0;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, -3, 2, 3, -4}).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, -5, 1, -4, 3, -2}).expect(8);

}
