package q300;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/game-of-life/
 *
 * According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton
 * devised by the British mathematician John Horton Conway in 1970."
 *
 * Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its
 * eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia
 * article):
 *
 * 1. Any live cell with fewer than two live neighbors dies, as if caused by under-population.
 * 2. Any live cell with two or three live neighbors lives on to the next generation.
 * 3. Any live cell with more than three live neighbors dies, as if by over-population..
 * 4. Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
 *
 * Write a function to compute the next state (after one update) of the board given its current state. The next state
 * is created by applying the above rules simultaneously to every cell in the current state, where births and deaths
 * occur simultaneously.
 *
 * Example:
 *
 * Input:
 * [
 * [0,1,0],
 * [0,0,1],
 * [1,1,1],
 * [0,0,0]
 * ]
 * Output:
 * [
 * [0,0,0],
 * [1,0,1],
 * [0,1,1],
 * [0,1,0]
 * ]
 *
 * Follow up:
 *
 * 1. Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update
 * some cells first and then use their updated values to update other cells.
 * 2. In this question, we represent the board using a 2D array. In principle, the board is infinite, which would
 * cause problems when the active area encroaches the border of the array. How would you address these problems?
 *
 * 题解:
 * 参数board 表示这个人是否活着(1: 生, 0: 死), 每个格子的8 个相邻格子(上下左右对角线) 表示他的邻居.
 * 现在有如下规则:
 * 1. 1 的格子如果为1 的邻居数量 < 2, 则变为0. 这表示人口衰退.
 * 2. 1 的格子如果为1 的邻居数量 = 2 或 3, 则本轮保持不变).
 * 3. 1 的格子如果为1 的邻居数量 > 3, 则变为0. 这表示人口过剩.
 * 4. 0 的格子如果为1 的邻居数量 = 3, 则变为1. 这表示人口繁衍.
 *
 * 要求根据这些规则计算出当前board 的下一轮人口形式, 不要创建额外的二维数组.
 */
@RunWith(LeetCodeRunner.class)
public class Q289_GameOfLife {

    @Answer
    public void gameOfLife(int[][] board) {
        if (board == null || board.length == 0) {
            return;
        }
        final int m = board.length, n = board[0].length;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                int count = 0;
                count += livedValue(board, i - 1, j - 1);
                count += livedValue(board, i - 1, j);
                count += livedValue(board, i - 1, j + 1);
                count += livedValue(board, i, j - 1);
                count += livedValue(board, i, j + 1);
                count += livedValue(board, i + 1, j - 1);
                count += livedValue(board, i + 1, j);
                count += livedValue(board, i + 1, j + 1);

                if (board[i][j] == 1) {
                    if (count == 2 || count == 3) {
                        board[i][j] = 3;
                    }
                } else {
                    if (count == 3) {
                        board[i][j] = 2;
                    }
                }
            }
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = board[i][j] >> 1;
            }
        }
    }

    private int livedValue(int[][] board, int i, int j) {
        return -1 < i && i < board.length && -1 < j && j < board[0].length ? (board[i][j] & 1) : 0;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(new int[][]{
                    {0, 1, 0},
                    {0, 0, 1},
                    {1, 1, 1},
                    {0, 0, 0}
            }).expectArgument(0, new int[][]{
                    {0, 0, 0},
                    {1, 0, 1},
                    {0, 1, 1},
                    {0, 1, 0}
            }).build();

}
