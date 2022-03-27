package q1800;

import org.junit.runner.RunWith;
import util.runner.Answer;
import util.runner.LeetCodeRunner;
import util.runner.TestData;
import util.runner.data.DataExpectation;

/**
 * [Easy] 1796. Second Largest Digit in a String
 * https://leetcode.com/problems/second-largest-digit-in-a-string/
 *
 * Given an alphanumeric string s, return the second largest numerical digit that appears in s, or -1 if it does not
 * exist.
 *
 * An alphanumeric string is a string consisting of lowercase English letters and digits.
 *
 * Example 1:
 *
 * Input: s = "dfa12321afd"
 * Output: 2
 * Explanation: The digits that appear in s are [1, 2, 3]. The second largest digit is 2.
 *
 * Example 2:
 *
 * Input: s = "abc1111"
 * Output: -1
 * Explanation: The digits that appear in s are [1]. There is no second largest digit.
 *
 * Constraints:
 *
 * 1 <= s.length <= 500
 * s consists of only lowercase English letters and/or digits.
 */
@RunWith(LeetCodeRunner.class)
public class Q1796_SecondLargestDigitInAString {

    @Answer
    public int secondHighest(String s) {
        int first = -1, second = -1;
        for (int i = 0; i < s.length(); i++) {
            int num = s.charAt(i) - '0';
            if (0 <= num && num <= 9) {
                if (first < num) {
                    second = first;
                    first = num;
                } else if (second < num && num < first) {
                    second = num;
                }
            }
        }
        return second;
    }

    @TestData
    public DataExpectation example1 = DataExpectation.create("dfa12321afd").expect(2);

    @TestData
    public DataExpectation example2 = DataExpectation.create("abc1111").expect(-1);

}
