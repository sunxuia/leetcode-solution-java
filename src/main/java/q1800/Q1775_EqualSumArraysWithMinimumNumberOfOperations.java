package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1775. Equal Sum Arrays With Minimum Number of Operations
 * https://leetcode.com/problems/equal-sum-arrays-with-minimum-number-of-operations/
 *
 * You are given two arrays of integers nums1 and nums2, possibly of different lengths. The values in the arrays are
 * between 1 and 6, inclusive.
 *
 * In one operation, you can change any integer's value in any of the arrays to any value between 1 and 6, inclusive.
 *
 * Return the minimum number of operations required to make the sum of values in nums1 equal to the sum of values in
 * nums2. Return -1 if it is not possible to make the sum of the two arrays equal.
 *
 * Example 1:
 *
 * Input: nums1 = [1,2,3,4,5,6], nums2 = [1,1,2,2,2,2]
 * Output: 3
 * Explanation: You can make the sums of nums1 and nums2 equal with 3 operations. All indices are 0-indexed.
 * - Change nums2[0] to 6. nums1 = [1,2,3,4,5,6], nums2 = [6,1,2,2,2,2].
 * - Change nums1[5] to 1. nums1 = [1,2,3,4,5,1], nums2 = [6,1,2,2,2,2].
 * - Change nums1[2] to 2. nums1 = [1,2,2,4,5,1], nums2 = [6,1,2,2,2,2].
 *
 * Example 2:
 *
 * Input: nums1 = [1,1,1,1,1,1,1], nums2 = [6]
 * Output: -1
 * Explanation: There is no way to decrease the sum of nums1 or to increase the sum of nums2 to make them equal.
 *
 * Example 3:
 *
 * Input: nums1 = [6,6], nums2 = [1]
 * Output: 3
 * Explanation: You can make the sums of nums1 and nums2 equal with 3 operations. All indices are 0-indexed.
 * - Change nums1[0] to 2. nums1 = [2,6], nums2 = [1].
 * - Change nums1[1] to 2. nums1 = [2,2], nums2 = [1].
 * - Change nums2[0] to 4. nums1 = [2,2], nums2 = [4].
 *
 * Constraints:
 *
 * 1 <= nums1.length, nums2.length <= 10^5
 * 1 <= nums1[i], nums2[i] <= 6
 */
@RunWith(LeetCodeRunner.class)
public class Q1775_EqualSumArraysWithMinimumNumberOfOperations {

    /**
     * 根据提示可以通过贪婪算法逼近
     */
    @Answer
    public int minOperations(int[] nums1, int[] nums2) {
        final int m = nums1.length;
        final int n = nums2.length;
        if (m > n * 6 || n > m * 6) {
            return -1;
        }

        int sum1 = 0;
        int[] count1 = new int[7];
        int sum2 = 0;
        int[] count2 = new int[7];
        for (int num : nums1) {
            sum1 += num;
            count1[num]++;
        }
        for (int num : nums2) {
            sum2 += num;
            count2[num]++;
        }

        int res = 0;
        loop:
        while (sum1 != sum2) {
            res++;
            if (sum1 < sum2) {
                int t = sum1;
                sum1 = sum2;
                sum2 = t;
                int[] at = count1;
                count1 = count2;
                count2 = at;
            }
            // 贪婪算法: 从大到小尝试逼近
            for (int diff = 5; diff > 0; diff--) {
                if (sum1 - sum2 >= diff) {
                    for (int i = 6, j = i - diff; j > 0; i--, j--) {
                        if (count1[i] > 0) {
                            // 大的减小
                            count1[i]--;
                            count1[j]++;
                            sum1 -= diff;
                            continue loop;
                        } else if (count2[j] > 0) {
                            // 小的增大
                            count2[i]++;
                            count2[j]--;
                            sum2 += diff;
                            continue loop;
                        }
                    }
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6}, new int[]{1, 1, 2, 2, 2, 2})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{6})
            .expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{6, 6}, new int[]{1})
            .expect(3);

}
