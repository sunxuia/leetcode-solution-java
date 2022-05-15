package q1950;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1910. Remove All Occurrences of a Substring
 * https://leetcode.com/problems/remove-all-occurrences-of-a-substring/
 *
 * Given two strings s and part, perform the following operation on s until all occurrences of the substring part are
 * removed:
 *
 * Find the leftmost occurrence of the substring part and remove it from s.
 *
 * Return s after removing all occurrences of part.
 *
 * A substring is a contiguous sequence of characters in a string.
 *
 * Example 1:
 *
 * Input: s = "daabcbaabcbc", part = "abc"
 * Output: "dab"
 * Explanation: The following operations are done:
 * - s = "daabcbaabcbc", remove "abc" starting at index 2, so s = "dabaabcbc".
 * - s = "dabaabcbc", remove "abc" starting at index 4, so s = "dababc".
 * - s = "dababc", remove "abc" starting at index 3, so s = "dab".
 * Now s has no occurrences of "abc".
 *
 * Example 2:
 *
 * Input: s = "axxxxyyyyb", part = "xy"
 * Output: "ab"
 * Explanation: The following operations are done:
 * - s = "axxxxyyyyb", remove "xy" starting at index 4 so s = "axxxyyyb".
 * - s = "axxxyyyb", remove "xy" starting at index 3 so s = "axxyyb".
 * - s = "axxyyb", remove "xy" starting at index 2 so s = "axyb".
 * - s = "axyb", remove "xy" starting at index 1 so s = "ab".
 * Now s has no occurrences of "xy".
 *
 * Constraints:
 *
 * 1 <= s.length <= 1000
 * 1 <= part.length <= 1000
 * s and part consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1910_RemoveAllOccurrencesOfASubstring {

    @Answer
    public String removeOccurrences(String s, String part) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        final int m = part.length();

        // 构建kmp
        int[][] kmp = new int[m][26];
        int[] reset = kmp[0];
        reset[part.charAt(0) - 'a'] = 1;
        for (int i = 1; i < m; i++) {
            for (int j = 0; j < 26; j++) {
                kmp[i][j] = reset[j];
            }
            int idx = part.charAt(i) - 'a';
            kmp[i][idx] = i + 1;
            reset = kmp[reset[idx]];
        }

        // 匹配
        int len = 0;
        // states[i+1] 表示匹配到 cs[i] (包含)时的状态.
        int[] states = new int[n + 1];
        for (int i = 0; i < n; i++) {
            cs[len++] = cs[i];
            states[len] = kmp[states[len - 1]][cs[i] - 'a'];
            if (states[len] == m) {
                // 找到匹配, 则向前回溯
                len -= m;
            }
        }
        return new String(cs, 0, len);
    }

    /**
     * leetcode 上最快的做法, 反而简单
     */
    @Answer
    public String removeOccurrences_leetcode(String s, String part) {
        final StringBuilder sb = new StringBuilder(s);
        for (int index = sb.indexOf(part);
                index != -1;
                index = sb.indexOf(part)) {
            sb.delete(index, index + part.length());
        }
        return sb.toString();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("daabcbaabcbc", "abc").expect("dab");

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("axxxxyyyyb", "xy").expect("ab");

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("a", "aa").expect("a");

}
