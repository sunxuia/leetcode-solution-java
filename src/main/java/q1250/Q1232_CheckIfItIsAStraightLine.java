package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1232. Check If It Is a Straight Line
 * https://leetcode.com/problems/check-if-it-is-a-straight-line/
 *
 * You are given an array coordinates, coordinates[i] = [x, y], where [x, y] represents the coordinate of a point.
 * Check if these points make a straight line in the XY plane.
 *
 * Example 1:
 * <img src="Q1232_PIC1.png">
 * Input: coordinates = [[1,2],[2,3],[3,4],[4,5],[5,6],[6,7]]
 * Output: true
 *
 * Example 2:
 * <img src="Q1232_PIC2.png">
 * Input: coordinates = [[1,1],[2,2],[3,4],[4,5],[5,6],[7,7]]
 * Output: false
 *
 * Constraints:
 *
 * 2 <= coordinates.length <= 1000
 * coordinates[i].length == 2
 * -10^4 <= coordinates[i][0], coordinates[i][1] <= 10^4
 * coordinates contains no duplicate point.
 */
@RunWith(LeetCodeRunner.class)
public class Q1232_CheckIfItIsAStraightLine {

    @Answer
    public boolean checkStraightLine(int[][] coordinates) {
        int diff1X = coordinates[1][0] - coordinates[0][0];
        int diff1Y = coordinates[1][1] - coordinates[0][1];
        for (int i = 2; i < coordinates.length; i++) {
            int diffIX = coordinates[i][0] - coordinates[0][0];
            int diffIY = coordinates[i][1] - coordinates[0][1];
            if (diff1X == 0) {
                if (diffIX != 0) {
                    return false;
                }
            } else if (diff1Y == 0) {
                if (diffIY != 0) {
                    return false;
                }
            } else {
                if (diff1X * diffIY != diff1Y * diffIX) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * LeetCode 上一种计算面积的方式
     */
    @Answer
    public boolean checkStraightLine2(int[][] coordinates) {
        for (int i = 0; i < coordinates.length - 2; i++) {
            int x1 = coordinates[i][0];
            int x2 = coordinates[i + 1][0];
            int x3 = coordinates[i + 2][0];
            int y1 = coordinates[i][1];
            int y2 = coordinates[i + 1][1];
            int y3 = coordinates[i + 2][1];

            int area = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
            if (area != 0) {
                return false;
            }
        }
        return true;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{1, 2}, {2, 3}, {3, 4}, {4, 5}, {5, 6}, {6, 7}})
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1}, {2, 2}, {3, 4}, {4, 5}, {5, 6}, {7, 7}})
            .expect(false);

}
