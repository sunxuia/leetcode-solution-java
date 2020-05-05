package q600;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/fraction-addition-and-subtraction/
 *
 * Given a string representing an expression of fraction addition and subtraction, you need to return the calculation
 * result in string format. The final result should be irreducible fraction. If your final result is an integer, say
 * 2, you need to change it to the format of fraction that has denominator 1. So in this case, 2 should be converted
 * to 2/1.
 *
 * Example 1:
 *
 * Input:"-1/2+1/2"
 * Output: "0/1"
 *
 * Example 2:
 *
 * Input:"-1/2+1/2+1/3"
 * Output: "1/3"
 *
 * Example 3:
 *
 * Input:"1/3-1/2"
 * Output: "-1/6"
 *
 * Example 4:
 *
 * Input:"5/3+1/3"
 * Output: "2/1"
 *
 * Note:
 *
 * 1. The input string only contains '0' to '9', '/', '+' and '-'. So does the output.
 * 2. Each fraction (input and output) has format ±numerator/denominator. If the first input fraction or the output
 * is positive, then '+' will be omitted.
 * 3. The input only contains valid irreducible fractions, where the numerator and denominator of each fraction will
 * always be in the range [1,10]. If the denominator is 1, it means this fraction is actually an integer in a
 * fraction format defined above.
 * 4. The number of given fractions will be in the range [1,10].
 * 5. The numerator and denominator of the final result are guaranteed to be valid and in the range of 32-bit int.
 */
@RunWith(LeetCodeRunner.class)
public class Q592_FractionAdditionAndSubtraction {

    @Answer
    public String fractionAddition(String expression) {
        int num = 1, deno = 1;
        Matcher matcher = PATTERN.matcher(expression);
        if (matcher.find()) {
            num = Integer.parseInt(matcher.group(1));
            deno = Integer.parseInt(matcher.group(2));
        }
        while (matcher.find()) {
            int currNum = Integer.parseInt(matcher.group(1));
            int currDeno = Integer.parseInt(matcher.group(2));
            num = num * currDeno + currNum * deno;
            deno = deno * currDeno;
            int gcd = absGcd(num, deno);
            num /= gcd;
            deno /= gcd;
        }
        return num + "/" + deno;
    }

    private static final Pattern PATTERN = Pattern.compile("(-?\\d+)/(\\d+)");

    // 辗转相除法求最大公约数的绝对值
    private int absGcd(int a, int b) {
        while (b != 0) {
            int t = b;
            b = a % b;
            a = t;
        }
        return Math.abs(a);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("-1/2+1/2").expect("0/1");

    @TestData
    public DataExpectation example2 = DataExpectation.create("-1/2+1/2+1/3").expect("1/3");

    @TestData
    public DataExpectation example3 = DataExpectation.create("1/3-1/2").expect("-1/6");

    @TestData
    public DataExpectation example4 = DataExpectation.create("5/3+1/3").expect("2/1");

}
