package q600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-square/
 *
 * Given the coordinates of four points in 2D space, return whether the four points could construct a square.
 *
 * The coordinate (x,y) of a point is represented by an integer array with two integers.
 *
 * Example:
 *
 * Input: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
 * Output: True
 *
 *
 *
 * Note:
 *
 * 1. All the input integers are in the range [-10000, 10000].
 * 2. A valid square has four equal sides with positive length and four equal angles (90-degree angles).
 * 3. Input points have no order.
 */
@RunWith(LeetCodeRunner.class)
public class Q593_ValidSquare {

    @Answer
    public boolean validSquare(int[] p1, int[] p2, int[] p3, int[] p4) {
        int[][] points = new int[][]{p1, p2, p3, p4};
        // 这样排列后顺时针方向 0213 或 0132 相连组成矩形.
        Arrays.sort(points, (a, b) -> a[0] == b[0] ? a[1] - b[1] : a[0] - b[0]);
        // 有相同的点肯定不是正方形
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] == points[i - 1][0] && points[i][1] == points[i - 1][1]) {
                return false;
            }
        }

        // 长宽相等、3个角90° 则是正方形
        double len1 = Math.pow(points[0][0] - points[1][0], 2) + Math.pow(points[0][1] - points[1][1], 2);
        double len2 = Math.pow(points[1][0] - points[3][0], 2) + Math.pow(points[1][1] - points[3][1], 2);
        return len1 == len2
                && isDegree90(points[0], points[1], points[3])
                && isDegree90(points[1], points[3], points[2])
                && isDegree90(points[3], points[2], points[0]);
    }

    // 判断线 pq 和 qr 是否是90度相交. 通过三角形面积的方法判断是否是直角.
    private boolean isDegree90(int[] p, int[] q, int[] r) {
        double a2 = 1.0 * (p[0] - q[0]) * (p[0] - q[0]) + (p[1] - q[1]) * (p[1] - q[1]);
        double b2 = 1.0 * (r[0] - q[0]) * (r[0] - q[0]) + (r[1] - q[1]) * (r[1] - q[1]);
        double c2 = 1.0 * (p[0] - r[0]) * (p[0] - r[0]) + (p[1] - r[1]) * (p[1] - r[1]);
        double square = (4.0 * a2 * c2 - (c2 + a2 - b2) * (c2 + a2 - b2)) / 4;
        return square == a2 * b2;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .createWith(new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, new int[]{0, 1})
            .expect(true);

    // 菱形 ◇
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{1, 0}, new int[]{-1, 0}, new int[]{0, 1}, new int[]{0, -1})
            .expect(true);

    // 这个正方形比较大
    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{1134, -2539}, new int[]{492, -1255}, new int[]{-792, -1897}, new int[]{-150, -3181})
            .expect(true);

    // 这个是长方形
    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{0, 0}, new int[]{5, 0}, new int[]{5, 4}, new int[]{0, 4})
            .expect(false);
    @TestData
    public DataExpectation normal4 = DataExpectation
            .createWith(new int[]{0, 0}, new int[]{1, 1}, new int[]{1, 0}, new int[]{1, 1})
            .expect(false);

}
