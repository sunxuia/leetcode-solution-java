package q600;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/delete-operation-for-two-strings/
 *
 * Given two words word1 and word2, find the minimum number of steps required to make word1 and word2 the same,
 * where in each step you can delete one character in either string.
 *
 * Example 1:
 *
 * Input: "sea", "eat"
 * Output: 2
 * Explanation: You need one step to make "sea" to "ea" and another step to make "eat" to "ea".
 *
 * Note:
 *
 * 1. The length of given words won't exceed 500.
 * 2. Characters in given words can only be lower-case letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q583_DeleteOperationForTwoStrings {

    /**
     * 这题可以使用带缓存的dfs, Solution 中还给出了一种dp 的解法.
     */
    @Answer
    public int minDistance(String word1, String word2) {
        final int len1 = word1.length(), len2 = word2.length();
        char[] wc1 = word1.toCharArray();
        char[] wc2 = word2.toCharArray();
        // dp[i][j] 表示对于 word1.substring(0, i) 和 word2.substring(0, j)
        int[][] dp = new int[len1 + 1][len2 + 1];
        for (int i = 1; i <= len1; i++) {
            for (int j = 1; j <= len2; j++) {
                if (wc1[i - 1] == wc2[j - 1]) {
                    // 相等的字符 +1
                    dp[i][j] = 1 + dp[i - 1][j - 1];
                } else {
                    // 字符不相等则取word1 和word2 中上一个的最大值
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }
        return len1 + len2 - 2 * dp[len1][len2];
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("sea", "eat").expect(2);

}
