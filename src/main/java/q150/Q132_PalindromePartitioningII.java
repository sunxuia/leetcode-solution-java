package q150;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.DataExpectation;
import util.runner.LeetCodeRunner;
import util.runner.TestData;

/**
 * https://leetcode.com/problems/palindrome-partitioning-ii/
 *
 * Given a string s, partition s such that every substring of the partition is a palindrome.
 *
 * Return the minimum cuts needed for a palindrome partitioning of s.
 *
 * Example:
 *
 * Input: "aab"
 * Output: 1
 * Explanation: The palindrome partitioning ["aa","b"] could be produced using 1 cut.
 */
@RunWith(LeetCodeRunner.class)
public class Q132_PalindromePartitioningII {

    /**
     * 使用dp[i + 1] 来记录s.substring(0, i) 的最小切割长度. (dp[0] = -1 是哨兵)
     * 如果 s.substring(j, i) 是回文的话, 那么最小切割长度就是 dp[(j - 1) + 1] + 1.
     *
     * 这种dp 的方式有些慢.
     */
    @Answer
    public int minCut(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        final int len = s.length();
        int[] dp = new int[len + 1];
        dp[0] = -1;
        for (int i = 0; i < len; i++) {
            int minIndex = i;
            for (int j = 0; j < i; j++) {
                if (dp[j] < dp[minIndex] && isPalindrome(s, j, i)) {
                    minIndex = j;
                }
            }
            dp[i + 1] = dp[minIndex] + 1;
        }
        return dp[len];
    }

    private boolean isPalindrome(String s, int start, int end) {
        while (start < end) {
            if (s.charAt(start++) != s.charAt(end--)) {
                return false;
            }
        }
        return true;
    }

    /**
     * LeetCode 上的一种比较快的方式.
     */
    @Answer
    public int leetCode(String s) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        final int len = s.length();

        // 前后加入2 个哨兵, 避免和字符串中的其它字符相等.
        char[] sc = new char[len + 2];
        sc[0] = '$';
        for (int i = 0; i < len; i++) {
            sc[i + 1] = s.charAt(i);
        }
        sc[len + 1] = '*';

        // 初始化最大的分隔次数, dp[0] 是哨兵(-1)
        int[] dp = new int[len + 1];
        for (int i = 0; i <= len; i++) {
            dp[i] = i - 1;
        }

        // 缩减回文次数
        for (int i = 1; i <= len; i++) {
            // "aba" 类型回文
            update(sc, i, i, dp);
            // "aa" 类型回文
            update(sc, i, i + 1, dp);
        }
        return dp[len];
    }

    private void update(char[] sc, int left, int right, int[] dp) {
        while (sc[left] == sc[right]) {
            dp[right] = Math.min(dp[right], dp[left - 1] + 1);
            left--;
            right++;
        }
    }

    @TestData
    public DataExpectation example = DataExpectation.create("aab").expect(1);

    @TestData
    public DataExpectation border = DataExpectation.create("").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("a").expect(0);

}
