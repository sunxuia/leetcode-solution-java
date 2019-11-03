package q100;

import java.util.Stack;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/n-queens-ii/
 *
 * The n-queens puzzle is the problem of placing n queens on an n×n chessboard such that no two queens attack each
 * other.
 *
 * (图见 Q052_PIC.png)
 *
 * Given an integer n, return the number of distinct solutions to the n-queens puzzle.
 *
 * Example:
 *
 * Input: 4
 * Output: 2
 * Explanation: There are two distinct solutions to the 4-queens puzzle as shown below.
 * [
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
 */
@RunWith(LeetCodeRunner.class)
public class Q052_NQueueII {

    /**
     * 类似上一题的dfs 的stack 实现方式. 这个的运行速度比较慢, 空间占用也高.
     */
    @Answer
    public int totalNQueens(int n) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        boolean[] row = new boolean[n],
                column = new boolean[n],
                diagonal = new boolean[2 * n - 1],
                diagonalReverse = new boolean[2 * n - 1];
        while (!stack.isEmpty()) {
            int x = stack.pop(), y = stack.size();
            if (y == n) {
                res++;
                continue;
            }
            if (x > 0) {
                row[x - 1] = column[y]
                        = diagonal[n - y + x - 2]
                        = diagonalReverse[x + y - 1]
                        = false;
            }
            x = Math.abs(x);
            if (x == n) {
                continue;
            }
            if (!row[x]
                    && !column[y]
                    && !diagonal[n - y + x - 1]
                    && !diagonalReverse[x + y]) {
                row[x] = column[y]
                        = diagonal[n - y + x - 1]
                        = diagonalReverse[x + y]
                        = true;
                stack.push(x + 1);
                stack.push(0);
            } else {
                stack.push(-x - 1);
            }
        }
        return res;
    }

    /**
     * 针对上一题的空间和时间占用情况, 进行修改.
     * (LeetCode 中递归要比Stack 快)
     */
    @Answer
    public int dfsEnhanced(int n) {
        return dfs(n, 0, new int[n]);
    }

    // arr 表示第y 行占据了第arr[y] 列的格子.
    private int dfs(int n, int y, int[] arr) {
        if (y == n) {
            return 1;
        }
        int res = 0;
        for (int x = 0; x < n; x++) {
            boolean isValid = true;
            for (int j = 0; isValid && j < y; j++) {
                isValid = arr[j] != x
                        && y + x != j + arr[j]
                        && x - y != arr[j] - j;
            }
            if (isValid) {
                arr[y] = x;
                res += dfs(n, y + 1, arr);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(4).expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create(1).expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.create(2).expect(0);

    @TestData
    public DataExpectation normal3 = DataExpectation.create(3).expect(0);

}
