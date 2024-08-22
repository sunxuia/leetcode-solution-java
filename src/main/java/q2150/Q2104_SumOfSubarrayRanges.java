package q2150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Medium] 2104. Sum of Subarray Ranges</h3>
 * <a href="https://leetcode.com/problems/sum-of-subarray-ranges/">
 * https://leetcode.com/problems/sum-of-subarray-ranges/
 * </a><br/>
 *
 * <p>You are given an integer array <code>nums</code>. The <strong>range</strong> of a subarray of <code>nums</code> is
 * the difference between the largest and smallest element in the subarray.</p>
 *
 * <p>Return <em>the <strong>sum of all</strong> subarray ranges of </em><code>nums</code><em>.</em></p>
 *
 * <p>A subarray is a contiguous <strong>non-empty</strong> sequence of elements within an array.</p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,2,3]
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong> The 6 subarrays of nums are the following:
 * [1], range = largest - smallest = 1 - 1 = 0
 * [2], range = 2 - 2 = 0
 * [3], range = 3 - 3 = 0
 * [1,2], range = 2 - 1 = 1
 * [2,3], range = 3 - 2 = 1
 * [1,2,3], range = 3 - 1 = 2
 * So the sum of all ranges is 0 + 0 + 0 + 1 + 1 + 2 = 4.</pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [1,3,3]
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong> The 6 subarrays of nums are the following:
 * [1], range = largest - smallest = 1 - 1 = 0
 * [3], range = 3 - 3 = 0
 * [3], range = 3 - 3 = 0
 * [1,3], range = 3 - 1 = 2
 * [3,3], range = 3 - 3 = 0
 * [1,3,3], range = 3 - 1 = 2
 * So the sum of all ranges is 0 + 0 + 0 + 2 + 0 + 2 = 4.
 * </pre>
 *
 * <p><strong class="example">Example 3:</strong></p>
 *
 * <pre>
 * <strong>Input:</strong> nums = [4,-2,-3,4,1]
 * <strong>Output:</strong> 59
 * <strong>Explanation:</strong> The sum of all subarray ranges of nums is 59.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= nums.length &lt;= 1000</code></li>
 * 	<li><code>-10<sup>9</sup> &lt;= nums[i] &lt;= 10<sup>9</sup></code></li>
 * </ul>
 *
 * <p>&nbsp;</p>
 * <p><strong>Follow-up:</strong> Could you find a solution with <code>O(n)</code> time complexity?</p>
 */
@RunWith(LeetCodeRunner.class)
public class Q2104_SumOfSubarrayRanges {

    @Answer
    public long subArrayRanges(int[] nums) {
        final int n = nums.length;
        long res = 0;
        for (int i = 0; i < n; i++) {
            int max = nums[i], min = nums[i];
            for (int j = i + 1; j < n; j++) {
                max = Math.max(max, nums[j]);
                min = Math.min(min, nums[j]);
                res += max - min;
            }
        }
        return res;
    }

    // 另一种思路, 找出最大值和最小值之和, 然后相减.
    // 通过将二分查找递增/递减队列来将时间复杂度降低到 NlogN
    @Answer
    public long subArrayRanges2(int[] nums) {
        final int n = nums.length;

        // 单调递减(找最大值)递增(找最小值)队列
        int[] queue = new int[n];
        int queueSize = 1;
        queue[0] = 0;

        // sums[i] 表示 nums[i] 结尾的从 0 到 i 的所有子数组的最大值/最小值之和
        long[] sums = new long[n];
        sums[0] = nums[0];

        long maxSums = 0;
        for (int i = 1; i < n; i++) {
            // 在递减队列 queue[0:queueSize) 中找出值 > target 的最大下标
            int start = -1, end = queueSize - 1;
            while (start < end) {
                int mid = (start + end + 1) / 2;
                if (nums[queue[mid]] > nums[i]) {
                    start = mid;
                } else {
                    end = mid - 1;
                }
            }
            if (start == -1) {
                // nums[i] 是以 nums[i] 结尾的所有子数组的最大值
                sums[i] = (long) (i + 1) * nums[i];
            } else {
                // nums[i] 是nums[idx, i] 中以 nums[i] 结尾的所有子数组的最大值,
                // 在idx 之前的最大值还是原来的, 因此这部分子数组的和还是之前的和
                int idx = queue[start];
                sums[i] = sums[idx] + (long) (i - idx) * nums[i];
            }
            maxSums += sums[i];
            queue[start + 1] = i;
            queueSize = start + 2;
        }

        queue[0] = 0;
        queueSize = 1;
        long minSums = 0;
        for (int i = 1; i < n; i++) {
            // 在递增队列 queue[0:queueSize) 中找出值 < target 的最大下标
            int start = -1, end = queueSize - 1;
            while (start < end) {
                int mid = (start + end + 1) / 2;
                if (nums[queue[mid]] < nums[i]) {
                    start = mid;
                } else {
                    end = mid - 1;
                }
            }
            if (start == -1) {
                sums[i] = (long) (i + 1) * nums[i];
            } else {
                int idx = queue[start];
                sums[i] = sums[idx] + (long) (i - idx) * nums[i];
            }
            minSums += sums[i];
            queue[start + 1] = i;
            queueSize = start + 2;
        }

        return maxSums - minSums;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3}).expect(4L);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{1, 3, 3}).expect(4L);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{4, -2, -3, 4, 1}).expect(59L);

}
