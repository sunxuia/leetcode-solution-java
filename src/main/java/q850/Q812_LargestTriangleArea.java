package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/largest-triangle-area/
 *
 * You have a list of points in the plane. Return the area of the largest triangle that can be formed by any 3 of
 * the points.
 *
 * Example:
 * Input: points = [[0,0],[0,1],[1,0],[0,2],[2,0]]
 * Output: 2
 * Explanation:
 * The five points are show in the figure below. The orange triangle is the largest.
 *
 * (图 Q812_PIC.png)
 *
 * Notes:
 *
 * 3 <= points.length <= 50.
 * No points will be duplicated.
 * -50 <= points[i][j] <= 50.
 * Answers within 10^-6 of the true value will be accepted as correct.
 */
@RunWith(LeetCodeRunner.class)
public class Q812_LargestTriangleArea {

    @Answer
    public double largestTriangleArea(int[][] points) {
        double res = 0.0;
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    double area = area(points[i], points[j], points[k]);
                    if (res < area) {
                        res = area;
                    }
                }
            }
        }
        return res;
    }

    // 底 * 高的方式求面积
//    private double area(int[] A, int[] B, int[] C) {
//        double a2 = distance2(B, C);
//        double b2 = distance2(A, C);
//        double c2 = distance2(A, B);
//        double h2 = (4 * a2 * c2 - Math.pow(a2 + c2 - b2, 2)) / c2 / 4;
//        return Math.sqrt(c2 * h2) / 2;
//    }

    // 海伦公式求面积
    private double area(int[] A, int[] B, int[] C) {
        double a = Math.sqrt(distance2(B, C));
        double b = Math.sqrt(distance2(A, C));
        double c = Math.sqrt(distance2(A, B));
        double p = (a + b + c) / 2;
        // 由于计算精度问题, 这里的返回值有可能是 NaN (根号里面是个很小的负数)
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    private double distance2(int[] A, int[] B) {
        return Math.pow(B[0] - A[0], 2) + Math.pow(B[1] - A[1], 2);
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[][]{{0, 0}, {0, 1}, {1, 0}, {0, 2}, {2, 0}})
            .expectDouble(2.0, 6)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument(new int[][]{{-35, 19}, {40, 19}, {27, -20}, {35, -3}, {44, 20}, {22, -21}, {35, 33}, {-19, 42},
                    {11, 47}, {11, 37}})
            .expectDouble(1799.0, 6)
            .build();

}
