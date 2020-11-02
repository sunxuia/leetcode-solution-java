package q1300;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1260. Shift 2D Grid
 * https://leetcode.com/problems/shift-2d-grid/
 *
 * Given a 2D grid of size m x n and an integer k. You need to shift the grid k times.
 *
 * In one shift operation:
 *
 * Element at grid[i][j] moves to grid[i][j + 1].
 * Element at grid[i][n - 1] moves to grid[i + 1][0].
 * Element at grid[m - 1][n - 1] moves to grid[0][0].
 *
 * Return the 2D grid after applying shift operation k times.
 *
 * Example 1:
 *
 * Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 1
 * Output: [[9,1,2],[3,4,5],[6,7,8]]
 *
 * Example 2:
 *
 * Input: grid = [[3,8,1,9],[19,7,2,5],[4,6,11,10],[12,0,21,13]], k = 4
 * Output: [[12,0,21,13],[3,8,1,9],[19,7,2,5],[4,6,11,10]]
 *
 * Example 3:
 *
 * Input: grid = [[1,2,3],[4,5,6],[7,8,9]], k = 9
 * Output: [[1,2,3],[4,5,6],[7,8,9]]
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m <= 50
 * 1 <= n <= 50
 * -1000 <= grid[i][j] <= 1000
 * 0 <= k <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1260_Shift2dGrid {

    @Answer
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        final int m = grid.length, n = grid[0].length;
        k %= m * n;
        List<List<Integer>> res = new ArrayList<>(m);
        for (int i = 0; i < m; i++) {
            res.add(new ArrayList<>(n));
            for (int j = 0; j < n; j++) {
                int pos = (i * n + j + m * n - k) % (m * n);
                res.get(i).add(grid[pos / n][pos % n]);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}
            }, 1)
            .expect(Arrays.asList(
                    Arrays.asList(9, 1, 2),
                    Arrays.asList(3, 4, 5),
                    Arrays.asList(6, 7, 8)));

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                    {3, 8, 1, 9},
                    {19, 7, 2, 5},
                    {4, 6, 11, 10},
                    {12, 0, 21, 13}}, 4)
            .expect(Arrays.asList(
                    Arrays.asList(12, 0, 21, 13),
                    Arrays.asList(3, 8, 1, 9),
                    Arrays.asList(19, 7, 2, 5),
                    Arrays.asList(4, 6, 11, 10)));

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[][]{
                    {1, 2, 3},
                    {4, 5, 6},
                    {7, 8, 9}}, 9)
            .expect(Arrays.asList(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(4, 5, 6),
                    Arrays.asList(7, 8, 9)));

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new int[][]{
                    {1}, {2}, {3}, {4}, {7}, {6}, {5}}, 23)
            .expect(Arrays.asList(Arrays.asList(6), Arrays.asList(5), Arrays.asList(1), Arrays.asList(2),
                    Arrays.asList(3), Arrays.asList(4), Arrays.asList(7)));

}
