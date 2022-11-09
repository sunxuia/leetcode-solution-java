package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 2000. Reverse Prefix of Word
 * https://leetcode.com/problems/reverse-prefix-of-word/
 *
 * Given a 0-indexed string word and a character ch, reverse the segment of word that starts at index 0 and ends at the
 * index of the first occurrence of ch (inclusive). If the character ch does not exist in word, do nothing.
 *
 * For example, if word = "abcdefd" and ch = "d", then you should reverse the segment that starts at 0 and ends at 3
 * (inclusive). The resulting string will be "dcbaefd".
 *
 * Return the resulting string.
 *
 * Example 1:
 *
 * Input: word = "abcdefd", ch = "d"
 * Output: "dcbaefd"
 * Explanation: The first occurrence of "d" is at index 3.
 * Reverse the part of word from 0 to 3 (inclusive), the resulting string is "dcbaefd".
 *
 * Example 2:
 *
 * Input: word = "xyxzxe", ch = "z"
 * Output: "zxyxxe"
 * Explanation: The first and only occurrence of "z" is at index 3.
 * Reverse the part of word from 0 to 3 (inclusive), the resulting string is "zxyxxe".
 *
 * Example 3:
 *
 * Input: word = "abcd", ch = "z"
 * Output: "abcd"
 * Explanation: "z" does not exist in word.
 * You should not do any reverse operation, the resulting string is "abcd".
 *
 * Constraints:
 *
 * 1 <= word.length <= 250
 * word consists of lowercase English letters.
 * ch is a lowercase English letter.
 */
@RunWith(LeetCodeRunner.class)
public class Q2000_ReversePrefixOfWord {

    @Answer
    public String reversePrefix(String word, char ch) {
        char[] cs = word.toCharArray();
        int end = 0;
        while (end < cs.length && cs[end] != ch) {
            end++;
        }
        if (end == cs.length) {
            return word;
        }
        for (int i = 0, j = end; i < j; i++, j--) {
            char t = cs[i];
            cs[i] = cs[j];
            cs[j] = t;
        }
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcdefd", 'd').expect("dcbaefd");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("xyxzxe", 'z').expect("zxyxxe");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("abcd", 'z').expect("abcd");

}
