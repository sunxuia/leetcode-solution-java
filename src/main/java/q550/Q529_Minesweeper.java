package q550;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/minesweeper/
 *
 * Let's play the minesweeper game (Wikipedia, online game)!
 *
 * You are given a 2D char matrix representing the game board. 'M' represents an unrevealed mine, 'E' represents an
 * unrevealed empty square, 'B' represents a revealed blank square that has no adjacent (above, below, left, right,
 * and all 4 diagonals) mines, digit ('1' to '8') represents how many mines are adjacent to this revealed square, and
 * finally 'X' represents a revealed mine.
 *
 * Now given the next click position (row and column indices) among all the unrevealed squares ('M' or 'E'), return
 * the board after revealing this position according to the following rules:
 *
 * If a mine ('M') is revealed, then the game is over - change it to 'X'.
 * If an empty square ('E') with no adjacent mines is revealed, then change it to revealed blank ('B') and all of
 * its adjacent unrevealed squares should be revealed recursively.
 * If an empty square ('E') with at least one adjacent mine is revealed, then change it to a digit ('1' to '8')
 * representing the number of adjacent mines.
 * Return the board when no more squares will be revealed.
 *
 *
 *
 * Example 1:
 *
 * Input:
 *
 * > [['E', 'E', 'E', 'E', 'E'],
 * >  ['E', 'E', 'M', 'E', 'E'],
 * >  ['E', 'E', 'E', 'E', 'E'],
 * >  ['E', 'E', 'E', 'E', 'E']]
 *
 * Click : [3,0]
 *
 * Output:
 *
 * > [['B', '1', 'E', '1', 'B'],
 * >  ['B', '1', 'M', '1', 'B'],
 * >  ['B', '1', '1', '1', 'B'],
 * >  ['B', 'B', 'B', 'B', 'B']]
 *
 * Explanation:
 *
 * (图 Q529_PIC1.png)
 *
 * Example 2:
 *
 * Input:
 *
 * > [['B', '1', 'E', '1', 'B'],
 * >  ['B', '1', 'M', '1', 'B'],
 * >  ['B', '1', '1', '1', 'B'],
 * >  ['B', 'B', 'B', 'B', 'B']]
 *
 * Click : [1,2]
 *
 * Output:
 *
 * [['B', '1', 'E', '1', 'B'],
 * >  ['B', '1', 'X', '1', 'B'],
 * >  ['B', '1', '1', '1', 'B'],
 * >  ['B', 'B', 'B', 'B', 'B']]
 *
 * Explanation:
 *
 * (图 Q529_PIC2.png)
 *
 *
 * Note:
 *
 * The range of the input matrix's height and width is [1,50].
 * The click position will only be an unrevealed square ('M' or 'E'), which also means the input board contains
 * at least one clickable square.
 * The input board won't be a stage when game is over (some mines have been revealed).
 * For simplicity, not mentioned rules should be ignored in this problem. For example, you don't need to reveal
 * all the unrevealed mines when the game is over, consider any cases that you will win the game or flag any
 * squares.
 */
@RunWith(LeetCodeRunner.class)
public class Q529_Minesweeper {

    @Answer
    public char[][] updateBoard(char[][] board, int[] click) {
        int y = click[0], x = click[1];
        if (board[y][x] == 'M') {
            board[y][x] = 'X';
            return board;
        }
        unreveal(board, y, x);
        return board;
    }

    private void unreveal(char[][] board, int y, int x) {
        if (y < 0 || y >= board.length
                || x < 0 || x >= board[0].length
                || board[y][x] != 'E') {
            return;
        }
        int count = mineCount(board, y - 1, x - 1)
                + mineCount(board, y - 1, x)
                + mineCount(board, y - 1, x + 1)
                + mineCount(board, y, x - 1)
                + mineCount(board, y, x + 1)
                + mineCount(board, y + 1, x - 1)
                + mineCount(board, y + 1, x)
                + mineCount(board, y + 1, x + 1);

        if (count == 0) {
            board[y][x] = 'B';
            unreveal(board, y - 1, x - 1);
            unreveal(board, y - 1, x);
            unreveal(board, y - 1, x + 1);
            unreveal(board, y, x - 1);
            unreveal(board, y, x + 1);
            unreveal(board, y + 1, x - 1);
            unreveal(board, y + 1, x);
            unreveal(board, y + 1, x + 1);
        } else {
            board[y][x] = (char) (count + '0');
        }
    }

    private int mineCount(char[][] board, int y, int x) {
        if (y < 0 || y >= board.length
                || x < 0 || x >= board[0].length) {
            return 0;
        }
        return board[y][x] == 'M' ? 1 : 0;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new char[][]{
                    {'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'M', 'E', 'E'},
                    {'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'E', 'E', 'E'}},
            new int[]{3, 0})
            .expect(new char[][]{
                    {'B', '1', 'E', '1', 'B'},
                    {'B', '1', 'M', '1', 'B'},
                    {'B', '1', '1', '1', 'B'},
                    {'B', 'B', 'B', 'B', 'B'}});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new char[][]{
                    {'B', '1', 'E', '1', 'B'},
                    {'B', '1', 'M', '1', 'B'},
                    {'B', '1', '1', '1', 'B'},
                    {'B', 'B', 'B', 'B', 'B'}},
            new int[]{1, 2})
            .expect(new char[][]{
                    {'B', '1', 'E', '1', 'B'},
                    {'B', '1', 'X', '1', 'B'},
                    {'B', '1', '1', '1', 'B'},
                    {'B', 'B', 'B', 'B', 'B'}});

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new char[][]{
                    {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'M'},
                    {'E', 'E', 'M', 'E', 'E', 'E', 'E', 'E'},
                    {'M', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E'},
                    {'E', 'E', 'M', 'M', 'E', 'E', 'E', 'E'}},
            new int[]{0, 0})
            .expect(new char[][]{
                    {'B', 'B', 'B', 'B', 'B', 'B', '1', 'E'},
                    {'B', '1', '1', '1', 'B', 'B', '1', 'M'},
                    {'1', '2', 'M', '1', 'B', 'B', '1', '1'},
                    {'M', '2', '1', '1', 'B', 'B', 'B', 'B'},
                    {'1', '1', 'B', 'B', 'B', 'B', 'B', 'B'},
                    {'B', 'B', 'B', 'B', 'B', 'B', 'B', 'B'},
                    {'B', '1', '2', '2', '1', 'B', 'B', 'B'},
                    {'B', '1', 'M', 'M', '1', 'B', 'B', 'B'}});

}
