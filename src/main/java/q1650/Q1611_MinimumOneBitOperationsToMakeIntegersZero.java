package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1611. Minimum One Bit Operations to Make Integers Zero
 * https://leetcode.com/problems/minimum-one-bit-operations-to-make-integers-zero/
 *
 * Given an integer n, you must transform it into 0 using the following operations any number of times:
 *
 * Change the rightmost (0th) bit in the binary representation of n.
 * Change the ith bit in the binary representation of n if the (i-1)th bit is set to 1 and the (i-2)th through 0th bits
 * are set to 0.
 *
 * Return the minimum number of operations to transform n into 0.
 *
 * Example 1:
 *
 * Input: n = 0
 * Output: 0
 *
 * Example 2:
 *
 * Input: n = 3
 * Output: 2
 * Explanation: The binary representation of 3 is "11".
 * "11" -> "01" with the 2nd operation since the 0th bit is 1.
 * "01" -> "00" with the 1st operation.
 *
 * Example 3:
 *
 * Input: n = 6
 * Output: 4
 * Explanation: The binary representation of 6 is "110".
 * "110" -> "010" with the 2nd operation since the 1st bit is 1 and 0th through 0th bits are 0.
 * "010" -> "011" with the 1st operation.
 * "011" -> "001" with the 2nd operation since the 0th bit is 1.
 * "001" -> "000" with the 1st operation.
 *
 * Example 4:
 *
 * Input: n = 9
 * Output: 14
 *
 * Example 5:
 *
 * Input: n = 333
 * Output: 393
 *
 * Constraints:
 *
 * 0 <= n <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1611_MinimumOneBitOperationsToMakeIntegersZero {

    /**
     * 这个就是格雷码的变形题.
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/minimum-one-bit-operations-to-make-integers-zero/solution/hua-shan-yi-tiao-dao-wo-men-di-gui-zhao-zou-by-luc/
     * @formatter:on
     */
    @Answer
    public int minimumOneBitOperations(int n) {
        int res = 0;
        while (n > 0) {
            res ^= n;
            n >>= 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(0).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(6).expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create(9).expect(14);

    @TestData
    public DataExpectation example5 = DataExpectation.create(333).expect(393);

}
