package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1015. Smallest Integer Divisible by K
 * https://leetcode.com/problems/smallest-integer-divisible-by-k/
 *
 * Given a positive integer K, you need find the smallest positive integer N such that N is divisible by K, and N only
 * contains the digit 1.
 *
 * Return the length of N.  If there is no such N, return -1.
 *
 * Example 1:
 *
 * Input: 1
 * Output: 1
 * Explanation: The smallest answer is N = 1, which has length 1.
 *
 * Example 2:
 *
 * Input: 2
 * Output: -1
 * Explanation: There is no such positive integer N divisible by 2.
 *
 * Example 3:
 *
 * Input: 3
 * Output: 3
 * Explanation: The smallest answer is N = 111, which has length 3.
 *
 * Note:
 *
 * 1 <= K <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1015_SmallestIntegerDivisibleByK {

    /**
     * 参考文档 https://www.cnblogs.com/zywscq/p/10699120.html
     */
    @Answer
    public int smallestRepunitDivByK(int K) {
        if (K % 2 == 0 || K % 5 == 0) {
            return -1;
        }
        int num = 0;
        for (int i = 1; i <= K + 1; i++) {
            num = (num * 10 + 1) % K;
            if (num == 0) {
                return i;
            }
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(3).expect(3);

}
