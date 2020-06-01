package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/prime-number-of-set-bits-in-binary-representation/
 *
 * Given two integers L and R, find the count of numbers in the range [L, R] (inclusive) having a prime number of
 * set bits in their binary representation.
 *
 * (Recall that the number of set bits an integer has is the number of 1s present when written in binary. For
 * example, 21 written in binary is 10101 which has 3 set bits. Also, 1 is not a prime.)
 *
 * Example 1:
 *
 * Input: L = 6, R = 10
 * Output: 4
 * Explanation:
 * 6 -> 110 (2 set bits, 2 is prime)
 * 7 -> 111 (3 set bits, 3 is prime)
 * 9 -> 1001 (2 set bits , 2 is prime)
 * 10->1010 (2 set bits , 2 is prime)
 *
 * Example 2:
 *
 * Input: L = 10, R = 15
 * Output: 5
 * Explanation:
 * 10 -> 1010 (2 set bits, 2 is prime)
 * 11 -> 1011 (3 set bits, 3 is prime)
 * 12 -> 1100 (2 set bits, 2 is prime)
 * 13 -> 1101 (3 set bits, 3 is prime)
 * 14 -> 1110 (3 set bits, 3 is prime)
 * 15 -> 1111 (4 set bits, 4 is not prime)
 *
 * Note:
 *
 * 1. L, R will be integers L <= R in the range [1, 10^6].
 * 2. R - L will be at most 10000.
 */
@RunWith(LeetCodeRunner.class)
public class Q762_PrimeNumberOfSetBitsInBinaryRepresentation {

    /**
     * 要求二进制的1 的数目是质数, [1, 10^6] 中可能的质数数量只有 2, 3, 5, 7, 11, 13, 17, 19 这几种.
     */
    @Answer
    public int countPrimeSetBits(int L, int R) {
        int res = 0;
        for (int i = L; i <= R; i++) {
            int count = count(i);
            if (count == 2
                    || count == 3
                    || count == 5
                    || count == 7
                    || count == 11
                    || count == 13
                    || count == 17
                    || count == 19) {
                res++;
            }
        }
        return res;
    }

    private int count(int num) {
        int res = 0;
        while (num > 0) {
            res += num & 1;
            num >>= 1;
        }
        return res;
    }

    /**
     * LeetCode 中最快的解答.
     */
    @Answer
    public int countPrimeSetBits2(int L, int R) {
        int n = L;
        int ones = 0;
        while (n != 0) {
            n = n & (n - 1);
            ones++;
        }
        int count = ((P >>> ones) & 1);
        for (int i = L + 1; i <= R; i++) {
            n = i;
            ones++;
            while ((n & 1) == 0) {
                ones--;
                n >>>= 1;
            }
            count += ((P >>> ones) & 1);
        }
        return count;
    }

    private static final int P = 665772;

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(6, 10).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(10, 15).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(248657, 257888).expect(3381);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(977581, 983119).expect(2036);

}
