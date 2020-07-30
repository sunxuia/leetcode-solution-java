package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 887. Super Egg Drop
 * https://leetcode.com/problems/super-egg-drop/
 *
 * You are given K eggs, and you have access to a building with N floors from 1 to N.
 *
 * Each egg is identical in function, and if an egg breaks, you cannot drop it again.
 *
 * You know that there exists a floor F with 0 <= F <= N such that any egg dropped at a floor higher than F will break,
 * and any egg dropped at or below floor F will not break.
 *
 * Each move, you may take an egg (if you have an unbroken one) and drop it from any floor X (with 1 <= X <= N).
 *
 * Your goal is to know with certainty what the value of F is.
 *
 * What is the minimum number of moves that you need to know with certainty what F is, regardless of the initial value
 * of F?
 *
 * Example 1:
 *
 * Input: K = 1, N = 2
 * Output: 2
 * Explanation:
 * Drop the egg from floor 1.  If it breaks, we know with certainty that F = 0.
 * Otherwise, drop the egg from floor 2.  If it breaks, we know with certainty that F = 1.
 * If it didn't break, then we know with certainty F = 2.
 * Hence, we needed 2 moves in the worst case to know what F is with certainty.
 *
 * Example 2:
 *
 * Input: K = 2, N = 6
 * Output: 3
 *
 * Example 3:
 *
 * Input: K = 3, N = 14
 * Output: 4
 *
 * Note:
 *
 * 1 <= K <= 100
 * 1 <= N <= 10000
 */
@RunWith(LeetCodeRunner.class)
public class Q887_SuperEggDrop {

    /**
     * 扔鸡蛋问题改编版
     * 扔鸡蛋问题参见 https://blog.csdn.net/joylnwang/article/details/6769160
     * 这题的参考文档 https://www.cnblogs.com/grandyang/p/11048142.html
     */
    @Answer
    public int superEggDrop(int K, int N) {
        int[][] dp = new int[N + 1][K + 1];
        int res = 0;
        while (dp[res][K] < N) {
            res++;
            for (int i = 1; i <= K; i++) {
                dp[res][i] = dp[res - 1][i - 1] + dp[res - 1][i] + 1;
            }
        }
        return res;
    }

    /**
     * LeetCode 上最快的解法
     */
    @Answer
    public int superEggDrop2(int K, int N) {
        int[] dp = new int[K + 1];
        int res;
        for (res = 0; dp[K] < N; res++) {
            for (int k = K; k > 0; k--) {
                dp[k] += dp[k - 1] + 1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 2).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 6).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(3, 14).expect(4);

}
