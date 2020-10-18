package q1200;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1190. Reverse Substrings Between Each Pair of Parentheses
 * https://leetcode.com/problems/reverse-substrings-between-each-pair-of-parentheses/
 *
 * You are given a string s that consists of lower case English letters and brackets.
 *
 * Reverse the strings in each pair of matching parentheses, starting from the innermost one.
 *
 * Your result should not contain any brackets.
 *
 * Example 1:
 *
 * Input: s = "(abcd)"
 * Output: "dcba"
 *
 * Example 2:
 *
 * Input: s = "(u(love)i)"
 * Output: "iloveu"
 * Explanation: The substring "love" is reversed first, then the whole string is reversed.
 *
 * Example 3:
 *
 * Input: s = "(ed(et(oc))el)"
 * Output: "leetcode"
 * Explanation: First, we reverse the substring "oc", then "etco", and finally, the whole string.
 *
 * Example 4:
 *
 * Input: s = "a(bcdefghijkl(mno)p)q"
 * Output: "apmnolkjihgfedcbq"
 *
 * Constraints:
 *
 * 0 <= s.length <= 2000
 * s only contains lower case English characters and parentheses.
 * It's guaranteed that all parentheses are balanced.
 */
@RunWith(LeetCodeRunner.class)
public class Q1190_ReverseSubstringsBetweenEachPairOfParentheses {

    /**
     * 递归方式
     */
    @Answer
    public String reverseParentheses(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        int count = 0, prev = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                    if (count++ == 0) {
                        prev = i + 1;
                    }
                    break;
                case ')':
                    if (--count == 0) {
                        String substr = reverseParentheses(s.substring(prev, i));
                        sb.append(new StringBuilder(substr).reverse());
                    }
                    break;
                default:
                    if (count == 0) {
                        sb.append(c);
                    }
            }
        }
        return sb.toString();
    }

    /**
     * 栈的形式
     */
    @Answer
    public String reverseParentheses2(String s) {
        Stack<Integer> prev = new Stack<>();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            switch (c) {
                case '(':
                    prev.push(sb.length());
                    break;
                case ')':
                    reverse(sb, prev.pop(), sb.length() - 1);
                    break;
                default:
                    sb.append(c);
            }
        }
        return sb.toString();
    }

    private void reverse(StringBuilder sb, int start, int end) {
        while (start < end) {
            char t = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));
            sb.setCharAt(end, t);
            start++;
            end--;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("(abcd)").expect("dcba");

    @TestData
    public DataExpectation example2 = DataExpectation.create("(u(love)i)").expect("iloveu");

    @TestData
    public DataExpectation example3 = DataExpectation.create("(ed(et(oc))el)").expect("leetcode");

    @TestData
    public DataExpectation example4 = DataExpectation.create("a(bcdefghijkl(mno)p)q").expect("apmnolkjihgfedcbq");

}
