package q1700;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import q1350.Q1340_JumpGameV;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1696. Jump Game VI
 * https://leetcode.com/problems/jump-game-vi/
 *
 * You are given a 0-indexed integer array nums and an integer k.
 *
 * You are initially standing at index 0. In one move, you can jump at most k steps forward without going outside the
 * boundaries of the array. That is, you can jump from index i to any index in the range [i + 1, min(n - 1, i + k)]
 * inclusive.
 *
 * You want to reach the last index of the array (index n - 1). Your score is the sum of all nums[j] for each index j
 * you visited in the array.
 *
 * Return the maximum score you can get.
 *
 * Example 1:
 *
 * Input: nums = [1,-1,-2,4,-7,3], k = 2
 * Output: 7
 * Explanation: You can choose your jumps forming the subsequence [1,-1,4,3] (underlined above). The sum is 7.
 *
 * Example 2:
 *
 * Input: nums = [10,-5,-2,4,0,3], k = 3
 * Output: 17
 * Explanation: You can choose your jumps forming the subsequence [10,4,3] (underlined above). The sum is 17.
 *
 * Example 3:
 *
 * Input: nums = [1,-5,-20,4,-1,3,-6,-3], k = 2
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= nums.length, k <= 10^5
 * -10^4 <= nums[i] <= 10^4
 *
 * 上一题 {@link Q1340_JumpGameV}
 */
@RunWith(LeetCodeRunner.class)
public class Q1696_JumpGameVI {

    /**
     * 递减队列的题
     */
    @Answer
    public int maxResult(int[] nums, int k) {
        final int n = nums.length;
        int[] dp = new int[n];
        dp[0] = nums[0];
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);
        for (int i = 1; i < n; i++) {
            while (queue.peek() + k < i) {
                queue.poll();
            }
            dp[i] = dp[queue.peek()] + nums[i];
            while (!queue.isEmpty() && dp[queue.peekLast()] <= dp[i]) {
                queue.pollLast();
            }
            queue.offer(i);
        }
        return dp[n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, -1, -2, 4, -7, 3}, 2)
            .expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{10, -5, -2, 4, 0, 3}, 3)
            .expect(17);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, -5, -20, 4, -1, 3, -6, -3}, 2)
            .expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(int[].class), 90000)
            .expect(212070827);

}
