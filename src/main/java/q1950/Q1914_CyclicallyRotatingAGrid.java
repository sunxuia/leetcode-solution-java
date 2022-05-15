package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1914. Cyclically Rotating a Grid
 * https://leetcode.com/problems/cyclically-rotating-a-grid/
 *
 * You are given an m x n integer matrix grid???, where m and n are both even integers, and an integer k.
 * (图Q1914_PIC1.png)
 * The matrix is composed of several layers, which is shown in the below image, where each color is its own layer:
 *
 * A cyclic rotation of the matrix is done by cyclically rotating each layer in the matrix. To cyclically rotate a layer
 * once, each element in the layer will take the place of the adjacent element in the counter-clockwise direction. An
 * example rotation is shown below:
 * (图Q1914_PIC2.jpg)
 * Return the matrix after applying k cyclic rotations to it.
 *
 * Example 1:
 * (图Q1914_PIC3.png)
 * Input: grid = [[40,10],[30,20]], k = 1
 * Output: [[10,20],[40,30]]
 * Explanation: The figures above represent the grid at every state.
 *
 * Example 2:
 * (图Q1914_PIC4.png)
 * Input: grid = [[1,2,3,4],[5,6,7,8],[9,10,11,12],[13,14,15,16]], k = 2
 * Output: [[3,4,8,12],[2,11,10,16],[1,7,6,15],[5,9,13,14]]
 * Explanation: The figures above represent the grid at every state.
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 2 <= m, n <= 50
 * Both m and n are even integers.
 * 1 <= grid[i][j] <= 5000
 * 1 <= k <= 10^9
 */
@RunWith(LeetCodeRunner.class)
public class Q1914_CyclicallyRotatingAGrid {

    @Answer
    public int[][] rotateGrid(int[][] grid, int k) {
        final int m = grid.length;
        final int n = grid[0].length;
        int[][] res = new int[m][n];
        int cycle = Math.min(m, n) / 2;
        int[] fromPos = new int[2];
        int[] toPos = new int[2];
        for (int c = 0; c < cycle; c++) {
            int height = m - c * 2 - 1;
            int width = n - c * 2 - 1;
            int len = 2 * height + 2 * width;
            for (int i = 0; i < len; i++) {
                setPos(m, n, c, i, fromPos);
                setPos(m, n, c, i + k, toPos);
                res[toPos[0]][toPos[1]] = grid[fromPos[0]][fromPos[1]];
            }
        }
        return res;
    }

    private void setPos(int m, int n, int c, int dist, int[] pos) {
        int height = m - c * 2 - 1;
        int width = n - c * 2 - 1;
        dist %= 2 * width + 2 * height;
        if (dist <= height) {
            pos[0] = c + dist;
            pos[1] = c;
            return;
        }
        dist -= height;
        if (dist <= width) {
            pos[0] = m - 1 - c;
            pos[1] = c + dist;
            return;
        }
        dist -= width;
        if (dist <= height) {
            pos[0] = m - 1 - c - dist;
            pos[1] = n - 1 - c;
            return;
        }
        dist -= height;
        pos[0] = c;
        pos[1] = n - 1 - c - dist;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{
                    {40, 10},
                    {30, 20}}, 1)
            .expect(new int[][]{
                    {10, 20},
                    {40, 30}});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{
                    {1, 2, 3, 4},
                    {5, 6, 7, 8},
                    {9, 10, 11, 12},
                    {13, 14, 15, 16}}, 2)
            .expect(new int[][]{
                    {3, 4, 8, 12},
                    {2, 11, 10, 16},
                    {1, 7, 6, 15},
                    {5, 9, 13, 14}});

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[][]{
                    {10, 1, 4, 8},
                    {6, 6, 3, 10},
                    {7, 4, 7, 10},
                    {1, 10, 6, 1},
                    {2, 1, 1, 10},
                    {3, 8, 9, 2},
                    {7, 1, 10, 10},
                    {7, 1, 4, 9},
                    {2, 2, 4, 2},
                    {10, 7, 5, 10}}, 1)
            .expect(new int[][]{
                    {1, 4, 8, 10},
                    {10, 3, 7, 10},
                    {6, 6, 6, 1},
                    {7, 4, 1, 10},
                    {1, 10, 9, 2},
                    {2, 1, 10, 10},
                    {3, 8, 4, 9},
                    {7, 1, 4, 2},
                    {7, 1, 2, 10},
                    {2, 10, 7, 5}});

}
