package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1267. Count Servers that Communicate
 * https://leetcode.com/problems/count-servers-that-communicate/
 *
 * You are given a map of a server center, represented as a m * n integer matrix grid, where 1 means that on that cell
 * there is a server and 0 means that it is no server. Two servers are said to communicate if they are on the same row
 * or on the same column.
 *
 * Return the number of servers that communicate with any other server.
 *
 * Example 1:
 * <img src="./Q1267_PIC1.png">
 * Input: grid = [[1,0],[0,1]]
 * Output: 0
 * Explanation: No servers can communicate with others.
 *
 * Example 2:
 * <img src="./Q1267_PIC2.png">
 * Input: grid = [[1,0],[1,1]]
 * Output: 3
 * Explanation: All three servers can communicate with at least one other server.
 *
 * Example 3:
 * <img src="./Q1267_PIC3.png">
 * Input: grid = [[1,1,0,0],[0,0,1,0],[0,0,1,0],[0,0,0,1]]
 * Output: 4
 * Explanation: The two servers in the first row can communicate with each other. The two servers in the third column
 * can communicate with each other. The server at right bottom corner can't communicate with any other server.
 *
 * Constraints:
 *
 * m == grid.length
 * n == grid[i].length
 * 1 <= m <= 250
 * 1 <= n <= 250
 * grid[i][j] == 0 or 1
 */
@RunWith(LeetCodeRunner.class)
public class Q1267_CountServersThatCommunicate {

    @Answer
    public int countServers(int[][] grid) {
        final int m = grid.length, n = grid[0].length;
        int[] rows = new int[m], columns = new int[n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                rows[i] += grid[i][j];
                columns[j] += grid[i][j];
            }
        }

        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1 && (rows[i] > 1 || columns[j] > 1)) {
                    res++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{{1, 0}, {0, 1}}).expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{{1, 0}, {1, 1}}).expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1}
    }).expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new int[][]{
            {1, 0, 0, 1, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 1, 0}
    }).expect(3);

}
