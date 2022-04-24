package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DebugWith;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import java.util.*;

/**
 * [Medium] 1861. Rotating the Box
 * https://leetcode.com/problems/rotating-the-box/
 *
 * You are given an m x n matrix of characters box representing a side-view of a box. Each cell of the box is one of the
 * following:
 *
 * - A stone '#'
 * - A stationary obstacle '*'
 * - Empty '.'
 *
 * The box is rotated 90 degrees clockwise, causing some of the stones to fall due to gravity. Each stone falls down
 * until it lands on an obstacle, another stone, or the bottom of the box. Gravity does not affect the obstacles'
 * positions, and the inertia from the box's rotation does not affect the stones' horizontal positions.
 *
 * It is guaranteed that each stone in box rests on an obstacle, another stone, or the bottom of the box.
 *
 * Return an n x m matrix representing the box after the rotation described above.
 *
 * Example 1:
 * (图Q1861_PIC1.png)
 * > Input: box = [["#",".","#"]]
 * > Output: [["."],
 * >          ["#"],
 * >          ["#"]]
 *
 * Example 2:
 * (图Q1861_PIC2.png)
 * > Input: box = [["#",".","*","."],
 * >               ["#","#","*","."]]
 * > Output: [["#","."],
 * >          ["#","#"],
 * >          ["*","*"],
 * >          [".","."]]
 *
 * Example 3:
 * (图Q1861_PIC3.png)
 * > Input: box = [["#","#","*",".","*","."],
 * >               ["#","#","#","*",".","."],
 * >               ["#","#","#",".","#","."]]
 * > Output: [[".","#","#"],
 * >          [".","#","#"],
 * >          ["#","#","*"],
 * >          ["#","*","."],
 * >          ["#",".","*"],
 * >          ["#",".","."]]
 *
 * Constraints:
 *
 * m == box.length
 * n == box[i].length
 * 1 <= m, n <= 500
 * box[i][j] is either '#', '*', or '.'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1861_RotatingTheBox {

    @Answer
    public char[][] rotateTheBox(char[][] box) {
        final int m = box.length, n = box[0].length;
        char[][] res = new char[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res[i][j] = box[m - 1 - j][i];
            }
        }

        for (int i = 0; i < m; i++) {
            int count = 0;
            for (int j = 0; j < n; j++) {
                if (res[j][i] == '#') {
                    res[j][i] = '.';
                    count++;
                } else if (res[j][i] == '*') {
                    while (count > 0) {
                        res[j - count][i] = '#';
                        count--;
                    }
                }
            }
            while (count > 0) {
                res[n - count][i] = '#';
                count--;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new char[][]{{'#', '.', '#'}})
            .expect(new char[][]{
                    {'.'},
                    {'#'},
                    {'#'}});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new char[][]{
            {'#', '.', '*', '.'},
            {'#', '#', '*', '.'}
    }).expect(new char[][]{
            {'#', '.'},
            {'#', '#'},
            {'*', '*'},
            {'.', '.'}});

    @TestData
    public DataExpectation example3 = DataExpectation.create(new char[][]{
            {'#', '#', '*', '.', '*', '.'},
            {'#', '#', '#', '*', '.', '.'},
            {'#', '#', '#', '.', '#', '.'}
    }).expect(new char[][]{
            {'.', '#', '#'},
            {'.', '#', '#'},
            {'#', '#', '*'},
            {'#', '*', '.'},
            {'#', '.', '*'},
            {'#', '.', '.'}});

}
