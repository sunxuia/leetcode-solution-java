package q1550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1524. Number of Sub-arrays With Odd Sum
 * https://leetcode.com/problems/number-of-sub-arrays-with-odd-sum/
 *
 * Given an array of integers arr. Return the number of sub-arrays with odd sum.
 *
 * As the answer may grow large, the answer must be computed modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: arr = [1,3,5]
 * Output: 4
 * Explanation: All sub-arrays are [[1],[1,3],[1,3,5],[3],[3,5],[5]]
 * All sub-arrays sum are [1,4,9,3,8,5].
 * Odd sums are [1,9,3,5] so the answer is 4.
 *
 * Example 2:
 *
 * Input: arr = [2,4,6]
 * Output: 0
 * Explanation: All sub-arrays are [[2],[2,4],[2,4,6],[4],[4,6],[6]]
 * All sub-arrays sum are [2,6,12,4,10,6].
 * All sub-arrays have even sum and the answer is 0.
 *
 * Example 3:
 *
 * Input: arr = [1,2,3,4,5,6,7]
 * Output: 16
 *
 * Example 4:
 *
 * Input: arr = [100,100,99,99]
 * Output: 4
 *
 * Example 5:
 *
 * Input: arr = [7]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= arr.length <= 10^5
 * 1 <= arr[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1524_NumberOfSubArraysWithOddSum {

    @Answer
    public int numOfSubarrays(int[] arr) {
        final int mod = 10_0000_0007;
        int res = 0, count = 0, prev1 = 0, prev2 = 0;
        for (int num : arr) {
            count++;
            if (num % 2 == 1) {
                // 奇数则为只包含该位的总数(count), 和包含前前位奇数的和
                int sum = (count + prev2) % mod;
                res = (res + sum) % mod;
                prev2 = prev1;
                prev1 = sum;
                count = 0;
            } else {
                // 偶数则为之前的结果向后扩展1 位
                res = (res + prev1) % mod;
            }
        }
        return res;
    }

    /**
     * leetcode 上最快的解法.
     */
    @Answer
    public int numOfSubarrays2(int[] arr) {
        final int mod = 10_0000_0007;
        int odd = 0, even = 1, parity = 0;
        int res = 0;
        for (int num : arr) {
            parity ^= num & 1;
            if (parity == 1) {
                // 奇偶变换
                res = (res + even) % mod;
                odd++;
            } else {
                res = (res + odd) % mod;
                even++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{1}).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{1, 2}).expect(2);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new int[]{1, 2, 3}).expect(4);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(6);

    @TestData
    public DataExpectation normal5 = DataExpectation.create(new int[]{1, 2, 3, 4, 5}).expect(9);

    @TestData
    public DataExpectation normal6 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6}).expect(12);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{1, 2, 3, 4, 5, 6, 7}).expect(16);

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 3, 5}).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{2, 4, 6}).expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[]{100, 100, 99, 99}).expect(4);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[]{7}).expect(1);

}
