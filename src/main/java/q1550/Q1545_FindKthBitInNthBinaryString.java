package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1545. Find Kth Bit in Nth Binary String
 * https://leetcode.com/problems/find-kth-bit-in-nth-binary-string/
 *
 * Given two positive integers n and k, the binary string  Sn is formed as follows:
 *
 * S1 = "0"
 * Si = Si-1 + "1" + reverse(invert(Si-1)) for i > 1
 *
 * Where + denotes the concatenation operation, reverse(x) returns the reversed string x, and invert(x) inverts all the
 * bits in x (0 changes to 1 and 1 changes to 0).
 *
 * For example, the first 4 strings in the above sequence are:
 *
 * S1 = "0"
 * S2 = "011"
 * S3 = "0111001"
 * S4 = "011100110110001"
 *
 * Return the kth bit in Sn. It is guaranteed that k is valid for the given n.
 *
 * Example 1:
 *
 * Input: n = 3, k = 1
 * Output: "0"
 * Explanation: S3 is "0111001". The first bit is "0".
 *
 * Example 2:
 *
 * Input: n = 4, k = 11
 * Output: "1"
 * Explanation: S4 is "011100110110001". The 11th bit is "1".
 *
 * Example 3:
 *
 * Input: n = 1, k = 1
 * Output: "0"
 *
 * Example 4:
 *
 * Input: n = 2, k = 3
 * Output: "1"
 *
 * Constraints:
 *
 * 1 <= n <= 20
 * 1 <= k <= 2^n - 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1545_FindKthBitInNthBinaryString {

    @Answer
    public char findKthBit(int n, int k) {
        boolean reverse = false;
        for (; n > 1; n--) {
            //  S(n-1)    1    RIS(n-1)
            // len(n-1) half   len(n-1)
            int half = 1 << n - 1;
            if (k == half) {
                // 中间的 1
                return reverse ? '0' : '1';
            } else if (k > half) {
                reverse = !reverse;
                k = (1 << n) - k;
            }
        }
        // 最终的 0
        return reverse ? '1' : '0';
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(3, 1).expect('0');

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 11).expect('1');

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(1, 1).expect('0');

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(2, 3).expect('1');

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, 7).expect('1');

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(4, 12).expect('0');

}
