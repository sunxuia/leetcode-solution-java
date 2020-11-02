package q1300;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1263. Minimum Moves to Move a Box to Their Target Location
 * https://leetcode.com/problems/minimum-moves-to-move-a-box-to-their-target-location/
 *
 * Storekeeper is a game in which the player pushes boxes around in a warehouse trying to get them to target locations.
 *
 * The game is represented by a grid of size m x n, where each element is a wall, floor, or a box.
 *
 * Your task is move the box 'B' to the target position 'T' under the following rules:
 *
 * 1. Player is represented by character 'S' and can move up, down, left, right in the grid if it is a floor (empy
 * cell).
 * 2. Floor is represented by character '.' that means free cell to walk.
 * 3. Wall is represented by character '#' that means obstacle  (impossible to walk there).
 * 4. There is only one box 'B' and one target cell 'T' in the grid.
 * 5. The box can be moved to an adjacent free cell by standing next to the box and then moving in the direction of the
 * box. This is a push.
 * 6. The player cannot walk through the box.
 *
 * Return the minimum number of pushes to move the box to the target. If there is no way to reach the target, return
 * -1.
 *
 * Example 1:
 * <img src="./Q1263_PIC.png">
 * > Input: grid = [["#","#","#","#","#","#"],
 * >                ["#","T","#","#","#","#"],
 * >                ["#",".",".","B",".","#"],
 * >                ["#",".","#","#",".","#"],
 * >                ["#",".",".",".","S","#"],
 * >                ["#","#","#","#","#","#"]]
 * Output: 3
 * Explanation: We return only the number of times the box is pushed.
 *
 * Example 2:
 *
 * > Input: grid = [["#","#","#","#","#","#"],
 * >                ["#","T","#","#","#","#"],
 * >                ["#",".",".","B",".","#"],
 * >                ["#","#","#","#",".","#"],
 * >                ["#",".",".",".","S","#"],
 * >                ["#","#","#","#","#","#"]]
 * Output: -1
 *
 * Example 3:
 *
 * > Input: grid = [["#","#","#","#","#","#"],
 * >                ["#","T",".",".","#","#"],
 * >                ["#",".","#","B",".","#"],
 * >                ["#",".",".",".",".","#"],
 * >                ["#",".",".",".","S","#"],
 * >                ["#","#","#","#","#","#"]]
 * Output: 5
 * Explanation:  push the box down, left, left, up and up.
 *
 * Example 4:
 *
 * > Input: grid = [["#","#","#","#","#","#","#"],
 * >                ["#","S","#",".","B","T","#"],
 * >                ["#","#","#","#","#","#","#"]]
 * Output: -1
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m <= 20
 * 1 <= n <= 20
 * grid contains only characters '.', '#',  'S' , 'T', or 'B'.
 * There is only one character 'S', 'B' and 'T' in the grid.
 */
@RunWith(LeetCodeRunner.class)
public class Q1263_MinimumMovesToMoveABoxToTheirTargetLocation {

    @Answer
    public int minPushBox(char[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[] source = findPos(grid, 'S');
        int[] box = findPos(grid, 'B');

        // visited[i][j][d] 表示推位于 (i, j) 的箱子往 DIRECTION[d] 的方向走
        boolean[][][] visited = new boolean[m][n][4];
        Queue<int[]> queue = new ArrayDeque<>();
        for (int d = 0; d < 4; d++) {
            if (pushable(grid, box[0], box[1], source[0], source[1], d)) {
                visited[box[0]][box[1]][d] = true;
                queue.add(new int[]{box[0], box[1], d});
            }
        }

        for (int res = 1; !queue.isEmpty(); res++) {
            for (int len = queue.size(); len > 0; len--) {
                int[] data = queue.poll();
                int si = data[0], sj = data[1];
                int bi = si + DIRECTIONS[data[2]][0];
                int bj = sj + DIRECTIONS[data[2]][1];
                if (grid[bi][bj] == 'T') {
                    return res;
                }
                for (int d = 0; d < 4; d++) {
                    if (!visited[bi][bj][d]
                            && pushable(grid, bi, bj, si, sj, d)) {
                        visited[bi][bj][d] = true;
                        queue.add(new int[]{bi, bj, d});
                    }
                }
            }
        }
        return -1;
    }

    private int[] findPos(char[][] grid, char expect) {
        final int m = grid.length, n = grid[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == expect) {
                    return new int[]{i, j};
                }
            }
        }
        throw new RuntimeException("Should not run to here.");
    }

    private boolean pushable(char[][] grid, int bi, int bj, int si, int sj, int pushDirection) {
        final int m = grid.length, n = grid[0].length;
        // 在 (pi, pj) 是否能将箱子 (bi, bj) 推到 (ti, tj)
        int pi = bi - DIRECTIONS[pushDirection][0];
        int pj = bj - DIRECTIONS[pushDirection][1];
        int ti = bi + DIRECTIONS[pushDirection][0];
        int tj = bj + DIRECTIONS[pushDirection][1];
        if (!valid(grid, pi, pj) || !valid(grid, ti, tj)) {
            return false;
        }

        // 是否能从 (si, sj) 走到推箱子的位置 (pi, pj)
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{si, sj});
        boolean[][] visited = new boolean[m][n];
        visited[si][sj] = true;
        visited[bi][bj] = true;
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            if (pi == pos[0] && pj == pos[1]) {
                return true;
            }
            for (int[] offset : DIRECTIONS) {
                int i = pos[0] + offset[0];
                int j = pos[1] + offset[1];
                if (valid(grid, i, j) && !visited[i][j]) {
                    visited[i][j] = true;
                    queue.add(new int[]{i, j});
                }
            }
        }
        return false;
    }

    private boolean valid(char[][] grid, int i, int j) {
        final int m = grid.length, n = grid[0].length;
        return -1 < i && i < m && -1 < j && j < n && grid[i][j] != '#';
    }

    private static final int[][] DIRECTIONS = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    @TestData
    public DataExpectation example1 = DataExpectation.create(new char[][]{
            {'#', '#', '#', '#', '#', '#'},
            {'#', 'T', '#', '#', '#', '#'},
            {'#', '.', '.', 'B', '.', '#'},
            {'#', '.', '#', '#', '.', '#'},
            {'#', '.', '.', '.', 'S', '#'},
            {'#', '#', '#', '#', '#', '#'}
    }).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new char[][]{
            {'#', '#', '#', '#', '#', '#'},
            {'#', 'T', '#', '#', '#', '#'},
            {'#', '.', '.', 'B', '.', '#'},
            {'#', '#', '#', '#', '.', '#'},
            {'#', '.', '.', '.', 'S', '#'},
            {'#', '#', '#', '#', '#', '#'}
    }).expect(-1);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new char[][]{
            {'#', '#', '#', '#', '#', '#'},
            {'#', 'T', '.', '.', '#', '#'},
            {'#', '.', '#', 'B', '.', '#'},
            {'#', '.', '.', '.', '.', '#'},
            {'#', '.', '.', '.', 'S', '#'},
            {'#', '#', '#', '#', '#', '#'}
    }).expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new char[][]{
            {'#', '#', '#', '#', '#', '#', '#'},
            {'#', 'S', '#', '.', 'B', 'T', '#'},
            {'#', '#', '#', '#', '#', '#', '#'}
    }).expect(-1);

}
