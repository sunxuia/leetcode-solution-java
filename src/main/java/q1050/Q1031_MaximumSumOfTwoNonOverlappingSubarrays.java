package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1031. Maximum Sum of Two Non-Overlapping Subarrays
 * https://leetcode.com/problems/maximum-sum-of-two-non-overlapping-subarrays/
 *
 * Given an array A of non-negative integers, return the maximum sum of elements in two non-overlapping (contiguous)
 * subarrays, which have lengths L and M.  (For clarification, the L-length subarray could occur before or after the
 * M-length subarray.)
 *
 * Formally, return the largest V for which V = (A[i] + A[i+1] + ... + A[i+L-1]) + (A[j] + A[j+1] + ... + A[j+M-1]) and
 * either:
 *
 * 0 <= i < i + L - 1 < j < j + M - 1 < A.length, or
 * 0 <= j < j + M - 1 < i < i + L - 1 < A.length.
 *
 * Example 1:
 *
 * Input: A = [0,6,5,2,2,5,1,9,4], L = 1, M = 2
 * Output: 20
 * Explanation: One choice of subarrays is [9] with length 1, and [6,5] with length 2.
 *
 * Example 2:
 *
 * Input: A = [3,8,1,3,2,1,8,9,0], L = 3, M = 2
 * Output: 29
 * Explanation: One choice of subarrays is [3,8,1] with length 3, and [8,9] with length 2.
 *
 * Example 3:
 *
 * Input: A = [2,1,5,6,0,9,5,0,3,8], L = 4, M = 3
 * Output: 31
 * Explanation: One choice of subarrays is [5,6,0,9] with length 4, and [3,8] with length 3.
 *
 * Note:
 *
 * L >= 1
 * M >= 1
 * L + M <= A.length <= 1000
 * 0 <= A[i] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1031_MaximumSumOfTwoNonOverlappingSubarrays {

    @Answer
    public int maxSumTwoNoOverlap(int[] A, int L, int M) {
        final int n = A.length;
        // sums[i] 表示 [0, i) 区间内元素之和
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = A[i] + sums[i];
        }

        // maxL[i] 表示 [i, n) 内 L 长区间的最大值
        int[] maxL = new int[n + 1];
        for (int i = n - L; i >= 0; i--) {
            maxL[i] = Math.max(maxL[i + 1], sums[i + L] - sums[i]);
        }
        // maxM[i] 表示 [i, n) 内 M 长区间的最大值
        int[] maxM = new int[n + 1];
        for (int i = n - M; i >= 0; i--) {
            maxM[i] = Math.max(maxM[i + 1], sums[i + M] - sums[i]);
        }

        // 选择 M 长区间 [i, i+M), 在后面匹配最大的 L 区间,
        // 或者选择 L 长区间 [i, i+L), 在后面匹配最大的 M 区间.
        int res = 0;
        for (int i = 0; i <= n - M - L; i++) {
            res = Math.max(res, sums[i + M] - sums[i] + maxL[i + M]);
            res = Math.max(res, sums[i + L] - sums[i] + maxM[i + L]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{0, 6, 5, 2, 2, 5, 1, 9, 4}, 1, 2)
            .expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{3, 8, 1, 3, 2, 1, 8, 9, 0}, 3, 2)
            .expect(29);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{2, 1, 5, 6, 0, 9, 5, 0, 3, 8}, 4, 3)
            .expect(31);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, 0, 3}, 1, 2)
            .expect(4);

}
