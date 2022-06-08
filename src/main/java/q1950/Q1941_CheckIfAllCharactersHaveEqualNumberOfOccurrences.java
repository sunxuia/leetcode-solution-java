package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1941. Check if All Characters Have Equal Number of Occurrences
 * https://leetcode.com/problems/check-if-all-characters-have-equal-number-of-occurrences/
 *
 * Given a string s, return true if s is a good string, or false otherwise.
 *
 * A string s is good if all the characters that appear in s have the same number of occurrences (i.e., the same
 * frequency).
 *
 * Example 1:
 *
 * Input: s = "abacbc"
 * Output: true
 * Explanation: The characters that appear in s are 'a', 'b', and 'c'. All characters occur 2 times in s.
 *
 * Example 2:
 *
 * Input: s = "aaabb"
 * Output: false
 * Explanation: The characters that appear in s are 'a' and 'b'.
 * 'a' occurs 3 times while 'b' occurs 2 times, which is not the same number of times.
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * s consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1941_CheckIfAllCharactersHaveEqualNumberOfOccurrences {

    @Answer
    public boolean areOccurrencesEqual(String s) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }
        int prev = 0;
        for (int i = 0; i < 26; i++) {
            if (counts[i] > 0) {
                if (prev == 0) {
                    prev = counts[i];
                } else if (prev != counts[i]) {
                    return false;
                }
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abacbc").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aaabb").expect(false);

}
