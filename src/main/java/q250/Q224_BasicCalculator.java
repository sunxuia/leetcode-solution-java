package q250;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/basic-calculator/
 *
 * Implement a basic calculator to evaluate a simple expression string.
 *
 * The expression string may contain open ( and closing parentheses ), the plus + or minus sign -, non-negative
 * integers and empty spaces .
 *
 * Example 1:
 *
 * Input: "1 + 1"
 * Output: 2
 *
 * Example 2:
 *
 * Input: " 2-1 + 2 "
 * Output: 3
 *
 * Example 3:
 *
 * Input: "(1+(4+5+2)-3)+(6+8)"
 * Output: 23
 *
 * Note:
 *
 * You may assume that the given expression is always valid.
 * Do not use the eval built-in library function.
 */
@RunWith(LeetCodeRunner.class)
public class Q224_BasicCalculator {

    @Answer
    public int calculate(String s) {
        Deque<Integer> stack = new ArrayDeque<>();
        // 哨兵
        stack.push(0);
        int num = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if (c >= '0' && c <= '9') {
                num = num * 10 + c - '0';
            } else if (c == '(') {
                // stack 中使用数字0 分隔( 分隔的段落.
                stack.push(0);
            } else {
                // 在栈顶计算之前的值.
                if (stack.element() != 0) {
                    int operation = stack.pop();
                    int prevNum = stack.pop();
                    num = prevNum + operation * num;
                }
                if (c == ')') {
                    stack.pop();
                } else {
                    // c == '+' || c == '-'
                    // 压入操作数和操作符
                    stack.push(num);
                    stack.push(c == '+' ? 1 : -1);
                    num = 0;
                }
            }
        }
        if (stack.element() != 0) {
            int prevOperation = stack.pop();
            int prevNum = stack.pop();
            num = prevNum + prevOperation * num;
        }
        return num;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("1 + 1").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("2 - 1 + 2").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create("(1+(4+5+2)-3)+(6+8)").expect(23);

    @TestData
    public DataExpectation border = DataExpectation.create("1").expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1 - (2 + 3)").expect(-4);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("1 - (2 - (3 - 4))").expect(-2);

}
