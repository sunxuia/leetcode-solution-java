package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1347. Minimum Number of Steps to Make Two Strings Anagram
 * https://leetcode.com/problems/minimum-number-of-steps-to-make-two-strings-anagram/
 *
 * Given two equal-size strings s and t. In one step you can choose any character of t and replace it with another
 * character.
 *
 * Return the minimum number of steps to make t an anagram of s.
 *
 * An Anagram of a string is a string that contains the same characters with a different (or the same) ordering.
 *
 * Example 1:
 *
 * Input: s = "bab", t = "aba"
 * Output: 1
 * Explanation: Replace the first 'a' in t with b, t = "bba" which is anagram of s.
 *
 * Example 2:
 *
 * Input: s = "leetcode", t = "practice"
 * Output: 5
 * Explanation: Replace 'p', 'r', 'a', 'i' and 'c' from t with proper characters to make t anagram of s.
 *
 * Example 3:
 *
 * Input: s = "anagram", t = "mangaar"
 * Output: 0
 * Explanation: "anagram" and "mangaar" are anagrams.
 *
 * Example 4:
 *
 * Input: s = "xxyyzz", t = "xxyyzz"
 * Output: 0
 *
 * Example 5:
 *
 * Input: s = "friend", t = "family"
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= s.length <= 50000
 * s.length == t.length
 * s and t contain lower-case English letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1347_MinimumNumberOfStepsToMakeTwoStringsAnagram {

    @Answer
    public int minSteps(String s, String t) {
        int[] counts = new int[26];
        for (int i = 0; i < s.length(); i++) {
            counts[s.charAt(i) - 'a']++;
            counts[t.charAt(i) - 'a']--;
        }

        int diff = 0;
        for (int i = 0; i < 26; i++) {
            diff += Math.abs(counts[i]);
        }
        return diff / 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("bab", "aba").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("leetcode", "practice").expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("anagram", "mangaar").expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("xxyyzz", "xxyyzz").expect(0);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith("friend", "family").expect(4);

}
