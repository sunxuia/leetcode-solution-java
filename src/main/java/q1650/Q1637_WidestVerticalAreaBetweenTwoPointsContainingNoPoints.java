package q1650;

import java.util.Arrays;
import java.util.Comparator;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1637. Widest Vertical Area Between Two Points Containing No Points
 * https://leetcode.com/problems/widest-vertical-area-between-two-points-containing-no-points/
 *
 * Given n points on a 2D plane where points[i] = [xi, yi], Return the widest vertical area between two points such that
 * no points are inside the area.
 *
 * A vertical area is an area of fixed-width extending infinitely along the y-axis (i.e., infinite height). The widest
 * vertical area is the one with the maximum width.
 *
 * Note that points on the edge of a vertical area are not considered included in the area.
 *
 * Example 1:
 * <img src="./Q1637_PIC.png">
 * Input: points = [[8,7],[9,9],[7,4],[9,7]]
 * Output: 1
 * Explanation: Both the red and the blue area are optimal.
 *
 * Example 2:
 *
 * Input: points = [[3,1],[9,0],[1,0],[1,4],[5,3],[8,8]]
 * Output: 3
 *
 * Constraints:
 *
 * n == points.length
 * 2 <= n <= 10^5
 * points[i].length == 2
 * 0 <= xi, yi <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1637_WidestVerticalAreaBetweenTwoPointsContainingNoPoints {

    @Answer
    public int maxWidthOfVerticalArea(int[][] points) {
        Arrays.sort(points, Comparator.comparingInt(p -> p[0]));
        int res = 0;
        for (int i = 1; i < points.length; i++) {
            res = Math.max(res, points[i][0] - points[i - 1][0]);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{8, 7}, {9, 9}, {7, 4}, {9, 7}})
            .expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{3, 1}, {9, 0}, {1, 0}, {1, 4}, {5, 3}, {8, 8}})
            .expect(3);

}
