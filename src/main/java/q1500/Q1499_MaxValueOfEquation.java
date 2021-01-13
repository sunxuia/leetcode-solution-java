package q1500;

import java.util.LinkedList;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1499. Max Value of Equation
 * https://leetcode.com/problems/max-value-of-equation/
 *
 * Given an array points containing the coordinates of points on a 2D plane, sorted by the x-values, where points[i] =
 * [xi, yi] such that xi < xj for all 1 <= i < j <= points.length. You are also given an integer k.
 *
 * Find the maximum value of the equation yi + yj + |xi - xj| where |xi - xj| <= k and 1 <= i < j <= points.length. It
 * is guaranteed that there exists at least one pair of points that satisfy the constraint |xi - xj| <= k.
 *
 * Example 1:
 *
 * Input: points = [[1,3],[2,0],[5,10],[6,-10]], k = 1
 * Output: 4
 * Explanation: The first two points satisfy the condition |xi - xj| <= 1 and if we calculate the equation we get 3 + 0
 * + |1 - 2| = 4. Third and fourth points also satisfy the condition and give a value of 10 + -10 + |5 - 6| = 1.
 * No other pairs satisfy the condition, so we return the max of 4 and 1.
 *
 * Example 2:
 *
 * Input: points = [[0,0],[3,0],[9,2]], k = 3
 * Output: 3
 * Explanation: Only the first two points have an absolute difference of 3 or less in the x-values, and give the value
 * of 0 + 0 + |0 - 3| = 3.
 *
 * Constraints:
 *
 * 2 <= points.length <= 10^5
 * points[i].length == 2
 * -10^8 <= points[i][0], points[i][1] <= 10^8
 * 0 <= k <= 2 * 10^8
 * points[i][0] < points[j][0] for all 1 <= i < j <= points.length
 * xi form a strictly increasing sequence.
 */
@RunWith(LeetCodeRunner.class)
public class Q1499_MaxValueOfEquation {

    /**
     * yi + yj + |xi - xj| 令 i < j, 则可以转换为 (yi - xi) + (yj + xj)
     */
    @Answer
    public int findMaxValueOfEquation(int[][] points, int k) {
        int res = Integer.MIN_VALUE;
        // 双端队列保存坐标, 坐标的 y - x 单调递减
        LinkedList<int[]> queue = new LinkedList<>();
        for (int[] point : points) {
            int x = point[0], y = point[1];
            // 移除超过范围的点
            while (!queue.isEmpty()
                    && queue.getFirst()[0] < x - k) {
                queue.pollFirst();
            }
            // 最开头的 y - x 最大, 和当前坐标的值相加
            if (!queue.isEmpty()) {
                int[] head = queue.getFirst();
                res = Math.max(res, head[1] - head[0] + x + y);
            }
            // 移除尾部 y - x 小于当前坐标结果的点
            while (!queue.isEmpty()) {
                int[] tail = queue.getLast();
                if (tail[1] - tail[0] <= y - x) {
                    queue.pollLast();
                } else {
                    break;
                }
            }
            // 加入当前点
            queue.offer(point);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{{1, 3}, {2, 0}, {5, 10}, {6, -10}}, 1)
            .expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{{0, 0}, {3, 0}, {9, 2}}, 3)
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{{-19, 9}, {-15, -19}, {-5, -8}}, 10)
            .expect(-6);

}
