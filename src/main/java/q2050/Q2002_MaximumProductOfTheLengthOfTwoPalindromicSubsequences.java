package q2050;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 2002. Maximum Product of the Length of Two Palindromic Subsequences
 * https://leetcode.com/problems/maximum-product-of-the-length-of-two-palindromic-subsequences/
 *
 * Given a string s, find two disjoint palindromic subsequences of s such that the product of their lengths is
 * maximized. The two subsequences are disjoint if they do not both pick a character at the same index.
 *
 * Return the maximum possible product of the lengths of the two palindromic subsequences.
 *
 * A subsequence is a string that can be derived from another string by deleting some or no characters without changing
 * the order of the remaining characters. A string is palindromic if it reads the same forward and backward.
 *
 * Example 1:
 * (图Q2002_PIC.png)
 * Input: s = "leetcodecom"
 * Output: 9
 * Explanation: An optimal solution is to choose "ete" for the 1st subsequence and "cdc" for the 2nd subsequence.
 * The product of their lengths is: 3 * 3 = 9.
 *
 * Example 2:
 *
 * Input: s = "bb"
 * Output: 1
 * Explanation: An optimal solution is to choose "b" (the first character) for the 1st subsequence and "b" (the second
 * character) for the 2nd subsequence.
 * The product of their lengths is: 1 * 1 = 1.
 *
 * Example 3:
 *
 * Input: s = "accbcaxxcxx"
 * Output: 25
 * Explanation: An optimal solution is to choose "accca" for the 1st subsequence and "xxcxx" for the 2nd subsequence.
 * The product of their lengths is: 5 * 5 = 25.
 *
 * Constraints:
 *
 * 2 <= s.length <= 12
 * s consists of lowercase English letters only.
 */
@RunWith(LeetCodeRunner.class)
public class Q2002_MaximumProductOfTheLengthOfTwoPalindromicSubsequences {

    @Answer
    public int maxProduct(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        final int len = 1 << n;
        boolean[] isPalindromic = new boolean[len];
        loop:
        for (int mask = 1; mask < len; mask++) {
            for (int i = 0, j = n - 1; i < j; ) {
                if ((1 << i & mask) == 0) {
                    i++;
                } else if ((1 << j & mask) == 0) {
                    j--;
                } else if (cs[i] != cs[j]) {
                    continue loop;
                } else {
                    i++;
                    j--;
                }
            }
            isPalindromic[mask] = true;
        }

        int res = 0;
        for (int mask1 = 1; mask1 < len; mask1++) {
            for (int mask2 = mask1 + 1; mask2 < len; mask2++) {
                if ((mask1 & mask2) == 0 && isPalindromic[mask1] && isPalindromic[mask2]) {
                    res = Math.max(res, getLength(mask1) * getLength(mask2));
                }
            }
        }
        return res;
    }

    private int getLength(int mask) {
        int res = 0;
        while (mask > 0) {
            mask = mask & (mask - 1);
            res++;
        }
        return res;
    }

    /**
     * 动态规划的算法, 参考leetcode 中的提示.
     */
    @Answer
    public int maxProduct2(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        final int len = 1 << n;
        // dp[mask] 表示mask 内最长的回文子字符串长度.
        int[] dp = new int[len];
        // 遍历数据组合
        for (int mask = 1; mask < len; mask++) {
            int h = getHigherBit(mask);
            int l = getLowerBit(mask);
            if (h == l) {
                dp[mask] = 1;
            } else if (cs[h] == cs[l]) {
                dp[mask] = 2 + dp[mask ^ (1 << h) ^ (1 << l)];
            } else {
                dp[mask] = Math.max(dp[mask ^ (1 << h)], dp[mask ^ (1 << l)]);
            }
        }

        int res = 0;
        for (int mask = 0; mask < len; mask++) {
            int product = dp[mask] * (dp[((1 << n) - 1) ^ mask]);
            res = Math.max(res, product);
        }
        return res;
    }

    private int getHigherBit(int mask) {
        int res = 0;
        while (mask != 1) {
            mask >>= 1;
            res++;
        }
        return res;
    }

    private int getLowerBit(int mask) {
        int res = 0;
        while ((mask & 1) == 0) {
            mask >>= 1;
            res++;
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("leetcodecom").expect(9);

    @TestData
    public DataExpectation example2 = DataExpectation.create("bb").expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.create("accbcaxxcxx").expect(25);

}
