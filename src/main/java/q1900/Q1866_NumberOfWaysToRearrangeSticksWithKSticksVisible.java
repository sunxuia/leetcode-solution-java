package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Hard] 1866. Number of Ways to Rearrange Sticks With K Sticks Visible
 * https://leetcode.com/problems/number-of-ways-to-rearrange-sticks-with-k-sticks-visible/
 *
 * There are n uniquely-sized sticks whose lengths are integers from 1 to n. You want to arrange the sticks such that
 * exactly k sticks are visible from the left. A stick is visible from the left if there are no longer sticks to the
 * left of it.
 *
 * For example, if the sticks are arranged [1,3,2,5,4], then the sticks with lengths 1, 3, and 5 are visible from the
 * left.
 *
 * Given n and k, return the number of such arrangements. Since the answer may be large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 3, k = 2
 * Output: 3
 * Explanation: [1,3,2], [2,3,1], and [2,1,3] are the only arrangements such that exactly 2 sticks are visible.
 * The visible sticks are underlined.
 *
 * Example 2:
 *
 * Input: n = 5, k = 5
 * Output: 1
 * Explanation: [1,2,3,4,5] is the only arrangement such that all 5 sticks are visible.
 * The visible sticks are underlined.
 *
 * Example 3:
 *
 * Input: n = 20, k = 11
 * Output: 647427950
 * Explanation: There are 647427950 (mod 10^9 + 7) ways to rearrange the sticks such that exactly 11 sticks are visible.
 *
 * Constraints:
 *
 * 1 <= n <= 1000
 * 1 <= k <= n
 */
@RunWith(LeetCodeRunner.class)
public class Q1866_NumberOfWaysToRearrangeSticksWithKSticksVisible {

    /**
     * 参考文档 https://www.796t.com/article.php?id=488305
     */
    @Answer
    public int rearrangeSticks(int n, int k) {
        // cache[i][j] 表示i 个stick 可被看到j 个的排列组合数量
        int[][] cache = new int[n + 1][k + 1];
        // 初始化为-1 表示没有值
        for (int i = 0; i <= n; i++) {
            Arrays.fill(cache[i], -1);
        }
        // i个stick 可被看到i 个只能有1 中排列方式
        for (int i = 0; i <= k; i++) {
            cache[i][i] = 1;
        }
        // i 个stick 可被看到0 次的排列方式为0
        for (int i = 1; i <= n; i++) {
            cache[i][0] = 0;
        }

        return dfs(cache, n, k);
    }

    private int dfs(int[][] cache, int n, int k) {
        if (cache[n][k] >= 0) {
            return cache[n][k];
        }

        // 如果这个stick 是可见的(只能选择第n 个stick)
        int visible = dfs(cache, n - 1, k - 1);
        // 如果这个stick 是不可见的(可以选择1~n-1 个stick)
        int inVisible = dfs(cache, n - 1, k);
        // 可能的排列总数
        long sum = (long) inVisible * (n - 1) + visible;
        cache[n][k] = (int) (sum % 10_0000_0007);
        return cache[n][k];
    }

    /**
     * 根据上方思路改造的dp 方案
     */
    @Answer
    public int rearrangeSticks_dp(int n, int k) {
        // dp[j] 表示在第i 轮(有i 个stick) 中能看到j 个stick 的组合数量.
        int[] dp = new int[k + 1];
        for (int i = 1; i <= n; i++) {
            if (i <= k) {
                // dp[i][i] = 1
                dp[i] = 1;
            }
            for (int j = Math.min(i - 1, k); j > 0; j--) {
                long sum = (long) dp[j] * (i - 1) + dp[j - 1];
                dp[j] = (int) (sum % 10_0000_0007);
            }
        }
        return dp[k];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 2).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 5).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(20, 11).expect(647427950);

}
