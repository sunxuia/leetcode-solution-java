package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1312. Minimum Insertion Steps to Make a String Palindrome
 * https://leetcode.com/problems/minimum-insertion-steps-to-make-a-string-palindrome/
 *
 * Given a string s. In one step you can insert any character at any index of the string.
 *
 * Return the minimum number of steps to make s palindrome.
 *
 * A Palindrome String is one that reads the same backward as well as forward.
 *
 * Example 1:
 *
 * Input: s = "zzazz"
 * Output: 0
 * Explanation: The string "zzazz" is already palindrome we don't need any insertions.
 *
 * Example 2:
 *
 * Input: s = "mbadm"
 * Output: 2
 * Explanation: String can be "mbdadbm" or "mdbabdm".
 *
 * Example 3:
 *
 * Input: s = "leetcode"
 * Output: 5
 * Explanation: Inserting 5 characters the string becomes "leetcodocteel".
 *
 * Example 4:
 *
 * Input: s = "g"
 * Output: 0
 *
 * Example 5:
 *
 * Input: s = "no"
 * Output: 1
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * All characters of s are lower case English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1312_MinimumInsertionStepsToMakeAStringPalindrome {

    @Answer
    public int minInsertions(String s) {
        final char[] sc = s.toCharArray();
        final int n = sc.length;
        // dp[i][j] 表示 s[i:j] 中需要插入的数量
        int[][] dp = new int[n][n];
        for (int range = 1; range < n; range++) {
            for (int i = 0, j = range; j < n; i++, j++) {
                // s[i:j] 可以由 s[i,j-1] 左边插入s[j], 或者有 s[i+1, j] 在右边插入 s[i] 组成
                dp[i][j] = Math.min(dp[i][j - 1], dp[i + 1][j]) + 1;
                // 如果 s[i] == s[j] 则还可以由 s[i+1:j-1]扩展而成
                if (sc[i] == sc[j]) {
                    dp[i][j] = Math.min(dp[i][j], dp[i + 1][j - 1]);
                }
            }
        }
        return dp[0][n - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("zzazz").expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create("mbadm").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("leetcode").expect(5);

    @TestData
    public DataExpectation example4 = DataExpectation.create("g").expect(0);

    @TestData
    public DataExpectation example5 = DataExpectation.create("no").expect(1);

}
