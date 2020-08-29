package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1006. Clumsy Factorial
 * https://leetcode.com/problems/clumsy-factorial/
 *
 * Normally, the factorial of a positive integer n is the product of all positive integers less than or equal to n.  For
 * example, factorial(10) = 10 * 9 * 8 * 7 * 6 * 5 * 4 * 3 * 2 * 1.
 *
 * We instead make a clumsy factorial: using the integers in decreasing order, we swap out the multiply operations for a
 * fixed rotation of operations: multiply (*), divide (/), add (+) and subtract (-) in this order.
 *
 * For example, clumsy(10) = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1.  However, these operations are still applied using
 * the usual order of operations of arithmetic: we do all multiplication and division steps before any addition or
 * subtraction steps, and multiplication and division steps are processed left to right.
 *
 * Additionally, the division that we use is floor division such that 10 * 9 / 8 equals 11.  This guarantees the result
 * is an integer.
 *
 * Implement the clumsy function as defined above: given an integer N, it returns the clumsy factorial of N.
 *
 * Example 1:
 *
 * Input: 4
 * Output: 7
 * Explanation: 7 = 4 * 3 / 2 + 1
 *
 * Example 2:
 *
 * Input: 10
 * Output: 12
 * Explanation: 12 = 10 * 9 / 8 + 7 - 6 * 5 / 4 + 3 - 2 * 1
 *
 * Note:
 *
 * 1 <= N <= 10000
 * -2^31 <= answer <= 2^31 - 1  (The answer is guaranteed to fit within a 32-bit integer.)
 */
@RunWith(LeetCodeRunner.class)
public class Q1006_ClumsyFactorial {

    @Answer
    public int clumsy(int N) {
        int res = N;
        if (N > 2) {
            res *= N - 1;
        }
        if (N > 3) {
            res /= N - 2;
        }
        res *= 2;
        while (N > 0) {
            int val = N;
            if (--N > 0) {
                val *= N;
            }
            if (--N > 0) {
                val /= N;
            }
            res -= val;
            if (--N > 0) {
                res += N;
            }
            N--;
        }
        return res;
    }

    /**
     * LeetCode 上的一种解法, 利用了这题的特殊规律
     */
    @Answer
    public int clumsy2(int N) {
        switch (N) {
            case 1:
                return 1;
            case 2:
                return 2;
            case 3:
                return 6;
            case 4:
                return 7;
            default:
        }
        switch ((N - 4) % 4) {
            case 0:
                return N + 1;
            case 1:
            case 2:
                return N + 2;
            default:
                return N - 1;
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(4).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create(10).expect(12);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1).expect(1);

}
