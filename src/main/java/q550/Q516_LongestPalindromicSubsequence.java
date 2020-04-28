package q550;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/longest-palindromic-subsequence/
 *
 * Given a string s, find the longest palindromic subsequence's length in s. You may assume that the maximum length
 * of s is 1000.
 *
 * Example 1:
 * Input:
 *
 * "bbbab"
 *
 * Output:
 *
 * 4
 *
 * One possible longest palindromic subsequence is "bbbb".
 *
 * Example 2:
 * Input:
 *
 * "cbbd"
 *
 * Output:
 *
 * 2
 *
 * One possible longest palindromic subsequence is "bb".
 */
@RunWith(LeetCodeRunner.class)
public class Q516_LongestPalindromicSubsequence {

    // 带缓存的dfs, 这种方式比较慢
    @Answer
    public int longestPalindromeSubseq(String s) {
        final int n = s.length();
        if (n < 2) {
            return n;
        }
        char[] sc = s.toCharArray();
        int[][] buffer = new int[n][n];
        for (int i = 0; i < n; i++) {
            buffer[i][i] = 1;
        }
        return dfs(sc, 0, n - 1, buffer);
    }

    private int dfs(char[] sc, int start, int end, int[][] buffer) {
        if (start >= end || buffer[start][end] > 0) {
            return buffer[start][end];
        }
        int res = 0;
        int left = start, right = end;
        while (left < right && sc[left] == sc[right]) {
            res += 2;
            left++;
            right--;
        }
        if (res > 0) {
            res += dfs(sc, left, right, buffer);
        }
        res = Math.max(res, Math.max(
                dfs(sc, start + 1, end, buffer),
                dfs(sc, start, end - 1, buffer)));
        buffer[start][end] = res;
        return res;
    }

    // 上面的方法是从外到里, 这里的方法是从里到外查找, 相对于上面的解法时间少一点, 但还是慢
    @Answer
    public int longestPalindromeSubseq2(String s) {
        char[] sc = s.toCharArray();
        int n = sc.length, res = 0;
        int[][] buffer = new int[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(buffer[i], -1);
        }
        for (int i = 0; i < n; i++) {
            res = Math.max(res, Math.max(
                    1 + dfs2(sc, i - 1, i + 1, buffer),
                    dfs2(sc, i, i + 1, buffer)));
        }
        return res;
    }

    private int dfs2(char[] sc, int left, int right, int[][] buffer) {
        if (left < 0 || right >= sc.length) {
            return 0;
        }
        if (buffer[left][right] > -1) {
            return buffer[left][right];
        }
        int res = 0;
        if (sc[left] == sc[right]) {
            res = 2 + dfs2(sc, left - 1, right + 1, buffer);
        }
        res = Math.max(res, Math.max(
                dfs2(sc, left - 1, right, buffer),
                dfs2(sc, left, right + 1, buffer)));
        buffer[left][right] = res;
        return res;
    }

    // LeetCode 上比较快的解法. DP 的做法.
    @Answer
    public int longestPalindromeSubseq3(String s) {
        int n = s.length();
        if (n < 2) {
            return n;
        }
        char[] sc = s.toCharArray();
        // dp[i][j] 表示 s[i, j] 区间中的最大回文子字符串长度
        int[][] dp = new int[n][n];
        for (int i = n - 1; i >= 0; i--) {
            dp[i][i] = 1;
            for (int j = i + 1; j < n; j++) {
                if (sc[i] == sc[j]) {
                    dp[i][j] = dp[i + 1][j - 1] + 2;
                } else {
                    dp[i][j] = Math.max(dp[i + 1][j], dp[i][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("bbbab").expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.create("cbbd").expect(2);

    @TestData
    public DataExpectation border0 = DataExpectation.create("").expect(0);

    @TestData
    public DataExpectation border1 = DataExpectation.create("a").expect(1);

    @TestData
    public DataExpectation overTime1 = DataExpectation.create(
            "euazbipzncptldueeuechubrcourfpftcebikrxhybkymimgvldiwqvkszfycvqyvtiwfckex"
                    + "mowcxztkfyzqovbtmzpxojfofbvwnncajvrvdbvjhcrameamcfmcoxryjukhpljwszkn"
                    + "hiypvyskmsujkuggpztltpgoczafmfelahqwjbhxtjmebnymdyxoeodqmvkxit"
                    + "txjnlltmoobsgzdfhismogqfpfhvqnxeuosjqqalvwhsidgiavcatjjgeztrjuoixxxoznk"
                    + "lcxolgpuktirmduxdywwlbikaqkqajzbsjvdgjcnbtfksqhquiwnwflkldgdrqrnwmshd"
                    + "pykicozfowmumzeuznolmgjlltypyufpzjpuvucmesnnrwppheizkapovoloneaxpfinao"
                    + "ntwtdqsdvzmqlgkdxlbeguackbdkftzbnynmcejtwudocemcfnuzbttcoew")
            .expect(159);

    @TestData
    public DataExpectation overTime2() {
        StringBuilder sb = new StringBuilder(1000);
        for (int i = 0; i < 493; i++) {
            sb.append('f');
        }
        for (int i = 0; i < 494; i++) {
            sb.append('g');
        }
        return DataExpectation.create(sb.toString()).expect(494);
    }

}
