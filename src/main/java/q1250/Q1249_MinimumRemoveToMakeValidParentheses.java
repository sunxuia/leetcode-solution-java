package q1250;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1249. Minimum Remove to Make Valid Parentheses
 * https://leetcode.com/problems/minimum-remove-to-make-valid-parentheses/
 *
 * Given a string s of '(' , ')' and lowercase English characters.
 *
 * Your task is to remove the minimum number of parentheses ( '(' or ')', in any positions ) so that the resulting
 * parentheses string is valid and return any valid string.
 *
 * Formally, a parentheses string is valid if and only if:
 *
 * It is the empty string, contains only lowercase characters, or
 * It can be written as AB (A concatenated with B), where A and B are valid strings, or
 * It can be written as (A), where A is a valid string.
 *
 * Example 1:
 *
 * Input: s = "lee(t(c)o)de)"
 * Output: "lee(t(c)o)de"
 * Explanation: "lee(t(co)de)" , "lee(t(c)ode)" would also be accepted.
 *
 * Example 2:
 *
 * Input: s = "a)b(c)d"
 * Output: "ab(c)d"
 *
 * Example 3:
 *
 * Input: s = "))(("
 * Output: ""
 * Explanation: An empty string is also valid.
 *
 * Example 4:
 *
 * Input: s = "(a(b(c)d)"
 * Output: "a(b(c)d)"
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s[i] is one of  '(' , ')' and lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1249_MinimumRemoveToMakeValidParentheses {

    @Answer
    public String minRemoveToMakeValid(String s) {
        Set<Integer> remove = new HashSet<>();
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                    stack.push(i);
                    remove.add(i);
                    break;
                case ')':
                    if (stack.isEmpty()) {
                        remove.add(i);
                    } else {
                        remove.remove(stack.pop());
                    }
                    break;
                default:
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if (!remove.contains(i)) {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();
    }

    /**
     * 针对上面的改进, 思路类似, 提升了性能.
     */
    @Answer
    public String minRemoveToMakeValid2(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int count = 0;
        for (int i = 0; i < n; i++) {
            switch (cs[i]) {
                case '(':
                    count++;
                    break;
                case ')':
                    if (count == 0) {
                        // 删除多余的")"
                        cs[i] = 0;
                    } else {
                        count--;
                    }
                    break;
                default:
            }
        }
        // 删除多余的"("
        for (int i = n - 1; i >= 0 && count > 0; i--) {
            if (cs[i] == '(') {
                cs[i] = 0;
                count--;
            }
        }

        int len = 0;
        for (int i = 0; i < n; i++) {
            if (cs[i] != 0) {
                cs[len++] = cs[i];
            }
        }
        return new String(cs, 0, len);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("lee(t(c)o)de)")
            .expect("lee(t(c)o)de").orExpect("lee(t(co)de)").orExpect("lee(t(c)ode)");

    @TestData
    public DataExpectation example2 = DataExpectation.create("a)b(c)d").expect("ab(c)d");

    @TestData
    public DataExpectation example3 = DataExpectation.create("))((").expect("");

    @TestData
    public DataExpectation example4 = DataExpectation.create("(a(b(c)d)").expect("a(b(c)d)").orExpect("(a(bc)d)");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("())()(((").expect("()()");

}
