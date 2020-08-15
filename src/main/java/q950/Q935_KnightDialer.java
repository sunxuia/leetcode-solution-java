package q950;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 935. Knight Dialer
 * https://leetcode.com/problems/knight-dialer/
 *
 * A chess knight can move as indicated in the chess diagram below:
 *
 * (图 Q935_PIC1.png) . (图Q935_PIC2.png)
 *
 * This time, we place our chess knight on any numbered key of a phone pad (indicated above), and the knight makes N-1
 * hops.  Each hop must be from one key to another numbered key.
 *
 * Each time it lands on a key (including the initial placement of the knight), it presses the number of that key,
 * pressing N digits total.
 *
 * How many distinct numbers can you dial in this manner?
 *
 * Since the answer may be large, output the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: 1
 * Output: 10
 *
 * Example 2:
 *
 * Input: 2
 * Output: 20
 *
 * Example 3:
 *
 * Input: 3
 * Output: 46
 *
 * Note:
 *
 * 1 <= N <= 5000
 *
 * 题解: 国际象棋的骑士, 也就是马, 现在在电话按键(Q535_PIC2.png) 的任一数字按键上, 按照马走日(2步+1步)的规则跳跃到其他数字按键上,
 * 每次跳跃都会按下按电话键(初始位置也会按下), 一共跳N-1 次, 最后形成N 位长的电话号码, 题目问电话号码的组合数量.
 */
@RunWith(LeetCodeRunner.class)
public class Q935_KnightDialer {

    private static final int MOD = 10_0000_0007;
    private static final int[][] NEXTS = new int[][]
            {{4, 6}, {6, 8}, {7, 9}, {4, 8}, {0, 3, 9}, {}, {0, 1, 7}, {2, 6}, {1, 3}, {2, 4}};
    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(10);
    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(20);
    @TestData
    public DataExpectation example3 = DataExpectation.create(3).expect(46);
    @TestData
    public DataExpectation example4 = DataExpectation.create(161).expect(533302150);

    /**
     * 这题按照related topics 的提示可以用dp 来做.
     */
    @Answer
    public int knightDialer(int N) {
        // dp[i][j] 表示第i 轮, 马在第j 格上的可能结果.
        int[][] dp = new int[N][10];
        Arrays.fill(dp[0], 1);
        for (int i = 0; i < N - 1; i++) {
            for (int j = 0; j < 10; j++) {
                for (int next : NEXTS[j]) {
                    dp[i + 1][next] += dp[i][j];
                    dp[i + 1][next] %= MOD;
                }
            }
        }

        int res = 0;
        for (int i = 0; i < 10; i++) {
            res = (res + dp[N - 1][i]) % MOD;
        }
        return res;
    }

}
