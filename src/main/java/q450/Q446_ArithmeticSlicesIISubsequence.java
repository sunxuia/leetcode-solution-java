package q450;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/arithmetic-slices-ii-subsequence/
 *
 * A sequence of numbers is called arithmetic if it consists of at least three elements and if the difference between
 * any two consecutive elements is the same.
 *
 * For example, these are arithmetic sequences:
 *
 * 1, 3, 5, 7, 9
 * 7, 7, 7, 7
 * 3, -1, -5, -9
 *
 * The following sequence is not arithmetic.
 *
 * 1, 1, 2, 5, 7
 *
 *
 *
 * A zero-indexed array A consisting of N numbers is given. A subsequence slice of that array is any sequence of
 * integers (P0, P1, ..., Pk) such that 0 ≤ P0 < P1 < ... < Pk < N.
 *
 * A subsequence slice (P0, P1, ..., Pk) of array A is called arithmetic if the sequence A[P0], A[P1], ..., A[Pk-1],
 * A[Pk] is arithmetic. In particular, this means that k ≥ 2.
 *
 * The function should return the number of arithmetic subsequence slices in the array A.
 *
 * The input contains N integers. Every integer is in the range of -2^31 and 2^31 - 1 and 0 ≤ N ≤ 1000. The output is
 * guaranteed to be less than 2^31 - 1.
 *
 *
 * Example:
 *
 * Input: [2, 4, 6, 8, 10]
 *
 * Output: 7
 *
 * Explanation:
 * All arithmetic subsequence slices are:
 * [2,4,6]
 * [4,6,8]
 * [6,8,10]
 * [2,4,6,8]
 * [4,6,8,10]
 * [2,4,6,8,10]
 * [2,6,10]
 *
 * 上一题 {@link Q413_ArithmeticSlices}
 */
@RunWith(LeetCodeRunner.class)
public class Q446_ArithmeticSlicesIISubsequence {

    /**
     * 相比上一题, 这题不相邻元素之间也可以组成子序列.
     * https://www.cnblogs.com/grandyang/p/6057934.html
     */
    @Answer
    public int numberOfArithmeticSlices(int[] A) {
        int res = 0, n = A.length;
        Map<Integer, Integer>[] dp = new Map[n];
        for (int i = 0; i < n; i++) {
            dp[i] = new HashMap<>();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                long delta = (long) A[i] - A[j];
                if (delta > Integer.MAX_VALUE
                        || delta < Integer.MIN_VALUE) {
                    continue;
                }
                int diff = (int) delta;
                dp[i].put(diff, dp[i].getOrDefault(diff, 0) + 1);

                Integer countJ = dp[j].get(diff);
                if (countJ != null) {
                    res += countJ;
                    dp[i].put(diff, dp[i].get(diff) + countJ);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[]{2, 4, 6, 8, 10}).expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[]{2, 2, 3, 4}).expect(2);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new int[]{0, 1, 2, 2, 2}).expect(4);

}
