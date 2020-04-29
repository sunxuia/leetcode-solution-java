package q550;

import java.util.ArrayDeque;
import java.util.Deque;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/01-matrix/
 *
 * Given a matrix consists of 0 and 1, find the distance of the nearest 0 for each cell.
 *
 * The distance between two adjacent cells is 1.
 *
 *
 *
 * Example 1:
 *
 * Input:
 * [[0,0,0],
 * [0,1,0],
 * [0,0,0]]
 *
 * Output:
 * [[0,0,0],
 * [0,1,0],
 * [0,0,0]]
 *
 * Example 2:
 *
 * Input:
 * [[0,0,0],
 * [0,1,0],
 * [1,1,1]]
 *
 * Output:
 * [[0,0,0],
 * [0,1,0],
 * [1,2,1]]
 *
 *
 *
 * Note:
 *
 * The number of elements of the given matrix will not exceed 10,000.
 * There are at least one 0 in the given matrix.
 * The cells are adjacent in only four directions: up, down, left and right.
 */
@RunWith(LeetCodeRunner.class)
public class Q542_01Matrix {

    // bfs
    @Answer
    public int[][] updateMatrix(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return matrix;
        }
        final int m = matrix.length, n = matrix[0].length;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    queue.add(i * n + j);
                } else {
                    matrix[i][j] = -1;
                }
            }
        }
        int depth = 0;
        while (!queue.isEmpty()) {
            for (int i = queue.size(); i > 0; i--) {
                int idx = queue.remove();
                int y = idx / n, x = idx % n;
                if (matrix[y][x] <= 0) {
                    matrix[y][x] = depth;
                    addToQueue(queue, matrix, y - 1, x);
                    addToQueue(queue, matrix, y, x + 1);
                    addToQueue(queue, matrix, y + 1, x);
                    addToQueue(queue, matrix, y, x - 1);
                }
            }
            depth++;
        }
        return matrix;
    }

    private void addToQueue(Deque<Integer> queue, int[][] matrix, int y, int x) {
        if (y < 0 || x < 0 || y == matrix.length || x == matrix[0].length
                || matrix[y][x] >= 0) {
            return;
        }
        queue.add(y * matrix[0].length + x);
    }

    // LeetCode 上比较快的方式, 先从左上角开始遍历, 然后再从右下角开始遍历, 更新格子的值.
    @Answer
    public int[][] updateMatrix2(int[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) {
            return matrix;
        }
        final int m = matrix.length, n = matrix[0].length;
        int[][] res = new int[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    res[i][j] = 0;
                } else {
                    int upCell = i > 0 ? res[i - 1][j] : m * n;
                    int leftCell = j > 0 ? res[i][j - 1] : m * n;
                    res[i][j] = Math.min(upCell, leftCell) + 1;
                }
            }
        }

        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                if (matrix[i][j] == 0) {
                    res[i][j] = 0;
                } else {
                    int downCell = i < m - 1 ? res[i + 1][j] : m * n;
                    int rightCell = j < n - 1 ? res[i][j + 1] : m * n;
                    res[i][j] = Math.min(res[i][j], Math.min(downCell, rightCell) + 1);
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    }).expect(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {0, 0, 0}
    });

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {1, 1, 1}
    }).expect(new int[][]{
            {0, 0, 0},
            {0, 1, 0},
            {1, 2, 1}
    });

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    }).expect(new int[][]{
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0},
            {0, 1, 0}
    });

}
