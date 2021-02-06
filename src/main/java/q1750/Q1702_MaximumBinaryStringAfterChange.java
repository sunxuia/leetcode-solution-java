package q1750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1702. Maximum Binary String After Change
 * https://leetcode.com/problems/maximum-binary-string-after-change/
 *
 * You are given a binary string binary consisting of only 0's or 1's. You can apply each of the following operations
 * any number of times:
 *
 * Operation 1: If the number contains the substring "00", you can replace it with "10".
 *
 * For example, "00010" -> "10010"
 *
 *
 * Operation 2: If the number contains the substring "10", you can replace it with "01".
 *
 * For example, "00010" -> "00001"
 *
 *
 *
 * Return the maximum binary string you can obtain after any number of operations. Binary string x is greater than
 * binary string y if x's decimal representation is greater than y's decimal representation.
 *
 * Example 1:
 *
 * Input: binary = "000110"
 * Output: "111011"
 * Explanation: A valid transformation sequence can be:
 * "000110" -> "000101"
 * "000101" -> "100101"
 * "100101" -> "110101"
 * "110101" -> "110011"
 * "110011" -> "111011"
 *
 * Example 2:
 *
 * Input: binary = "01"
 * Output: "01"
 * Explanation: "01" cannot be transformed any further.
 *
 * Constraints:
 *
 * 1 <= binary.length <= 10^5
 * binary consist of '0' and '1'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1702_MaximumBinaryStringAfterChange {

    /**
     * 找规律的题
     *
     * @formatter:off
     * 证明方式参见参考文档
     * https://leetcode-cn.com/problems/maximum-binary-string-after-change/solution/maximum-binary-string-after-change-by-ik-tugt/
     * @formatter:on
     */
    @Answer
    public String maximumBinaryString(String binary) {
        final int n = binary.length();
        char[] cs = binary.toCharArray();
        int one = 0;
        boolean hasZero = false;
        for (char c : cs) {
            if (c == '0') {
                hasZero = true;
            } else if (hasZero) {
                one++;
            }
        }
        if (!hasZero) {
            return binary;
        }
        Arrays.fill(cs, '1');
        cs[n - 1 - one] = '0';
        return new String(cs);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("000110").expect("111011");

    @TestData
    public DataExpectation example2 = DataExpectation.create("01").expect("01");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("01111001100000110010").expect("11111111110111111111");

    @TestData
    public DataExpectation normal2 = DataExpectation.create("01110").expect("10111");

}
