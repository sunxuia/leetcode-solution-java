package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1240. Tiling a Rectangle with the Fewest Squares
 * https://leetcode.com/problems/tiling-a-rectangle-with-the-fewest-squares/
 *
 * Given a rectangle of size n x m, find the minimum number of integer-sided squares that tile the rectangle.
 *
 * Example 1:
 * <img src="./Q1240_PIC1.png">
 * Input: n = 2, m = 3
 * Output: 3
 * Explanation: 3 squares are necessary to cover the rectangle.
 * 2 (squares of 1x1)
 * 1 (square of 2x2)
 *
 * Example 2:
 * <img src="./Q1240_PIC2.png">
 * Input: n = 5, m = 8
 * Output: 5
 *
 * Example 3:
 * <img src="./Q1240_PIC3.png">
 * Input: n = 11, m = 13
 * Output: 6
 *
 * Constraints:
 *
 * 1 <= n <= 13
 * 1 <= m <= 13
 */
@RunWith(LeetCodeRunner.class)
public class Q1240_TilingARectangleWithTheFewestSquares {

    /**
     * 可以将大矩形分解为多个小矩形递归求解
     * 参考文档
     * https://leetcode.jp/leetcode-1240-tiling-a-rectangle-with-the-fewest-squares-解题思路分析/
     */
    @Answer
    public int tilingRectangle(int n, int m) {
        if (m == n) {
            return 1; // 如果长宽相同，即是正方形，返回1
        }
        helper(new int[n][m], 0, 0, 0); // 递归找最优解
        return min;
    }

    int min = Integer.MAX_VALUE; // 返回结果，先设个最大值

    // visited表示哪些格子已经放入正方形
    // row和col代表当前点的坐标
    // count为到目前为止已经摆放了几个正方形
    private void helper(int[][] visited, int row, int col, int count) {
        // 如果当前的count已经大于等于之前的最小值，那没有必要继续摆放下去
        if (count >= min) {
            return;
        }
        // 从当前点开始循环，找到下一个还没摆放的点
        for (int r = row; r < visited.length; r++) {
            for (int c = col; c < visited[0].length; c++) {
                // 如果当前点已经摆放过，就跳过
                if (visited[r][c] == 1) {
                    continue;
                }
                // 以当前点为起点，向右下方向理论上能画出的最大正方形边长
                int lengthMax = Math.min(visited.length - r, visited[0].length - c);
                // 从最大边长循环到1画正方形
                for (int i = lengthMax; i >= 1; i--) {
                    // 如果当前正方形区域内存在以访问过的点，跳过，缩小正方形边长
                    if (!findRectangle(visited, r, c, i)) {
                        continue;
                    }
                    // 将当前正方形范围标记为已访问状态
                    updateVisited(visited, r, c, i, 1);
                    // 递归到下一层，下一层的起点从当前正方形的右上角开始向后
                    helper(visited, r, c + i, count + 1);
                    // 为了下次循环，还原当前正方形范围的访问状态
                    updateVisited(visited, r, c, i, 0);
                }
                return;
            }
            col = 0;
        }
        // 程序到此说明已经铺满所有区域。
        min = Math.min(min, count);
    }

    // 查看正方形区域内是否存在已访问过的点
    private boolean findRectangle(int[][] visited, int row, int col, int length) {
        int endRow = row + length, endCol = col + length;
        for (int i = row; i < endRow; i++) {
            for (int j = col; j < endCol; j++) {
                if (visited[i][j] == 1) {
                    return false;
                }
            }
        }
        return true;
    }

    // 更新正方形内访问记录
    private void updateVisited(int[][] visited, int row, int col, int length, int val) {
        int endRow = row + length, endCol = col + length;
        for (int r = row; r < endRow; r++) {
            for (int c = col; c < endCol; c++) {
                visited[r][c] = val;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 3).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(5, 8).expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(11, 13).expect(6);

}
