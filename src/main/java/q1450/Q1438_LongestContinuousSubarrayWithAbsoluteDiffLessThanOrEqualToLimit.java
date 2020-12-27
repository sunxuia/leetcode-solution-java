package q1450;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1438. Longest Continuous Subarray With Absolute Diff Less Than or Equal to Limit
 * https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/
 *
 * Given an array of integers nums and an integer limit, return the size of the longest non-empty subarray such that the
 * absolute difference between any two elements of this subarray is less than or equal to limit.
 *
 * Example 1:
 *
 * Input: nums = [8,2,4,7], limit = 4
 * Output: 2
 * Explanation: All subarrays are:
 * [8] with maximum absolute diff |8-8| = 0 <= 4.
 * [8,2] with maximum absolute diff |8-2| = 6 > 4.
 * [8,2,4] with maximum absolute diff |8-2| = 6 > 4.
 * [8,2,4,7] with maximum absolute diff |8-2| = 6 > 4.
 * [2] with maximum absolute diff |2-2| = 0 <= 4.
 * [2,4] with maximum absolute diff |2-4| = 2 <= 4.
 * [2,4,7] with maximum absolute diff |2-7| = 5 > 4.
 * [4] with maximum absolute diff |4-4| = 0 <= 4.
 * [4,7] with maximum absolute diff |4-7| = 3 <= 4.
 * [7] with maximum absolute diff |7-7| = 0 <= 4.
 * Therefore, the size of the longest subarray is 2.
 *
 * Example 2:
 *
 * Input: nums = [10,1,2,4,7,2], limit = 5
 * Output: 4
 * Explanation: The subarray [2,4,7,2] is the longest since the maximum absolute diff is |2-7| = 5 <= 5.
 *
 * Example 3:
 *
 * Input: nums = [4,2,2,2,4,4,2,2], limit = 0
 * Output: 3
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^9
 * 0 <= limit <= 10^9
 *
 * 题解: 注意这题中的子数组的定义是 nums[s:e], s与e 之间的元素是不能少的.
 */
@RunWith(LeetCodeRunner.class)
public class Q1438_LongestContinuousSubarrayWithAbsoluteDiffLessThanOrEqualToLimit {

    /**
     * 滑动窗口的题目.
     * 类似 {@link Q1425_ConstrainedSubsequenceSum#constrainedSubsetSum2(int[], int)} 的做法.
     * 时间复杂度 O(N)
     */
    @Answer
    public int longestSubarray(int[] nums, int limit) {
        int res = 0;
        Deque<Integer> maxs = new ArrayDeque<>();
        Deque<Integer> mins = new ArrayDeque<>();
        for (int i = 0, j = 0; j < nums.length; i++) {
            // 向右扩展窗口
            while (j < nums.length && (maxs.isEmpty() || mins.isEmpty()
                    || nums[j] + limit >= nums[maxs.peekFirst()]
                    && nums[j] - limit <= nums[mins.peekFirst()])) {
                while (!maxs.isEmpty() && nums[maxs.peekLast()] <= nums[j]) {
                    maxs.pollLast();
                }
                while (!mins.isEmpty() && nums[mins.peekLast()] >= nums[j]) {
                    mins.pollLast();
                }
                maxs.offerLast(j);
                mins.offerLast(j);
                j++;
            }

            res = Math.max(res, j - i);

            // 从左边缩小窗口
            if (!maxs.isEmpty() && maxs.peekFirst() == i) {
                maxs.pollFirst();
            }
            if (!mins.isEmpty() && mins.peekFirst() == i) {
                mins.pollFirst();
            }
        }
        return res;
    }

    /**
     * LeetCode 上最快的解法, 时间复杂度 O(N^2)
     */
    @Answer
    public int longestSubarray2(int[] nums, int limit) {
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        int res = 0, i = 0, j = 0;
        while (j < nums.length && i <= j) {
            min = Math.min(min, nums[j]);
            max = Math.max(max, nums[j]);
            if (max - min <= limit) {
                // 窗口满足限制条件.
                res = Math.max(res, j - i + 1);
                j++;
            } else if (res - 1 > nums.length - j) {
                // 剩下的空间遍历结果不会超过res, 无需再遍历了,
                // 这个判断用于剪枝, 删掉后速度会慢很多.
                break;
            } else {
                // k 从j 到i 遍历, 重新寻找i 之后的最大最小值.
                int k = j;
                int preMax = max = nums[j];
                int preMin = min = nums[j];
                while (k >= i && max - min <= limit) {
                    preMax = max;
                    preMin = min;
                    max = Math.max(max, nums[k]);
                    min = Math.min(min, nums[k]);
                    k--;
                }
                i = k + 2;
                max = preMax;
                min = preMin;
            }
        }
        return res;
    }


    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{8, 2, 4, 7}, 4)
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{10, 1, 2, 4, 7, 2}, 5)
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{4, 2, 2, 2, 4, 4, 2, 2}, 0)
            .expect(3);

}
