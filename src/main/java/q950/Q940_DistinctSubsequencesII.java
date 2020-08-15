package q950;

import org.junit.runner.RunWith;
import q150.Q115_DistinctSubsequences;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 940. Distinct Subsequences II
 * https://leetcode.com/problems/distinct-subsequences-ii/
 *
 * Given a string S, count the number of distinct, non-empty subsequences of S .
 *
 * Since the result may be large, return the answer modulo 10^9 + 7.
 *
 * Example 1:
 *
 * Input: "abc"
 * Output: 7
 * Explanation: The 7 distinct subsequences are "a", "b", "c", "ab", "ac", "bc", and "abc".
 *
 * Example 2:
 *
 * Input: "aba"
 * Output: 6
 * Explanation: The 6 distinct subsequences are "a", "b", "ab", "ba", "aa" and "aba".
 *
 * Example 3:
 *
 * Input: "aaa"
 * Output: 3
 * Explanation: The 3 distinct subsequences are "a", "aa" and "aaa".
 *
 * Note:
 *
 * S contains only lowercase letters.
 * 1 <= S.length <= 2000
 *
 * 上一题 {@link Q115_DistinctSubsequences}
 */
@RunWith(LeetCodeRunner.class)
public class Q940_DistinctSubsequencesII {

    @TestData
    public DataExpectation example1 = DataExpectation.create("abc").expect(7);
    @TestData
    public DataExpectation example2 = DataExpectation.create("aba").expect(6);
    @TestData
    public DataExpectation example3 = DataExpectation.create("aaa").expect(3);
    @TestData
    public DataExpectation normal1 = DataExpectation
            .create("blljuffdyfrkqtwfyfztpdiyktrhftgtabxxoibcclbjvirnqyynkyaqlxgyybkgyzvcahmytjdqqtctirnxfjpktxmjkojlvvrr")
            .expect(589192369);

    /**
     * LeetCode 上的解法, 不太好理解
     *
     * @formatter:off 参考文档:
     * https://leetcode.com/problems/distinct-subsequences-ii/discuss/192030/Java-DP-ON2-time-greater-ON-time-greater
     * -O1-space
     * https://www.jianshu.com/p/02501f516437
     * https://www.cnblogs.com/grandyang/p/12735600.html
     * @formatter:on
     */
    @Answer
    public int distinctSubseqII(String S) {
        final int mod = 10_0000_0007;
        // endWith[c] 表示以字符c 结尾的序列的数量.
        int[] endWith = new int[26];
        // 表示所有子序列的数量
        int res = 0;
        for (int i = 0; i < S.length(); i++) {
            int idx = S.charAt(i) - 'a';
            // plus 表示当前新增的子序列数量.
            // 每次更新, 都会在之前所有子序列的基础上加上新的字符idx
            // 但同时还需要去除原来以idx 结尾的重复的数量( endWith[index] )
            // +1 表示idx 单个字母的情况, 第一次则endWith[idx] 为0, 第n 次则res - endWith[idx] 会扣掉
            // + mod 是为了防止取余后res < endWith[idx] 的问题.
            int plus = (res - endWith[idx] + 1 + mod) % mod;
            res = (res + plus) % mod;
            endWith[idx] = (endWith[idx] + plus) % mod;
        }
        return res;
    }

}
