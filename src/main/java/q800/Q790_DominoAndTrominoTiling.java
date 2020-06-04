package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/domino-and-tromino-tiling/
 *
 * We have two types of tiles: a 2x1 domino shape, and an "L" tromino shape. These shapes may be rotated.
 *
 * XX  <- domino
 *
 * XX  <- "L" tromino
 * X
 *
 * Given N, how many ways are there to tile a 2 x N board? Return your answer modulo 10^9 + 7.
 *
 * (In a tiling, every square must be covered by a tile. Two tilings are different if and only if there are two
 * 4-directionally adjacent cells on the board such that exactly one of the tilings has both squares occupied by a
 * tile.)
 *
 * Example:
 * Input: 3
 * Output: 5
 * Explanation:
 * The five different ways are listed below, different letters indicates different tiles:
 * XYZ XXZ XYY XXY XYY
 * XYZ YYZ XZZ XYY XXY
 *
 * Note:
 *
 * N  will be in range [1, 1000].
 */
@RunWith(LeetCodeRunner.class)
public class Q790_DominoAndTrominoTiling {

    // (看不懂题目什么意思)
    // https://www.cnblogs.com/grandyang/p/9179556.html
    @Answer
    public int numTilings(int N) {
        long[] dp = new long[Math.max(3, N + 1)];
        dp[0] = 1;
        dp[1] = 1;
        dp[2] = 2;
        for (int i = 3; i <= N; ++i) {
            dp[i] = (dp[i - 1] * 2 + dp[i - 3]) % 10_0000_0007;
        }
        return (int) dp[N];
    }

    @TestData
    public DataExpectation example = DataExpectation.create(3).expect(5);

    @TestData
    public DataExpectation value1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation value30 = DataExpectation.create(30).expect(312342182);

}
