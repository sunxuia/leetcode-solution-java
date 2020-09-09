package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1025. Divisor Game
 * https://leetcode.com/problems/divisor-game/
 *
 * Alice and Bob take turns playing a game, with Alice starting first.
 *
 * Initially, there is a number N on the chalkboard.  On each player's turn, that player makes a move consisting of:
 *
 * Choosing any x with 0 < x < N and N % x == 0.
 * Replacing the number N on the chalkboard with N - x.
 *
 * Also, if a player cannot make a move, they lose the game.
 *
 * Return True if and only if Alice wins the game, assuming both players play optimally.
 *
 * Example 1:
 *
 * Input: 2
 * Output: true
 * Explanation: Alice chooses 1, and Bob has no more moves.
 *
 * Example 2:
 *
 * Input: 3
 * Output: false
 * Explanation: Alice chooses 1, Bob chooses 1, and Alice has no more moves.
 *
 * Note:
 *
 * 1 <= N <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1025_DivisorGame {

    /**
     * 带缓存的dfs, 单纯dfs 会超时
     */
    @Answer
    public boolean divisorGame(int N) {
        int[] cache = new int[N + 1];
        cache[1] = -1;
        return dfs(N, cache);
    }

    private boolean dfs(int n, int[] cache) {
        if (cache[n] != 0) {
            return cache[n] == 1;
        }
        for (int i = n / 2; i >= 1; i--) {
            if (n % i == 0 && !dfs(n - i, cache)) {
                cache[n] = 1;
                return true;
            }
        }
        cache[n] = -1;
        return false;
    }

    /**
     * DP 解法, 根据上面解法改进而来.
     * 这个因为会计算中间的一些不会用到的状态所以效率要比上面的慢.
     */
    @Answer
    public boolean divisorGame2(int N) {
        boolean[] dp = new boolean[N + 1];
        for (int i = 2; i <= N; i++) {
            for (int j = 1; j <= i / 2; j++) {
                if (i % j == 0 && !dp[i - j]) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[N];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(3).expect(false);

    @TestData
    public DataExpectation overTime1 = DataExpectation.create(196).expect(true);

    @TestData
    public DataExpectation overTime2 = DataExpectation.create(377).expect(false);

}
