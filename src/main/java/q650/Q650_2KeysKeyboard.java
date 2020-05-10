package q650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/2-keys-keyboard/
 *
 * Initially on a notepad only one character 'A' is present. You can perform two operations on this notepad for each
 * step:
 *
 * Copy All: You can copy all the characters present on the notepad (partial copy is not allowed).
 * Paste: You can paste the characters which are copied last time.
 *
 *
 *
 * Given a number n. You have to get exactly n 'A' on the notepad by performing the minimum number of steps permitted
 * . Output the minimum number of steps to get n 'A'.
 *
 * Example 1:
 *
 * Input: 3
 * Output: 3
 * Explanation:
 * Intitally, we have one character 'A'.
 * In step 1, we use Copy All operation.
 * In step 2, we use Paste operation to get 'AA'.
 * In step 3, we use Paste operation to get 'AAA'.
 *
 *
 *
 * Note:
 *
 * 1. The n will be in the range [1, 1000].
 */
@RunWith(LeetCodeRunner.class)
public class Q650_2KeysKeyboard {

    // 这种dfs 方式比较慢
    @Answer
    public int minSteps(int n) {
        if (n == 1) {
            return 0;
        }
        return 1 + dfs(n, 1, 1);
    }

    private int dfs(int n, int notepad, int paste) {
        if (notepad > n) {
            return 1000;
        }
        if (notepad == n) {
            return 0;
        }
        return Math.min(1 + dfs(n, notepad + paste, paste),
                2 + dfs(n, notepad + notepad, notepad));
    }

    /**
     * dp 的方式, 这种方式也很慢.
     * dp[i][j] 表示notepad 有i 个A, 剪切板有j 个A 时最小的操作数. 可以得出状态转移方程:
     * dp[i][j] = min(1 + dp[i-j][j], 2 + min(dp[i/2][1], dp[i/2][2], ..., dp[i/2][n]))
     * (需要考虑到边界条件)
     */
    @Answer
    public int minSteps2(int n) {
        int[][] dp = new int[n + 1][n + 1];
        int[] min = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            min[i] = 1000;
            Arrays.fill(dp[i], 1000);
        }
        dp[1][0] = 0;
        min[1] = 0;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                dp[i][j] = 1 + dp[i - j][j];
            }
            if (i % 2 == 0 && min[i / 2] + 2 <= n) {
                dp[i][i / 2] = Math.min(dp[i][i / 2], 2 + min[i / 2]);
            }
            for (int j = 1; j <= i; j++) {
                min[i] = Math.min(min[i], dp[i][j]);
            }
        }
        return min[n];
    }

    // LeetCode 上最快的做法, 详情见 Solution
    @Answer
    public int minSteps3(int n) {
        int res = 0, d = 2;
        while (n > 1) {
            while (n % d == 0) {
                res += d;
                n /= d;
            }
            d++;
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.create(3).expect(3);

    @TestData
    public DataExpectation arg1 = DataExpectation.create(1).expect(0);

    @TestData
    public DataExpectation arg2 = DataExpectation.create(2).expect(2);

    @TestData
    public DataExpectation arg4 = DataExpectation.create(4).expect(4);

}
