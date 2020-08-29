package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1009. Complement of Base 10 Integer
 * https://leetcode.com/problems/complement-of-base-10-integer/
 *
 * Every non-negative integer N has a binary representation.  For example, 5 can be represented as "101" in binary, 11
 * as "1011" in binary, and so on.  Note that except for N = 0, there are no leading zeroes in any binary
 * representation.
 *
 * The complement of a binary representation is the number in binary you get when changing every 1 to a 0 and 0 to a 1.
 * For example, the complement of "101" in binary is "010" in binary.
 *
 * For a given number N in base-10, return the complement of it's binary representation as a base-10 integer.
 *
 * Example 1:
 *
 * Input: 5
 * Output: 2
 * Explanation: 5 is "101" in binary, with complement "010" in binary, which is 2 in base-10.
 *
 * Example 2:
 *
 * Input: 7
 * Output: 0
 * Explanation: 7 is "111" in binary, with complement "000" in binary, which is 0 in base-10.
 *
 * Example 3:
 *
 * Input: 10
 * Output: 5
 * Explanation: 10 is "1010" in binary, with complement "0101" in binary, which is 5 in base-10.
 *
 * Note:
 *
 * 0 <= N < 10^9
 * This question is the same as 476: https://leetcode.com/problems/number-complement/
 */
@RunWith(LeetCodeRunner.class)
public class Q1009_ComplementOfBase10Integer {

    @Answer
    public int bitwiseComplement(int N) {
        if (N == 0) {
            return 1;
        }
        int res = 0;
        for (int i = 0; N > 0; N >>= 1, i++) {
            res |= (N & 1 ^ 1) << i;
        }
        return res;
    }

    /**
     * LeetCode 中最快的解法
     */
    @Answer
    public int bitwiseComplement2(int N) {
        int x = 1;
        while (N > x) {
            x = x * 2 + 1;
        }
        return x - N;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(7).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(10).expect(5);

}
