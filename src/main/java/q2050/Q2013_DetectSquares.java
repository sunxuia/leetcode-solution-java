package q2050;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import util.asserthelper.AssertUtils;

/**
 * [Medium] 2013. Detect Squares
 * https://leetcode.com/problems/detect-squares/
 *
 * You are given a stream of points on the X-Y plane. Design an algorithm that:
 *
 * - Adds new points from the stream into a data structure. Duplicate points are allowed and should be treated as
 * different points.
 * - Given a query point, counts the number of ways to choose three points from the data structure such that the three
 * points and the query point form an axis-aligned square with positive area.
 *
 * An axis-aligned square is a square whose edges are all the same length and are either parallel or perpendicular to
 * the x-axis and y-axis.
 *
 * Implement the DetectSquares class:
 *
 * - DetectSquares() Initializes the object with an empty data structure.
 * - void add(int[] point) Adds a new point point = [x, y] to the data structure.
 * - int count(int[] point) Counts the number of ways to form axis-aligned squares with point point = [x, y] as
 * described above.
 *
 * Example 1:
 * (Q2013_PIC.png)
 * Input
 * ["DetectSquares", "add", "add", "add", "count", "count", "add", "count"]
 * [[], [[3, 10]], [[11, 2]], [[3, 2]], [[11, 10]], [[14, 8]], [[11, 2]], [[11, 10]]]
 * Output
 * [null, null, null, null, 1, 0, null, 2]
 *
 * Explanation
 * DetectSquares detectSquares = new DetectSquares();
 * detectSquares.add([3, 10]);
 * detectSquares.add([11, 2]);
 * detectSquares.add([3, 2]);
 * detectSquares.count([11, 10]); // return 1. You can choose:
 * //   - The first, second, and third points
 * detectSquares.count([14, 8]);  // return 0. The query point cannot form a square with any points in the data
 * structure.
 * detectSquares.add([11, 2]);    // Adding duplicate points is allowed.
 * detectSquares.count([11, 10]); // return 2. You can choose:
 * //   - The first, second, and third points
 * //   - The first, third, and fourth points
 *
 * Constraints:
 *
 * point.length == 2
 * 0 <= x, y <= 1000
 * At most 3000 calls in total will be made to add and count.
 */
public class Q2013_DetectSquares {

    private static class DetectSquares {

        Map<Integer, Map<Integer, Integer>> xMap = new HashMap<>();

        Map<Integer, Map<Integer, Integer>> yMap = new HashMap<>();

        public DetectSquares() {
        }

        public void add(int[] point) {
            int x = point[0], y = point[1];
            xMap.computeIfAbsent(x, k -> new HashMap<>())
                    .compute(y, (k, c) -> c == null ? 1 : c + 1);
            yMap.computeIfAbsent(y, k -> new HashMap<>())
                    .compute(x, (k, c) -> c == null ? 1 : c + 1);
        }

        public int count(int[] point) {
            int x = point[0], y = point[1];
            Map<Integer, Integer> xAxis = xMap.get(x);
            Map<Integer, Integer> yAxis = yMap.get(y);
            if (xAxis == null || yAxis == null) {
                return 0;
            }
            int res = 0;
            for (Integer yp : xAxis.keySet()) {
                int len = Math.abs(y - yp);
                if (len == 0) {
                    continue;
                }
                for (Integer xp : new int[]{x + len, x - len}) {
                    if (yAxis.containsKey(xp)) {
                        Integer pt = yMap.get(yp).get(xp);
                        if (pt != null) {
                            res += xAxis.get(yp) * yAxis.get(xp) * pt;
                        }
                    }
                }
            }
            return res;
        }
    }

    /**
     * Your DetectSquares object will be instantiated and called as such:
     * DetectSquares obj = new DetectSquares();
     * obj.add(point);
     * int param_2 = obj.count(point);
     */

    @Test
    public void testMethod() {
        int res;
        DetectSquares detectSquares = new DetectSquares();
        detectSquares.add(new int[]{3, 10});
        detectSquares.add(new int[]{11, 2});
        detectSquares.add(new int[]{3, 2});
        res = detectSquares.count(new int[]{11, 10});
        AssertUtils.assertEquals(1, res);

        res = detectSquares.count(new int[]{14, 8});
        AssertUtils.assertEquals(0, res);

        detectSquares.add(new int[]{11, 2});
        res = detectSquares.count(new int[]{11, 10});
        AssertUtils.assertEquals(2, res);
    }

}
