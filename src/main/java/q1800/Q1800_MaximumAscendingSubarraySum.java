package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1800. Maximum Ascending Subarray Sum
 * https://leetcode.com/problems/maximum-ascending-subarray-sum/
 *
 * Given an array of positive integers nums, return the maximum possible sum of an ascending subarray in nums.
 *
 * A subarray is defined as a contiguous sequence of numbers in an array.
 *
 * A subarray [numsl, numsl+1, ..., numsr-1, numsr] is ascending if for all i where l <= i < r, numsi  < numsi+1. Note
 * that a subarray of size 1 is ascending.
 *
 * Example 1:
 *
 * Input: nums = [10,20,30,5,10,50]
 * Output: 65
 * Explanation: [5,10,50] is the ascending subarray with the maximum sum of 65.
 *
 * Example 2:
 *
 * Input: nums = [10,20,30,40,50]
 * Output: 150
 * Explanation: [10,20,30,40,50] is the ascending subarray with the maximum sum of 150.
 *
 * Example 3:
 *
 * Input: nums = [12,17,15,13,10,11,12]
 * Output: 33
 * Explanation: [10,11,12] is the ascending subarray with the maximum sum of 33.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 100
 * 1 <= nums[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1800_MaximumAscendingSubarraySum {

    @Answer
    public int maxAscendingSum(int[] nums) {
        int sum = nums[0], max = sum;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i - 1] >= nums[i]) {
                max = Math.max(max, sum);
                sum = 0;
            }
            sum += nums[i];
        }
        return Math.max(max, sum);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{10, 20, 30, 5, 10, 50}).expect(65);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{10, 20, 30, 40, 50}).expect(150);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{12, 17, 15, 13, 10, 11, 12}).expect(33);

}
