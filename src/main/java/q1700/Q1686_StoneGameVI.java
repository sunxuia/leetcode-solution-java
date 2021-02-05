package q1700;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import q1600.Q1563_StoneGameV;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1686. Stone Game VI
 * https://leetcode.com/problems/stone-game-vi/
 *
 * Alice and Bob take turns playing a game, with Alice starting first.
 *
 * There are n stones in a pile. On each player's turn, they can remove a stone from the pile and receive points based
 * on the stone's value. Alice and Bob may value the stones differently.
 *
 * You are given two integer arrays of length n, aliceValues and bobValues. Each aliceValues[i] and bobValues[i]
 * represents how Alice and Bob, respectively, value the ith stone.
 *
 * The winner is the person with the most points after all the stones are chosen. If both players have the same amount
 * of points, the game results in a draw. Both players will play optimally. Both players know the other's values.
 *
 * Determine the result of the game, and:
 *
 * If Alice wins, return 1.
 * If Bob wins, return -1.
 * If the game results in a draw, return 0.
 *
 * Example 1:
 *
 * Input: aliceValues = [1,3], bobValues = [2,1]
 * Output: 1
 * Explanation:
 * If Alice takes stone 1 (0-indexed) first, Alice will receive 3 points.
 * Bob can only choose stone 0, and will only receive 2 points.
 * Alice wins.
 *
 * Example 2:
 *
 * Input: aliceValues = [1,2], bobValues = [3,1]
 * Output: 0
 * Explanation:
 * If Alice takes stone 0, and Bob takes stone 1, they will both have 1 point.
 * Draw.
 *
 * Example 3:
 *
 * Input: aliceValues = [2,4,3], bobValues = [1,6,7]
 * Output: -1
 * Explanation:
 * Regardless of how Alice plays, Bob will be able to have more points than Alice.
 * For example, if Alice takes stone 1, Bob can take stone 2, and Alice takes stone 0, Alice will have 6 points to Bob's
 * 7.
 * Bob wins.
 *
 * Constraints:
 *
 * n == aliceValues.length == bobValues.length
 * 1 <= n <= 10^5
 * 1 <= aliceValues[i], bobValues[i] <= 100
 *
 * 上一题 {@link Q1563_StoneGameV}
 * 下一题 {@link Q1690_StoneGameVII}
 */
@RunWith(LeetCodeRunner.class)
public class Q1686_StoneGameVI {

    /**
     * 根据提示可以使用贪婪算法, 每次选择 aliceValues[i] + bobValues[i] 最大的结果.
     *
     * 证明过程:
     * https://leetcode-cn.com/problems/stone-game-vi/solution/tan-xin-by-o0o0o0o0o0-rzvp/
     *
     * Alice的收益为 Alice[a1] + Alice[a2] + ... + Alice[ak]
     * Bob的收益为 Sum(Bob) − Bob[a1] − Bob[a2] − ... − Bob[ak]
     * (a1, a2 ... ak 是 Alice 选择的石头的下标).
     *
     * 则Alice 的最大化收益可以等价为
     * S = (Alice[a1] + Bob[a1]) + (Alice[a2] + Bob[a2]) + ... + (Alice[ak] + Bob[Ak]) 与 Sum(Bob) 的比较结果.
     *
     * 因此Alice 和Bob 的策略就是优先选择最大的 Alice[i] + Bob[i] 的最大结果,
     * 这样对Alice 来说S 可以尽可能大, 对Bob 来说 S 可以尽可能小.
     */
    @Answer
    public int stoneGameVI(int[] aliceValues, int[] bobValues) {
        final int n = aliceValues.length;
        Integer[] sort = new Integer[n];
        for (int i = 0; i < n; i++) {
            sort[i] = i;
        }
        Arrays.sort(sort, Comparator.comparingInt(
                i -> -aliceValues[i] - bobValues[i]));

        int alice = 0, bob = 0;
        for (int i = 0; i < n; i++) {
            if (i % 2 == 0) {
                alice += aliceValues[sort[i]];
            } else {
                bob += bobValues[sort[i]];
            }
        }
        return Integer.compare(alice, bob);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 3}, new int[]{2, 1})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2}, new int[]{3, 1})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2, 4, 3}, new int[]{1, 6, 7})
            .expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(
            new int[]{9, 9, 5, 5, 2, 8, 2, 4, 10, 2, 3, 3, 4},
            new int[]{9, 5, 3, 4, 4, 6, 6, 6, 4, 3, 7, 5, 10})
            .expect(1);

}
