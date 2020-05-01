package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/permutation-in-string/
 *
 * Given two strings s1 and s2, write a function to return true if s2 contains the permutation of s1. In other words,
 * one of the first string's permutations is the substring of the second string.
 *
 * Example 1:
 *
 * Input: s1 = "ab" s2 = "eidbaooo"
 * Output: True
 * Explanation: s2 contains one permutation of s1 ("ba").
 *
 * Example 2:
 *
 * Input:s1= "ab" s2 = "eidboaoo"
 * Output: False
 *
 * Note:
 *
 * 1. The input strings only contain lower case letters.
 * 2. The length of both given strings is in range [1, 10,000].
 */
@RunWith(LeetCodeRunner.class)
public class Q567_PermutationInString {

    @Answer
    public boolean checkInclusion(String s1, String s2) {
        final int len1 = s1.length(), len2 = s2.length();
        if (len1 > len2) {
            return false;
        }
        int[] counts = new int[26];
        for (int i = 0; i < len1; i++) {
            counts[s1.charAt(i) - 'a']++;
        }
        for (int i = 0; i < len2; i++) {
            counts[s2.charAt(i) - 'a']--;
            if (i >= len1) {
                counts[s2.charAt(i - len1) - 'a']++;
            }
            if (isValid(counts)) {
                return true;
            }
        }
        return false;
    }

    private boolean isValid(int[] counts) {
        for (int count : counts) {
            if (count != 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ab", "eidbaooo").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ab", "eidboaoo").expect(false);

}
