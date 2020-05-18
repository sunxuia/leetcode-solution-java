package q750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minimum-ascii-delete-sum-for-two-strings/
 *
 * Given two strings s1, s2, find the lowest ASCII sum of deleted characters to make two strings equal.
 *
 * Example 1:
 *
 * Input: s1 = "sea", s2 = "eat"
 * Output: 231
 * Explanation: Deleting "s" from "sea" adds the ASCII value of "s" (115) to the sum.
 * Deleting "t" from "eat" adds 116 to the sum.
 * At the end, both strings are equal, and 115 + 116 = 231 is the minimum sum possible to achieve this.
 *
 * Example 2:
 *
 * Input: s1 = "delete", s2 = "leet"
 * Output: 403
 * Explanation: Deleting "dee" from "delete" to turn the string into "let",
 * adds 100[d]+101[e]+101[e] to the sum.  Deleting "e" from "leet" adds 101[e] to the sum.
 * At the end, both strings are equal to "let", and the answer is 100+101+101+101 = 403.
 * If instead we turned both strings into "lee" or "eet", we would get answers of 433 or 417, which are higher.
 *
 * Note:
 * 0 < s1.length, s2.length <= 1000.
 * All elements of each string will have an ASCII value in [97, 122].
 */
@RunWith(LeetCodeRunner.class)
public class Q712_MinimumASCIIDeleteSumForTwoStrings {

    // 普通的dfs 会超时, 所以加上缓存
    @Answer
    public int minimumDeleteSum(String s1, String s2) {
        char[] sc1 = s1.toCharArray(), sc2 = s2.toCharArray();
        final int m = sc1.length, n = sc2.length;
        int[][] cache = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cache[i], 0, n, -1);
        }

        // 边界值
        for (int i = m - 1; i >= 0; i--) {
            cache[i][n] = cache[i + 1][n] + sc1[i];
        }
        for (int i = n - 1; i >= 0; i--) {
            cache[m][i] = cache[m][i + 1] + sc2[i];
        }

        return dfs(s1.toCharArray(), 0, s2.toCharArray(), 0, cache);
    }

    private int dfs(char[] sc1, int i, char[] sc2, int j, int[][] cache) {
        if (cache[i][j] > -1) {
            return cache[i][j];
        }

        int res;
        if (sc1[i] == sc2[j]) {
            res = dfs(sc1, i + 1, sc2, j + 1, cache);
        } else {
            res = Math.min(sc1[i] + dfs(sc1, i + 1, sc2, j, cache), sc2[j] + dfs(sc1, i, sc2, j + 1, cache));
        }
        return cache[i][j] = res;
    }

    // DP 做法. 方向和上面的dfs 相反
    @Answer
    public int minimumDeleteSum_dp(String s1, String s2) {
        char[] sc1 = s1.toCharArray(), sc2 = s2.toCharArray();
        final int m = sc1.length, n = sc2.length;
        int[][] dp = new int[m + 1][n + 1];

        // 边界值
        for (int i = 0; i < m; i++) {
            dp[i + 1][0] = dp[i][0] + sc1[i];
        }
        for (int i = 0; i < n; i++) {
            dp[0][i + 1] = dp[0][i] + sc2[i];
        }

        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (sc1[i - 1] == sc2[j - 1]) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = Math.min(sc1[i - 1] + dp[i - 1][j], sc2[j - 1] + dp[i][j - 1]);
                }
            }
        }
        return dp[m][n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("sea", "eat").expect(231);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("delete", "leet").expect(403);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("baaaabcccaaccc", "cbaaacacaabba").expect(1080);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("bbccacacaab", "aabccb").expect(878);

}
