package q1750;

import java.util.ArrayList;
import java.util.List;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1735. Count Ways to Make Array With Product
 * https://leetcode.com/problems/count-ways-to-make-array-with-product/
 *
 * You are given a 2D integer array, queries. For each queries[i], where queries[i] = [ni, ki], find the number of
 * different ways you can place positive integers into an array of size ni such that the product of the integers is ki.
 * As the number of ways may be too large, the answer to the ith query is the number of ways modulo 109 + 7.
 *
 * Return an integer array answer where answer.length == queries.length, and answer[i] is the answer to the ith query.
 *
 * Example 1:
 *
 * Input: queries = [[2,6],[5,1],[73,660]]
 * Output: [4,1,50734910]
 * Explanation: Each query is independent.
 * [2,6]: There are 4 ways to fill an array of size 2 that multiply to 6: [1,6], [2,3], [3,2], [6,1].
 * [5,1]: There is 1 way to fill an array of size 5 that multiply to 1: [1,1,1,1,1].
 * [73,660]: There are 1050734917 ways to fill an array of size 73 that multiply to 660. 1050734917 modulo 109 + 7 =
 * 50734910.
 *
 * Example 2:
 *
 * Input: queries = [[1,1],[2,2],[3,3],[4,4],[5,5]]
 * Output: [1,2,3,10,5]
 *
 * Constraints:
 *
 * 1 <= queries.length <= 10^4
 * 1 <= ni, ki <= 10^4
 */
@RunWith(LeetCodeRunner.class)
public class Q1735_CountWaysToMakeArrayWithProduct {

    /**
     * 质因数分解后使用插板法求组合数.
     * 假设对数字 k 进行质因数分解后, k = a1^e1 * a2^e2 * ... * ax^ex, 现要将这些数字整合为 n 个数字,
     * 则这题可以等效为将 sum = e1 + e2 + ... + ex 个球放入 n 个盒子(盒子可以为空),
     * 之后可以转换为将 sum 个球放入 n + sum 个盒子里 (盒子不可以为空),
     *      (也就是在 sum 个球中插入 n 个空球, 这样不影响结果, 但是可以让所有盒子都被填满.)
     * 之后通过插板法, 在 sum + n 之间的sum+n-1 个空隙中插入 sum-1 个板子, 将其分入不同的盒子中,
     * 则结果就变成求排列组合数 C (sum-1, sum + n -1),  sum+n-1 中找出 sum-1 的排列组合.
     *
     * @formatter:off
     * 参考文档
     * https://leetcode-cn.com/problems/count-ways-to-make-array-with-product/solution/fen-jie-zhi-yin-shu-ge-ban-fa-by-ujimats-y4uu/
     * @formatter:on
     */
    @Answer
    public int[] waysToFillArray(int[][] queries) {
        int[] res = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int n = queries[i][0], k = queries[i][1];
            List<Integer> primeCounts = getPrimeFactorCounts(k);
            long w = 1;
            for (int c : primeCounts) {
                w = w * C[n + c - 1][c] % MOD;
            }
            res[i] = (int) w;
        }
        return res;
    }

    /**
     * 对数字 val 进行质因数分解, 取得每个质因数的数量.
     */
    private List<Integer> getPrimeFactorCounts(int val) {
        List<Integer> res = new ArrayList<>();
        int p = 0;
        while (val > 1) {
            while (val % PRIMES[p] != 0) {
                p++;
            }
            int count = 0;
            while (val % PRIMES[p] == 0) {
                val /= PRIMES[p];
                count++;
            }
            res.add(count);
        }
        return res;
    }

    // 表示 1万以内的质数.
    private static final int[] PRIMES = new int[1229];

    // 插板法排列组合的缓存.
    private static long[][] C = new long[11000][20];

    private static final int MOD = 10_0000_0007;

    static {
        // 初始化 PRIMES
        int len = 0;
        loop:
        for (int num = 2; num < 1_0000; num++) {
            for (int i = 0; i < len; i++) {
                if (num % PRIMES[i] == 0) {
                    continue loop;
                }
            }
            PRIMES[len++] = num;
        }

        // 初始化 COUNTS (动态规划)
        for (int i = 0; i < 10500; i++) {
            C[i][0] = 1;
            if (i < 15) {
                C[i][i] = 1;
            }
            for (int j = 1; j < Math.min(i, 15); j++) {
                C[i][j] = (C[i - 1][j - 1] + C[i - 1][j]) % MOD;
            }
        }
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .create(new int[][]{{2, 6}, {5, 1}, {73, 660}})
            .expect(new int[]{4, 1, 50734910});

    @TestData
    public DataExpectation example2 = DataExpectation
            .create(new int[][]{{1, 1}, {2, 2}, {3, 3}, {4, 4}, {5, 5}})
            .expect(new int[]{1, 2, 3, 10, 5});

}
