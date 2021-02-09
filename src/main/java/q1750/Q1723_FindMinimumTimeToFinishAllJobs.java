package q1750;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1723. Find Minimum Time to Finish All Jobs
 * https://leetcode.com/problems/find-minimum-time-to-finish-all-jobs/
 *
 * You are given an integer array jobs, where jobs[i] is the amount of time it takes to complete the ith job.
 *
 * There are k workers that you can assign jobs to. Each job should be assigned to exactly one worker. The working time
 * of a worker is the sum of the time it takes to complete all jobs assigned to them. Your goal is to devise an optimal
 * assignment such that the maximum working time of any worker is minimized.
 *
 * Return the minimum possible maximum working time of any assignment.
 *
 * Example 1:
 *
 * Input: jobs = [3,2,3], k = 3
 * Output: 3
 * Explanation: By assigning each person one job, the maximum time is 3.
 *
 * Example 2:
 *
 * Input: jobs = [1,2,4,7,8], k = 2
 * Output: 11
 * Explanation: Assign the jobs the following way:
 * Worker 1: 1, 2, 8 (working time = 1 + 2 + 8 = 11)
 * Worker 2: 4, 7 (working time = 4 + 7 = 11)
 * The maximum working time is 11.
 *
 * Constraints:
 *
 * 1 <= k <= jobs.length <= 12
 * 1 <= jobs[i] <= 10^7
 */
@RunWith(LeetCodeRunner.class)
public class Q1723_FindMinimumTimeToFinishAllJobs {

    /**
     * 状态压缩, 用 mask 来表示已完成的工作.
     */
    @Answer
    public int minimumTimeRequired(int[] jobs, int k) {
        final int n = jobs.length, len = 1 << n;

        // 一个工人完成对应任务的时间
        int[] times = new int[len];
        for (int i = 0; i < n; i++) {
            times[1 << i] = jobs[i];
        }
        for (int i = 0; i < len; i++) {
            if (times[i] == 0) {
                int prev = i & (i - 1);
                times[i] = times[prev] + times[i - prev];
            }
        }

        // dp[i][mask] 表示 1-i 个工人共完成 mask 表示的工作所需要的时间.
        int[][] dp = new int[k][len];
        dp[0] = times;
        for (int i = 1; i < k; i++) {
            for (int mask = 1; mask < len; mask++) {
                dp[i][mask] = times[mask];
                // 遍历mask 的子集
                for (int sub = mask; sub != 0; sub = (sub - 1) & mask) {
                    dp[i][mask] = Math.min(dp[i][mask],
                            Math.max(dp[i - 1][sub], times[mask - sub]));
                }
            }
        }
        return dp[k - 1][len - 1];
    }

    /**
     * LeetCode 上比较快的解法, 带剪枝操作的递归.
     */
    @Answer
    public int minimumTimeRequired2(int[] jobs, int k) {
        // 排序, 首先取时间最长的任务, 可以帮助递归时的剪枝操作 (max>=min 判断)
        Arrays.sort(jobs);
        // recurse from the worker who is having maximum work load
        recurse(jobs, jobs.length - 1, new int[k]);
        return min;
    }

    // 表示最小工作时间
    private int min = Integer.MAX_VALUE;

    private void recurse(int[] jobs, int jobIndex, int[] workTimes) {
        final int k = workTimes.length;

        // 找出现有的最大工作时间
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < k; i++) {
            max = Math.max(max, workTimes[i]);
        }
        // 超过最小值
        if (max >= min) {
            return;
        }
        // 所有工作遍历结束
        if (jobIndex < 0) {
            min = Math.min(min, max);
            return;
        }

        // 遍历工人, 让某个工人承担这个工作.
        for (int i = 0; i < k; i++) {
            if (i > 0 && workTimes[i] == workTimes[i - 1]) {
                // 工作尽量给之前的工人去做 (如果两个人工作时间相同, 则让前面的人去做)
                continue;
            }
            workTimes[i] += jobs[jobIndex];
            recurse(jobs, jobIndex - 1, workTimes);
            workTimes[i] -= jobs[jobIndex];
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{3, 2, 3}, 3).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{1, 2, 4, 7, 8}, 2).expect(11);

}
