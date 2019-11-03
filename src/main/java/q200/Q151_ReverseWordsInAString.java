package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/reverse-words-in-a-string/
 *
 * Given an input string, reverse the string word by word.
 *
 *
 *
 * Example 1:
 *
 * Input: "the sky is blue"
 * Output: "blue is sky the"
 *
 * Example 2:
 *
 * Input: "  hello world!  "
 * Output: "world! hello"
 * Explanation: Your reversed string should not contain leading or trailing spaces.
 *
 * Example 3:
 *
 * Input: "a good   example"
 * Output: "example good a"
 * Explanation: You need to reduce multiple spaces between two words to a single space in the reversed string.
 *
 *
 *
 * Note:
 *
 * A word is defined as a sequence of non-space characters.
 * Input string may contain leading or trailing spaces. However, your reversed string should not contain leading
 * or trailing spaces.
 * You need to reduce multiple spaces between two words to a single space in the reversed string.
 *
 *
 *
 * Follow up:
 *
 * For C programmers, try to solve it in-place in O(1) extra space.
 */
@RunWith(LeetCodeRunner.class)
public class Q151_ReverseWordsInAString {

    @Answer
    public String reverseWords(String s) {
        char[] cs = new char[s.length() + 1];
        cs[0] = ' '; // 哨兵
        for (int i = 0; i < s.length(); i++) {
            cs[i + 1] = s.charAt(i);
        }
        StringBuilder sb = new StringBuilder();
        for (int i = cs.length - 1, last = i; i >= 0; i--) {
            if (cs[i] == ' ') {
                if (i < last) {
                    for (int j = i + 1; j <= last; j++) {
                        sb.append(cs[j]);
                    }
                    sb.append(' ');
                }
                last = i - 1;
            }
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("the sky is blue").expect("blue is sky the");

    @TestData
    public DataExpectation example2 = DataExpectation.create("  hello world!  ").expect("world! hello");

    @TestData
    public DataExpectation example3 = DataExpectation.create("a good   example").expect("example good a");

    @TestData
    public DataExpectation border1 = DataExpectation.create("   ").expect("");

}
