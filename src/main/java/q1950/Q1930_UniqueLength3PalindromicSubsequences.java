package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1930. Unique Length-3 Palindromic Subsequences
 * https://leetcode.com/problems/unique-length-3-palindromic-subsequences/
 *
 * Given a string s, return the number of unique palindromes of length three that are a subsequence of s.
 *
 * Note that even if there are multiple ways to obtain the same subsequence, it is still only counted once.
 *
 * A palindrome is a string that reads the same forwards and backwards.
 *
 * A subsequence of a string is a new string generated from the original string with some characters (can be none)
 * deleted without changing the relative order of the remaining characters.
 *
 * For example, "ace" is a subsequence of "abcde".
 *
 * Example 1:
 * (图Q1931_PIC1.png)
 * Input: s = "aabca"
 * Output: 3
 * Explanation: The 3 palindromic subsequences of length 3 are:
 * - "aba" (subsequence of "aabca")
 * - "aaa" (subsequence of "aabca")
 * - "aca" (subsequence of "aabca")
 *
 * Example 2:
 * (图Q1931_PIC2.png)
 * Input: s = "adc"
 * Output: 0
 * Explanation: There are no palindromic subsequences of length 3 in "adc".
 *
 * Example 3:
 *
 * Input: s = "bbcbaba"
 * Output: 4
 * Explanation: The 4 palindromic subsequences of length 3 are:
 * - "bbb" (subsequence of "bbcbaba")
 * - "bcb" (subsequence of "bbcbaba")
 * - "bab" (subsequence of "bbcbaba")
 * - "aba" (subsequence of "bbcbaba")
 *
 * Constraints:
 *
 * 3 <= s.length <= 10^5
 * s consists of only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1930_UniqueLength3PalindromicSubsequences {

    @Answer
    public int countPalindromicSubsequence(String s) {
        final int n = s.length();
        int[] counts = new int[26];
        int[] last = new int[26];
        int[] mask = new int[26];
        int res = 0;
        for (int i = 0; i < n; i++) {
            int idx = s.charAt(i) - 'a';
            if (counts[idx] > 0) {
                // a ... a ... a 这样的情况
                if (counts[idx] == 2) {
                    res++;
                }
                // a ... x ... a 这样的情况
                for (int j = 0; j < 26; j++) {
                    if (last[idx] < last[j] && (mask[idx] >> j & 1) == 0) {
                        mask[idx] |= 1 << j;
                        res++;
                    }
                }
            }
            counts[idx]++;
            last[idx] = i;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aabca").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("adc").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("bbcbaba").expect(4);

}
