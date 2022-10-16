package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1970. Last Day Where You Can Still Cross
 * https://leetcode.com/problems/last-day-where-you-can-still-cross/
 *
 * There is a 1-based binary matrix where 0 represents land and 1 represents water. You are given integers row and col
 * representing the number of rows and columns in the matrix, respectively.
 *
 * Initially on day 0, the entire matrix is land. However, each day a new cell becomes flooded with water. You are given
 * a 1-based 2D array cells, where cells[i] = [ri, ci] represents that on the ith day, the cell on the rith row and cith
 * column (1-based coordinates) will be covered with water (i.e., changed to 1).
 *
 * You want to find the last day that it is possible to walk from the top to the bottom by only walking on land cells.
 * You can start from any cell in the top row and end at any cell in the bottom row. You can only travel in the four
 * cardinal directions (left, right, up, and down).
 *
 * Return the last day where it is possible to walk from the top to the bottom by only walking on land cells.
 *
 * Example 1:
 * (图Q1970_PIC1.png)
 * Input: row = 2, col = 2, cells = [[1,1],[2,1],[1,2],[2,2]]
 * Output: 2
 * Explanation: The above image depicts how the matrix changes each day starting from day 0.
 * The last day where it is possible to cross from top to bottom is on day 2.
 *
 * Example 2:
 * (图Q1970_PIC2.png)
 * Input: row = 2, col = 2, cells = [[1,1],[1,2],[2,1],[2,2]]
 * Output: 1
 * Explanation: The above image depicts how the matrix changes each day starting from day 0.
 * The last day where it is possible to cross from top to bottom is on day 1.
 *
 * Example 3:
 * (图Q1970_PIC3.png)
 * Input: row = 3, col = 3, cells = [[1,2],[2,1],[3,3],[2,2],[1,1],[1,3],[2,3],[3,2],[3,1]]
 * Output: 3
 * Explanation: The above image depicts how the matrix changes each day starting from day 0.
 * The last day where it is possible to cross from top to bottom is on day 3.
 *
 * Constraints:
 *
 * 2 <= row, col <= 2 * 10^4
 * 4 <= row * col <= 2 * 10^4
 * cells.length == row * col
 * 1 <= ri <= row
 * 1 <= ci <= col
 * All the values of cells are unique.
 */
@RunWith(LeetCodeRunner.class)
public class Q1970_LastDayWhereYouCanStillCross {

    @Answer
    public int latestDayToCross(int row, int col, int[][] cells) {
        int[] roots = new int[row * col + 2];
        for (int i = 0; i < roots.length; i++) {
            roots[i] = i;
        }
        // 分别表示地图左边的水和地图右边的水(地图外面), 只要左右的水连起来了就隔断了
        int left = row * col, right = row * col + 1;
        boolean[][] water = new boolean[row][col];
        for (int i = 0; i < cells.length; i++) {
            final int y = cells[i][0] - 1;
            final int x = cells[i][1] - 1;
            for (int dy = -1; dy <= 1; dy++) {
                for (int dx = -1; dx <= 1; dx++) {
                    int ny = y + dy, nx = x + dx;
                    if (nx == -1) {
                        // 和左边的水连起来了
                        union(roots, left, y * col + x);
                    } else if (nx == col) {
                        // 和右边的水连起来了
                        union(roots, right, y * col + x);
                    } else if (0 <= ny && ny < row && 0 <= nx && nx < col && water[ny][nx]) {
                        // 和周边的水连起来了
                        union(roots, ny * col + nx, y * col + x);
                    }
                }
            }
            if (findRoot(roots, left) == findRoot(roots, right)) {
                // 左右的水连起来了
                return i;
            }
            water[y][x] = true;
        }
        return cells.length;
    }

    private void union(int[] roots, int i, int j) {
        roots[findRoot(roots, j)] = findRoot(roots, i);
    }

    private int findRoot(int[] roots, int i) {
        if (roots[i] == i) {
            return i;
        }
        return roots[i] = findRoot(roots, roots[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(2, 2, new int[][]{{1, 1}, {2, 1}, {1, 2}, {2, 2}})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(2, 2, new int[][]{{1, 1}, {1, 2}, {2, 1}, {2, 2}})
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(3, 3, new int[][]{{1, 2}, {2, 1}, {3, 3}, {2, 2}, {1, 1}, {1, 3}, {2, 3}, {3, 2}, {3, 1}})
            .expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(6, 2, new int[][]{
                    {4, 2}, {6, 2}, {2, 1}, {4, 1}, {6, 1}, {3, 1}, {2, 2}, {3, 2}, {1, 1}, {5, 1}, {5, 2}, {1, 2}})
            .expect(3);

}
