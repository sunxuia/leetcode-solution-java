package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1785. Minimum Elements to Add to Form a Given Sum
 * https://leetcode.com/problems/minimum-elements-to-add-to-form-a-given-sum/
 *
 * You are given an integer array nums and two integers limit and goal. The array nums has an interesting property that
 * abs(nums[i]) <= limit.
 *
 * Return the minimum number of elements you need to add to make the sum of the array equal to goal. The array must
 * maintain its property that abs(nums[i]) <= limit.
 *
 * Note that abs(x) equals x if x >= 0, and -x otherwise.
 *
 * Example 1:
 *
 * Input: nums = [1,-1,1], limit = 3, goal = -4
 * Output: 2
 * Explanation: You can add -2 and -3, then the sum of the array will be 1 - 1 + 1 - 2 - 3 = -4.
 *
 * Example 2:
 *
 * Input: nums = [1,-10,9,1], limit = 100, goal = 0
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= limit <= 10^6
 * -limit <= nums[i] <= limit
 * -10^9 <= goal <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1785_MinimumElementsToAddToFormAGivenSum {

    @Answer
    public int minElements(int[] nums, int limit, int goal) {
        long offset = goal;
        for (int num : nums) {
            offset -= num;
        }
        offset = Math.abs(offset);
        return (int) (offset / limit + (offset % limit > 0 ? 1 : 0));
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, -1, 1}, 3, -4).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, -10, 9, 1}, 100, 0).expect(1);

    @TestData
    public DataExpectation border() {
        int[] nums = new int[10_0000];
        Arrays.fill(nums, 100_0000);
        return DataExpectation.createWith(nums, 1000000, -1000000000).expect(101000);
    }
}
