package q1000;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 972. Equal Rational Numbers
 * https://leetcode.com/problems/equal-rational-numbers/
 *
 * Given two strings S and T, each of which represents a non-negative rational number, return True if and only if they
 * represent the same number. The strings may use parentheses to denote the repeating part of the rational number.
 *
 * In general a rational number can be represented using up to three parts: an integer part, a non-repeating part, and a
 * repeating part. The number will be represented in one of the following three ways:
 *
 * <IntegerPart> (e.g. 0, 12, 123)
 * <IntegerPart><.><NonRepeatingPart>  (e.g. 0.5, 1., 2.12, 2.0001)
 * <IntegerPart><.><NonRepeatingPart><(><RepeatingPart><)> (e.g. 0.1(6), 0.9(9), 0.00(1212))
 *
 * The repeating portion of a decimal expansion is conventionally denoted within a pair of round brackets.  For
 * example:
 *
 * 1 / 6 = 0.16666666... = 0.1(6) = 0.1666(6) = 0.166(66)
 *
 * Both 0.1(6) or 0.1666(6) or 0.166(66) are correct representations of 1 / 6.
 *
 * Example 1:
 *
 * Input: S = "0.(52)", T = "0.5(25)"
 * Output: true
 * Explanation:
 * Because "0.(52)" represents 0.52525252..., and "0.5(25)" represents 0.52525252525..... , the strings represent the
 * same number.
 *
 * Example 2:
 *
 * Input: S = "0.1666(6)", T = "0.166(66)"
 * Output: true
 *
 * Example 3:
 *
 * Input: S = "0.9(9)", T = "1."
 * Output: true
 * Explanation:
 * "0.9(9)" represents 0.999999999... repeated forever, which equals 1.  [See this link for an explanation.]
 * "1." represents the number 1, which is formed correctly: (IntegerPart) = "1" and (NonRepeatingPart) = "".
 *
 * Note:
 *
 * Each part consists only of digits.
 * The  will not begin with 2 or more zeros.  (There is no other restriction on the digits of each part.)
 * 1 <= .length <= 4
 * 0 <= .length <= 4
 * 1 <= .length <= 4
 */
@RunWith(LeetCodeRunner.class)
public class Q972_EqualRationalNumbers {

    // LeetCode 上有更快的解法
    @Answer
    public boolean isRationalEqual(String S, String T) {
        Data ds = new Data(S);
        Data dt = new Data(T);
        int end = Math.max(ds.str.length() + ds.repeat.size() * 2,
                dt.str.length() + dt.repeat.size() * 2);
        for (int i = 0; i < end; i++) {
            if (ds.nextChar() != dt.nextChar()) {
                return false;
            }
        }
        return true;
    }

    private static class Data {

        String str;

        final Queue<Character> repeat = new ArrayDeque<>();

        int i = -1;

        Data(String raw) {
            int leftPar = raw.indexOf('(');
            int dot = raw.indexOf('.');
            double nonRepeat;
            if (leftPar == -1) {
                nonRepeat = Double.parseDouble(raw);
            } else {
                nonRepeat = Double.parseDouble(raw.substring(0, leftPar));
                // 9 循环的进位
                String repeatPart = raw.substring(leftPar + 1, raw.length() - 1);
                if (repeatPart.matches("9+")) {
                    double digit = Math.pow(10, leftPar - dot - 1);
                    nonRepeat = (nonRepeat * digit + 1.0) / digit;
                    leftPar = -1;
                }
            }
            if (leftPar == -1) {
                str = String.valueOf(nonRepeat);
                repeat.offer('0');
            } else {
                str = String.format("%." + (leftPar - dot - 1) + "f", nonRepeat);
                if (str.indexOf('.') < 0) {
                    str += '.';
                }
                for (int i = leftPar + 1; i < raw.length() - 1; i++) {
                    repeat.offer(raw.charAt(i));
                }
            }
        }

        char nextChar() {
            i++;
            if (i < str.length()) {
                return str.charAt(i);
            }
            Character c = repeat.poll();
            repeat.offer(c);
            return c;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("0.(52)", "0.5(25)").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("0.1666(6)", "0.166(66)").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("0.9(9)", "1.").expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("1.9(0)", "1.8(9)").expect(true);

}
