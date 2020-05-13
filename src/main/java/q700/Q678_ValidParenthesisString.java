package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-parenthesis-string/
 *
 * Given a string containing only three types of characters: '(', ')' and '*', write a function to check whether
 * this string is valid. We define the validity of a string by these rules:
 *
 * Any left parenthesis '(' must have a corresponding right parenthesis ')'.
 * Any right parenthesis ')' must have a corresponding left parenthesis '('.
 * Left parenthesis '(' must go before the corresponding right parenthesis ')'.
 * '*' could be treated as a single right parenthesis ')' or a single left parenthesis '(' or an empty string.
 * An empty string is also valid.
 *
 * Example 1:
 *
 * Input: "()"
 * Output: True
 *
 * Example 2:
 *
 * Input: "(*)"
 * Output: True
 *
 * Example 3:
 *
 * Input: "(*))"
 * Output: True
 *
 * Note:
 *
 * The string size will be in the range [1, 100].
 */
@RunWith(LeetCodeRunner.class)
public class Q678_ValidParenthesisString {

    @Answer
    public boolean checkValidString(String s) {
        return recursion(s.toCharArray(), 0, 0);
    }

    // open 表示左括号的数量(包括解释为左括号的*号)
    private boolean recursion(char[] sc, int i, int open) {
        if (i == sc.length || open < 0) {
            return open == 0;
        }
        boolean res = false;
        if (sc[i] == '*') {
            res = recursion(sc, i + 1, open);
        }
        if (sc[i] == '*' || sc[i] == '(') {
            res = res || recursion(sc, i + 1, open + 1);
        }
        if (sc[i] == '*' || sc[i] == ')') {
            res = res || recursion(sc, i + 1, open - 1);
        }
        return res;
    }

    // LeetCode 上最快的解答: 贪婪算法.
    @Answer
    public boolean checkValidString2(String s) {
        // 分别表示左括号可能的最小数量和最大数量
        int low = 0, high = 0;
        for (char c : s.toCharArray()) {
            // (: +1, ): -1, *: -1, *号按照右括号来处理, 消去左括号
            low += c == '(' ? 1 : -1;
            // 如果low < 0 则需要将部分* 转换为左括号, 避免右括号多于左括号的情况
            low = Math.max(0, low);
            // (: +1, ): -1, *: +1, *号按照左括号来处理, 增加左括号
            high += c == ')' ? -1 : 1;
            // 如果最大数量 <0 则说明* 全部变成了左括号的情况下右括号还是多
            if (high < 0) {
                return false;
            }
        }
        // 左括号最大数量 >= 0 且最小数量 == 0
        // 如果 low > 0 则说明(从上一次low = 0 开始的区间内) 在* 号全部转换为右括号的情况下左括号还是多.
        return low == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("()").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("(*)").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create("(*))").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("(").expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .create("(())((())()()(*)(*()(())())())()()((()())((()))(*").expect(false);

}
