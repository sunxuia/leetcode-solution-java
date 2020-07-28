package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 879. Profitable Schemes
 * https://leetcode.com/problems/profitable-schemes/
 *
 * There are G people in a gang, and a list of various crimes they could commit.
 *
 * The i-th crime generates a profit[i] and requires group[i] gang members to participate.
 *
 * If a gang member participates in one crime, that member can't participate in another crime.
 *
 * Let's call a profitable scheme any subset of these crimes that generates at least P profit, and the total number of
 * gang members participating in that subset of crimes is at most G.
 *
 * How many schemes can be chosen?  Since the answer may be very large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: G = 5, P = 3, group = [2,2], profit = [2,3]
 * Output: 2
 * Explanation:
 * To make a profit of at least 3, the gang could either commit crimes 0 and 1, or just crime 1.
 * In total, there are 2 schemes.
 *
 * Example 2:
 *
 * Input: G = 10, P = 5, group = [2,3,5], profit = [6,7,8]
 * Output: 7
 * Explanation:
 * To make a profit of at least 5, the gang could commit any crimes, as long as they commit one.
 * There are 7 possible schemes: (0), (1), (2), (0,1), (0,2), (1,2), and (0,1,2).
 *
 * Note:
 *
 * 1 <= G <= 100
 * 0 <= P <= 100
 * 1 <= group[i] <= 100
 * 0 <= profit[i] <= 100
 * 1 <= group.length = profit.length <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q879_ProfitableSchemes {

    // 参考文档 https://www.cnblogs.com/grandyang/p/11108205.html
    @Answer
    public int profitableSchemes(int G, int P, int[] group, int[] profit) {
        final int N = group.length, M = 10_0000_0007;
        // dp[k][g][p] 表示使用k 个任务, g 个人, 目标p 个收益, 符合的组合数
        int[][][] dp = new int[N + 1][G + 1][P + 1];
        dp[0][0][0] = 1;
        for (int k = 1; k <= N; k++) {
            for (int g = 0; g <= G; g++) {
                for (int p = 0; p <= P; p++) {
                    dp[k][g][p] = dp[k - 1][g][p];
                    if (g >= group[k - 1]) {
                        dp[k][g][p] += dp[k - 1][g - group[k - 1]][Math.max(0, p - profit[k - 1])];
                        dp[k][g][p] %= M;
                    }
                }
            }
        }

        int res = 0;
        for (int g = 0; g <= G; g++) {
            res = (res + dp[N][g][P]) % M;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, 3, new int[]{2, 2}, new int[]{2, 3})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(10, 5, new int[]{2, 3, 5}, new int[]{6, 7, 8})
            .expect(7);

}
