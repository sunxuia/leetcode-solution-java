package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/transform-to-chessboard/
 *
 * An N x N board contains only 0s and 1s. In each move, you can swap any 2 rows with each other, or any 2 columns
 * with each other.
 *
 * What is the minimum number of moves to transform the board into a "chessboard" - a board where no 0s and no 1s are
 * 4-directionally adjacent? If the task is impossible, return -1.
 *
 * Examples:
 * Input: board = [[0,1,1,0],[0,1,1,0],[1,0,0,1],[1,0,0,1]]
 * Output: 2
 * Explanation:
 * One potential sequence of moves is shown below, from left to right:
 *
 * 0110     1010     1010
 * 0110 --> 1010 --> 0101
 * 1001     0101     1010
 * 1001     0101     0101
 *
 * The first move swaps the first and second column.
 * The second move swaps the second and third row.
 *
 *
 * Input: board = [[0, 1], [1, 0]]
 * Output: 0
 * Explanation:
 * Also note that the board with 0 in the top left corner,
 * 01
 * 10
 *
 * is also a valid chessboard.
 *
 * Input: board = [[1, 0], [1, 0]]
 * Output: -1
 * Explanation:
 * No matter what sequence of moves you make, you cannot end with a valid chessboard.
 *
 * Note:
 *
 * board will have the same number of rows and columns, a number in the range [2, 30].
 * board[i][j] will be only 0s or 1s.
 */
@RunWith(LeetCodeRunner.class)
public class Q782_TransformToChessboard {

    // https://www.cnblogs.com/grandyang/p/9053705.html
    @Answer
    public int movesToChessboard(int[][] board) {
        final int n = board.length;
        int rowSum = 0, colSum = 0, rowDiff = 0, colDiff = 0;
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if ((board[0][0] ^ board[i][0] ^ board[0][j] ^ board[i][j]) > 0) {
                    return -1;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            rowSum += board[0][i];
            colSum += board[i][0];
            rowDiff += (board[i][0] == i % 2) ? 1 : 0;
            colDiff += (board[0][i] == i % 2) ? 1 : 0;
        }
        if (n / 2 > rowSum || rowSum > (n + 1) / 2) {
            return -1;
        }
        if (n / 2 > colSum || colSum > (n + 1) / 2) {
            return -1;
        }
        if (n % 2 > 0) {
            if (rowDiff % 2 > 0) {
                rowDiff = n - rowDiff;
            }
            if (colDiff % 2 > 0) {
                colDiff = n - colDiff;
            }
        } else {
            rowDiff = Math.min(n - rowDiff, rowDiff);
            colDiff = Math.min(n - colDiff, colDiff);
        }
        return (rowDiff + colDiff) / 2;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new int[][]{
            {0, 1, 1, 0},
            {0, 1, 1, 0},
            {1, 0, 0, 1},
            {1, 0, 0, 1}
    }).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new int[][]{
            {0, 1},
            {1, 0}
    }).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new int[][]{
            {1, 0},
            {1, 0}
    }).expect(-1);

}
