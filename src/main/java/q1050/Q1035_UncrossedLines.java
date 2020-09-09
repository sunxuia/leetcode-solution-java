package q1050;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1035. Uncrossed Lines
 * https://leetcode.com/problems/uncrossed-lines/
 *
 * We write the integers of A and B (in the order they are given) on two separate horizontal lines.
 *
 * Now, we may draw connecting lines: a straight line connecting two numbers A[i] and B[j] such that:
 *
 * A[i] == B[j];
 * The line we draw does not intersect any other connecting (non-horizontal) line.
 *
 * Note that a connecting lines cannot intersect even at the endpoints: each number can only belong to one connecting
 * line.
 *
 * Return the maximum number of connecting lines we can draw in this way.
 *
 * Example 1:
 * (图 Q1035_PIC.png)
 * Input: A = [1,4,2], B = [1,2,4]
 * Output: 2
 * Explanation: We can draw 2 uncrossed lines as in the diagram.
 * We cannot draw 3 uncrossed lines, because the line from A[1]=4 to B[2]=4 will intersect the line from A[2]=2 to
 * B[1]=2.
 *
 * Example 2:
 *
 * Input: A = [2,5,1,2,5], B = [10,5,2,1,5,2]
 * Output: 3
 *
 * Example 3:
 *
 * Input: A = [1,3,7,1,7,5], B = [1,9,2,5,1]
 * Output: 2
 *
 * Note:
 *
 * 1 <= A.length <= 500
 * 1 <= B.length <= 500
 * 1 <= A[i], B[i] <= 2000
 */
@RunWith(LeetCodeRunner.class)
public class Q1035_UncrossedLines {

    /**
     * 带缓存的dfs, 不带缓存会超时
     */
    @Answer
    public int maxUncrossedLines(int[] A, int[] B) {
        int[][] cache = new int[A.length][B.length];
        for (int i = 0; i < cache.length; i++) {
            Arrays.fill(cache[i], -1);
        }
        return dfs(A, 0, B, 0, cache);
    }

    private int dfs(int[] A, int i, int[] B, int j, int[][] cache) {
        if (i == A.length || j == B.length) {
            return 0;
        }
        if (cache[i][j] != -1) {
            return cache[i][j];
        }
        // A[i] 不做连线
        int res = dfs(A, i + 1, B, j, cache);
        for (int k = j; k < B.length; k++) {
            if (A[i] == B[k]) {
                // A[i] 与 B[nj] 连线
                res = Math.max(res, 1 + dfs(A, i + 1, B, k + 1, cache));
                break;
            }
        }
        cache[i][j] = res;
        return res;
    }

    /**
     * 根据上面思路改进而来的dp 解法
     */
    @Answer
    public int maxUncrossedLines_dp(int[] A, int[] B) {
        final int m = A.length, n = B.length;
        // dp[i][j] 表示 A[i:m) 和B[j:n) 子序列的不相交数量,
        // 最后一行和最后一列是哨兵.
        int[][] dp = new int[m + 1][n + 1];
        for (int i = m - 1; i >= 0; i--) {
            for (int j = n - 1; j >= 0; j--) {
                dp[i][j] = dp[i + 1][j];
                for (int k = j; k < n; k++) {
                    if (A[i] == B[k]) {
                        dp[i][j] = Math.max(dp[i][j], 1 + dp[i + 1][k + 1]);
                        break;
                    }
                }
            }
        }
        return dp[0][0];
    }

    /**
     * LeetCode 中比较快的解法.
     * 相比上面的从后往前的思路, 这个是从前往后的.
     */
    @Answer
    public int maxUncrossedLines3(int[] A, int[] B) {
        final int m = A.length, n = B.length;
        // dp[i+1][j+1] 表示 A[0:i] 和 B[0:j] 子序列的不相交数量.
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (A[i] == B[j]) {
                    dp[i + 1][j + 1] = 1 + dp[i][j];
                } else {
                    dp[i + 1][j + 1] = Math.max(dp[i][j + 1], dp[i + 1][j]);
                }
            }
        }
        return dp[m][n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new int[]{1, 4, 2}, new int[]{1, 2, 4})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new int[]{2, 5, 1, 2, 5}, new int[]{10, 5, 2, 1, 5, 2})
            .expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new int[]{1, 3, 7, 1, 7, 5}, new int[]{1, 9, 2, 5, 1})
            .expect(2);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(new int[]{
                    15, 14, 1, 7, 15, 1, 12, 18, 9, 15, 1, 20, 18, 15, 16, 18, 11, 8, 11, 18, 11, 11, 17, 20, 16, 20,
                    15, 15, 9, 18, 16, 4, 16, 1, 13, 10, 10, 20, 4, 18, 17, 3, 8, 1, 8, 19, 14, 10, 10, 12
            }, new int[]{
                    12, 8, 17, 4, 2, 18, 16, 10, 11, 12, 7, 1, 8, 16, 4, 14, 12, 18, 18, 19, 19, 1, 11, 18, 1, 6, 12,
                    17, 6, 19, 10, 5, 11, 16, 6, 17, 12, 1, 9, 3, 19, 2, 18, 18, 2, 4, 11, 11, 14, 9, 20, 19, 2, 20, 9,
                    15, 8, 7, 8, 6, 19, 12, 4, 11, 18, 18, 1, 6, 9, 17, 13, 19, 5, 4, 14, 9, 11, 15, 2, 5, 4, 1, 10, 11,
                    6, 4, 9, 7, 11, 7, 3, 8, 11, 12, 4, 19, 12, 17, 14, 18
            }).expect(23);

}
