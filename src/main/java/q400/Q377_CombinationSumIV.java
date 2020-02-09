package q400;

import org.junit.runner.RunWith;
import q250.Q216_CombinationSumIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/combination-sum-iv/
 *
 * Given an integer array with all positive numbers and no duplicates, find the number of possible combinations that
 * add up to a positive integer target.
 *
 * Example:
 *
 * nums = [1, 2, 3]
 * target = 4
 *
 * The possible combination ways are:
 * (1, 1, 1, 1)
 * (1, 1, 2)
 * (1, 2, 1)
 * (1, 3)
 * (2, 1, 1)
 * (2, 2)
 * (3, 1)
 *
 * Note that different sequences are counted as different combinations.
 *
 * Therefore the output is 7.
 *
 *
 *
 * Follow up:
 * What if negative numbers are allowed in the given array?
 * How does it change the problem?
 * What limitation we need to add to the question to allow negative numbers?
 *
 * Credits:
 * Special thanks to @pbrother for adding this problem and creating all test cases.
 *
 * 上一题: {@link Q216_CombinationSumIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q377_CombinationSumIV {

    @Answer
    public int combinationSum4(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int a : nums) {
                if (i >= a) {
                    dp[i] += dp[i - a];
                }
            }
        }
        return dp[target];
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(new int[]{1, 2, 3}, 4).expect(7);

}
