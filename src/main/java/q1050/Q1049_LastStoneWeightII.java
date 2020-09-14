package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1049. Last Stone Weight II
 * https://leetcode.com/problems/last-stone-weight-ii/
 *
 * We have a collection of rocks, each rock has a positive integer weight.
 *
 * Each turn, we choose any two rocks and smash them together.  Suppose the stones have weights x and y with x <= y.
 * The result of this smash is:
 *
 * If x == y, both stones are totally destroyed;
 * If x != y, the stone of weight x is totally destroyed, and the stone of weight y has new weight y-x.
 *
 * At the end, there is at most 1 stone left.  Return the smallest possible weight of this stone (the weight is 0 if
 * there are no stones left.)
 *
 * Example 1:
 *
 * Input: [2,7,4,1,8,1]
 * Output: 1
 * Explanation:
 * We can combine 2 and 4 to get 2 so the array converts to [2,7,1,8,1] then,
 * we can combine 7 and 8 to get 1 so the array converts to [2,1,1,1] then,
 * we can combine 2 and 1 to get 1 so the array converts to [1,1,1] then,
 * we can combine 1 and 1 to get 0 so the array converts to [1] then that's the optimal value.
 *
 * Note:
 *
 * 1 <= stones.length <= 30
 * 1 <= stones[i] <= 100
 *
 * 上一题 {@link Q1046_LastStoneWeight}
 */
@RunWith(LeetCodeRunner.class)
public class Q1049_LastStoneWeightII {

    /**
     * 根据hint 得到的提示, 这题可以使用dp 来做.
     * 这题其实就是数字的加减结果(扣除全加全减)
     */
    @Answer
    public int lastStoneWeightII(int[] stones) {
        final int n = stones.length, max = n * 100;
        // dp[i][max+val] 表示对于 stones[0, i), 是否可以组成结果 val.
        // 注意这里可能会加上全部的石头或减去全部的石头, 但是因为肯定会小于加减交错的情况, 所以最后肯定不会命中结果.
        boolean[][] dp = new boolean[n + 1][max * 2 + 1];
        dp[0][max] = true;
        for (int i = 0; i < n; i++) {
            for (int val = -max; val <= max; val++) {
                if (dp[i][max + val]) {
                    dp[i + 1][max + val + stones[i]] = true;
                    dp[i + 1][max + val - stones[i]] = true;
                }
            }
        }

        for (int i = 0; i <= max; i++) {
            if (dp[n][max + i] || dp[n][max - i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * LeetCode 上比较快的解法.
     */
    @Answer
    public int lastStoneWeightII2(int[] stones) {
        final int n = stones.length, max = n * 100;
        // dp[i] 表示加入若干石头的组合中, 被减数字的和是否是 i.
        boolean[] dp = new boolean[max / 2 + 1];
        dp[0] = true;

        // sum 表示所有石头的总和
        int sum = 0;
        for (int stone : stones) {
            sum += stone;
            // 减去当前石头的情况, 从后往前避免干扰
            for (int i = Math.min(max / 2, sum); i >= stone; i--) {
                dp[i] |= dp[i - stone];
            }
        }

        for (int i = sum / 2; i >= 0; i--) {
            if (dp[i]) {
                // sum - 被减石头应该从sum 扣掉的值 - 被减石头的值
                return sum - i - i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 7, 4, 1, 8, 1}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{31, 26, 33, 21, 40}).expect(5);

}
