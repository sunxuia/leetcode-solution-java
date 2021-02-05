package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1684. Count the Number of Consistent Strings
 * https://leetcode.com/problems/count-the-number-of-consistent-strings/
 *
 * You are given a string allowed consisting of distinct characters and an array of strings words. A string is
 * consistent if all characters in the string appear in the string allowed.
 *
 * Return the number of consistent strings in the array words.
 *
 * Example 1:
 *
 * Input: allowed = "ab", words = ["ad","bd","aaab","baa","badab"]
 * Output: 2
 * Explanation: Strings "aaab" and "baa" are consistent since they only contain characters 'a' and 'b'.
 *
 * Example 2:
 *
 * Input: allowed = "abc", words = ["a","b","c","ab","ac","bc","abc"]
 * Output: 7
 * Explanation: All strings are consistent.
 *
 * Example 3:
 *
 * Input: allowed = "cad", words = ["cc","acd","b","ba","bac","bad","ac","d"]
 * Output: 4
 * Explanation: Strings "cc", "acd", "ac", and "d" are consistent.
 *
 * Constraints:
 *
 * 1 <= words.length <= 104
 * 1 <= allowed.length <= 26
 * 1 <= words[i].length <= 10
 * The characters in allowed are distinct.
 * words[i] and allowed contain only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1684_CountTheNumberOfConsistentStrings {

    @Answer
    public int countConsistentStrings(String allowed, String[] words) {
        boolean[] exists = new boolean[26];
        for (int i = 0; i < allowed.length(); i++) {
            exists[allowed.charAt(i) - 'a'] = true;
        }

        int res = 0;
        for (String word : words) {
            boolean consistent = true;
            for (int i = 0; i < word.length() && consistent; i++) {
                consistent = exists[word.charAt(i) - 'a'];
            }
            if (consistent) {
                res++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("ab", new String[]{"ad", "bd", "aaab", "baa", "badab"})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("abc", new String[]{"a", "b", "c", "ab", "ac", "bc", "abc"})
            .expect(7);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("cad", new String[]{"cc", "acd", "b", "ba", "bac", "bad", "ac", "d"})
            .expect(4);

}
