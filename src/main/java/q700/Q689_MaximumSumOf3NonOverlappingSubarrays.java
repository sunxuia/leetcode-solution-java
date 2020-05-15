package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/maximum-sum-of-3-non-overlapping-subarrays/
 *
 * In a given array nums of positive integers, find three non-overlapping subarrays with maximum sum.
 *
 * Each subarray will be of size k, and we want to maximize the sum of all 3*k entries.
 *
 * Return the result as a list of indices representing the starting position of each interval (0-indexed). If there
 * are multiple answers, return the lexicographically smallest one.
 *
 * Example:
 *
 * Input: [1,2,1,2,6,7,5,1], 2
 * Output: [0, 3, 5]
 * Explanation: Subarrays [1, 2], [2, 6], [7, 5] correspond to the starting indices [0, 3, 5].
 * We could have also taken [2, 1], but an answer of [1, 3, 5] would be lexicographically larger.
 *
 *
 *
 * Note:
 *
 * nums.length will be between 1 and 20000.
 * nums[i] will be between 1 and 65535.
 * k will be between 1 and floor(nums.length / 3).
 */
@RunWith(LeetCodeRunner.class)
public class Q689_MaximumSumOf3NonOverlappingSubarrays {

    // https://www.cnblogs.com/grandyang/p/8453386.html
    // 遍历选择中间的区间, 然后从左右选择.
    @Answer
    public int[] maxSumOfThreeSubarrays(int[] nums, int k) {
        final int n = nums.length;
        int[] sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
        // kSums[i] 表示 [i, i + k) 区间元素的总和
        int[] kSums = new int[n - k + 1];
        for (int i = 0; i <= n - k; i++) {
            kSums[i] = sums[i + k] - sums[i];
        }

        // left[i] 表示在区间[0, i) 范围内长度为k 且和最大的子数组的起始位置
        int[] left = new int[n - 2 * k + 1];
        int best = 0;
        for (int i = k; i <= n - 2 * k; i++) {
            best = kSums[i - k] > kSums[best] ? i - k : best;
            left[i] = best;
        }

        // right[i] 表示在区间 [i, n) 范围内长度为k 且和最大的子数组的起始位置
        int[] right = new int[n - k + 1];
        best = n - k;
        for (int i = n - k; i >= 2 * k; i--) {
            best = kSums[i] >= kSums[best] ? i : best;
            right[i] = best;
        }

        // 遍历数组, 选择中间的区间
        int[] res = new int[3];
        int max = 0;
        for (int i = k; i <= n - 2 * k; i++) {
            int sum = kSums[left[i]] + kSums[i] + kSums[right[i + k]];
            if (max < sum) {
                max = sum;
                res[0] = left[i];
                res[1] = i;
                res[2] = right[i + k];
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 2, 1, 2, 6, 7, 5, 1}, 2).expect(new int[]{0, 3, 5});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{9, 8, 7, 6, 2, 2, 2, 2}, 2).expect(new int[]{0, 2, 4});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{7, 13, 20, 19, 19, 2, 10, 1, 1, 19}, 3).expect(new int[]{1, 4, 7});

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{20, 18, 9, 2, 14, 1, 10, 3, 11, 18}, 3).expect(new int[]{0, 4, 7});

}
