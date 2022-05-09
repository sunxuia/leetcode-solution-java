package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1900. The Earliest and Latest Rounds Where Players Compete
 * https://leetcode.com/problems/the-earliest-and-latest-rounds-where-players-compete/
 *
 * There is a tournament where n players are participating. The players are standing in a single row and are numbered
 * from 1 to n based on their initial standing position (player 1 is the first player in the row, player 2 is the second
 * player in the row, etc.).
 *
 * The tournament consists of multiple rounds (starting from round number 1). In each round, the ith player from the
 * front of the row competes against the ith player from the end of the row, and the winner advances to the next round.
 * When the number of players is odd for the current round, the player in the middle automatically advances to the next
 * round.
 *
 * For example, if the row consists of players 1, 2, 4, 6, 7
 *
 * Player 1 competes against player 7.
 * Player 2 competes against player 6.
 * Player 4 automatically advances to the next round.
 *
 * After each round is over, the winners are lined back up in the row based on the original ordering assigned to them
 * initially (ascending order).
 *
 * The players numbered firstPlayer and secondPlayer are the best in the tournament. They can win against any other
 * player before they compete against each other. If any two other players compete against each other, either of them
 * might win, and thus you may choose the outcome of this round.
 *
 * Given the integers n, firstPlayer, and secondPlayer, return an integer array containing two values, the earliest
 * possible round number and the latest possible round number in which these two players will compete against each
 * other, respectively.
 *
 * Example 1:
 *
 * Input: n = 11, firstPlayer = 2, secondPlayer = 4
 * Output: [3,4]
 * Explanation:
 * One possible scenario which leads to the earliest round number:
 * First round: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
 * Second round: 2, 3, 4, 5, 6, 11
 * Third round: 2, 3, 4
 * One possible scenario which leads to the latest round number:
 * First round: 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11
 * Second round: 1, 2, 3, 4, 5, 6
 * Third round: 1, 2, 4
 * Fourth round: 2, 4
 *
 * Example 2:
 *
 * Input: n = 5, firstPlayer = 1, secondPlayer = 5
 * Output: [1,1]
 * Explanation: The players numbered 1 and 5 compete in the first round.
 * There is no way to make them compete in any other round.
 *
 * Constraints:
 *
 * 2 <= n <= 28
 * 1 <= firstPlayer < secondPlayer <= n
 */
@RunWith(LeetCodeRunner.class)
public class Q1900_TheEarliestAndLatestRoundsWherePlayersCompete {

    /**
     * leetcode 上最快的做法:
     *
     * Algorithm: Recursion with 2 pointers
     * Intuitive: With the insight about tracking the number of players between champions, we can simplify the
     * algorithm by computing possible transitions instead of simulating individual competitions. Say, we have 15
     * total players; 4 players on the left and 5 - on the right (including champions).
     * -, -, -, L, +, +, +, +, +, +, R, *, *, *, *
     * |<-- 4 -->|<--   6        -->|<--   5  -->|
     *
     * For the second round, we will have 8 total players, and two possible cases: [1, 4] (or [4, 1]) and [2, 3] (or
     * [3, 2]).
     * L, +, +, +, R, *, *, *
     * 1|  3      |   4     |
     * or
     * -, L, +, +, +, R, *, *
     * 2 |  3      |   3   |
     *
     * Recursion:
     * 1. So we will track players on the left l and right r, including champions, and total number of players n. for
     * each iteration:
     * 1.1 If l == r, our champions will compete in the current round. we update the minRound and maxRound.
     * 1.2 else, we determine the valid standings of players i and j with simplified steps/rules below:
     * 1.2.1 To simplify the calculation, we always make sure l < r. We can do it because reult for [8, 3]
     * will be the same as for [3, 8].
     * 1.2.2 i and j should be at least one to account for a champion.
     * 1.2.3 Picking i players on the left meaning that l - i players lose to players on the right.
     * 1.2.4 So we have to pick l - i + 1 players on the right or more (+1 is for the champion).
     * 1.2.5 Winers on the left and right (i + j) should not exceed the number of players in the next round (
     * (n + 1) / 2).
     * 1.2.6 Loosers on the left and right (l - i + r - j) should not exceed the half of players in the
     * current round.
     * 2. return {minRound, maxRound} when recursion is done
     */
    @Answer
    public int[] earliestAndLatest(int n, int firstPlayer, int secondPlayer) {
        res = new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE};
        dfs(firstPlayer, n - secondPlayer + 1, n, 1);
        return res;
    }

    private int[] res;

    private void dfs(int left, int right, int n, int round) {
        if (left == right) {
            res[0] = Math.min(res[0], round);
            res[1] = Math.max(res[1], round);
        } else {
            //1.2.1 always make left < right
            if (left > right) {
                //swap left and right
                int t = left;
                left = right;
                right = t;
            }
        }
        //1.2.2 choose i and j
        //1.2.3 pick i
        for (int i = 1; i < left + 1; i++) {
            //1.2.4 pick j, 1.2.5 i+j < (n+1)/2
            for (int j = left - i + 1; i + j <= Math.min(right, (n + 1) / 2); j++) {
                //1.2.6 left - i + right - j <= n/2
                if (left + right - (i + j) <= n / 2) {
                    //continue next selection
                    dfs(i, j, (n + 1) / 2, round + 1);
                }
            }
        }
    }

    /**
     * 带中文说明的DP 解法, 参考文档:
     * <a link="https://leetcode.cn/problems/the-earliest-and-latest-rounds-where-players-compete/solution/dong-tai-gui-hua-fen-lei-tao-lun-zhuan-y-9pjd/"></a>
     */
    @Answer
    public int[] earliestAndLatest2(int n, int firstPlayer, int secondPlayer) {
        return DP[n][firstPlayer][secondPlayer];
    }

    private static final int[][][][] DP = new int[29][29][29][2];

    static {
        for (int n = 2; n <= 28; n++) {
            for (int f = 1; f <= n; f++) {
                // 只考虑 f < s 的情况，因为两者交换位置不会影响结果
                // f > s 的情况可直接利用对称性求出 DP[n][s][f] = DP[n][f][s]
                for (int s = f + 1; s <= n; s++) {
                    if (f + s == n + 1) {
                        DP[n][f][s][0] = DP[n][f][s][1] = DP[n][s][f][0] = DP[n][s][f][1] = 1;
                        continue;
                    }
                    // 最小回合初始正无穷，注意防止溢出
                    DP[n][f][s][0] = Integer.MAX_VALUE - 1;
                    // 最大回合初始负无穷
                    DP[n][f][s][1] = Integer.MIN_VALUE;
                    if (s <= n / 2) {
                        for (int i = f - 1; i >= 0; i--) {
                            for (int j = s - f - 1; j >= 0; j--) {
                                DP[n][f][s][0] = Math.min(DP[n][f][s][0], DP[n - n / 2][f - i][s - i - j][0] + 1);
                                DP[n][f][s][1] = Math.max(DP[n][f][s][1], DP[n - n / 2][f - i][s - i - j][1] + 1);
                            }
                        }
                    } else {
                        int s0 = n + 1 - s;
                        int k = (s - s0 + 1) / 2;
                        if (f < s0) {
                            for (int i = f - 1; i >= 0; i--) {
                                for (int j = s0 - f - 1; j >= 0; j--) {
                                    DP[n][f][s][0] = Math.min(DP[n][f][s][0],
                                            DP[n - n / 2][f - i][s - i - j - k][0] + 1);
                                    DP[n][f][s][1] = Math.max(DP[n][f][s][1],
                                            DP[n - n / 2][f - i][s - i - j - k][1] + 1);
                                }
                            }
                        } else {
                            if (f <= n / 2) {
                                for (int i = s0 - 1; i >= 0; i--) {
                                    for (int j = f - s0; j >= 1; j--) {
                                        DP[n][f][s][0] = Math.min(DP[n][f][s][0],
                                                DP[n - n / 2][f - i - j][s - i - k][0] + 1);
                                        DP[n][f][s][1] = Math.max(DP[n][f][s][1],
                                                DP[n - n / 2][f - i - j][s - i - k][1] + 1);
                                    }
                                }
                            } else {
                                for (int i = s0 - 1; i >= 0; i--) {
                                    for (int j = s - f - 1; j >= 0; j--) {
                                        DP[n][f][s][0] = Math.min(DP[n][f][s][0],
                                                DP[n - n / 2][f - i - (k - j)][s - i - k][0] + 1);
                                        DP[n][f][s][1] = Math.max(DP[n][f][s][1],
                                                DP[n - n / 2][f - i - (k - j)][s - i - k][1] + 1);
                                    }
                                }
                            }
                        }
                    }
                    // 对称性
                    DP[n][s][f][0] = DP[n][f][s][0];
                    DP[n][s][f][1] = DP[n][f][s][1];
                }
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(11, 2, 4).expect(new int[]{3, 4});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 1, 5).expect(new int[]{1, 1});

}
