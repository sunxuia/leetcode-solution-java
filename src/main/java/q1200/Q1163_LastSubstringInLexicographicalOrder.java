package q1200;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFile;
import util.runner.data.TestDataFileHelper;

/**
 * [Hard] 1163. Last Substring in Lexicographical Order
 * https://leetcode.com/problems/last-substring-in-lexicographical-order/
 *
 * Given a string s, return the last substring of s in lexicographical order.
 *
 * Example 1:
 *
 * Input: "abab"
 * Output: "bab"
 * Explanation: The substrings are ["a", "ab", "aba", "abab", "b", "ba", "bab"]. The lexicographically maximum substring
 * is "bab".
 *
 * Example 2:
 *
 * Input: "leetcode"
 * Output: "tcode"
 *
 * Note:
 *
 * 1 <= s.length <= 4 * 10^5
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1163_LastSubstringInLexicographicalOrder {

    /**
     * 时间复杂度 O(n^2) + 剪枝操作.
     */
    @Answer
    public String lastSubstring(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int max = 0, diff = 0;
        for (int i = 1; i < n; i++) {
            // 在这里添加剪枝操作(针对 aaa 这样的情况), 否则会超时.
            // diff 表示i 之前不同的字符, 如果之前的字符比它大则没有比较的意义.
            int comp = cs[i] - cs[diff];
            if (comp != 0) {
                diff = i;
            }
            if (comp <= 0) {
                continue;
            }

            // 比较字符串[max, n) 与 [i, n) 大小
            int j = i, k = max;
            while (j < n && cs[j] == cs[k]) {
                j++;
                k++;
            }
            if (j < n && cs[j] > cs[k]) {
                max = i;
            }
        }
        return s.substring(max);
    }

    /**
     * LeetCode 上比较快的解法, 与上面时间复杂度一样都是 O(n^2).
     */
    @Answer
    public String lastSubstring2(String s) {
        final char[] cs = s.toCharArray();
        final int n = cs.length;
        int max = 0;
        for (int i = 1, len = 0; i + len < n; ) {
            if (cs[max + len] == cs[i + len]) {
                // 剪枝操作(针对 aaa 这样的情况)
                len++;
            } else if (cs[max + len] < cs[i + len]) {
                // [max, len] < [i, len], 则 [max, n) < [i, n)
                max = i++;
                len = 0;
            } else {
                // [max, len] >= [i, len] 的情况, 则i 更新为最新的位置.
                i += len + 1;
                len = 0;
            }
        }
        return s.substring(max);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abab").expect("bab");

    @TestData
    public DataExpectation example2 = DataExpectation.create("leetcode").expect("tcode");

    @TestData
    public DataExpectation normal1 = DataExpectation.create("cacb").expect("cb");

    private TestDataFile testDataFile = new TestDataFile();

    @TestData
    public DataExpectation overTime = DataExpectation
            .create(TestDataFileHelper.read(testDataFile, 1, String.class))
            .expect(TestDataFileHelper.read(testDataFile, 1, String.class));

}
