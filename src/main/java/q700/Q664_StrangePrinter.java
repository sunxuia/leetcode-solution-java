package q700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/strange-printer/
 *
 * There is a strange printer with the following two special requirements:
 *
 * 1. The printer can only print a sequence of the same character each time.
 * 2. At each turn, the printer can print new characters starting from and ending at any places, and will cover the
 * original existing characters.
 *
 * Given a string consists of lower English letters only, your job is to count the minimum number of turns the
 * printer needed in order to print it.
 *
 * Example 1:
 *
 * Input: "aaabbb"
 * Output: 2
 * Explanation: Print "aaa" first and then print "bbb".
 *
 * Example 2:
 *
 * Input: "aba"
 * Output: 2
 * Explanation: Print "aaa" first and then print "b" from the second place of the string, which will cover the
 * existing character 'a'.
 *
 * Hint: Length of the given string will not exceed 100.
 */
@RunWith(LeetCodeRunner.class)
public class Q664_StrangePrinter {

    /**
     * 这题贪婪算法无法得到解答, 还是要使用dp.
     * 参考 Solution 和 https://www.cnblogs.com/grandyang/p/8319913.html
     * 推导过程:
     * 1. 对于只有相同字符的字符串, 结果是 1
     * 2. 对于相同字符串都连在一起, 不存在间隔出现的情况, 结果是不同字符的个数
     * 3. 对于 aba, 结果是 2
     * 4.1. 对于 abac, 可以分为 aba 和c, 结果是 2 + 1 = 3
     * 4.2. 对于 abab, 处理方法也是 a + bab, 结果是 3
     * 4.3. 对于 abba, 则可以分为 a + bba, 且a 和 bba 的末尾相同, 所以结果是 1 + 2 - 1 = 2
     *
     * 对于[i, j] 范围的字符, 我们从i+1 位置上的字符开始遍历到j, 如果和i 位置上的字符相等, 我们就以此位置为界,
     * 将[i+1, j] 范围内的字符拆为两个部分, 将二者的dp 值加起来, 和原dp 值相比, 取较小的那个.
     *
     * 由此可以设定dp[i][j] 表示字符范围[i, j] 内的最优解, 可以得到状态转移方程(s 表示字符串s):
     * dp[i][j] = min(dp[i][j], dp[i + 1][k - 1] + dp[k][j])       (s[k] == s[i] 且 i + 1 <= k <= j)
     */
    @Answer
    public int strangePrinter(String s) {
        final int n = s.length();
        if (n == 0) {
            return 0;
        }
        char[] sc = s.toCharArray();
        int[][] dp = new int[n][n];
        // 填写 [i, j]内的结果
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i; j < n; j++) {
                // 初始值, 默认 [i, j] 的值是 打印i 1 次 + [i+1, j] 的值
                dp[i][j] = (i == j) ? 1 : (1 + dp[i + 1][j]);
                for (int k = i + 1; k <= j; k++) {
                    // 如果 i 和k 相同, 则[i, k] 对应 4.3 中的条件,
                    // 值 [i+1, k-1] 是插入i, k 连续字符中间的, 所以可以将[i, j] 拆分为 [i+1, k-1] 和 [k, j].
                    if (sc[i] == sc[k]) {
                        dp[i][j] = Math.min(dp[i][j], dp[i + 1][k - 1] + dp[k][j]);
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    // 带cache 的dfs 的解法, 这个是从前往后做的, 方便理解一点
    @Answer
    public int strangePrinter2(String s) {
        final int n = s.length();
        char[] sc = s.toCharArray();
        return recursive(sc, 0, n - 1, new int[n][n]);
    }

    // 输出 [i, j] 范围内的结果.
    private int recursive(char[] sc, int i, int j, int[][] memo) {
        if (i > j) {
            return 0;
        }
        if (memo[i][j] == 0) {
            // 单独打印该字符
            int res = recursive(sc, i + 1, j, memo) + 1;
            // 如果 i 和k 相同, 则[i, k] 对应 4.3 中的条件,
            // 值 [i+1, k-1] 是插入i, k 连续字符中间的, 所以可以将[i, j] 拆分为 [i+1, k-1] 和 [k, j].
            for (int k = i + 1; k <= j; ++k) {
                if (sc[k] == sc[i]) {
                    res = Math.min(res, recursive(sc, i, k - 1, memo) + recursive(sc, k + 1, j, memo));
                }
            }
            memo[i][j] = res;
        }
        return memo[i][j];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aaabbb").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aba").expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("tbgtgb").expect(4);

}
