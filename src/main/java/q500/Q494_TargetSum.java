package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/target-sum/
 *
 * You are given a list of non-negative integers, a1, a2, ..., an, and a target, S. Now you have 2 symbols + and -.
 * For each integer, you should choose one from + and - as its new symbol.
 *
 * Find out how many ways to assign symbols to make sum of integers equal to target S.
 *
 * Example 1:
 *
 * Input: nums is [1, 1, 1, 1, 1], S is 3.
 * Output: 5
 * Explanation:
 *
 * -1+1+1+1+1 = 3
 * +1-1+1+1+1 = 3
 * +1+1-1+1+1 = 3
 * +1+1+1-1+1 = 3
 * +1+1+1+1-1 = 3
 *
 * There are 5 ways to assign symbols to make the sum of nums be target 3.
 *
 * Note:
 *
 * The length of the given array is positive and will not exceed 20.
 * The sum of elements in the given array will not exceed 1000.
 * Your output answer is guaranteed to be fitted in a 32-bit integer.
 */
@RunWith(LeetCodeRunner.class)
public class Q494_TargetSum {

    @Answer
    public int findTargetSumWays(int[] nums, int S) {
        return dfs(nums, 0, S);
    }

    private int dfs(int[] nums, int i, int sum) {
        if (i == nums.length) {
            return sum == 0 ? 1 : 0;
        }
        return dfs(nums, i + 1, sum - nums[i])
                + dfs(nums, i + 1, sum + nums[i]);
    }

    /**
     * LeetCode 上比较快的 dp 的方式:
     * sum 是所有数相加的结果, S 是部分数相加减去部分数的结果,
     * 那么 (sum + S) / 2 表示的就是计算出S 的相加的数字的和.
     * 那么这题就可以退化为求nums 中和为 (sum + S) / 2 的子集的数量.
     */
    @Answer
    public int findTargetSumWays_dp(int[] nums, int S) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < S || ((sum + S) & 1) == 1) {
            return 0;
        }
        sum = (S + sum) / 2;
        int[] dp = new int[sum + 1];
        // 哨兵, 表示sum 为0 时可能的组合情况: 1个.
        dp[0] = 1;
        // 遍历nums 中的元素, 处理当加上num 的时候可以得出的结果.
        for (int num : nums) {
            for (int j = sum; j >= num; j--) {
                dp[j] += dp[j - num];
            }
        }
        return dp[sum];
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.createWith(new int[]{1, 1, 1, 1, 1}, 3).expect(5);

}
