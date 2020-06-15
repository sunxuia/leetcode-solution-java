package q850;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rectangle-overlap/
 *
 * A rectangle is represented as a list [x1, y1, x2, y2], where (x1, y1) are the coordinates of its bottom-left
 * corner, and (x2, y2) are the coordinates of its top-right corner.
 *
 * Two rectangles overlap if the area of their intersection is positive.  To be clear, two rectangles that only touch
 * at the corner or edges do not overlap.
 *
 * Given two (axis-aligned) rectangles, return whether they overlap.
 *
 * Example 1:
 *
 * Input: rec1 = [0,0,2,2], rec2 = [1,1,3,3]
 * Output: true
 *
 * Example 2:
 *
 * Input: rec1 = [0,0,1,1], rec2 = [1,0,2,1]
 * Output: false
 *
 * Notes:
 *
 * Both rectangles rec1 and rec2 are lists of 4 integers.
 * All coordinates in rectangles will be between -10^9 and 10^9.
 */
@RunWith(LeetCodeRunner.class)
public class Q836_RectangleOverlap {

    // 思路参见 https://www.cnblogs.com/grandyang/p/10367583.html
    @Answer
    public boolean isRectangleOverlap(int[] rec1, int[] rec2) {
        return rec1[0] < rec2[2] && rec2[0] < rec1[2] && rec1[1] < rec2[3] && rec2[1] < rec1[3];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{0, 0, 2, 2}, new int[]{1, 1, 3, 3}).expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{0, 0, 1, 1}, new int[]{1, 0, 2, 1}).expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[]{7, 8, 13, 15}, new int[]{10, 8, 12, 20}).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation
            .createWith(new int[]{2, 17, 6, 20}, new int[]{3, 8, 6, 20}).expect(true);

    @TestData
    public DataExpectation normal3 = DataExpectation
            .createWith(new int[]{-7, -3, 10, 5}, new int[]{-6, -5, 5, 10}).expect(true);

}
