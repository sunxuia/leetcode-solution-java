package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1175. Prime Arrangements
 * https://leetcode.com/problems/prime-arrangements/
 *
 * Return the number of permutations of 1 to n so that prime numbers are at prime indices (1-indexed.)
 *
 * (Recall that an integer is prime if and only if it is greater than 1, and cannot be written as a product of two
 * positive integers both smaller than it.)
 *
 * Since the answer may be large, return the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 5
 * Output: 12
 * Explanation: For example [1,2,5,4,3] is a valid permutation, but [5,2,3,4,1] is not because the prime number 5 is at
 * index 1.
 *
 * Example 2:
 *
 * Input: n = 100
 * Output: 682289015
 *
 * Constraints:
 *
 * 1 <= n <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1175_PrimeArrangements {

    @Answer
    public int numPrimeArrangements(int n) {
        final int mod = 10_0000_0007;
        int count = 0;
        for (int i = 2; i <= n; i++) {
            if (isPrime(i)) {
                count++;
            }
        }

        long res = 1;
        for (int i = count; i > 0; i--) {
            res = res * i % mod;
        }
        for (int i = n - count; i > 0; i--) {
            res = res * i % mod;
        }
        return (int) res;
    }

    private boolean isPrime(int val) {
        for (int i = 2; i * i <= val; i++) {
            if (val % i == 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(5).expect(12);

    @TestData
    public DataExpectation example2 = DataExpectation.create(100).expect(682289015);

}
