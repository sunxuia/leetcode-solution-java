package q1650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1641. Count Sorted Vowel Strings
 * https://leetcode.com/problems/count-sorted-vowel-strings/
 *
 * Given an integer n, return the number of strings of length n that consist only of vowels (a, e, i, o, u) and are
 * lexicographically sorted.
 *
 * A string s is lexicographically sorted if for all valid i, s[i] is the same as or comes before s[i+1] in the
 * alphabet.
 *
 * Example 1:
 *
 * Input: n = 1
 * Output: 5
 * Explanation: The 5 sorted strings that consist of vowels only are ["a","e","i","o","u"].
 *
 * Example 2:
 *
 * Input: n = 2
 * Output: 15
 * Explanation: The 15 sorted strings that consist of vowels only are
 * ["aa","ae","ai","ao","au","ee","ei","eo","eu","ii","io","iu","oo","ou","uu"].
 * Note that "ea" is not a valid string since 'e' comes after 'a' in the alphabet.
 *
 * Example 3:
 *
 * Input: n = 33
 * Output: 66045
 *
 * Constraints:
 *
 * 1 <= n <= 50
 */
@RunWith(LeetCodeRunner.class)
public class Q1641_CountSortedVowelStrings {

    @Answer
    public int countVowelStrings(int n) {
        // dp[i][j] 表示长度为 i+1, 最后一个字符为 j 的组合数
        int[][] dp = new int[n][5];
        Arrays.fill(dp[0], 1);
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                for (int k = 0; k <= j; k++) {
                    dp[i][j] += dp[i - 1][k];
                }
            }
        }

        int res = 0;
        for (int i = 0; i < 5; i++) {
            res += dp[n - 1][i];
        }
        return res;
    }

    /**
     * dp 解法2
     */
    @Answer
    public int countVowelStrings2(int n) {
        // dp[i][j] 表示长度为 i+1, 最后一个字符 <= j 的组合数
        int[][] dp = new int[n][5];
        for (int i = 0; i < 5; i++) {
            dp[0][i] = i + 1;
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 5; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j > 0) {
                    dp[i][j] += dp[i][j - 1];
                }
            }
        }
        return dp[n - 1][4];
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create(1).expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create(2).expect(15);

    @TestData
    public DataExpectation example3 = DataExpectation.create(33).expect(66045);

}
