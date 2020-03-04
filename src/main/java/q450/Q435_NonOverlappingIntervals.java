package q450;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/non-overlapping-intervals/
 *
 * Given a collection of intervals, find the minimum number of intervals you need to remove to make the rest of the
 * intervals non-overlapping.
 *
 *
 *
 * Example 1:
 *
 * Input: [[1,2],[2,3],[3,4],[1,3]]
 * Output: 1
 * Explanation: [1,3] can be removed and the rest of intervals are non-overlapping.
 *
 * Example 2:
 *
 * Input: [[1,2],[1,2],[1,2]]
 * Output: 2
 * Explanation: You need to remove two [1,2] to make the rest of intervals non-overlapping.
 *
 * Example 3:
 *
 * Input: [[1,2],[2,3]]
 * Output: 0
 * Explanation: You don't need to remove any of the intervals since they're already non-overlapping.
 *
 *
 *
 * Note:
 *
 * 1. You may assume the interval's end point is always bigger than its start point.
 * 2. Intervals like [1,2] and [2,3] have borders "touching" but they don't overlap each other.
 */
@RunWith(LeetCodeRunner.class)
public class Q435_NonOverlappingIntervals {

    /**
     * https://www.cnblogs.com/grandyang/p/6017505.html
     */
    @Answer
    public int eraseOverlapIntervals(int[][] intervals) {
        // last 表示上一个区间
        int res = 0, last = 0;
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        for (int i = 1; i < intervals.length; i++) {
            // 如果当前区间遮住了上一个区间, 就移除一个区间
            if (intervals[i][0] < intervals[last][1]) {
                res++;
                // 移除区间末尾靠后的那一个区间
                if (intervals[i][1] < intervals[last][1]) {
                    last = i;
                }
            } else {
                // 前后区间没有重叠就不用移除
                last = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {3, 4}, {1, 3}})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 2}, {1, 2}, {1, 2}})
            .expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}})
            .expect(0);

}
