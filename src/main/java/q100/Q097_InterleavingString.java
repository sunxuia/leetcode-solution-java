package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/interleaving-string/
 *
 * Given s1, s2, s3, find whether s3 is formed by the interleaving of s1 and s2.
 *
 * Example 1:
 *
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbcbcac"
 * Output: true
 *
 * Example 2:
 *
 * Input: s1 = "aabcc", s2 = "dbbca", s3 = "aadbbbaccc"
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q097_InterleavingString {

    /**
     * dfs 方式空间占用率小, 但是时间开销大
     */
    @Answer
    public boolean isInterleave(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        return dfs(s1, s2, s3, 0, 0);
    }

    private boolean dfs(String s1, String s2, String s3, int i1, int i2) {
        if (i1 + i2 == s3.length()) {
            return true;
        }
        return i1 < s1.length() && s1.charAt(i1) == s3.charAt(i1 + i2) && dfs(s1, s2, s3, i1 + 1, i2)
                || i2 < s2.length() && s2.charAt(i2) == s3.charAt(i1 + i2) && dfs(s1, s2, s3, i1, i2 + 1);
    }

    /**
     * dp 方式时间开销小, 但是空间占用大
     */
    @Answer
    public boolean dp(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
        for (int i = 1; i <= s1.length() && s1.charAt(i - 1) == s3.charAt(i - 1); i++) {
            dp[i][0] = i;
        }
        for (int i = 1; i <= s2.length() && s2.charAt(i - 1) == s3.charAt(i - 1); i++) {
            dp[0][i] = i;
        }
        for (int i = 1; i <= s1.length(); i++) {
            for (int j = 1; j <= s2.length(); j++) {
                if (dp[i - 1][j] > 0 && s3.charAt(i + j - 1) == s1.charAt(i - 1)
                        || dp[i][j - 1] > 0 && s3.charAt(i + j - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = i + j;
                }
            }
        }
        return dp[s1.length()][s2.length()] == s3.length();
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("aabcc", "dbbca", "aadbbcbcac").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("aabcc", "dbbca", "aadbbbaccc").expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation
            .createWith("a", "b", "c").expect(false);

}
