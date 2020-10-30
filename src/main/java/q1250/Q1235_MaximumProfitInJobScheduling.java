package q1250;

import java.util.Arrays;
import java.util.TreeMap;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1235. Maximum Profit in Job Scheduling
 * https://leetcode.com/problems/maximum-profit-in-job-scheduling/
 *
 * We have n jobs, where every job is scheduled to be done from startTime[i] to endTime[i], obtaining a profit of
 * profit[i].
 *
 * You're given the startTime , endTime and profit arrays, you need to output the maximum profit you can take such that
 * there are no 2 jobs in the subset with overlapping time range.
 *
 * If you choose a job that ends at time X you will be able to start another job that starts at time X.
 *
 * Example 1:
 * <img src="Q1235_PIC1.png">
 * Input: startTime = [1,2,3,3], endTime = [3,4,5,6], profit = [50,10,40,70]
 * Output: 120
 * Explanation: The subset chosen is the first and fourth job.
 * Time range [1-3]+[3-6] , we get profit of 120 = 50 + 70.
 *
 * Example 2:
 * <img src="Q1235_PIC2.png">
 * Input: startTime = [1,2,3,4,6], endTime = [3,5,10,6,9], profit = [20,20,100,70,60]
 * Output: 150
 * Explanation: The subset chosen is the first, fourth and fifth job.
 * Profit obtained 150 = 20 + 70 + 60.
 *
 * Example 3:
 * <img src="Q1235_PIC3.png">
 * Input: startTime = [1,1,1], endTime = [2,3,4], profit = [5,6,4]
 * Output: 6
 *
 * Constraints:
 *
 * 1 <= startTime.length == endTime.length == profit.length <= 5 * 10^4
 * 1 <= startTime[i] < endTime[i] <= 10^9
 * 1 <= profit[i] <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1235_MaximumProfitInJobScheduling {

    /**
     * 使用TreeMap 的做法, 时间复杂度 O(NlogN)
     */
    @Answer
    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        final int n = startTime.length;
        Schedule[] schedules = new Schedule[n];
        for (int i = 0; i < n; i++) {
            schedules[i] = new Schedule(startTime[i], endTime[i], profit[i]);
        }
        // 按照结束时间排序
        Arrays.sort(schedules, (a, b) -> a.endTime == b.endTime
                ? a.startTime - b.startTime : a.endTime - b.endTime);

        // 表示某个时刻结束时的最大盈利
        TreeMap<Integer, Integer> profits = new TreeMap<>();
        profits.put(0, 0);
        int res = 0;
        for (Schedule sch : schedules) {
            // 此刻使用sch 可以得到的最大盈利
            int pft = profits.floorEntry(sch.startTime).getValue() + sch.profit;
            if (pft > res) {
                profits.put(sch.endTime, pft);
                res = pft;
            }
        }
        return res;
    }

    private static class Schedule {

        final int startTime, endTime, profit;

        private Schedule(int startTime, int endTime, int profit) {
            this.startTime = startTime;
            this.endTime = endTime;
            this.profit = profit;
        }

        @Override
        public String toString() {
            return "{" +
                    "startTime=" + startTime +
                    ", endTime=" + endTime +
                    ", profit=" + profit +
                    '}';
        }
    }

    /**
     * 参考LeetCode 改进而来的DP解法, 使用数组而不是 TreeMap 来保存结果.
     * 时间复杂度 O(n^2), 这个在LeetCode 中比上面的解法更快.
     */
    @Answer
    public int jobScheduling2(int[] startTime, int[] endTime, int[] profit) {
        final int n = startTime.length;
        Schedule[] schedules = new Schedule[n];
        for (int i = 0; i < n; i++) {
            schedules[i] = new Schedule(startTime[i], endTime[i], profit[i]);
        }
        // 按照结束时间排序
        Arrays.sort(schedules, (a, b) -> a.endTime == b.endTime
                ? a.startTime - b.startTime : a.endTime - b.endTime);

        // dp[i] 表示使用 [0, i]项目时的最大利润.
        int[] dp = new int[n];
        dp[0] = schedules[0].profit;
        for (int i = 1; i < n; i++) {
            // schedules[i].profit 表示前面没有项目, 从这个项目开始
            // dp[i-1] 表示不使用当前项目, 使用 [0, i-1] 中选择的项目.
            dp[i] = Math.max(schedules[i].profit, dp[i - 1]);
            // 使用当前项目并承接前面项目的情况.
            for (int j = i - 1; j >= 0; j--) {
                if (schedules[j].endTime <= schedules[i].startTime) {
                    dp[i] = Math.max(dp[i], schedules[i].profit + dp[j]);
                    break;
                }
            }
        }
        return dp[n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 2, 3, 3}, new int[]{3, 4, 5, 6}, new int[]{50, 10, 40, 70})
            .expect(120);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{1, 2, 3, 4, 6}, new int[]{3, 5, 10, 6, 9}, new int[]{20, 20, 100, 70, 60})
            .expect(150);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 1, 1}, new int[]{2, 3, 4}, new int[]{5, 6, 4})
            .expect(6);

}
