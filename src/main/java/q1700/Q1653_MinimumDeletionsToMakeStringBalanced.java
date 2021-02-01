package q1700;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1653. Minimum Deletions to Make String Balanced
 * https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/
 *
 * You are given a string s consisting only of characters 'a' and 'b'????.
 *
 * You can delete any number of characters in s to make s balanced. s is balanced if there is no pair of indices (i,j)
 * such that i < j and s[i] = 'b' and s[j]= 'a'.
 *
 * Return the minimum number of deletions needed to make s balanced.
 *
 * Example 1:
 *
 * Input: s = "aababbab"
 * Output: 2
 * Explanation: You can either:
 * Delete the characters at 0-indexed positions 2 and 6 ("aababbab" -> "aaabbb"), or
 * Delete the characters at 0-indexed positions 3 and 6 ("aababbab" -> "aabbbb").
 *
 * Example 2:
 *
 * Input: s = "bbaaaaabb"
 * Output: 2
 * Explanation: The only solution is to delete the first two characters.
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s[i] is 'a' or 'b'.
 *
 * 题解: 也就是删除一些字符, 让 s 变为前面全是 a 后面全是 b, 求最少的删除数.
 */
@RunWith(LeetCodeRunner.class)
public class Q1653_MinimumDeletionsToMakeStringBalanced {

    @Answer
    public int minimumDeletions(String s) {
        final int n = s.length();
        int[] countB = new int[n + 1];
        int[] countA = new int[n + 1];
        for (int i = 0; i < n; i++) {
            countB[i + 1] = countB[i] + (s.charAt(i) == 'b' ? 1 : 0);
        }
        for (int i = n - 1; i >= 0; i--) {
            countA[i] = countA[i + 1] + (s.charAt(i) == 'a' ? 1 : 0);
        }

        int res = n;
        for (int i = 0; i <= n; i++) {
            res = Math.min(res, countB[i] + countA[i]);
        }
        return res;
    }

    /**
     * LeetCode 上比较快的解法.
     *
     * @formatter:off
     * 参考文档
     * https://leetcode.com/problems/minimum-deletions-to-make-string-balanced/discuss/997750/6-different-approaches%3A-from-basic-to-the-most-optimal-(C%2B%2B)
     * @formatter:on
     */
    @Answer
    public int minimumDeletions2(String s) {
        int res = 0, b = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'b') {
                // 保持 b
                b++;
            } else {
                // 两种情况: (两种情况的转换只会转换一次)
                // 删除 a, 则数量是 res + 1
                // 删除 b, 则左边的 'b' 的数量就是 b
                res = Math.min(res + 1, b);
            }
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aababbab").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("bbaaaaabb").expect(2);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .create("ababaaaabbbbbaaababbbbbbaaabbaababbabbbbaabbbbaabbabbabaabbbababaa")
            .expect(25);

}
