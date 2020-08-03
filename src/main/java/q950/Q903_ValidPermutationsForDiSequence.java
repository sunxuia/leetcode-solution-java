package q950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 903. Valid Permutations for DI Sequence
 * https://leetcode.com/problems/valid-permutations-for-di-sequence/
 *
 * We are given S, a length n string of characters from the set {'D', 'I'}. (These letters stand for "decreasing" and
 * "increasing".)
 *
 * A valid permutation is a permutation P[0], P[1], ..., P[n] of integers {0, 1, ..., n}, such that for all i:
 *
 * If S[i] == 'D', then P[i] > P[i+1], and;
 * If S[i] == 'I', then P[i] < P[i+1].
 *
 * How many valid permutations are there?  Since the answer may be large, return your answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: "DID"
 * Output: 5
 * Explanation:
 * The 5 valid permutations of (0, 1, 2, 3) are:
 * (1, 0, 3, 2)
 * (2, 0, 3, 1)
 * (2, 1, 3, 0)
 * (3, 0, 2, 1)
 * (3, 1, 2, 0)
 *
 * Note:
 *
 * 1 <= S.length <= 200
 * S consists only of characters from the set {'D', 'I'}.
 */
@RunWith(LeetCodeRunner.class)
public class Q903_ValidPermutationsForDiSequence {


    /**
     * 参考文档 https://www.cnblogs.com/grandyang/p/11094525.html
     */
    @Answer
    public int numPermsDISequence(String S) {
        final int mod = 10_0000_0007;
        final int n = S.length();
        char[] sc = S.toCharArray();

        // dp[i][j] 表示由范围 [0, i] 内的数字组成且最后一个数字为j 的不同序列的个数
        int[][] dp = new int[n + 1][n + 1];
        dp[0][0] = 1;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= i; j++) {
                if (sc[i - 1] == 'D') {
                    for (int k = j; k <= i - 1; ++k) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][k]) % mod;
                    }
                } else {
                    for (int k = 0; k <= j - 1; ++k) {
                        dp[i][j] = (dp[i][j] + dp[i - 1][k]) % mod;
                    }
                }
            }
        }

        int res = 0;
        for (int i = 0; i <= n; i++) {
            res = (res + dp[n][i]) % mod;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create("DID").expect(5);

}
