package q1400;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1397. Find All Good Strings
 * https://leetcode.com/problems/find-all-good-strings/
 *
 * Given the strings s1 and s2 of size n, and the string evil. Return the number of good strings.
 *
 * A good string has size n, it is alphabetically greater than or equal to s1, it is alphabetically smaller than or
 * equal to s2, and it does not contain the string evil as a substring. Since the answer can be a huge number, return
 * this modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: n = 2, s1 = "aa", s2 = "da", evil = "b"
 * Output: 51
 * Explanation: There are 25 good strings starting with 'a': "aa","ac","ad",...,"az". Then there are 25 good strings
 * starting with 'c': "ca","cc","cd",...,"cz" and finally there is one good string starting with 'd': "da".
 *
 * Example 2:
 *
 * Input: n = 8, s1 = "leetcode", s2 = "leetgoes", evil = "leet"
 * Output: 0
 * Explanation: All strings greater than or equal to s1 and smaller than or equal to s2 start with the prefix "leet",
 * therefore, there is not any good string.
 *
 * Example 3:
 *
 * Input: n = 2, s1 = "gx", s2 = "gz", evil = "x"
 * Output: 2
 *
 * Constraints:
 *
 * s1.length == n
 * s2.length == n
 * s1 <= s2
 * 1 <= n <= 500
 * 1 <= evil.length <= 50
 * All strings consist of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1397_FindAllGoodStrings {

    /**
     * 数位dp + kmp 的题,
     * @formatter:off
     * 参考文档:
     * https://leetcode-cn.com/problems/find-all-good-strings/solution/zhao-dao-suo-you-hao-zi-fu-chuan-by-leetcode-solut/
     * https://leetcode-cn.com/problems/find-all-good-strings/solution/shu-wei-dp-kmp-by-qodjf/
     * https://blog.csdn.net/huayunhualuo/article/details/105369471
     * @formatter:on
     */
    @Answer
    public int findGoodStrings(int n, String s1, String s2, String evil) {
        this.s1 = s1;
        this.s2 = s2;
        this.evil = evil;

        int m = evil.length();
        fail = new int[m];
        // 这是 KMP 算法的一部分
        // 把「evil」看作模式串，得到它的 fail[] 数组
        for (int i = 1; i < m; ++i) {
            int j = fail[i - 1];
            while (j != 0 && evil.charAt(j) != evil.charAt(i)) {
                j = fail[j - 1];
            }
            if (evil.charAt(j) == evil.charAt(i)) {
                fail[i] = j + 1;
            }
        }
        // 将未搜索过的动态规划状态置为 -1
        dp = new int[n][m][4];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < m; ++j) {
                Arrays.fill(dp[i][j], -1);
            }
        }
        // 将未计算过的转移函数置为 -1
        trans = new int[m][26];
        for (int i = 0; i < m; ++i) {
            Arrays.fill(trans[i], -1);
        }
        // 答案即为 f[0][0][3]
        return dfs(0, 0, 3);
    }

    private int[] fail;

    // 这是存储动态规划结果的数组
    // 维度从左到右分别为 pos, stats, bound
    // int f[500][50][4];
    private int[][][] dp;

    // 这是存储转移函数结果的数组
    // 两个维度分别为 stats 和 d
    private int[][] trans;

    private String s1, s2, evil;

    private static final int MOD = 10_0000_0007;

    // 这是用来进行记忆化搜索的函数
    // 输入为 pos, stats 和 bound
    // 输出为 f[pos][stats][bound]
    public int dfs(int pos, int stats, int bound) {
        // 如果匹配到了 evil 的末尾
        // 说明字符串不满足要求了
        // 返回 0
        if (stats == evil.length()) {
            return 0;
        }
        // 如果匹配到了上下界的末尾
        // 说明找到了一个满足要求的字符串
        // 返回 1
        if (pos == s1.length()) {
            return 1;
        }
        // 记忆化搜索的关键
        // 如果之前计算过该状态就直接返回结果
        if (dp[pos][stats][bound] != -1) {
            return dp[pos][stats][bound];
        }

        // 将当前状态初始化
        // 因为未初始化的状态值为 -1
        dp[pos][stats][bound] = 0;
        // 计算第 pos 位可枚举的字符下界
        char l = ((bound & 1) != 0 ? s1.charAt(pos) : 'a');
        // 计算第 pos 位可枚举的字符上界
        char r = ((bound & 2) != 0 ? s2.charAt(pos) : 'z');
        for (char ch = l; ch <= r; ++ch) {
            int nxt_stats = getTrans(stats, ch);
            // 这里写得较为复杂
            // 本质上就是关于 bound 的转移函数
            // 可以根据自己的理解编写
            int nxt_bound = ((bound & 1) != 0 ? (ch == s1.charAt(pos) ? 1 : 0) : 0) ^ ((bound & 2) != 0 ?
                    (ch == s2.charAt(pos) ? 1 : 0) << 1 : 0);
            dp[pos][stats][bound] += dfs(pos + 1, nxt_stats, nxt_bound);
            dp[pos][stats][bound] %= MOD;
        }
        return dp[pos][stats][bound];
    }

    // 这是计算关于 stats 的转移函数
    // 输入为 stats 和 d
    // 输出为转移的结果 g_s(stats, d)
    public int getTrans(int stats, char ch) {
        // 如果之前计算过该转移函数就直接返回结果
        if (trans[stats][ch - 'a'] != -1) {
            return trans[stats][ch - 'a'];
        }
        // 这是 KMP 算法的一部分
        // 把 evil 看作「模式串」，stats 看作「主串」匹配到的位置
        while (stats != 0 && evil.charAt(stats) != ch) {
            stats = fail[stats - 1];
        }
        // 存储结果并返回
        return trans[stats][ch - 'a'] = (evil.charAt(stats) == ch ? stats + 1 : 0);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith(2, "aa", "da", "b").expect(51);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith(8, "leetcode", "leetgoes", "leet").expect(0);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith(2, "gx", "gz", "x").expect(2);

}
