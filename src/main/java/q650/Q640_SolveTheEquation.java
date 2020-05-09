package q650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/solve-the-equation/
 *
 * Solve a given equation and return the value of x in the form of string "x=#value". The equation contains only
 * '+', '-' operation, the variable x and its coefficient.
 *
 * If there is no solution for the equation, return "No solution".
 *
 * If there are infinite solutions for the equation, return "Infinite solutions".
 *
 * If there is exactly one solution for the equation, we ensure that the value of x is an integer.
 *
 * Example 1:
 *
 * Input: "x+5-3+x=6+x-2"
 * Output: "x=2"
 *
 * Example 2:
 *
 * Input: "x=x"
 * Output: "Infinite solutions"
 *
 * Example 3:
 *
 * Input: "2x=x"
 * Output: "x=0"
 *
 * Example 4:
 *
 * Input: "2x+3x-6x=x+2"
 * Output: "x=-1"
 *
 * Example 5:
 *
 * Input: "x=x+2"
 * Output: "No solution"
 */
@RunWith(LeetCodeRunner.class)
public class Q640_SolveTheEquation {

    @Answer
    public String solveEquation(String equation) {
        int equalIndex = equation.indexOf("=");
        int[] left = simplifyExpression(equation, 0, equalIndex);
        int[] right = simplifyExpression(equation, equalIndex + 1, equation.length());
        left[0] -= right[0];
        left[1] -= right[1];
        if (left[0] == 0 && left[1] == 0) {
            return "Infinite solutions";
        } else if (left[1] == 0) {
            return "No solution";
        } else {
            return "x=" + (-left[0] / left[1]);
        }
    }

    private int[] simplifyExpression(String expression, int begin, int end) {
        int value = 0, x = 0, normal = 0, sign = 1;
        for (int i = begin; i < end; i++) {
            char c = expression.charAt(i);
            if (c == '+') {
                normal += sign * value;
                sign = 1;
                value = 0;
            } else if (c == '-') {
                normal += sign * value;
                sign = -1;
                value = 0;
            } else if (c == 'x') {
                // 避免0x 这种情况
                if (value != 0 || i == 0 || expression.charAt(i - 1) != '0') {
                    x += value == 0 ? sign : sign * value;
                }
                sign = 1;
                value = 0;
            } else {
                value = value * 10 + c - '0';
            }
        }
        normal += sign * value;
        return new int[]{normal, x};
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("x+5-3+x=6+x-2").expect("x=2");

    @TestData
    public DataExpectation example2 = DataExpectation.create("x=x").expect("Infinite solutions");

    @TestData
    public DataExpectation example3 = DataExpectation.create("2x=x").expect("x=0");

    @TestData
    public DataExpectation example4 = DataExpectation.create("2x+3x-6x=x+2").expect("x=-1");

    @TestData
    public DataExpectation example5 = DataExpectation.create("x=x+2").expect("No solution");


    @TestData
    public DataExpectation normal1 = DataExpectation.create("0x=0").expect("Infinite solutions");

}
