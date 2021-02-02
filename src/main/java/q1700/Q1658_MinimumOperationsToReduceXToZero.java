package q1700;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1658. Minimum Operations to Reduce X to Zero
 * https://leetcode.com/problems/minimum-operations-to-reduce-x-to-zero/
 *
 * You are given an integer array nums and an integer x. In one operation, you can either remove the leftmost or the
 * rightmost element from the array nums and subtract its value from x. Note that this modifies the array for future
 * operations.
 *
 * Return the minimum number of operations to reduce x to exactly 0 if it's possible, otherwise, return -1.
 *
 * Example 1:
 *
 * Input: nums = [1,1,4,2,3], x = 5
 * Output: 2
 * Explanation: The optimal solution is to remove the last two elements to reduce x to zero.
 *
 * Example 2:
 *
 * Input: nums = [5,6,7,8,9], x = 4
 * Output: -1
 *
 * Example 3:
 *
 * Input: nums = [3,2,20,1,1,3], x = 10
 * Output: 5
 * Explanation: The optimal solution is to remove the last three elements and the first two elements (5 operations in
 * total) to reduce x to zero.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * 1 <= nums[i] <= 10^4
 * 1 <= x <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1658_MinimumOperationsToReduceXToZero {

    @Answer
    public int minOperations(int[] nums, int x) {
        final int n = nums.length;
        int[] leftSums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            leftSums[i + 1] = leftSums[i] + nums[i];
        }
        int res = Arrays.binarySearch(leftSums, x);
        if (res < 0) {
            res = Integer.MAX_VALUE;
        }
        int rightSum = 0;
        for (int i = n - 1; i >= 0 && rightSum <= x; i--) {
            rightSum += nums[i];
            int idx = Arrays.binarySearch(leftSums, x - rightSum);
            if (0 <= idx && idx < i) {
                res = Math.min(res, idx + n - i);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }

    /**
     * LeetCode 上比较快的方法.
     * 通过求中间剩下的结果的最大长度来反向推导.
     */
    @Answer
    public int minOperations2(int[] nums, int x) {
        final int n = nums.length;
        int total = 0;
        for (int num : nums) {
            total += num;
        }
        if (x > total) {
            return -1;
        }
        if (total == x) {
            return n;
        }
        // [left, right] 表示 nums 扣掉x 夹在中间的结果.
        int left = 0, sum = 0, maxLen = 0;
        // 这个区间从 [0, 0] 开始向右移动/ 扩展.
        for (int right = 0; right < n; right++) {
            sum += nums[right];
            // 如果中间的和 > 期望的结果, 则左边向前进, 扣掉部分结果.
            while (left < right && sum > total - x) {
                sum -= nums[left];
                left++;
            }
            if (sum == total - x) {
                // 扣掉 [left, right], 左右两端的和就是 x
                maxLen = Math.max(maxLen, right - left + 1);
            }
        }
        if (maxLen == 0) {
            return -1;
        }
        return n - maxLen;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 1, 4, 2, 3}, 5).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{5, 6, 7, 8, 9}, 4).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{3, 2, 20, 1, 1, 3}, 10).expect(5);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 562401301)
            .expect(-1);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(new int[]{1, 1}, 3).expect(-1);

}
