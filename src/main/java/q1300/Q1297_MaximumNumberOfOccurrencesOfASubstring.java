package q1300;

import java.util.HashMap;
import java.util.Map;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;
import util.runner.data.TestDataFileHelper;

/**
 * [Medium] 1297. Maximum Number of Occurrences of a Substring
 * https://leetcode.com/problems/maximum-number-of-occurrences-of-a-substring/
 *
 * Given a string s, return the maximum number of ocurrences of any substring under the following rules:
 *
 * The number of unique characters in the substring must be less than or equal to maxLetters.
 * The substring size must be between minSize and maxSize inclusive.
 *
 * Example 1:
 *
 * Input: s = "aababcaab", maxLetters = 2, minSize = 3, maxSize = 4
 * Output: 2
 * Explanation: Substring "aab" has 2 ocurrences in the original string.
 * It satisfies the conditions, 2 unique letters and size 3 (between minSize and maxSize).
 *
 * Example 2:
 *
 * Input: s = "aaaa", maxLetters = 1, minSize = 3, maxSize = 3
 * Output: 2
 * Explanation: Substring "aaa" occur 2 times in the string. It can overlap.
 *
 * Example 3:
 *
 * Input: s = "aabcabcab", maxLetters = 2, minSize = 2, maxSize = 3
 * Output: 3
 *
 * Example 4:
 *
 * Input: s = "abcde", maxLetters = 2, minSize = 3, maxSize = 3
 * Output: 0
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * 1 <= maxLetters <= 26
 * 1 <= minSize <= maxSize <= min(26, s.length)
 * s only contains lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1297_MaximumNumberOfOccurrencesOfASubstring {

    @Answer
    public int maxFreq(String s, int maxLetters, int minSize, int maxSize) {
        Map<String, Integer> map = new HashMap<>();
        loop:
        for (int i = 0; i <= s.length() - minSize; i++) {
            int j = i, letters = 0, count = 0;
            while (j < s.length() && j < i + minSize) {
                if ((letters & (1 << s.charAt(j) - 'a')) == 0) {
                    count++;
                    letters |= 1 << s.charAt(j) - 'a';
                }
                if (count > maxLetters) {
                    continue loop;
                }
                j++;
            }
            if (j - i >= minSize) {
                String str = s.substring(i, j);
                map.put(str, map.getOrDefault(str, 0) + 1);
            }
        }
        return map.values().stream()
                .max(Integer::compareTo)
                .orElse(0);
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("aababcaab", 2, 3, 4).expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("aaaa", 1, 3, 3).expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("aabcabcab", 2, 2, 3).expect(3);

    @TestData
    public DataExpectation example4 = DataExpectation.createWith("abcde", 2, 3, 3).expect(0);

    // 1万个字符, 末尾的字符串匹配
    @TestData
    public DataExpectation normal1 = DataExpectation
            .createWith(TestDataFileHelper.readString("Q1297_TestData_1"), 6, 20, 26).expect(1);

    // 5万个字符
    @TestData
    public DataExpectation overTime = DataExpectation
            .createWith(TestDataFileHelper.readString("Q1297_TestData_2"), 3, 5, 26).expect(18);

}
