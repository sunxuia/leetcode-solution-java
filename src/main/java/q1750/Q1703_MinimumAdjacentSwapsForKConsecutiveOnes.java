package q1750;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1703. Minimum Adjacent Swaps for K Consecutive Ones
 * https://leetcode.com/problems/minimum-adjacent-swaps-for-k-consecutive-ones/
 *
 * You are given an integer array, nums, and an integer k. nums comprises of only 0's and 1's. In one move, you can
 * choose two adjacent indices and swap their values.
 *
 * Return the minimum number of moves required so that nums has k consecutive 1's.
 *
 * Example 1:
 *
 * Input: nums = [1,0,0,1,0,1], k = 2
 * Output: 1
 * Explanation: In 1 move, nums could be [1,0,0,0,1,1] and have 2 consecutive 1's.
 *
 * Example 2:
 *
 * Input: nums = [1,0,0,0,0,0,1,1], k = 3
 * Output: 5
 * Explanation: In 5 moves, the leftmost 1 can be shifted right until nums = [0,0,0,0,0,1,1,1].
 *
 * Example 3:
 *
 * Input: nums = [1,1,0,1], k = 2
 * Output: 0
 * Explanation: nums already has 2 consecutive 1's.
 *
 * Constraints:
 *
 * 1 <= nums.length <= 10^5
 * nums[i] is 0 or 1.
 * 1 <= k <= sum(nums)
 */
@RunWith(LeetCodeRunner.class)
public class Q1703_MinimumAdjacentSwapsForKConsecutiveOnes {

    /**
     * 滑动窗口.
     * 这题计算移动距离的方法为: 对 1 的和为 k 的连续区间内每个 0 左边/右边的区间内的1 计数,
     * 取较小值就是将这个 0 移动到区间边上的距离, 相对的, 1 就集中在区间中间了.
     */
    @Answer
    public int minMoves(int[] nums, int k) {
        final int n = nums.length, half = k / 2;
        // 表示 1 左边的与相邻 1 之间的 0 的数量, 方便下面的遍历
        List<Integer> zeros = new ArrayList<>();
        for (int i = 0, zero = 0; i < n; i++) {
            if (nums[i] == 0) {
                zero++;
            } else {
                zeros.add(zero);
                zero = 0;
            }
        }

        // 计算 [0, k-1] 之间需要移动的次数.
        int left = 0, leftCount = 0;
        for (int i = 1; i <= half; i++) {
            left += zeros.get(i) * i;
            leftCount += zeros.get(i);
        }
        int right = 0, rightCount = 0;
        for (int i = half + 1; i < k; i++) {
            right += zeros.get(i) * (k - i);
            rightCount += zeros.get(i);
        }

        // 移动区间
        int res = left + right, mid = half + 1;
        for (int i = k; i < zeros.size(); i++, mid++) {
            // 减去左边移出的值(剩余整体 -1 ), 加上中间移入的值
            left -= leftCount;
            leftCount -= zeros.get(i - k + 1);
            left += zeros.get(mid) * half;
            leftCount += zeros.get(mid);

            // 减去中间移出的值(剩余整体 +1 ), 加上右边移入的值
            right -= zeros.get(mid) * (k - half - 1);
            rightCount -= zeros.get(mid);
            rightCount += zeros.get(i);
            right += rightCount;

            res = Math.min(res, left + right);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 0, 0, 1, 0, 1}, 2)
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 0, 0, 0, 0, 0, 1, 1}, 3)
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 1, 0, 1}, 2)
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1}, 7)
            .expect(6);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{0, 1, 0, 1, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0},
                    12)
            .expect(52);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 0}, 7)
            .expect(4);

    @TestData
    public DataExpectation normal4 = DataExpectation
            .createWith(new int[]{1, 0, 1, 1, 0, 1, 1}, 4)
            .expect(2);

    @TestData
    public DataExpectation normal5 = DataExpectation
            .createWith(new int[]{0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 0, 0, 0, 1, 1, 0,
                    1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 1, 0, 0}, 26)
            .expect(119);

    @TestData
    public DataExpectation overTime() {
        int[] nums = new int[10_0000];
        for (int i = 1; i < nums.length; i += 2) {
            nums[i] = 1;
        }
        return DataExpectation.createWith(nums, 25000).expect(156250000);
    }

}
