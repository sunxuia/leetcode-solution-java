package q1600;

import org.junit.runner.RunWith;
import q1550.Q1510_StoneGameIV;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1563. Stone Game V
 * https://leetcode.com/problems/stone-game-v/
 *
 * There are several stones arranged in a row, and each stone has an associated value which is an integer given in the
 * array stoneValue.
 *
 * In each round of the game, Alice divides the row into two non-empty rows (i.e. left row and right row), then Bob
 * calculates the value of each row which is the sum of the values of all the stones in this row. Bob throws away the
 * row which has the maximum value, and Alice's score increases by the value of the remaining row. If the value of the
 * two rows are equal, Bob lets Alice decide which row will be thrown away. The next round starts with the remaining
 * row.
 *
 * The game ends when there is only one stone remaining. Alice's is initially zero.
 *
 * Return the maximum score that Alice can obtain.
 *
 * Example 1:
 *
 * Input: stoneValue = [6,2,3,4,5,5]
 * Output: 18
 * Explanation: In the first round, Alice divides the row to [6,2,3], [4,5,5]. The left row has the value 11 and the
 * right row has value 14. Bob throws away the right row and Alice's score is now 11.
 * In the second round Alice divides the row to [6], [2,3]. This time Bob throws away the left row and Alice's score
 * becomes 16 (11 + 5).
 * The last round Alice has only one choice to divide the row which is [2], [3]. Bob throws away the right row and
 * Alice's score is now 18 (16 + 2). The game ends because only one stone is remaining in the row.
 *
 * Example 2:
 *
 * Input: stoneValue = [7,7,7,7,7,7,7]
 * Output: 28
 *
 * Example 3:
 *
 * Input: stoneValue = [4]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= stoneValue.length <= 500
 * 1 <= stoneValue[i] <= 10^6
 *
 * 上一题 {@link Q1510_StoneGameIV}
 *
 * 题解: 题目中有个隐含条件就是Alice 切分的时候不会改变元素顺序和位置, 只是在数组中间分割.
 */
@RunWith(LeetCodeRunner.class)
public class Q1563_StoneGameV {

    @Answer
    public int stoneGameV(int[] stoneValue) {
        final int n = stoneValue.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + stoneValue[i];
        }
        int[][] dp = new int[n][n];
        for (int step = 1; step < n; step++) {
            for (int s = 0, e = s + step; e < n; s++, e++) {
                for (int i = s + 1; i <= e; i++) {
                    if (sums[i] - sums[s] >= sums[e + 1] - sums[i]) {
                        dp[s][e] = Math.max(dp[s][e], sums[e + 1] - sums[i] + dp[i][e]);
                    }
                    if (sums[i] - sums[s] <= sums[e + 1] - sums[i]) {
                        dp[s][e] = Math.max(dp[s][e], sums[i] - sums[s] + dp[s][i - 1]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * LeetCode 上更快的解法.
     * 参考文档 https://leetcode-cn.com/problems/stone-game-v/solution/shi-zi-you-xi-v-by-leetcode-solution/
     */
    @Answer
    public int stoneGameV2(int[] stoneValue) {
        final int n = stoneValue.length;
        int[][] dp = new int[n][n];
        int[][] maxLeft = new int[n][n];
        int[][] maxRight = new int[n][n];
        for (int left = n - 1; left >= 0; left--) {
            maxLeft[left][left] = maxRight[left][left] = stoneValue[left];
            int sum = stoneValue[left], suml = 0;
            for (int right = left + 1, i = left - 1; right < n; right++) {
                sum += stoneValue[right];
                while (i + 1 < right && (suml + stoneValue[i + 1]) * 2 <= sum) {
                    suml += stoneValue[i + 1];
                    i++;
                }
                if (left <= i) {
                    dp[left][right] = Math.max(dp[left][right], maxLeft[left][i]);
                }
                if (i + 1 < right) {
                    dp[left][right] = Math.max(dp[left][right], maxRight[i + 2][right]);
                }
                if (suml * 2 == sum) {
                    dp[left][right] = Math.max(dp[left][right], maxRight[i + 1][right]);
                }
                maxLeft[left][right] = Math.max(maxLeft[left][right - 1], sum + dp[left][right]);
                maxRight[left][right] = Math.max(maxRight[left + 1][right], sum + dp[left][right]);
            }
        }
        return dp[0][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{6, 2, 3, 4, 5, 5}).expect(18);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{7, 7, 7, 7, 7, 7, 7}).expect(28);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{4}).expect(0);

}
