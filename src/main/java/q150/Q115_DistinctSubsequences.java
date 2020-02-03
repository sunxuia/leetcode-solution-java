package q150;

import java.util.Arrays;
import org.junit.Before;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/distinct-subsequences/
 *
 * Given a string S and a string T, count the number of distinct subsequences of S which equals T.
 *
 * A subsequence of a string is a new string which is formed from the original string by deleting some (can be none)
 * of the characters without disturbing the relative positions of the remaining characters. (ie, "ACE" is a
 * subsequence of "ABCDE" while "AEC" is not).
 *
 * Example 1:
 *
 * Input: S = "rabbbit", T = "rabbit"
 * Output: 3
 * Explanation:
 *
 * As shown below, there are 3 ways you can generate "rabbit" from S.
 * (The caret symbol ^ means the chosen letters)
 *
 * > rabbbit
 * > ^^^^ ^^
 * > rabbbit
 * > ^^ ^^^^
 * > rabbbit
 * > ^^^ ^^^
 *
 * Example 2:
 *
 * Input: S = "babgbag", T = "bag"
 * Output: 5
 * Explanation:
 *
 * As shown below, there are 5 ways you can generate "bag" from S.
 * (The caret symbol ^ means the chosen letters)
 *
 * > babgbag
 * > ^^ ^
 * > babgbag
 * > ^^    ^
 * > babgbag
 * > ^    ^^
 * > babgbag
 * >   ^  ^^
 * > babgbag
 * >     ^^^
 */
@RunWith(LeetCodeRunner.class)
public class Q115_DistinctSubsequences {

    // region dfs (这个会超时)
    public int numDistinct(String s, String t) {
        dfs0(s, t, 0, 0);
        return count;
    }

    private int count = 0;

    private void dfs0(String s, String t, int si, int ti) {
        if (ti == t.length()) {
            count++;
        }
        if (si == s.length() || ti == t.length()) {
            return;
        }
        dfs(s, t, si + 1, ti);
        if (s.charAt(si) == t.charAt(ti)) {
            dfs(s, t, si + 1, ti + 1);
        }
    }

    @Before
    public void setup() {
        count = 0;
    }
    // endregion

    // region dfs 为了避免超时加入了缓存. 这个运行起来比较慢.
    @Answer
    public int numDistinctDfs(String s, String t) {
        final int m = s.length(), n = t.length();
        cache = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            Arrays.fill(cache[i], -1);
            cache[i][n] = 1;
        }
        Arrays.fill(cache[m], 0);
        cache[m][n] = 1;

        return dfs(s, t, 0, 0);
    }

    private int[][] cache;

    private int dfs(String s, String t, int si, int ti) {
        if (cache[si][ti] == -1) {
            cache[si][ti] = 0;
            if (s.charAt(si) == t.charAt(ti)) {
                cache[si][ti] = dfs(s, t, si + 1, ti + 1);
            }
            cache[si][ti] += dfs(s, t, si + 1, ti);
        }
        return cache[si][ti];
    }
    // endregion

    // 根据上面的dfs 改进而来的dp 解法.
    @Answer
    public int dp(String s, String t) {
        final int m = t.length(), n = s.length();
        // dp 表示当进行到t 的第i 个匹配时, 可能的匹配数量.
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i <= n; i++) {
            dp[0][i] = 1;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (t.charAt(i - 1) == s.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                dp[i][j] += dp[i][j - 1];
            }
//            new TablePrinter().leftAxisChars(" " + t).topAxisChars(" " + s).setMessage(i + ":")
//                    .print(dp).printLine();
        }
        return dp[m][n];
    }

    // leetcode 上最快的解法
    @Answer
    public int leetCode(String s, String t) {
        // count 用于缓存在第i 位与 t 后t.length()-i 位匹配的数量.
        int[] count = new int[t.length() + 1];
        count[0] = 1;
        for (int i = 0; i < s.length(); i++) {
            for (int j = t.length() - 1; j >= 0; j--) {
                if (s.charAt(i) == t.charAt(j)) {
                    count[j + 1] += count[j];
                }
            }
        }
        return count[t.length()];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("rabbbit", "rabbit").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("babgbag", "bag").expect(5);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith("adbdadeecadeadeccaeaabdabdbcdabddddabcaaadbabaaedeeddeaeebcdeabcaaaeeaeeabcd"
                            + "dcebddebeebedaecccbdcbcedbdaeaedcdebeecdaaedaacadbdccabddaddacdddc",
                    "bcddceeeebecbc")
            .expect(700531452);

}
