package q1300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1252. Cells with Odd Values in a Matrix
 * https://leetcode.com/problems/cells-with-odd-values-in-a-matrix/
 *
 * Given n and m which are the dimensions of a matrix initialized by zeros and given an array indices where indices[i] =
 * [ri, ci]. For each pair of [ri, ci] you have to increment all cells in row ri and column ci by 1.
 *
 * Return the number of cells with odd values in the matrix after applying the increment to all indices.
 *
 * Example 1:
 * <img src="Q1252_PIC1.png">
 * Input: n = 2, m = 3, indices = [[0,1],[1,1]]
 * Output: 6
 * Explanation: Initial matrix = [[0,0,0],[0,0,0]].
 * After applying first increment it becomes [[1,2,1],[0,1,0]].
 * The final matrix will be [[1,3,1],[1,3,1]] which contains 6 odd numbers.
 *
 * Example 2:
 * <img src="Q1252_PIC2.png">
 * Input: n = 2, m = 2, indices = [[1,1],[0,0]]
 * Output: 0
 * Explanation: Final matrix = [[2,2],[2,2]]. There is no odd number in the final matrix.
 *
 * Constraints:
 *
 * 1 <= n <= 50
 * 1 <= m <= 50
 * 1 <= indices.length <= 100
 * 0 <= indices[i][0] < n
 * 0 <= indices[i][1] < m
 */
@RunWith(LeetCodeRunner.class)
public class Q1252_CellsWithOddValuesInAMatrix {

    @Answer
    public int oddCells(int n, int m, int[][] indices) {
        long row = 0, column = 0;
        for (int[] index : indices) {
            row ^= 1L << index[0];
            column ^= 1L << index[1];
        }

        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res += ((row >> i) ^ (column >> j)) & 1;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 3, new int[][]{{0, 1}, {1, 1}}).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(2, 2, new int[][]{{1, 1}, {0, 0}}).expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(41, 38, new int[][]{
            {38, 33}, {37, 24}, {33, 6}, {15, 0}, {13, 22}, {1, 13}, {12, 8}, {20, 4}, {30, 9}, {21, 0}, {35, 29},
            {21, 20}, {36, 24}, {40, 16}, {3, 24}, {32, 6}, {19, 28}, {1, 13}, {30, 19}, {28, 20}, {28, 2}
    }).expect(659);

}
