package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1416. Restore The Array
 * https://leetcode.com/problems/restore-the-array/
 *
 * A program was supposed to print an array of integers. The program forgot to print whitespaces and the array is
 * printed as a string of digits and all we know is that all integers in the array were in the range [1, k] and there
 * are no leading zeros in the array.
 *
 * Given the string s and the integer k. There can be multiple ways to restore the array.
 *
 * Return the number of possible array that can be printed as a string s using the mentioned program.
 *
 * The number of ways could be very large so return it modulo 10^9 + 7
 *
 * Example 1:
 *
 * Input: s = "1000", k = 10000
 * Output: 1
 * Explanation: The only possible array is [1000]
 *
 * Example 2:
 *
 * Input: s = "1000", k = 10
 * Output: 0
 * Explanation: There cannot be an array that was printed this way and has all integer >= 1 and <= 10.
 *
 * Example 3:
 *
 * Input: s = "1317", k = 2000
 * Output: 8
 * Explanation: Possible arrays are [1317],[131,7],[13,17],[1,317],[13,1,7],[1,31,7],[1,3,17],[1,3,1,7]
 *
 * Example 4:
 *
 * Input: s = "2020", k = 30
 * Output: 1
 * Explanation: The only possible array is [20,20]. [2020] is invalid because 2020 > 30. [2,020] is ivalid because 020
 * contains leading zeros.
 *
 * Example 5:
 *
 * Input: s = "1234567890", k = 90
 * Output: 34
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5.
 * s consists of only digits and doesn't contain leading zeros.
 * 1 <= k <= 10^9.
 */
@RunWith(LeetCodeRunner.class)
public class Q1416_RestoreTheArray {

    /**
     * 带缓存的dfs
     */
    @Answer
    public int numberOfArrays(String s, int k) {
        final int n = s.length();
        char[] cs = s.toCharArray();
        int[] cache = new int[n + 1];
        for (int i = 0; i < n; i++) {
            // 题目中限定了没有0 和前导0 的情况
            cache[i] = cs[i] == '0' ? 0 : -1;
        }
        cache[n] = 1;

        return dfs(cache, cs, 0, k);
    }

    private int dfs(int[] cache, char[] cs, int idx, int k) {
        if (cache[idx] > -1) {
            return cache[idx];
        }
        long res = 0, num = 0;
        for (int i = idx; i < cs.length; i++) {
            num = num * 10 + cs[i] - '0';
            if (num > k) {
                // 当前数字超过了限制
                break;
            }
            res += dfs(cache, cs, i + 1, k);
        }
        return cache[idx] = (int) (res % 10_0000_0007);
    }

    /**
     * 上面解法的dp 改写.
     */
    @Answer
    public int numberOfArrays2(String s, int k) {
        final int n = s.length();
        final int mod = 10_0000_0007;
        char[] cs = s.toCharArray();
        int[] dp = new int[n];
        for (int i = n - 1; i >= 0; i--) {
            if (cs[i] != '0') {
                long num = 0;
                for (int j = i; j < n && num <= k; j++) {
                    dp[i] = (dp[i] + dp[j]) % mod;
                    num = num * 10 + cs[j] - '0';
                }
                if (num <= k) {
                    dp[i] = (dp[i] + 1) % mod;
                }
            }
        }
        return dp[0];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("1000", 10000).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("1000", 10).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("1317", 2000).expect(8);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("2020", 30).expect(1);

    @TestData
    public DataExpectation example5 = DataExpectation.createWith("1234567890", 90).expect(34);

    @TestData
    public DataExpectation overflow = DataExpectation
            .createWith("600342244431311113256628376226052681059918526204", 703)
            .expect(411743991);

}
