package q1450;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1425. Constrained Subsequence Sum
 * https://leetcode.com/problems/constrained-subsequence-sum/
 *
 * Given an integer array nums and an integer k, return the maximum sum of a non-empty subsequence of that array such
 * that for every two consecutive integers in the subsequence, nums[i] and nums[j], where i < j, the condition j - i <=
 * k is satisfied.
 *
 * A subsequence of an array is obtained by deleting some number of elements (can be zero) from the array, leaving the
 * remaining elements in their original order.
 *
 * Example 1:
 *
 * Input: nums = [10,2,-10,5,20], k = 2
 * Output: 37
 * Explanation: The subsequence is [10, 2, 5, 20].
 *
 * Example 2:
 *
 * Input: nums = [-1,-2,-3], k = 1
 * Output: -1
 * Explanation: The subsequence must be non-empty, so we choose the largest number.
 *
 * Example 3:
 *
 * Input: nums = [10,-2,-10,-5,20], k = 2
 * Output: 23
 * Explanation: The subsequence is [10, -2, -5, 20].
 *
 * Constraints:
 *
 * 1 <= k <= nums.length <= 10^5
 * -10^4 <= nums[i] <= 10^4
 *
 * 题解: 要从nums 中每隔不超过k 距离选择1 个元素, 求这些元素的和的最大值.
 */
@RunWith(LeetCodeRunner.class)
public class Q1425_ConstrainedSubsequenceSum {

    /**
     * 时间复杂度 O(NlogK)
     */
    @Answer
    public int constrainedSubsetSum(int[] nums, int k) {
        // 遍历到 nums[0:i] 时结果和下标的映射关系
        TreeMap<Integer, Integer> sumIndex = new TreeMap<>();
        // 遍历到 nums[0:i] 时下标和结果的值映射关系
        Map<Integer, Integer> indexSum = new HashMap<>();
        int res = nums[0];
        sumIndex.put(nums[0], 0);
        indexSum.put(0, nums[0]);
        for (int i = 1; i < nums.length; i++) {
            // 前k 个最大值(或者没有)和当前值相加
            int prevMax = sumIndex.floorKey(Integer.MAX_VALUE);
            int sum = nums[i] + Math.max(0, prevMax);
            res = Math.max(res, sum);

            sumIndex.put(sum, i);
            indexSum.put(i, sum);
            if (i >= k) {
                // 删除前面超过范围的值
                int prevSum = indexSum.remove(i - k);
                if (sumIndex.get(prevSum) == i - k) {
                    sumIndex.remove(prevSum);
                }
            }
        }
        return res;
    }

    /**
     * leetcode 上的另一种做法, 时间复杂度 O(N).
     */
    @Answer
    public int constrainedSubsetSum2(int[] nums, int k) {
        // 单调递增栈, 由于还需要删除超过范围的元素所以是双端队列
        Deque<int[]> dequeue = new ArrayDeque<>();
        int res = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            if (!dequeue.isEmpty()) {
                sum += dequeue.peekFirst()[1];
            }
            res = Math.max(res, sum);
            // 加入队列
            if (sum > 0) {
                // 前面的值小于当前的值, 为了保持单调递增
                while (!dequeue.isEmpty()
                        && dequeue.peekLast()[1] < sum) {
                    dequeue.pollLast();
                }
                dequeue.offerLast(new int[]{i, sum});
            }
            // 删除超过范围的值
            if (!dequeue.isEmpty()
                    && dequeue.peekFirst()[0] == i - k) {
                dequeue.pollFirst();
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{10, 2, -10, 5, 20}, 2).expect(37);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{-1, -2, -3}, 1).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{10, -2, -10, -5, 20}, 2).expect(23);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{-5266, 4019, 7336, -3681, -5767}, 2)
            .expect(11355);

}
