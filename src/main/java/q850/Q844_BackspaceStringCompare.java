package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/backspace-string-compare/
 *
 * Given two strings S and T, return if they are equal when both are typed into empty text editors. # means a
 * backspace character.
 *
 * Note that after backspacing an empty text, the text will continue empty.
 *
 * Example 1:
 *
 * Input: S = "ab#c", T = "ad#c"
 * Output: true
 * Explanation: Both S and T become "ac".
 *
 * Example 2:
 *
 * Input: S = "ab##", T = "c#d#"
 * Output: true
 * Explanation: Both S and T become "".
 *
 * Example 3:
 *
 * Input: S = "a##c", T = "#a#c"
 * Output: true
 * Explanation: Both S and T become "c".
 *
 * Example 4:
 *
 * Input: S = "a#c", T = "b"
 * Output: false
 * Explanation: S becomes "c" while T becomes "b".
 *
 * Note:
 *
 * 1 <= S.length <= 200
 * 1 <= T.length <= 200
 * S and T only contain lowercase letters and '#' characters.
 *
 * Follow up:
 *
 * Can you solve it in O(N) time and O(1) space?
 */
@RunWith(LeetCodeRunner.class)
public class Q844_BackspaceStringCompare {

    @Answer
    public boolean backspaceCompare(String S, String T) {
        return real(S).equals(real(T));
    }

    private String real(String str) {
        StringBuilder sb = new StringBuilder();
        for (char c : str.toCharArray()) {
            if (c == '#') {
                sb.setLength(Math.max(sb.length(), 1) - 1);
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("ab#c", "ad#c").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ab##", "c#d#").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("a##c", "#a#c").expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("a#c", "b").expect(false);

}
