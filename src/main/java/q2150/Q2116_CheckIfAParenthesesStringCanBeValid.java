package q2150;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2116. Check if a Parentheses String Can Be Valid</h3>
 * <a href="https://leetcode.com/problems/check-if-a-parentheses-string-can-be-valid/">
 * https://leetcode.com/problems/check-if-a-parentheses-string-can-be-valid/
 * </a><br/>
 *
 * <p>A parentheses string is a <strong>non-empty</strong> string consisting only of <code>&#39;(&#39;</code> and
 * <code>&#39;)&#39;</code>. It is valid if <strong>any</strong> of the following conditions is
 * <strong>true</strong>:</p>
 *
 * <ul>
 * 	<li>It is <code>()</code>.</li>
 * 	<li>It can be written as <code>AB</code> (<code>A</code> concatenated with <code>B</code>), where <code>A</code>
 * 	and <code>B</code> are valid parentheses strings.</li>
 * 	<li>It can be written as <code>(A)</code>, where <code>A</code> is a valid parentheses string.</li>
 * </ul>
 *
 * <p>You are given a parentheses string <code>s</code> and a string <code>locked</code>, both of length
 * <code>n</code>. <code>locked</code> is a binary string consisting only of <code>&#39;0&#39;</code>s and
 * <code>&#39;1&#39;</code>s. For <strong>each</strong> index <code>i</code> of <code>locked</code>,</p>
 *
 * <ul>
 * 	<li>If <code>locked[i]</code> is <code>&#39;1&#39;</code>, you <strong>cannot</strong> change <code>s[i]</code>
 * 	.</li>
 * 	<li>But if <code>locked[i]</code> is <code>&#39;0&#39;</code>, you <strong>can</strong> change <code>s[i]</code>
 * 	to either <code>&#39;(&#39;</code> or <code>&#39;)&#39;</code>.</li>
 * </ul>
 *
 * <p>Return <code>true</code> <em>if you can make <code>s</code> a valid parentheses string</em>. Otherwise, return
 * <code>false</code>.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2021/11/06/eg1.png" style="width: 311px; height: 101px;" />
 * <pre>
 * <strong>Input:</strong> s = &quot;))()))&quot;, locked = &quot;010100&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> locked[1] == &#39;1&#39; and locked[3] == &#39;1&#39;, so we cannot change s[1] or s[3].
 * We change s[0] and s[4] to &#39;(&#39; while leaving s[2] and s[5] unchanged to make s valid.</pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;()()&quot;, locked = &quot;0000&quot;
 * <strong>Output:</strong> true
 * <strong>Explanation:</strong> We do not need to make any changes because s is already valid.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> s = &quot;)&quot;, locked = &quot;0&quot;
 * <strong>Output:</strong> false
 * <strong>Explanation:</strong> locked permits us to change s[0].
 * Changing s[0] to either &#39;(&#39; or &#39;)&#39; will not make s valid.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>n == s.length == locked.length</code></li>
 * 	<li><code>1 &lt;= n &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>s[i]</code> is either <code>&#39;(&#39;</code> or <code>&#39;)&#39;</code>.</li>
 * 	<li><code>locked[i]</code> is either <code>&#39;0&#39;</code> or <code>&#39;1&#39;</code>.</li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2116_CheckIfAParenthesesStringCanBeValid {

    // 根据topic 提示, 使用stack 的做法
    @Answer
    public boolean canBeValid(String s, String locked) {
        final int n = s.length();
        if (n % 2 == 1) {
            return false;
        }
        Deque<Integer> stack = new ArrayDeque<>();
        int flexible = 0;
        for (int i = 0; i < n; i++) {
            if (locked.charAt(i) == '0') {
                flexible++;
            } else if (s.charAt(i) == ')') {
                if (stack.isEmpty()) {
                    if (flexible == 0) {
                        return false;
                    } else {
                        flexible--;
                    }
                } else {
                    flexible += stack.pop();
                }
            } else {
                // fixed '('
                stack.push(flexible);
                flexible = 0;
            }
        }
        while (!stack.isEmpty()) {
            if (flexible == 0) {
                return false;
            }
            flexible = flexible - 1 + stack.pop();
        }
        return true;
    }

    // leetcode 上比较快的解法
    @Answer
    public boolean canBeValid2(String s, String locked) {
        final int n = s.length();
        if (n % 2 == 1) {
            return false;
        }

        // 最多和最少的'(' 的数量
        int maxLeft = 0, minLeft = 0;
        for (int i = 0; i < n; i++) {
            if (locked.charAt(i) == '1') {
                if (s.charAt(i) == '(') {
                    // 固定的'(', 都+1
                    maxLeft++;
                    minLeft++;
                } else {
                    // 固定的')' 抵消一个'('
                    maxLeft--;
                    minLeft--;
                }
            } else {
                // 可变的
                maxLeft++;
                minLeft--;
            }

            // ')' 比所有可能的 '(' 多了, 说明不可能是有效的
            if (maxLeft < 0) {
                return false;
            }
            // 如果固定的'(' 被')' 抵消的多了,
            // 那就使用可变位来抵消, 固定的'(' 归0
            if (minLeft < 0) {
                minLeft = 0;
            }
        }
        // 固定的 '(' 都需要被匹配掉
        return minLeft == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("))()))", "010100").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("()()", "0000").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(")", "0").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("())(()(()(())()())(())((())(()())((())))))(((((((())(()))))(",
                    "100011110110011011010111100111011101111110000101001101001111")
            .expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("(()(", "1001").expect(false);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(")(", "01").expect(false);

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(")(", "00").expect(true);

    // @formatter:off
    @TestData
    public DataExpectation normal5 = DataExpectation
            .createWith("(()))()))(()((()()(((()))()()()()()())))()()(()())()(()((()()((()((((((()(()()(()()())(((((())((()))))()(((((((()()())()))())((((((()(())())()((())()()((())((((())(((())(())()()))(((()()())())))((()))))()()()((()))())(())(((()()((())(())(())())()((()))())(())()(()())((((()(((())((()()())(((()(((((()))()))))))(()((())())(())))))(())(((())()()(()))())())))(((())))()()))()())))))())()(()()))(())(()())))())()))((((())(()))()(((())())(()(()))()))((()(())()()))))())(()(((((()", "110001111001011100000100011110101000100110010010011001110010111111100111000100000000101111101001111111011101001110011001001100100001100000000010100010101101100000100001000110111000111110110010111011010010100011111101110011100010010001111001010001001000111101101111111011001000100111100110101000100011011001001100110011111111111100101000100111111100000100101101000101111101000011110001001011111010011010000100100000000011101011001110000110011000100001110101100101111111110100").expect(false);
    // @formatter:on

}
