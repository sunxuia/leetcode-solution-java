package q1950;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.common.json.JsonTypeWrapper;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1943. Describe the Painting
 * https://leetcode.com/problems/describe-the-painting/
 *
 * There is a long and thin painting that can be represented by a number line. The painting was painted with multiple
 * overlapping segments where each segment was painted with a unique color. You are given a 2D integer array segments,
 * where segments[i] = [starti, endi, colori] represents the half-closed segment [starti, endi) with colori as the
 * color.
 *
 * The colors in the overlapping segments of the painting were mixed when it was painted. When two or more colors mix,
 * they form a new color that can be represented as a set of mixed colors.
 *
 * For example, if colors 2, 4, and 6 are mixed, then the resulting mixed color is {2,4,6}.
 *
 * For the sake of simplicity, you should only output the sum of the elements in the set rather than the full set.
 *
 * You want to describe the painting with the minimum number of non-overlapping half-closed segments of these mixed
 * colors. These segments can be represented by the 2D array painting where painting[j] = [leftj, rightj, mixj]
 * describes a half-closed segment [leftj, rightj) with the mixed color sum of mixj.
 *
 * For example, the painting created with segments = [[1,4,5],[1,7,7]] can be described by painting = [[1,4,12],[4,7,7]]
 * because:
 *
 * [1,4) is colored {5,7} (with a sum of 12) from both the first and second segments.
 * [4,7) is colored {7} from only the second segment.
 *
 *
 *
 * Return the 2D array painting describing the finished painting (excluding any parts that are not painted). You may
 * return the segments in any order.
 *
 * A half-closed segment [a, b) is the section of the number line between points a and b including point a and not
 * including point b.
 *
 * Example 1:
 * (图Q1943_PIC1.png)
 * Input: segments = [[1,4,5],[4,7,7],[1,7,9]]
 * Output: [[1,4,14],[4,7,16]]
 * Explanation: The painting can be described as follows:
 * - [1,4) is colored {5,9} (with a sum of 14) from the first and third segments.
 * - [4,7) is colored {7,9} (with a sum of 16) from the second and third segments.
 *
 * Example 2:
 * (图Q1943_PIC2.png)
 * Input: segments = [[1,7,9],[6,8,15],[8,10,7]]
 * Output: [[1,6,9],[6,7,24],[7,8,15],[8,10,7]]
 * Explanation: The painting can be described as follows:
 * - [1,6) is colored 9 from the first segment.
 * - [6,7) is colored {9,15} (with a sum of 24) from the first and second segments.
 * - [7,8) is colored 15 from the second segment.
 * - [8,10) is colored 7 from the third segment.
 *
 * Example 3:
 * (图Q1943_PIC3.png)
 * Input: segments = [[1,4,5],[1,4,7],[4,7,1],[4,7,11]]
 * Output: [[1,4,12],[4,7,12]]
 * Explanation: The painting can be described as follows:
 * - [1,4) is colored {5,7} (with a sum of 12) from the first and second segments.
 * - [4,7) is colored {1,11} (with a sum of 12) from the third and fourth segments.
 * Note that returning a single segment [1,7) is incorrect because the mixed color sets are different.
 *
 * Constraints:
 *
 * 1 <= segments.length <= 2 * 10^4
 * segments[i].length == 3
 * 1 <= starti < endi <= 10^5
 * 1 <= colori <= 10^9
 * Each colori is distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1943_DescribeThePainting {

    @Answer
    public List<List<Long>> splitPainting(int[][] segments) {
        Arrays.sort(segments, (s1, s2) -> s1[0] == s2[0] ? s1[1] - s2[1] : s1[0] - s2[0]);
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s[1]));
        List<List<Long>> res = new ArrayList<>();
        long start = 0, color = 0;
        for (int[] segment : segments) {
            while (!pq.isEmpty() && pq.peek()[1] <= segment[0]) {
                int[] seg = pq.poll();
                if (start < seg[1]) {
                    // 因为 "Each colori is distinct." 所以不用担心颜色合并的问题
                    res.add(List.of(start, (long) seg[1], color));
                }
                start = seg[1];
                color -= seg[2];
            }
            if (color != 0 && start < segment[0]) {
                res.add(List.of(start, (long) segment[0], color));
            }
            pq.offer(segment);
            start = segment[0];
            color += segment[2];
        }
        while (!pq.isEmpty()) {
            int[] seg = pq.poll();
            if (start < seg[1]) {
                res.add(List.of(start, (long) seg[1], color));
            }
            start = seg[1];
            color -= seg[2];
        }
        return res;
    }


    /**
     * 参考 leetcode 上最快做法, 利用了"1 <= starti < endi <= 10^5" 的条件
     */
    @Answer
    public List<List<Long>> splitPainting2(int[][] segments) {
        int len = 0;
        for (int[] segment : segments) {
            len = Math.max(len, segment[1]);
        }
        len += 1;

        long[] offsets = new long[len];
        boolean[] points = new boolean[len];
        for (int[] segment : segments) {
            offsets[segment[0]] += segment[2];
            points[segment[0]] = true;
            offsets[segment[1]] -= segment[2];
            points[segment[1]] = true;
        }
        List<List<Long>> res = new ArrayList<>();
        long start = 0, color = 0;
        for (int i = 0; i < len; i++) {
            if (points[i]) {
                if (color > 0) {
                    res.add(List.of(start, (long) i, color));
                }
                color += offsets[i];
                start = i;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 4, 5}, {4, 7, 7}, {1, 7, 9}})
            .expect(List.of(List.of(1L, 4L, 14L), List.of(4L, 7L, 16L)));

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 7, 9}, {6, 8, 15}, {8, 10, 7}})
            .expect(List.of(List.of(1L, 6L, 9L), List.of(6L, 7L, 24L), List.of(7L, 8L, 15L), List.of(8L, 10L, 7L)));

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{1, 4, 5}, {1, 4, 7}, {4, 7, 1}, {4, 7, 11}})
            .expect(List.of(List.of(1L, 4L, 12L), List.of(4L, 7L, 12L)));

    private static TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation largeData = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, int[][].class))
            .expect(TestDataFileHelper.read(testDataFile, 2, new JsonTypeWrapper<List<List<Long>>>() {}));

}
