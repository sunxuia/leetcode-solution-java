package q800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-tic-tac-toe-state/
 *
 * A Tic-Tac-Toe board is given as a string array board. Return True if and only if it is possible to reach this
 * board position during the course of a valid tic-tac-toe game.
 *
 * The board is a 3 x 3 array, and consists of characters " ", "X", and "O".  The " " character represents an empty
 * square.
 *
 * Here are the rules of Tic-Tac-Toe:
 *
 * Players take turns placing characters into empty squares (" ").
 * The first player always places "X" characters, while the second player always places "O" characters.
 * "X" and "O" characters are always placed into empty squares, never filled ones.
 * The game ends when there are 3 of the same (non-empty) character filling any row, column, or diagonal.
 * The game also ends if all squares are non-empty.
 * No more moves can be played if the game is over.
 *
 * Example 1:
 * Input: board = ["O  ", "   ", "   "]
 * Output: false
 * Explanation: The first player always plays "X".
 *
 * Example 2:
 * Input: board = ["XOX", " X ", "   "]
 * Output: false
 * Explanation: Players take turns making moves.
 *
 * Example 3:
 * Input: board = ["XXX", "   ", "OOO"]
 * Output: false
 *
 * Example 4:
 * Input: board = ["XOX", "O O", "XOX"]
 * Output: true
 *
 * Note:
 *
 * board is a length-3 array of strings, where each string board[i] has length 3.
 * Each board[i][j] is a character in the set {" ", "X", "O"}.
 */
@RunWith(LeetCodeRunner.class)
public class Q794_ValidTicTacToeState {

    @Answer
    public boolean validTicTacToe(String[] board) {
        char[][] bd = new char[3][];
        for (int i = 0; i < 3; i++) {
            bd[i] = board[i].toCharArray();
        }
        int x = 0, o = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                x += bd[i][j] == 'X' ? 1 : 0;
                o += bd[i][j] == 'O' ? 1 : 0;
            }
        }

        return x == o && !hasLine(bd, 'X')
                || x == o + 1 && !hasLine(bd, 'O');
    }

    private boolean hasLine(char[][] bd, char c) {
        if (c == bd[1][1]) {
            if (c == bd[1][0] && c == bd[1][2]
                    || c == bd[0][1] && c == bd[2][1]
                    || c == bd[0][0] && c == bd[2][2]
                    || c == bd[0][2] && c == bd[2][0]) {
                return true;
            }
        }
        if (c == bd[0][0]) {
            if (c == bd[1][0] && c == bd[2][0]
                    || c == bd[0][1] && c == bd[0][2]) {
                return true;
            }
        }
        if (c == bd[2][2]) {
            if (c == bd[1][2] && c == bd[0][2]
                    || c == bd[2][1] && c == bd[2][0]) {
                return true;
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(new String[]{
            "O  ",
            "   ",
            "   "
    }).expect(false);

    @TestData
    public DataExpectation example2 = DataExpectation.create(new String[]{
            "XOX",
            " X ",
            "   "
    }).expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create(new String[]{
            "XXX",
            "   ",
            "OOO"
    }).expect(false);

    @TestData
    public DataExpectation example4 = DataExpectation.create(new String[]{
            "XOX",
            "O O",
            "XOX"
    }).expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(new String[]{
            "XXX",
            "OOX",
            "OOX"
    }).expect(true);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(new String[]{
            "OXX",
            "XOX",
            "OXO"
    }).expect(false);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(new String[]{
            "XOX",
            "X O",
            "X O"
    }).expect(true);

    @TestData
    public DataExpectation normal4 = DataExpectation.create(new String[]{
            "XOX",
            "OOX",
            "XO "
    }).expect(true);

}
