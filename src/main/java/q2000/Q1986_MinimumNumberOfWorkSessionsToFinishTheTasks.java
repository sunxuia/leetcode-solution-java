package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1986. Minimum Number of Work Sessions to Finish the Tasks
 * https://leetcode.com/problems/minimum-number-of-work-sessions-to-finish-the-tasks/
 *
 * There are n tasks assigned to you. The task times are represented as an integer array tasks of length n, where the
 * ith task takes tasks[i] hours to finish. A work session is when you work for at most sessionTime consecutive hours
 * and then take a break.
 *
 * You should finish the given tasks in a way that satisfies the following conditions:
 *
 * - If you start a task in a work session, you must complete it in the same work session.
 * - You can start a new task immediately after finishing the previous one.
 * - You may complete the tasks in any order.
 *
 * Given tasks and sessionTime, return the minimum number of work sessions needed to finish all the tasks following the
 * conditions above.
 *
 * The tests are generated such that sessionTime is greater than or equal to the maximum element in tasks[i].
 *
 * Example 1:
 *
 * Input: tasks = [1,2,3], sessionTime = 3
 * Output: 2
 * Explanation: You can finish the tasks in two work sessions.
 * - First work session: finish the first and the second tasks in 1 + 2 = 3 hours.
 * - Second work session: finish the third task in 3 hours.
 *
 * Example 2:
 *
 * Input: tasks = [3,1,3,1,1], sessionTime = 8
 * Output: 2
 * Explanation: You can finish the tasks in two work sessions.
 * - First work session: finish all the tasks except the last one in 3 + 1 + 3 + 1 = 8 hours.
 * - Second work session: finish the last task in 1 hour.
 *
 * Example 3:
 *
 * Input: tasks = [1,2,3,4,5], sessionTime = 15
 * Output: 1
 * Explanation: You can finish all the tasks in one work session.
 *
 * Constraints:
 *
 * n == tasks.length
 * 1 <= n <= 14
 * 1 <= tasks[i] <= 10
 * max(tasks[i]) <= sessionTime <= 15
 */
@RunWith(LeetCodeRunner.class)
public class Q1986_MinimumNumberOfWorkSessionsToFinishTheTasks {

    @Answer
    public int minSessions(int[] tasks, int sessionTime) {
        final int n = tasks.length;
        final int len = 1 << n;
        int[] sessions = new int[len];
        int[] remains = new int[len];
        for (int curr = 1; curr < (1 << n); curr++) {
            sessions[curr] = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if ((curr >> j & 1) == 1) {
                    int from = curr ^ (1 << j);
                    int rest = remains[from] - tasks[j];
                    int count;
                    if (rest < 0) {
                        count = sessions[from] + 1;
                        rest = sessionTime - tasks[j];
                    } else {
                        count = sessions[from];
                    }
                    if (sessions[curr] < count) {
                        // pass
                    } else if (sessions[curr] > count) {
                        sessions[curr] = count;
                        remains[curr] = rest;
                    } else if (remains[curr] < rest) {
                        remains[curr] = rest;
                    }
                }
            }
        }
        return sessions[len - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[]{1, 2, 3}, 3).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[]{3, 1, 3, 1, 1}, 8).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new int[]{1, 2, 3, 4, 5}, 15).expect(1);

    // 这个测试用例不适合贪婪算法
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{2, 3, 3, 4, 4, 4, 5, 6, 7, 10}, 12)
            .expect(4);
    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{9, 6, 9}, 14)
            .expect(3);
    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}, 14)
            .expect(1);

}
