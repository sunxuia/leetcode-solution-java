package q1100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1092. Shortest Common Supersequence
 * https://leetcode.com/problems/shortest-common-supersequence/
 *
 * Given two strings str1 and str2, return the shortest string that has both str1 and str2 as subsequences.  If multiple
 * answers exist, you may return any of them.
 *
 * (A string S is a subsequence of string T if deleting some number of characters from T (possibly 0, and the characters
 * are chosen anywhere from T) results in the string S.)
 *
 * Example 1:
 *
 * Input: str1 = "abac", str2 = "cab"
 * Output: "cabac"
 * Explanation:
 * str1 = "abac" is a subsequence of "cabac" because we can delete the first "c".
 * str2 = "cab" is a subsequence of "cabac" because we can delete the last "ac".
 * The answer provided is the shortest such string that satisfies these properties.
 *
 * Note:
 *
 * 1 <= str1.length, str2.length <= 1000
 * str1 and str2 consist of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1092_ShortestCommonSupersequence {

    /**
     * 参考文档 https://zxi.mytechroad.com/blog/dynamic-programming/leetcode-1092-shortest-common-supersequence/
     */
    @Answer
    public String shortestCommonSupersequence(String str1, String str2) {
        final char[] cs1 = str1.toCharArray();
        final char[] cs2 = str2.toCharArray();
        final int len1 = cs1.length, len2 = cs2.length;

        // 从前往后 dp 求最长公共子序列长度
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                if (cs1[i] == cs2[j]) {
                    dp[i + 1][j + 1] = 1 + dp[i][j];
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i + 1][j], dp[i][j + 1]);
                }
            }
        }

        // 从后往前求结果
        StringBuilder sb = new StringBuilder();
        for (int i = len1 - 1, j = len2 - 1; i >= 0 || j >= 0; ) {
            char c;
            if (i < 0) {
                c = cs2[j--];
            } else if (j < 0) {
                c = cs1[i--];
            } else if (cs1[i] == cs2[j]) {
                // 公共字符
                c = cs1[i--];
                j--;
            } else if (dp[i][j + 1] == dp[i + 1][j + 1]) {
                // str1 中有str2 中没有的字符
                c = cs1[i--];
            } else {
                // dp[i + 1][j] == dp[i + 1][j + 1]
                // str2 中有str1 中没有的字符
                c = cs2[j--];
            }
            sb.append(c);
        }
        return sb.reverse().toString();
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("abac", "cab").expect("cabac");

}
