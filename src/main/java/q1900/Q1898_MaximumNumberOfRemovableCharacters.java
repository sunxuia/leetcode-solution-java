package q1900;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1898. Maximum Number of Removable Characters
 * https://leetcode.com/problems/maximum-number-of-removable-characters/
 *
 * You are given two strings s and p where p is a subsequence of s. You are also given a distinct 0-indexed integer
 * array removable containing a subset of indices of s (s is also 0-indexed).
 *
 * You want to choose an integer k (0 <= k <= removable.length) such that, after removing k characters from s using the
 * first k indices in removable, p is still a subsequence of s. More formally, you will mark the character at
 * s[removable[i]] for each 0 <= i < k, then remove all marked characters and check if p is still a subsequence.
 *
 * Return the maximum k you can choose such that p is still a subsequence of s after the removals.
 *
 * A subsequence of a string is a new string generated from the original string with some characters (can be none)
 * deleted without changing the relative order of the remaining characters.
 *
 * Example 1:
 *
 * Input: s = "abcacb", p = "ab", removable = [3,1,0]
 * Output: 2
 * Explanation: After removing the characters at indices 3 and 1, "abcacb" becomes "accb".
 * "ab" is a subsequence of "accb".
 * If we remove the characters at indices 3, 1, and 0, "abcacb" becomes "ccb", and "ab" is no longer a subsequence.
 * Hence, the maximum k is 2.
 *
 * Example 2:
 *
 * Input: s = "abcbddddd", p = "abcd", removable = [3,2,1,4,5,6]
 * Output: 1
 * Explanation: After removing the character at index 3, "abcbddddd" becomes "abcddddd".
 * "abcd" is a subsequence of "abcddddd".
 *
 * Example 3:
 *
 * Input: s = "abcab", p = "abc", removable = [0,1,2,3,4]
 * Output: 0
 * Explanation: If you remove the first index in the array removable, "abc" is no longer a subsequence.
 *
 * Constraints:
 *
 * 1 <= p.length <= s.length <= 10^5
 * 0 <= removable.length < s.length
 * 0 <= removable[i] < s.length
 * p is a subsequence of s.
 * s and p both consist of lowercase English letters.
 * The elements in removable are distinct.
 */
@RunWith(LeetCodeRunner.class)
public class Q1898_MaximumNumberOfRemovableCharacters {

    /**
     * 根据hint 使用二分查找, 时间复杂度 O(mlog(len))
     */
    @Answer
    public int maximumRemovals(String s, String p, int[] removable) {
        final int m = s.length();
        final int n = p.length();
        final int len = removable.length;
        final char[] scs = s.toCharArray();
        final char[] pcs = p.toCharArray();

        // s 中的下标对应到removable 中的位置
        int[] removes = new int[m];
        Arrays.fill(removes, len);
        for (int i = 0; i < len; i++) {
            removes[removable[i]] = i;
        }

        // 结果在 [start, end) 的区间里
        int start = 0, end = len;
        while (start < end) {
            int mid = (start + end) / 2;
            int match = 0;
            for (int i = 0; i < m && match < n; i++) {
                if (removes[i] > mid && scs[i] == pcs[match]) {
                    match++;
                }
            }
            if (match == n) {
                // 有子字符串
                start = mid + 1;
            } else {
                // 无子字符串
                end = mid;
            }
        }
        return start;
    }

    @TestData
    public DataExpectation example1 = DataExpectation
            .createWith("abcacb", "ab", new int[]{3, 1, 0})
            .expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation
            .createWith("abcbddddd", "abcd", new int[]{3, 2, 1, 4, 5, 6})
            .expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation
            .createWith("abcab", "abc", new int[]{0, 1, 2, 3, 4})
            .expect(0);

    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith("aaaaa", "aaa", new int[]{0, 1, 2, 3, 4})
            .expect(2);

}
