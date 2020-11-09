package q1350;

import java.util.HashSet;
import java.util.Set;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Hard] 1316. Distinct Echo Substrings
 * https://leetcode.com/problems/distinct-echo-substrings/
 *
 * Return the number of distinct non-empty substrings of text that can be written as the concatenation of some string
 * with itself (i.e. it can be written as a + a where a is some string).
 *
 * Example 1:
 *
 * Input: text = "abcabcabc"
 * Output: 3
 * Explanation: The 3 substrings are "abcabc", "bcabca" and "cabcab".
 *
 * Example 2:
 *
 * Input: text = "leetcodeleetcode"
 * Output: 2
 * Explanation: The 2 substrings are "ee" and "leetcodeleetcode".
 *
 * Constraints:
 *
 * 1 <= text.length <= 2000
 * text has only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1316_DistinctEchoSubstrings {

    /**
     * 没看懂题目什么意思.
     * 参考文档 https://leetcode.jp/problemdetail.php?id=1316
     */
    @Answer
    public int distinctEchoSubstrings(String text) {
        final int n = text.length();
        // 保存找到的不重复的循环子串
        Set<String> set = new HashSet<>();
        // 循环子串开头位置
        for (int i = 0; i < n; i++) {
            // 循环子串长度
            for (int j = 0; (j + 1) * 2 + i - 1 < n; j++) {
                // 当前子串长度
                int length = j + 1;
                // 当前子串
                String s1 = text.substring(i, i + length);
                // 与当前子串长度相同的相邻子串
                String s2 = text.substring(i + length, i + length * 2);
                // 如果2子串相同，将其加入返回结果
                if (s1.equals(s2)) {
                    set.add(s1);
                }
            }
        }
        return set.size();
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("abcabcabc").expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.create("leetcodeleetcode").expect(2);

}
