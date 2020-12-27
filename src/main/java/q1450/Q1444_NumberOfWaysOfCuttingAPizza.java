package q1450;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1444. Number of Ways of Cutting a Pizza
 * https://leetcode.com/problems/number-of-ways-of-cutting-a-pizza/
 *
 * Given a rectangular pizza represented as a rows x cols matrix containing the following characters: 'A' (an apple) and
 * '.' (empty cell) and given the integer k. You have to cut the pizza into k pieces using k-1 cuts.
 *
 * For each cut you choose the direction: vertical or horizontal, then you choose a cut position at the cell boundary
 * and cut the pizza into two pieces. If you cut the pizza vertically, give the left part of the pizza to a person. If
 * you cut the pizza horizontally, give the upper part of the pizza to a person. Give the last piece of pizza to the
 * last person.
 *
 * Return the number of ways of cutting the pizza such that each piece contains at least one apple. Since the answer can
 * be a huge number, return this modulo 10^9 + 7.
 *
 * Example 1:
 * <img src="./Q1444_PIC.png">
 * Input: pizza = ["A..","AAA","..."], k = 3
 * Output: 3
 * Explanation: The figure above shows the three ways to cut the pizza. Note that pieces must contain at least one
 * apple.
 *
 * Example 2:
 *
 * Input: pizza = ["A..","AA.","..."], k = 3
 * Output: 1
 *
 * Example 3:
 *
 * Input: pizza = ["A..","A..","..."], k = 1
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= rows, cols <= 50
 * rows == pizza.length
 * cols == pizza[i].length
 * 1 <= k <= 10
 * pizza consists of characters 'A' and '.' only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1444_NumberOfWaysOfCuttingAPizza {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int ways(String[] pizza, int k) {
        final int m = pizza.length, n = pizza[0].length();
        int[][][] cache = new int[m][n][k + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                Arrays.fill(cache[i][j], -1);
            }
        }

        int[][] apples = constructApples(pizza);
        return dfs(cache, apples, 0, 0, k);
    }

    private int dfs(int[][][] cache, int[][] apples, int si, int sj, int k) {
        final int m = apples.length - 1;
        final int n = apples[0].length - 1;
        if (k == 1) {
            // 分成1 份的情况
            return hasApple(apples, si, sj, m, n) ? 1 : 0;
        }
        if (cache[si][sj][k] != -1) {
            return cache[si][sj][k];
        }
        int res = 0;
        // 横着切
        for (int i = si + 1; i < m; i++) {
            if (hasApple(apples, si, sj, i, n)) {
                int count = dfs(cache, apples, i, sj, k - 1);
                res = (res + count) % MOD;
            }
        }
        // 竖着切
        for (int j = sj + 1; j < n; j++) {
            if (hasApple(apples, si, sj, m, j)) {
                int count = dfs(cache, apples, si, j, k - 1);
                res = (res + count) % MOD;
            }
        }
        cache[si][sj][k] = res;
        return res;
    }

    /**
     * apples(i,j) 表示 (0, 0)(含) 到 (i, j)(不含) 矩形区域的苹果累加和, 便于计算指定矩形区域内是否有苹果.
     */
    private int[][] constructApples(String[] pizza) {
        final int m = pizza.length, n = pizza[0].length();
        int[][] apples = new int[m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                apples[i + 1][j + 1] = apples[i + 1][j] + apples[i][j + 1] - apples[i][j];
                if (pizza[i].charAt(j) == 'A') {
                    apples[i + 1][j + 1]++;
                }
            }
        }
        return apples;
    }

    /**
     * 判断指定矩形区域内是否有苹果, (si, sj) 是pizza左上角(包含), (ei, ej) 是右下角(不包含).
     */
    private boolean hasApple(int[][] apples, int si, int sj, int ei, int ej) {
        return apples[ei][ej] - apples[ei][sj] - apples[si][ej] + apples[si][sj] > 0;
    }

    private static final int MOD = 10_0000_0007;

    /**
     * 上面解法的dp 改写
     */
    @Answer
    public int ways2(String[] pizza, int k) {
        final int m = pizza.length, n = pizza[0].length();
        int[][] apples = constructApples(pizza);
        // dp[ki][si][sj] 表示 (si, sj)(含) 到 (m, n) 分成ki 块的可能数量.
        int[][][] dp = new int[k + 1][m][n];
        // 分成1 块的情况
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[1][i][j] = hasApple(apples, i, j, m, n) ? 1 : 0;
            }
        }
        for (int ki = 2; ki <= k; ki++) {
            for (int si = m - 1; si >= 0; si--) {
                for (int sj = n - 1; sj >= 0; sj--) {
                    for (int i = si + 1; i < m; i++) {
                        if (hasApple(apples, si, sj, i, n)) {
                            dp[ki][si][sj] = (dp[ki][si][sj] + dp[ki - 1][i][sj]) % MOD;
                        }
                    }
                    for (int j = sj + 1; j < n; j++) {
                        if (hasApple(apples, si, sj, m, j)) {
                            dp[ki][si][sj] = (dp[ki][si][sj] + dp[ki - 1][si][j]) % MOD;
                        }
                    }
                }
            }
        }
        return dp[k][0][0];
    }

    /**
     * 针对上面dp 的优化, 优化了空间占用.
     */
    @Answer
    public int ways3(String[] pizza, int k) {
        final int m = pizza.length, n = pizza[0].length();
        int[][] apples = constructApples(pizza);
        // dp[si][sj] 表示 (si, sj)(含) 到 (m, n) 分成几块的可能数量.
        int[][] dp = new int[m][n];
        // 分成1 块的情况
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                dp[i][j] = hasApple(apples, i, j, m, n) ? 1 : 0;
            }
        }
        for (int ki = 2; ki <= k; ki++) {
            for (int si = 0; si < m; si++) {
                for (int sj = 0; sj < n; sj++) {
                    int count = 0;
                    for (int i = si + 1; i < m; i++) {
                        if (hasApple(apples, si, sj, i, n)) {
                            count = (count + dp[i][sj]) % MOD;
                        }
                    }
                    for (int j = sj + 1; j < n; j++) {
                        if (hasApple(apples, si, sj, m, j)) {
                            count = (count + dp[si][j]) % MOD;
                        }
                    }
                    dp[si][sj] = count;
                }
            }
        }
        return dp[0][0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{
                    "A..",
                    "AAA",
                    "..."
            }, 3)
            .expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{
                    "A..",
                    "AA.",
                    "..."
            }, 3)
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{
                    "A..",
                    "A..",
                    "..."
            }, 1)
            .expect(1);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.read(String[].class), 8)
            .expect(641829390);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{
                    ".A",
                    "AA",
                    "A."
            }, 3)
            .expect(3);

}
