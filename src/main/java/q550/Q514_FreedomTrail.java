package q550;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/freedom-trail/
 *
 * In the video game Fallout 4, the quest "Road to Freedom" requires players to reach a metal dial called the
 * "Freedom Trail Ring", and use the dial to spell a specific keyword in order to open the door.
 *
 * Given a string ring, which represents the code engraved on the outer ring and another string key, which represents
 * the keyword needs to be spelled. You need to find the minimum number of steps in order to spell all the characters
 * in the keyword.
 *
 * Initially, the first character of the ring is aligned at 12:00 direction. You need to spell all the characters in
 * the string key one by one by rotating the ring clockwise or anticlockwise to make each character of the string key
 * aligned at 12:00 direction and then by pressing the center button.
 *
 * At the stage of rotating the ring to spell the key character key[i]:
 *
 * You can rotate the ring clockwise or anticlockwise one place, which counts as 1 step. The final purpose of the
 * rotation is to align one of the string ring's characters at the 12:00 direction, where this character must
 * equal to the character key[i].
 * If the character key[i] has been aligned at the 12:00 direction, you need to press the center button to spell,
 * which also counts as 1 step. After the pressing, you could begin to spell the next character in the key (next
 * stage), otherwise, you've finished all the spelling.
 *
 * Example:
 * (图 Q514_PIC.png)
 *
 * Input: ring = "godding", key = "gd"
 * Output: 4
 * Explanation:
 * For the first key character 'g', since it is already in place, we just need 1 step to spell this character.
 * For the second key character 'd', we need to rotate the ring "godding" anticlockwise by two steps to make it
 * become "ddinggo".
 * Also, we need 1 more step for spelling.
 * So the final output is 4.
 *
 * Note:
 *
 * Length of both ring and key will be in range 1 to 100.
 * There are only lowercase letters in both strings and might be some duplcate characters in both strings.
 * It's guaranteed that string key could always be spelled by rotating the string ring.
 */
@RunWith(LeetCodeRunner.class)
public class Q514_FreedomTrail {

    /**
     * 这题不能用暴力dfs, 也不适用贪心算法, 也不适合分治, 那就是dp 了.
     * LeetCode 还有一个为dfs 添加缓存的方法, 那个是最快的解法
     */
    @Answer
    public int findRotateSteps(String ring, String key) {
        final int n = ring.length(), m = key.length();
        char[] rc = ring.toCharArray();
        int[][] dp = new int[m + 1][n];
        for (int i = 0; i <= m; i++) {
            Arrays.fill(dp[i], Integer.MAX_VALUE);
        }
        dp[0][0] = 0;
        for (int i = 0; i < key.length(); i++) {
            char c = key.charAt(i);
            for (int j = 0; j < n; j++) {
                if (dp[i][j] < Integer.MAX_VALUE) {
                    for (int k = 0; k < n; k++) {
                        if (rc[k] == c) {
                            dp[i + 1][k] = Math.min(dp[i + 1][k],
                                    dp[i][j] + 1 + Math.min((k - j + n) % n, (j - k + n) % n));
                        }
                    }
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            res = Math.min(res, dp[m][i]);
        }
        return res;
    }

    @TestData
    public DataExpectation example = DataExpectation.createWith("godding", "gd").expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("nyngl", "yyynnnnnnlllggg").expect(19);

    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith("caotmcaataijjxi", "oatjiioicitatajtijciocjcaaxaaatmctxamacaamjjx").expect(137);

}
