package q250;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/different-ways-to-add-parentheses/
 *
 * Given a string of numbers and operators, return all possible results from computing all the different possible
 * ways to group numbers and operators. The valid operators are +, - and *.
 *
 * Example 1:
 *
 * Input: "2-1-1"
 * Output: [0, 2]
 * Explanation:
 * ((2-1)-1) = 0
 * (2-(1-1)) = 2
 *
 * Example 2:
 *
 * Input: "2*3-4*5"
 * Output: [-34, -14, -10, -10, 10]
 * Explanation:
 * (2*(3-(4*5))) = -34
 * ((2*3)-(4*5)) = -14
 * ((2*(3-4))*5) = -10
 * (2*((3-4)*5)) = -10
 * (((2*3)-4)*5) = 10
 */
@RunWith(LeetCodeRunner.class)
public class Q241_DifferentWaysToAddParentheses {

    // 这题看懂了就比较无聊了, 容易错的点在最后分隔的算式一定是被括号两分的.
    // 这题还可以使用dp 或者cache 来进行优化
    @Answer
    public List<Integer> diffWaysToCompute(String input) {
        List<Integer> expression = getExpression(input);
        return dfs(expression, 0, expression.size() - 1);
    }

    private List<Integer> getExpression(String input) {
        List<Integer> expression = new ArrayList<>();
        int num = 0, sign = 1;
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            if (Character.isDigit(c)) {
                num = num * 10 + sign * (c - '0');
            } else if (i == 0 || !Character.isDigit(input.charAt(i - 1))) {
                sign = c == '+' ? 1 : -1;
            } else {
                expression.add(num);
                expression.add((int) c);
                num = 0;
                sign = 1;
            }
        }
        expression.add(num);
        return expression;
    }

    private List<Integer> dfs(List<Integer> expression, int start, int end) {
        if (start >= end - 2) {
            return Collections.singletonList(calculate(expression, start, end));
        }
        List<Integer> res = new ArrayList<>();
        for (int i = start; i < end; i += 2) {
            List<Integer> left = dfs(expression, start, i);
            List<Integer> right = dfs(expression, i + 2, end);
            for (Integer l : left) {
                for (Integer r : right) {
                    res.add(calculate(Arrays.asList(l, expression.get(i + 1), r), 0, 2));
                }
            }
        }
        return res;
    }

    private int calculate(List<Integer> expression, int start, int end) {
        int res = 0, sign = 1, m = 1;
        for (int i = start; i < end; i += 2) {
            int val = expression.get(i);
            char operator = (char) expression.get(i + 1).intValue();
            if (operator == '*') {
                m *= val;
            } else {
                res += sign * m * val;
                m = 1;
                sign = operator == '+' ? 1 : -1;
            }
        }
        res += sign * m * expression.get(end);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("2-1-1")
            .expect(Arrays.asList(0, 2))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("2*3-4*5")
            .expect(Arrays.asList(-34, -14, -10, -10, 10))
            .unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.create("1+1").expect(Collections.singleton(2));

    @TestData
    public DataExpectation normal2 = DataExpectation.create("11").expect(Collections.singleton(11));

    @TestData
    public DataExpectation normal3 = DataExpectation.create("-1+1").expect(Collections.singleton(0));

}
