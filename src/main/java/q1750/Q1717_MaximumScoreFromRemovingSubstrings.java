package q1750;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Medium] 1717. Maximum Score From Removing Substrings
 * https://leetcode.com/problems/maximum-score-from-removing-substrings/
 *
 * You are given a string s and two integers x and y. You can perform two types of operations any number of times.
 *
 * Remove substring "ab" and gain x points.
 *
 * For example, when removing "ab" from "cabxbae" it becomes "cxbae".
 *
 *
 * Remove substring "ba" and gain y points.
 *
 * For example, when removing "ba" from "cabxbae" it becomes "cabxe".
 *
 *
 *
 * Return the maximum points you can gain after applying the above operations on s.
 *
 * Example 1:
 *
 * Input: s = "cdbcbbaaabab", x = 4, y = 5
 * Output: 19
 * Explanation:
 * - Remove the "ba" underlined in "cdbcbbaaabab". Now, s = "cdbcbbaaab" and 5 points are added to the score.
 * - Remove the "ab" underlined in "cdbcbbaaab". Now, s = "cdbcbbaa" and 4 points are added to the score.
 * - Remove the "ba" underlined in "cdbcbbaa". Now, s = "cdbcba" and 5 points are added to the score.
 * - Remove the "ba" underlined in "cdbcba". Now, s = "cdbc" and 5 points are added to the score.
 * Total score = 5 + 4 + 5 + 5 = 19.
 *
 * Example 2:
 *
 * Input: s = "aabbaaxybbaabb", x = 5, y = 4
 * Output: 20
 *
 * Constraints:
 *
 * 1 <= s.length <= 10^5
 * 1 <= x, y <= 10^4
 * s consists of lowercase English letters.
 */
@RunWith(LeetCodeRunner.class)
public class Q1717_MaximumScoreFromRemovingSubstrings {

    @Answer
    public int maximumGain(String s, int x, int y) {
        int res = 0, a = 0, b = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a') {
                if (b > 0 && y >= x) {
                    b--;
                    res += y;
                } else {
                    a++;
                }
            } else if (c == 'b') {
                if (a > 0 && x >= y) {
                    a--;
                    res += x;
                } else {
                    b++;
                }
            } else {
                res += Math.min(a, b) * Math.min(x, y);
                a = b = 0;
            }
        }
        res += Math.min(a, b) * Math.min(x, y);
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.createWith("cdbcbbaaabab", 4, 5).expect(19);

    @TestData
    public DataExpectation example2 = DataExpectation.createWith("aabbaaxybbaabb", 5, 4).expect(20);

}
