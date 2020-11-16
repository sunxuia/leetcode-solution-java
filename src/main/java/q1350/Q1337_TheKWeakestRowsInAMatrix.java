package q1350;

import java.util.PriorityQueue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1337. The K Weakest Rows in a Matrix
 * https://leetcode.com/problems/the-k-weakest-rows-in-a-matrix/
 *
 * Given a m * n matrix mat of ones (representing soldiers) and zeros (representing civilians), return the indexes of
 * the k weakest rows in the matrix ordered from the weakest to the strongest.
 *
 * A row i is weaker than row j, if the number of soldiers in row i is less than the number of soldiers in row j, or
 * they have the same number of soldiers but i is less than j. Soldiers are always stand in the frontier of a row, that
 * is, always ones may appear first and then zeros.
 *
 * Example 1:
 *
 * Input: mat =
 * [[1,1,0,0,0],
 * [1,1,1,1,0],
 * [1,0,0,0,0],
 * [1,1,0,0,0],
 * [1,1,1,1,1]],
 * k = 3
 * Output: [2,0,3]
 * Explanation:
 * The number of soldiers for each row is:
 * row 0 -> 2
 * row 1 -> 4
 * row 2 -> 1
 * row 3 -> 2
 * row 4 -> 5
 * Rows ordered from the weakest to the strongest are [2,0,3,1,4]
 *
 * Example 2:
 *
 * Input: mat =
 * [[1,0,0,0],
 * [1,1,1,1],
 * [1,0,0,0],
 * [1,0,0,0]],
 * k = 2
 * Output: [0,2]
 * Explanation:
 * The number of soldiers for each row is:
 * row 0 -> 1
 * row 1 -> 4
 * row 2 -> 1
 * row 3 -> 1
 * Rows ordered from the weakest to the strongest are [0,2,3,1]
 *
 * Constraints:
 *
 * m == mat.length
 * n == mat[i].length
 * 2 <= n, m <= 100
 * 1 <= k <= m
 * matrix[i][j] is either 0 or 1.
 */
@RunWith(LeetCodeRunner.class)
public class Q1337_TheKWeakestRowsInAMatrix {

    @Answer
    public int[] kWeakestRows(int[][] mat, int k) {
        final int m = mat.length;
        PriorityQueue<int[]> pq = new PriorityQueue<>(
                k + 1, (a, b) -> a[1] == b[1] ? b[0] - a[0] : b[1] - a[1]);
        for (int i = 0; i < m; i++) {
            pq.offer(new int[]{i, count(mat[i])});
            if (pq.size() > k) {
                pq.poll();
            }
        }

        int[] res = new int[k];
        for (int i = k - 1; i >= 0; i--) {
            res[i] = pq.poll()[0];
        }
        return res;
    }

    private int count(int[] row) {
        int start = 0, end = row.length;
        while (start < end) {
            int mid = (start + end) / 2;
            if (row[mid] == 0) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }
        return end;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(new int[][]{
            {1, 1, 0, 0, 0},
            {1, 1, 1, 1, 0},
            {1, 0, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {1, 1, 1, 1, 1}
    }, 3).expect(new int[]{2, 0, 3});

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(new int[][]{
            {1, 0, 0, 0},
            {1, 1, 1, 1},
            {1, 0, 0, 0},
            {1, 0, 0, 0}
    }, 2).expect(new int[]{0, 2});

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(new int[][]{
            {1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0},
            {1, 1, 0, 0, 0},
            {1, 1, 1, 1, 0},
            {1, 1, 1, 1, 1}
    }, 3).expect(new int[]{1, 2, 3});

}
