package q250;

import org.junit.runner.RunWith;
import q850.Q850_RectangleAreaII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/rectangle-area/
 *
 * Find the total area covered by two rectilinear rectangles in a 2D plane.
 *
 * Each rectangle is defined by its bottom left corner and top right corner as shown in the figure.
 *
 * (见图 Q223_PIC.png )
 *
 * Example:
 *
 * Input: A = -3, B = 0, C = 3, D = 4, E = 0, F = -1, G = 9, H = 2
 * Output: 45
 *
 * Note:
 *
 * Assume that the total area is never beyond the maximum possible value of int.
 *
 * 题解: 求以 (A, B) - (C, D) 为对角线和 (E, F) - (G, H) 为对角线的两个矩形占据的面积, 因为2 个矩形可能有重叠, 重叠的面积不能重复计算.
 * 输入: (A, B) 和 (C, D) 分别是矩形的左下角和右上角, 另一个矩形一样.
 *
 * 下一题 {@link Q850_RectangleAreaII}
 */
@RunWith(LeetCodeRunner.class)
public class Q223_RectangleArea {

    /**
     * 尝试先找出所有的不相交的情况, 只有四种, 一个矩形在另一个的上下左右四个位置不重叠, 这四种情况下返回两个矩形面积之和.
     */
    @Answer
    public int computeArea(int A, int B, int C, int D, int E, int F, int G, int H) {
        int sum1 = (C - A) * (D - B), sum2 = (H - F) * (G - E);
        if (E >= C || F >= D || B >= H || A >= G) {
            return sum1 + sum2;
        }
        return sum1 - ((Math.min(G, C) - Math.max(A, E)) * (Math.min(D, H) - Math.max(B, F))) + sum2;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith(-3, 0, 3, 4, 0, -1, 9, 2).expect(45);

}
