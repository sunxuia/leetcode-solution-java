package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/add-strings/
 *
 * Given two non-negative integers num1 and num2 represented as string, return the sum of num1 and num2.
 *
 * Note:
 *
 * The length of both num1 and num2 is < 5100.
 * Both num1 and num2 contains only digits 0-9.
 * Both num1 and num2 does not contain any leading zero.
 * You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
@RunWith(LeetCodeRunner.class)
public class Q415_AddStrings {

    @Answer
    public String addStrings(String num1, String num2) {
        StringBuilder sb = new StringBuilder();
        int i = num1.length() - 1, j = num2.length() - 1, carry = 0;
        while (i >= 0 || j >= 0) {
            int d1 = i < 0 ? 0 : num1.charAt(i) - '0';
            int d2 = j < 0 ? 0 : num2.charAt(j) - '0';
            int sum = d1 + d2 + carry;
            sb.append(sum % 10);
            carry = sum / 10;
            i--;
            j--;
        }
        if (carry > 0) {
            sb.append(carry);
        }
        return sb.reverse().toString();
    }

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("1234", "999").expect("2233");

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("1234", "0").expect("1234");

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith("9", "1").expect("10");

}
