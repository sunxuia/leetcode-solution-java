package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/maximal-rectangle/
 *
 * Given a 2D binary matrix filled with 0's and 1's, find the largest rectangle containing only 1's and return its area.
 *
 * Example:
 *
 * Input:
 * [
 * ["1","0","1","0","0"],
 * ["1","0","1","1","1"],
 * ["1","1","1","1","1"],
 * ["1","0","0","1","0"]
 * ]
 * Output: 6
 */
@RunWith(LeetCodeRunner.class)
public class Q085_MaximalRectangle {

    /**
     * 保存每行中每列的高度, 将该问题退化为上一题 {@link Q084_LargestRectangleInHistogram} 的解答
     */
    @Answer
    public int maximalRectangle(char[][] matrix) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return 0;
        }

        final int len = matrix[0].length;
        int res = 0;
        int[] height = new int[len];
        int[] leftLess = new int[len];
        leftLess[0] = -1;
        int[] rightLess = new int[len];
        rightLess[len - 1] = len;
        for (char[] chars : matrix) {
            // 当前行的高度
            for (int i = 0; i < len; i++) {
                height[i] = (chars[i] - '0') * (height[i] + 1);
            }

            // 同上一题计算最大矩形面积
            for (int i = 1; i < len; i++) {
                int prev = i - 1;
                while (prev > -1 && height[prev] >= height[i]) {
                    prev = leftLess[prev];
                }
                leftLess[i] = prev;
            }
            for (int i = len - 2; i >= 0; i--) {
                int prev = i + 1;
                while (prev < len && height[i] <= height[prev]) {
                    prev = rightLess[prev];
                }
                rightLess[i] = prev;
            }
            for (int i = 0; i < len; i++) {
                res = Math.max(res, (rightLess[i] - leftLess[i] - 1) * height[i]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation
            .create(new char[][]{
                    {'1', '0', '1', '0', '0'},
                    {'1', '0', '1', '1', '1'},
                    {'1', '1', '1', '1', '1'},
                    {'1', '0', '0', '1', '0'}
            }).expect(6);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new char[][]{
                    {'0', '1'}
            }).expect(1);

    @TestData
    public DataExpectation border = DataExpectation
            .create(new char[0][0]).expect(0);

}
