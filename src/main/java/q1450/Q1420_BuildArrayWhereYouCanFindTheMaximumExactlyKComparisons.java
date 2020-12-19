package q1450;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1420. Build Array Where You Can Find The Maximum Exactly K Comparisons
 * https://leetcode.com/problems/build-array-where-you-can-find-the-maximum-exactly-k-comparisons/
 *
 * Given three integers n, m and k. Consider the following algorithm to find the maximum element of an array of positive
 * integers:
 * <img src="./Q1420_PIC.png">
 * You should build the array arr which has the following properties:
 *
 * 1. arr has exactly n integers.
 * 2. 1 <= arr[i] <= m where (0 <= i < n).
 * 3. After applying the mentioned algorithm to arr, the value search_cost is equal to k.
 *
 * Return the number of ways to build the array arr under the mentioned conditions. As the answer may grow large, the
 * answer must be computed modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 2, m = 3, k = 1
 * Output: 6
 * Explanation: The possible arrays are [1, 1], [2, 1], [2, 2], [3, 1], [3, 2] [3, 3]
 *
 * Example 2:
 *
 * Input: n = 5, m = 2, k = 3
 * Output: 0
 * Explanation: There are no possible arrays that satisify the mentioned conditions.
 *
 * Example 3:
 *
 * Input: n = 9, m = 1, k = 1
 * Output: 1
 * Explanation: The only possible array is [1, 1, 1, 1, 1, 1, 1, 1, 1]
 *
 * Example 4:
 *
 * Input: n = 50, m = 100, k = 25
 * Output: 34549172
 * Explanation: Don't forget to compute the answer modulo 1000000007
 *
 * Example 5:
 *
 * Input: n = 37, m = 17, k = 7
 * Output: 418930126
 *
 * Constraints:
 *
 * 1 <= n <= 50
 * 1 <= m <= 100
 * 0 <= k <= n
 */
@RunWith(LeetCodeRunner.class)
public class Q1420_BuildArrayWhereYouCanFindTheMaximumExactlyKComparisons {

    @Answer
    public int numOfArrays(int n, int m, int k) {
        int[][][] cache = new int[n][k][m + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                Arrays.fill(cache[i][j], -1);
            }
        }

        int res = 0;
        for (int v = 1; v <= m; v++) {
            int count = dfs(cache, m, n - 1, k - 1, v);
            res = (res + count) % MOD;
        }
        return res;
    }

    // prev 表示前面的数字中的最大值
    private int dfs(int[][][] cache, int m, int n, int k, int prev) {
        if (n == 0) {
            return k == 0 ? 1 : 0;
        }
        if (cache[n][k][prev] != -1) {
            return cache[n][k][prev];
        }
        // 当前取的值 <= 之前的值
        long res = (long) prev * dfs(cache, m, n - 1, k, prev);
        if (k > 0) {
            // 当前取的值 > 之前的值
            for (int v = prev + 1; v <= m; v++) {
                res += dfs(cache, m, n - 1, k - 1, v);
            }
        }
        return cache[n][k][prev] = (int) (res % MOD);
    }

    private static final int MOD = 10_0000_0007;

    /**
     * 上面解法的dp 改写
     */
    @Answer
    public int numOfArrays_dp(int n, int m, int k) {
        // dp[i][s][v] 表示长度为i, search_cost 为s, 且最大值为v 的结果
        int[][][] dp = new int[n + 1][k + 1][m + 1];
        dp[0][0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int s = 1; s <= i && s <= k; s++) {
                for (int v = s; v <= m; v++) {
                    long count = (long) v * dp[i - 1][s][v];
                    for (int prev = s - 1; prev < v; prev++) {
                        count += dp[i - 1][s - 1][prev];
                    }
                    dp[i][s][v] = (int) (count % MOD);
                }
            }
        }

        int res = 0;
        for (int i = k; i <= m; i++) {
            res = (res + dp[n][k][i]) % MOD;
        }
        return res;
    }

    /**
     * leetcode 上最快的解法, 与上面是不同的dp 解法.
     */
    @Answer
    public int numOfArrays3(int n, int m, int k) {
        //dp[v][s] 表示当前最大值为v+1 且search_cost 为s的结果
        long[][] dp = new long[m][k + 1];
        // 初始化search_cost = 1 的情况
        for (int i = 0; i < m; i++) {
            dp[i][1] = 1;
        }

        for (int i = 2; i <= n; i++) {
            long[][] temp = new long[m][k + 1];
            for (int s = 1; s <= i && s <= k; s++) {
                long sum = 0;
                for (int v = 0; v < m; v++) {
                    temp[v][s] = (sum + dp[v][s] * (v + 1)) % MOD;
                    sum += dp[v][s - 1];
                }
            }
            dp = temp;
        }

        long res = 0;
        for (int i = 0; i < m; i++) {
            res += dp[i][k];
        }
        return (int) (res % MOD);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 3, 1).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 2, 3).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(9, 1, 1).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, 2, 1).expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(50, 100, 25).expect(34549172);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith(37, 17, 7).expect(418930126);

}
