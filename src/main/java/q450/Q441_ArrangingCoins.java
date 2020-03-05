package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/arranging-coins/
 *
 * You have a total of n coins that you want to form in a staircase shape, where every k-th row must have exactly k
 * coins.
 *
 * Given n, find the total number of full staircase rows that can be formed.
 *
 * n is a non-negative integer and fits within the range of a 32-bit signed integer.
 *
 * Example 1:
 *
 * n = 5
 *
 * The coins can form the following rows:
 * ¤
 * ¤ ¤
 * ¤ ¤
 *
 * Because the 3rd row is incomplete, we return 2.
 *
 * Example 2:
 *
 * n = 8
 *
 * The coins can form the following rows:
 * ¤
 * ¤ ¤
 * ¤ ¤ ¤
 * ¤ ¤
 *
 * Because the 4th row is incomplete, we return 3.
 */
@RunWith(LeetCodeRunner.class)
public class Q441_ArrangingCoins {

    /**
     * 就是求解不等式的最大值, 设台阶数为x, 则可得公式 (1+x)*x/2 <= n,
     * 变换后为 x <= √(2n+1/4) - 1/2, 求x 的最大整数解
     */
    @Answer
    public int arrangeCoins(int n) {
        return (int) (Math.sqrt(2.0 * n + 0.25) - 0.5);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(8).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1804289383).expect(60070);

}
