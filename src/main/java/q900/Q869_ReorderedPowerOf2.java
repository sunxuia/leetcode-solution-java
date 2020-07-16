package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 869. Reordered Power of 2
 * https://leetcode.com/problems/reordered-power-of-2/
 *
 * Starting with a positive integer N, we reorder the digits in any order (including the original order) such that the
 * leading digit is not zero.
 *
 * Return true if and only if we can do this in a way such that the resulting number is a power of 2.
 *
 * Example 1:
 *
 * Input: 1
 * Output: true
 *
 * Example 2:
 *
 * Input: 10
 * Output: false
 *
 * Example 3:
 *
 * Input: 16
 * Output: true
 *
 * Example 4:
 *
 * Input: 24
 * Output: false
 *
 * Example 5:
 *
 * Input: 46
 * Output: true
 *
 * Note:
 *
 * 1 <= N <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q869_ReorderedPowerOf2 {

    @Answer
    public boolean reorderedPowerOf2(int N) {
        long expect = sign(N);
        for (int val = 1; val < 10_0000_0000; val <<= 1) {
            if (expect == sign(val)) {
                return true;
            }
        }
        return false;
    }

    private long sign(int val) {
        long res = 0;
        for (int i = 0; val > 0; i++, val /= 10) {
            res += 1L << val % 10 * 4;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(10).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(16).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.create(24).expect(false);

    @TestData
    public DataExpectation example5 = DataExpectation.create(46).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1821).expect(false);

}
