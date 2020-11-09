package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1318. Minimum Flips to Make a OR b Equal to c
 * https://leetcode.com/problems/minimum-flips-to-make-a-or-b-equal-to-c/
 *
 * Given 3 positives numbers a, b and c. Return the minimum flips required in some bits of a and b to make ( a OR b == c
 * ). (bitwise OR operation).
 *
 * Flip operation consists of change any single bit 1 to 0 or change the bit 0 to 1 in their binary representation.
 *
 * Example 1:
 * <img src="./Q1318_PIC.png">
 * Input: a = 2, b = 6, c = 5
 * Output: 3
 * Explanation: After flips a = 1 , b = 4 , c = 5 such that (a OR b == c)
 *
 * Example 2:
 *
 * Input: a = 4, b = 2, c = 7
 * Output: 1
 *
 * Example 3:
 *
 * Input: a = 1, b = 2, c = 3
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= a <= 10^9
 * 1 <= b <= 10^9
 * 1 <= c <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1318_MinimumFlipsToMakeAOrBEqualToC {

    @Answer
    public int minFlips(int a, int b, int c) {
        int res = 0;
        while (a > 0 || b > 0 || c > 0) {
            if ((c & 1) == 0) {
                res += (a & 1) + (b & 1);
            } else {
                // ab 的第 0 位和, 1或2 -> 0, 0 -> 1
                res += (2 - (a & 1) - (b & 1)) >> 1;
            }
            a >>= 1;
            b >>= 1;
            c >>= 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 6, 5).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(4, 2, 7).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(1, 2, 3).expect(0);

}
