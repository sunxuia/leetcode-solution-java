package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/count-and-say/
 *
 * The count-and-say sequence is the sequence of integers with the first five terms as following:
 *
 * 1.     1
 * 2.     11
 * 3.     21
 * 4.     1211
 * 5.     111221
 * 1 is read off as "one 1" or 11.
 * 11 is read off as "two 1s" or 21.
 * 21 is read off as "one 2, then one 1" or 1211.
 *
 * Given an integer n where 1 ≤ n ≤ 30, generate the nth term of the count-and-say sequence.
 *
 * Note: Each term of the sequence of integers will be represented as a string.
 *
 *
 *
 * Example 1:
 *
 * Input: 1
 * Output: "1"
 * Example 2:
 *
 * Input: 4
 * Output: "1211"
 */
@RunWith(LeetCodeRunner.class)
public class Q038_CountAndSay {

    @Answer
    public String dp(int n) {
        StringBuilder sb = new StringBuilder(n);
        String[] dp = new String[n];
        dp[0] = "1";
        for (int i = 1; i < n; i++) {
            sb.setLength(0);
            char lastOne = dp[i - 1].charAt(0);
            int lastOneCount = 1;
            for (int j = 1; j < dp[i - 1].length(); j++) {
                if (lastOne == dp[i - 1].charAt(j)) {
                    lastOneCount++;
                } else {
                    sb.append(lastOneCount).append(lastOne);
                    lastOne = dp[i - 1].charAt(j);
                    lastOneCount = 1;
                }
            }
            sb.append(lastOneCount).append(lastOne);
            dp[i] = sb.toString();
        }
        return dp[n - 1];
    }

    /**
     * dfs 的方式要更快.
     */
    @Answer
    public String dfs(int n) {
        if (n == 1) {
            return "1";
        }
        String last = dfs(n - 1);
        StringBuilder sb = new StringBuilder(n);
        char lastOne = last.charAt(0);
        int lastOneCount = 1;
        for (int j = 1; j < last.length(); j++) {
            if (lastOne == last.charAt(j)) {
                lastOneCount++;
            } else {
                sb.append(lastOneCount).append(lastOne);
                lastOne = last.charAt(j);
                lastOneCount = 1;
            }
        }
        sb.append(lastOneCount).append(lastOne);
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument(1)
            .expect("1")
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument(4)
            .expect("1211")
            .build();
}
