package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/multiply-strings/
 *
 * Given two non-negative integers num1 and num2 represented as strings, return the product of num1 and num2, also
 * represented as a string.
 *
 * Example 1:
 *
 * Input: num1 = "2", num2 = "3"
 * Output: "6"
 * Example 2:
 *
 * Input: num1 = "123", num2 = "456"
 * Output: "56088"
 * Note:
 *
 * The length of both num1 and num2 is < 110.
 * Both num1 and num2 contain only digits 0-9.
 * Both num1 and num2 do not contain any leading zero, except the number 0 itself.
 * You must not use any built-in BigInteger library or convert the inputs to integer directly.
 */
@RunWith(LeetCodeRunner.class)
public class Q043_MultiplyStrings {

    @Answer
    public String multiply(String num1, String num2) {
        final int n1Length = num1.length(), n2Length = num2.length();
        int[] n1 = new int[n1Length];
        for (int i = 0; i < n1Length; i++) {
            n1[i] = num1.charAt(n1Length - 1 - i) - '0';
        }
        int[] n2 = new int[n2Length];
        for (int i = 0; i < n2Length; i++) {
            n2[i] = num2.charAt(n2Length - 1 - i) - '0';
        }

        int[] buffer = new int[n1Length * n2Length + 1];
        for (int i = 0; i < n1Length; i++) {
            int carry = 0, j;
            for (j = 0; j < n2Length; j++) {
                int sum = n1[i] * n2[j] + carry + buffer[i + j];
                carry = sum / 10;
                buffer[i + j] = sum % 10;
            }
            while (carry > 0) {
                buffer[i + j] += carry;
                carry = buffer[i + j] / 10;
                buffer[i + j] %= 10;
                j++;
            }
        }
        int i = buffer.length - 1;
        while (i >= 0 && buffer[i] == 0) {
            i--;
        }
        if (i == -1) {
            return "0";
        }
        StringBuilder sb = new StringBuilder(i + 1);
        while (i >= 0) {
            sb.append(buffer[i--]);
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("2")
            .addArgument("3")
            .expect("6")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("123")
            .addArgument("456")
            .expect("56088")
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("9")
            .addArgument("9")
            .expect("81")
            .build();
}
