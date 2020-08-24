package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 992. Subarrays with K Different Integers
 * https://leetcode.com/problems/subarrays-with-k-different-integers/
 *
 * Given an array A of positive integers, call a (contiguous, not necessarily distinct) subarray of A good if the number
 * of different integers in that subarray is exactly K.
 *
 * (For example, [1,2,3,1,2] has 3 different integers: 1, 2, and 3.)
 *
 * Return the number of good subarrays of A.
 *
 * Example 1:
 *
 * Input: A = [1,2,1,2,3], K = 2
 * Output: 7
 * Explanation: Subarrays formed with exactly 2 different integers: [1,2], [2,1], [1,2], [2,3], [1,2,1], [2,1,2],
 * [1,2,1,2].
 *
 * Example 2:
 *
 * Input: A = [1,2,1,3,4], K = 3
 * Output: 3
 * Explanation: Subarrays formed with exactly 3 different integers: [1,2,1,3], [2,1,3], [1,3,4].
 *
 * Note:
 *
 * 1 <= A.length <= 20000
 * 1 <= A[i] <= A.length
 * 1 <= K <= A.length
 */
@RunWith(LeetCodeRunner.class)
public class Q992_SubarraysWithKDifferentIntegers {

    /**
     * 划分区间为如下样式:
     * . . . left . . . right . . . farRight . . .
     * 其中 [left, right] 和 [left, farRight) 中的不同数字之和都为K,
     * left 为本次判定的区间起始位置, right 为区间有效的最近位置, farRight - 1 为区间有效的最远位置.
     */
    @Answer
    public int subarraysWithKDistinct(int[] A, int K) {
        final int n = A.length;
        int[] counts = new int[n + 1];
        int res = 0, sum = 0;
        int left = -1, right = -1, farRight = 0;
        while (++left < n) {
            while (sum < K && ++right < n) {
                if (++counts[A[right]] == 1) {
                    sum++;
                }
            }
            if (sum < K) {
                break;
            }
            while (farRight < n && counts[A[farRight]] > 0) {
                farRight++;
            }
            res += farRight - right;
            if (--counts[A[left]] == 0) {
                sum--;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 1, 2, 3}, 2).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 1, 3, 4}, 3).expect(3);

}
