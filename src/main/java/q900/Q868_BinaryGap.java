package q900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 868. Binary Gap
 * https://leetcode.com/problems/binary-gap/
 *
 * Given a positive integer N, find and return the longest distance between two consecutive 1's in the binary
 * representation of N.
 *
 * If there aren't two consecutive 1's, return 0.
 *
 * Example 1:
 *
 * Input: 22
 * Output: 2
 * Explanation:
 * 22 in binary is 0b10110.
 * In the binary representation of 22, there are three ones, and two consecutive pairs of 1's.
 * The first consecutive pair of 1's have distance 2.
 * The second consecutive pair of 1's have distance 1.
 * The answer is the largest of these two distances, which is 2.
 *
 * Example 2:
 *
 * Input: 5
 * Output: 2
 * Explanation:
 * 5 in binary is 0b101.
 *
 * Example 3:
 *
 * Input: 6
 * Output: 1
 * Explanation:
 * 6 in binary is 0b110.
 *
 * Example 4:
 *
 * Input: 8
 * Output: 0
 * Explanation:
 * 8 in binary is 0b1000.
 * There aren't any consecutive pairs of 1's in the binary representation of 8, so we return 0.
 *
 * Note:
 *
 * 1 <= N <= 10^9
 *
 * 题解: 就是要求二进制表示的N 中, 两个1 之间的连续0 的数量+1
 */
@RunWith(LeetCodeRunner.class)
public class Q868_BinaryGap {

    @Answer
    public int binaryGap(int N) {
        while (N > 0 && (N & 1) == 0) {
            N >>= 1;
        }
        N >>= 1;
        int res = 0, dist = 1;
        while (N > 0) {
            if ((N & 1) == 1) {
                res = Math.max(res, dist);
                dist = 1;
            } else {
                dist++;
            }
            N >>= 1;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(22).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(5).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(6).expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation.create(8).expect(0);

}
