package q1700;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1657. Determine if Two Strings Are Close
 * https://leetcode.com/problems/determine-if-two-strings-are-close/
 *
 * Two strings are considered close if you can attain one from the other using the following operations:
 *
 * Operation 1: Swap any two existing characters.
 *
 * For example, abcde -> aecdb
 *
 *
 * Operation 2: Transform every occurrence of one existing character into another existing character, and do the same
 * with the other character.
 *
 * For example, aacabb -> bbcbaa (all a's turn into b's, and all b's turn into a's)
 *
 *
 *
 * You can use the operations on either string as many times as necessary.
 *
 * Given two strings, word1 and word2, return true if word1 and word2 are close, and false otherwise.
 *
 * Example 1:
 *
 * Input: word1 = "abc", word2 = "bca"
 * Output: true
 * Explanation: You can attain word2 from word1 in 2 operations.
 * Apply Operation 1: "abc" -> "acb"
 * Apply Operation 1: "acb" -> "bca"
 *
 * Example 2:
 *
 * Input: word1 = "a", word2 = "aa"
 * Output: false
 * Explanation: It is impossible to attain word2 from word1, or vice versa, in any number of operations.
 *
 * Example 3:
 *
 * Input: word1 = "cabbba", word2 = "abbccc"
 * Output: true
 * Explanation: You can attain word2 from word1 in 3 operations.
 * Apply Operation 1: "cabbba" -> "caabbb"
 * Apply Operation 2: "caabbb" -> "baaccc"
 * Apply Operation 2: "baaccc" -> "abbccc"
 *
 * Example 4:
 *
 * Input: word1 = "cabbba", word2 = "aabbss"
 * Output: false
 * Explanation: It is impossible to attain word2 from word1, or vice versa, in any amount of operations.
 *
 * Constraints:
 *
 * 1 <= word1.length, word2.length <= 105
 * word1 and word2 contain only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1657_DetermineIfTwoStringsAreClose {

    @Answer
    public boolean closeStrings(String word1, String word2) {
        final int n = word1.length();
        if (n != word2.length()) {
            return false;
        }
        int[] counts1 = new int[26];
        int[] counts2 = new int[26];
        for (int i = 0; i < n; i++) {
            counts1[word1.charAt(i) - 'a']++;
            counts2[word2.charAt(i) - 'a']++;
        }
        for (int i = 0; i < 26; i++) {
            if (counts1[i] == 0 && counts2[i] != 0
                    || counts1[i] != 0 && counts2[i] == 0) {
                return false;
            }
        }
        Arrays.sort(counts1);
        Arrays.sort(counts2);
        for (int i = 0; i < 26; i++) {
            if (counts1[i] != counts2[i]) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abc", "bca").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("a", "aa").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("cabbba", "abbccc").expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("cabbba", "aabbss").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("uau", "ssx").expect(false);

}
