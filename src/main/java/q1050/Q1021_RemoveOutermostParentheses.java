package q1050;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1021. Remove Outermost Parentheses
 * https://leetcode.com/problems/remove-outermost-parentheses/
 *
 * A valid parentheses string is either empty (""), "(" + A + ")", or A + B, where A and B are valid parentheses
 * strings, and + represents string concatenation.  For example, "", "()", "(())()", and "(()(()))" are all valid
 * parentheses strings.
 *
 * A valid parentheses string S is primitive if it is nonempty, and there does not exist a way to split it into S = A+B,
 * with A and B nonempty valid parentheses strings.
 *
 * Given a valid parentheses string S, consider its primitive decomposition: S = P_1 + P_2 + ... + P_k, where P_i are
 * primitive valid parentheses strings.
 *
 * Return S after removing the outermost parentheses of every primitive string in the primitive decomposition of S.
 *
 * Example 1:
 *
 * Input: "(()())(())"
 * Output: "()()()"
 * Explanation:
 * The input string is "(()())(())", with primitive decomposition "(()())" + "(())".
 * After removing outer parentheses of each part, this is "()()" + "()" = "()()()".
 *
 * Example 2:
 *
 * Input: "(()())(())(()(()))"
 * Output: "()()()()(())"
 * Explanation:
 * The input string is "(()())(())(()(()))", with primitive decomposition "(()())" + "(())" + "(()(()))".
 * After removing outer parentheses of each part, this is "()()" + "()" + "()(())" = "()()()()(())".
 *
 * Example 3:
 *
 * Input: "()()"
 * Output: ""
 * Explanation:
 * The input string is "()()", with primitive decomposition "()" + "()".
 * After removing outer parentheses of each part, this is "" + "" = "".
 *
 * Note:
 *
 * S.length <= 10000
 * S[i] is "(" or ")"
 * S is a valid parentheses string
 */
@RunWith(LeetCodeRunner.class)
public class Q1021_RemoveOutermostParentheses {

    @Answer
    public String removeOuterParentheses(String S) {
        StringBuilder sb = new StringBuilder();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < S.length(); i++) {
            if (S.charAt(i) == '(') {
                stack.push(i);
            } else {
                int open = stack.pop();
                if (stack.isEmpty()) {
                    sb.append(S, open + 1, i);
                }
            }
        }
        return sb.toString();
    }

    /**
     * 对上面使用栈的小优化
     */
    @Answer
    public String removeOuterParentheses2(String S) {
        StringBuilder sb = new StringBuilder();
        int count = 0;
        for (int i = 0; i < S.length(); i++) {
            char c = S.charAt(i);
            if (c == '(' && count > 0
                    || c == ')' && count > 1) {
                sb.append(c);
            }
            count += c == '(' ? 1 : -1;
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("(()())(())").expect("()()()");

    @TestData
    public DataExpectation example2 = DataExpectation.create("(()())(())(()(()))").expect("()()()()(())");

    @TestData
    public DataExpectation example3 = DataExpectation.create("()()").expect("");

}
