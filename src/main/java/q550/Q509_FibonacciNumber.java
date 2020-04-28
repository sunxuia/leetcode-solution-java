package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/fibonacci-number/
 *
 * The Fibonacci numbers, commonly denoted F(n) form a sequence, called the Fibonacci sequence, such that each number
 * is the sum of the two preceding ones, starting from 0 and 1. That is,
 *
 * F(0) = 0,   F(1) = 1
 * F(N) = F(N - 1) + F(N - 2), for N > 1.
 *
 * Given N, calculate F(N).
 *
 *
 *
 * Example 1:
 *
 * Input: 2
 * Output: 1
 * Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
 *
 * Example 2:
 *
 * Input: 3
 * Output: 2
 * Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.
 *
 * Example 3:
 *
 * Input: 4
 * Output: 3
 * Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.
 *
 *
 *
 * Note:
 *
 * 0 ≤ N ≤ 30.
 */
@RunWith(LeetCodeRunner.class)
public class Q509_FibonacciNumber {

    @Answer
    public int fib(int N) {
        if (N < 2) {
            return N;
        }
        return fib(N - 1) + fib(N - 2);
    }

    @Answer
    public int fib2(int N) {
        int curr = 0, prev = 1;
        while (N-- > 0) {
            int p = curr;
            curr += prev;
            prev = p;
        }
        return curr;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(2).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(3).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create(4).expect(3);

    @TestData
    public DataExpectation border0 = DataExpectation.create(0).expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.create(1).expect(1);

}
