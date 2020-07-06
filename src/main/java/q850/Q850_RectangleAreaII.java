package q850;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import q250.Q223_RectangleArea;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rectangle-area-ii/
 *
 * We are given a list of (axis-aligned) rectangles.  Each rectangle[i] = [x1, y1, x2, y2] , where (x1, y1) are the
 * coordinates of the bottom-left corner, and (x2, y2) are the coordinates of the top-right corner of the ith rectangle.
 *
 * Find the total area covered by all rectangles in the plane.  Since the answer may be too large, return it modulo
 * 10^9 + 7.
 *
 * (图 Q850_PIC.png)
 *
 * Example 1:
 *
 * Input: [[0,0,2,2],[1,0,2,3],[1,0,3,1]]
 * Output: 6
 * Explanation: As illustrated in the picture.
 *
 * Example 2:
 *
 * Input: [[0,0,1000000000,1000000000]]
 * Output: 49
 * Explanation: The answer is 10^18 modulo (10^9 + 7), which is (10^9)^2 = (-7)^2 = 49.
 *
 * Note:
 *
 * 1 <= rectangles.length <= 200
 * rectanges[i].length = 4
 * 0 <= rectangles[i][j] <= 10^9
 * The total area covered by all rectangles will never exceed 2^63 - 1 and thus will fit in a 64-bit signed integer.
 *
 * 上一题 {@link Q223_RectangleArea}
 */
@RunWith(LeetCodeRunner.class)
public class Q850_RectangleAreaII {

    // https://www.cnblogs.com/grandyang/p/11371256.html
    @Answer
    public int rectangleArea(int[][] rectangles) {
        List<int[]> all = new ArrayList<>();
        for (int[] rectangle : rectangles) {
            helper(all, rectangle, 0);
        }

        long res = 0;
        for (int[] a : all) {
            res = (res + (long) (a[2] - a[0]) * (long) (a[3] - a[1])) % 10_0000_0007;
        }
        return (int) res;
    }

    void helper(List<int[]> all, int[] cur, int start) {
        if (start >= all.size()) {
            all.add(cur);
            return;
        }
        int[] rec = all.get(start);
        if (cur[2] <= rec[0] || cur[3] <= rec[1] || cur[0] >= rec[2] || cur[1] >= rec[3]) {
            helper(all, cur, start + 1);
            return;
        }
        if (cur[0] < rec[0]) {
            helper(all, new int[]{cur[0], cur[1], rec[0], cur[3]}, start + 1);
        }
        if (cur[2] > rec[2]) {
            helper(all, new int[]{rec[2], cur[1], cur[2], cur[3]}, start + 1);
        }
        if (cur[1] < rec[1]) {
            helper(all, new int[]{Math.max(rec[0], cur[0]), cur[1], Math.min(rec[2], cur[2]), rec[1]}, start + 1);
        }
        if (cur[3] > rec[3]) {
            helper(all, new int[]{Math.max(rec[0], cur[0]), rec[3], Math.min(rec[2], cur[2]), cur[3]}, start + 1);
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{0, 0, 2, 2}, {1, 0, 2, 3}, {1, 0, 3, 1}})
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{0, 0, 1000000000, 1000000000}})
            .expect(49);

}
