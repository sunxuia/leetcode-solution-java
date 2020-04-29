package q550;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/complex-number-multiplication/
 *
 * Given two strings representing two complex numbers.
 *
 * You need to return a string representing their multiplication. Note i^2 = -1 according to the definition.
 *
 * Example 1:
 *
 * Input: "1+1i", "1+1i"
 * Output: "0+2i"
 * Explanation: (1 + i) * (1 + i) = 1 + i2 + 2 * i = 2i, and you need convert it to the form of 0+2i.
 *
 * Example 2:
 *
 * Input: "1+-1i", "1+-1i"
 * Output: "0+-2i"
 * Explanation: (1 - i) * (1 - i) = 1 + i2 - 2 * i = -2i, and you need convert it to the form of 0+-2i.
 *
 * Note:
 *
 * The input strings will not have extra blank.
 * The input strings will be given in the form of a+bi, where the integer a and b will both belong to the range
 * of [-100, 100]. And the output should be also in this form.
 */
@RunWith(LeetCodeRunner.class)
public class Q537_ComplexNumberMultiplication {

    // 不用正则表达式会快点
    @Answer
    public String complexNumberMultiply(String a, String b) {
        Matcher ma = COMPLEX_PATTERN.matcher(a);
        ma.find();
        int realA = Integer.parseInt(ma.group(1));
        int imaginaryA = Integer.parseInt(ma.group(2));
        Matcher mb = COMPLEX_PATTERN.matcher(b);
        mb.find();
        int realB = Integer.parseInt(mb.group(1));
        int imaginaryB = Integer.parseInt(mb.group(2));

        int realRes = realA * realB - imaginaryA * imaginaryB;
        int imaginaryRes = realA * imaginaryB + realB * imaginaryA;
        return realRes + "+" + imaginaryRes + "i";
    }

    private static final Pattern COMPLEX_PATTERN = Pattern.compile("^(-?\\d+)\\+(-?\\d+)i");

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("1+1i", "1+1i").expect("0+2i");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("1+-1i", "1+-1i").expect("0+-2i");

}
