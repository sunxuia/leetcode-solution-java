package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1329. Sort the Matrix Diagonally
 * https://leetcode.com/problems/sort-the-matrix-diagonally/
 *
 * Given a m * n matrix mat of integers, sort it diagonally in ascending order from the top-left to the bottom-right
 * then return the sorted array.
 *
 * Example 1:
 * <img src="./Q1329_PIC.png">
 * Input: mat = [[3,3,1,1],[2,2,1,2],[1,1,1,2]]
 * Output: [[1,1,1,1],[1,2,2,2],[1,2,3,3]]
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 1 <= m, n <= 100
 * 1 <= mat[i][j] <= 100
 */
@RunWith(LeetCodeRunner.class)
public class Q1329_SortTheMatrixDiagonally {

    @Answer
    public int[][] diagonalSort(int[][] mat) {
        final int m = mat.length, n = mat[0].length;
        for (int i = 0; i < m + n - 1; i++) {
            quickSort(mat, i, 0, length(mat, i) - 1);
        }
        return mat;
    }

    private void quickSort(int[][] mat, int line, int start, int end) {
        if (start >= end) {
            return;
        }
        int guard = get(mat, line, start);
        int i = start, j = end;
        while (i < j) {
            while (i < j && guard <= get(mat, line, j)) {
                j--;
            }
            if (i < j) {
                set(mat, line, i++, get(mat, line, j));
            }
            while (i < j && get(mat, line, i) < guard) {
                i++;
            }
            if (i < j) {
                set(mat, line, j--, get(mat, line, i));
            }
        }
        set(mat, line, i, guard);
        quickSort(mat, line, start, i - 1);
        quickSort(mat, line, i + 1, end);
    }

    private int get(int[][] mat, int line, int idx) {
        final int n = mat[0].length;
        int i = line < n ? idx : line - n + 1 + idx;
        int j = line < n ? n - line - 1 + idx : idx;
        return mat[i][j];
    }

    private void set(int[][] mat, int line, int idx, int val) {
        final int n = mat[0].length;
        int i = line < n ? idx : line - n + 1 + idx;
        int j = line < n ? n - line - 1 + idx : idx;
        mat[i][j] = val;
    }

    private int length(int[][] mat, int line) {
        final int m = mat.length, n = mat[0].length;
        if (line < n) {
            return Math.min(line + 1, m);
        } else {
            return Math.min(m + n - 1 - line, n);
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create(new int[][]{
            {3, 3, 1, 1},
            {2, 2, 1, 2},
            {1, 1, 1, 2}
    }).expect(new int[][]{
            {1, 1, 1, 1},
            {1, 2, 2, 2},
            {1, 2, 3, 3}
    });

}
