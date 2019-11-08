package q250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/minimum-size-subarray-sum
 *
 * Given an array of n positive integers and a positive integer s, find the minimal length of a contiguous subarray
 * of which the sum ≥ s. If there isn't one, return 0 instead.
 *
 * Example:
 *
 * Input: s = 7, nums = [2,3,1,2,4,3]
 * Output: 2
 * Explanation: the subarray [4,3] has the minimal length under the problem constraint.
 *
 * Follow up:
 * If you have figured out the O(n) solution, try coding another solution of which the time complexity is O(n log n).
 */
@RunWith(LeetCodeRunner.class)
public class Q209_MinimumSizeSubarraySum {

    // O(n) 的解法
    @Answer
    public int minSubArrayLen(int s, int[] nums) {
        int res = Integer.MAX_VALUE;
        for (int i = 0, start = 0, sum = 0; i < nums.length; i++) {
            sum += nums[i];
            if (sum >= s) {
                while (sum >= s) {
                    sum -= nums[start++];
                }
                res = Math.min(res, i - start + 2);
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    // O(nlogn) 的解法: 累加后的结果肯定是递增的, 通过二分查找找出匹配的值.
    @Answer
    public int minSubArrayLen2(int s, int[] nums) {
        final int n = nums.length;
        int[] sum = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + nums[i];
        }
        int res = Integer.MAX_VALUE;
        for (int i = 1; i <= n; i++) {
            // 在循环中进行二分查找
            int target = s + sum[i - 1];
            if (sum[n] >= target) {
                int start = i, end = n;
                while (start < end) {
                    int middle = (start + end) / 2;
                    if (sum[middle] < target) {
                        start = middle + 1;
                    } else {
                        end = middle;
                    }
                }
                res = Math.min(res, start - i + 1);
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(7, new int[]{2, 3, 1, 2, 4, 3}).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(7, new int[]{7, 1, 2, 3, 4, 5, 6}).expect(1);

    @TestData
    public DataExpectation border = DataExpectation.createWith(100, new int[0]).expect(0);

}
