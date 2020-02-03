package q050;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * Given a string containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is
 * valid.
 *
 * An input string is valid if:
 *
 * Open brackets must be closed by the same type of brackets.
 * Open brackets must be closed in the correct order.
 * Note that an empty string is also considered valid.
 *
 * Example 1:
 *
 * Input: "()"
 * Output: true
 * Example 2:
 *
 * Input: "()[]{}"
 * Output: true
 * Example 3:
 *
 * Input: "(]"
 * Output: false
 * Example 4:
 *
 * Input: "([)]"
 * Output: false
 * Example 5:
 *
 * Input: "{[]}"
 * Output: true
 */
@RunWith(LeetCodeRunner.class)
public class Q020_ValidParentheses {

    @Answer
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            Character last = stack.isEmpty() ? 'X' : stack.peek();
            if (c == ']' && last != '['
                    || c == '}' && last != '{'
                    || c == ')' && last != '(') {
                return false;
            }
            if (c == ']' && last == '['
                    || c == '}' && last == '{'
                    || c == ')' && last == '(') {
                stack.pop();
            } else {
                stack.push(c);
            }
        }
        return stack.isEmpty();
    }

    private boolean inRange(char c, char c1, char c2, char c3) {
        return c == c1 || c == c2 || c == c3;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("()")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("()[]{}")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("(]")
            .expect(false)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument("([)]")
            .expect(false)
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("{[]}")
            .expect(true)
            .build();
}
