package q050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/wildcard-matching/
 *
 * Given an input string (s) and a pattern (p), implement wildcard pattern matching with support for '?' and '*'.
 *
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 * The matching should cover the entire input string (not partial).
 *
 * Note:
 *
 * s could be empty and contains only lowercase letters a-z.
 * p could be empty and contains only lowercase letters a-z, and characters like ? or *.
 * Example 1:
 *
 * Input:
 * s = "aa"
 * p = "a"
 * Output: false
 * Explanation: "a" does not match the entire string "aa".
 * Example 2:
 *
 * Input:
 * s = "aa"
 * p = "*"
 * Output: true
 * Explanation: '*' matches any sequence.
 * Example 3:
 *
 * Input:
 * s = "cb"
 * p = "?a"
 * Output: false
 * Explanation: '?' matches 'c', but the second letter is 'a', which does not match 'b'.
 * Example 4:
 *
 * Input:
 * s = "adceb"
 * p = "*a*b"
 * Output: true
 * Explanation: The first '*' matches the empty sequence, while the second '*' matches the substring "dce".
 * Example 5:
 *
 * Input:
 * s = "acdcb"
 * p = "a*c?b"
 * Output: false
 */
@RunWith(LeetCodeRunner.class)
public class Q044_WildcardMatching {

    /**
     * dp 的方式.
     */
    @Answer
    public boolean isMatch(String s, String p) {
        boolean[][] dp = new boolean[1 + s.length()][1 + p.length()];
        dp[0][0] = true;
        for (int i = 0; i < p.length() && p.charAt(i) == '*'; i++) {
            dp[0][i + 1] = true;
        }
        for (int si = 0; si < s.length(); si++) {
            for (int pi = 0; pi < p.length(); pi++) {
                char pc = p.charAt(pi);
                if (pc == '*') {
                    dp[si + 1][pi + 1] = dp[si][pi] || dp[si + 1][pi] || dp[si][pi + 1];
                } else {
                    dp[si + 1][pi + 1] = (pc == '?' || pc == s.charAt(si)) && dp[si][pi];
                }

//                TablePrinter printer = new TablePrinter();
//                printer.leftAxisChars(" " + s).topAxisChars(" " + p);
//                printer.addPointer(si + 1, pi + 1, "p");
//                printer.print(dp, v -> v ? "√" : "×").printLine();
            }
        }
        return dp[s.length()][p.length()];
    }

    /**
     * LeetCode 上的最快解题. 因为* 匹配总是全部匹配, 所以当前*
     * 匹配之前的*匹配的内容就无关重要了, 再采用懒惰匹配就可以了.
     */
    @Answer
    public boolean leetCode(String s, String p) {
        // si 表示当前s 匹配的位置, pi 表示当前p 对应的匹配位置
        // pStar 表示上一次* 匹配p 中的位置, sStar 表示上一次* 匹配s 中的位置.
        int si = 0, pi = 0, pStar = -1, sStar = -1;
        while (si < s.length()) {
            // 因为s 和p 中都没有大写字母, 所以这里使用'X' 作为停止字符
            char pc = pi < p.length() ? p.charAt(pi) : 'X';
            if (pc == '?' || pc == s.charAt(si)) {
                // 单字符匹配
                pi++;
                si++;
            } else if (pc == '*') {
                // * 匹配, 在这里记住上一次匹配位置. 默认0 匹配.
                pStar = pi;
                sStar = si;
                pi++;
            } else if (pStar == -1) {
                // 不匹配, 且没有上一个* 匹配
                return false;
            } else {
                // 退回到上次* 匹配, * 匹配次数 + 1
                pi = pStar + 1;
                si = ++sStar;
            }
        }
        // 处理末尾的* 号
        while (pi < p.length() && p.charAt(pi) == '*') {
            pi++;
        }
        return pi == p.length();
    }

    /**
     * dfs 的方式, 这种方式会造成运行超时. (测试用例 longString1, longString2)
     */
    public boolean dfs(String s, String p) {
        return dfs(s, p, 0, 0);
    }

    private boolean dfs(String s, String p, int sIndex, int pIndex) {
        if (pIndex == p.length()) {
            return sIndex == s.length();
        }
        if (sIndex <= s.length()) {
            char pc = p.charAt(pIndex);
            if (pc == '?') {
                return dfs(s, p, sIndex + 1, pIndex + 1);
            } else if (pc == '*') {
                // 贪婪匹配, 这个会造成 longString1 超时
//                int matchCount = s.length() - sIndex;
//                while (matchCount >= 0) {
//                    if (dfs(s, p, sIndex + matchCount, pIndex + 1)) {
//                        return true;
//                    }
//                    matchCount--;
//                }
                // 懒惰匹配, 这个会造成 longString2 超时
                for (int i = sIndex; i <= s.length(); i++) {
                    if (dfs(s, p, i, pIndex + 1)) {
                        return true;
                    }
                }
            } else {
                if (sIndex < s.length() && s.charAt(sIndex) == pc) {
                    return dfs(s, p, sIndex + 1, pIndex + 1);
                }
            }
        }
        return false;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.builder()
            .addArgument("aa")
            .addArgument("a")
            .expect(false)
            .build();

    @TestData
    public DataExpectation example2 = DataExpectation.builder()
            .addArgument("aa")
            .addArgument("*")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example3 = DataExpectation.builder()
            .addArgument("cb")
            .addArgument("?a")
            .expect(false)
            .build();

    @TestData
    public DataExpectation example4 = DataExpectation.builder()
            .addArgument("adceb")
            .addArgument("a*b")
            .expect(true)
            .build();

    @TestData
    public DataExpectation example5 = DataExpectation.builder()
            .addArgument("acdcb")
            .addArgument("a*c?b")
            .expect(false)
            .build();

    @TestData
    public DataExpectation normal1 = DataExpectation.builder()
            .addArgument("adceb")
            .addArgument("*a*b")
            .expect(true)
            .build();

    @TestData
    public DataExpectation normal2 = DataExpectation.builder()
            .addArgument("aaaa")
            .addArgument("***a")
            .expect(true)
            .build();

    @TestData
    public DataExpectation border1 = DataExpectation.builder()
            .addArgument("")
            .addArgument("?")
            .expect(false)
            .build();

    @TestData
    public DataExpectation border2 = DataExpectation.builder()
            .addArgument("")
            .addArgument("")
            .expect(true)
            .build();

    /**
     * 如果使用dfs 的话, 这个测试用例会运行很长时间. 造成运行超时.
     */
    @TestData
    public DataExpectation longString1 = DataExpectation.builder()
            .addArgument("aaaabaaaabbbbaabbbaabbaababbabbaaaababaaabbbbbbaabbbaba"
                    + "babbaaabaabaaaaaabbaabbbbaababbababaabbbaababbbba")
            .addArgument("*****b*aba***babaa*bbaba***a*aaba*b*aa**a*b**ba***a*a*")
            .expect(true)
            .build();

    @TestData
    public DataExpectation longString2 = DataExpectation.builder()
            .addArgument("aaabbbaabaaaaababaabaaabbabbbbbbbbaabababbabbbaaaaba")
            .addArgument("a*******b")
            .expect(false)
            .build();

}
