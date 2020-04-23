package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/ones-and-zeroes/
 *
 * In the computer world, use restricted resource you have to generate maximum benefit is what we always want to pursue.
 *
 * For now, suppose you are a dominator of m 0s and n 1s respectively. On the other hand, there is an array with
 * strings consisting of only 0s and 1s.
 *
 * Now your task is to find the maximum number of strings that you can form with given m 0s and n 1s. Each 0 and 1
 * can be used at most once.
 *
 * Note:
 *
 * The given numbers of 0s and 1s will both not exceed 100
 * The size of given string array won't exceed 600.
 *
 *
 *
 * Example 1:
 *
 * Input: Array = {"10", "0001", "111001", "1", "0"}, m = 5, n = 3
 * Output: 4
 *
 * Explanation: This are totally 4 strings can be formed by the using of 5 0s and 3 1s, which are “10,”0001”,”1”,”0”
 *
 *
 *
 * Example 2:
 *
 * Input: Array = {"10", "0", "1"}, m = 1, n = 1
 * Output: 2
 *
 * Explanation: You could form "10", but then you'd have nothing left. Better form "0" and "1".
 */
@RunWith(LeetCodeRunner.class)
public class Q474_OnesAndZeroes {

    /**
     * 简单的递归匹配会超时, 这题需要使用dp
     * 参考 https://www.cnblogs.com/grandyang/p/6188893.html
     * 以dp[i][j] 表示有i 个0 和j 个1 时最大的匹配数量, 则dp[m][n] 就是所求结果.
     * 这题需要在遍历字符串的时候针对每次遍历到的字符串进行一次dp 计算,
     * 以zero, one 表示当前字符串中0和1 的数量, 则得出状态转移方程:
     * dp[i][j] = max( dp[i][j], dp[i - zero][j - one] + 1)
     * dp[i-zero][j-one] 之后的 0或1 的个数不足, dp[i-zero][j-one] 之前的数量 <=  dp[i-zero][j-one]
     */
    @Answer
    public int findMaxForm(String[] strs, int m, int n) {
        int[][] dp = new int[m + 1][n + 1];
        for (String str : strs) {
            int zero = 0, one = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == '0') {
                    zero++;
                } else {
                    one++;
                }
            }
            // 从后向前遍历, 避免dp[i][j] 被新的值覆盖
            for (int i = m; i >= zero; i--) {
                for (int j = n; j >= one; j--) {
                    dp[i][j] = Math.max(dp[i][j], dp[i - zero][j - one] + 1);
                }
            }
        }
        return dp[m][n];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"10", "0001", "111001", "1", "0"}, 5, 3).expect(4);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"10", "0", "1"}, 1, 1).expect(2);
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(new String[]{"101", "110", "0", "0", "0", "0001", "1010", "01", "10110", "0011",
                    "0", "10", "11", "110", "1", "10", "0", "1", "00", "1", "0", "010", "1", "000", "0", "101", "0",
                    "11", "1", "01111", "110000", "1", "01"}, 47, 88).expect(33);

}
