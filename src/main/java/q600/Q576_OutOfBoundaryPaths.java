package q600;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/out-of-boundary-paths/
 *
 * There is an m by n grid with a ball. Given the start coordinate (i,j) of the ball, you can move the ball to
 * adjacent cell or cross the grid boundary in four directions (up, down, left, right). However, you can at most move
 * N times. Find out the number of paths to move the ball out of grid boundary. The answer may be very large, return
 * it after mod 10^9 + 7.
 *
 * Example 1:
 *
 * Input: m = 2, n = 2, N = 2, i = 0, j = 0
 * Output: 6
 * Explanation:
 *
 * (图 Q576_PIC1.png)
 *
 * Example 2:
 *
 * Input: m = 1, n = 3, N = 3, i = 0, j = 1
 * Output: 12
 * Explanation:
 *
 * (图 Q576_PIC2.png)
 *
 * Note:
 *
 * 1. Once you move the ball out of boundary, you cannot move it back.
 * 2. The length and height of the grid is in range [1,50].
 * 3. N is in range [0,50].
 */
@RunWith(LeetCodeRunner.class)
public class Q576_OutOfBoundaryPaths {

    // 这道题暴力dfs 会超时.
    @Answer
    public int findPaths(int m, int n, int N, int i, int j) {
        long[][][] counts = new long[m][n][N + 1];
        for (int x = 0; x < m; x++) {
            for (int y = 0; y < n; y++) {
                Arrays.fill(counts[x][y], -1);
            }
        }
        long res = dfs(counts, N, i, j);
        return (int) (res % MOD);
    }

    private static final long MOD = 10_0000_0007L;

    private long dfs(long[][][] counts, int n, int i, int j) {
        if (n == -1) {
            return 0L;
        }
        if (i == -1 || j == -1 || i == counts.length || j == counts[0].length) {
            return 1L;
        }
        if (counts[i][j][n] > -1) {
            return counts[i][j][n];
        }
        long count = dfs(counts, n - 1, i - 1, j)
                + dfs(counts, n - 1, i, j + 1)
                + dfs(counts, n - 1, i + 1, j)
                + dfs(counts, n - 1, i, j - 1);
        // 这里取余对结果无影响, 如果不取余, 则需要使用BigDecimal, 这个速度上会慢一些 (3ms 对 9ms)
        count %= MOD;
        return counts[i][j][n] = count;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, 2, 2, 0, 0).expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(1, 3, 3, 0, 1).expect(12);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith(8, 7, 16, 1, 5).expect(102984580);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith(8, 50, 23, 5, 26).expect(914783380);

    @TestData
    public DataExpectation normal3 = DataExpectation.createWith(36, 5, 50, 15, 3).expect(390153306);

}
