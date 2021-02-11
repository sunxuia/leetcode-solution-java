package q1800;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import q1400.Q1353_MaximumNumberOfEventsThatCanBeAttended;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1751. Maximum Number of Events That Can Be Attended II
 * https://leetcode.com/problems/maximum-number-of-events-that-can-be-attended-ii/
 *
 * You are given an array of events where events[i] = [startDayi, endDayi, valuei]. The ith event starts at startDayi
 * and ends at endDayi, and if you attend this event, you will receive a value of valuei. You are also given an integer
 * k which represents the maximum number of events you can attend.
 *
 * You can only attend one event at a time. If you choose to attend an event, you must attend the entire event. Note
 * that the end day is inclusive: that is, you cannot attend two events where one of them starts and the other ends on
 * the same day.
 *
 * Return the maximum sum of values that you can receive by attending events.
 *
 * Example 1:
 *
 * Input: events = [[1,2,4],[3,4,3],[2,3,1]], k = 2
 * Output: 7
 * Explanation: Choose the green events, 0 and 1 (0-indexed) for a total value of 4 + 3 = 7.
 *
 * Example 2:
 *
 * Input: events = [[1,2,4],[3,4,3],[2,3,10]], k = 2
 * Output: 10
 * Explanation: Choose event 2 for a total value of 10.
 * Notice that you cannot attend any other event as they overlap, and that you do not have to attend k events.
 *
 * Example 3:
 *
 * Input: events = [[1,1,1],[2,2,2],[3,3,3],[4,4,4]], k = 3
 * Output: 9
 * Explanation: Although the events do not overlap, you can only attend 3 events. Pick the highest valued three.
 *
 * Constraints:
 *
 * 1 <= k <= events.length
 * 1 <= k * events.length <= 10^6
 * 1 <= startDayi <= endDayi <= 10^9
 * 1 <= valuei <= 10^6
 *
 * 上一题 {@link Q1353_MaximumNumberOfEventsThatCanBeAttended}
 */
@RunWith(LeetCodeRunner.class)
public class Q1751_MaximumNumberOfEventsThatCanBeAttendedII {

    @Answer
    public int maxValue(int[][] events, int k) {
        final int n = events.length;
        // 按照结束时间排序
        Arrays.sort(events, Comparator.comparingInt(a -> a[1]));
        // dp[i][j] 表示到第 events[i] 场会议结束时, 最多参见了 j 场会议的结果.
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i < n; i++) {
            // 找出该场会议的开始前最近结束的会议
            int start = -1, prev = i - 1;
            while (start < prev) {
                int mid = (start + prev + 1) / 2;
                if (events[mid][1] >= events[i][0]) {
                    prev = mid - 1;
                } else {
                    start = mid;
                }
            }
            dp[i + 1][0] = dp[i][0];
            for (int j = 1; j <= k; j++) {
                // 不参加该会议, 参加了但总次数 < j, 参加了且是第 j 场会议
                dp[i + 1][j] = Math.max(dp[i][j], Math.max(
                        dp[i + 1][j - 1], dp[prev + 1][j - 1] + events[i][2]));
            }
        }
        return dp[n][k];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 2, 4}, {3, 4, 3}, {2, 3, 1}}, 2)
            .expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{1, 2, 4}, {3, 4, 3}, {2, 3, 10}}, 2)
            .expect(10);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{1, 1, 1}, {2, 2, 2}, {3, 3, 3}, {4, 4, 4}}, 3)
            .expect(9);

}
