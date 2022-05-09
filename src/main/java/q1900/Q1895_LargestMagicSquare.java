package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1895. Largest Magic Square
 * https://leetcode.com/problems/largest-magic-square/
 *
 * A k x k magic square is a k x k grid filled with integers such that every row sum, every column sum, and both
 * diagonal sums are all equal. The integers in the magic square do not have to be distinct. Every 1 x 1 grid is
 * trivially a magic square.
 *
 * Given an m x n integer grid, return the size (i.e., the side length k) of the largest magic square that can be found
 * within this grid.
 *
 * Example 1:
 * (图Q1895_PIC1.png)
 * Input: grid = [[7,1,4,5,6],[2,5,1,6,4],[1,5,4,3,2],[1,2,7,3,4]]
 * Output: 3
 * Explanation: The largest magic square has a size of 3.
 * Every row sum, column sum, and diagonal sum of this magic square is equal to 12.
 * - Row sums: 5+1+6 = 5+4+3 = 2+7+3 = 12
 * - Column sums: 5+5+2 = 1+4+7 = 6+3+3 = 12
 * - Diagonal sums: 5+4+3 = 6+4+2 = 12
 *
 * Example 2:
 * (图Q1895_PIC2.png)
 * Input: grid = [[5,1,3,1],[9,3,3,1],[1,3,3,8]]
 * Output: 2
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * 1 <= grid[i][j] <= 10^6
 */
@RunWith(LeetCodeRunner.class)
public class Q1895_LargestMagicSquare {

    /**
     * 比较无聊的题目.
     */
    @Answer
    public int largestMagicSquare(int[][] grid) {
        final int m = grid.length;
        final int n = grid[0].length;
        // 从左往右的水平和 (horizon)
        int[][] hSums = new int[m][n];
        for (int i = 0; i < m; i++) {
            hSums[i][0] = grid[i][0];
            for (int j = 1; j < n; j++) {
                hSums[i][j] = hSums[i][j - 1] + grid[i][j];
            }
        }
        // 从上到下的垂直和 (vertical)
        int[][] vSums = new int[m][n];
        for (int j = 0; j < n; j++) {
            vSums[0][j] = grid[0][j];
            for (int i = 1; i < m; i++) {
                vSums[i][j] = vSums[i - 1][j] + grid[i][j];
            }
        }
        // 从左上角到右下角的对角线和 (diagonal)
        int[][] dSums = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dSums[i][j] = grid[i][j];
                if (i > 0 && j > 0) {
                    dSums[i][j] += dSums[i - 1][j - 1];
                }
            }
        }
        // 从右上角到左下角的对对角线和 (reverse diagonal)
        int[][] rSums = new int[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rSums[i][j] = grid[i][j];
                if (i > 0 && j < n - 1) {
                    rSums[i][j] += rSums[i - 1][j + 1];
                }
            }
        }

        // 以 (i,j)为左上角, (y,x) 为右下角, 检查正方形
        int res = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int size = res + 1;
                loop:
                for (int y = i + size - 1, x = j + size - 1;
                        y < m && x < n;
                        y++, x++, size++) {
                    int d = dSums[y][x] - dSums[i][j] + grid[i][j];
                    int r = rSums[y][j] - rSums[i][x] + grid[i][x];
                    if (d != r) {
                        continue loop;
                    }
                    for (int a = i; a <= y; a++) {
                        int sum = hSums[a][x] - hSums[a][j] + grid[a][j];
                        if (d != sum) {
                            continue loop;
                        }
                    }
                    for (int b = j; b <= x; b++) {
                        int sum = vSums[y][b] - vSums[i][b] + grid[i][b];
                        if (d != sum) {
                            continue loop;
                        }
                    }
                    res = size;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
                    {7, 1, 4, 5, 6},
                    {2, 5, 1, 6, 4},
                    {1, 5, 4, 3, 2},
                    {1, 2, 7, 3, 4}})
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
                    {5, 1, 3, 1},
                    {9, 3, 3, 1},
                    {1, 3, 3, 8}})
            .expect(2);

}
