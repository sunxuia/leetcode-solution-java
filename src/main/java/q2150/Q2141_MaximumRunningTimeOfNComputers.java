package q2150;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * <h3>[Hard] 2141. Maximum Running Time of N Computers</h3>
 * <a href="https://leetcode.com/problems/maximum-running-time-of-n-computers/">
 * https://leetcode.com/problems/maximum-running-time-of-n-computers/
 * </a><br/>
 *
 * <p>You have <code>n</code> computers. You are given the integer <code>n</code> and a <strong>0-indexed</strong>
 * integer array <code>batteries</code> where the <code>i<sup>th</sup></code> battery can <strong>run</strong> a
 * computer for <code>batteries[i]</code> minutes. You are interested in running <strong>all</strong> <code>n</code>
 * computers <strong>simultaneously</strong> using the given batteries.</p>
 *
 * <p>Initially, you can insert <strong>at most one battery</strong> into each computer. After that and at any integer
 * time moment, you can remove a battery from a computer and insert another battery <strong>any number of
 * times</strong>. The inserted battery can be a totally new battery or a battery from another computer. You may assume
 * that the removing and inserting processes take no time.</p>
 *
 * <p>Note that the batteries cannot be recharged.</p>
 *
 * <p>Return <em>the <strong>maximum</strong> number of minutes you can run all the </em><code>n</code><em> computers
 * simultaneously.</em></p>
 *
 * <p>&nbsp;</p>
 * <p><strong class="example">Example 1:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/01/06/example1-fit.png" style="width: 762px; height:
 * 150px;" />
 * <pre>
 * <strong>Input:</strong> n = 2, batteries = [3,3,3]
 * <strong>Output:</strong> 4
 * <strong>Explanation:</strong>
 * Initially, insert battery 0 into the first computer and battery 1 into the second computer.
 * After two minutes, remove battery 1 from the second computer and insert battery 2 instead. Note that battery 1 can still run for one minute.
 * At the end of the third minute, battery 0 is drained, and you need to remove it from the first computer and insert battery 1 instead.
 * By the end of the fourth minute, battery 1 is also drained, and the first computer is no longer running.
 * We can run the two computers simultaneously for at most 4 minutes, so we return 4.
 *
 * </pre>
 *
 * <p><strong class="example">Example 2:</strong></p>
 * <img alt="" src="https://assets.leetcode.com/uploads/2022/01/06/example2.png" style="width: 629px; height: 150px;"
 * />
 * <pre>
 * <strong>Input:</strong> n = 2, batteries = [1,1,1,1]
 * <strong>Output:</strong> 2
 * <strong>Explanation:</strong>
 * Initially, insert battery 0 into the first computer and battery 2 into the second computer.
 * After one minute, battery 0 and battery 2 are drained so you need to remove them and insert battery 1 into the first computer and battery 3 into the second computer.
 * After another minute, battery 1 and battery 3 are also drained so the first and second computers are no longer running.
 * We can run the two computers simultaneously for at most 2 minutes, so we return 2.
 * </pre>
 *
 * <p>&nbsp;</p>
 * <p><strong>Constraints:</strong></p>
 *
 * <ul>
 * 	<li><code>1 &lt;= n &lt;= batteries.length &lt;= 10<sup>5</sup></code></li>
 * 	<li><code>1 &lt;= batteries[i] &lt;= 10<sup>9</sup></code></li>
 * </ul>
 */
@RunWith(LeetCodeRunner.class)
public class Q2141_MaximumRunningTimeOfNComputers {

    /**
     * 根据提示, 对结果进行二分查找, 通过贪婪匹配的方式验证是否可行.
     * 时间复杂度 O(mlogm)
     */
    @Answer
    public long maxRunTime(int n, int[] batteries) {
        final int m = batteries.length;
        // (最耗时的操作)
        Arrays.sort(batteries);
        long[] sums = new long[m + 1];
        for (int i = 0; i < m; i++) {
            sums[i + 1] = sums[i] + batteries[i];
        }

        long min = 1L, max = sums[m] / n;
        while (min < max) {
            long runTime = (min + max + 1) / 2;
            // 首先找出能覆盖这个时间的电池(这个电池将从头到尾仅为一台电脑服务)
            // 这个电池数量就是 m - index, 如果该数量 >= n 则已经满足要求,
            // 否则需要从 < expect 的电池中为剩下的 (n - (m-index)) 个电脑组合供电,
            // 因为剩下的都 < expect, 可以完全耗尽, 只要剩下的电池总时数大于总需求数量就能满足要求.
            int index = binarySearch(batteries, runTime);
            boolean available = m - index >= n
                    || sums[index] >= (n - m + index) * runTime;
            if (available) {
                min = runTime;
            } else {
                max = runTime - 1;
            }
        }
        return max;
    }

    // 找出 >= target 的第一个元素下标
    private int binarySearch(int[] nums, long target) {
        int start = 0, end = nums.length;
        while (start < end) {
            int mid = (start + end) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else {
                end = mid;
            }
        }
        return end;
    }

    /**
     * leetcode 上比较快的解法.
     */
    @Answer
    public long maxRunTime2(int n, int[] batteries) {
        long min = 0, max = 0;
        long sum = 0;
        for (int battery : batteries) {
            sum += battery;
            min = Math.min(battery, min);
            max = Math.max(battery, max);
        }

        // 剪枝操作, runTime >= max, 则可以完全分配
        if (sum >= (long) n * max) {
            return sum / n;
        }

        while (min < max) {
            long runTime = (min + max + 1) / 2;

            long total = 0, left = n;
            for (int battery : batteries) {
                if (battery >= runTime) {
                    left--;
                } else {
                    total += battery;
                }
                if (total >= runTime * n) {
                    // 电池总时数已经足够供电runTime 了
                    break;
                }
            }
            if (total >= runTime * left) {
                min = runTime;
            } else {
                max = runTime - 1;
            }
        }
        return min;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, new int[]{3, 3, 3}).expect(4L);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, new int[]{1, 1, 1, 1}).expect(2L);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(3, new int[]{10, 10, 3, 5}).expect(8L);

}
