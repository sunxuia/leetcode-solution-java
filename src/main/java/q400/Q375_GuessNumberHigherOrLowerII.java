package q400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/guess-number-higher-or-lower-ii/
 *
 * We are playing the Guess Game. The game is as follows:
 *
 * I pick a number from 1 to n. You have to guess which number I picked.
 *
 * Every time you guess wrong, I'll tell you whether the number I picked is higher or lower.
 *
 * However, when you guess a particular number x, and you guess wrong, you pay $x. You win the game when you guess
 * the number I picked.
 *
 * Example:
 *
 * n = 10, I pick 8.
 *
 * First round:  You guess 5, I tell you that it's higher. You pay $5.
 * Second round: You guess 7, I tell you that it's higher. You pay $7.
 * Third round:  You guess 9, I tell you that it's lower. You pay $9.
 *
 * Game over. 8 is the number I picked.
 *
 * You end up paying $5 + $7 + $9 = $21.
 *
 * Given a particular n ≥ 1, find out how much money you need to have to guarantee a win.
 */
@RunWith(LeetCodeRunner.class)
public class Q375_GuessNumberHigherOrLowerII {

    // 道题需要用到 Minimax 极小化极大算法
    // https://www.iteye.com/blog/univasity-1170216
    // 解法参考 https://www.cnblogs.com/grandyang/p/5677550.html
    @Answer
    public int getMoneyAmount(int n) {
        return helper(1, n, new int[n + 1][n + 1]);
    }

    /**
     * 结果在 [start, end] 这个区间内的猜测所需要的最少花费
     */
    private int helper(int start, int end, int[][] cache) {
        if (start >= end) {
            return 0;
        }
        if (cache[start][end] > 0) {
            return cache[start][end];
        }
        int res = Integer.MAX_VALUE;
        // 猜数字是 k 的时候, 需要的花费是多少, 取花费最少的 k 的结果
        for (int k = start; k <= end; k++) {
            // 在左边区间所需要的最少花费和右边区间所需要的最少花费.
            // 因为结果可能在左右之中的任一个, 所以取最大值
            int t = k + Math.max(helper(start, k - 1, cache), helper(k + 1, end, cache));
            res = Math.min(res, t);
        }
        cache[start][end] = res;
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(10).expect(16);

}
