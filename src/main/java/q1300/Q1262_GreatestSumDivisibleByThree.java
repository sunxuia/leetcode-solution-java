package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1262. Greatest Sum Divisible by Three
 * https://leetcode.com/problems/greatest-sum-divisible-by-three/
 *
 * Given an array nums of integers, we need to find the maximum possible sum of elements of the array such that it is
 * divisible by three.
 *
 * Example 1:
 *
 * Input: nums = [3,6,5,1,8]
 * Output: 18
 * Explanation: Pick numbers 3, 6, 1 and 8 their sum is 18 (maximum sum divisible by 3).
 *
 * Example 2:
 *
 * Input: nums = [4]
 * Output: 0
 * Explanation: Since 4 is not divisible by 3, do not pick any number.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3,4,4]
 * Output: 12
 * Explanation: Pick numbers 1, 3, 4 and 4 their sum is 12 (maximum sum divisible by 3).
 *
 * Constraints:
 *
 * 1 <= nums.length <= 4 * 10^4
 * 1 <= nums[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1262_GreatestSumDivisibleByThree {

    @Answer
    public int maxSumDivThree(int[] nums) {
        int[][] dp = new int[2][3];
        for (int i = 0; i < nums.length; i++) {
            int[] curr = dp[i % 2], next = dp[(i + 1) % 2];
            System.arraycopy(curr, 0, next, 0, 3);
            for (int j = 0; j < 3; j++) {
                int val = curr[j] + nums[i];
                next[val % 3] = Math.max(next[val % 3], val);
            }
        }
        return dp[nums.length % 2][0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{3, 6, 5, 1, 8}).expect(18);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 4}).expect(12);

}
