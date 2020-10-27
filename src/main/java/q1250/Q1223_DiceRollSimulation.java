package q1250;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1223. Dice Roll Simulation
 * https://leetcode.com/problems/dice-roll-simulation/
 *
 * A die simulator generates a random number from 1 to 6 for each roll. You introduced a constraint to the generator
 * such that it cannot roll the number i more than rollMax[i] (1-indexed) consecutive times.
 *
 * Given an array of integers rollMax and an integer n, return the number of distinct sequences that can be obtained
 * with exact n rolls.
 *
 * Two sequences are considered different if at least one element differs from each other. Since the answer may be too
 * large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 2, rollMax = [1,1,2,2,2,3]
 * Output: 34
 * Explanation: There will be 2 rolls of die, if there are no constraints on the die, there are 6 * 6 = 36 possible
 * combinations. In this case, looking at rollMax array, the numbers 1 and 2 appear at most once consecutively,
 * therefore sequences (1,1) and (2,2) cannot occur, so the final answer is 36-2 = 34.
 *
 * Example 2:
 *
 * Input: n = 2, rollMax = [1,1,1,1,1,1]
 * Output: 30
 *
 * Example 3:
 *
 * Input: n = 3, rollMax = [1,1,1,2,2,3]
 * Output: 181
 *
 * Constraints:
 *
 * 1 <= n <= 5000
 * rollMax.length == 6
 * 1 <= rollMax[i] <= 15
 */
@RunWith(LeetCodeRunner.class)
public class Q1223_DiceRollSimulation {

    /**
     * 参考文档 https://www.acwing.com/solution/LeetCode/content/5270/
     */
    @Answer
    public int dieSimulator(int n, int[] rollMax) {
        final int mod = 10_0000_0007;
        // dp[i][j][k] 表示长度为i 的以k 个j 结尾的组合的数量.
        int[][][] dp = new int[n + 1][7][16];
        for (int i = 1; i <= 6; i++) {
            dp[1][i][1] = 1;
        }
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= 6; j++) {
                // 以1个j 结尾, 就是dp[i-1] 非j 结尾之和
                for (int k = 1; k <= 6; k++) {
                    if (k != j) {
                        for (int l = 1; l <= 15; l++) {
                            dp[i][j][1] = (dp[i][j][1] + dp[i - 1][k][l]) % mod;
                        }
                    }
                }
                // 以 k 个j 结尾, 就是dp[i-1] 的 k-1 结尾之和
                // 且结尾长度不能超过 rollMax[j-1]
                for (int k = 2; k <= i && k <= rollMax[j - 1]; k++) {
                    dp[i][j][k] = dp[i - 1][j][k - 1];
                }
            }
        }

        int res = 0;
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 15; j++) {
                res = (res + dp[n][i][j]) % mod;
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解答.
     */
    @Answer
    public int dieSimulator2(int n, int[] rollMax) {
        final int mod = 10_0000_0007;
        // dp[i][j] 表示长度为i, 以j 结尾的组合数量.
        // dp[i][0] 是dp[i][1:6] 的总和.
        int[][] dp = new int[n + 1][7];
        Arrays.fill(dp[1], 1);
        dp[1][0] = 6;

        for (int i = 2; i <= n; i++) {
            int total = 0;
            for (int j = 1; j <= 6; j++) {
                // 超过长度限制的起始位置
                int prev = i - rollMax[j - 1] - 1;
                // j 附在 i-1 长度的组合上组成i 长度的结果.
                dp[i][j] = dp[i - 1][0];
                // 刚好超过长度限制, 减去这个超过限制的组合.
                if (prev == 0) {
                    dp[i][j]--;
                }
                // 超过长度限制1 个以上的字符.
                if (prev > 0) {
                    // 表示 dp[prev] 的总和扣掉以 j 结尾的数量,
                    // 因为这里以j 结尾的已经在之前 dp[i-1] 的时候扣掉了.
                    int reduction = dp[prev][0] - dp[prev][j];
                    dp[i][j] = ((dp[i][j] - reduction) % mod + mod) % mod;
                }
                total = (total + dp[i][j]) % mod;
            }
            dp[i][0] = total;
        }
        return dp[n][0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, new int[]{1, 1, 2, 2, 2, 3}).expect(34);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, new int[]{1, 1, 1, 1, 1, 1}).expect(30);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, new int[]{1, 1, 1, 2, 2, 3}).expect(181);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(4, new int[]{2, 1, 1, 3, 3, 2}).expect(1082);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(30, new int[]{2, 3, 1, 2, 1, 2}).expect(753152086);

}
