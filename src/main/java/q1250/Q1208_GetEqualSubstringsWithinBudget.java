package q1250;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1208. Get Equal Substrings Within Budget
 * https://leetcode.com/problems/get-equal-substrings-within-budget/
 *
 * You are given two strings s and t of the same length. You want to change s to t. Changing the i-th character of s to
 * i-th character of t costs |s[i] - t[i]| that is, the absolute difference between the ASCII values of the characters.
 *
 * You are also given an integer maxCost.
 *
 * Return the maximum length of a substring of s that can be changed to be the same as the corresponding substring of
 * twith a cost less than or equal to maxCost.
 *
 * If there is no substring from s that can be changed to its corresponding substring from t, return 0.
 *
 * Example 1:
 *
 * Input: s = "abcd", t = "bcdf", maxCost = 3
 * Output: 3
 * Explanation: "abc" of s can change to "bcd". That costs 3, so the maximum length is 3.
 *
 * Example 2:
 *
 * Input: s = "abcd", t = "cdef", maxCost = 3
 * Output: 1
 * Explanation: Each character in s costs 2 to change to charactor in t, so the maximum length is 1.
 *
 * Example 3:
 *
 * Input: s = "abcd", t = "acde", maxCost = 0
 * Output: 1
 * Explanation: You can't make any change, so the maximum length is 1.
 *
 * Constraints:
 *
 * 1 <= s.length, t.length <= 10^5
 * 0 <= maxCost <= 10^6
 * s and t only contain lower case English letters.
 *
 * 题解: 大意就是变化一个字符 s[i] -> t[i] 需要花费 | s[i] - t[i] |,
 * 现在选择一个区间 [i, j] 让s和t 的子数组 s[i:j] -> t[i:j] 的变化之和消耗不超过 maxCost,
 * 求这个区间的最大长度.
 */
@RunWith(LeetCodeRunner.class)
public class Q1208_GetEqualSubstringsWithinBudget {

    @Answer
    public int equalSubstring(String s, String t, int maxCost) {
        int res = 0, sum = 0, prev = 0;
        for (int i = 0; i < s.length(); i++) {
            sum += Math.abs(s.charAt(i) - t.charAt(i));
            while (sum > maxCost) {
                sum -= Math.abs(s.charAt(prev) - t.charAt(prev));
                prev++;
            }
            res = Math.max(res, i - prev + 1);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("abcd", "bcdf", 3).expect(3);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("abcd", "cdef", 3).expect(1);

    @TestData
    public DataExpectation example3 = DataExpectation.createWith("abcd", "acde", 0).expect(1);

    @TestData
    public DataExpectation normal1 = DataExpectation.createWith("krrgw", "zjxss", 19).expect(2);

}
