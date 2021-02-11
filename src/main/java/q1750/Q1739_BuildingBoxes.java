package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1739. Building Boxes
 * https://leetcode.com/problems/building-boxes/
 *
 * You have a cubic storeroom where the width, length, and height of the room are all equal to n units. You are asked to
 * place n boxes in this room where each box is a cube of unit side length. There are however some rules to placing the
 * boxes:
 *
 * You can place the boxes anywhere on the floor.
 * If box x is placed on top of the box y, then each side of the four vertical sides of the box y must either be
 * adjacent to another box or to a wall.
 *
 * Given an integer n, return the minimum possible number of boxes touching the floor.
 *
 * Example 1:
 * <img src="./Q1739_PIC1.png">
 * Input: n = 3
 * Output: 3
 * Explanation: The figure above is for the placement of the three boxes.
 * These boxes are placed in the corner of the room, where the corner is on the left side.
 *
 * Example 2:
 * <img src="./Q1739_PIC2.png">
 * Input: n = 4
 * Output: 3
 * Explanation: The figure above is for the placement of the four boxes.
 * These boxes are placed in the corner of the room, where the corner is on the left side.
 *
 * Example 3:
 * <img src="./Q1739_PIC3.png">
 * Input: n = 10
 * Output: 6
 * Explanation: The figure above is for the placement of the ten boxes.
 * These boxes are placed in the corner of the room, where the corner is on the back side.
 *
 * Constraints:
 *
 * 1 <= n <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1739_BuildingBoxes {

    /**
     * 找规律的题目
     *
     * n   |   1   2   3   4   5   6   7   8   9  10  11  12  13  14  15  16  17  18  19  20
     * res |   1   2   3   3   4   5   5   6   6   6   7   8   8   9   9   9  10  10  10  10
     * >   |   -  ----------  ----------------------  --------------------------------------
     * n   |  21  22  23  34  25  26  27  28  29  30  31  32  33  34  35  36  37  38  39  40 ...
     * res |  11  12  12  13  13  13  14  14  14  14  14  15  15  15  15  15  16  17  17  18 ...
     * >   |  --------------------------------------------------------------  -------------- ...
     */
    @Answer
    public int minimumBoxes(int n) {
        // 找到 <= n 的满排列 (4, 10, 20, 35 那样盖满的情况)
        int sum = 1, bottom = 1, border = 1;
        while (sum + bottom + border + 1 <= n) {
            border += 1;
            bottom += border;
            sum += bottom;
        }
        // 从 sum 的排列开始寻找到 n
        for (int step = 1; sum < n; step++) {
            sum += step;
            bottom++;
        }
        return bottom;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(3).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(4).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(10).expect(6);

}
