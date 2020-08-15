package q950;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 939. Minimum Area Rectangle
 * https://leetcode.com/problems/minimum-area-rectangle/
 *
 * Given a set of points in the xy-plane, determine the minimum area of a rectangle formed from these points, with sides
 * parallel to the x and y axes.
 *
 * If there isn't any rectangle, return 0.
 *
 * Example 1:
 *
 * Input: [[1,1],[1,3],[3,1],[3,3],[2,2]]
 * Output: 4
 *
 * Example 2:
 *
 * Input: [[1,1],[1,3],[3,1],[3,3],[4,1],[4,3]]
 * Output: 2
 *
 * Note:
 *
 * 1 <= points.length <= 500
 * 0 <= points[i][0] <= 40000
 * 0 <= points[i][1] <= 40000
 * All points are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q939_MinimumAreaRectangle {

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 1}, {1, 3}, {3, 1}, {3, 3}, {2, 2}})
            .expect(4);
    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1}, {1, 3}, {3, 1}, {3, 3}, {4, 1}, {4, 3}})
            .expect(2);

    // 题目中限定了矩形是和x 轴y 轴平行的
    @Answer
    public int minAreaRect(int[][] points) {
        Map<Integer, Set<Integer>> axis = new HashMap<>();
        for (int[] point : points) {
            axis.computeIfAbsent(point[0], k -> new HashSet<>()).add(point[1]);
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                int x1 = points[i][0], y1 = points[i][1];
                int x2 = points[j][0], y2 = points[j][1];
                if (x1 != x2 && y1 != y2
                        && axis.containsKey(x1) && axis.get(x1).contains(y2)
                        && axis.containsKey(x2) && axis.get(x2).contains(y1)) {
                    res = Math.min(res, Math.abs(x1 - x2) * Math.abs(y1 - y2));
                }
            }
        }
        return res == Integer.MAX_VALUE ? 0 : res;
    }

}
