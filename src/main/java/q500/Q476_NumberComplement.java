package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-complement/
 *
 * Given a positive integer, output its complement number. The complement strategy is to flip the bits of its binary
 * representation.
 *
 *
 *
 * Example 1:
 *
 * Input: 5
 * Output: 2
 * Explanation: The binary representation of 5 is 101 (no leading zero bits), and its complement is 010. So you need
 * to output 2.
 *
 *
 *
 * Example 2:
 *
 * Input: 1
 * Output: 0
 * Explanation: The binary representation of 1 is 1 (no leading zero bits), and its complement is 0. So you need to
 * output 0.
 *
 *
 *
 * Note:
 *
 * The given integer is guaranteed to fit within the range of a 32-bit signed integer.
 * You could assume no leading zero bit in the integer’s binary representation.
 * This question is the same as 1009: https://leetcode.com/problems/complement-of-base-10-integer/
 */
@RunWith(LeetCodeRunner.class)
public class Q476_NumberComplement {

    @Answer
    public int findComplement(int num) {
        int i = 31;
        while (num >> i == 0) {
            i--;
        }
        while (i >= 0) {
            num ^= 1 << i;
            i--;
        }
        return num;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(5).expect(2);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(1).expect(0);

}
