package q1500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1453. Maximum Number of Darts Inside of a Circular Dartboard
 * https://leetcode.com/problems/maximum-number-of-darts-inside-of-a-circular-dartboard/
 *
 * You have a very large square wall and a circular dartboard placed on the wall. You have been challenged to throw
 * darts into the board blindfolded. Darts thrown at the wall are represented as an array of points on a 2D plane.
 *
 * Return the maximum number of points that are within or lie on any circular dartboard of radius r.
 *
 * Example 1:
 * <img src="./Q1453_PIC1.png">
 * Input: points = [[-2,0],[2,0],[0,2],[0,-2]], r = 2
 * Output: 4
 * Explanation: Circle dartboard with center in (0,0) and radius = 2 contain all points.
 *
 * Example 2:
 * <img src="./Q1453_PIC2.png">
 * Input: points = [[-3,0],[3,0],[2,6],[5,4],[0,9],[7,8]], r = 5
 * Output: 5
 * Explanation: Circle dartboard with center in (0,4) and radius = 5 contain all points except the point (7,8).
 *
 * Example 3:
 *
 * Input: points = [[-2,0],[2,0],[0,2],[0,-2]], r = 1
 * Output: 1
 *
 * Example 4:
 *
 * Input: points = [[1,2],[3,5],[1,-1],[2,3],[4,1],[1,3]], r = 2
 * Output: 4
 *
 * Constraints:
 *
 * 1 <= points.length <= 100
 * points[i].length == 2
 * -10^4 <= points[i][0], points[i][1] <= 10^4
 * 1 <= r <= 5000
 */
@RunWith(LeetCodeRunner.class)
public class Q1453_MaximumNumberOfDartsInsideOfACircularDartboard {

    /**
     * 通过两点在圆的边上确定圆心, 再计算其他点到圆心的距离得到结果.
     * 但是算起来实在是麻烦(•́へ•́╬), leetcode 上比较快的解法如下:
     */
    @Answer
    public int numPoints(int[][] points, int r) {
        final int n = points.length, rs = r * r;
        int res = 1;
        for (int i = 0; i < n; i++) {
            final int x1 = points[i][0], y1 = points[i][1];
            int same = 1;
            for (int j = i + 1; j < n; j++) {
                final int x2 = points[j][0], y2 = points[j][1];
                if (x1 == x2 && y1 == y2) {
                    res = Math.max(res, ++same);
                    continue;
                }
                int sx = x1 + x2, sy = y1 + y2;
                int dx = x2 - x1, dy = y2 - y1;
                int dSqr = dx * dx + dy * dy;
                if (dSqr > (rs << 2)) {
                    continue;
                }
                double z = Math.sqrt(4.0 * rs / dSqr - 1);
                double h = (sx - dy * z) / 2, k = (sy + dx * z) / 2;
                int count = 2;
                for (int p = 0; p < n; p++) {
                    if (p == i || p == j) {
                        continue;
                    }
                    double tx = points[p][0] - h, ty = points[p][1] - k;
                    if (tx * tx + ty * ty <= rs + 1e-6) {
                        count++;
                    }
                }
                res = Math.max(res, count);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{-2, 0}, {2, 0}, {0, 2}, {0, -2}}, 2)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{-3, 0}, {3, 0}, {2, 6}, {5, 4}, {0, 9}, {7, 8}}, 5)
            .expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{{-2, 0}, {2, 0}, {0, 2}, {0, -2}}, 1)
            .expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new int[][]{{1, 2}, {3, 5}, {1, -1}, {2, 3}, {4, 1}, {1, 3}}, 2)
            .expect(4);

}
