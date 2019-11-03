package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/jump-game/
 *
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Determine if you are able to reach the last index.
 *
 * Example 1:
 *
 * Input: [2,3,1,1,4]
 * Output: true
 * Explanation: Jump 1 step from index 0 to 1, then 3 steps to the last index.
 * Example 2:
 *
 * Input: [3,2,1,0,4]
 * Output: false
 * Explanation: You will always arrive at index 3 no matter what. Its maximum
 * jump length is 0, which makes it impossible to reach the last index.
 */
@RunWith(LeetCodeRunner.class)
public class Q055_JumpGame {

    /**
     * 这种dp 方式不够快
     */
    @Answer
    public boolean canJump(int[] nums) {
        final int length = nums.length;
        if (length < 2) {
            return true;
        }
        boolean[] dp = new boolean[length];
        dp[0] = true;
        for (int i = 0; i < length && dp[i]; i++) {
            for (int j = i + 1; j < length && j <= i + nums[i]; j++) {
                dp[j] = true;
            }
        }
        return dp[length - 1];
    }

    /**
     * LeetCode 上非dp 的实现方式
     */
    @Answer
    public boolean canJump2(int[] nums) {
        // next 表示最远可以跳到的下标
        int next = 0, dist = nums.length - 1;
        for (int i = 0; i <= next && next < dist; i++) {
            next = Math.max(next, i + nums[i]);
        }
        return next >= dist;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{2, 3, 1, 1, 4})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{3, 2, 1, 0, 4})
            .expect(false);
}
