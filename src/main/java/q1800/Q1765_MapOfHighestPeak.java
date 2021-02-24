package q1800;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Queue;
import org.junit.Assert;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1765. Map of Highest Peak
 * https://leetcode.com/problems/map-of-highest-peak/
 *
 * You are given an integer matrix isWater of size m x n that represents a map of land and water cells.
 *
 * If isWater[i][j] == 0, cell (i, j) is a land cell.
 * If isWater[i][j] == 1, cell (i, j) is a water cell.
 *
 * You must assign each cell a height in a way that follows these rules:
 *
 * The height of each cell must be non-negative.
 * If the cell is a water cell, its height must be 0.
 * Any two adjacent cells must have an absolute height difference of at most 1. A cell is adjacent to another cell if
 * the former is directly north, east, south, or west of the latter (i.e., their sides are touching).
 *
 * Find an assignment of heights such that the maximum height in the matrix is maximized.
 *
 * Return an integer matrix height of size m x n where height[i][j] is cell (i, j)'s height. If there are multiple
 * solutions, return any of them.
 *
 * Example 1:
 * <img src="./Q1765_PIC1.png">
 * Input: isWater = [[0,1],[0,0]]
 * Output: [[1,0],[2,1]]
 * Explanation: The image shows the assigned heights of each cell.
 * The blue cell is the water cell, and the green cells are the land cells.
 *
 * Example 2:
 * <img src="./Q1765_PIC2.png">
 * Input: isWater = [[0,0,1],[1,0,0],[0,0,0]]
 * Output: [[1,1,0],[0,1,1],[1,2,2]]
 * Explanation: A height of 2 is the maximum possible height of any assignment.
 * Any height assignment that has a maximum height of 2 while still meeting the rules will also be accepted.
 *
 * Constraints:
 *
 * m == isWater.length
 * n == isWater[i].length
 * 1 <= m, n <= 1000
 * isWater[i][j] is 0 or 1.
 * There is at least one water cell.
 */
@RunWith(LeetCodeRunner.class)
public class Q1765_MapOfHighestPeak {

    @Answer
    public int[][] highestPeak(int[][] isWater) {
        final int m = isWater.length, n = isWater[0].length;
        Queue<int[]> queue = new ArrayDeque<>();
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (isWater[i][j] == 1) {
                    queue.offer(new int[]{i, j, 0});
                }
            }
        }

        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(res[i], Integer.MAX_VALUE);
        }
        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            int i = pos[0], j = pos[1], height = pos[2];
            if (i == -1 || i == m || j == -1 || j == n
                    || res[i][j] <= height) {
                continue;
            }
            res[i][j] = height;
            queue.offer(new int[]{i - 1, j, height + 1});
            queue.offer(new int[]{i, j + 1, height + 1});
            queue.offer(new int[]{i + 1, j, height + 1});
            queue.offer(new int[]{i, j - 1, height + 1});
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = createTestData(new int[][]{
            {0, 1},
            {0, 0}}, 2, 1);

    private DataExpectation createTestData(int[][] isWater, int maxHeight, int maxHeightCount) {
        final int m = isWater.length, n = isWater[0].length;
        return DataExpectation.create(isWater)
                .assertResult((int[][] res) -> {
                    int count = maxHeightCount;
                    for (int i = 0; i < m; i++) {
                        for (int j = 0; j < n; j++) {
                            if (isWater[i][j] == 1) {
                                Assert.assertEquals(0, res[i][j]);
                            } else {
                                Assert.assertTrue(res[i][j] <= maxHeight);
                                if (res[i][j] == maxHeight) {
                                    count--;
                                }
                                if (i > 0) {
                                    Assert.assertTrue(Math.abs(res[i][j] - res[i - 1][j]) <= 1);
                                }
                                if (j > 0) {
                                    Assert.assertTrue(Math.abs(res[i][j] - res[i][j - 1]) <= 1);
                                }
                                if (i < m - 1) {
                                    Assert.assertTrue(Math.abs(res[i][j] - res[i + 1][j]) <= 1);
                                }
                                if (j < n - 1) {
                                    Assert.assertTrue(Math.abs(res[i][j] - res[i][j + 1]) <= 1);
                                }
                            }
                        }
                    }
                    Assert.assertEquals(0, count);
                });
    }

    @TestData
    public DataExpectation example2 = createTestData(new int[][]{
            {0, 0, 1},
            {1, 0, 0},
            {0, 0, 0}}, 2, 2);

    @TestData
    public DataExpectation normal1 = createTestData(new int[][]{
            {1, 1, 0, 1, 1, 1},
            {1, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {1, 1, 1, 1, 1, 1},
            {0, 1, 1, 1, 1, 0},
            {1, 1, 1, 0, 0, 0},
            {1, 1, 0, 1, 1, 1},
            {1, 1, 0, 1, 0, 1}}, 1, 16);

}
