package q1300;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1284. Minimum Number of Flips to Convert Binary Matrix to Zero Matrix
 * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/
 *
 * Given a m x n binary matrix mat. In one step, you can choose one cell and flip it and all the four neighbours of it
 * if they exist (Flip is changing 1 to 0 and 0 to 1). A pair of cells are called neighboors if they share one edge.
 *
 * Return the minimum number of steps required to convert mat to a zero matrix or -1 if you cannot.
 *
 * Binary matrix is a matrix with all cells equal to 0 or 1 only.
 *
 * Zero matrix is a matrix with all cells equal to 0.
 *
 * Example 1:
 * <img src="./Q1284_PIC.png">
 * Input: mat = [[0,0],[0,1]]
 * Output: 3
 * Explanation: One possible solution is to flip (1, 0) then (0, 1) and finally (1, 1) as shown.
 *
 * Example 2:
 *
 * Input: mat = [[0]]
 * Output: 0
 * Explanation: Given matrix is a zero matrix. We don't need to change it.
 *
 * Example 3:
 *
 * Input: mat = [[1,1,1],[1,0,1],[0,0,0]]
 * Output: 6
 *
 * Example 4:
 *
 * Input: mat = [[1,0,0],[1,0,0]]
 * Output: -1
 * Explanation: Given matrix can't be a zero matrix
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[0].length
 * 1 <= m <= 3
 * 1 <= n <= 3
 * mat[i][j] is 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1284_MinimumNumberOfFlipsToConvertBinaryMatrixToZeroMatrix {

    /**
     * 因为矩形不大, 所以可以用位运算+bfs
     */
    @Answer
    public int minFlips(int[][] mat) {
        final int m = mat.length, n = mat[0].length;
        int mask = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                mask |= mat[i][j] << i * n + j;
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        boolean[] visited = new boolean[1 << m * n];
        queue.add(mask);
        visited[mask] = true;
        int res = 0;
        while (!queue.isEmpty()) {
            for (int len = queue.size(); len > 0; len--) {
                mask = queue.poll();
                if (mask == 0) {
                    return res;
                }
                for (int i = 0; i < m; i++) {
                    for (int j = 0; j < n; j++) {
                        int flip = mask ^ 1 << i * n + j;
                        if (i > 0) {
                            flip ^= 1 << i * n + j - n;
                        }
                        if (i < m - 1) {
                            flip ^= 1 << i * n + j + n;
                        }
                        if (j > 0) {
                            flip ^= 1 << i * n + j - 1;
                        }
                        if (j < n - 1) {
                            flip ^= 1 << i * n + j + 1;
                        }
                        if (!visited[flip]) {
                            visited[flip] = true;
                            queue.add(flip);
                        }
                    }
                }
            }
            res++;
        }
        return -1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 0},
            {0, 1}
    }).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{0}}).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 1},
            {1, 0, 1},
            {0, 0, 0}
    }).expect(6);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new int[][]{
            {1, 0, 0},
            {1, 0, 0}
    }).expect(-1);

}
