package q1850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1818. Minimum Absolute Sum Difference
 * https://leetcode.com/problems/minimum-absolute-sum-difference/
 *
 * You are given two positive integer arrays nums1 and nums2, both of length n.
 *
 * The absolute sum difference of arrays nums1 and nums2 is defined as the sum of |nums1[i] - nums2[i]| for each 0 <= i
 * < n (0-indexed).
 *
 * You can replace at most one element of nums1 with any other element in nums1 to minimize the absolute sum
 * difference.
 *
 * Return the minimum absolute sum difference after replacing at most one element in the array nums1. Since the answer
 * may be large, return it modulo 10^9 + 7.
 *
 * |x| is defined as:
 *
 * x if x >= 0, or
 * -x if x < 0.
 *
 * Example 1:
 *
 * Input: nums1 = [1,7,5], nums2 = [2,3,5]
 * Output: 3
 * Explanation: There are two possible optimal solutions:
 * - Replace the second element with the first: [1,7,5] => [1,1,5], or
 * - Replace the second element with the third: [1,7,5] => [1,5,5].
 * Both will yield an absolute sum difference of |1-2| + (|1-3| or |5-3|) + |5-5| = 3.
 *
 * Example 2:
 *
 * Input: nums1 = [2,4,6,8,10], nums2 = [2,4,6,8,10]
 * Output: 0
 * Explanation: nums1 is equal to nums2 so no replacement is needed. This will result in an
 * absolute sum difference of 0.
 *
 * Example 3:
 *
 * Input: nums1 = [1,10,4,4,2,7], nums2 = [9,3,5,1,7,4]
 * Output: 20
 * Explanation: Replace the first element with the second: [1,10,4,4,2,7] => [10,10,4,4,2,7].
 * This yields an absolute sum difference of |10-9| + |10-3| + |4-5| + |4-1| + |2-7| + |7-4| = 20
 *
 * Constraints:
 *
 * n == nums1.length
 * n == nums2.length
 * 1 <= n <= 10^5
 * 1 <= nums1[i], nums2[i] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1818_MinimumAbsoluteSumDifference {

    @Answer
    public int minAbsoluteSumDiff(int[] nums1, int[] nums2) {
        final int max = 10_0000;
        int[] less = new int[max + 1];
        less[0] = -max;
        int[] more = new int[max + 1];
        more[max] = 2 * max;
        for (int num : nums1) {
            less[num] = num;
            more[num] = num;
        }
        for (int i = 1; i <= max; i++) {
            if (less[i] == 0) {
                less[i] = less[i - 1];
            }
        }
        for (int i = max - 1; i > 0; i--) {
            if (more[i] == 0) {
                more[i] = more[i + 1];
            }
        }

        int res = 0, maxOffset = 0;
        for (int i = 0; i < nums1.length; i++) {
            int v1 = nums1[i], v2 = nums2[i];
            int abs = Math.abs(v1 - v2);
            res += abs;
            int near = Math.min(v2 - less[v2], more[v2] - v2);
            int offset = abs - near;
            if (offset > maxOffset) {
                res += maxOffset - offset;
                maxOffset = offset;
            }
            res %= 10_0000_0007;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 7, 5}, new int[]{2, 3, 5})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 4, 6, 8, 10}, new int[]{2, 4, 6, 8, 10})
            .expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 10, 4, 4, 2, 7}, new int[]{9, 3, 5, 1, 7, 4})
            .expect(20);

}
