package q1050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1037. Valid Boomerang
 * https://leetcode.com/problems/valid-boomerang/
 *
 * A boomerang is a set of 3 points that are all distinct and not in a straight line.
 *
 * Given a list of three points in the plane, return whether these points are a boomerang.
 *
 * Example 1:
 *
 * Input: [[1,1],[2,3],[3,2]]
 * Output: true
 *
 * Example 2:
 *
 * Input: [[1,1],[2,2],[3,3]]
 * Output: false
 *
 * Note:
 *
 * points.length == 3
 * points[i].length == 2
 * 0 <= points[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1037_ValidBoomerang {

    @Answer
    public boolean isBoomerang(int[][] points) {
        int ax = points[0][0], ay = points[0][1];
        int bx = points[1][0], by = points[1][1];
        int cx = points[2][0], cy = points[2][1];
        return (ay - by) * (ax - cx) != (ax - bx) * (ay - cy);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 1}, {2, 3}, {3, 2}}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 1}, {2, 2}, {3, 3}}).expect(false);

}
