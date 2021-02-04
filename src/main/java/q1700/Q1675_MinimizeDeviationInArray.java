package q1700;

import java.util.PriorityQueue;
import java.util.TreeSet;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1675. Minimize Deviation in Array
 * https://leetcode.com/problems/minimize-deviation-in-array/
 *
 * You are given an array nums of n positive integers.
 *
 * You can perform two types of operations on any element of the array any number of times:
 *
 * If the element is even, divide it by 2.
 *
 * For example, if the array is [1,2,3,4], then you can do this operation on the last element, and the array will be
 * [1,2,3,2].
 *
 *
 * If the element is odd, multiply it by 2.
 *
 * For example, if the array is [1,2,3,4], then you can do this operation on the first element, and the array will be
 * [2,2,3,4].
 *
 *
 *
 * The deviation of the array is the maximum difference between any two elements in the array.
 *
 * Return the minimum deviation the array can have after performing some number of operations.
 *
 * Example 1:
 *
 * Input: nums = [1,2,3,4]
 * Output: 1
 * Explanation: You can transform the array to [1,2,3,2], then to [2,2,3,2], then the deviation will be 3 - 2 = 1.
 *
 * Example 2:
 *
 * Input: nums = [4,1,5,20,3]
 * Output: 3
 * Explanation: You can transform the array after two operations to [4,2,5,5,3], then the deviation will be 5 - 2 = 3.
 *
 * Example 3:
 *
 * Input: nums = [2,10,8]
 * Output: 3
 *
 * Constraints:
 *
 * n == nums.length
 * 2 <= n <= 10^5
 * 1 <= nums[i] <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1675_MinimizeDeviationInArray {

    /**
     * 因为偶数只能缩小, 奇数只能扩大, 所以先将所有数字变成偶数,
     * 然后把最大的数字 /2, 计算可能的最小结果, 直到最大的数字是奇数为止.
     * (反过来使用奇数, 因为偶数/2 可能还是偶数, 无法还原为原来的数字, 所以不行).
     * 时间复杂度 O(NlogN)
     */
    @Answer
    public int minimumDeviation(int[] nums) {
        TreeSet<Integer> set = new TreeSet<>();
        for (int num : nums) {
            while (num % 2 == 1) {
                num *= 2;
            }
            set.add(num);
        }

        int res = Integer.MAX_VALUE;
        for (int max = set.last();
                max % 2 == 0;
                max = set.last()) {
            int min = set.first();
            res = Math.min(res, max - min);
            set.remove(max);
            set.add(max / 2);
        }
        res = Math.min(res, set.last() - set.first());
        return res;
    }

    /**
     * LeetCode 中比较快的解法, 思路与上面类似, 使用优先队列来做.
     */
    @Answer
    public int minimumDeviation2(int[] nums) {
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        int min = Integer.MAX_VALUE;
        for (int num : nums) {
            if (num % 2 == 1) {
                num *= 2;
            }
            pq.offer(num);
            min = Math.min(min, num);
        }

        int res = Integer.MAX_VALUE, max = pq.poll();
        for (; max % 2 == 0; max = pq.poll()) {
            res = Math.min(res, max - min);
            min = Math.min(min, max / 2);
            pq.offer(max / 2);
        }
        res = Math.min(res, max - min);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[]{1, 2, 3, 4}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[]{4, 1, 5, 20, 3}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[]{2, 10, 8}).expect(3);

}
