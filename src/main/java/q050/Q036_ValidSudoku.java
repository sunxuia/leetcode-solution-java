package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/valid-sudoku/
 *
 * Determine if a 9x9 Sudoku board is valid. Only the filled cells need to be validated according to the following
 * rules:
 *
 * (图片见 P036_PIC.bmp)
 *
 * Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the 9 3x3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 *
 *
 * A partially filled sudoku which is valid.
 *
 * The Sudoku board could be partially filled, where empty cells are filled with the character '.'.
 *
 * Example 1:
 *
 * Input:
 * [
 * ["5","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * Output: true
 *
 * Example 2:
 *
 * Input:
 * [
 * ["8","3",".",".","7",".",".",".","."],
 * ["6",".",".","1","9","5",".",".","."],
 * [".","9","8",".",".",".",".","6","."],
 * ["8",".",".",".","6",".",".",".","3"],
 * ["4",".",".","8",".","3",".",".","1"],
 * ["7",".",".",".","2",".",".",".","6"],
 * [".","6",".",".",".",".","2","8","."],
 * [".",".",".","4","1","9",".",".","5"],
 * [".",".",".",".","8",".",".","7","9"]
 * ]
 * Output: false
 * Explanation: Same as Example 1, except with the 5 in the top left corner being
 * modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
 *
 * 题解:
 * 判断当前已经填了部分值的数独板是否有效.
 *
 * 一个数独板是一个9*9 的网格, 如果一个数独板有效, 那么填满之后的每行都是不重复的数字1-9,
 * 每列都是不重复的数字1-9, 左上/上/右上/左中/中/右中/左下/下/右下 9 个3*3 的区域也是1-9 不重复的数字.
 *
 * 输入的数组中如果是数字则表示已经填上数字了, 如果是'.' 则表示是空的.
 * 注意: 这题只需要检查已经填了值的内容是否满足要求, 没有填值的格子('.') 不需要检查.
 * (即不需要知道这个数独是否真的有解, 这个问题是下一题(Q037) 要解决的问题)
 */
@RunWith(LeetCodeRunner.class)
public class Q036_ValidSudoku {

    @Answer
    public boolean isValidSudoku(char[][] board) {
        final int length = 9;
        boolean[] filled = new boolean[length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                filled[j] = false;
            }
            for (int j = 0; j < length; j++) {
                char c = board[i][j];
                if (c != '.') {
                    if (filled[c - '1']) {
                        return false;
                    }
                    filled[c - '1'] = true;
                }
            }
        }
        for (int j = 0; j < length; j++) {
            for (int i = 0; i < length; i++) {
                filled[i] = false;
            }
            for (int i = 0; i < length; i++) {
                char c = board[i][j];
                if (c != '.') {
                    if (filled[c - '1']) {
                        return false;
                    }
                    filled[c - '1'] = true;
                }
            }
        }
        for (int m = 0; m < length; m++) {
            for (int n = 0; n < length; n++) {
                filled[n] = false;
            }
            for (int n = 0; n < length; n++) {
                int i = (m / 3) * 3 + n / 3;
                int j = (m % 3) * 3 + n % 3;
                char c = board[i][j];
                if (c != '.') {
                    if (filled[c - '1']) {
                        return false;
                    }
                    filled[c - '1'] = true;
                }
            }
        }
        return true;
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
        return DataExpectation.builder()
                .addArgument(board)
                .expect(true)
                .build();
    }

    @TestData
    public DataExpectation example2() {
        // 这里左上角的3*3 格中有2 个8, 所以不成立
        char[][] board = new char[][]{
                {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        return DataExpectation.builder()
                .addArgument(board)
                .expect(false)
                .build();
    }
}
