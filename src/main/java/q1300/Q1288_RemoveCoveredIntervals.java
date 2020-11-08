package q1300;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1288. Remove Covered Intervals
 * https://leetcode.com/problems/remove-covered-intervals/
 *
 * Given a list of intervals, remove all intervals that are covered by another interval in the list.
 *
 * Interval [a,b) is covered by interval [c,d) if and only if c <= a and b <= d.
 *
 * After doing so, return the number of remaining intervals.
 *
 * Example 1:
 *
 * Input: intervals = [[1,4],[3,6],[2,8]]
 * Output: 2
 * Explanation: Interval [3,6] is covered by [2,8], therefore it is removed.
 *
 * Example 2:
 *
 * Input: intervals = [[1,4],[2,3]]
 * Output: 1
 *
 * Example 3:
 *
 * Input: intervals = [[0,10],[5,12]]
 * Output: 2
 *
 * Example 4:
 *
 * Input: intervals = [[3,10],[4,10],[5,11]]
 * Output: 2
 *
 * Example 5:
 *
 * Input: intervals = [[1,2],[1,4],[3,4]]
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= intervals.length <= 1000
 * intervals[i].length == 2
 * 0 <= intervals[i][0] < intervals[i][1] <= 10^5
 * All the intervals are unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1288_RemoveCoveredIntervals {

    /**
     * 排序的做法, 比较慢
     */
    @Answer
    public int removeCoveredIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> a[0] == b[0] ? b[1] - a[1] : a[0] - b[0]);
        int res = 0, right = -1;
        for (int[] interval : intervals) {
            if (interval[1] > right) {
                res++;
                right = interval[1];
            }
        }
        return res;
    }

    /**
     * LeetCode 上比较快的做法. 在排序的时候进行判断.
     */
    @Answer
    public int removeCoveredIntervals2(int[][] intervals) {
        List<int[]> minIntervals = new ArrayList<>();
        for (int[] interval : intervals) {
            addInterval(minIntervals, interval);
        }
        return minIntervals.size();
    }

    private void addInterval(List<int[]> minIntervals, int[] curr) {
        for (int i = 0; i < minIntervals.size(); i++) {
            int[] prev = minIntervals.get(i);
            if (isCovered(curr, prev)) {
                // curr 被 prev 包括
                return;
            }
            if (isCovered(prev, curr)) {
                // prev 被curr 包括
                minIntervals.remove(i);
                i--;
            }
        }
        minIntervals.add(curr);
    }

    private boolean isCovered(int[] a, int[] b) {
        return (a[0] >= b[0] && a[1] <= b[1]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 4}, {3, 6}, {2, 8}}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 4}, {2, 3}}).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{0, 10}, {5, 12}}).expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{{3, 10}, {4, 10}, {5, 11}}).expect(2);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{{1, 2}, {1, 4}, {3, 4}}).expect(1);

}
