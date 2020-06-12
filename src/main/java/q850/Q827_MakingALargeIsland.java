package q850;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;


/**
 * https://leetcode.com/problems/making-a-large-island/
 *
 * In a 2D grid of 0s and 1s, we change at most one 0 to a 1.
 *
 * After, what is the size of the largest island? (An island is a 4-directionally connected group of 1s).
 *
 * Example 1:
 *
 * Input: [[1, 0], [0, 1]]
 * Output: 3
 * Explanation: Change one 0 to 1 and connect two 1s, then we get an island with area = 3.
 *
 * Example 2:
 *
 * Input: [[1, 1], [1, 0]]
 * Output: 4
 * Explanation: Change the 0 to 1 and make the island bigger, only one island with area = 4.
 *
 * Example 3:
 *
 * Input: [[1, 1], [1, 1]]
 * Output: 4
 * Explanation: Can't change any 0 to 1, only one island with area = 4.
 *
 *
 *
 * Notes:
 *
 * 1 <= grid.length = grid[0].length <= 50.
 * 0 <= grid[i][j] <= 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q827_MakingALargeIsland {

    @Answer
    public int largestIsland(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        Map<Integer, Integer> counts = new HashMap<>();
        int color = 1;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    int area = setColor(grid, i, j, ++color);
                    counts.put(color, area);
                }
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 0) {
                    res = Math.max(res, search(grid, i, j, counts));
                }
            }
        }
        return res == 0 ? m * n : res;
    }

    private int setColor(int[][] grid, int i, int j, int color) {
        if (i == -1 || j == -1 || i == grid.length || j == grid[0].length
                || grid[i][j] != 1) {
            return 0;
        }
        grid[i][j] = color;
        int res = 1;
        res += setColor(grid, i + 1, j, color);
        res += setColor(grid, i, j + 1, color);
        res += setColor(grid, i - 1, j, color);
        res += setColor(grid, i, j - 1, color);
        return res;
    }

    private int search(int[][] grid, int i, int j, Map<Integer, Integer> counts) {
        Set<Integer> set = new HashSet<>(4);
        set.add(i == 0 ? 0 : grid[i - 1][j]);
        set.add(i == grid.length - 1 ? 0 : grid[i + 1][j]);
        set.add(j == 0 ? 0 : grid[i][j - 1]);
        set.add(j == grid[0].length - 1 ? 0 : grid[i][j + 1]);
        set.remove(0);

        int res = 1;
        for (Integer neighbor : set) {
            res += counts.get(neighbor);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 0}, {0, 1}}).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 1}, {1, 0}}).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{{1, 1}, {1, 1}}).expect(4);

}
