package q050;

import org.junit.runner.RunWith;
import util.asserthelper.AssertUtils;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/sudoku-solver/
 *
 * Write a program to solve a Sudoku puzzle by filling the empty cells.
 *
 * A sudoku solution must satisfy all of the following rules:
 *
 * Each of the digits 1-9 must occur exactly once in each row.
 * Each of the digits 1-9 must occur exactly once in each column.
 * Each of the the digits 1-9 must occur exactly once in each of the 9 3x3 sub-boxes of the grid.
 *
 * Empty cells are indicated by the character '.'.
 *
 * (图片 Q037_PIC1.bmp)
 * (图片 Q037_PIC2.bmp)
 *
 * A sudoku puzzle...
 *
 *
 * ...and its solution numbers marked in red.
 *
 * Note:
 *
 * The given board contain only digits 1-9 and the character '.'.
 * You may assume that the given Sudoku puzzle will have a single unique solution.
 * The given board size is always 9x9.
 *
 * 题解:
 * 上一题不需要数独是否有效, 这题就是要求数独是否有效.
 */
@RunWith(LeetCodeRunner.class)
public class Q037_SudokuSolver {

    /**
     * dfs 求解, LeetCode 上因为测试用例少, 所以最快的代码是根据输入直接返回对应的结果.
     */
    @Answer
    public void solveSudoku(char[][] board) {
        this.board = board;
        initial();
        dfs(0, 0);
    }

    private boolean[][] claimRow, claimColumn, claimArea;

    private char[][] board;

    private void initial() {
        this.claimRow = new boolean[9][9];
        this.claimColumn = new boolean[9][9];
        this.claimArea = new boolean[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (board[i][j] != '.') {
                    claim(i, j, board[i][j] - '1', true);
                }
            }
        }
    }

    private void claim(int i, int j, int ci, boolean claim) {
        this.claimRow[i][ci] = claim;
        this.claimColumn[j][ci] = claim;
        this.claimArea[i / 3 * 3 + j / 3][ci] = claim;
    }

    private boolean dfs(int i, int j) {
        char c = board[i][j];
        if (c == '.') {
            for (char can = 0; can < 9; can++) {
                if (!isClaimed(i, j, can)) {
                    claim(i, j, can, true);
                    board[i][j] = (char) (can + '1');
                    if (moveNext(i, j)) {
                        return true;
                    } else {
                        claim(i, j, can, false);
                        board[i][j] = '.';
                    }
                }
            }
            return false;
        } else {
            return moveNext(i, j);
        }
    }

    private boolean isClaimed(int i, int j, int ci) {
        return this.claimRow[i][ci]
                || this.claimColumn[j][ci]
                || this.claimArea[i / 3 * 3 + j / 3][ci];
    }

    private boolean moveNext(int i, int j) {
        j++;
        if (j == 9) {
            i++;
            if (i == 9) {
                return true;
            }
            j = 0;
        }
        return dfs(i, j);
    }

    @TestData
    public DataExpectation example1() {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        char[][] expected = new char[][]{
                {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                {'3', '4', '5', '2', '8', '6', '1', '7', '9'}
        };
        return DataExpectation.builder()
                .addArgument(board)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(expected, board);
                })
                .build();
    }

    @TestData
    public DataExpectation example2() {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}};
        char[][] expected = new char[][]{
                {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                {'3', '4', '5', '2', '8', '6', '1', '7', '9'}
        };
        return DataExpectation.builder()
                .addArgument(board)
                .assertMethod((e, a) -> {
                    AssertUtils.assertEquals(expected, board);
                })
                .build();
    }
}
