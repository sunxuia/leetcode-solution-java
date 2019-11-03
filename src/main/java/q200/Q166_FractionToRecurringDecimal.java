package q200;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.UsingTestData;

/**
 * https://leetcode.com/problems/fraction-to-recurring-decimal/
 *
 * Given two integers representing the numerator and denominator of a fraction, return the fraction in string format.
 *
 * If the fractional part is repeating, enclose the repeating part in parentheses.
 *
 * Example 1:
 *
 * Input: numerator = 1, denominator = 2
 * Output: "0.5"
 *
 * Example 2:
 *
 * Input: numerator = 2, denominator = 1
 * Output: "2"
 *
 * Example 3:
 *
 * Input: numerator = 2, denominator = 3
 * Output: "0.(6)"
 */
@RunWith(LeetCodeRunner.class)
public class Q166_FractionToRecurringDecimal {

    @UsingTestData()
    @Answer
    public String fractionToDecimal(int numerator, int denominator) {
        StringBuilder sb = new StringBuilder();
        if ((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0)) {
            sb.append('-');
        }
        long left = Math.abs((long) numerator);
        long right = Math.abs((long) denominator);
        sb.append(left / right);
        left = left % right;
        if (left == 0) {
            return sb.toString();
        }
        sb.append('.');
        int cycleIndex = sb.length();
        Map<Long, Integer> complements = new HashMap<>();
        complements.put(left, cycleIndex);
        left *= 10;
        while (true) {
            long quotient = left / right;
            long complement = left % right;
            sb.append(quotient);
            if (complement == 0) {
                return sb.toString();
            }
            if (complements.containsKey(complement)) {
                int index = complements.get(complement);
                sb.insert(index, '(').append(')');
                return sb.toString();
            }
            complements.put(complement, ++cycleIndex);
            left = complement * 10;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 2).expect("0.5");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 1).expect("2");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(2, 3).expect("0.(6)");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(-5, 3).expect("-1.(6)");

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(2, 7).expect("0.(285714)");

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(1, 17).expect("0.(0588235294117647)");

    @TestData
    public DataExpectation normal4 = DataExpectation.createWith(1, 6).expect("0.1(6)");

    @TestData
    public DataExpectation normal5 = DataExpectation.createWith(-1, Integer.MIN_VALUE)
            .expect("0.0000000004656612873077392578125");

    @TestData
    public DataExpectation normal6 = DataExpectation.createWith(22, 7).expect("3.(142857)");

}
