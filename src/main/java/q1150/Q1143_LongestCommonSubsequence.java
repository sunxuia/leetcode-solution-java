package q1150;

import org.junit.runner.RunWith;
import q1100.Q1092_ShortestCommonSupersequence;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1143. Longest Common Subsequence
 * https://leetcode.com/problems/longest-common-subsequence/
 *
 * Given two strings text1 and text2, return the length of their longest common subsequence.
 *
 * A subsequence of a string is a new string generated from the original string with some characters(can be none)
 * deleted without changing the relative order of the remaining characters. (eg, "ace" is a subsequence of "abcde" while
 * "aec" is not). A common subsequence of two strings is a subsequence that is common to both strings.
 *
 * If there is no common subsequence, return 0.
 *
 * Example 1:
 *
 * Input: text1 = "abcde", text2 = "ace"
 * Output: 3
 * Explanation: The longest common subsequence is "ace" and its length is 3.
 *
 * Example 2:
 *
 * Input: text1 = "abc", text2 = "abc"
 * Output: 3
 * Explanation: The longest common subsequence is "abc" and its length is 3.
 *
 * Example 3:
 *
 * Input: text1 = "abc", text2 = "def"
 * Output: 0
 * Explanation: There is no such common subsequence, so the result is 0.
 *
 * Constraints:
 *
 * 1 <= text1.length <= 1000
 * 1 <= text2.length <= 1000
 * The input strings consist of lowercase English characters only.
 *
 * 类似题目 {@link Q1092_ShortestCommonSupersequence}
 */
@RunWith(LeetCodeRunner.class)
public class Q1143_LongestCommonSubsequence {

    @Answer
    public int longestCommonSubsequence(String text1, String text2) {
        char[] cs1 = text1.toCharArray();
        char[] cs2 = text2.toCharArray();
        final int m = cs1.length, n = cs2.length;
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (cs1[i] == cs2[j]) {
                    dp[i + 1][j + 1] = dp[i][j] + 1;
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }
        return dp[m][n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcde", "ace").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abc", "abc").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("abc", "def").expect(0);

}
