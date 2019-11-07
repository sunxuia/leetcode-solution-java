package q200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/reverse-bits/
 *
 * Reverse bits of a given 32 bits unsigned integer.
 *
 *
 *
 * Example 1:
 *
 * Input: 00000010100101000001111010011100
 * Output: 00111001011110000010100101000000
 * Explanation: The input binary string 00000010100101000001111010011100 represents the unsigned integer 43261596, so
 * return 964176192 which its binary representation is 00111001011110000010100101000000.
 *
 * Example 2:
 *
 * Input: 11111111111111111111111111111101
 * Output: 10111111111111111111111111111111
 * Explanation: The input binary string 11111111111111111111111111111101 represents the unsigned integer 4294967293,
 * so return 3221225471 which its binary representation is 10111111111111111111111111111111.
 *
 *
 *
 * Note:
 *
 * Note that in some languages such as Java, there is no unsigned integer type. In this case, both input and
 * output will be given as signed integer type and should not affect your implementation, as the internal binary
 * representation of the integer is the same whether it is signed or unsigned.
 * In Java, the compiler represents the signed integers using 2's complement notation. Therefore, in Example 2
 * above the input represents the signed integer -3 and the output represents the signed integer -1073741825.
 */
@RunWith(LeetCodeRunner.class)
public class Q190_ReverseBits {

    /**
     * 对Java 来说, 32 位int 的>> 和 << 位移, 等于 *2 或者/2 的运算, 同样会溢出; >>> 操作会连带标志位一起移动, 且不循环移位.
     */
    @Answer
    public int reverseBits(int n) {
        int res = 0;
        for (int i = 0; i < 32; i++) {
            res = (res * 2) + ((n >>> i) & 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(0b00000010100101000001111010011100)
            .expect(0b00111001011110000010100101000000);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(0b11111111111111111111111111111101)
            .expect(0b10111111111111111111111111111111);

}
