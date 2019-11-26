package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/power-of-two/
 *
 * Given an integer, write a function to determine if it is a power of two.
 *
 * Example 1:
 *
 * Input: 1
 * Output: true
 * Explanation: 2^0 = 1
 *
 * Example 2:
 *
 * Input: 16
 * Output: true
 * Explanation: 2^4 = 16
 *
 * Example 3:
 *
 * Input: 218
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q231_PowerOfTwo {

    @Answer
    public boolean isPowerOfTwo(int n) {
        int count = 0;
        while (n > 0 && count < 2) {
            count += n & 1;
            n /= 2;
        }
        return count == 1;
    }

    // LeetCode 上最好的解法
    @Answer
    public boolean leetCode(int n) {
        return n > 0 && (n & (n - 1)) == 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(16).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.create(218).expect(false);

}
