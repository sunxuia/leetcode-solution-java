package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2018. Check if Word Can Be Placed In Crossword
 * https://leetcode.com/problems/check-if-word-can-be-placed-in-crossword/
 *
 * You are given an m x n matrix board, representing the current state of a crossword puzzle. The crossword contains
 * lowercase English letters (from solved words), ' ' to represent any empty cells, and '#' to represent any blocked
 * cells.
 *
 * A word can be placed horizontally (left to right or right to left) or vertically (top to bottom or bottom to top) in
 * the board if:
 *
 * It does not occupy a cell containing the character '#'.
 * The cell each letter is placed in must either be ' ' (empty) or match the letter already on the board.
 * There must not be any empty cells ' ' or other lowercase letters directly left or right of the word if the word was
 * placed horizontally.
 * There must not be any empty cells ' ' or other lowercase letters directly above or below the word if the word was
 * placed vertically.
 *
 * Given a string word, return true if word can be placed in board, or false otherwise.
 *
 * Example 1:
 * (图Q2018_PIC1.png)
 * Input: board = [["#", " ", "#"], [" ", " ", "#"], ["#", "c", " "]], word = "abc"
 * Output: true
 * Explanation: The word "abc" can be placed as shown above (top to bottom).
 *
 * Example 2:
 * (图Q2018_PIC2.png)
 * Input: board = [[" ", "#", "a"], [" ", "#", "c"], [" ", "#", "a"]], word = "ac"
 * Output: false
 * Explanation: It is impossible to place the word because there will always be a space/letter above or below it.
 *
 * Example 3:
 * (图Q2018_PIC3.png)
 * Input: board = [["#", " ", "#"], [" ", " ", "#"], ["#", " ", "c"]], word = "ca"
 * Output: true
 * Explanation: The word "ca" can be placed as shown above (right to left).
 *
 * Constraints:
 *
 * m == board.length
 * n == board[i].length
 * 1 <= m * n <= 2 * 10^5
 * board[i][j] will be ' ', '#', or a lowercase English letter.
 * 1 <= word.length <= max(m, n)
 * word will contain only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q2018_CheckIfWordCanBePlacedInCrossword {

    @Answer
    public boolean placeWordInCrossword(char[][] board, String word) {
        final int m = board.length;
        final int n = board[0].length;
        final char[] cs = word.toCharArray();
        final int len = cs.length;

        for (int i = 0; i < m; i++) {
            int prev = -1;
            for (int j = 0; j <= n; j++) {
                if (j == n || board[i][j] == '#') {
                    if (j - prev - 1 == len) {
                        // 左->右匹配
                        boolean match = true;
                        for (int k = 0; k < len && match; k++) {
                            char c = board[i][prev + 1 + k];
                            match = c == ' ' || c == cs[k];
                        }
                        if (!match) {
                            // 右 ->左匹配
                            match = true;
                            for (int k = 0; k < len && match; k++) {
                                char c = board[i][j - 1 - k];
                                match = c == ' ' || c == cs[k];
                            }
                        }
                        if (match) {
                            return true;
                        }
                    }
                    prev = j;
                }
            }
        }

        for (int j = 0; j < n; j++) {
            int prev = -1;
            for (int i = 0; i <= m; i++) {
                if (i == m || board[i][j] == '#') {
                    if (i - prev - 1 == len) {
                        // 上 ->下匹配
                        boolean match = true;
                        for (int k = 0; k < len && match; k++) {
                            char c = board[prev + 1 + k][j];
                            match = c == ' ' || c == cs[k];
                        }
                        if (!match) {
                            // 下 ->上匹配
                            match = true;
                            for (int k = 0; k < len && match; k++) {
                                char c = board[i - 1 - k][j];
                                match = c == ' ' || c == cs[k];
                            }
                        }
                        if (match) {
                            return true;
                        }
                    }
                    prev = i;
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new char[][]{
                    {'#', ' ', '#'},
                    {' ', ' ', '#'},
                    {'#', 'c', ' '}}, "abc")
            .expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new char[][]{
                    {' ', '#', 'a'},
                    {' ', '#', 'c'},
                    {' ', '#', 'a'}}, "ac")
            .expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(new char[][]{
                    {'#', ' ', '#'},
                    {' ', ' ', '#'},
                    {'#', ' ', 'c'}}, "ca")
            .expect(true);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new char[][]{{' '}, {'#'}, {'o'}, {' '}, {'t'}, {'m'}, {'o'}, {' '}, {'#'}, {' '}}, "octmor")
            .expect(true);

}
