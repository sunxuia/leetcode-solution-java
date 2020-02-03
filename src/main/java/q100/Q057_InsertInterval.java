package q100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import q400.Q352_DataStreamAsDisjointIntervals;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/insert-interval/
 *
 * Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).
 *
 * You may assume that the intervals were initially sorted according to their start times.
 *
 * Example 1:
 *
 * Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
 * Output: [[1,5],[6,9]]
 * Example 2:
 *
 * Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
 * Output: [[1,2],[3,10],[12,16]]
 * Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method
 * signature.
 *
 * 相关题目: {@link Q352_DataStreamAsDisjointIntervals}
 */
@RunWith(LeetCodeRunner.class)
public class Q057_InsertInterval {

    /**
     * 找出要插入的起始位置, 注意边界条件, 即可.
     */
    @Answer
    public int[][] insert(int[][] intervals, int[] newInterval) {
        if (intervals.length == 0) {
            return new int[][]{newInterval};
        }
        int start = binarySearch(intervals, 1, 0, intervals.length, newInterval[0]);
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < start; i++) {
            list.add(intervals[i]);
        }
        if (start == intervals.length) {
            list.add(newInterval);
            return list.toArray(new int[list.size()][]);
        }
        list.add(new int[]{Math.min(intervals[start][0], newInterval[0]), newInterval[1]});

        int end = binarySearch(intervals, 0, start, intervals.length, newInterval[1]);
        if (end > 0) {
            list.get(list.size() - 1)[1] = Math.max(list.get(list.size() - 1)[1], intervals[end - 1][1]);
        }
        if (end == intervals.length) {
            return list.toArray(new int[list.size()][]);
        }
        if (intervals[end][0] == newInterval[1]) {
            list.get(start)[1] = intervals[end][1];
        } else {
            list.add(intervals[end]);
        }
        for (int i = end + 1; i < intervals.length; i++) {
            list.add(intervals[i]);
        }
        return list.toArray(new int[list.size()][]);
    }

    private int binarySearch(int[][] intervals, int i, int start, int end, int expect) {
        while (start < end) {
            int middle = (start + end) / 2;
            if (intervals[middle][i] < expect) {
                start = middle + 1;
            } else {
                end = middle;
            }
        }
        return start;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{{1, 3}, {6, 9}})
            .addArgument(new int[]{2, 5})
            .expect(new int[][]{{1, 5}, {6, 9}})
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}})
            .addArgument(new int[]{4, 8})
            .expect(new int[][]{{1, 2}, {3, 10}, {12, 16}})
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[][]{{1, 5}})
            .addArgument(new int[]{2, 3})
            .expect(new int[][]{{1, 5}})
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[][]{{1, 5}})
            .addArgument(new int[]{0, 3})
            .expect(new int[][]{{0, 5}})
            .build();

    @TestData
    public DataExpectation normal3 = DataExpectation.builder()
            .addArgument(new int[][]{{2, 4}, {5, 7}, {8, 10}, {11, 13}})
            .addArgument(new int[]{3, 6})
            .expect(new int[][]{{2, 7}, {8, 10}, {11, 13}})
            .build();

    @TestData
    public DataExpectation normal4 = DataExpectation.builder()
            .addArgument(new int[][]{{1, 5}})
            .addArgument(new int[]{0, 1})
            .expect(new int[][]{{0, 5}})
            .build();
}
