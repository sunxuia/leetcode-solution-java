package q1150;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1106. Parsing A Boolean Expression
 * https://leetcode.com/problems/parsing-a-boolean-expression/
 *
 * Return the result of evaluating a given boolean expression, represented as a string.
 *
 * An expression can either be:
 *
 * "t", evaluating to True;
 * "f", evaluating to False;
 * "!(expr)", evaluating to the logical NOT of the inner expression expr;
 * "&(expr1,expr2,...)", evaluating to the logical AND of 2 or more inner expressions expr1, expr2, ...;
 * "|(expr1,expr2,...)", evaluating to the logical OR of 2 or more inner expressions expr1, expr2, ...
 *
 * Example 1:
 *
 * Input: expression = "!(f)"
 * Output: true
 *
 * Example 2:
 *
 * Input: expression = "|(f,t)"
 * Output: true
 *
 * Example 3:
 *
 * Input: expression = "&(t,f)"
 * Output: false
 *
 * Example 4:
 *
 * Input: expression = "|(&(t,f,t),!(t))"
 * Output: false
 *
 * Constraints:
 *
 * 1 <= expression.length <= 20000
 * expression[i] consists of characters in {'(', ')', '&', '|', '!', 't', 'f', ','}.
 * expression is a valid expression representing a boolean, as given in the description.
 */
@RunWith(LeetCodeRunner.class)
public class Q1106_ParsingABooleanExpression {

    @Answer
    public boolean parseBoolExpr(String expression) {
        Deque<Boolean> result = new ArrayDeque<>();
        Deque<Character> opers = new ArrayDeque<>();
        result.push(false);
        opers.push('|');
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            switch (c) {
                case 't':
                case 'f':
                    result.push(c == 't');
                    calculate(opers, result);
                    break;
                case '(':
                    result.push(opers.peek() == '&');
                    break;
                case ')':
                    opers.pop();
                    calculate(opers, result);
                    break;
                case '|':
                case '&':
                case '!':
                    opers.push(c);
                    break;
                default:
            }
        }
        return result.peek();
    }

    private void calculate(Deque<Character> opers, Deque<Boolean> result) {
        boolean curr = result.pop();
        boolean prev = result.pop();
        switch (opers.peek()) {
            case '!':
                result.push(!curr);
                break;
            case '&':
                result.push(prev && curr);
                break;
            case '|':
                result.push(prev || curr);
                break;
            default:
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("!(f)").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("|(f,t)").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create("&(t,f)").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create("|(&(t,f,t),!(t))").expect(false);

}
