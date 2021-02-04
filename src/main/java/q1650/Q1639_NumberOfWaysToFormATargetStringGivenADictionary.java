package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1639. Number of Ways to Form a Target String Given a Dictionary
 * https://leetcode.com/problems/number-of-ways-to-form-a-target-string-given-a-dictionary/
 *
 * You are given a list of strings of the same length words and a string target.
 *
 * Your task is to form target using the given words under the following rules:
 *
 * target should be formed from left to right.
 * To form the ith character (0-indexed) of target, you can choose the kth character of the jth string in words if
 * target[i] = words[j][k].
 * Once you use the kth character of the jth string of words, you can no longer use the xth character of any string in
 * words where x <= k. In other words, all characters to the left of or at index k become unusuable for every string.
 * Repeat the process until you form the string target.
 *
 * Notice that you can use multiple characters from the same string in words provided the conditions above are met.
 *
 * Return the number of ways to form target from words. Since the answer may be too large, return it modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: words = ["acca","bbbb","caca"], target = "aba"
 * Output: 6
 * Explanation: There are 6 ways to form target.
 * "aba" -> index 0 ("acca"), index 1 ("bbbb"), index 3 ("caca")
 * "aba" -> index 0 ("acca"), index 2 ("bbbb"), index 3 ("caca")
 * "aba" -> index 0 ("acca"), index 1 ("bbbb"), index 3 ("acca")
 * "aba" -> index 0 ("acca"), index 2 ("bbbb"), index 3 ("acca")
 * "aba" -> index 1 ("caca"), index 2 ("bbbb"), index 3 ("acca")
 * "aba" -> index 1 ("caca"), index 2 ("bbbb"), index 3 ("caca")
 *
 * Example 2:
 *
 * Input: words = ["abba","baab"], target = "bab"
 * Output: 4
 * Explanation: There are 4 ways to form target.
 * "bab" -> index 0 ("baab"), index 1 ("baab"), index 2 ("abba")
 * "bab" -> index 0 ("baab"), index 1 ("baab"), index 3 ("baab")
 * "bab" -> index 0 ("baab"), index 2 ("baab"), index 3 ("baab")
 * "bab" -> index 1 ("abba"), index 2 ("baab"), index 3 ("baab")
 *
 * Example 3:
 *
 * Input: words = ["abcd"], target = "abcd"
 * Output: 1
 *
 * Example 4:
 *
 * Input: words = ["abab","baba","abba","baab"], target = "abba"
 * Output: 16
 *
 * Constraints:
 *
 * 1 <= words.length <= 1000
 * 1 <= words[i].length <= 1000
 * All strings in words have the same length.
 * 1 <= target.length <= 1000
 * words[i] and target contain only lowercase English letters.
 *
 * 题解: 这题的意思就是, words 中所有字符串长度相同, 构成一个矩阵, 有一个只能前进的游标 s,
 * 扫描 words 的列来构造 t, 如果存在需要的字符则取得这个字符并前进. 求有多少个不同的构造方式.
 */
@RunWith(LeetCodeRunner.class)
public class Q1639_NumberOfWaysToFormATargetStringGivenADictionary {

    @Answer
    public int numWays(String[] words, String target) {
        final int mod = 10_0000_0007;
        final int m = words[0].length();
        final int n = target.length();
        final char[] tc = target.toCharArray();

        int[][] counts = new int[m][26];
        for (int i = 0; i < m; i++) {
            for (String word : words) {
                counts[i][word.charAt(i) - 'a']++;
            }
        }

        // dp[i][j] 表示匹配到 target[i] 位置, 游标在 j 位置的结果数量.
        int[][] dp = new int[n][m];
        dp[0][0] = counts[0][tc[0] - 'a'];
        for (int j = 1; j < m; j++) {
            dp[0][j] = dp[0][j - 1] + counts[j][tc[0] - 'a'];
        }
        for (int i = 1; i < n; i++) {
            for (int j = i; j < m; j++) {
                long prev = (long) counts[j][tc[i] - 'a'] * dp[i - 1][j - 1] % mod;
                dp[i][j] = (dp[i][j - 1] + (int) prev) % mod;
            }
        }
        return dp[n - 1][m - 1];
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith(new String[]{"acca", "bbbb", "caca"}, "aba")
            .expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith(new String[]{"abba", "baab"}, "bab")
            .expect(4);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith(new String[]{"abcd"}, "abcd")
            .expect(1);

    @TestData
    public DataExpectation example4 = DataExpectation
            .createWith(new String[]{"abab", "baba", "abba", "baab"}, "abba")
            .expect(16);

    @TestData
    public DataExpectation overflow() {
        return DataExpectation.createWith(
                new String[]{
                        "cabbaacaaaccaabbbbaccacbabbbcb",
                        "bbcabcbcccbcacbbbaacacaaabbbac",
                        "cbabcaacbcaaabbcbaabaababbacbc",
                        "aacabbbcaaccaabbaccacabccaacca",
                        "bbabbaabcaabccbbabccaaccbabcab",
                        "bcaccbbaaccaabcbabbacaccbbcbbb",
                        "cbbcbcaaaacacabbbabacbaabbabaa",
                        "cbbbbbbcccbabbacacacacccbbccca",
                        "bcbccbccacccacaababcbcbbacbbbc",
                        "ccacaabaaabbbacacbacbaaacbcaca",
                        "bacaaaabaabccbcbbaacacccabbbcb",
                        "bcbcbcabbccabacbcbcaccacbcaaab",
                        "babbbcccbbbbbaabbbacbbaabaabcc",
                        "baaaacaaacbbaacccababbaacccbcb",
                        "babbaaabaaccaabacbbbacbcbababa",
                        "cbacacbacaaacbaaaabacbbccccaca",
                        "bcbcaccaabacaacaaaccaabbcacaaa",
                        "cccbabccaabbcbccbbabaaacbacaaa",
                        "bbbcabacbbcabcbcaaccbcacacccca",
                        "ccccbbaababacbabcaacabaccbabaa",
                        "caaabccbcaaccabbcbcaacccbcacba",
                        "cccbcaacbabaacbaaabbbbcbbbbcbb",
                        "cababbcacbabcbaababcbcabbaabba",
                        "aaaacacaaccbacacbbbbccaabcccca",
                        "cbcaaaaabacbacaccbcbcbccaabaac",
                        "bcbbccbabaccabcccacbbaacbbcbba",
                        "cccbabbbcbbabccbbabbbbcaaccaab",
                        "acccacccaabbcaccbcaaccbababacc",
                        "bcacabaacccbbcbbacabbbbbcaaaab",
                        "cacccaacbcbccbabccabbcbabbcacc",
                        "aacabbabcaacbaaacaabcabcaccaab",
                        "cccacabacbabccbccaaaaabbcacbcc",
                        "cabaacacacaaabaacaabababccbaaa",
                        "caabaccaacccbaabcacbcbbabccabc",
                        "bcbbccbbaaacbaacbccbcbababcacb",
                        "bbabbcabcbbcababbbbccabaaccbca",
                        "cacbbbccabaaaaccacbcbabaabbcba",
                        "ccbcacbabababbbcbcabbcccaccbca",
                        "acccabcacbcbbcbccaccaacbabcaab",
                        "ccacaabcbbaabaaccbabcbacaaabaa",
                        "cbabbbbcabbbbcbccabaabccaccaca",
                        "acbbbbbccabacabcbbabcaacbbaacc",
                        "baaababbcabcacbbcbabacbcbaaabc",
                        "cabbcabcbbacaaaaacbcbbcacaccac"
                }, "acbaccacbbaaabbbabac")
                .expect(555996654);
    }

}
