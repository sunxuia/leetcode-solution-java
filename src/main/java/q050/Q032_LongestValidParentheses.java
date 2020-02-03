package q050;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-valid-parentheses/
 *
 * Given a string containing just the characters '(' and ')', find the length of the longest valid (well-formed)
 * parentheses substring.
 *
 * Example 1:
 *
 * Input: "(()"
 * Output: 2
 * Explanation: The longest valid parentheses substring is "()"
 * Example 2:
 *
 * Input: ")()())"
 * Output: 4
 * Explanation: The longest valid parentheses substring is "()()"
 */
@RunWith(LeetCodeRunner.class)
public class Q032_LongestValidParentheses {

    @Answer
    public int longestValidParentheses(String s) {
        Stack<Integer> stack = new Stack<>();
        int res = 0;
        for (int i = 0, lastMissMatch = -1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                stack.push(i);
            } else if (!stack.isEmpty()) {
                stack.pop();
                int last = stack.isEmpty() ? lastMissMatch : stack.peek();
                res = Math.max(res, i - last);
            } else {
                lastMissMatch = i;
            }
        }
        return res;
    }

    @Answer
    public int dp(String s) {
        // dp 表示当前 ')' 字符的结尾的有效字符串长度
        int[] dp = new int[s.length()];
        int res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == ')') {
                if (s.charAt(i - 1) == '(') {
                    // 如果上一个字符是'(' 就是一个'()'. 如果'()' 前面是')' 那么就与前面进行连接.
                    dp[i] = 2 + (i >= 2 && s.charAt(i - 2) == ')' ? dp[i - 2] : 0);
                } else {
                    // 如果上一个字符是')', 可能是'((xxx))' 的形式, 那么看前一个匹配的前一个字符是否
                    // '(', 如果是就形成了一个'((xxx))' 的形式. 最后再检查一下前面是否是')' 并连接.
                    int prev = i - dp[i - 1] - 1;
                    if (prev >= 0 && s.charAt(prev) == '(') {
                        dp[i] = 2 + dp[i - 1] + (prev - 1 >= 0 ? dp[prev - 1] : 0);
                    }
                }
                res = Math.max(res, dp[i]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("(()")
            .expect(2)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(")()())")
            .expect(4)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("()")
            .expect(2)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument("()(())")
            .expect(6)
            .build();
}
