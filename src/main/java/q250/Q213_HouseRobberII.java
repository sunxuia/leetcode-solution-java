package q250;

import org.junit.runner.RunWith;
import q200.Q198_HouseRobber;
import q350.Q337_HouseRobberIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/house-robber-ii/
 *
 * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money
 * stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the last
 * one. Meanwhile, adjacent houses have security system connected and it will automatically contact the police if two
 * adjacent houses were broken into on the same night.
 *
 * Given a list of non-negative integers representing the amount of money of each house, determine the maximum amount
 * of money you can rob tonight without alerting the police.
 *
 * Example 1:
 *
 * Input: [2,3,2]
 * Output: 3
 * Explanation: You cannot rob house 1 (money = 2) and then rob house 3 (money = 2),
 * because they are adjacent houses.
 *
 * Example 2:
 *
 * Input: [1,2,3,1]
 * Output: 4
 * Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3).
 * Total amount you can rob = 1 + 3 = 4.
 *
 * 相关题目: {@link Q198_HouseRobber}, {@link Q337_HouseRobberIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q213_HouseRobberII {

    // 针对上一题的问题进行的改进
    @Answer
    public int rob(int[] nums) {
        final int len = nums.length;
        if (len == 1) {
            return nums[0];
        }
        int[] dp = new int[len + 3];
        // 不抢第1 间
        for (int i = 1; i < len; i++) {
            dp[i + 3] = Math.max(dp[i], dp[i + 1]) + nums[i];
        }
        int res = Math.max(dp[len + 1], dp[len + 2]);
        // 不抢最后1 间
        for (int i = 0; i < len - 1; i++) {
            dp[i + 3] = Math.max(dp[i], dp[i + 1]) + nums[i];
        }
        res = Math.max(res, Math.max(dp[len], dp[len + 1]));
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, 2}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, 1}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1}).expect(1);

}
