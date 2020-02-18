package q450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/remove-k-digits/
 *
 * Given a non-negative integer num represented as a string, remove k digits from the number so that the new number is
 * the smallest possible.
 *
 * Note:
 *
 * The length of num is less than 10002 and will be ≥ k.
 * The given num does not contain any leading zero.
 *
 * Example 1:
 *
 * Input: num = "1432219", k = 3
 * Output: "1219"
 * Explanation: Remove the three digits 4, 3, and 2 to form the new number 1219 which is the smallest.
 *
 * Example 2:
 *
 * Input: num = "10200", k = 1
 * Output: "200"
 * Explanation: Remove the leading 1 and the number is 200. Note that the output must not contain leading zeroes.
 *
 * Example 3:
 *
 * Input: num = "10", k = 2
 * Output: "0"
 * Explanation: Remove all the digits from the number and it is left with nothing which is 0.
 */
@RunWith(LeetCodeRunner.class)
public class Q402_RemoveKDigits {

    // 单调递增栈
    @Answer
    public String removeKdigits(String num, int k) {
        StringBuilder sb = new StringBuilder();
        // 哨兵
        sb.append('0');
        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (k > 0 && c < sb.charAt(sb.length() - 1)) {
                k--;
                sb.setLength(sb.length() - 1);
            }
            sb.append(c);
        }

        // 处理末尾是递增的情况
        sb.setLength(sb.length() - k);

        // 处理前面的"0"
        int i = 0;
        while (i < sb.length() - 1 && sb.charAt(i) == '0') {
            i++;
        }
        return sb.substring(i);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("1432219", 3).expect("1219");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("10200", 1).expect("200");

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("10", 2).expect("0");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("9", 1).expect("0");

}
