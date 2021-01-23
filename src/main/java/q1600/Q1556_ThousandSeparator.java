package q1600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1556. Thousand Separator
 * https://leetcode.com/problems/thousand-separator/
 *
 * Given an integer n, add a dot (".") as the thousands separator and return it in string format.
 *
 * Example 1:
 *
 * Input: n = 987
 * Output: "987"
 *
 * Example 2:
 *
 * Input: n = 1234
 * Output: "1.234"
 *
 * Example 3:
 *
 * Input: n = 123456789
 * Output: "123.456.789"
 *
 * Example 4:
 *
 * Input: n = 0
 * Output: "0"
 *
 * Constraints:
 *
 * 0 <= n < 2^31
 */
@RunWith(LeetCodeRunner.class)
public class Q1556_ThousandSeparator {

    @Answer
    public String thousandSeparator(int n) {
        if (n == 0) {
            return "0";
        }
        StringBuilder sb = new StringBuilder();
        while (n > 0) {
            sb.append(n % 10);
            if (sb.length() % 4 == 3) {
                sb.append('.');
            }
            n /= 10;
        }
        if (sb.length() % 4 == 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.reverse().toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(987).expect("987");

    @TestData
    public DataExpectation example2 = DataExpectation.create(1234).expect("1.234");

    @TestData
    public DataExpectation example3 = DataExpectation.create(123456789).expect("123.456.789");

    @TestData
    public DataExpectation example4 = DataExpectation.create(0).expect("0");

}
