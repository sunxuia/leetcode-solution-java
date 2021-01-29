package q1650;

import java.util.Arrays;
import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1624. Largest Substring Between Two Equal Characters
 * https://leetcode.com/problems/largest-substring-between-two-equal-characters/
 *
 * Given a string s, return the length of the longest substring between two equal characters, excluding the two
 * characters. If there is no such substring return -1.
 *
 * A substring is a contiguous sequence of characters within a string.
 *
 * Example 1:
 *
 * Input: s = "aa"
 * Output: 0
 * Explanation: The optimal substring here is an empty substring between the two 'a's.
 *
 * Example 2:
 *
 * Input: s = "abca"
 * Output: 2
 * Explanation: The optimal substring here is "bc".
 *
 * Example 3:
 *
 * Input: s = "cbzxy"
 * Output: -1
 * Explanation: There are no characters that appear twice in s.
 *
 * Example 4:
 *
 * Input: s = "cabbac"
 * Output: 4
 * Explanation: The optimal substring here is "abba". Other non-optimal substrings include "bb" and "".
 *
 * Constraints:
 *
 * 1 <= s.length <= 300
 * s contains only lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1624_LargestSubstringBetweenTwoEqualCharacters {

    @Answer
    public int maxLengthBetweenEqualCharacters(String s) {
        int[] prevs = new int[26];
        Arrays.fill(prevs, -1);
        int res = -1;
        for (int i = 0; i < s.length(); i++) {
            int idx = s.charAt(i) - 'a';
            if (prevs[idx] == -1) {
                prevs[idx] = i;
            }
            res = Math.max(res, i - prevs[idx] - 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("aa").expect(0);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abca").expect(2);

    @TestData
    public DataExpectation example3 = DataExpectation.create("cbzxy").expect(-1);

    @TestData
    public DataExpectation example4 = DataExpectation.create("cabbac").expect(4);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("mgntdygtxrvxjnwksqhxuxtrv").expect(18);

}
