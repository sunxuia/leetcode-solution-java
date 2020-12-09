package q1450;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q1150.Q1140_StoneGameII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1406. Stone Game III
 * https://leetcode.com/problems/stone-game-iii/
 *
 * Alice and Bob continue their games with piles of stones. There are several stones arranged in a row, and each stone
 * has an associated value which is an integer given in the array stoneValue.
 *
 * Alice and Bob take turns, with Alice starting first. On each player's turn, that player can take 1, 2 or 3 stones
 * from the first remaining stones in the row.
 *
 * The score of each player is the sum of values of the stones taken. The score of each player is 0 initially.
 *
 * The objective of the game is to end with the highest score, and the winner is the player with the highest score and
 * there could be a tie. The game continues until all the stones have been taken.
 *
 * Assume Alice and Bob play optimally.
 *
 * Return "Alice" if Alice will win, "Bob" if Bob will win or "Tie" if they end the game with the same score.
 *
 * Example 1:
 *
 * Input: values = [1,2,3,7]
 * Output: "Bob"
 * Explanation: Alice will always lose. Her best move will be to take three piles and the score become 6. Now the score
 * of Bob is 7 and Bob wins.
 *
 * Example 2:
 *
 * Input: values = [1,2,3,-9]
 * Output: "Alice"
 * Explanation: Alice must choose all the three piles at the first move to win and leave Bob with negative score.
 * If Alice chooses one pile her score will be 1 and the next move Bob's score becomes 5. The next move Alice will take
 * the pile with value = -9 and lose.
 * If Alice chooses two piles her score will be 3 and the next move Bob's score becomes 3. The next move Alice will take
 * the pile with value = -9 and also lose.
 * Remember that both play optimally so here Alice will choose the scenario that makes her win.
 *
 * Example 3:
 *
 * Input: values = [1,2,3,6]
 * Output: "Tie"
 * Explanation: Alice cannot win this game. She can end the game in a draw if she decided to choose all the first three
 * piles, otherwise she will lose.
 *
 * Example 4:
 *
 * Input: values = [1,2,3,-1,-2,-3,7]
 * Output: "Alice"
 *
 * Example 5:
 *
 * Input: values = [-1,-2,-3]
 * Output: "Tie"
 *
 * Constraints:
 *
 * 1 <= values.length <= 50000
 * -1000 <= values[i] <= 1000
 *
 * 上一题 {@link Q1140_StoneGameII}
 * 和上题相比就是在取走的石头的数量上有变化.
 */
@RunWith(LeetCodeRunner.class)
public class Q1406_StoneGameIII {

    /**
     * 这个可以通过LeetCode 的OJ, 但是本地需要调整方法栈的大小.
     */
//    @Answer
    public String stoneGameIII(int[] stoneValue) {
        final int n = stoneValue.length;
        int sum = Arrays.stream(stoneValue).sum();
        int[] cache = new int[n + 1];
        Arrays.fill(cache, Integer.MIN_VALUE);
        cache[n] = 0;
        int alice = take(sum, cache, 0, stoneValue);
        return alice * 2 == sum ? "Tie"
                : alice * 2 > sum ? "Alice" : "Bob";
    }

    private int take(final int sum, int[] cache, int start, int[] stoneValue) {
        final int n = stoneValue.length;
        if (cache[start] != Integer.MIN_VALUE) {
            return cache[start];
        }
        int other = Integer.MAX_VALUE, rest = sum;
        for (int i = start; i < n && i < start + 3; i++) {
            rest -= stoneValue[i];
            other = Math.min(other, take(rest, cache, i + 1, stoneValue));
        }
        return cache[start] = sum - other;
    }

    /**
     * 根据上面解法的dp 改写.
     */
    @Answer
    public String stoneGameIII2(int[] stoneValue) {
        final int n = stoneValue.length;
        int[] dp = new int[n + 3];
        int sum = 0;
        for (int i = n - 1; i >= 0; i--) {
            sum += stoneValue[i];
            dp[i] = sum - Math.min(dp[i + 1], Math.min(dp[i + 2], dp[i + 3]));
        }
        return dp[0] * 2 == sum ? "Tie"
                : dp[0] * 2 > sum ? "Alice" : "Bob";
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 7}).expect("Bob");

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 3, -9}).expect("Alice");

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 6}).expect("Tie");

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{1, 2, 3, -1, -2, -3, 7}).expect("Alice");

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{-1, -2, -3}).expect("Tie");

    @TestData
    public DataExpectation overTime = DataExpectation.create(TestDataFileHelper.read(int[].class)).expect("Alice");

}
