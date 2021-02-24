package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1763. Longest Nice Substring
 * https://leetcode.com/problems/longest-nice-substring/
 *
 * A string s is nice if, for every letter of the alphabet that s contains, it appears both in uppercase and lowercase.
 * For example, "abABB" is nice because 'A' and 'a' appear, and 'B' and 'b' appear. However, "abA" is not because 'b'
 * appears, but 'B' does not.
 *
 * Given a string s, return the longest substring of s that is nice. If there are multiple, return the substring of the
 * earliest occurrence. If there are none, return an empty string.
 *
 * Example 1:
 *
 * Input: s = "YazaAay"
 * Output: "aAa"
 * Explanation: "aAa" is a nice string because 'A/a' is the only letter of the alphabet in s, and both 'A' and 'a'
 * appear.
 * "aAa" is the longest nice substring.
 *
 * Example 2:
 *
 * Input: s = "Bb"
 * Output: "Bb"
 * Explanation: "Bb" is a nice string because both 'B' and 'b' appear. The whole string is a substring.
 *
 * Example 3:
 *
 * Input: s = "c"
 * Output: ""
 * Explanation: There are no nice substrings.
 *
 * Example 4:
 *
 * Input: s = "dDzeE"
 * Output: "dD"
 * Explanation: Both "dD" and "eE" are the longest nice substrings.
 * As there are multiple longest nice substrings, return "dD" since it occurs earlier.
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * s consists of uppercase and lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1763_LongestNiceSubstring {

    @Answer
    public String longestNiceSubstring(String s) {
        final char[] cs = s.toCharArray();
        int start = 0, length = 0;
        for (int i = 0; i < cs.length; i++) {
            int[] counts = new int[128];
            loop:
            for (int j = i; j < cs.length; j++) {
                counts[cs[j]]++;
                for (int k = 0; k < 26; k++) {
                    if (counts['a' + k] != 0 && counts['A' + k] == 0
                            || counts['a' + k] == 0 && counts['A' + k] != 0) {
                        continue loop;
                    }
                }
                if (j - i + 1 > length) {
                    start = i;
                    length = j - i + 1;
                }
            }
        }
        return new String(cs, start, length);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("YazaAay").expect("aAa");

    @TestData
    public DataExpectation example2 = DataExpectation.create("Bb").expect("Bb");

    @TestData
    public DataExpectation example3 = DataExpectation.create("c").expect("");

    @TestData
    public DataExpectation example4 = DataExpectation.create("dDzeE").expect("dD");

}
