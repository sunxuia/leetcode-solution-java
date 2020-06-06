package q850;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/bricks-falling-when-hit/
 *
 * We have a grid of 1s and 0s; the 1s in a cell represent bricks.  A brick will not drop if and only if it is
 * directly connected to the top of the grid, or at least one of its (4-way) adjacent bricks will not drop.
 *
 * We will do some erasures sequentially. Each time we want to do the erasure at the location (i, j), the brick (if
 * it exists) on that location will disappear, and then some other bricks may drop because of that erasure.
 *
 * Return an array representing the number of bricks that will drop after each erasure in sequence.
 *
 * Example 1:
 * Input:
 * grid = [[1,0,0,0],[1,1,1,0]]
 * hits = [[1,0]]
 * Output: [2]
 * Explanation:
 * If we erase the brick at (1, 0), the brick at (1, 1) and (1, 2) will drop. So we should return 2.
 *
 * Example 2:
 * Input:
 * grid = [[1,0,0,0],[1,1,0,0]]
 * hits = [[1,1],[1,0]]
 * Output: [0,0]
 * Explanation:
 * When we erase the brick at (1, 0), the brick at (1, 1) has already disappeared due to the last move. So each
 * erasure will cause no bricks dropping.  Note that the erased brick (1, 0) will not be counted as a dropped brick.
 *
 *
 *
 * Note:
 *
 * The number of rows and columns in the grid will be in the range [1, 200].
 * The number of erasures will not exceed the area of the grid.
 * It is guaranteed that each erasure will be different from any other erasure, and located inside the grid.
 * An erasure may refer to a location with no brick - if it does, no bricks drop.
 */
@RunWith(LeetCodeRunner.class)
public class Q803_BricksFallingWhenHit {

    /**
     * https://www.cnblogs.com/grandyang/p/9362777.html
     * 使用并查集.
     */
    @Answer
    public int[] hitBricks(int[][] grid, int[][] hits) {
        final int m = grid.length, n = grid[0].length, k = hits.length;
        int[] res = new int[k];
        int[] root = new int[m * n];
        Arrays.fill(root, -1);
        int[] count = new int[m * n];
        Arrays.fill(count, 1);
        int[] t = new int[m * n];
        int[][] dirs = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
        for (int i = 0; i < k; ++i) {
            grid[hits[i][0]][hits[i][1]] -= 1;
        }
        for (int i = 0; i < n; ++i) {
            t[i] = 1;
        }
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (grid[i][j] != 1) {
                    continue;
                }
                if (i + 1 < m && grid[i + 1][j] == 1) {
                    int x = find(root, i * n + j), y = find(root, (i + 1) * n + j);
                    if (x != y) {
                        root[y] = x;
                        count[x] += count[y];
                        t[x] = t[y] = (t[x] | t[y]);
                    }
                }
                if (j + 1 < n && grid[i][j + 1] == 1) {
                    int x = find(root, i * n + j), y = find(root, i * n + j + 1);
                    if (x != y) {
                        root[y] = x;
                        count[x] += count[y];
                        t[x] = t[y] = (t[x] | t[y]);
                    }
                }
            }
        }
        for (int i = k - 1; i >= 0; --i) {
            int x = hits[i][0], y = hits[i][1], a = find(root, x * n + y), cnt = 0;
            if (++grid[x][y] != 1) {
                continue;
            }
            for (int[] dir : dirs) {
                int newX = x + dir[0], newY = y + dir[1];
                if (newX < 0 || newX >= m || newY < 0 || newY >= n || grid[newX][newY] != 1) {
                    continue;
                }
                int b = find(root, newX * n + newY);
                if (a == b) {
                    continue;
                }
                if (t[b] == 0) {
                    cnt += count[b];
                }
                root[b] = a;
                count[a] += count[b];
                t[a] = t[b] = (t[a] | t[b]);
            }
            if (t[a] != 0) {
                res[i] = cnt;
            }
        }
        return res;
    }

    int find(int[] root, int x) {
        return (root[x] == -1) ? x : find(root, root[x]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[][]{
                            {1, 0, 0, 0},
                            {1, 1, 1, 0}},
                    new int[][]{{1, 0}}
            ).expect(new int[]{2});

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[][]{
                            {1, 0, 0, 0},
                            {1, 1, 0, 0}},
                    new int[][]{{1, 1}, {1, 0}}
            ).expect(new int[]{0, 0});

}
