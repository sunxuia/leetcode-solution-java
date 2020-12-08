package q1400;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1392. Longest Happy Prefix
 * https://leetcode.com/problems/longest-happy-prefix/
 *
 * A string is called a happy prefix if is a non-empty prefix which is also a suffix (excluding itself).
 *
 * Given a string s. Return the longest happy prefix of s .
 *
 * Return an empty string if no such prefix exists.
 *
 * Example 1:
 *
 * Input: s = "level"
 * Output: "l"
 * Explanation: s contains 4 prefix excluding itself ("l", "le", "lev", "leve"), and suffix ("l", "el", "vel", "evel").
 * The largest prefix which is also suffix is given by "l".
 *
 * Example 2:
 *
 * Input: s = "ababab"
 * Output: "abab"
 * Explanation: "abab" is the largest prefix which is also suffix. They can overlap in the original string.
 *
 * Example 3:
 *
 * Input: s = "leetcodeleet"
 * Output: "leet"
 *
 * Example 4:
 *
 * Input: s = "a"
 * Output: ""
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1392_LongestHappyPrefix {

    /**
     * rolling hash 的解法, 参考文档
     * https://leetcode.jp/leetcode-1392-longest-happy-prefix-%E8%A7%A3%E9%A2%98%E6%80%9D%E8%B7%AF%E5%88%86%E6%9E%90/
     * 哈希算法: hash(str+c) = hash(str) * 31 + c
     * 这种算法在测试用例中没有重复情况.
     */
    @Answer
    public String longestPrefix(String s) {
        int head = 0, tail = 0, power = 1, maxLen = 0;
        for (int i = 0, j = s.length() - 1; j > 0; i++, j--) {
            head = head * 31 + s.charAt(i);
            tail = s.charAt(j) * power + tail;
            power *= 31;
            if (head == tail) {
                maxLen = i + 1;
            }
        }
        return s.substring(0, maxLen);
    }

    /**
     * kmp/ dp 解法.
     * 参考文档 https://zhuanlan.zhihu.com/p/122220057
     */
    @Answer
    public String longestPrefix2(String s) {
        final int n = s.length();
        int[] dp = new int[n];
        for (int i = 1; i < n; i++) {
            int j = dp[i - 1];
            // 找到匹配的上一个字符的位置
            while (j > 0 && s.charAt(i) != s.charAt(j)) {
                j = dp[j - 1];
            }
            if (s.charAt(i) == s.charAt(j)) {
                dp[i] = j + 1;
            }
        }
        return s.substring(0, dp[n - 1]);
    }

    /**
     * leetcode 上最快的解法, 用最长回文(LPS) 来做.
     */
    @Answer
    public String longestPrefix3(String s) {
        int[] lps = buildLPS(s);
        int len = lps[s.length() - 1];
        return s.substring(0, len);
    }

    private int[] buildLPS(String s) {
        final char[] sc = s.toCharArray();
        final int n = sc.length;
        int[] lps = new int[n];
        lps[0] = 0;
        int i = 1;
        int len = 0;
        while (i < n) {
            if (sc[i] == sc[len]) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len > 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = len;
                    i++;
                }
            }
        }
        return lps;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("level").expect("l");

    @TestData
    public DataExpectation example2 = DataExpectation.create("ababab").expect("abab");

    @TestData
    public DataExpectation example3 = DataExpectation.create("leetcodeleet").expect("leet");

    @TestData
    public DataExpectation example4 = DataExpectation.create("a").expect("");

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime1 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, String.class))
            .expect(TestDataFileHelper.read(testDataFile, 1, String.class)
                    .then(s -> s.substring(0, s.indexOf('b'))));

    @TestData
    public DataExpectation overTime2 = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 3, String.class))
            .expect(TestDataFileHelper.read(testDataFile, 3, String.class)
                    .then(s -> s.substring(0, 70000)));

}
