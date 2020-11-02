package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1269. Number of Ways to Stay in the Same Place After Some Steps
 * https://leetcode.com/problems/number-of-ways-to-stay-in-the-same-place-after-some-steps/
 *
 * You have a pointer at index 0 in an array of size arrLen. At each step, you can move 1 position to the left, 1
 * position to the right in the array or stay in the same place  (The pointer should not be placed outside the array at
 * any time).
 *
 * Given two integers steps and arrLen, return the number of ways such that your pointer still at index 0 after exactly
 * steps steps.
 *
 * Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: steps = 3, arrLen = 2
 * Output: 4
 * Explanation: There are 4 differents ways to stay at index 0 after 3 steps.
 * Right, Left, Stay
 * Stay, Right, Left
 * Right, Stay, Left
 * Stay, Stay, Stay
 *
 * Example 2:
 *
 * Input: steps = 2, arrLen = 4
 * Output: 2
 * Explanation: There are 2 differents ways to stay at index 0 after 2 steps
 * Right, Left
 * Stay, Stay
 *
 * Example 3:
 *
 * Input: steps = 4, arrLen = 2
 * Output: 8
 *
 * Constraints:
 *
 * 1 <= steps <= 500
 * 1 <= arrLen <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1269_NumberOfWaysToStayInTheSamePlaceAfterSomeSteps {

    @Answer
    public int numWays(int steps, int arrLen) {
        final int mod = 10_0000_0007;
        // 左右 2 边是哨兵
        int[][] dp = new int[2][Math.min(steps + 1, arrLen) + 2];
        dp[0][1] = dp[0][2] = 1;
        for (int step = 1; step < steps; step++) {
            int[] curr = dp[step % 2], prev = dp[(step - 1) % 2];
            // i <= step+2 这个限制用于将时间复杂度限制在 O(step^2)
            for (int i = 1; i <= arrLen && i <= step + 2; i++) {
                curr[i] = ((prev[i - 1] + prev[i]) % mod + prev[i + 1]) % mod;
            }
        }
        return dp[(steps - 1) % 2][1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 2).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 4).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(4, 2).expect(8);

    // 这个测试用例如果空间复杂度为 O(steps*arrLen), 在 OJ 上会超过内存限制
    @TestData
    public DataExpectation overMemory = DataExpectation.createWith(430, 148488).expect(525833932);

    // 这个测试用例如果时间复杂度为 O(steps*arrLen), 在 OJ 上会超时
    @TestData
    public DataExpectation overTime = DataExpectation.createWith(500, 969997).expect(374847123);

}
