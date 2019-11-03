package q100;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/merge-intervals/
 *
 * Given a collection of intervals, merge all overlapping intervals.
 *
 * Example 1:
 *
 * Input: [[1,3],[2,6],[8,10],[15,18]]
 * Output: [[1,6],[8,10],[15,18]]
 * Explanation: Since intervals [1,3] and [2,6] overlaps, merge them into [1,6].
 * Example 2:
 *
 * Input: [[1,4],[4,5]]
 * Output: [[1,5]]
 * Explanation: Intervals [1,4] and [4,5] are considered overlapping.
 * NOTE: input types have been changed on April 15, 2019. Please reset to default code definition to get new method
 * signature.
 *
 * 题解:
 * 输入参数intervals 中的每个元素都表示一个区间的上下限, 假设为[a, b], 另有一个元素假设为 [c, d], 如果[a, b] 和 [c, d]
 * 有重叠(b >= c), 则合并[a, b] 和 [c, d] 为 [a, d].
 * 输入的intervals 是无序的.
 */
@RunWith(LeetCodeRunner.class)
public class Q056_MergeIntervals {

    @Answer
    public int[][] merge(int[][] intervals) {
        if (intervals.length < 2) {
            return intervals;
        }
        Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
        int cur = 1;
        for (int i = 1; i < intervals.length; i++) {
            intervals[cur][0] = intervals[i][0];
            intervals[cur][1] = intervals[i][1];
            for (; cur > 0; cur--) {
                int[] prev = intervals[cur - 1], curr = intervals[cur];
                if (prev[1] < curr[0]) {
                    break;
                }
                if (prev[0] > curr[0]) {
                    prev[0] = curr[0];
                }
                if (prev[1] < curr[1]) {
                    prev[1] = curr[1];
                }
            }
            cur++;
        }
        return Arrays.copyOf(intervals, cur);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 3},
                    {2, 6},
                    {8, 10},
                    {15, 18}
            }).expect(new int[][]{
                    {1, 6},
                    {8, 10},
                    {15, 18}
            }).unorderResult()
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 4},
                    {4, 5}
            }).expect(new int[][]{
                    {1, 5}
            }).unorderResult()
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 4},
                    {0, 4}
            }).expect(new int[][]{
                    {0, 4}
            }).build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument(new int[][]{
                    {1, 4},
                    {0, 0}
            }).expect(new int[][]{
                    {0, 0},
                    {1, 4}
            }).unorderResult()
            .build();

}
