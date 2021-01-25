package q1600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1584. Min Cost to Connect All Points
 * https://leetcode.com/problems/min-cost-to-connect-all-points/
 *
 * You are given an array points representing integer coordinates of some points on a 2D-plane, where points[i] = [xi,
 * yi].
 *
 * The cost of connecting two points [xi, yi] and [xj, yj] is the manhattan distance between them: |xi - xj| + |yi -
 * yj|, where |val| denotes the absolute value of val.
 *
 * Return the minimum cost to make all points connected. All points are connected if there is exactly one simple path
 * between any two points.
 *
 * Example 1:
 * <img src="./Q1584_PIC1.png">
 * Input: points = [[0,0],[2,2],[3,10],[5,2],[7,0]]
 * Output: 20
 * Explanation:
 * <img src="./Q1584_PIC2.png">
 * We can connect the points as shown above to get the minimum cost of 20.
 * Notice that there is a unique path between every pair of points.
 *
 * Example 2:
 *
 * Input: points = [[3,12],[-2,5],[-4,1]]
 * Output: 18
 *
 * Example 3:
 *
 * Input: points = [[0,0],[1,1],[1,0],[-1,1]]
 * Output: 4
 *
 * Example 4:
 *
 * Input: points = [[-1000000,-1000000],[1000000,1000000]]
 * Output: 4000000
 *
 * Example 5:
 *
 * Input: points = [[0,0]]
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= points.length <= 1000
 * -10^6 <= xi, yi <= 10^6
 * All pairs (xi, yi) are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1584_MinCostToConnectAllPoints {

    @Answer
    public int minCostConnectPoints(int[][] points) {
        final int n = points.length;
        int res = 0, min = 0;
        int[] dists = new int[n];
        Arrays.fill(dists, 1, n, Integer.MAX_VALUE);
        for (int i = 1; i < n; i++) {
            res += dists[min];
            // 更新最小距离
            for (int j = 0; j < n; j++) {
                dists[j] = Math.min(dists[j], Math.abs(points[min][0] - points[j][0])
                        + Math.abs(points[min][1] - points[j][1]));
            }
            // 找到新的最小距离
            for (int j = 0; j < n; j++) {
                if (dists[j] != 0 && (dists[min] == 0 || dists[min] > dists[j])) {
                    min = j;
                }
            }
        }
        res += dists[min];
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{0, 0}, {2, 2}, {3, 10}, {5, 2}, {7, 0}})
            .expect(20);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{3, 12}, {-2, 5}, {-4, 1}})
            .expect(18);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{0, 0}, {1, 1}, {1, 0}, {-1, 1}})
            .expect(4);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{{-1000000, -1000000}, {1000000, 1000000}})
            .expect(4000000);

    @TestData
    public DataExpectation example5 = DataExpectation.create(new int[][]{{0, 0}})
            .expect(0);

}
