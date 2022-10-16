package q2000;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1958. Check if Move is Legal
 * https://leetcode.com/problems/check-if-move-is-legal/
 *
 * You are given a 0-indexed 8 x 8 grid board, where board[r][c] represents the cell (r, c) on a game board. On the
 * board, free cells are represented by '.', white cells are represented by 'W', and black cells are represented by
 * 'B'.
 *
 * Each move in this game consists of choosing a free cell and changing it to the color you are playing as (either white
 * or black). However, a move is only legal if, after changing it, the cell becomes the endpoint of a good line
 * (horizontal, vertical, or diagonal).
 *
 * A good line is a line of three or more cells (including the endpoints) where the endpoints of the line are one color,
 * and the remaining cells in the middle are the opposite color (no cells in the line are free). You can find examples
 * for good lines in the figure below:
 * (图Q1958_PIC1.png)
 *
 * Given two integers rMove and cMove and a character color representing the color you are playing as (white or black),
 * return true if changing cell (rMove, cMove) to color color is a legal move, or false if it is not legal.
 *
 * Example 1:
 * (图Q1958_PIC2.png)
 * Input: board =
 * [[".",".",".","B",".",".",".","."],[".",".",".","W",".",".",".","."],[".",".",".","W",".",".",".","."],[".",".","
 * .","W",".",".",".","."],["W","B","B",".","W","W","W","B"],[".",".",".","B",".",".",".","."],[".",".",".","B",".","
 * .",".","."],[".",".",".","W",".",".",".","."]],
 * rMove = 4, cMove = 3, color = "B"
 * Output: true
 * Explanation: '.', 'W', and 'B' are represented by the colors blue, white, and black respectively, and cell (rMove,
 * cMove) is marked with an 'X'.
 * The two good lines with the chosen cell as an endpoint are annotated above with the red rectangles.
 *
 * Example 2:
 * (图Q1958_PIC3.png)
 * Input: board =
 * [[".",".",".",".",".",".",".","."],[".","B",".",".","W",".",".","."],[".",".","W",".",".",".",".","."],[".",".","
 * .","W","B",".",".","."],[".",".",".",".",".",".",".","."],[".",".",".",".","B","W",".","."],[".",".",".",".",".","
 * .","W","."],[".",".",".",".",".",".",".","B"]],
 * rMove = 4, cMove = 4, color = "W"
 * Output: false
 * Explanation: While there are good lines with the chosen cell as a middle cell, there are no good lines with the
 * chosen cell as an endpoint.
 *
 * Constraints:
 *
 * board.length == board[r].length == 8
 * 0 <= rMove, cMove < 8
 * board[rMove][cMove] == '.'
 * color is either 'B' or 'W'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1958_CheckIfMoveIsLegal {

    @Answer
    public boolean checkMove(char[][] board, int rMove, int cMove, char color) {
        for (int dy = -1; dy <= 1; dy++) {
            for (int dx = -1; dx <= 1; dx++) {
                if (dy == 0 && dx == 0) {
                    continue;
                }
                int y = rMove + dy, x = cMove + dx;
                if (y == -1 || x == -1 || y == N || x == N
                        || board[y][x] == color || board[y][x] == '.') {
                    continue;
                }

                y += dy;
                x += dx;
                while (y >= 0 && x >= 0 && y < N && x < N) {
                    if (board[y][x] == '.') {
                        break;
                    } else if (board[y][x] == color) {
                        return true;
                    }
                    y += dy;
                    x += dx;
                }
            }
        }
        return false;
    }

    private static final int N = 8;

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new char[][]{
                    {'.', '.', '.', 'B', '.', '.', '.', '.'},
                    {'.', '.', '.', 'W', '.', '.', '.', '.'},
                    {'.', '.', '.', 'W', '.', '.', '.', '.'},
                    {'.', '.', '.', 'W', '.', '.', '.', '.'},
                    {'W', 'B', 'B', '.', 'W', 'W', 'W', 'B'},
                    {'.', '.', '.', 'B', '.', '.', '.', '.'},
                    {'.', '.', '.', 'B', '.', '.', '.', '.'},
                    {'.', '.', '.', 'W', '.', '.', '.', '.'}}, 4, 3, 'B')
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new char[][]{
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', 'B', '.', '.', 'W', '.', '.', '.'},
                    {'.', '.', 'W', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', 'W', 'B', '.', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', '.'},
                    {'.', '.', '.', '.', 'B', 'W', '.', '.'},
                    {'.', '.', '.', '.', '.', '.', 'W', '.'},
                    {'.', '.', '.', '.', '.', '.', '.', 'B'}}, 4, 4, 'W')
            .expect(false);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new char[][]{
                    {'.', '.', 'W', '.', 'B', 'W', 'W', 'B'},
                    {'B', 'W', '.', 'W', '.', 'W', 'B', 'B'},
                    {'.', 'W', 'B', 'W', 'W', '.', 'W', 'W'},
                    {'W', 'W', '.', 'W', '.', '.', 'B', 'B'},
                    {'B', 'W', 'B', 'B', 'W', 'W', 'B', '.'},
                    {'W', '.', 'W', '.', '.', 'B', 'W', 'W'},
                    {'B', '.', 'B', 'B', '.', '.', 'B', 'B'},
                    {'.', 'W', '.', 'W', '.', 'W', '.', 'W'}}, 5, 4, 'W')
            .expect(true);

}
