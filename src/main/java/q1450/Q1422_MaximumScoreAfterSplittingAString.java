package q1450;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1422. Maximum Score After Splitting a String
 * https://leetcode.com/problems/maximum-score-after-splitting-a-string/
 *
 * Given a string s of zeros and ones, return the maximum score after splitting the string into two non-empty substrings
 * (i.e. left substring and right substring).
 *
 * The score after splitting a string is the number of zeros in the left substring plus the number of ones in the right
 * substring.
 *
 * Example 1:
 *
 * Input: s = "011101"
 * Output: 5
 * Explanation:
 * All possible ways of splitting s into two non-empty substrings are:
 * left = "0" and right = "11101", score = 1 + 4 = 5
 * left = "01" and right = "1101", score = 1 + 3 = 4
 * left = "011" and right = "101", score = 1 + 2 = 3
 * left = "0111" and right = "01", score = 1 + 1 = 2
 * left = "01110" and right = "1", score = 2 + 1 = 3
 *
 * Example 2:
 *
 * Input: s = "00111"
 * Output: 5
 * Explanation: When left = "00" and right = "111", we get the maximum score = 2 + 3 = 5
 *
 * Example 3:
 *
 * Input: s = "1111"
 * Output: 3
 *
 * Constraints:
 *
 * 2 <= s.length <= 500
 * The string s consists of characters '0' and '1' only.
 */
@RunWith(LeetCodeRunner.class)
public class Q1422_MaximumScoreAfterSplittingAString {

    @Answer
    public int maxScore(String s) {
        int right = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '1') {
                right++;
            }
        }
        int res = 0, left = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '1') {
                right--;
            } else {
                left++;
            }
            res = Math.max(res, left + right);
        }
        return res;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("011101").expect(5);

    @TestData
    public DataExpectation example2 = DataExpectation.create("00111").expect(5);

    @TestData
    public DataExpectation example3 = DataExpectation.create("1111").expect(3);

    @TestData
    public DataExpectation normal1 = DataExpectation.create("00").expect(1);

}
