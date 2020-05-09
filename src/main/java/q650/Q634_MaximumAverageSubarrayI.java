package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-average-subarray-i/
 *
 * Given an array consisting of n integers, find the contiguous subarray of given length k that has the maximum
 * average value. And you need to output the maximum average value.
 *
 * Example 1:
 *
 * Input: [1,12,-5,-6,50,3], k = 4
 * Output: 12.75
 * Explanation: Maximum average is (12-5-6+50)/4 = 51/4 = 12.75
 *
 *
 *
 * Note:
 *
 * 1 <= k <= n <= 30,000.
 * Elements of the given array will be in the range [-10,000, 10,000].
 */
@RunWith(LeetCodeRunner.class)
public class Q634_MaximumAverageSubarrayI {

    @Answer
    public double findMaxAverage(int[] nums, int k) {
        int max = 0, sum = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i < k) {
                max = sum += nums[i];
            } else {
                sum += nums[i] - nums[i - k];
                max = Math.max(max, sum);
            }
        }
        return (double) max / k;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 12, -5, -6, 50, 3}, 4).expect(12.75);

}
