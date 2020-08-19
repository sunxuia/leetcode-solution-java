package q1000;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import q950.Q939_MinimumAreaRectangle;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 963. Minimum Area Rectangle II
 * https://leetcode.com/problems/minimum-area-rectangle-ii/
 *
 * Given a set of points in the xy-plane, determine the minimum area of any rectangle formed from these points, with
 * sides not necessarily parallel to the x and y axes.
 *
 * If there isn't any rectangle, return 0.
 *
 * Example 1:
 * (图Q963_PIC1.png)
 * Input: [[1,2],[2,1],[1,0],[0,1]]
 * Output: 2.00000
 * Explanation: The minimum area rectangle occurs at [1,2],[2,1],[1,0],[0,1], with an area of 2.
 *
 * Example 2:
 * (图Q963_PIC2.png)
 * Input: [[0,1],[2,1],[1,1],[1,0],[2,0]]
 * Output: 1.00000
 * Explanation: The minimum area rectangle occurs at [1,0],[1,1],[2,1],[2,0], with an area of 1.
 *
 * Example 3:
 * (图Q963_PIC3.png)
 * Input: [[0,3],[1,2],[3,1],[1,3],[2,1]]
 * Output: 0
 * Explanation: There is no possible rectangle to form from these points.
 *
 * Example 4:
 * (图Q963_PIC4.png)
 * Input: [[3,1],[1,1],[0,1],[2,1],[3,3],[3,2],[0,2],[2,3]]
 * Output: 2.00000
 * Explanation: The minimum area rectangle occurs at [2,1],[2,3],[3,3],[3,1], with an area of 2.
 *
 * Note:
 *
 * 1 <= points.length <= 50
 * 0 <= points[i][0] <= 40000
 * 0 <= points[i][1] <= 40000
 * All points are distinct.
 * Answers within 10^-5 of the actual value will be accepted as correct.
 *
 * 上一题 {@link Q939_MinimumAreaRectangle} 和上一题相比这里不需要和x 轴y 轴平行了
 */
@RunWith(LeetCodeRunner.class)
public class Q963_MinimumAreaRectangleII {

    @Answer
    public double minAreaFreeRect(int[][] points) {
        Map<Integer, Set<Integer>> axis = new HashMap<>();
        for (int[] point : points) {
            axis.computeIfAbsent(point[0], k -> new HashSet<>()).add(point[1]);
        }
        double res = Double.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    int[] A = rightAnglePoint(points[i], points[j], points[k]);
                    if (A == null) {
                        continue;
                    }
                    int[] B = points[i] == A ? points[j] : points[i];
                    int[] C = points[k] == A ? points[j] : points[k];
                    int[] last = lastPoint(A, B, C);
                    if (axis.getOrDefault(last[0], Collections.emptySet()).contains(last[1])) {
                        res = Math.min(res, area(A, B, C));
                    }
                }
            }
        }
        return res == Double.MAX_VALUE ? 0.0 : res;
    }

    // 寻找直角, 根据勾股定理求得
    private int[] rightAnglePoint(int[] A, int[] B, int[] C) {
        long a2 = (long) (B[0] - C[0]) * (B[0] - C[0]) + (B[1] - C[1]) * (B[1] - C[1]);
        long b2 = (long) (A[0] - C[0]) * (A[0] - C[0]) + (A[1] - C[1]) * (A[1] - C[1]);
        long c2 = (long) (A[0] - B[0]) * (A[0] - B[0]) + (A[1] - B[1]) * (A[1] - B[1]);
        if (a2 + b2 == c2) {
            return C;
        }
        if (a2 + c2 == b2) {
            return B;
        }
        if (b2 + c2 == a2) {
            return A;
        }
        return null;
    }

    // 根据3 个点求另一个点, ∠A 是直角, 根据矩形中点是对角线两端坐标的一半求得
    private int[] lastPoint(int[] A, int[] B, int[] C) {
        double midX = C[0] + (B[0] - C[0]) / 2.0;
        double midY = C[1] + (B[1] - C[1]) / 2.0;
        double x = A[0] + 2 * (midX - A[0]);
        double y = A[1] + 2 * (midY - A[1]);
        return new int[]{(int) x, (int) y};
    }

    // 求矩形面积, ∠A 是直角
    private double area(int[] A, int[] B, int[] C) {
        double b2 = (double) (A[0] - C[0]) * (A[0] - C[0]) + (A[1] - C[1]) * (A[1] - C[1]);
        double c2 = (double) (A[0] - B[0]) * (A[0] - B[0]) + (A[1] - B[1]) * (A[1] - B[1]);
        return Math.sqrt(b2 * c2);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new int[][]{{1, 2}, {2, 1}, {1, 0}, {0, 1}})
            .expectDouble(2.00000, 5)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new int[][]{{0, 1}, {2, 1}, {1, 1}, {1, 0}, {2, 0}})
            .expectDouble(1.00000, 5)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new int[][]{{0, 3}, {1, 2}, {3, 1}, {1, 3}, {2, 1}})
            .expectDouble(0, 5)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument(new int[][]{{3, 1}, {1, 1}, {0, 1}, {2, 1}, {3, 3}, {3, 2}, {0, 2}, {2, 3}})
            .expectDouble(2.00000, 5)
            .build();

}
