package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1768. Merge Strings Alternately
 * https://leetcode.com/problems/merge-strings-alternately/
 *
 * You are given two strings word1 and word2. Merge the strings by adding letters in alternating order, starting with
 * word1. If a string is longer than the other, append the additional letters onto the end of the merged string.
 *
 * Return the merged string.
 *
 * Example 1:
 *
 * Input: word1 = "abc", word2 = "pqr"
 * Output: "apbqcr"
 * Explanation: The merged string will be merged as so:
 * word1:  a   b   c
 * word2:    p   q   r
 * merged: a p b q c r
 *
 * Example 2:
 *
 * Input: word1 = "ab", word2 = "pqrs"
 * Output: "apbqrs"
 * Explanation: Notice that as word2 is longer, "rs" is appended to the end.
 * word1:  a   b
 * word2:    p   q   r   s
 * merged: a p b q   r   s
 *
 * Example 3:
 *
 * Input: word1 = "abcd", word2 = "pq"
 * Output: "apbqcd"
 * Explanation: Notice that as word1 is longer, "cd" is appended to the end.
 * word1:  a   b   c   d
 * word2:    p   q
 * merged: a p b q c   d
 *
 * Constraints:
 *
 * 1 <= word1.length, word2.length <= 100
 * word1 and word2 consist of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1768_MergeStringsAlternately {

    @Answer
    public String mergeAlternately(String word1, String word2) {
        final int m = word1.length(), n = word2.length();
        StringBuilder sb = new StringBuilder(m + n);
        int i = 0, j = 0;
        while (i < m || j < n) {
            if (i < m) {
                sb.append(word1.charAt(i++));
            }
            if (j < n) {
                sb.append(word2.charAt(j++));
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abc", "pqr").expect("apbqcr");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ab", "pqrs").expect("apbqrs");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("abcd", "pq").expect("apbqcd");

}
