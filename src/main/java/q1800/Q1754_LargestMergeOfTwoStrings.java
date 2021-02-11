package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1754. Largest Merge Of Two Strings
 * https://leetcode.com/problems/largest-merge-of-two-strings/
 *
 * You are given two strings word1 and word2. You want to construct a string merge in the following way: while either
 * word1 or word2 are non-empty, choose one of the following options:
 *
 * If word1 is non-empty, append the first character in word1 to merge and delete it from word1.
 *
 * For example, if word1 = "abc" and merge = "dv", then after choosing this operation, word1 = "bc" and merge = "dva".
 *
 *
 * If word2 is non-empty, append the first character in word2 to merge and delete it from word2.
 *
 * For example, if word2 = "abc" and merge = "", then after choosing this operation, word2 = "bc" and merge = "a".
 *
 *
 *
 * Return the lexicographically largest merge you can construct.
 *
 * A string a is lexicographically larger than a string b (of the same length) if in the first position where a and b
 * differ, a has a character strictly larger than the corresponding character in b. For example, "abcd" is
 * lexicographically larger than "abcc" because the first position they differ is at the fourth character, and d is
 * greater than c.
 *
 * Example 1:
 *
 * Input: word1 = "cabaa", word2 = "bcaaa"
 * Output: "cbcabaaaaa"
 * Explanation: One way to get the lexicographically largest merge is:
 * - Take from word1: merge = "c", word1 = "abaa", word2 = "bcaaa"
 * - Take from word2: merge = "cb", word1 = "abaa", word2 = "caaa"
 * - Take from word2: merge = "cbc", word1 = "abaa", word2 = "aaa"
 * - Take from word1: merge = "cbca", word1 = "baa", word2 = "aaa"
 * - Take from word1: merge = "cbcab", word1 = "aa", word2 = "aaa"
 * - Append the remaining 5 a's from word1 and word2 at the end of merge.
 *
 * Example 2:
 *
 * Input: word1 = "abcabc", word2 = "abdcaba"
 * Output: "abdcabcabcaba"
 *
 * Constraints:
 *
 * 1 <= word1.length, word2.length <= 3000
 * word1 and word2 consist only of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1754_LargestMergeOfTwoStrings {

    @Answer
    public String largestMerge(String word1, String word2) {
        final char[] sc1 = word1.toCharArray();
        final char[] sc2 = word2.toCharArray();
        final int m = sc1.length, n = sc2.length;

        // 比大小
        // comp[i][j] 表示 word1[i:) 是否大于 word2[j:) (如果相等则默认小于)
        boolean[][] comp = new boolean[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            // 哨兵, 避免下面遍历时越界
            comp[i][n] = true;
            for (int j = n - 1; j >= 0; j--) {
                if (sc1[i] == sc2[j]) {
                    comp[i][j] = comp[i + 1][j + 1];
                } else {
                    comp[i][j] = sc1[i] > sc2[j];
                }
            }
        }

        char[] res = new char[m + n];
        int i1 = 0, i2 = 0;
        for (int i = 0; i < m + n; i++) {
            if (comp[i1][i2]) {
                res[i] = sc1[i1++];
            } else {
                res[i] = sc2[i2++];
            }
        }
        return new String(res);
    }

    /**
     * 参考 LeetCode 上比较快的解法, 直接比较字符串, 这样在 OJ 上反而更快.
     */
    @Answer
    public String largestMerge2(String word1, String word2) {
        final int m = word1.length(), n = word2.length();
        char[] res = new char[m + n];
        int i1 = 0, i2 = 0;
        for (int i = 0; i < m + n; i++) {
            if (i1 == m) {
                res[i] = word2.charAt(i2++);
            } else if (i2 == n) {
                res[i] = word1.charAt(i1++);
            } else if (word1.charAt(i1) < word2.charAt(i2)) {
                res[i] = word2.charAt(i2++);
            } else if (word1.charAt(i1) > word2.charAt(i2)) {
                res[i] = word1.charAt(i1++);
            } else if (word1.substring(i1).compareTo(word2.substring(i2)) < 0) {
                res[i] = word2.charAt(i2++);
            } else {
                res[i] = word1.charAt(i1++);
            }
        }
        return new String(res);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("cabaa", "bcaaa").expect("cbcabaaaaa");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcabc", "abdcaba").expect("abdcabcabcaba");

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("guguuuuuuuuuuuuuuguguuuuguug", "gguggggggguuggguugggggg")
            .expect("guguuuuuuuuuuuuuuguguuuuguugguggggggguuggguuggggggg");

}
