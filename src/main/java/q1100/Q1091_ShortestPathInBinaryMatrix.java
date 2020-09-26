package q1100;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1091. Shortest Path in Binary Matrix
 * https://leetcode.com/problems/shortest-path-in-binary-matrix/
 *
 * In an N by N square grid, each cell is either empty (0) or blocked (1).
 *
 * A clear path from top-left to bottom-right has length k if and only if it is composed of cells C_1, C_2, ..., C_k
 * such that:
 *
 * Adjacent cells C_i and C_{i+1} are connected 8-directionally (ie., they are different and share an edge or corner)
 * C_1 is at location (0, 0) (ie. has value grid[0][0])
 * C_k is at location (N-1, N-1) (ie. has value grid[N-1][N-1])
 * If C_i is located at (r, c), then grid[r][c] is empty (ie. grid[r][c] == 0).
 *
 * Return the length of the shortest such clear path from top-left to bottom-right.  If such a path does not exist,
 * return -1.
 *
 * Example 1:
 *
 * Input: [[0,1],[1,0]]
 * (图 Q1091_PIC1.png)
 * Output: 2
 * (图 Q1091_PIC2.png)
 * Example 2:
 *
 * Input: [[0,0,0],[1,1,0],[1,1,0]]
 * (图 Q1091_PIC3.png)
 * Output: 4
 * (图 Q1091_PIC4.png)
 * Note:
 *
 * 1 <= grid.length == grid[0].length <= 100
 * grid[r][c] is 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1091_ShortestPathInBinaryMatrix {

    /**
     * dfs 会超时, 所以用bfs
     */
    @Answer
    public int shortestPathBinaryMatrix(int[][] grid) {
        final int n = grid.length;
        int[][] path = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(path[i], Integer.MAX_VALUE);
        }
        Queue<Integer> queue = new ArrayDeque<>();
        queue.add(0);
        queue.add(0);
        int res = 1;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len -= 2) {
                int i = queue.poll();
                int j = queue.poll();
                if (i == -1 || i == n || j == -1 || j == n
                        || grid[i][j] == 1 || path[i][j] <= res) {
                    continue;
                }
                if (i == n - 1 && j == n - 1) {
                    return res;
                }
                path[i][j] = res;
                for (int[] direction : DIRECTIONS) {
                    queue.add(i + direction[0]);
                    queue.add(j + direction[1]);
                }
            }
            res++;
        }
        return -1;
    }

    private static final int[][] DIRECTIONS = new int[][]{
            {-1, -1}, {-1, 0}, {-1, 1},
            {0, -1}, {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    };

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{0, 1}, {1, 0}}).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 0, 0},
            {1, 1, 0},
            {1, 1, 0}}).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {1, 1, 0},
            {1, 1, 0}
    }).expect(-1);

    // 100 * 100
    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read2DArray("Q1091_TestData"))
            .expect(-1);

}
