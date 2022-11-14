package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 2014. Longest Subsequence Repeated k Times
 * https://leetcode.com/problems/longest-subsequence-repeated-k-times/
 *
 * You are given a string s of length n, and an integer k. You are tasked to find the longest subsequence repeated k
 * times in string s.
 *
 * A subsequence is a string that can be derived from another string by deleting some or no characters without changing
 * the order of the remaining characters.
 *
 * A subsequence seq is repeated k times in the string s if seq * k is a subsequence of s, where seq * k represents a
 * string constructed by concatenating seq k times.
 *
 * For example, "bba" is repeated 2 times in the string "bababcba", because the string "bbabba", constructed by
 * concatenating "bba" 2 times, is a subsequence of the string "bababcba".
 *
 * Return the longest subsequence repeated k times in string s. If multiple such subsequences are found, return the
 * lexicographically largest one. If there is no such subsequence, return an empty string.
 *
 * Example 1:
 * (图2014_PIC.png)
 * Input: s = "letsleetcode", k = 2
 * Output: "let"
 * Explanation: There are two longest subsequences repeated 2 times: "let" and "ete".
 * "let" is the lexicographically largest one.
 *
 * Example 2:
 *
 * Input: s = "bb", k = 2
 * Output: "b"
 * Explanation: The longest subsequence repeated 2 times is "b".
 *
 * Example 3:
 *
 * Input: s = "ab", k = 2
 * Output: ""
 * Explanation: There is no subsequence repeated 2 times. Empty string is returned.
 *
 * Constraints:
 *
 * n == s.length
 * 2 <= n, k <= 2000
 * 2 <= n < k * 8
 * s consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q2014_LongestSubsequenceRepeatedKTimes {

    /**
     * 按照 hint 中的提示得出的解法, 穷举法.
     */
    @Answer
    public String longestSubsequenceRepeatedK(String s, int k) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int[] counts = new int[128];
        for (int i = 0; i < n; i++) {
            counts[cs[i]]++;
        }
        char[] permutation = new char[n / k];
        return dfs(permutation, 0, cs, counts, k);
    }

    private String dfs(char[] permutation, int i, char[] cs, int[] counts, int k) {
        final int len = permutation.length;
        if (i == len) {
            // 检查字符串是否满足条件
            int start = 0;
            while (start < len && permutation[start] == 0) {
                start++;
            }
            int scan = 0;
            for (int t = 0; t < k; t++) {
                for (int j = start; j < len; j++) {
                    while (scan < cs.length && cs[scan] != permutation[j]) {
                        scan++;
                    }
                    if (scan == cs.length) {
                        return "";
                    }
                    scan++;
                }
            }
            return new String(permutation, start, len - start);
        } else {
            // 尝试排列组合
            for (char c = 'z'; c >= 'a'; c--) {
                if (counts[c] >= k) {
                    counts[c] -= k;
                    permutation[i] = c;
                    String str = dfs(permutation, i + 1, cs, counts, k);
                    if (!str.isEmpty()) {
                        return str;
                    }
                    counts[c] += k;
                }
            }
            permutation[i] = 0;
            return dfs(permutation, i + 1, cs, counts, k);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("letsleetcode", 2).expect("let");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("bb", 2).expect("b");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("ab", 2).expect("");

}
