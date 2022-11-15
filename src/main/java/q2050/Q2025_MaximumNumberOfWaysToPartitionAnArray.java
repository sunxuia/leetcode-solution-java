package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 2025. Maximum Number of Ways to Partition an Array
 * https://leetcode.com/problems/maximum-number-of-ways-to-partition-an-array/
 *
 * You are given a 0-indexed integer array nums of length n. The number of ways to partition nums is the number of pivot
 * indices that satisfy both conditions:
 *
 * 1 <= pivot < n
 * nums[0] + nums[1] + ... + nums[pivot - 1] == nums[pivot] + nums[pivot + 1] + ... + nums[n - 1]
 *
 * You are also given an integer k. You can choose to change the value of one element of nums to k, or to leave the
 * array unchanged.
 *
 * Return the maximum possible number of ways to partition nums to satisfy both conditions after changing at most one
 * element.
 *
 * Example 1:
 *
 * Input: nums = [2,-1,2], k = 3
 * Output: 1
 * Explanation: One optimal approach is to change nums[0] to k. The array becomes [3,-1,2].
 * There is one way to partition the array:
 * - For pivot = 2, we have the partition [3,-1 | 2]: 3 + -1 == 2.
 *
 * Example 2:
 *
 * Input: nums = [0,0,0], k = 1
 * Output: 2
 * Explanation: The optimal approach is to leave the array unchanged.
 * There are two ways to partition the array:
 * - For pivot = 1, we have the partition [0 | 0,0]: 0 == 0 + 0.
 * - For pivot = 2, we have the partition [0,0 | 0]: 0 + 0 == 0.
 *
 * Example 3:
 *
 * Input: nums = [22,4,-25,-20,-15,15,-16,7,19,-10,0,-13,-14], k = -33
 * Output: 4
 * Explanation: One optimal approach is to change nums[2] to k. The array becomes
 * [22,4,-33,-20,-15,15,-16,7,19,-10,0,-13,-14].
 * There are four ways to partition the array.
 *
 * Constraints:
 *
 * n == nums.length
 * 2 <= n <= 10^5
 * -10^5 <= k, nums[i] <= 10^5
 *
 * 题解: 题目问的是, 在最多改变一个元素的前提下, 有多少种分割数组的方式, 求的是分割方式的最大值.
 */
@RunWith(LeetCodeRunner.class)
public class Q2025_MaximumNumberOfWaysToPartitionAnArray {

    /**
     * 这题是个转换视角的题.
     */
    @Answer
    public int waysToPartition(int[] nums, int k) {
        final int n = nums.length;
        long[] sums = new long[n + 1];
        int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE;
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
            min = Math.min(min, k - nums[i]);
            max = Math.max(max, k - nums[i]);
        }
        // left[o] 和 right[o] 表示k 与左右数字的差值对应的数量
        final int len = max - min + 1;
        int[] left = new int[len];
        int[] right = new int[len];
        for (int i = 0; i < n; i++) {
            right[k - nums[i] - min]++;
        }

        // 不需要改变元素的情况下的结果
        int zero = 0;
        // 在改变1 个元素(左边/右边)的情况下的结果
        // 如果nums 上多个点有相同的 k-nums[i] 差值, 最左(leftCount)和最右(rightCount)的点是最多的.
        int[] leftCount = new int[len];
        int[] rightCount = new int[len];
        for (int p = 1; p < n; p++) {
            // nums[i-1] 从右边移动到左边
            int idx = k - nums[p - 1] - min;
            left[idx]++;
            right[idx]--;
            // 点从右边移动到左边了, 因此对应计数取大值
            leftCount[idx] = Math.max(leftCount[idx], rightCount[idx]);
            // 右边-左边的差值
            long offset = (sums[n] - sums[p]) - sums[p];
            if (offset == 0) {
                zero++;
                continue;
            }
            if (min <= offset && offset <= max && left[(int) (offset - min)] > 0) {
                leftCount[(int) (offset - min)]++;
            }
            if (min <= -offset && -offset <= max && right[(int) (-offset - min)] > 0) {
                rightCount[(int) (-offset - min)]++;
            }
        }

        // 找出最大值
        int res = Math.max(zero, rightCount[k - nums[n - 1] - min]);
        for (int count : leftCount) {
            res = Math.max(res, count);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{2, -1, 2}, 3)
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{0, 0, 0}, 1)
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{22, 4, -25, -20, -15, 15, -16, 7, 19, -10, 0, -13, -14}, -33)
            .expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 30827,
                    0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}, 0)
            .expect(33);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{
                    0, 0, 0, 0, 0, 0, 0, 0, 0, -4732, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                    0, 0, 0, 0}, -4732)
            .expect(26);

    @TestData
    public DataExpectation overtime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 0)
            .expect(0);

}
