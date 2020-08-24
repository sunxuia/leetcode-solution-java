package q1000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 999. Available Captures for Rook
 * https://leetcode.com/problems/available-captures-for-rook/
 *
 * On an 8 x 8 chessboard, there is one white rook.  There also may be empty squares, white bishops, and black pawns.
 * These are given as characters 'R', '.', 'B', and 'p' respectively. Uppercase characters represent white pieces, and
 * lowercase characters represent black pieces.
 *
 * The rook moves as in the rules of Chess: it chooses one of four cardinal directions (north, east, west, and south),
 * then moves in that direction until it chooses to stop, reaches the edge of the board, or captures an opposite colored
 * pawn by moving to the same square it occupies.  Also, rooks cannot move into the same square as other friendly
 * bishops.
 *
 * Return the number of pawns the rook can capture in one move.
 *
 * Example 1:
 * (图 Q999_PIC1.png)
 * Input: [[".",".",".",".",".",".",".","."],[".",".",".","p",".",".",".","."],[".",".",".","R",".",".",".","p"],["
 * .",".",".",".",".",".",".","."],[".",".",".",".",".",".",".","."],[".",".",".","p",".",".",".","."],[".",".",".","
 * .",".",".",".","."],[".",".",".",".",".",".",".","."]]
 * Output: 3
 * Explanation:
 * In this example the rook is able to capture all the pawns.
 *
 * Example 2:
 * (图 Q999_PIC2.png)
 * Input: [[".",".",".",".",".",".",".","."],[".","p","p","p","p","p",".","."],[".","p","p","B","p","p",".","."],["
 * .","p","B","R","B","p",".","."],[".","p","p","B","p","p",".","."],[".","p","p","p","p","p",".","."],[".",".",".","
 * .",".",".",".","."],[".",".",".",".",".",".",".","."]]
 * Output: 0
 * Explanation:
 * Bishops are blocking the rook to capture any pawn.
 *
 * Example 3:
 * (图 Q999_PIC3.png)
 * Input: [[".",".",".",".",".",".",".","."],[".",".",".","p",".",".",".","."],[".",".",".","p",".",".",".","."],
 * ["p","p",".","R",".","p","B","."],[".",".",".",".",".",".",".","."],[".",".",".","B",".",".",".","."],[".",".","
 * .","p",".",".",".","."],[".",".",".",".",".",".",".","."]]
 * Output: 3
 * Explanation:
 * The rook can capture the pawns at positions b5, d6 and f5.
 *
 * Note:
 *
 * board.length == board[i].length == 8
 * board[i][j] is either 'R', '.', 'B', or 'p'
 * There is exactly one cell with board[i][j] == 'R'
 */
@RunWith(LeetCodeRunner.class)
public class Q999_AvailableCapturesForRook {

    @Answer
    public int numRookCaptures(char[][] board) {
        final int n = 8;
        final int[] directions = new int[]{0, 1, 0, -1, 0};

        int ri = 0, rj = 0;
        findLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (board[i][j] == 'R') {
                    ri = i;
                    rj = j;
                    break findLoop;
                }
            }
        }

        int res = 0;
        for (int k = 0; k < 4; k++) {
            int i = ri, j = rj;
            while (true) {
                i += directions[k];
                j += directions[k + 1];
                if (i == -1 || j == -1 || i == n || j == n) {
                    break;
                }
                if (board[i][j] == '.') {
                    continue;
                }
                if (board[i][j] == 'p') {
                    res++;
                }
                break;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new char[][]{
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', 'p', '.', '.', '.', '.'},
                    {'.', '.', '.', 'R', '.', '.', '.', 'p'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', 'p', '.', '.', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'}
            }).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new char[][]{
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', 'p', 'p', 'p', 'p', 'p', '.', '.'},
                    {'.', 'p', 'p', 'B', 'p', 'p', '.', '.'},
                    {'.', 'p', 'B', 'R', 'B', 'p', '.', '.'},
                    {'.', 'p', 'p', 'B', 'p', 'p', '.', '.'},
                    {'.', 'p', 'p', 'p', 'p', 'p', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'}
            }).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation
            .create(new char[][]{
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', 'p', '.', '.', '.', '.'},
                    {'.', '.', '.', 'p', '.', '.', '.', '.'},
                    {'p', 'p', '.', 'R', '.', 'p', 'B', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', 'B', '.', '.', '.', '.'},
                    {'.', '.', '.', 'p', '.', '.', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'}
            }).expect(3);

}
