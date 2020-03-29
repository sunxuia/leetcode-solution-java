package q500;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * https://leetcode.com/problems/repeated-substring-pattern/
 *
 * Given a non-empty string check if it can be constructed by taking a substring of it and appending multiple copies
 * of the substring together. You may assume the given string consists of lowercase English letters only and its
 * length will not exceed 10000.
 *
 *
 *
 * Example 1:
 *
 * Input: "abab"
 * Output: True
 * Explanation: It's the substring "ab" twice.
 *
 * Example 2:
 *
 * Input: "aba"
 * Output: False
 *
 * Example 3:
 *
 * Input: "abcabcabcabc"
 * Output: True
 * Explanation: It's the substring "abc" four times. (And the substring "abcabc" twice.)
 */
@RunWith(LeetCodeRunner.class)
public class Q459_RepeatedSubstringPattern {

    // 这个时间复杂度是 O(n^2), 不知道为什么和 O(N) 的那些解法耗时一样了
    @Answer
    public boolean repeatedSubstringPattern(String s) {
        char[] sc = s.toCharArray();
        for (int i = sc.length / 2; i > 0; i--) {
            if (sc.length % i != 0) {
                continue;
            }
            int j = 0;
            while (j < sc.length - i && sc[j] == sc[i + j]) {
                j++;
            }
            if (j == sc.length - i) {
                return true;
            }
        }
        return false;
    }

    // leetcode 上使用 kmp 的解法
    @Answer
    public boolean repeatedSubstringPattern2(String s) {
        char[] sc = s.toCharArray();
        final int n = sc.length;
        int[] kmp = new int[n];
        int i = 0, j = 1;
        while (j < n) {
            if (sc[i] == sc[j]) {
                // 相等 -> 继续匹配
                kmp[j] = i + 1;
                i++;
                j++;
            } else if (i > 0) {
                // 不相等且已经匹配了一部分距离 -> 跳到上一个匹配位置
                i = kmp[i - 1];
            } else {
                // 没有任何匹配
                i = 0;
                j++;
            }
        }
        return kmp[n - 1] != 0 && (kmp[n - 1] % (n - kmp[n - 1]) == 0);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abab").expect(true);

    @TestData
    public DataExpectation example2 = DataExpectation.create("aba").expect(false);

    @TestData
    public DataExpectation example3 = DataExpectation.create("abcabcabcabc").expect(true);

}
