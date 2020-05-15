package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/binary-number-with-alternating-bits/
 *
 * Given a positive integer, check whether it has alternating bits: namely, if two adjacent bits will always have
 * different values.
 *
 * Example 1:
 *
 * Input: 5
 * Output: True
 * Explanation:
 * The binary representation of 5 is: 101
 *
 * Example 2:
 *
 * Input: 7
 * Output: False
 * Explanation:
 * The binary representation of 7 is: 111.
 *
 * Example 3:
 *
 * Input: 11
 * Output: False
 * Explanation:
 * The binary representation of 11 is: 1011.
 *
 * Example 4:
 *
 * Input: 10
 * Output: True
 * Explanation:
 * The binary representation of 10 is: 1010.
 */
@RunWith(LeetCodeRunner.class)
public class Q693_BinaryNumberWithAlternatingBits {

    @Answer
    public boolean hasAlternatingBits(int n) {
        int prev = n & 1;
        n >>= 1;
        for (int i = 1; i < 32 && n > 0; i++) {
            if ((n & 1 ^ prev) == 0) {
                return false;
            }
            prev = n & 1;
            n >>= 1;
        }
        return true;
    }

    @TestData
    public DataExpectation exmaple1 = DataExpectation.create(5).expect(true);

    @TestData
    public DataExpectation exmaple2 = DataExpectation.create(7).expect(false);

    @TestData
    public DataExpectation exmaple3 = DataExpectation.create(11).expect(false);

    @TestData
    public DataExpectation exmaple4 = DataExpectation.create(10).expect(true);

}
