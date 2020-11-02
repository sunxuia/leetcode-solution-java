package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1266. Minimum Time Visiting All Points
 * https://leetcode.com/problems/minimum-time-visiting-all-points/
 *
 * On a plane there are n points with integer coordinates points[i] = [xi, yi]. Your task is to find the minimum time in
 * seconds to visit all points.
 *
 * You can move according to the next rules:
 *
 * 1. In one second always you can either move vertically, horizontally by one unit or diagonally (it means to move one
 * unit vertically and one unit horizontally in one second).
 * 2. You have to visit the points in the same order as they appear in the array.
 *
 * Example 1:
 * <img src="./Q1266_PIC.png">
 * Input: points = [[1,1],[3,4],[-1,0]]
 * Output: 7
 * Explanation: One optimal path is [1,1] -> [2,2] -> [3,3] -> [3,4] -> [2,3] -> [1,2] -> [0,1] -> [-1,0]
 * Time from [1,1] to [3,4] = 3 seconds
 * Time from [3,4] to [-1,0] = 4 seconds
 * Total time = 7 seconds
 *
 * Example 2:
 *
 * Input: points = [[3,2],[-2,2]]
 * Output: 5
 *
 * Constraints:
 *
 * points.length == n
 * 1 <= n <= 100
 * points[i].length == 2
 * -1000 <= points[i][0], points[i][1] <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1266_MinimumTimeVisitingAllPoints {

    @Answer
    public int minTimeToVisitAllPoints(int[][] points) {
        int res = 0;
        for (int i = 1; i < points.length; i++) {
            int x = Math.abs(points[i][0] - points[i - 1][0]);
            int y = Math.abs(points[i][1] - points[i - 1][1]);
            res += Math.max(x, y);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 1}, {3, 4}, {-1, 0}}).expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{3, 2}, {-2, 2}}).expect(5);

}
