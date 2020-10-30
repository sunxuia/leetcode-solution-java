package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1234. Replace the Substring for Balanced String
 * https://leetcode.com/problems/replace-the-substring-for-balanced-string/
 *
 * You are given a string containing only 4 kinds of characters 'Q', 'W', 'E' and 'R'.
 *
 * A string is said to be balanced if each of its characters appears n/4 times where n is the length of the string.
 *
 * Return the minimum length of the substring that can be replaced with any other string of the same length to make the
 * original string s balanced.
 *
 * Return 0 if the string is already balanced.
 *
 * Example 1:
 *
 * Input: s = "QWER"
 * Output: 0
 * Explanation: s is already balanced.
 *
 * Example 2:
 *
 * Input: s = "QQWE"
 * Output: 1
 * Explanation: We need to replace a 'Q' to 'R', so that "RQWE" (or "QRWE") is balanced.
 *
 * Example 3:
 *
 * Input: s = "QQQW"
 * Output: 2
 * Explanation: We can replace the first "QQ" to "ER".
 *
 * Example 4:
 *
 * Input: s = "QQQQ"
 * Output: 3
 * Explanation: We can replace the last 3 'Q' to make s = "QWER".
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s.length is a multiple of 4
 * s contains only 'Q', 'W', 'E' and 'R'.
 */
@RunWith(LeetCodeRunner.class)
public class Q1234_ReplaceTheSubstringForBalancedString {

    /**
     * 确定首尾要保留的字符串, 这样中间的就是要被替换的, 时间复杂度 O(mn)
     */
    @Answer
    public int balancedString(String s) {
        final int n = s.length(), m = n / 4;
        final char[] cs = s.toCharArray();
        int[] head = new int[4];
        int[] tail = new int[4];
        int res = n;
        // s[i, j] 表示要删除的区间
        for (int i = 0, j = n - 1; i <= j; ) {
            // 每个字符总数 < m 则可以保留
            if (head[MAP[cs[i]]] + tail[MAP[cs[i]]] < m) {
                head[MAP[cs[i++]]]++;
            } else if (head[MAP[cs[j]]] + tail[MAP[cs[j]]] < m) {
                tail[MAP[cs[j--]]]++;
            } else {
                // 对于尾部字符c, 如果 head[c] + tail[c] == m,
                // 则需要将head 回退到上一次 head[c] 增加的位置,
                // 让tail[c] + 1, 由此进行回溯.
                while (i > 0 && cs[i - 1] != cs[j]) {
                    head[MAP[cs[--i]]]--;
                }
                if (i > 0) {
                    head[MAP[cs[--i]]]--;
                    tail[MAP[cs[j--]]]++;
                } else {
                    break;
                }
            }
            res = Math.min(res, j - i + 1);
        }
        return res;
    }

    private static final int[] MAP = new int[128];

    static {
        MAP['Q'] = 0;
        MAP['W'] = 1;
        MAP['E'] = 2;
        MAP['R'] = 3;
    }

    /**
     * LeetCode 上比较快的做法.
     */
    @Answer
    public int balancedString2(String s) {
        final int n = s.length(), m = n / 4;
        final char[] cs = s.toCharArray();
        int[] counts = new int[4];
        for (char c : cs) {
            counts[MAP[c]]++;
        }
        if (counts[0] == m && counts[1] == m && counts[2] == m) {
            return 0;
        }
        // s[left, right] 表示要删除的区间
        int res = n, left = 0, right = 0;
        while (right <= n) {
            if (counts[0] > m || counts[1] > m
                    || counts[2] > m || counts[3] > m) {
                if (right == n) {
                    break;
                }
                counts[MAP[cs[right++]]]--;
            } else {
                res = Math.min(res, right - left);
                counts[MAP[cs[left++]]]++;
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("QWER").expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create("QQWE").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("QQQW").expect(2);

    @TestData
    public DataExpectation example4 = DataExpectation.create("QQQQ").expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("WWEQERQWQWWRWWERQWEQ").expect(4);

    @TestData
    public DataExpectation normal2 = DataExpectation.create("WWQQRRRRQRQQ").expect(4);

}
