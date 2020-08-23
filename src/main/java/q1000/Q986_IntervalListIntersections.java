package q1000;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 986. Interval List Intersections
 * https://leetcode.com/problems/interval-list-intersections/
 *
 * Given two lists of closed intervals, each list of intervals is pairwise disjoint and in sorted order.
 *
 * Return the intersection of these two interval lists.
 *
 * (Formally, a closed interval [a, b] (with a <= b) denotes the set of real numbers x with a <= x <= b.  The
 * intersection of two closed intervals is a set of real numbers that is either empty, or can be represented as a closed
 * interval.  For example, the intersection of [1, 3] and [2, 4] is [2, 3].)
 *
 * Example 1:
 * (图Q986_PIC.png)
 * Input: A = [[0,2],[5,10],[13,23],[24,25]], B = [[1,5],[8,12],[15,24],[25,26]]
 * Output: [[1,2],[5,5],[8,10],[15,23],[24,24],[25,25]]
 *
 * Note:
 *
 * 0 <= A.length < 1000
 * 0 <= B.length < 1000
 * 0 <= A[i].start, A[i].end, B[i].start, B[i].end < 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q986_IntervalListIntersections {

    @Answer
    public int[][] intervalIntersection(int[][] A, int[][] B) {
        List<int[]> list = new ArrayList<>();
        int a = 0, b = 0;
        while (a < A.length && b < B.length) {
            if (A[a][1] < B[b][0]) {
                a++;
            } else if (B[b][1] < A[a][0]) {
                b++;
            } else if (A[a][1] < B[b][1]) {
                list.add(new int[]{Math.max(A[a][0], B[b][0]), A[a][1]});
                a++;
            } else {
                list.add(new int[]{Math.max(A[a][0], B[b][0]), B[b][1]});
                b++;
            }
        }
        return list.toArray(new int[0][]);
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(
            new int[][]{{0, 2}, {5, 10}, {13, 23}, {24, 25}},
            new int[][]{{1, 5}, {8, 12}, {15, 24}, {25, 26}})
            .expect(new int[][]{{1, 2}, {5, 5}, {8, 10}, {15, 23}, {24, 24}, {25, 25}});

}
