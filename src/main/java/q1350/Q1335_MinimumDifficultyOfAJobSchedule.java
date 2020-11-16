package q1350;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1335. Minimum Difficulty of a Job Schedule
 * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/
 *
 * You want to schedule a list of jobs in d days. Jobs are dependent (i.e To work on the i-th job, you have to finish
 * all the jobs j where 0 <= j < i).
 *
 * You have to finish at least one task every day. The difficulty of a job schedule is the sum of difficulties of each
 * day of the d days. The difficulty of a day is the maximum difficulty of a job done in that day.
 *
 * Given an array of integers jobDifficulty and an integer d. The difficulty of the i-th job is jobDifficulty[i].
 *
 * Return the minimum difficulty of a job schedule. If you cannot find a schedule for the jobs return -1.
 *
 * Example 1:
 * <img src="./Q1335_PIC.png">
 * Input: jobDifficulty = [6,5,4,3,2,1], d = 2
 * Output: 7
 * Explanation: First day you can finish the first 5 jobs, total difficulty = 6.
 * Second day you can finish the last job, total difficulty = 1.
 * The difficulty of the schedule = 6 + 1 = 7
 *
 * Example 2:
 *
 * Input: jobDifficulty = [9,9,9], d = 4
 * Output: -1
 * Explanation: If you finish a job per day you will still have a free day. you cannot find a schedule for the given
 * jobs.
 *
 * Example 3:
 *
 * Input: jobDifficulty = [1,1,1], d = 3
 * Output: 3
 * Explanation: The schedule is one job per day. total difficulty will be 3.
 *
 * Example 4:
 *
 * Input: jobDifficulty = [7,1,7,1,7,1], d = 3
 * Output: 15
 *
 * Example 5:
 *
 * Input: jobDifficulty = [11,111,22,222,33,333,44,444], d = 6
 * Output: 843
 *
 * Constraints:
 *
 * 1 <= jobDifficulty.length <= 300
 * 0 <= jobDifficulty[i] <= 1000
 * 1 <= d <= 10
 */
@RunWith(LeetCodeRunner.class)
public class Q1335_MinimumDifficultyOfAJobSchedule {

    /**
     * dp, 时间复杂度 O(N*N*D), 空间复杂度 O(N*D)
     * LeetCode 上有空间复杂度化简为 O(N) 的解法, 是这个算法的优化.
     */
    @Answer
    public int minDifficulty(int[] jobDifficulty, int d) {
        final int n = jobDifficulty.length;
        if (n < d) {
            return -1;
        }
        // dp[i][j] 表示第i 天完成第j 个任务的结果 (从1 开始)
        int[][] dp = new int[d + 1][n + 1];
        for (int i = 0; i <= d; i++) {
            Arrays.fill(dp[i], 10000);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= d; i++) {
            for (int j = i; j <= n; j++) {
                int difficulty = 0;
                // 第i 天完成 [k:j] 的工作, 因此结果就是前一天的结果 + 今天的最大难度
                for (int k = j; k >= 1; k--) {
                    difficulty = Math.max(difficulty, jobDifficulty[k - 1]);
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][k - 1] + difficulty);
                }
            }
        }
        return dp[d][n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{6, 5, 4, 3, 2, 1}, 2).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{9, 9, 9}, 4).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 1, 1}, 3).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(new int[]{7, 1, 7, 1, 7, 1}, 3).expect(15);

    @TestData
    public DataExpectation example5 = DataExpectation
            .createWith(new int[]{11, 111, 22, 222, 33, 333, 44, 444}, 6).expect(843);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{186, 398, 479, 206, 885, 423, 805, 112, 925, 656, 16, 932, 740, 292, 671, 360}, 4)
            .expect(1803);

}
