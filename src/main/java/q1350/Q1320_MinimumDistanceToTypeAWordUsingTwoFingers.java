package q1350;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1320. Minimum Distance to Type a Word Using Two Fingers
 * https://leetcode.com/problems/minimum-distance-to-type-a-word-using-two-fingers/
 *
 * <img src="Q1320_PIC.png">
 * You have a keyboard layout as shown above in the XY plane, where each English uppercase letter is located at some
 * coordinate, for example, the letter A is located at coordinate (0,0), the letter B is located at coordinate (0,1),
 * the letter P is located at coordinate (2,3) and the letter Z is located at coordinate (4,1).
 *
 * Given the string word, return the minimum total distance to type such string using only two fingers. The distance
 * between coordinates (x1,y1) and (x2,y2) is |x1 - x2| + |y1 - y2|.
 *
 * Note that the initial positions of your two fingers are considered free so don't count towards your total distance,
 * also your two fingers do not have to start at the first letter or the first two letters.
 *
 * Example 1:
 *
 * Input: word = "CAKE"
 * Output: 3
 * Explanation:
 * Using two fingers, one optimal way to type "CAKE" is:
 * Finger 1 on letter 'C' -> cost = 0
 * Finger 1 on letter 'A' -> cost = Distance from letter 'C' to letter 'A' = 2
 * Finger 2 on letter 'K' -> cost = 0
 * Finger 2 on letter 'E' -> cost = Distance from letter 'K' to letter 'E' = 1
 * Total distance = 3
 *
 * Example 2:
 *
 * Input: word = "HAPPY"
 * Output: 6
 * Explanation:
 * Using two fingers, one optimal way to type "HAPPY" is:
 * Finger 1 on letter 'H' -> cost = 0
 * Finger 1 on letter 'A' -> cost = Distance from letter 'H' to letter 'A' = 2
 * Finger 2 on letter 'P' -> cost = 0
 * Finger 2 on letter 'P' -> cost = Distance from letter 'P' to letter 'P' = 0
 * Finger 1 on letter 'Y' -> cost = Distance from letter 'A' to letter 'Y' = 4
 * Total distance = 6
 *
 * Example 3:
 *
 * Input: word = "NEW"
 * Output: 3
 *
 * Example 4:
 *
 * Input: word = "YEAR"
 * Output: 7
 *
 * Constraints:
 *
 * 2 <= word.length <= 300
 * Each word[i] is an English uppercase letter.
 *
 * 题解: 将字符串分为2 组, 要求从前往后遍历这2 组中的字符的时候移动的距离最短.
 */
@RunWith(LeetCodeRunner.class)
public class Q1320_MinimumDistanceToTypeAWordUsingTwoFingers {

    /**
     * 二维动态规划的方式, 时间复杂度 O(N^3)
     */
    @Answer
    public int minimumDistance(String word) {
        final char[] cs = word.toCharArray();
        final int n = cs.length;
        int[] distances = new int[n];
        for (int i = 1; i < n; i++) {
            distances[i] = distances[i - 1] + getDistance(cs[i - 1], cs[i]);
        }
        // dp[p][c], p < c, 表示 s[0:c] 之间都被填满了,
        // 其中一个指头在 s[p] 处, 一个指头在 s[c] 处,
        // 所以 s[p+1, c] 之间都是由一个指头来操作的.
        int[][] dp = new int[n][n];
        // 当前手指的位置
        for (int c = 2; c < n; c++) {
            // 另一个手指的位置
            for (int p = c - 1; p >= 0; p--) {
                // 当前手指从 p+1 开始
                dp[p][c] = distances[p] + distances[c] - distances[p + 1];
                // 当前手指有在p 之前的位置
                for (int i = p - 1; i >= 0; i--) {
                    int dist = dp[i][p] + getDistance(cs[p + 1], cs[i]) +
                            distances[c] - distances[p + 1];
                    dp[p][c] = Math.min(dp[p][c], dist);
                }
            }
        }

        int res = Integer.MAX_VALUE;
        for (int p = 0; p < n - 1; p++) {
            res = Math.min(res, dp[p][n - 1]);
        }
        return res;
    }

    private int getDistance(char a, char b) {
        int ai = (a - 'A') / 6, aj = (a - 'A') % 6;
        int bi = (b - 'A') / 6, bj = (b - 'A') % 6;
        return Math.abs(ai - bi) + Math.abs(aj - bj);
    }

    /**
     * 参考LeetCode上的解答, 更简单的动态规划的方式, 时间复杂度 O(N).
     */
    @Answer
    public int minimumDistance2(String word) {
        final char[] cs = word.toCharArray();
        final int n = cs.length;
        int[] dp = new int[26];
        int res = 0, save = 0;
        for (int i = 0; i < n - 1; i++) {
            int from = cs[i] - 'A', to = cs[i + 1] - 'A';
            for (char c = 0; c < 26; c++) {
                dp[from] = Math.max(dp[from],
                        dp[c] + getDistance(from, to) - getDistance(c, to));
            }
            save = Math.max(save, dp[from]);
            res += getDistance(from, to);
        }
        return res - save;
    }

    private int getDistance(int a, int b) {
        return Math.abs(a / 6 - b / 6) + Math.abs(a % 6 - b % 6);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("CAKE").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("HAPPY").expect(6);

    @TestData
    public DataExpectation example3 = DataExpectation.create("NEW").expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.create("YEAR").expect(7);

}
