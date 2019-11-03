package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/valid-number/
 *
 * Validate if a given string can be interpreted as a decimal number.
 *
 * Some examples:
 * "0" => true
 * " 0.1 " => true
 * "abc" => false
 * "1 a" => false
 * "2e10" => true
 * " -90e3   " => true
 * " 1e" => false
 * "e3" => false
 * " 6e-1" => true
 * " 99e2.5 " => false
 * "53.5e93" => true
 * " --6 " => false
 * "-+3" => false
 * "95a54e53" => false
 *
 * Note: It is intended for the problem statement to be ambiguous. You should gather all requirements up front before
 * implementing one. However, here is a list of characters that can be in a valid decimal number:
 *
 * Numbers 0-9
 * Exponent - "e"
 * Positive/negative sign - "+"/"-"
 * Decimal point - "."
 * Of course, the context of these characters also matters in the input.
 *
 * Update (2015-02-10):
 * The signature of the C++ function had been updated. If you still see your function signature accepts a const char
 * * argument, please click the reload button to reset your code definition.
 */
@RunWith(LeetCodeRunner.class)
public class Q065_ValidNumber {

    /**
     * 相当无聊的题目, 判断是否有效数字的有很多的特例.
     */
    @Answer
    public boolean isNumber(String s) {
        int i = 0;
        while (i < s.length() && s.charAt(i) == ' ') {
            i++;
        }
        int end = s.length() - 1;
        while (end > i && s.charAt(end) == ' ') {
            end--;
        }

        if (isSign(s, i, end)) {
            i++;
        }

        int prev = i;
        i += numberLength(s, i, end);
        if (i <= end && s.charAt(i) == '.') {
            int numberLength = numberLength(s, i + 1, end);
            if (numberLength == 0 && prev == i) {
                return false;
            }
            i += 1 + numberLength;
        } else if (prev == i) {
            return false;
        }

        if (i <= end && s.charAt(i) == 'e') {
            i++;
            if (isSign(s, i, end)) {
                i++;
            }
            int numberLength = numberLength(s, i, end);
            if (numberLength == 0) {
                return false;
            }
            i += numberLength;
        }

        return i == end + 1;
    }

    private boolean isSign(String s, int i, int end) {
        return i <= end && (s.charAt(i) == '+' || s.charAt(i) == '-');
    }

    private int numberLength(String s, int start, int end) {
        int i = start;
        while (i <= end && '0' <= s.charAt(i) && s.charAt(i) <= '9') {
            i++;
        }
        return i - start;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("0").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(" 0.1 ").expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create("abc").expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create("1 a").expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation.create("2e10").expect(true);

    @TestData
    public DataExpectation example6 = DataExpectation.create(" -90e3   ").expect(true);

    @TestData
    public DataExpectation example7 = DataExpectation.create(" 1e").expect(false);

    @TestData
    public DataExpectation example8 = DataExpectation.create("e3").expect(false);

    @TestData
    public DataExpectation example9 = DataExpectation.create(" 6e-1").expect(true);

    @TestData
    public DataExpectation example10 = DataExpectation.create(" 99e2.5 ").expect(false);

    @TestData
    public DataExpectation example11 = DataExpectation.create("53.5e93").expect(true);

    @TestData
    public DataExpectation example12 = DataExpectation.create(" --6 ").expect(false);

    @TestData
    public DataExpectation example13 = DataExpectation.create("-+3").expect(false);

    @TestData
    public DataExpectation example14 = DataExpectation.create("95a54e53").expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("12e12e12").expect(false);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("12e+12").expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(".1").expect(true);

    @TestData
    public DataExpectation normal4 = DataExpectation.create("+.1").expect(true);

    @TestData
    public DataExpectation normal5 = DataExpectation.create("1.").expect(true);

    @TestData
    public DataExpectation normal6 = DataExpectation.create("1.e1").expect(true);

    @TestData
    public DataExpectation normal7 = DataExpectation.create(".e1").expect(false);

    @TestData
    public DataExpectation border1 = DataExpectation.create("").expect(false);

    @TestData
    public DataExpectation border2 = DataExpectation.create(" ").expect(false);

}
