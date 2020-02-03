package q200;

import org.junit.runner.RunWith;
import q250.Q213_HouseRobberII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/house-robber/
 *
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money
 * stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security system
 * connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount
 * of money you can rob tonight without alerting the police.
 *
 * Example 1:
 *
 * Input: [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * Example 2:
 *
 * Input: [2,7,9,3,1]
 * Output: 12
 * Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1).
 * Total amount you can rob = 2 + 9 + 1 = 12.
 *
 * 相关题目: {@link Q213_HouseRobberII}
 */
@RunWith(LeetCodeRunner.class)
public class Q198_HouseRobber {

    @Answer
    public int rob(int[] nums) {
        final int len = nums.length;
        int[] dp = new int[len + 3];
        for (int i = 0; i < len; i++) {
            dp[i + 3] = Math.max(dp[i], dp[i + 1]) + nums[i];
        }
        return Math.max(dp[len + 1], dp[len + 2]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 1}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 7, 9, 3, 1}).expect(12);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{2, 1, 1, 2}).expect(4);

}
