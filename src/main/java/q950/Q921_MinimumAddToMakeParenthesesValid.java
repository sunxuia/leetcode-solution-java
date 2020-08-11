package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 921. Minimum Add to Make Parentheses Valid
 * https://leetcode.com/problems/minimum-add-to-make-parentheses-valid/
 *
 * Given a string S of '(' and ')' parentheses, we add the minimum number of parentheses ( '(' or ')', and in any
 * positions ) so that the resulting parentheses string is valid.
 *
 * Formally, a parentheses string is valid if and only if:
 *
 * It is the empty string, or
 * It can be written as AB (A concatenated with B), where A and B are valid strings, or
 * It can be written as (A), where A is a valid string.
 *
 * Given a parentheses string, return the minimum number of parentheses we must add to make the resulting string valid.
 *
 * Example 1:
 *
 * Input: "())"
 * Output: 1
 *
 * Example 2:
 *
 * Input: "((("
 * Output: 3
 *
 * Example 3:
 *
 * Input: "()"
 * Output: 0
 *
 * Example 4:
 *
 * Input: "()))(("
 * Output: 4
 *
 * Note:
 *
 * S.length <= 1000
 * S only consists of '(' and ')' characters.
 */
@RunWith(LeetCodeRunner.class)
public class Q921_MinimumAddToMakeParenthesesValid {

    @Answer
    public int minAddToMakeValid(String S) {
        int left = 0, right = 0;
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                left++;
            } else if (left > 0) {
                left--;
            } else {
                right++;
            }
        }
        return right + left;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("())").expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create("(((").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create("()").expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create("()))((").expect(4);

}
