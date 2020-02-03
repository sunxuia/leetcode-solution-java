package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/wildcard-matching/submissions/
 *
 * Given an array of non-negative integers, you are initially positioned at the first index of the array.
 *
 * Each element in the array represents your maximum jump length at that position.
 *
 * Your goal is to reach the last index in the minimum number of jumps.
 *
 * Example:
 *
 * Input: [2,3,1,1,4]
 * Output: 2
 * Explanation: The minimum number of jumps to reach the last index is 2.
 * Jump 1 step from index 0 to 1, then 3 steps to the last index.
 *
 * Note:
 *
 * You can assume that you can always reach the last index.
 */
@RunWith(LeetCodeRunner.class)
public class Q045_JumpGameII {

    /**
     * dp 解法, 这个还不是最快的方法
     */
    @Answer
    public int dp(int[] nums) {
        final int length = nums.length;
        int[] dp = new int[length];
        for (int i = 1; i < length; i++) {
            dp[i] = Integer.MAX_VALUE;
        }
        for (int i = 0; i < length - 1; i++) {
            for (int j = 1; j <= nums[i] && i + j < length; j++) {
                dp[i + j] = Math.min(dp[i] + 1, dp[i + j]);
            }
        }
        return dp[length - 1];
    }

    /**
     * LeetCode 上最快的解法, 看当前一步能够走到的最远位置.
     */
    @Answer
    public int jump(int[] nums) {
        // far : 当前能够走到的最远下标
        // end : 走到这个最远下标的出发下标
        // res : 走到这个最远下标的跳跃次数(结果)
        int far = 0, end = 0, res = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            far = Math.max(far, i + nums[i]);
            if (i == end) {
                res++;
                end = far;
            }
        }
        return res;
    }

    /**
     * dfs 解法, 这个会出现运行超时的情况(测试用例 longTime)
     */
    public int dfs(int[] nums) {
        return dfs(nums, 0);
    }

    private int dfs(int[] nums, int i) {
        if (i >= nums.length - 1) {
            return 0;
        }
        if (nums[i] == 0) {
            return -1;
        }
        int min = Integer.MAX_VALUE;
        for (int j = 1; j <= nums[i]; j++) {
            int jump = dfs(nums, i + j);
            if (jump >= 0) {
                min = Math.min(jump, min);
            }
        }
        return min + 1;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[]{2, 3, 1, 1, 4})
            .expect(2)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[]{2, 3, 0, 1, 4})
            .expect(2)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[]{5, 9, 3, 2, 1, 0, 2, 3, 3, 1, 0, 0})
            .expect(3)
            .build();

    @TestData
    public DataExpectation longTime = DataExpectation.builder()
            .addArgument(new int[]{
                    5, 6, 4, 4, 6, 9, 4, 4, 7, 4, 4, 8, 2, 6, 8,
                    1, 5, 9, 6, 5, 2, 7, 9, 7, 9, 6, 9, 4, 1, 6,
                    8, 8, 4, 4, 2, 0, 3, 8, 5
            })
            .expect(5)
            .build();

}
