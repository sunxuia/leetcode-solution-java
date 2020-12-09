package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1401. Circle and Rectangle Overlapping
 * https://leetcode.com/problems/circle-and-rectangle-overlapping/
 *
 * Given a circle represented as (radius, x_center, y_center) and an axis-aligned rectangle represented as (x1, y1, x2,
 * y2), where (x1, y1) are the coordinates of the bottom-left corner, and (x2, y2) are the coordinates of the top-right
 * corner of the rectangle.
 *
 * Return True if the circle and rectangle are overlapped otherwise return False.
 *
 * In other words, check if there are any point (xi, yi) such that belongs to the circle and the rectangle at the same
 * time.
 *
 * Example 1:
 * <img src="./Q1401_PIC1.png">
 * Input: radius = 1, x_center = 0, y_center = 0, x1 = 1, y1 = -1, x2 = 3, y2 = 1
 * Output: true
 * Explanation: Circle and rectangle share the point (1,0)
 *
 * Example 2:
 * <img src="./Q1401_PIC2.png">
 * Input: radius = 1, x_center = 0, y_center = 0, x1 = -1, y1 = 0, x2 = 0, y2 = 1
 * Output: true
 *
 * Example 3:
 * <img src="./Q1401_PIC3.png">
 * Input: radius = 1, x_center = 1, y_center = 1, x1 = -3, y1 = -3, x2 = 3, y2 = 3
 * Output: true
 *
 * Example 4:
 *
 * Input: radius = 1, x_center = 1, y_center = 1, x1 = 1, y1 = -3, x2 = 2, y2 = -1
 * Output: false
 *
 * Constraints:
 *
 * 1 <= radius <= 2000
 * -10^4 <= x_center, y_center, x1, y1, x2, y2 <= 10^4
 * x1 < x2
 * y1 < y2
 */
@RunWith(LeetCodeRunner.class)
public class Q1401_CircleAndRectangleOverlapping {

    @Answer
    public boolean checkOverlap(int radius, int x_center, int y_center, int x1, int y1, int x2, int y2) {
        // 圆心在矩形内
        if (x1 <= x_center && x_center <= x2 && y1 <= y_center && y_center <= y2) {
            return true;
        }
        // 圆心在矩形的上下
        if (x1 <= x_center && x_center <= x2) {
            int dist = Math.min(Math.abs(y_center - y1), Math.abs(y_center - y2));
            if (dist <= radius) {
                return true;
            }
        }
        // 圆心在矩形的左右
        if (y1 <= y_center && y_center <= y2) {
            int dist = Math.min(Math.abs(x_center - x1), Math.abs(x_center - x2));
            if (dist <= radius) {
                return true;
            }
        }
        // 矩形的4 角是否至少有1 个在圆内
        int radius2 = radius * radius;
        if (distance2(x_center, y_center, x1, y1) <= radius2
                || distance2(x_center, y_center, x1, y2) <= radius2
                || distance2(x_center, y_center, x2, y1) <= radius2
                || distance2(x_center, y_center, x2, y2) <= radius2) {
            return true;
        }
        return false;
    }

    private double distance2(int x1, int y1, int x2, int y2) {
        return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(1, 0, 0, 1, -1, 3, 1).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(1, 0, 0, -1, 0, 0, 1).expect(true);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(1, 1, 1, -3, -3, 3, 3).expect(true);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith(1, 1, 1, 1, -3, 2, -1).expect(false);

}
