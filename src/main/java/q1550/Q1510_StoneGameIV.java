package q1550;

import org.junit.runner.RunWith;
import q1450.Q1406_StoneGameIII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1510. Stone Game IV
 * https://leetcode.com/problems/stone-game-iv/
 *
 * Alice and Bob take turns playing a game, with Alice starting first.
 *
 * Initially, there are n stones in a pile.  On each player's turn, that player makes a move consisting of removing any
 * non-zero square number of stones in the pile.
 *
 * Also, if a player cannot make a move, he/she loses the game.
 *
 * Given a positive integer n. Return True if and only if Alice wins the game otherwise return False, assuming both
 * players play optimally.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: true
 * Explanation: Alice can remove 1 stone winning the game because Bob doesn't have any moves.
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: false
 * Explanation: Alice can only remove 1 stone, after that Bob removes the last one winning the game (2 -> 1 -> 0).
 *
 * Example 3:
 *
 * Input: n = 4
 * Output: true
 * Explanation: n is already a perfect square, Alice can win with one move, removing 4 stones (4 -> 0).
 *
 * Example 4:
 *
 * Input: n = 7
 * Output: false
 * Explanation: Alice can't win the game if Bob plays optimally.
 * If Alice starts removing 4 stones, Bob will remove 1 stone then Alice should remove only 1 stone and finally Bob
 * removes the last one (7 -> 3 -> 2 -> 1 -> 0).
 * If Alice starts removing 1 stone, Bob will remove 4 stones then Alice only can remove 1 stone and finally Bob removes
 * the last one (7 -> 6 -> 2 -> 1 -> 0).
 *
 * Example 5:
 *
 * Input: n = 17
 * Output: false
 * Explanation: Alice can't win the game if Bob plays optimally.
 *
 * Constraints:
 *
 * 1 <= n <= 10^5
 *
 * 上一题 {@link Q1406_StoneGameIII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1510_StoneGameIV {

    /**
     * 简单的dfs 会超时.
     */
    @Answer
    public boolean winnerSquareGame(int n) {
        boolean[] dp = new boolean[n + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j * j <= i && !dp[i]; j++) {
                dp[i] = !dp[i - j * j];
            }
        }
        return dp[n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(4).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.create(7).expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation.create(17).expect(false);

    @TestData
    public DataExpectation overTime = DataExpectation.create(99).expect(true);

}
