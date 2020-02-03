package q250;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sliding-window-maximum/
 *
 * Given an array nums, there is a sliding window of size k which is moving from the very left of the array to the
 * very right. You can only see the k numbers in the window. Each time the sliding window moves right by one position
 * . Return the max sliding window.
 *
 * Example:
 *
 * Input: nums = [1,3,-1,-3,5,3,6,7], and k = 3
 * Output: [3,3,5,5,6,7]
 * Explanation:
 *
 * >  Window position                Max
 * >  ---------------               -----
 * >  [1  3  -1] -3  5  3  6  7       3
 * >   1 [3  -1  -3] 5  3  6  7       3
 * >   1  3 [-1  -3  5] 3  6  7       5
 * >   1  3  -1 [-3  5  3] 6  7       5
 * >   1  3  -1  -3 [5  3  6] 7       6
 * >   1  3  -1  -3  5 [3  6  7]      7
 *
 * Note:
 * You may assume k is always valid, 1 ≤ k ≤ input array's size for non-empty array.
 *
 * Follow up:
 * Could you solve it in linear time?
 *
 * 题解: 求解 k 窗口中的最大值, 要求 O(n) 的时间复杂度.
 */
@RunWith(LeetCodeRunner.class)
public class Q239_SlidingWindowMaximum {

    // O(nlogn) 的解法, 使用优先队列
    @Answer
    public int[] maxSlidingWindow(int[] nums, int k) {
        if (nums.length == 0) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        for (int i = 0; i < nums.length; i++) {
            pq.add(nums[i]);
            if (i >= k - 1) {
                res[i - k + 1] = pq.peek();
                pq.remove(nums[i - k + 1]);
            }
        }
        return res;
    }

    /**
     * 根据LeetCode 得到的提醒:
     * 使用双端队列保存最大值, 这样在最大值前面小于这个最大值的数字就不是最大值了, 移除的时候判断一下最大值是否等于要移除的值.
     * 这个双端队列是从前往后递减的, 确保当最大的过界之后, 下一个就是最小的.
     */
    @Answer
    public int[] linerTime(int[] nums, int k) {
        if (nums.length == 0) {
            return new int[0];
        }
        int[] res = new int[nums.length - k + 1];
        Deque<Integer> queue = new ArrayDeque<>(k);
        for (int i = 0; i < nums.length; i++) {
            while (!queue.isEmpty() && queue.getLast() < nums[i]) {
                queue.removeLast();
            }
            queue.addLast(nums[i]);
            if (i >= k - 1) {
                res[i - k + 1] = queue.getFirst();
                if (nums[i - k + 1] == queue.getFirst()) {
                    queue.removeFirst();
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{1, 3, -1, -3, 5, 3, 6, 7}, 3)
            .expect(new int[]{3, 3, 5, 5, 6, 7});

    // 题设中只是对非空数组假设 k>=1, LeetCode 还是有空数组这个测试用例的.
    @TestData
    public DataExpectation border = DataExpectation.createWith(new int[0], 0).expect(new int[0]);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, 3, 1, 2, 0, 5}, 3)
            .expect(new int[]{3, 3, 2, 5});

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{1, 3, 1, 2, 0, 5}, 1)
            .expect(new int[]{1, 3, 1, 2, 0, 5});

}
