package q1350;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1326. Minimum Number of Taps to Open to Water a Garden
 * https://leetcode.com/problems/minimum-number-of-taps-to-open-to-water-a-garden/
 *
 * There is a one-dimensional garden on the x-axis. The garden starts at the point 0 and ends at the point n. (i.e The
 * length of the garden is n).
 *
 * There are n + 1 taps located at points [0, 1, ..., n] in the garden.
 *
 * Given an integer n and an integer array ranges of length n + 1 where ranges[i] (0-indexed) means the i-th tap can
 * water the area [i - ranges[i], i + ranges[i]] if it was open.
 *
 * Return the minimum number of taps that should be open to water the whole garden, If the garden cannot be watered
 * return -1.
 *
 * Example 1:
 * <img src="./Q1326_PIC.png">
 * Input: n = 5, ranges = [3,4,1,1,0,0]
 * Output: 1
 * Explanation: The tap at point 0 can cover the interval [-3,3]
 * The tap at point 1 can cover the interval [-3,5]
 * The tap at point 2 can cover the interval [1,3]
 * The tap at point 3 can cover the interval [2,4]
 * The tap at point 4 can cover the interval [4,4]
 * The tap at point 5 can cover the interval [5,5]
 * Opening Only the second tap will water the whole garden [0,5]
 *
 * Example 2:
 *
 * Input: n = 3, ranges = [0,0,0,0]
 * Output: -1
 * Explanation: Even if you activate all the four taps you cannot water the whole garden.
 *
 * Example 3:
 *
 * Input: n = 7, ranges = [1,2,1,0,2,1,0,1]
 * Output: 3
 *
 * Example 4:
 *
 * Input: n = 8, ranges = [4,0,0,0,0,0,0,0,4]
 * Output: 2
 *
 * Example 5:
 *
 * Input: n = 8, ranges = [4,0,0,0,4,0,0,0,4]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= n <= 10^4
 * ranges.length == n + 1
 * 0 <= ranges[i] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1326_MinimumNumberOfTapsToOpenToWaterAGarden {

    /**
     * 根据 hint 中的提示, 用的单调栈的方法, 时间复杂度 O(NlogN)
     */
    @Answer
    public int minTaps(int n, int[] ranges) {
        // 将每个水龙头能覆盖的范围按照结束位置进行排序
        List<Cover> covers = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            if (ranges[i] > 0) {
                covers.add(new Cover(
                        Math.max(0, i - ranges[i]),
                        Math.min(n, i + ranges[i] - 1)));
            }
        }
        Collections.sort(covers);

        // 单调栈, 按照cover.start 递增方式排列
        Stack<Cover> stack = new Stack<>();
        // 哨兵
        stack.push(new Cover(-2, -2));
        stack.push(new Cover(-1, -1));
        for (Cover cover : covers) {
            // 去掉被新范围覆盖的情况
            while (true) {
                Cover prev1 = stack.peek();
                Cover prev2 = stack.get(stack.size() - 2);
                if (cover.start <= prev1.start
                        || cover.start <= prev2.end + 1) {
                    stack.pop();
                } else {
                    break;
                }
            }
            // 加入能与前面衔接的有效的覆盖面积
            Cover prev = stack.peek();
            if (prev.end + 1 >= cover.start
                    && prev.end < n - 1
                    && prev.end < cover.end) {
                stack.push(cover);
            }
        }

        if (stack.peek().end < n - 1) {
            // 没能全部覆盖
            return -1;
        }
        return stack.size() - 2;
    }

    private static class Cover implements Comparable<Cover> {

        final int start, end;

        public Cover(int start, int end) {
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Cover o) {
            if (this.end == o.end) {
                return this.start - o.start;
            } else {
                return this.end - o.end;
            }
        }

        @Override
        public String toString() {
            return String.format("[%d, %d]", start, end);
        }
    }

    /**
     * LeetCode 上比较快的dp 的方法, 时间复杂度 O(N)
     */
    @Answer
    public int minTaps2(int n, int[] ranges) {
        // dp[i] 表示对于第i 块草坪, 某个水龙头能覆盖到的最右边的位置(不包括)
        // (就是说一个水龙头能覆盖 [i, dp[i]) 的区间)
        int[] dp = new int[n];
        for (int i = 0; i <= n; i++) {
            if (ranges[i] > 0) {
                int left = Math.max(0, i - ranges[i]);
                dp[left] = Math.max(dp[left], i + ranges[i]);
            }
        }

        // right 表示能走到的最远距离
        // curr 表示所的在水龙头起始位置
        // jumps 表示跳跃次数
        int right = 0, curr = 0, jumps = 0;
        for (int i = 0; i < n; i++) {
            right = Math.max(right, dp[i]);
            if (i == curr) {
                curr = right;
                jumps++;
            }
        }
        return curr >= n ? jumps : -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(5, new int[]{3, 4, 1, 1, 0, 0}).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(3, new int[]{0, 0, 0, 0}).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(7, new int[]{1, 2, 1, 0, 2, 1, 0, 1}).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(8, new int[]{4, 0, 0, 0, 0, 0, 0, 0, 4}).expect(2);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(8, new int[]{4, 0, 0, 0, 4, 0, 0, 0, 4}).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(9, new int[]{0, 5, 0, 3, 3, 3, 1, 4, 0, 4}).expect(2);

}
