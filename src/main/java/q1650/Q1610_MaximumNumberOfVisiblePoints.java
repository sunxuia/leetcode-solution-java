package q1650;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1610. Maximum Number of Visible Points
 * https://leetcode.com/problems/maximum-number-of-visible-points/
 *
 * You are given an array points, an integer angle, and your location, where location = [posx, posy] and points[i] =
 * [xi, yi] both denote integral coordinates on the X-Y plane.
 *
 * Initially, you are facing directly east from your position. You cannot move from your position, but you can rotate.
 * In other words, posx and posy cannot be changed. Your field of view in degrees is represented by angle, determining
 * how wide you can see from any given view direction. Let d be the amount in degrees that you rotate counterclockwise.
 * Then, your field of view is the inclusive range of angles [d - angle/2, d + angle/2].
 *
 * Your browser does not support the video tag or this video format.
 * <video src="./Q1610_VIDEO.mp4">
 * You can see some set of points if, for each point, the angle formed by the point, your position, and the immediate
 * east direction from your position is in your field of view.
 *
 * There can be multiple points at one coordinate. There may be points at your location, and you can always see these
 * points regardless of your rotation. Points do not obstruct your vision to other points.
 *
 * Return the maximum number of points you can see.
 *
 * Example 1:
 * <img src="./Q1610_PIC1.png">
 * Input: points = [[2,1],[2,2],[3,3]], angle = 90, location = [1,1]
 * Output: 3
 * Explanation: The shaded region represents your field of view. All points can be made visible in your field of view,
 * including [3,3] even though [2,2] is in front and in the same line of sight.
 *
 * Example 2:
 *
 * Input: points = [[2,1],[2,2],[3,4],[1,1]], angle = 90, location = [1,1]
 * Output: 4
 * Explanation: All points can be made visible in your field of view, including the one at your location.
 *
 * Example 3:
 * <img src="./Q1610_PIC2.png">
 * Input: points = [[1,0],[2,1]], angle = 13, location = [1,1]
 * Output: 1
 * Explanation: You can only see one of the two points, as shown above.
 *
 * Constraints:
 *
 * 1 <= points.length <= 10^5
 * points[i].length == 2
 * location.length == 2
 * 0 <= angle < 360
 * 0 <= posx, posy, xi, yi <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1610_MaximumNumberOfVisiblePoints {

    @Answer
    public int visiblePoints(List<List<Integer>> points, int angle, List<Integer> location) {
        // 将位置转化为相对location 的角度
        List<Double> degrees = new ArrayList<>();
        // (表示和 location 相同位置的 points 中的点的数量)
        int same = 0;
        for (int i = 0; i < points.size(); i++) {
            int dx = points.get(i).get(0) - location.get(0);
            int dy = points.get(i).get(1) - location.get(1);
            if (dx == 0) {
                if (dy == 0) {
                    same++;
                } else {
                    degrees.add(dy > 0 ? 90d : 270d);
                }
            } else if (dx > 0) {
                double radio = Math.atan((double) dy / dx);
                degrees.add((radio / Math.PI * 180 + 360) % 360);
            } else {
                double radio = Math.atan((double) dy / dx) + Math.PI;
                degrees.add(radio / Math.PI * 180);
            }
        }
        Collections.sort(degrees);

        // 滑动窗口
        final int n = degrees.size();
        int max = 0, left = 0, right = 0;
        while (left < n && right < left + n) {
            double range = degrees.get(right % n) - degrees.get(left);
            range = (range + 360) % 360;
            if (range <= angle) {
                max = Math.max(max, right - left + 1);
                right++;
            } else {
                left++;
            }
        }
        return max + same;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(List.of(List.of(2, 1), List.of(2, 2), List.of(3, 3)), 90, List.of(1, 1))
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(List.of(List.of(2, 1), List.of(2, 2), List.of(3, 4), List.of(1, 1)), 90, List.of(1, 1))
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(List.of(List.of(1, 0), List.of(2, 1)), 13, List.of(1, 1))
            .expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(List.of(List.of(34, 26), List.of(35, 95), List.of(31, 56), List.of(84, 75), List.of(26, 76),
                    List.of(22, 15), List.of(26, 78), List.of(90, 41), List.of(94, 18), List.of(12, 88),
                    List.of(42, 82), List.of(27, 0), List.of(85, 49), List.of(69, 71), List.of(13, 36), List.of(59, 58),
                    List.of(58, 18), List.of(21, 62)), 15, List.of(67, 91))
            .expect(4);

}
