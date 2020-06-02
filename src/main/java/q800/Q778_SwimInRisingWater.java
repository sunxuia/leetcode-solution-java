package q800;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/swim-in-rising-water/
 *
 * On an N x N grid, each square grid[i][j] represents the elevation at that point (i,j).
 *
 * Now rain starts to fall. At time t, the depth of the water everywhere is t. You can swim from a square to another
 * 4-directionally adjacent square if and only if the elevation of both squares individually are at most t. You can
 * swim infinite distance in zero time. Of course, you must stay within the boundaries of the grid during your swim.
 *
 * You start at the top left square (0, 0). What is the least time until you can reach the bottom right square (N-1,
 * N-1)?
 *
 * Example 1:
 *
 * Input: [[0,2],[1,3]]
 * Output: 3
 * Explanation:
 * At time 0, you are in grid location (0, 0).
 * You cannot go anywhere else because 4-directionally adjacent neighbors have a higher elevation than t = 0.
 *
 * You cannot reach point (1, 1) until time 3.
 * When the depth of water is 3, we can swim anywhere inside the grid.
 *
 * Example 2:
 *
 * Input: [[0,1,2,3,4],[24,23,22,21,5],[12,13,14,15,16],[11,17,18,19,20],[10,9,8,7,6]]
 * Output: 16
 * Explanation:
 * >  0  1  2  3  4
 * > 24 23 22 21  5
 * > 12 13 14 15 16
 * > 11 17 18 19 20
 * > 10  9  8  7  6
 *
 * The final route is marked in bold.
 * We need to wait until time 16 so that (0, 0) and (4, 4) are connected.
 *
 * Note:
 *
 * 1. 2 <= N <= 50.
 * 2. grid[i][j] is a permutation of [0, ..., N*N - 1].
 */
@RunWith(LeetCodeRunner.class)
public class Q778_SwimInRisingWater {

    // dfs, 这种方式比较慢
    @Answer
    public int swimInWater(int[][] grid) {
        final int n = grid.length;
        int[][] water = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(water[i], Integer.MAX_VALUE);
        }
        dfs(grid, water, 0, 0, 0);
        return water[n - 1][n - 1];
    }

    private void dfs(int[][] grid, int[][] water, int i, int j, int t) {
        final int n = grid.length;
        if (i < 0 || j < 0 || i == n || j == n) {
            return;
        }

        t = Math.max(grid[i][j], t);
        if (water[i][j] <= t) {
            return;
        }
        water[i][j] = t;

        dfs(grid, water, i - 1, j, t);
        dfs(grid, water, i, j - 1, t);
        dfs(grid, water, i + 1, j, t);
        dfs(grid, water, i, j + 1, t);
    }

    // 优先队列的方式, 优先往低水位的方向走.
    @Answer
    public int swimInWater2(int[][] grid) {
        final int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        PriorityQueue<Integer> pq = new PriorityQueue<>(
                Comparator.comparingInt(i -> grid[i / n][i % n]));
        visited[0][0] = true;
        pq.add(0);
        int res = 0;
        while (!pq.isEmpty()) {
            int pos = pq.poll();
            int i = pos / n, j = pos % n;

            res = Math.max(res, grid[i][j]);
            if (i == n - 1 && j == n - 1) {
                return res;
            }

            for (int[] d : DIRECTIONS) {
                int ni = i + d[0], nj = j + d[1];
                if (0 <= ni && ni < n && 0 <= nj && nj < n
                        && !visited[ni][nj]) {
                    visited[ni][nj] = true;
                    pq.add(ni * n + nj);
                }
            }
        }
        return -1;
    }

    private int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 2},
            {1, 3}
    }).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 1, 2, 3, 4},
            {24, 23, 22, 21, 5},
            {12, 13, 14, 15, 16},
            {11, 17, 18, 19, 20},
            {10, 9, 8, 7, 6}
    }).expect(16);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {3, 2},
            {0, 1}
    }).expect(3);
}
