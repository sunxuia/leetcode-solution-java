package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/add-binary/
 *
 * Given two binary strings, return their sum (also a binary string).
 *
 * The input strings are both non-empty and contains only characters 1 or 0.
 *
 * Example 1:
 *
 * Input: a = "11", b = "1"
 * Output: "100"
 * Example 2:
 *
 * Input: a = "1010", b = "1011"
 * Output: "10101"
 */
@RunWith(LeetCodeRunner.class)
public class Q067_AddBinary {

    @Answer
    public String addBinary(String a, String b) {
        int carry = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = a.length() - 1, j = b.length() - 1; i >= 0 || j >= 0; i--, j--) {
            int v = carry;
            if (i >= 0 && a.charAt(i) == '1') {
                v++;
            }
            if (j >= 0 && b.charAt(j) == '1') {
                v++;
            }
            carry = v / 2;
            v = v % 2;
            sb.append(v == 1 ? '1' : '0');
        }
        if (carry == 1) {
            sb.append('1');
        }
        sb.reverse();
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("11", "1").expect("100");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("1010", "1011").expect("10101");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("11", "11").expect("110");

}
