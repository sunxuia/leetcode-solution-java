package q200;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/number-of-islands/
 *
 * Given a 2d grid map of '1's (land) and '0's (water), count the number of islands. An island is surrounded by water
 * and is formed by connecting adjacent lands horizontally or vertically. You may assume all four edges of the grid
 * are all surrounded by water.
 *
 * Example 1:
 *
 * Input:
 * 11110
 * 11010
 * 11000
 * 00000
 *
 * Output: 1
 *
 * Example 2:
 *
 * Input:
 * 11000
 * 11000
 * 00100
 * 00011
 *
 * Output: 3
 */
@RunWith(LeetCodeRunner.class)
public class Q200_NumberOfIslands {

    // dfs 染色方法
    @Answer
    public int numIslands(char[][] grid) {
        int res = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    dfs(grid, i, j);
                }
            }
        }
        return res;
    }

    private void dfs(char[][] grid, int i, int j) {
        if (i == -1 || i == grid.length || j == -1 || j == grid[i].length || grid[i][j] == '0') {
            return;
        }
        grid[i][j] = '0';
        for (int[] direction : DIRECTIONS) {
            dfs(grid, i + direction[0], j + direction[1]);
        }
    }

    private static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    // bfs 染色方法
    @Answer
    public int numIslands2(char[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int res = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    queue.add(i);
                    queue.add(j);
                    grid[i][j] = '0';
                    while (!queue.isEmpty()) {
                        int y = queue.poll();
                        int x = queue.poll();
                        for (int[] direction : DIRECTIONS) {
                            int ny = y + direction[0];
                            int nx = x + direction[1];
                            if (-1 < ny && ny < m && -1 < nx && nx < n
                                    && grid[ny][nx] == '1') {
                                grid[ny][nx] = '0';
                                queue.add(ny);
                                queue.add(nx);
                            }
                        }
                    }
                }
            }
        }
        return res;
    }

    // 并查集方式
    @Answer
    public int numIslands3(char[][] grid) {
        final int m = grid.length, n = grid[0].length;

        // 初始化
        int[] roots = new int[m * n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    roots[i * n + j + 1] = i * n + j + 1;
                }
            }
        }

        // 连接
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == '1') {
                    for (int[] direction : DIRECTIONS) {
                        int ni = i + direction[0];
                        int nj = j + direction[1];
                        if (-1 < ni && ni < m && -1 < nj && nj < n
                                && grid[ni][nj] == '1') {
                            roots[findRoot(roots, ni * n + nj + 1)]
                                    = findRoot(roots, i * n + j + 1);
                        }
                    }
                }
            }
        }

        // 计算结果, 根不是自己则从属于其他并查集
        int res = 0;
        for (int i = 1; i < roots.length; i++) {
            if (roots[i] == i) {
                res++;
            }
        }
        return res;
    }

    private int findRoot(int[] roots, int i) {
        if (roots[i] == i) {
            return i;
        }
        return roots[i] = findRoot(roots, roots[i]);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new char[][]{
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
    }).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new char[][]{
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
    }).expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new char[][]{{'1'}, {'1'}}).expect(1);

}
