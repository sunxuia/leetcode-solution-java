package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1662. Check If Two String Arrays are Equivalent
 * https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/
 *
 * Given two string arrays word1 and word2, return true if the two arrays represent the same string, and false
 * otherwise.
 *
 * A string is represented by an array if the array elements concatenated in order forms the string.
 *
 * Example 1:
 *
 * Input: word1 = ["ab", "c"], word2 = ["a", "bc"]
 * Output: true
 * Explanation:
 * word1 represents string "ab" + "c" -> "abc"
 * word2 represents string "a" + "bc" -> "abc"
 * The strings are the same, so return true.
 *
 * Example 2:
 *
 * Input: word1 = ["a", "cb"], word2 = ["ab", "c"]
 * Output: false
 *
 * Example 3:
 *
 * Input: word1  = ["abc", "d", "defg"], word2 = ["abcddefg"]
 * Output: true
 *
 * Constraints:
 *
 * 1 <= word1.length, word2.length <= 103
 * 1 <= word1[i].length, word2[i].length <= 103
 * 1 <= sum(word1[i].length), sum(word2[i].length) <= 103
 * word1[i] and word2[i] consist of lowercase letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1662_CheckIfTwoStringArraysAreEquivalent {

    @Answer
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        final int len1 = word1.length, len2 = word2.length;
        int a1 = 0, a2 = 0, b1 = 0, b2 = 0;
        while (true) {
            while (a1 < len1 && a2 == word1[a1].length()) {
                a1++;
                a2 = 0;
            }
            while (b1 < len2 && b2 == word2[b1].length()) {
                b1++;
                b2 = 0;
            }
            if (a1 == len1 || b1 == len2) {
                return a1 == len1 && b1 == len2;
            }
            if (word1[a1].charAt(a2) != word2[b1].charAt(b2)) {
                return false;
            }
            a2++;
            b2++;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"ab", "c"}, new String[]{"a", "bc"})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"a", "cb"}, new String[]{"ab", "c"})
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"abc", "d", "defg"}, new String[]{"abcddefg"})
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{"ecxarwyyy", "ppf", "tdyayjd"}, new String[]{"ecxarwyyyppft", "dyayj", "q"})
            .expect(false);

}
