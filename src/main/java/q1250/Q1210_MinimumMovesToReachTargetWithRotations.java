package q1250;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1210. Minimum Moves to Reach Target with Rotations
 * https://leetcode.com/problems/minimum-moves-to-reach-target-with-rotations/
 *
 * In an n*n grid, there is a snake that spans 2 cells and starts moving from the top left corner at (0, 0) and (0, 1).
 * The grid has empty cells represented by zeros and blocked cells represented by ones. The snake wants to reach the
 * lower right corner at (n-1, n-2) and (n-1, n-1).
 *
 * In one move the snake can:
 *
 * 1. Move one cell to the right if there are no blocked cells there. This move keeps the horizontal/vertical position
 * of the snake as it is.
 * 2. Move down one cell if there are no blocked cells there. This move keeps the horizontal/vertical position of the
 * snake as it is.
 * 3. Rotate clockwise if it's in a horizontal position and the two cells under it are both empty. In that case the
 * snake moves from (r, c) and (r, c+1) to (r, c) and (r+1, c).
 * <img src="Q1210_PIC1.png">
 * 4. Rotate counterclockwise if it's in a vertical position and the two cells to its right are both empty. In that case
 * the snake moves from (r, c) and (r+1, c) to (r, c) and (r, c+1).
 * <img src="Q1210_PIC2.png">
 * Return the minimum number of moves to reach the target.
 *
 * If there is no way to reach the target, return -1.
 *
 * Example 1:
 * <img src="Q1210_PIC3.png">
 * Input: grid = [[0,0,0,0,0,1],
 * [1,1,0,0,1,0],
 * [0,0,0,0,1,1],
 * [0,0,1,0,1,0],
 * [0,1,1,0,0,0],
 * [0,1,1,0,0,0]]
 * Output: 11
 * Explanation:
 * One possible solution is [right, right, rotate clockwise, right, down, down, down, down, rotate counterclockwise,
 * right, down].
 *
 * Example 2:
 *
 * Input: grid = [[0,0,1,1,1,1],
 * [0,0,0,0,1,1],
 * [1,1,0,0,0,1],
 * [1,1,1,0,0,1],
 * [1,1,1,0,0,1],
 * [1,1,1,0,0,0]]
 * Output: 9
 *
 * Constraints:
 *
 * 2 <= n <= 100
 * 0 <= grid[i][j] <= 1
 * It is guaranteed that the snake starts at empty cells.
 */
@RunWith(LeetCodeRunner.class)
public class Q1210_MinimumMovesToReachTargetWithRotations {

    @Answer
    public int minimumMoves(int[][] grid) {
        final int n = grid.length;
        Snake expect = new Snake(grid, n - 1, n - 2, n - 1, n - 1);
        Set<Snake> visited = new HashSet<>();
        visited.add(null);
        Queue<Snake> queue = new ArrayDeque<>();
        queue.add(new Snake(grid, 0, 0, 0, 1));
        int res = 0;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                Snake curr = queue.poll();
                if (expect.equals(curr)) {
                    return res;
                }
                for (Snake next : new Snake[]{
                        curr.moveRight(),
                        curr.moveDown(),
                        curr.rotateClockwise(),
                        curr.rotateCounterclockwise()
                }) {
                    if (visited.add(next)) {
                        queue.add(next);
                    }
                }
            }
            res++;
        }
        return -1;
    }

    private static class Snake {

        final int[][] grid;

        final int fi, fj, ti, tj;

        private Snake(int[][] grid, int fi, int fj, int ti, int tj) {
            this.grid = grid;
            this.fi = fi;
            this.fj = fj;
            this.ti = ti;
            this.tj = tj;
        }

        Snake moveRight() {
            return isAvailable(fi, fj + 1) && isAvailable(ti, tj + 1)
                    ? new Snake(grid, fi, fj + 1, ti, tj + 1) : null;
        }

        boolean isAvailable(int i, int j) {
            final int n = grid.length;
            return i < n && j < n && grid[i][j] == 0;
        }

        Snake moveDown() {
            return isAvailable(fi + 1, fj) && isAvailable(ti + 1, tj)
                    ? new Snake(grid, fi + 1, fj, ti + 1, tj) : null;
        }

        Snake rotateClockwise() {
            return fi == ti && isAvailable(fi + 1, fj) && isAvailable(ti + 1, tj)
                    ? new Snake(grid, fi, fj, fi + 1, fj) : null;
        }

        Snake rotateCounterclockwise() {
            return fj == tj && isAvailable(fi, fj + 1) && isAvailable(fi + 1, fj + 1)
                    ? new Snake(grid, fi, fj, fi, fj + 1) : null;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Snake snake = (Snake) o;
            return fi == snake.fi &&
                    fj == snake.fj &&
                    ti == snake.ti &&
                    tj == snake.tj;
        }

        @Override
        public int hashCode() {
            return Objects.hash(fi, fj, ti, tj);
        }

        @Override
        public String toString() {
            if (fi == ti) {
                return String.format("(%d, %d) (%d, %d)", fi, fj, ti, tj);
            } else {
                return String.format("(%d, %d)\n(%d, %d)", fi, fj, ti, tj);
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 0, 0, 0, 0, 1},
            {1, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1},
            {0, 0, 1, 0, 1, 0},
            {0, 1, 1, 0, 0, 0},
            {0, 1, 1, 0, 0, 0}
    }).expect(11);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 0, 1, 1, 1, 1},
            {0, 0, 0, 0, 1, 1},
            {1, 1, 0, 0, 0, 1},
            {1, 1, 1, 0, 0, 1},
            {1, 1, 1, 0, 0, 1},
            {1, 1, 1, 0, 0, 0}
    }).expect(9);

}
