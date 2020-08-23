package q1000;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import q100.Q063_UniquePathsII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 980. Unique Paths III
 * https://leetcode.com/problems/unique-paths-iii/
 *
 * On a 2-dimensional grid, there are 4 types of squares:
 *
 * 1 represents the starting square.  There is exactly one starting square.
 * 2 represents the ending square.  There is exactly one ending square.
 * 0 represents empty squares we can walk over.
 * -1 represents obstacles that we cannot walk over.
 *
 * Return the number of 4-directional walks from the starting square to the ending square, that walk over every
 * non-obstacle square exactly once.
 *
 * Example 1:
 *
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,2,-1]]
 * Output: 2
 * Explanation: We have the following two paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2)
 * 2. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2)
 *
 * Example 2:
 *
 * Input: [[1,0,0,0],[0,0,0,0],[0,0,0,2]]
 * Output: 4
 * Explanation: We have the following four paths:
 * 1. (0,0),(0,1),(0,2),(0,3),(1,3),(1,2),(1,1),(1,0),(2,0),(2,1),(2,2),(2,3)
 * 2. (0,0),(0,1),(1,1),(1,0),(2,0),(2,1),(2,2),(1,2),(0,2),(0,3),(1,3),(2,3)
 * 3. (0,0),(1,0),(2,0),(2,1),(2,2),(1,2),(1,1),(0,1),(0,2),(0,3),(1,3),(2,3)
 * 4. (0,0),(1,0),(2,0),(2,1),(1,1),(0,1),(0,2),(0,3),(1,3),(1,2),(2,2),(2,3)
 *
 * Example 3:
 *
 * Input: [[0,1],[2,0]]
 * Output: 0
 * Explanation:
 * There is no path that walks over every empty square exactly once.
 * Note that the starting and ending square can be anywhere in the grid.
 *
 * Note:
 *
 * 1 <= grid.length * grid[0].length <= 20
 *
 * 上一题 {@link Q063_UniquePathsII}
 */
@RunWith(LeetCodeRunner.class)
public class Q980_UniquePathsIII {

    // dfs 回溯法最快, bfs 这种方式慢一点
    @Answer
    public int uniquePathsIII(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int startMask = 0, start = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                switch (grid[i][j]) {
                    case 1:
                        start = i * n + j;
                    case -1:
                        startMask |= 1 << i * n + j;
                }
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(start);
        queue.add(startMask);
        int res = 0;
        while (!queue.isEmpty()) {
            int index = queue.poll();
            int mask = queue.poll();
            int i = index / n, j = index % n;
            if (grid[i][j] == 2) {
                if (mask == (1 << m * n) - 1) {
                    res++;
                }
                continue;
            }
            for (int k = 0; k < 4; k++) {
                int ni = i + DIRECTIONS[k];
                int nj = j + DIRECTIONS[k + 1];
                if (0 <= ni && ni < m && 0 <= nj && nj < n
                        && (mask >> ni * n + nj & 1) == 0) {
                    queue.add(ni * n + nj);
                    queue.add(mask | (1 << ni * n + nj));
                }
            }
        }
        return res;
    }

    private static final int[] DIRECTIONS = new int[]{-1, 0, 1, 0, -1};

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{
                    {1, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 2, -1}
            }).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{
                    {1, 0, 0, 0},
                    {0, 0, 0, 0},
                    {0, 0, 0, 2}
            }).expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {0, 1},
            {2, 0}
    }).expect(0);

}
