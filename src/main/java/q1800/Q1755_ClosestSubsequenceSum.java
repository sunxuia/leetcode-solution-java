package q1800;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1755. Closest Subsequence Sum
 * https://leetcode.com/problems/closest-subsequence-sum/
 *
 * You are given an integer array nums and an integer goal.
 *
 * You want to choose a subsequence of nums such that the sum of its elements is the closest possible to goal. That is,
 * if the sum of the subsequence's elements is sum, then you want to minimize the absolute difference abs(sum - goal).
 *
 * Return the minimum possible value of abs(sum - goal).
 *
 * Note that a subsequence of an array is an array formed by removing some elements (possibly all or none) of the
 * original array.
 *
 * Example 1:
 *
 * Input: nums = [5,-7,3,5], goal = 6
 * Output: 0
 * Explanation: Choose the whole array as a subsequence, with a sum of 6.
 * This is equal to the goal, so the absolute difference is 0.
 *
 * Example 2:
 *
 * Input: nums = [7,-9,15,-2], goal = -5
 * Output: 1
 * Explanation: Choose the subsequence [7,-9,-2], with a sum of -4.
 * The absolute difference is abs(-4 - (-5)) = abs(1) = 1, which is the minimum.
 *
 * Example 3:
 *
 * Input: nums = [1,2,3], goal = -7
 * Output: 7
 *
 * Constraints:
 *
 * 1 <= nums.length <= 40
 * -10^7 <= nums[i] <= 10^7
 * -10^9 <= goal <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1755_ClosestSubsequenceSum {

    /**
     * 将数组沿着中间位置切分, 对左右部分分别进行状态压缩, 求出每种组合可能的结果,
     * 然后遍历左右结果的和, 找出最小结果.
     *
     * 参考文档
     * https://leetcode-cn.com/problems/closest-subsequence-sum/solution/zhuang-ya-dp-zhi-cong-kan-shu-ju-fan-wei-kve3/
     */
    @Answer
    public int minAbsDifference(int[] nums, int goal) {
        final int n = nums.length;

        // 左半部分状态压缩, 求出各种组合的结果.
        int[] left = getSums(nums, 0, n / 2);

        // 右半部分状态压缩, 求出各种组合的结果.
        int[] right = getSums(nums, n / 2, n);

        // 组合
        int res = Integer.MAX_VALUE;
        for (int sum : left) {
            res = Math.min(res, Math.abs(goal - sum));
        }
        for (int sum : right) {
            res = Math.min(res, Math.abs(goal - sum));
        }
        int i = 0, j = right.length - 1;
        // 排序后的左右逼近
        while (i < left.length && j >= 0) {
            int sum = left[i] + right[j];
            res = Math.min(res, Math.abs(goal - sum));
            if (sum > goal) {
                j--;
            } else {
                i++;
            }
        }
        return res;
    }

    /**
     * 求 nums[start, end) 中所有元素组合之和.
     */
    private int[] getSums(int[] nums, int start, int end) {
        int len = 1 << (end - start);
        int[] sums = new int[len];
        for (int mask = 1; mask < len; mask++) {
            for (int j = 0; j < end - start; j++) {
                if ((mask >> j & 1) == 1) {
                    sums[mask] = sums[mask - (1 << j)] + nums[j + start];
                    break;
                }
            }
        }
        Arrays.sort(sums);
        return sums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{5, -7, 3, 5}, 6).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{7, -9, 15, -2}, -5).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 3}, -7).expect(7);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{-6651, 401, -8998, -9269, -9167, 7741, -9699}, 30536)
            .expect(22394);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{0, -8, -14, 7, -21, 21, -7, 5, 13, -6, 30, 30}, -54)
            .expect(2);

}
