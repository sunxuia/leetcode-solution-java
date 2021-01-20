package q1550;

import java.util.Arrays;
import org.junit.runner.RunWith;
import q450.Q443_StringCompression;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1531. String Compression II
 * https://leetcode.com/problems/string-compression-ii/
 *
 * Run-length encoding (https://en.wikipedia.org/wiki/Run-length_encoding) is a string compression method that works by
 * replacing consecutive identical characters (repeated 2 or more times) with the concatenation of the character and the
 * number marking the count of the characters (length of the run). For example, to compress the string "aabccc" we
 * replace "aa" by "a2" and replace "ccc" by "c3". Thus the compressed string becomes "a2bc3".
 *
 * Notice that in this problem, we are not adding '1' after single characters.
 *
 * Given a string s and an integer k. You need to delete at most k characters from s such that the run-length encoded
 * version of s has minimum length.
 *
 * Find the minimum length of the run-length encoded version of s after deleting at most k characters.
 *
 * Example 1:
 *
 * Input: s = "aaabcccd", k = 2
 * Output: 4
 * Explanation: Compressing s without deleting anything will give us "a3bc3d" of length 6. Deleting any of the
 * characters 'a' or 'c' would at most decrease the length of the compressed string to 5, for instance delete 2 'a'
 * then
 * we will have s = "abcccd" which compressed is abc3d. Therefore, the optimal way is to delete 'b' and 'd', then the
 * compressed version of s will be "a3c3" of length 4.
 *
 * Example 2:
 *
 * Input: s = "aabbaa", k = 2
 * Output: 2
 * Explanation: If we delete both 'b' characters, the resulting compressed string would be "a4" of length 2.
 *
 * Example 3:
 *
 * Input: s = "aaaaaaaaaaa", k = 0
 * Output: 3
 * Explanation: Since k is zero, we cannot delete anything. The compressed string is "a11" of length 3.
 *
 * Constraints:
 *
 * 1 <= s.length <= 100
 * 0 <= k <= s.length
 * s contains only lowercase English letters.
 *
 * 上一题 {@link Q443_StringCompression}
 */
@RunWith(LeetCodeRunner.class)
public class Q1531_StringCompressionII {

    /**
     * 可以使用动态规划来做, 但是转移方程比较复杂.
     * 参考文档
     * https://leetcode-cn.com/problems/string-compression-ii/solution/ya-suo-zi-fu-chuan-ii-by-leetcode-solution/
     */
    @Answer
    public int getLengthOfOptimalCompression(String s, int k) {
        final int n = s.length();
        // dp[i][j] 表示 s[0:i-1] 删除了j 个字符后的最短压缩长度.
        int[][] dp = new int[n + 1][k + 1];
        for (int i = 0; i <= n; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= k && j <= i; j++) {
                if (j > 0) {
                    dp[i][j] = dp[i - 1][j - 1];
                }
                int same = 0, diff = 0;
                for (int l = i; l >= 1 && diff <= j; l--) {
                    if (s.charAt(l - 1) == s.charAt(i - 1)) {
                        same++;
                        dp[i][j] = Math.min(dp[i][j], dp[l - 1][j - diff] + calc(same));
                    } else {
                        diff++;
                    }
                }
            }
        }

        return dp[n][k];
    }

    public int calc(int x) {
        if (x == 1) {
            return 1;
        }
        if (x < 10) {
            return 2;
        }
        if (x < 100) {
            return 3;
        }
        return 4;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("aaabcccd", 2).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("aabbaa", 2).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("aaaaaaaaaaa", 0).expect(3);

}
