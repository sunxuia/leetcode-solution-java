package q2000;

import org.junit.runner.RunWith;
import q050.Q005_LongestPalindromicSubstring;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1960. Maximum Product of the Length of Two Palindromic Substrings
 * https://leetcode.com/problems/maximum-product-of-the-length-of-two-palindromic-substrings/
 *
 * You are given a 0-indexed string s and are tasked with finding two non-intersecting palindromic substrings of odd
 * length such that the product of their lengths is maximized.
 *
 * More formally, you want to choose four integers i, j, k, l such that 0 <= i <= j < k <= l < s.length and both the
 * substrings s[i...j] and s[k...l] are palindromes and have odd lengths. s[i...j] denotes a substring from index i to
 * index j inclusive.
 *
 * Return the maximum possible product of the lengths of the two non-intersecting palindromic substrings.
 *
 * A palindrome is a string that is the same forward and backward. A substring is a contiguous sequence of characters in
 * a string.
 *
 * Example 1:
 *
 * Input: s = "ababbb"
 * Output: 9
 * Explanation: Substrings "aba" and "bbb" are palindromes with odd length. product = 3 * 3 = 9.
 *
 * Example 2:
 *
 * Input: s = "zaaaxbbby"
 * Output: 9
 * Explanation: Substrings "aaa" and "bbb" are palindromes with odd length. product = 3 * 3 = 9.
 *
 * Constraints:
 *
 * 2 <= s.length <= 10^5
 * s consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1960_MaximumProductOfTheLengthOfTwoPalindromicSubstrings {

    /**
     * Manacher's Algorithm 马拉车(音译)算法, 参见 {@link Q005_LongestPalindromicSubstring#manacherAlg(java.lang.String)}
     */
//    @DebugWith("normal1")
    @Answer
    public long maxProduct(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;

        // Manacher's Algorithm, p 表示以p[i] 为中心的最长(奇数)回文字符串的半径.
        int[] p = new int[n];
        int id = 0, mx = 0;
        for (int i = 0; i < n; i++) {
            int max = i < mx ? Math.min(p[2 * id - i], mx - i) : 1;
            // 以i 为中心, 寻找最长回文.
            while (0 <= i - max && i + max < n
                    && cs[i - max] == cs[i + max]) {
                max++;
            }
            if (i + max > mx) {
                mx = i + max;
                id = i;
            }
            p[i] = max;
        }

        // start[i] 表示 [i, n) 区间内最长的(奇数)回文长度
        int[] start = new int[n];
        for (int i = 0; i < n; i++) {
            int idx = i - p[i] + 1;
            start[idx] = Math.max(start[idx], p[i] * 2 - 1);
        }
        for (int i = 1; i < n; i++) {
            start[i] = Math.max(start[i - 1] - 2, start[i]);
        }
        for (int i = n - 2; i >= 0; i--) {
            start[i] = Math.max(start[i], start[i + 1]);
        }

        // end[i] 表示 [0, i] 区间内最长的(奇数)回文长度
        int[] end = new int[n];
        for (int i = 0; i < n; i++) {
            int idx = i + p[i] - 1;
            end[idx] = Math.max(end[idx], p[i] * 2 - 1);
        }
        for (int i = n - 2; i >= 0; i--) {
            end[i] = Math.max(end[i], end[i + 1] - 2);
        }
        for (int i = 1; i < n; i++) {
            end[i] = Math.max(end[i - 1], end[i]);
        }

        // 求解结果
        long res = 0;
        for (int i = 1; i < n; i++) {
            res = Math.max(res, (long) end[i - 1] * start[i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("ababbb").expect(9L);

    @TestData
    public DataExpectation example2 = DataExpectation.create("zaaaxbbby").expect(9L);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create("ggbswiymmlevedhkbdhntnhdbkhdevelmmyiwsbgg")
            .expect(45L);

}
