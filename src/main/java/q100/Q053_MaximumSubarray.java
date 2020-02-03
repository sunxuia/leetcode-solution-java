package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-subarray/
 *
 * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest
 * sum and return its sum.
 *
 * Example:
 *
 * Input: [-2,1,-3,4,-1,2,1,-5,4],
 * Output: 6
 * Explanation: [4,-1,2,1] has the largest sum = 6.
 * Follow up:
 *
 * If you have figured out the O(n) solution, try coding another solution using the divide and conquer approach,
 * which is more subtle.
 */
@RunWith(LeetCodeRunner.class)
public class Q053_MaximumSubarray {

    @Answer
    public int maxSubArray(int[] nums) {
        int sum = 0, res = nums[0];
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0 && sum + nums[i] < 0) {
                sum = 0;
                res = Math.max(nums[i], res);
            } else {
                sum += nums[i];
                res = Math.max(sum, res);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4})
            .expect(6)
            .build();

    @TestData
    public DataExpectation border = DataExpectation.builder()
            .addArgument(new int[]{-2})
            .expect(-2)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{-1, -2})
            .expect(-1)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{-2, -1})
            .expect(-1)
            .build();
}
