package q100;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/n-queens/
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each
 * other.
 *
 * (图片见 P051_PIC.bmp)
 *
 * Given an integer n, return all distinct solutions to the n-queens puzzle.
 *
 * Each solution contains a distinct board configuration of the n-queens' placement, where 'Q' and '.' both indicate
 * a queen and an empty space respectively.
 *
 * Example:
 *
 * Input: 4
 * Output: [
 * [".Q..",  // Solution 1
 * "...Q",
 * "Q...",
 * "..Q."],
 *
 * ["..Q.",  // Solution 2
 * "Q...",
 * "...Q",
 * ".Q.."]
 * ]
 * Explanation: There exist two distinct solutions to the 4-queens puzzle as shown above.
 */
@RunWith(LeetCodeRunner.class)
public class Q051_NQueens {

    @Answer
    public List<List<String>> solveNQueens(int n) {
        this.n = n;

        // 可选的不同情况(Q 在一行中不同列的情况), 用于生成结果
        queues = new String[n];
        StringBuilder sb = new StringBuilder(n);
        for (int i = 0; i < n; i++) {
            sb.append('.');
        }
        for (int i = 0; i < n; i++) {
            sb.setCharAt(i, 'Q');
            queues[i] = sb.toString();
            sb.setCharAt(i, '.');
        }

        candidates = new ArrayList<>(n);
        row = new boolean[n];
        column = new boolean[n];
        diagonal = new boolean[2 * n - 1];
        diagonalReverse = new boolean[2 * n - 1];
        result = new ArrayList<>();

        dfs(0);
        return result;
    }

    private int n;

    private List<List<String>> result;

    // dfs 遍历时可能的排列
    private ArrayList<String> candidates;

    private String[] queues;

    // 行, 列, 2个对角线的占用情况
    private boolean[] row, column, diagonal, diagonalReverse;

    private void dfs(int y) {
        if (y == n) {
            result.add((List<String>) candidates.clone());
            return;
        }
        for (int x = 0; x < n; x++) {
            if (isValid(y, x)) {
                setValid(y, x, true);
                candidates.add(queues[x]);
                dfs(y + 1);
                candidates.remove(y);
                setValid(y, x, false);
            }
        }
    }

    private boolean isValid(int y, int x) {
        return !row[x]
                && !column[y]
                && !diagonal[n - y + x - 1]
                && !diagonalReverse[x + y];
    }

    private void setValid(int y, int x, boolean valid) {
        row[x] = column[y]
                = diagonal[n - y + x - 1]
                = diagonalReverse[x + y]
                = valid;
    }

    @TestData
    public DataExpectation example = DataExpectation.builder()
            .addArgument(4)
            .expect(new String[][]{
                    {
                            ".Q..",
                            "...Q",
                            "Q...",
                            "..Q."
                    },
                    {
                            "..Q.",
                            "Q...",
                            "...Q",
                            ".Q.."
                    }
            })
            .unorderResult()
            .build();
}
