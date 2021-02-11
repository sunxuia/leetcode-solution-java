package q1300;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q150.Q132_PalindromePartitioningII;
import q1750.Q1745_PalindromePartitioningIV;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1278. Palindrome Partitioning III
 * https://leetcode.com/problems/palindrome-partitioning-iii/
 *
 * You are given a string s containing lowercase letters and an integer k. You need to :
 *
 * First, change some characters of s to other lowercase English letters.
 * Then divide s into k non-empty disjoint substrings such that each substring is palindrome.
 *
 * Return the minimal number of characters that you need to change to divide the string.
 *
 * Example 1:
 *
 * Input: s = "abc", k = 2
 * Output: 1
 * Explanation: You can split the string into "ab" and "c", and change 1 character in "ab" to make it palindrome.
 *
 * Example 2:
 *
 * Input: s = "aabbc", k = 3
 * Output: 0
 * Explanation: You can split the string into "aa", "bb" and "c", all of them are palindrome.
 *
 * Example 3:
 *
 * Input: s = "leetcode", k = 8
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= k <= s.length <= 100.
 * s only contains lowercase English letters.
 *
 * 上一题 {@link Q132_PalindromePartitioningII}
 * 下一题 {@link Q1745_PalindromePartitioningIV}
 */
@RunWith(LeetCodeRunner.class)
public class Q1278_PalindromePartitioningIII {

    /**
     * 带缓存的dfs.
     */
    @Answer
    public int palindromePartition(String s, int k) {
        final int n = s.length();
        int[][] cache = new int[s.length()][k + 1];
        for (int i = 0; i < n; i++) {
            Arrays.fill(cache[i], -1);
        }
        return dfs(s.toCharArray(), 0, k, cache);
    }

    private int dfs(char[] sc, int i, int k, int[][] cache) {
        if (sc.length - i == k) {
            return 0;
        }
        if (cache[i][k] != -1) {
            return cache[i][k];
        }
        if (k == 1) {
            return countChange(sc, i, sc.length - 1);
        }
        int res = Integer.MAX_VALUE;
        for (int j = i; j <= sc.length - k; j++) {
            res = Math.min(res, countChange(sc, i, j) + dfs(sc, j + 1, k - 1, cache));
        }
        cache[i][k] = res;
        return res;
    }

    private int countChange(char[] sc, int left, int right) {
        int res = 0;
        while (left < right) {
            res += sc[left] == sc[right] ? 0 : 1;
            left++;
            right--;
        }
        return res;
    }

    /**
     * 上面解法的dp 变种.
     */
    @Answer
    public int palindromePartition2(String s, int k) {
        final char[] sc = s.toCharArray();
        final int n = sc.length;
        int[][] diffs = getDiffs(sc);
        // dp[i][j] 表示 s[0:j] 分为 i+1 份的最小结果.
        int[][] dp = new int[k][n];
        System.arraycopy(diffs[0], 0, dp[0], 0, n);
        for (int i = 1; i < k; i++) {
            // [start, end] 作为i+1 份独立回文的最后一个.
            for (int end = i; end < n; end++) {
                dp[i][end] = Integer.MAX_VALUE;
                for (int start = end; start >= i; start--) {
                    int change = dp[i - 1][start - 1] + diffs[start][end];
                    dp[i][end] = Math.min(dp[i][end], change);
                }
            }
        }
        return dp[k - 1][n - 1];
    }

    /**
     * 参考 LeetCode 中的解法, 求回文中需要改变的字符数量.
     * sc[start][end] 表示的就是 sc[start, end] 作为一个回文需要改变的字符数量.
     */
    private int[][] getDiffs(char[] sc) {
        final int n = sc.length;
        final int[][] offsets = new int[][]{{-1, 1}, {0, 1}};
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int[] offset : offsets) {
                int left = i + offset[0], right = i + offset[1];
                while (0 <= left && right < n) {
                    if (sc[left] == sc[right]) {
                        res[left][right] = res[left + 1][right - 1];
                    } else {
                        res[left][right] = res[left + 1][right - 1] + 1;
                    }
                    left--;
                    right++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abc", 2).expect(1);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("aabbc", 3).expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("leetcode", 8).expect(0);

    @TestData
    public DataExpectation overTime = DataExpectation.createWith(
            "fyhowoxzyrincxivwarjuwxrwealesxsimsepjdqsstfggjnjhilvrwwytbgsqbpnwjaojfnmiqiqnyzijfmvekgakefjaxryyml", 32)
            .expect(20);

}
