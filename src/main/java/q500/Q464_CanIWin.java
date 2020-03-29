package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/can-i-win/
 *
 * In the "100 game," two players take turns adding, to a running total, any integer from 1..10. The player who first
 * causes the running total to reach or exceed 100 wins.
 *
 * What if we change the game so that players cannot re-use integers?
 *
 * For example, two players might take turns drawing from a common pool of numbers of 1..15 without replacement until
 * they reach a total >= 100.
 *
 * Given an integer maxChoosableInteger and another integer desiredTotal, determine if the first player to move can
 * force a win, assuming both players play optimally.
 *
 * You can always assume that maxChoosableInteger will not be larger than 20 and desiredTotal will not be larger than
 * 300.
 *
 * Example
 *
 * Input:
 * maxChoosableInteger = 10
 * desiredTotal = 11
 *
 * Output:
 * false
 *
 * Explanation:
 * No matter which integer the first player choose, the first player will lose.
 * The first player can choose an integer from 1 up to 10.
 * If the first player choose 1, the second player can only choose integers from 2 up to 10.
 * The second player will win by choosing 10 and get a total = 11, which is >= desiredTotal.
 * Same with other integers chosen by the first player, the second player will always win.
 */
@RunWith(LeetCodeRunner.class)
public class Q464_CanIWin {

    // 参考 https://www.cnblogs.com/grandyang/p/6089060.html 和 LeetCode 中的解答
    // 使用整数的位来表示是否访问, 并缓存结果.
    @Answer
    public boolean canIWin(int maxChoosableInteger, int desiredTotal) {
        // 当前玩家可以直接加到结果
        if (maxChoosableInteger >= desiredTotal) {
            return true;
        }
        // 所有数相加都到不了结果
        if (maxChoosableInteger * (maxChoosableInteger + 1) / 2 < desiredTotal) {
            return false;
        }
        byte[] cache = new byte[(1 << (maxChoosableInteger + 1)) - 1];
        return canWin(maxChoosableInteger, desiredTotal, 0, cache);
    }

    /**
     * 递归计算结果
     *
     * @param maxChoosableInteger 最多可选择的数字, 从1 到 maxChoosableInteger
     * @param total 期望的结果
     * @param visited 从 1 - maxChoosableInteger 位分别表示改为所代表的数字是否被使用过了
     * @param cache 缓存visited 和对应的结果, 0 表示没有, 1 表示true, -1 表示false
     * @return 该玩家是否能赢
     */
    public boolean canWin(int maxChoosableInteger, int total, int visited, byte[] cache) {
        if (cache[visited] != 0) {
            return cache[visited] == 1;
        }
        for (int i = 1; i <= maxChoosableInteger; i++) {
            if ((1 & (visited >> i)) == 0) {
                if (i >= total || !canWin(maxChoosableInteger, total - i, visited | (1 << i), cache)) {
                    cache[visited] = 1;
                    return true;
                }
            }
        }
        cache[visited] = -1;
        return false;
    }


    @TestData
    public DataExpectation exmaple = DataExpectation.createWith(10, 11).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(10, 40).expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(4, 6).expect(true);

}
