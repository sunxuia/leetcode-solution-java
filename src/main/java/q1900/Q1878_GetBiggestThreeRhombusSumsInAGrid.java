package q1900;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1878. Get Biggest Three Rhombus Sums in a Grid
 * https://leetcode.com/problems/get-biggest-three-rhombus-sums-in-a-grid/
 *
 * You are given an m x n integer matrix grid.
 *
 * A rhombus sum is the sum of the elements that form the border of a regular rhombus shape in grid???. The rhombus must
 * have the shape of a square rotated 45 degrees with each of the corners centered in a grid cell. Below is an image of
 * four valid rhombus shapes with the corresponding colored cells that should be included in each rhombus sum:
 * (图Q1878_PIC1.png)
 * Note that the rhombus can have an area of 0, which is depicted by the purple rhombus in the bottom right corner.
 *
 * Return the biggest three distinct rhombus sums in the grid in descending order. If there are less than three distinct
 * values, return all of them.
 *
 * Example 1:
 * (图Q1878_PIC2.png)
 * Input: grid = [[3,4,5,1,3],[3,3,4,2,3],[20,30,200,40,10],[1,5,5,4,1],[4,3,2,2,5]]
 * Output: [228,216,211]
 * Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
 * - Blue: 20 + 3 + 200 + 5 = 228
 * - Red: 200 + 2 + 10 + 4 = 216
 * - Green: 5 + 200 + 4 + 2 = 211
 *
 * Example 2:
 * (图Q1878_PIC3.png)
 * Input: grid = [[1,2,3],[4,5,6],[7,8,9]]
 * Output: [20,9,8]
 * Explanation: The rhombus shapes for the three biggest distinct rhombus sums are depicted above.
 * - Blue: 4 + 2 + 6 + 8 = 20
 * - Red: 9 (area 0 rhombus in the bottom right corner)
 * - Green: 8 (area 0 rhombus in the bottom middle)
 *
 * Example 3:
 *
 * Input: grid = [[7,7,7]]
 * Output: [7]
 * Explanation: All three possible rhombus sums are the same, so return [7].
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m, n <= 50
 * 1 <= grid[i][j] <= 10^5
 */
@RunWith(LeetCodeRunner.class)
public class Q1878_GetBiggestThreeRhombusSumsInAGrid {

    @Answer
    public int[] getBiggestThree(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[][] sumsF = getSumsSlashForward(grid);
        int[][] sumsB = getSumsSlashBack(grid);

        int[] res = new int[3];
        for (int y = 0; y < m; y++) {
            for (int x = 0; x < n; x++) {
                addDistinctTop3(res, grid[y][x]);
                for (int top = y - 1, right = x + 1, bottom = y + 1, left = x - 1;
                        top >= 0 && right < n && bottom < m && left >= 0;
                        top--, right++, bottom++, left--) {
                    int sum = sumsB[y][right] - sumsB[top][x]
                            + sumsB[bottom][x] - sumsB[y][left]
                            + sumsF[top][x] - sumsF[y][left]
                            + sumsF[y][right] - sumsF[bottom][x]
                            + grid[y][left] - grid[y][right];
                    addDistinctTop3(res, sum);
                }
            }
        }

        if (res[1] == 0) {
            return new int[]{res[0]};
        } else if (res[2] == 0) {
            return new int[]{res[0], res[1]};
        } else {
            return res;
        }
    }

    /**
     * /这样斜着的行的和, 从左下角起始.
     * res[i][j] 表示从(i, j) 左下角的线起始到 (i, j) (含) 的总和.
     */
    private int[][] getSumsSlashForward(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            int prev = 0;
            for (int j = 0; j <= i && j < n; j++) {
                int y = i - j, x = j;
                res[y][x] = prev + grid[y][x];
                prev = res[y][x];
            }
        }
        for (int i = 1; i < n; i++) {
            int prev = 0;
            for (int j = 0; j < m && i + j < n; j++) {
                int y = m - 1 - j, x = i + j;
                res[y][x] = prev + grid[y][x];
                prev = res[y][x];
            }
        }
        return res;
    }

    /**
     * \这样斜着的行的和, 从左上角起始.
     * res[i][j] 表示从(i, j) 左上角的线起始到 (i, j) (含) 的总和.
     */
    private int[][] getSumsSlashBack(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[][] res = new int[m][n];
        for (int i = n - 1; i > 0; i--) {
            int prev = 0;
            for (int j = 0; j < m && i + j < n; j++) {
                int y = j, x = i + j;
                res[y][x] = prev + grid[y][x];
                prev = res[y][x];
            }
        }
        for (int i = 0; i < m; i++) {
            int prev = 0;
            for (int j = 0; i + j < m && j < n; j++) {
                int y = i + j, x = j;
                res[y][x] = prev + grid[y][x];
                prev = res[y][x];
            }
        }
        return res;
    }

    private void addDistinctTop3(int[] max, int val) {
        for (int i = 0; i < 3; i++) {
            if (val == max[i]) {
                return;
            }
            if (val > max[i]) {
                int t = max[i];
                max[i] = val;
                val = t;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
                    {3, 4, 5, 1, 3},
                    {3, 3, 4, 2, 3},
                    {20, 30, 200, 40, 10},
                    {1, 5, 5, 4, 1},
                    {4, 3, 2, 2, 5}})
            .expect(new int[]{228, 216, 211});

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}})
            .expect(new int[]{20, 9, 8});

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new int[][]{{7, 7, 7}})
            .expect(new int[]{7});

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create(new int[][]{
                    {20, 17, 9, 13, 5, 2, 9, 1, 5},
                    {14, 9, 9, 9, 16, 18, 3, 4, 12},
                    {18, 15, 10, 20, 19, 20, 15, 12, 11},
                    {19, 16, 19, 18, 8, 13, 15, 14, 11},
                    {4, 19, 5, 2, 19, 17, 7, 2, 2}})
            .expect(new int[]{107, 103, 102});

}
