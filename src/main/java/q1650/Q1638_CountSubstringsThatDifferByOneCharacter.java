package q1650;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1638. Count Substrings That Differ by One Character
 * https://leetcode.com/problems/count-substrings-that-differ-by-one-character/
 *
 * Given two strings s and t, find the number of ways you can choose a non-empty substring of s and replace a single
 * character by a different character such that the resulting substring is a substring of t. In other words, find the
 * number of substrings in s that differ from some substring in t by exactly one character.
 *
 * For example, the underlined substrings in "computer" and "computation" only differ by the 'e'/'a', so this is a valid
 * way.
 *
 * Return the number of substrings that satisfy the condition above.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: s = "aba", t = "baba"
 * Output: 6
 * Explanation: The following are the pairs of substrings from s and t that differ by exactly 1 character:
 * ("aba", "baba")
 * ("aba", "baba")
 * ("aba", "baba")
 * ("aba", "baba")
 * ("aba", "baba")
 * ("aba", "baba")
 * The underlined portions are the substrings that are chosen from s and t.
 *
 * Example 2:
 *
 * Input: s = "ab", t = "bb"
 * Output: 3
 * Explanation: The following are the pairs of substrings from s and t that differ by 1 character:
 * ("ab", "bb")
 * ("ab", "bb")
 * ("ab", "bb")
 * The underlined portions are the substrings that are chosen from s and t.
 *
 * Example 3:
 *
 * Input: s = "a", t = "a"
 * Output: 0
 *
 * Example 4:
 *
 * Input: s = "abe", t = "bbc"
 * Output: 10
 *
 * Constraints:
 *
 * 1 <= s.length, t.length <= 100
 * s and t consist of lowercase English letters only.
 *
 * 题解: (下划线在注释说明中显示不出来)
 * 从 s 中找出一非空子字符串 str, 然后替换 str 中的一个字符, str' 与 t 的子字符串 sub 相等.
 * 现要求返回 str 与 sub 的组合数目 (s 与 t 中内容一样位置不同的子字符串算不同的子字符串).
 */
@RunWith(LeetCodeRunner.class)
public class Q1638_CountSubstringsThatDifferByOneCharacter {

    /**
     * 暴力求解. 这种方式比较慢.
     */
    @Answer
    public int countSubstrings(String s, String t) {
        final int m = s.length(), n = t.length();
        final char[] sc = s.toCharArray();
        final char[] tc = t.toCharArray();
        int res = 0;
        // s 子串起始位置
        for (int i = 0; i < m; i++) {
            // s 子串结束位置 (包含)
            for (int j = i; j < m; j++) {
                // 子串中变换的字符位置
                for (int k = i; k <= j; k++) {
                    // t 中子串的起始位置
                    for (int l = 0; l < n; l++) {
                        if (match(sc, tc, i, j, k, l)) {
                            res++;
                        }
                    }
                }
            }
        }
        return res;
    }

    private boolean match(char[] sc, char[] tc, int ss, int se, int sd, int ts) {
        while (ss <= se && ts < tc.length) {
            if (ss == sd) {
                if (sc[ss] == tc[ts]) {
                    return false;
                }
            } else {
                if (sc[ss] != tc[ts]) {
                    return false;
                }
            }
            ss++;
            ts++;
        }
        return ss > se;
    }

    /**
     * LeetCode 上比较快的方式.
     */
    @Answer
    public int countSubstrings2(String s, String t) {
        final int m = s.length(), n = t.length();
        final char[] sc = s.toCharArray();
        final char[] tc = t.toCharArray();
        int res = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // p : s 子串起始位置
                // q : t 子串起始位置
                // r : 已经变换的字符个数
                int p = i, q = j, r = 0;
                while (p < m && q < n) {
                    if (sc[p] != tc[q]) {
                        r++;
                    }
                    if (r == 1) {
                        // 只需要变换1 个, 而其他的相等,
                        // 则这里直接 +1, 避免了上面的多层循环
                        res++;
                    } else if (r == 2) {
                        break;
                    }
                    p++;
                    q++;
                }
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("aba", "baba").expect(6);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("ab", "bb").expect(3);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("a", "a").expect(0);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("abe", "bbc").expect(10);

}
