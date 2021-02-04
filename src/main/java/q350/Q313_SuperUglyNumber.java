package q350;

import org.junit.runner.RunWith;
import q300.Q264_UglyNumberII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/super-ugly-number/
 *
 * Write a program to find the nth super ugly number.
 *
 * Super ugly numbers are positive numbers whose all prime factors are in the given prime list primes of size k.
 *
 * Example:
 *
 * Input: n = 12, primes = [2,7,13,19]
 * Output: 32
 * Explanation: [1,2,4,7,8,13,14,16,19,26,28,32] is the sequence of the first 12
 * super ugly numbers given primes = [2,7,13,19] of size 4.
 *
 * Note:
 *
 * 1 is a super ugly number for any given primes.
 * The given numbers in primes are in ascending order.
 * 0 < k ≤ 100, 0 < n ≤ 10^6, 0 < primes[i] < 1000.
 * The nth super ugly number is guaranteed to fit in a 32-bit signed integer.
 *
 * 相关题目 {@link Q264_UglyNumberII}
 */
@RunWith(LeetCodeRunner.class)
public class Q313_SuperUglyNumber {

    @Answer
    public int nthSuperUglyNumber(int n, int[] primes) {
        int[] nums = new int[n];
        nums[0] = 1;
        int next = 1;
        int[] index = new int[primes.length];
        int[] toAdd = new int[primes.length];
        int nextToAdd = -1;
        while (next < n) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < primes.length; i++) {
                int val = nums[index[i]] * primes[i];
                if (min > val) {
                    min = val;
                    toAdd[0] = i;
                    nextToAdd = 1;
                } else if (min == val) {
                    toAdd[nextToAdd++] = i;
                }
            }
            nums[next++] = min;
            while (--nextToAdd >= 0) {
                index[toAdd[nextToAdd]]++;
            }
        }
        return nums[n - 1];
    }

    @TestData
    public DataExpectation exmaple = DataExpectation.createWith(12, new int[]{2, 7, 13, 19}).expect(32);

    @TestData
    public DataExpectation border = DataExpectation.createWith(1, new int[]{2}).expect(1);

}
