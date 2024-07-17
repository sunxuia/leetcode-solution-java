package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DebugWith;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 2040. Kth Smallest Product of Two Sorted Arrays
 * https://leetcode.com/problems/kth-smallest-product-of-two-sorted-arrays/
 *
 * Given two sorted 0-indexed integer arrays nums1 and nums2 as well as an integer k, return the kth (1-based) smallest
 * product of nums1[i] * nums2[j] where 0 <= i < nums1.length and 0 <= j < nums2.length.
 *
 * Example 1:
 *
 * Input: nums1 = [2,5], nums2 = [3,4], k = 2
 * Output: 8
 * Explanation: The 2 smallest products are:
 * - nums1[0] * nums2[0] = 2 * 3 = 6
 * - nums1[0] * nums2[1] = 2 * 4 = 8
 * The 2nd smallest product is 8.
 *
 * Example 2:
 *
 * Input: nums1 = [-4,-2,0,3], nums2 = [2,4], k = 6
 * Output: 0
 * Explanation: The 6 smallest products are:
 * - nums1[0] * nums2[1] = (-4) * 4 = -16
 * - nums1[0] * nums2[0] = (-4) * 2 = -8
 * - nums1[1] * nums2[1] = (-2) * 4 = -8
 * - nums1[1] * nums2[0] = (-2) * 2 = -4
 * - nums1[2] * nums2[0] = 0 * 2 = 0
 * - nums1[2] * nums2[1] = 0 * 4 = 0
 * The 6th smallest product is 0.
 *
 * Example 3:
 *
 * Input: nums1 = [-2,-1,0,1,2], nums2 = [-3,-1,2,4,5], k = 3
 * Output: -6
 * Explanation: The 3 smallest products are:
 * - nums1[0] * nums2[4] = (-2) * 5 = -10
 * - nums1[0] * nums2[3] = (-2) * 4 = -8
 * - nums1[4] * nums2[0] = 2 * (-3) = -6
 * The 3rd smallest product is -6.
 *
 * Constraints:
 *
 * 1 <= nums1.length, nums2.length <= 5 * 10^4
 * -10^5 <= nums1[i], nums2[j] <= 10^5
 * 1 <= k <= nums1.length * nums2.length
 * nums1 and nums2 are sorted.
 */
@RunWith(LeetCodeRunner.class)
public class Q2040_KthSmallestProductOfTwoSortedArrays {

    @Answer
    public long kthSmallestProduct(int[] nums1, int[] nums2, long k) {
        final int m = nums1.length;
        final int n = nums2.length;
        long min = Math.min(Math.min((long) nums1[0] * nums2[0], (long) nums1[0] * nums2[n - 1]),
                Math.min((long) nums1[m - 1] * nums2[0], (long) nums1[m - 1] * nums2[n - 1]));
        long max = Math.max(Math.max((long) nums1[0] * nums2[0], (long) nums1[0] * nums2[n - 1]),
                Math.max((long) nums1[m - 1] * nums2[0], (long) nums1[m - 1] * nums2[n - 1]));
        while (min < max) {
            long mid = max <= 0 ? (max - 1 + min) / 2 : (max + min) / 2;
            long total = 0;
            // 遍历 nums1 中的元素, 统计与nums2 的乘积 <= mid 的数量
            for (int i = 0; i < m; i++) {
                if (nums1[i] >= 0) {
                    // 找出nums2 中第一个 mid < nums1[i] * nums2[j] 的下标
                    int s = 0, e = n;
                    while (s < e) {
                        int j = (s + e) / 2;
                        if ((long) nums1[i] * nums2[j] <= mid) {
                            s = j + 1;
                        } else {
                            e = j;
                        }
                    }
                    total += s;
                } else {
                    // 因为是负数, 所以要逆向找出nums2 中满足 mid <= nums1[i] * nums2[j] 最小的下标
                    int s = 0, e = n;
                    while (s < e) {
                        int j = (s + e) / 2;
                        if ((long) nums1[i] * nums2[j] <= mid) {
                            e = j;
                        } else {
                            s = j + 1;
                        }
                    }
                    total += n - e;
                }
            }
            // 如果数量小于 k, 则表示目标值肯定 > min, 否则目标值 <= max
            if (total < k) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }
        return min;
    }


    /**
     * 针对nums1 和 num2 中元素都 >=0 的简化条件的解法, 用于理解上面的解法.
     * 时间复杂度 (logv)n(logm) (v表示 nums1 与 nums2 的乘积可能取值的范围)
     */
    @DebugWith({"example1", "normal1"})
    @Answer
    public long kthSmallestProduct_positive(int[] nums1, int[] nums2, long k) {
        final int m = nums1.length;
        final int n = nums2.length;
        // 二分查找可能取值的范围, 找出那个目标值
        long min = (long) nums1[0] * nums2[0];
        long max = (long) nums1[m - 1] * nums2[n - 1];
        while (min < max) {
            long mid = min + (max - min) / 2;

            // 遍历 nums1 中的元素, 统计与nums2 的乘积 <= mid 的数量
            long total = 0;
            for (int i = 0; i < m; i++) {
                // 找出nums2 中第一个 mid < nums1[i] * nums2[j] 的下标
                int s = 0, e = n;
                while (s < e) {
                    int j = (s + e) / 2;
                    if ((long) nums1[i] * nums2[j] <= mid) {
                        s = j + 1;
                    } else {
                        e = j;
                    }
                }
                total += s;
            }

            // 如果数量小于 k, 则表示目标值肯定 > min, 否则目标值 <= max
            if (total < k) {
                min = mid + 1;
            } else {
                max = mid;
            }
        }
        return min;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, 5}, new int[]{3, 4}, 2)
            .expect(8L);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{-4, -2, 0, 3}, new int[]{2, 4}, 6)
            .expect(0L);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{-2, -1, 0, 1, 2}, new int[]{-3, -1, 2, 4, 5}, 3)
            .expect(-6L);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 5, 6}, new int[]{3, 4, 5, 6, 7, 10}, 8L)
            .expect(9L);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{-10_0000, 10_0000}, new int[]{-10_0000, 10_0000}, 1L)
            .expect(-100_0000_0000L);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{-2, -1, 0, 1, 2}, new int[]{-3, -1, 2, 4, 5}, 3L)
            .expect(-6L);

    @TestData
    public DataExpectation normal4() {
        var file = new TestDataFile("Q2040_TestData");
        return DataExpectation.createWith(
                TestDataFileHelper.read(file, 1, int[].class),
                TestDataFileHelper.read(file, 2, int[].class),
                2499989959L
        ).expect(9997500066L);
    }

}
