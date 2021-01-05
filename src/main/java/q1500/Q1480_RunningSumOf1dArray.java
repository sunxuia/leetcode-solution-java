package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1480. Running Sum of 1d Array
 * https://leetcode.com/problems/running-sum-of-1d-array/
 *
 * Given an array nums. We define a running sum of an array as runningSum[i] = sum(nums[0]&hellip;nums[i]).
 *
 * Return the running sum of nums.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4]
 * Output: [1,3,6,10]
 * Explanation: Running sum is obtained as follows: [1, 1+2, 1+2+3, 1+2+3+4].
 *
 * Example 2:
 *
 * Input: nums = [1,1,1,1,1]
 * Output: [1,2,3,4,5]
 * Explanation: Running sum is obtained as follows: [1, 1+1, 1+1+1, 1+1+1+1, 1+1+1+1+1].
 *
 * Example 3:
 *
 * Input: nums = [3,1,2,10,1]
 * Output: [3,4,6,16,17]
 *
 * Constraints:
 *
 * 1 <= nums.length <= 1000
 * -10^6 <= nums[i] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1480_RunningSumOf1dArray {

    @Answer
    public int[] runningSum(int[] nums) {
        final int n = nums.length;
        for (int i = 1; i < n; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[]{1, 2, 3, 4})
            .expect(new int[]{1, 3, 6, 10});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[]{1, 1, 1, 1, 1})
            .expect(new int[]{1, 2, 3, 4, 5});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[]{3, 1, 2, 10, 1})
            .expect(new int[]{3, 4, 6, 16, 17});

}
