package q100;

import org.junit.runner.RunWith;
import q250.Q212_WordSearchII;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/word-search/
 *
 * Given a 2D board and a word, find if the word exists in the grid.
 *
 * The word can be constructed from letters of sequentially adjacent cell, where "adjacent" cells are those
 * horizontally or vertically neighboring. The same letter cell may not be used more than once.
 *
 * Example:
 *
 * board =
 * [
 * ['A','B','C','E'],
 * ['S','F','C','S'],
 * ['A','D','E','E']
 * ]
 *
 * Given word = "ABCCED", return true.
 * Given word = "SEE", return true.
 * Given word = "ABCB", return false.
 *
 * 相关题目: {@link Q212_WordSearchII}
 */
@RunWith(LeetCodeRunner.class)
public class Q079_WordSearch {

    @Answer
    public boolean exist(char[][] board, String word) {
        final int m = board.length, n = board[0].length;
        boolean[][] visited = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (dfs(board, visited, i, j, word, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean dfs(char[][] board, boolean[][] visited, int i, int j, String word, int wi) {
        if (wi == word.length()) {
            return true;
        }
        if (i == -1 || j == -1 || i == board.length || j == board[0].length
                || visited[i][j] || board[i][j] != word.charAt(wi)) {
            return false;
        }
        visited[i][j] = true;
        boolean match = dfs(board, visited, i - 1, j, word, wi + 1);
        match = match || dfs(board, visited, i, j + 1, word, wi + 1);
        match = match || dfs(board, visited, i + 1, j, word, wi + 1);
        match = match || dfs(board, visited, i, j - 1, word, wi + 1);
        visited[i][j] = match;
        return match;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            })
            .addArgument("ABCCED")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            })
            .addArgument("SEE")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument(new char[][]{
                    {'A', 'B', 'C', 'E'},
                    {'S', 'F', 'C', 'S'},
                    {'A', 'D', 'E', 'E'}
            })
            .addArgument("ABCB")
            .expect(false)
            .build();

}
