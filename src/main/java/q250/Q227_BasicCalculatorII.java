package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/basic-calculator-ii/
 *
 * Implement a basic calculator to evaluate a simple expression string.
 *
 * The expression string contains only non-negative integers, +, -, *, / operators and empty spaces . The integer
 * division should truncate toward zero.
 *
 * Example 1:
 *
 * Input: "3+2*2"
 * Output: 7
 *
 * Example 2:
 *
 * Input: " 3/2 "
 * Output: 1
 *
 * Example 3:
 *
 * Input: " 3+5 / 2 "
 * Output: 5
 *
 * Note:
 *
 * You may assume that the given expression is always valid.
 * Do not use the eval built-in library function.
 */
@RunWith(LeetCodeRunner.class)
public class Q227_BasicCalculatorII {

    @Answer
    public int calculate(String s) {
        // num: 当前数字; res: 结果; mdv: 乘/除操作的上一结果;
        // pmFlag: 加减标记(1: 加, -1: 减); mdvFlag: 乘除标记(0: 不是乘除, 1: 乘, 2: 除);
        int num = 0, res = 0, mdv = 0, pmFlag = 1, mdvFlag = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == ' ') {
                continue;
            }
            if ('0' <= c && c <= '9') {
                num = num * 10 + c - '0';
            } else {
                if (mdvFlag > 0) {
                    num = mdvFlag == 1 ? mdv * num : mdv / num;
                }
                if (c == '+' || c == '-') {
                    res += pmFlag * num;
                    pmFlag = c == '+' ? 1 : -1;
                    mdvFlag = 0;
                } else {
                    mdv = num;
                    mdvFlag = c == '*' ? 1 : 2;
                }
                num = 0;
            }
        }
        if (mdvFlag > 0) {
            num = mdvFlag == 1 ? mdv * num : mdv / num;
        }
        res += pmFlag * num;
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("3+2*2").expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create(" 3/2 ").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(" 3+5 / 2 ").expect(5);

    @TestData
    public DataExpectation border1 = DataExpectation.create("10").expect(10);

    @TestData
    public DataExpectation border2 = DataExpectation.create("-1").expect(-1);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1/2").expect(0);

}
