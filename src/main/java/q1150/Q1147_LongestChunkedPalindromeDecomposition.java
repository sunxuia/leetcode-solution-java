package q1150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1147. Longest Chunked Palindrome Decomposition
 * https://leetcode.com/problems/longest-chunked-palindrome-decomposition/
 *
 * Return the largest possible k such that there exists a_1, a_2, ..., a_k such that:
 *
 * Each a_i is a non-empty string;
 * Their concatenation a_1 + a_2 + ... + a_k is equal to text;
 * For all 1 <= i <= k,  a_i = a_{k+1 - i}.
 *
 * Example 1:
 *
 * Input: text = "ghiabcdefhelloadamhelloabcdefghi"
 * Output: 7
 * Explanation: We can split the string on "(ghi)(abcdef)(hello)(adam)(hello)(abcdef)(ghi)".
 *
 * Example 2:
 *
 * Input: text = "merchant"
 * Output: 1
 * Explanation: We can split the string on "(merchant)".
 *
 * Example 3:
 *
 * Input: text = "antaprezatepzapreanta"
 * Output: 11
 * Explanation: We can split the string on "(a)(nt)(a)(pre)(za)(tpe)(za)(pre)(a)(nt)(a)".
 *
 * Example 4:
 *
 * Input: text = "aaa"
 * Output: 3
 * Explanation: We can split the string on "(a)(a)(a)".
 *
 * Constraints:
 *
 * text consists only of lowercase English characters.
 * 1 <= text.length <= 1000
 */
@RunWith(LeetCodeRunner.class)
public class Q1147_LongestChunkedPalindromeDecomposition {

    /**
     * 这题参考网上思路, 可以使用左右指针来做.
     */
    @Answer
    public int longestDecomposition(String text) {
        return dfs(text.toCharArray(), 0, text.length() - 1);
    }

    private int dfs(char[] sc, int left, int right) {
        if (left > right) {
            return 0;
        }
        for (int i = left; i < right; i++, right--) {
            if (isPalindrome(sc, left, right, i - left + 1)) {
                // 如果两端存在相等的回文段, 则可以直接返回.
                // 如果还存在更长的相等回文段, 则说明存在可以重复的回文段(比如 "abab ... abab" 这样),
                // 这种情况下应当直接分为 "(ab)(ab) ... (ab)(ab)", 而不是继续查看 "(abab) ... (abab)" 这种可能性.
                return 2 + dfs(sc, i + 1, right - 1);
            }
        }
        return 1;
    }

    /**
     * 网上是比较子字符串, 方法中这种直接比较char[] 的方式要更快.
     */
    private boolean isPalindrome(char[] sc, int left, int right, int len) {
        for (int i = 0; i < len; i++) {
            if (sc[left + i] != sc[right + i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * dp 的解法, 由上面的解答修改而来, 但是这种解法要慢好多.
     */
    @Answer
    public int longestDecomposition2(String text) {
        final char[] cs = text.toCharArray();
        final int n = cs.length;
        int[][] dp = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                dp[i][j] = 1;
            }
        }
        for (int i = n - 1; i >= 0; i--) {
            for (int j = i + 1; j < n; j++) {
                for (int l = i, r = j; l < r; l++, r--) {
                    if (isPalindrome(cs, i, r, l - i + 1)) {
                        dp[i][j] = Math.max(dp[i][j], 2 + dp[l + 1][r - 1]);
                        break;
                    }
                }
            }
        }
        return dp[0][n - 1];
    }

    /**
     * LeetCode 上比较简洁的方法.
     * 类似dfs, 但是直接使用子字符串equals 的方式来判断是否是回文段的.
     */
    @Answer
    public int longestDecomposition3(String text) {
        final int n = text.length();
        if (n == 0) {
            return 0;
        }
        for (int i = 0; i < n / 2; i++) {
            if (text.substring(0, i + 1).equals(text.substring(n - 1 - i))) {
                return 2 + longestDecomposition3(text.substring(i + 1, n - 1 - i));
            }
        }
        return 1;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("ghiabcdefhelloadamhelloabcdefghi").expect(7);

    @TestData
    public DataExpectation example2 = DataExpectation.create("merchant").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("antaprezatepzapreanta").expect(11);

    @TestData
    public DataExpectation example4 = DataExpectation.create("aaa").expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("ghiabcdefhelloadamhelloabcdefghi").expect(7);

}
