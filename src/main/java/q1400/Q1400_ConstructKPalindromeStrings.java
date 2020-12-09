package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1400. Construct K Palindrome Strings
 * https://leetcode.com/problems/construct-k-palindrome-strings/
 *
 * Given a string s and an integer k. You should construct k non-empty palindrome strings using all the characters in
 * s.
 *
 * Return True if you can use all the characters in s to construct k palindrome strings or False otherwise.
 *
 * Example 1:
 *
 * Input: s = "annabelle", k = 2
 * Output: true
 * Explanation: You can construct two palindromes using all characters in s.
 * Some possible constructions "anna" + "elble", "anbna" + "elle", "anellena" + "b"
 *
 * Example 2:
 *
 * Input: s = "leetcode", k = 3
 * Output: false
 * Explanation: It is impossible to construct 3 palindromes using all the characters of s.
 *
 * Example 3:
 *
 * Input: s = "true", k = 4
 * Output: true
 * Explanation: The only possible solution is to put each character in a separate string.
 *
 * Example 4:
 *
 * Input: s = "yzyzyzyzyzyzyzy", k = 2
 * Output: true
 * Explanation: Simply you can put all z's in one string and all y's in the other string. Both strings will be
 * palindrome.
 *
 * Example 5:
 *
 * Input: s = "cr", k = 7
 * Output: false
 * Explanation: We don't have enough characters in s to construct 7 palindromes.
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * All characters in s are lower-case English letters.
 * 1 <= k <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1400_ConstructKPalindromeStrings {

    @Answer
    public boolean canConstruct(String s, int k) {
        if (s.length() < k) {
            return false;
        }
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
        }
        int single = 0;
        for (int i = 0; i < 26; i++) {
            single += counts[i] % 2;
        }
        return single <= k;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("annabelle", 2).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("leetcode", 3).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("true", 4).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("yzyzyzyzyzyzyzy", 2).expect(true);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith("cr", 7).expect(false);

}
