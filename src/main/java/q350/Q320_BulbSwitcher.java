package q350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/bulb-switcher/
 *
 * There are n bulbs that are initially off. You first turn on all the bulbs. Then, you turn off every second bulb.
 * On the third round, you toggle every third bulb (turning on if it's off or turning off if it's on). For the i-th
 * round, you toggle every i bulb. For the n-th round, you only toggle the last bulb. Find how many bulbs are on
 * after n rounds.
 *
 * Example:
 *
 * Input: 3
 * Output: 1
 * Explanation:
 * At first, the three bulbs are [off, off, off].
 * After first round, the three bulbs are [on, on, on].
 * After second round, the three bulbs are [on, off, on].
 * After third round, the three bulbs are [on, off, off].
 *
 * So you should return 1, because there is only one bulb is on.
 */
@RunWith(LeetCodeRunner.class)
public class Q320_BulbSwitcher {

    // 符合题意的解法, 这个会超时
    public int bruceForce(int n) {
        boolean[] bulbs = new boolean[n + 1];
        for (int step = 1; step <= n; step++) {
            for (int i = step; i <= n; i += step) {
                bulbs[i] = !bulbs[i];
            }
        }
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += bulbs[i] ? 1 : 0;
        }
        return res;
    }

    /**
     * https://www.cnblogs.com/grandyang/p/5100098.html
     * 这题实际上是一个计算质数的题目, 在纸上画一画每位在第几次变化就知道了.
     * 实际上题目就是求 n 的平方的结果.
     */
    @Answer
    public int bulbSwitch(int n) {
        return (int) Math.sqrt(n);
    }

    @TestData
    public DataExpectation example = DataExpectation.create(3).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(99999999).expect(9999);

}
