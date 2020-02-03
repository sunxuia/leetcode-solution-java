package q100;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/decode-ways/
 *
 * A message containing letters from A-Z is being encoded to numbers using the following mapping:
 *
 * 'A' -> 1
 * 'B' -> 2
 * ...
 * 'Z' -> 26
 *
 * Given a non-empty string containing only digits, determine the total number of ways to decode it.
 *
 * Example 1:
 *
 * Input: "12"
 * Output: 2
 * Explanation: It could be decoded as "AB" (1 2) or "L" (12).
 *
 * Example 2:
 *
 * Input: "226"
 * Output: 3
 * Explanation: It could be decoded as "BZ" (2 26), "VF" (22 6), or "BBF" (2 2 6).
 */
@RunWith(LeetCodeRunner.class)
public class Q091_DecodeWays {

    // dp 的方式
    @Answer
    public int numDecodingsDp(String s) {
        final int len = s.length();
        char[] cs = new char[len + 1];
        for (int i = 1; i <= len; i++) {
            cs[i] = s.charAt(i - 1);
        }
        int[] dp = new int[len + 1];
        dp[0] = 1;
        for (int i = 1; i <= len; i++) {
            // 可以组成单个字母
            if (cs[i] > '0') {
                dp[i] = dp[i - 1];
            }
            // 和前一个数字能组合成字母
            if (cs[i - 1] == '1' || cs[i - 1] == '2' && cs[i] <= '6') {
                dp[i] += dp[i - 2];
            }
        }
        return dp[len];
    }

    // dfs 的方式, 这就很慢了.
    @Answer
    public int numDecodingsDfs(String s) {
        return dfs(s.toCharArray(), 0);
    }

    private int dfs(char[] cs, int i) {
        if (i == cs.length) {
            return 1;
        }
        int res = 0;
        if (cs[i] > '0') {
            res = dfs(cs, i + 1);
        }
        if (i < cs.length - 1 && (cs[i] == '1' || cs[i] == '2' && cs[i + 1] <= '6')) {
            res += dfs(cs, i + 2);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("12").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("226").expect(3);

    @TestData
    public DataExpectation border1 = DataExpectation.createWith("0").expect(0);

    @TestData
    public DataExpectation border2 = DataExpectation.createWith("012").expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("10").expect(1);

    @TestData
    public DataExpectation normal2 = DataExpectation.createWith("12120").expect(3);

}
