package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1712. Ways to Split Array Into Three Subarrays
 * https://leetcode.com/problems/ways-to-split-array-into-three-subarrays/
 *
 * A split of an integer array is good if:
 *
 * - The array is split into three non-empty contiguous subarrays - named left, mid, right respectively from left to
 * right.
 * - The sum of the elements in left is less than or equal to the sum of the elements in mid, and the sum of the
 * elements in mid is less than or equal to the sum of the elements in right.
 *
 * Given nums, an array of non-negative integers, return the number of good ways to split nums. As the number may be
 * too
 * large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: nums = [1,1,1]
 * Output: 1
 * Explanation: The only good way to split nums is [1] [1] [1].
 *
 * Example 2:
 *
 * Input: nums = [1,2,2,2,5,0]
 * Output: 3
 * Explanation: There are three good ways of splitting nums:
 * [1] [2] [2,2,5,0]
 * [1] [2,2] [2,5,0]
 * [1,2] [2,2] [5,0]
 *
 * Example 3:
 *
 * Input: nums = [3,2,1]
 * Output: 0
 * Explanation: There is no good way to split nums.
 *
 * Constraints:
 *
 * 3 <= nums.length <= 10^5
 * 0 <= nums[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1712_WaysToSplitArrayIntoThreeSubarrays {

    /**
     * 边界条件太烦人了.
     *
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/ways-to-split-array-into-three-subarrays/solution/5643-jiang-shu-zu-fen-cheng-san-ge-zi-sh-fmep/
     * @formatter:on
     */
    @Answer
    public int waysToSplit(int[] nums) {
        final int mod = 1000000007;
        final int n = nums.length;

        // 计算前缀和
        int[] sums = new int[n];
        sums[0] = nums[0];
        for (int i = 1; i < n; i++) {
            sums[i] = sums[i - 1] + nums[i];
        }

        long res = 0;
        // 第一刀的最大值：sum(nums) / 3
        int t = sums[n - 1] / 3;
        for (int i = 0; i < n && sums[i] <= t; i++) {
            // 二分查找第二刀的最小值：sum(mid) == sum(left)
            // 在 [i+1, n] 中二分查找 sums[i] * 2，sums[i] 为到 i 为止元素和，因为是前缀数组，因而应该查找 sum(left) + sum(mid)
            int left = lowerBound(i + 1, n - 1, sums, sums[i] * 2);
            // 二分查找第二刀的最大值：sum(mid) == sum(mid + right) / 2
            // 在 [i+1, n] 中二分查找 sums[i] + (sums[n - 1] - sums[i]) / 2)，因为是前缀数组，因而应该查找 sum(left) + sum(mid + right) / 2
            int right = upperBound(i + 1, n - 1, sums, sums[i] + (sums[n - 1] - sums[i]) / 2);
            if (right >= left) {
                res += right - left + 1;
            }
        }
        return (int) (res % mod);
    }

    public int lowerBound(int left, int right, int[] nums, int target) {
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public int upperBound(int left, int right, int[] nums, int target) {
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (nums[mid] <= target) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left - 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 1, 1}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 2, 2, 2, 5, 0}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{3, 2, 1}).expect(0);

}
