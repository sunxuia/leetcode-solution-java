package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1771. Maximize Palindrome Length From Subsequences
 * https://leetcode.com/problems/maximize-palindrome-length-from-subsequences/
 *
 * You are given two strings, word1 and word2. You want to construct a string in the following manner:
 *
 * Choose some non-empty subsequence subsequence1 from word1.
 * Choose some non-empty subsequence subsequence2 from word2.
 * Concatenate the subsequences: subsequence1 + subsequence2, to make the string.
 *
 * Return the length of the longest palindrome that can be constructed in the described manner. If no palindromes can be
 * constructed, return 0.
 *
 * A subsequence of a string s is a string that can be made by deleting some (possibly none) characters from s without
 * changing the order of the remaining characters.
 *
 * A palindrome is a string that reads the same forward as well as backward.
 *
 * Example 1:
 *
 * Input: word1 = "cacb", word2 = "cbba"
 * Output: 5
 * Explanation: Choose "ab" from word1 and "cba" from word2 to make "abcba", which is a palindrome.
 *
 * Example 2:
 *
 * Input: word1 = "ab", word2 = "ab"
 * Output: 3
 * Explanation: Choose "ab" from word1 and "a" from word2 to make "aba", which is a palindrome.
 *
 * Example 3:
 *
 * Input: word1 = "aa", word2 = "bb"
 * Output: 0
 * Explanation: You cannot construct a palindrome from the described method, so return 0.
 *
 * Constraints:
 *
 * 1 <= word1.length, word2.length <= 1000
 * word1 and word2 consist of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1771_MaximizePalindromeLengthFromSubsequences {

    @Answer
    public int longestPalindrome(String word1, String word2) {
        final char[] cs = (word1 + word2).toCharArray();
        final int len = cs.length, m = word1.length();

        // dp[i][j] 表示 cs[i:j] 中回文的长度
        int[][] dp = new int[len][len];
        // 边界条件: 长度 1
        for (int i = 0; i < len; i++) {
            dp[i][i] = 1;
        }
        // 长度 >=2
        for (int leap = 2; leap <= len; leap++) {
            for (int i = 0, j = i + leap - 1; j < len; i++, j++) {
                dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                if (cs[i] == cs[j]) {
                    dp[i][j] = Math.max(dp[i][j], dp[i + 1][j - 1] + 2);
                }
            }
        }

        // 左右各取一个相等字符, 中间的回文可以在任意一边
        int res = 0;
        for (int i = m - 1; i >= 0; i--) {
            for (int j = m; j < len; j++) {
                if (cs[i] == cs[j]) {
                    res = Math.max(res, dp[i + 1][j - 1] + 2);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("cacb", "cbba").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ab", "ab").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("aa", "bb").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("ceebeddc", "d").expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("afaaadacb", "ca").expect(6);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("d", "ece").expect(0);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .createWith("c", "fffaeacefcefeecedeedefecdcbedebebcfadffeacddcffa")
            .expect(27);

}
