package q2000;

import java.util.ArrayDeque;
import java.util.Queue;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1977. Number of Ways to Separate Numbers
 * https://leetcode.com/problems/number-of-ways-to-separate-numbers/
 *
 * You wrote down many positive integers in a string called num. However, you realized that you forgot to add commas to
 * seperate the different numbers. You remember that the list of integers was non-decreasing and that no integer had
 * leading zeros.
 *
 * Return the number of possible lists of integers that you could have written down to get the string num. Since the
 * answer may be large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: num = "327"
 * Output: 2
 * Explanation: You could have written down the numbers:
 * 3, 27
 * 327
 *
 * Example 2:
 *
 * Input: num = "094"
 * Output: 0
 * Explanation: No numbers can have leading zeros and all numbers must be positive.
 *
 * Example 3:
 *
 * Input: num = "0"
 * Output: 0
 * Explanation: No numbers can have leading zeros and all numbers must be positive.
 *
 * Constraints:
 *
 * 1 <= num.length <= 3500
 * num consists of digits '0' through '9'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1977_NumberOfWaysToSeparateNumbers {

//    @DebugWith("normal2")
    @Answer
    public int numberOfCombinations(String num) {
        final int mod = 10_0000_0007;
        final int n = num.length();
        final char[] cs = num.toCharArray();

        // (less than or equal to)
        // ltoet[len][i] = 是否 num[i,i+len) <= num[i+len, i+len+len)
        boolean[][] ltoet = new boolean[n + 1][n];
        Queue<Integer> queue = new ArrayDeque<>();
        for (int len = 1; len <= n / 2; len++) {
            for (int i = 0; i < n; i++) {
                int next = i + len;
                if (next < n && cs[i] != cs[next]) {
                    queue.offer(i);
                }
                int prev = i - len + 1;
                if (0 <= prev) {
                    if (queue.isEmpty()) {
                        ltoet[len][prev] = true;
                    } else {
                        int peek = queue.peek();
                        ltoet[len][prev] = cs[peek] <= cs[peek + len];
                        if (prev == peek) {
                            queue.poll();
                        }
                    }
                }
            }
        }

        // dp[i][len] 表示子字符串 num[i:n) 可能的组合数, 且第一个数字的长度 >= len
        int[][] dp = new int[n][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            if (cs[i] == '0') {
                continue;
            }
            dp[i][n - i] = 1;
            for (int len = n - i - 1; len > 0; len--) {
                dp[i][len] = dp[i][len + 1];
                final int j = i + len;
                if (j + len <= n) {
                    if (ltoet[len][i]) {
                        // num[i,i+len) <= num[j,j+len)
                        dp[i][len] = (dp[i][len] + dp[j][len]) % mod;
                    } else {
                        // num[i,i+len) <= num[j,j+len+1)
                        dp[i][len] = (dp[i][len] + dp[j][len + 1]) % mod;
                    }
                }
            }
        }

        return dp[0][1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("327").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("094").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.create("0").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("111").expect(3);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("299737").expect(7);

    @TestData
    public DataExpectation overTime() {
        StringBuilder sb = new StringBuilder(3500);
        for (int i = 0; i < 3500; i++) {
            sb.append('1');
        }
        return DataExpectation.create(sb.toString()).expect(755568658);
    }

}
